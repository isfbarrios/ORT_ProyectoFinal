import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Box,
  Divider,
  HStack,
  Heading,
  SimpleGrid,
  Skeleton,
  SkeletonText,
  Text,
  VStack,
  Alert,
  AlertIcon,
  usePrefersReducedMotion,
} from "@chakra-ui/react";
import { motion } from "framer-motion";

import { getMenuItemsByMenu } from "../services/menuService";
import { addItemToCartAsync } from "../redux/features/cartSlice";
import MenuHeader from "../components/menu/MenuHeader";
import MenuItemCard from "../components/menu/MenuItemCard";
import MenuEmptyState from "../components/menu/MenuEmptyState";

export default function Menu({ menuId = 1, refreshKey = 0 }) {

  const dispatch = useDispatch();
  const cart = useSelector((state) => state.cart);
  const shouldReduceMotion = usePrefersReducedMotion();

  const [items, setItems] = useState([]);
  const [loadingMenu, setLoadingMenu] = useState(true);
  const [errorMenu, setErrorMenu] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");

  // Cargar items del menú
  useEffect(() => {
    const loadMenu = async () => {
      setLoadingMenu(true);
      setErrorMenu(null);

      try {
        const data = await getMenuItemsByMenu(menuId);
        setItems(data);
      } catch (err) {
        console.error("Error cargando menú:", err);
        setErrorMenu(err.message || "Error al cargar el menú");
      } finally {
        setLoadingMenu(false);
      }
    };

    loadMenu();
  }, [menuId, refreshKey]);

  // Cargar carrito (si existe sesión)
  /*useEffect(() => {
    dispatch(fetchCartAsync());
  }, [dispatch]);*/

  const handleAddToCart = (menuItemId) => {
    dispatch(addItemToCartAsync(menuItemId, 1));
  };

  const filteredItems = items.filter((item) =>
    item.name.toLowerCase().includes(searchQuery.trim().toLowerCase())
  );

  const normalizeCategoryName = (value) =>
    value?.toString().trim().toLowerCase();

  const getCategoryKey = (item) => {
    const typeName = normalizeCategoryName(item?.type?.name);
    if (!typeName) return "otros";

    if (typeName.includes("entrada") || typeName.includes("principal") || typeName.includes("comida") || typeName.includes("plato")) {
      return "comidas";
    }

    if (typeName.includes("postre")) {
      return "postres";
    }

    if (typeName.includes("bebida") || typeName.includes("refresco")) {
      return "refrescos";
    }

    return "otros";
  };

  const groupedItems = filteredItems.reduce((acc, item) => {
    const categoryKey = getCategoryKey(item);
    if (!acc[categoryKey]) acc[categoryKey] = [];
    acc[categoryKey].push(item);
    return acc;
  }, {});

  const categoryOrder = [
    { key: "comidas", label: "Comidas" },
    { key: "postres", label: "Postres" },
    { key: "refrescos", label: "Refrescos" },
    { key: "otros", label: "Otros" },
  ];

  const containerVariants = {
    hidden: {},
    show: {
      transition: {
        staggerChildren: 0.08,
      },
    },
  };

  const sectionVariants = {
    hidden: { opacity: 0, y: 12 },
    show: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.4, ease: "easeOut" },
    },
  };

  const skeletonItems = Array.from({ length: 6 }, (_, index) => index);

  return (
    <Box>
      <MenuHeader
        searchQuery={searchQuery}
        onSearchChange={setSearchQuery}
      />

      {loadingMenu && (
        <VStack spacing={6} py={6} align="stretch">
          <SimpleGrid columns={{ base: 1, md: 2, lg: 3 }} spacing={6}>
            {skeletonItems.map((index) => (
              <Box
                key={`menu-skeleton-${index}`}
                bg="white"
                borderWidth="1px"
                borderColor="orange.100"
                rounded="2xl"
                boxShadow="sm"
                p={4}
              >
                <Skeleton height="22px" mb={3} borderRadius="full" />
                <SkeletonText noOfLines={2} spacing="3" skeletonHeight="3" />
                <Skeleton height="18px" mt={4} width="40%" borderRadius="full" />
                <Skeleton height="36px" mt={4} borderRadius="full" />
              </Box>
            ))}
          </SimpleGrid>
        </VStack>
      )}

      {errorMenu && (
        <Alert status="error" borderRadius="md" mb={6}>
          <AlertIcon />
          {errorMenu}
        </Alert>
      )}

      {!loadingMenu && !errorMenu && filteredItems.length > 0 && (
        <VStack
          spacing={8}
          align="stretch"
          {...(shouldReduceMotion
            ? {}
            : {
                as: motion.div,
                initial: "hidden",
                animate: "show",
                variants: containerVariants,
              })}
        >
          {categoryOrder.map((category) => {
            const categoryItems = groupedItems[category.key] || [];
            if (categoryItems.length === 0) return null;

            return (
              <Box
                key={category.key}
                bg="white"
                borderWidth="1px"
                borderColor="orange.100"
                rounded="2xl"
                boxShadow="sm"
                p={{ base: 4, md: 5 }}
                {...(shouldReduceMotion
                  ? {}
                  : {
                      as: motion.div,
                      variants: sectionVariants,
                    })}
              >
                <HStack spacing={3} mb={4}>
                  <Heading size="md" color="orange.700">
                    {category.label}
                  </Heading>
                  <Divider borderColor="orange.100" />
                  <Text color="gray.500" fontSize="sm">
                    {categoryItems.length} ítem{categoryItems.length !== 1 ? "s" : ""}
                  </Text>
                </HStack>
                <SimpleGrid columns={{ base: 1, md: 2, lg: 3 }} spacing={6}>
                  {categoryItems.map((item) => (
                    <MenuItemCard
                      key={item.id}
                      item={item}
                      onAddToCart={handleAddToCart}
                      isLoading={cart.loading}
                    />
                  ))}
                </SimpleGrid>
              </Box>
            );
          })}
        </VStack>
      )}

      {!loadingMenu && !errorMenu && items.length === 0 && (
        <MenuEmptyState message="No hay ítems cargados para este menú." />
      )}

      {!loadingMenu && !errorMenu && items.length > 0 && filteredItems.length === 0 && (
        <MenuEmptyState message="No se encontraron platos con ese nombre." />
      )}
    </Box>
  );
}
