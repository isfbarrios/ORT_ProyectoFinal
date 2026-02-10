import { Box, Heading, Text } from "@chakra-ui/react";

export default function UserDirectionHeaderCard() {
  return (
    <Box
      bg="white"
      borderWidth="1px"
      borderColor="orange.100"
      rounded="xl"
      p={{ base: 4, md: 6 }}
      boxShadow="sm"
    >
      <Heading size="lg" mb={2}>
        Dirección de entrega
      </Heading>
      <Text color="gray.500">
        Guarda una dirección para agilizar tus pedidos.
      </Text>
    </Box>
  );
}
