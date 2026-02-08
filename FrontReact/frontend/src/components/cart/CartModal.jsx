import { useDispatch, useSelector } from "react-redux";
import {
  Alert,
  AlertIcon,
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
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { closeCartAsync, closeCartModal, confirmCartAsync } from "../../redux/features/cartSlice";
import { USER_TYPE, getFromLocalStorage } from "../../functions/localStorage";

export default function CartModal() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { isCartModalOpen, items, totalAmount, loading, error, cartId } = useSelector(
    (state) => state.cart
  );

  const userType = getFromLocalStorage(USER_TYPE);
  const isLocal = userType === "LOCAL";
  const hasUnprocessedItems = items.some((item) => Number(item.processed) === 0);
  const canCloseCart = !hasUnprocessedItems && !loading;

  if (!isCartModalOpen) return null;

  const payload = {
    cartId,
    deliveryMode: userType,
    paymentMethod: 99,
    phone: "",
    comments: "",
    directionId: -1,
  };

  const handleConfirm = async () => {
    const order = await dispatch(confirmCartAsync());
    if (!order) {
      return;
    }

    if (isLocal) {
      dispatch(closeCartModal());
      navigate("/menu");
      return;
    }

    const bill = await dispatch(closeCartAsync(payload));
    dispatch(closeCartModal());
    navigate("/checkout", { state: { bill } });
  };

  const handleClose = async () => {
    dispatch(closeCartModal());
    const bill = await dispatch(closeCartAsync(payload));
    navigate("/checkout", { state: { bill } });
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
        <DrawerCloseButton color="orange.500" _hover={{ color: "orange.600" }} />
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

          {items.length === 0 && !loading && <Text color="gray.500">No hay productos.</Text>}

          {items.length > 0 && (
            <VStack spacing={3} align="stretch">
              {items.map((item, index) => (
                <Box
                  key={`${item.cartItemId}-${index}`}
                  borderWidth="1px"
                  borderColor="orange.100"
                  rounded="lg"
                  px={3}
                  py={2}
                  bg="orange.50"
                  boxShadow="sm"
                >
                  <HStack justify="space-between" align="start">
                    <Box>
                      <Text fontWeight="semibold">{item.name}</Text>
                      <Text fontSize="sm" color="gray.500">
                        Cantidad: {item.quantity}
                      </Text>
                    </Box>
                    <Text fontWeight="semibold">${Number(item.unitPrice) * item.quantity}</Text>
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
          <Button variant="ghost" colorScheme="orange" onClick={() => dispatch(closeCartModal())}>
            Volver
          </Button>
          {isLocal && (
            <Button
              variant="outline"
              colorScheme="red"
              onClick={handleClose}
              isDisabled={!canCloseCart}
            >
              Cerrar carrito
            </Button>
          )}
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
