import {
  Box,
  Card,
  CardBody,
  Divider,
  Heading,
  HStack,
  Text,
  VStack,
} from "@chakra-ui/react";

export default function OrderSummaryCard({ items, totalAmount }) {
  return (
    <Card w={{ base: "100%", lg: "360px" }} borderWidth="1px">
      <CardBody>
        <Heading size="md" mb={4}>
          Resumen
        </Heading>
        <VStack spacing={3} align="stretch">
          {items.map((item) => (
            <HStack key={item.cartItemId} justify="space-between">
              <Box>
                <Text fontWeight="semibold">{item.name}</Text>
                <Text fontSize="sm" color="gray.500">
                  x{item.quantity}
                </Text>
              </Box>
              <Text fontWeight="semibold">
                ${Number(item.unitPrice) * item.quantity}
              </Text>
            </HStack>
          ))}
          {items.length === 0 && (
            <Text color="gray.500">No hay productos en el carrito.</Text>
          )}
          <Divider />
          <HStack justify="space-between">
            <Text fontWeight="bold">Total</Text>
            <Text fontWeight="bold">${totalAmount}</Text>
          </HStack>
        </VStack>
      </CardBody>
    </Card>
  );
}
