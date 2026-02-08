import React from "react";
import {
  Badge,
  Button,
  Card,
  CardBody,
  CardFooter,
  Heading,
  HStack,
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
        transform: "translateY(-3px) scale(1.01)",
        boxShadow: "lg",
      }}
      _active={{ transform: "translateY(-1px) scale(1.005)" }}
    >
      <CardBody>
        <Stack spacing={3}>
          <Stack spacing={2}>
            <HStack justify="space-between" align="start">
              <Heading size="md">{item.name}</Heading>
              {item?.type?.name && (
                <Badge
                  colorScheme="orange"
                  variant="subtle"
                  borderRadius="full"
                  px={2}
                >
                  {item.type.name}
                </Badge>
              )}
            </HStack>
            {item.description && <Text color="gray.500">{item.description}</Text>}
          </Stack>
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
