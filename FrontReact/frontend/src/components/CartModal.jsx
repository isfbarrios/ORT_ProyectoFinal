import { useDispatch, useSelector } from "react-redux";
import {
  Box,
  Button,
  Divider,
  Drawer,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  HStack,
  Spinner,
  Text,
  VStack,
  Alert,
  AlertIcon,
} from "@chakra-ui/react";

import {
  closeCartModal,
  confirmCartAsync,
  //closeCartAsync,
} from "../redux/features/cartSlice";
import { useNavigate } from "react-router-dom";
import { USER_TYPE, getFromLocalStorage } from "../functions/localStorage";

export default function CartModal() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { isCartModalOpen, items, totalAmount, loading, error, /*cartId*/ } = useSelector(
    (state) => state.cart
  );
  const userType = getFromLocalStorage(USER_TYPE);

  const isLocal = userType === "LOCAL";
  //const hasItems = items.length > 0;
  const hasUnprocessedItems = items.some(
    (item) => Number(item.processed) === 0
  );
  const canCloseCart = !hasUnprocessedItems && !loading;

  if (!isCartModalOpen) return null;

  const handleConfirm = async () => {
    if (isLocal) {
      const order = await dispatch(confirmCartAsync());
      if (order) {
        dispatch(closeCartModal());
        navigate("/menu");
      }

      return;
    }
    else {
      const order = await dispatch(confirmCartAsync());
      if (order) {
        dispatch(closeCartModal());
        navigate("/checkout");
      }

      return;
    }
  };

  const handleClose = async () => {
    dispatch(closeCartModal());
    navigate("/checkout");
  };

  return (
    <Drawer
      isOpen={isCartModalOpen}
      placement="right"
      size="sm"
      onClose={() => dispatch(closeCartModal())}
    >
      <DrawerOverlay />
      <DrawerContent>
        <DrawerCloseButton />
        <DrawerHeader>Carrito</DrawerHeader>

        <DrawerBody>
          {loading && (
            <HStack spacing={3} mb={4}>
              <Spinner size="sm" />
              <Text>Cargando...</Text>
            </HStack>
          )}

          {error && (
            <Alert status="error" mb={4} borderRadius="md">
              <AlertIcon />
              {error}
            </Alert>
          )}

          {items.length === 0 && !loading && (
            <Text color="gray.500">No hay productos.</Text>
          )}

          {items.length > 0 && (
            <VStack spacing={3} align="stretch">
              {items.map((item, index) => (
                <Box
                  key={`${item.cartItemId}-${index}`}
                  borderWidth="1px"
                  borderColor="gray.200"
                  rounded="md"
                  px={3}
                  py={2}
                >
                  <HStack justify="space-between" align="start">
                    <Box>
                      <Text fontWeight="semibold">{item.name}</Text>
                      <Text fontSize="sm" color="gray.500">
                        Cantidad: {item.quantity}
                      </Text>
                    </Box>
                    <Text fontWeight="semibold">
                      ${Number(item.unitPrice) * item.quantity}
                    </Text>
                  </HStack>
                </Box>
              ))}
            </VStack>
          )}

          <Divider my={4} />

          <HStack justify="space-between">
            <Text fontWeight="bold">Total</Text>
            <Text fontWeight="bold">${totalAmount}</Text>
          </HStack>
        </DrawerBody>

        <DrawerFooter gap={2}>
          <Button variant="ghost" onClick={() => dispatch(closeCartModal())}>
            Volver
          </Button>
          <Button
            variant="outline"
            colorScheme="red"
            onClick={handleClose}
            isDisabled={!canCloseCart}
          >
            Cerrar carrito
          </Button>
          <Button
            colorScheme="orange"
            onClick={handleConfirm}
            isDisabled={!hasUnprocessedItems || loading}
          >
            Confirmar pedido
          </Button>
        </DrawerFooter>
      </DrawerContent>
    </Drawer>
  );
}
