import { Badge, Box, Heading, HStack, Stack, Text } from "@chakra-ui/react";

export default function ReservationHeaderCard() {
  return (
    <Box
      bg="white"
      borderWidth="1px"
      borderColor="orange.100"
      rounded="xl"
      p={{ base: 4, md: 6 }}
      boxShadow="sm"
    >
      <Stack spacing={2}>
        <Heading size="lg">Reservas</Heading>
        <Text color="gray.500">
          Elegí fecha y turno, luego seleccioná tu mesa.
        </Text>
        <HStack spacing={3} wrap="wrap">
          <Badge colorScheme="green">Disponible</Badge>
          <Badge colorScheme="red">Ocupada</Badge>
        </HStack>
      </Stack>
    </Box>
  );
}
