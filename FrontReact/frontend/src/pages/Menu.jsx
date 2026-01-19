import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Box,
  Button,
  Heading,
  SimpleGrid,
  Spinner,
  Text,
  VStack,
  Alert,
  AlertIcon,
  Card,
  CardBody,
  CardFooter,
  Stack,
} from "@chakra-ui/react";

import { getMenuItemsByMenu } from "../services/menuService";
import { addItemToCartAsync, fetchCartAsync } from "../redux/features/cartSlice";

export default function Menu({ menuId = 1 }) {
  const dispatch = useDispatch();
  const cart = useSelector((state) => state.cart);

  const [items, setItems] = useState([]);
  const [loadingMenu, setLoadingMenu] = useState(true);
  const [errorMenu, setErrorMenu] = useState(null);

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
  }, [menuId]);

  // Cargar carrito (si existe sesión)
  useEffect(() => {
    dispatch(fetchCartAsync());
  }, [dispatch]);

  const handleAddToCart = (menuItemId) => {
    dispatch(addItemToCartAsync(menuItemId, 1));
  };

  return (
    <Box>
      <VStack spacing={2} textAlign="center" mb={6}>
        <Heading size="lg">Menú del Restaurante</Heading>
        <Text color="gray.500">
          Elegí tus platos y confirmá el pedido cuando estés listo.
        </Text>
      </VStack>

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
          {items.map((item) => (
            <Card key={item.id} borderWidth="1px" borderColor="orange.100">
              <CardBody>
                <Stack spacing={3}>
                  <Heading size="md">{item.name}</Heading>
                  {item.description && (
                    <Text color="gray.500">{item.description}</Text>
                  )}
                  <Text fontSize="xl" fontWeight="bold" color="orange.600">
                    {item.basePrice !== undefined && item.basePrice !== null
                      ? `$${item.basePrice.toFixed(2)}`
                      : "—"}
                  </Text>
                </Stack>
              </CardBody>
              <CardFooter>
                <Button
                  colorScheme="orange"
                  width="100%"
                  onClick={() => handleAddToCart(item.id)}
                  isLoading={cart.loading}
                  loadingText="Agregando..."
                >
                  Añadir al pedido
                </Button>
              </CardFooter>
            </Card>
          ))}
        </SimpleGrid>
      )}

      {!loadingMenu && !errorMenu && items.length === 0 && (
        <Text textAlign="center" color="gray.500" mt={6}>
          No hay ítems cargados para este menú.
        </Text>
      )}
    </Box>
  );
}
