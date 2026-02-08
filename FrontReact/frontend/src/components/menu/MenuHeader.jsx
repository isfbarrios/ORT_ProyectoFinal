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
    <Box
      className="surface-card"
      p={{ base: 5, md: 6 }}
      mb={6}
      borderWidth="1px"
      borderColor="orange.100"
      rounded="2xl"
      boxShadow="sm"
      bgGradient="linear(to-r, orange.50, yellow.50)"
    >
      <VStack spacing={3} textAlign="center">
        <Heading size="lg" color="orange.700">
          Menú del Restaurante
        </Heading>
        <Text color="gray.600">
          Elegí tus platos y confirmá el pedido cuando estés listo.
        </Text>
        <InputGroup maxW="440px">
          <InputLeftElement pointerEvents="none">
            <SearchIcon color="orange.400" />
          </InputLeftElement>
          <Input
            placeholder="Buscar platos..."
            value={searchQuery}
            onChange={(e) => onSearchChange(e.target.value)}
            variant="filled"
            size="md"
            bg="white"
            focusBorderColor="orange.400"
            _hover={{ bg: "orange.50" }}
            _focus={{ bg: "orange.50" }}
            borderRadius="full"
          />
        </InputGroup>
      </VStack>
    </Box>
  );
}
