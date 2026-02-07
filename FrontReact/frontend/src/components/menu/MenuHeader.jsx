import React from "react";
import {
  Heading,
  Input,
  InputGroup,
  InputLeftElement,
  Text,
  VStack,
} from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";

export default function MenuHeader({ searchQuery, onSearchChange }) {
  return (
    <VStack spacing={2} textAlign="center" mb={6}>
      <Heading size="lg">Menú del Restaurante</Heading>
      <Text color="gray.500">
        Elegí tus platos y confirmá el pedido cuando estés listo.
      </Text>
      <InputGroup maxW="420px">
        <InputLeftElement pointerEvents="none">
          <SearchIcon color="orange.400" />
        </InputLeftElement>
        <Input
          placeholder="Buscar platos..."
          value={searchQuery}
          onChange={(e) => onSearchChange(e.target.value)}
          variant="filled"
          size="md"
          bg="orange.50"
          focusBorderColor="orange.400"
          borderRadius="full"
        />
      </InputGroup>
    </VStack>
  );
}
