import { Heading, Stack, Text } from "@chakra-ui/react";

export default function UpdateMenuHeader() {
  return (
    <Stack spacing={2} mb={6} align="center">
      <Heading size="lg">Actualizar menú por Excel</Heading>
      <Text color="gray.500">
        Cargá un archivo `.xlsx` para actualizar el menú del restaurante.
      </Text>
    </Stack>
  );
}
