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

export default function MenuItemCard({ item, onAddToCart, isLoading }) {
  return (
    <Card borderWidth="1px" borderColor="orange.100">
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
    </Card>
  );
}
