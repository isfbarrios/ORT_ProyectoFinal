import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Box,
  SimpleGrid,
  Spinner,
  Text,
  VStack,
  Alert,
  AlertIcon,
} from "@chakra-ui/react";

import { getMenuItemsByMenu } from "../services/menuService";
import { addItemToCartAsync } from "../redux/features/cartSlice";
import MenuHeader from "../components/menu/MenuHeader";
import MenuItemCard from "../components/menu/MenuItemCard";
import MenuEmptyState from "../components/menu/MenuEmptyState";

export default function Menu({ menuId = 1, refreshKey = 0 }) {

  const dispatch = useDispatch();
  const cart = useSelector((state) => state.cart);

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

  return (
    <Box>
      <MenuHeader
        searchQuery={searchQuery}
        onSearchChange={setSearchQuery}
      />

      {loadingMenu && (
        <VStack spacing={3} py={10}>
          <Spinner size="lg" color="orange.400" />
          <Text color="gray.500">Cargando menú...</Text>
        </VStack>
      )}

      {errorMenu && (
        <Alert status="error" borderRadius="md" mb={6}>
          <AlertIcon />
          {errorMenu}
        </Alert>
      )}

      {!loadingMenu && !errorMenu && (
        <SimpleGrid columns={{ base: 1, md: 2, lg: 3 }} spacing={6}>
          {filteredItems.map((item) => (
            <MenuItemCard
              key={item.id}
              item={item}
              onAddToCart={handleAddToCart}
              isLoading={cart.loading}
            />
          ))}
        </SimpleGrid>
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
