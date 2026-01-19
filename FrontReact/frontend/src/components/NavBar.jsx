import { NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  Badge,
  Box,
  Flex,
  HStack,
  Link as ChakraLink,
  Text,
  Button,
} from "@chakra-ui/react";
import { openCartModal, fetchCartAsync } from "../redux/features/cartSlice";

function NavItem({ to, children }) {
  return (
    <ChakraLink
      as={NavLink}
      to={to}
      px={3}
      py={2}
      rounded="md"
      fontWeight="semibold"
      _hover={{ textDecoration: "none", bg: "orange.50" }}
      _activeLink={{ bg: "orange.100", color: "orange.700" }}
    >
      {children}
    </ChakraLink>
  );
}

function NavAction({ onClick, children }) {
  return (
    <Button
      onClick={onClick}
      px={3}
      py={2}
      rounded="md"
      fontWeight="semibold"
      variant="outline"
      colorScheme="orange"
      _hover={{ bg: "orange.50" }}
      _active={{ bg: "orange.100", color: "orange.700" }}
    >
      {children}
    </Button>
  );
}

export default function NavBar() {
  const dispatch = useDispatch();
  const itemsCount = useSelector((state) => state.cart.items.length);
  const totalAmount = useSelector((state) => state.cart.totalAmount);
  const user = useSelector((state) => state.app.user);
  const userType = (user?.userType || user?.type || user?.role || "").toString();
  const normalizedType = userType.toUpperCase();
  const isLocal = normalizedType === "LOCAL";
  const isKitchen = normalizedType === "COCINA";

  const handleOpenCart = () => {
    dispatch(fetchCartAsync());
    dispatch(openCartModal());
  };

  return (
    <Box
      as="nav"
      bg="white"
      borderWidth="1px"
      borderColor="orange.100"
      rounded="lg"
      px={4}
      py={3}
      mb={6}
      boxShadow="sm"
    >
      <Flex align="center" justify="space-between" gap={4} wrap="wrap">
        <HStack spacing={2}>
          {isLocal && (
            <>
              <NavItem to="/menu">Menu</NavItem>
              <NavAction onClick={() => {}}>Llamar mozo</NavAction>
            </>
          )}
          {!isLocal && (
            <>
              {isKitchen && <NavItem to="/dashboard">Dashboard</NavItem>}
              <NavItem to="/menu">Menu</NavItem>
              <NavItem to="/directions">Direcciones</NavItem>
              <NavItem to="/reserva">Reservas</NavItem>
            </>
          )}
        </HStack>

        <Button
          onClick={handleOpenCart}
          colorScheme="orange"
          variant="solid"
          size="sm"
        >
          <HStack spacing={2}>
            <Text>Carrito</Text>
            <Badge colorScheme="whiteAlpha" bg="whiteAlpha.900" color="orange.700">
              {itemsCount}
            </Badge>
            {totalAmount > 0 && (
              <Text fontWeight="semibold">- ${totalAmount}</Text>
            )}
          </HStack>
        </Button>
      </Flex>
    </Box>
  );
}
