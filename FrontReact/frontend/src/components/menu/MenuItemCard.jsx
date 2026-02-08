import React from "react";
import {
  Button,
  Card,
  CardBody,
  CardFooter,
  Heading,
  Stack,
  Text,
} from "@chakra-ui/react";
import { USER_TYPE, getFromLocalStorage } from "../../functions/localStorage";

export default function MenuItemCard({ item, onAddToCart, isLoading }) {
  const userType = getFromLocalStorage(USER_TYPE);
  const isKitchen = userType === "COCINA";

  return (
    <Card
      borderWidth="1px"
      borderColor="orange.100"
      borderRadius="xl"
      boxShadow="sm"
      transition="transform 120ms ease, box-shadow 120ms ease"
      _hover={{
        transform: "translateY(-2px)",
        boxShadow: "md",
      }}
    >
      <CardBody>
        <Stack spacing={3}>
          <Heading size="md">{item.name}</Heading>
          {item.description && <Text color="gray.500">{item.description}</Text>}
          <Text fontSize="xl" fontWeight="bold" color="orange.600">
            {item.basePrice !== undefined && item.basePrice !== null
              ? `$${item.basePrice.toFixed(2)}`
              : "—"}
          </Text>
        </Stack>
      </CardBody>
      {!isKitchen && (
        <CardFooter>
          <Button
            colorScheme="orange"
            width="100%"
            onClick={() => onAddToCart(item.id)}
            isLoading={isLoading}
            loadingText="Agregando..."
          >
            Añadir al pedido
          </Button>
        </CardFooter>
      )}
    </Card>
  );
}
