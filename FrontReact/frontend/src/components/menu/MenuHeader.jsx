import React from "react";
import {
  Box,
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
    <Box className="surface-card" p={{ base: 4, md: 5 }} mb={6}>
      <VStack spacing={2} textAlign="center">
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
            _hover={{ bg: "orange.100" }}
            _focus={{ bg: "orange.100" }}
            borderRadius="full"
          />
        </InputGroup>
      </VStack>
    </Box>
  );
}
