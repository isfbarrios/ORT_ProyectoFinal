import React from "react";
import { Text } from "@chakra-ui/react";

export default function MenuEmptyState({ message }) {
  return (
    <Text textAlign="center" color="gray.500" mt={6}>
      {message}
    </Text>
  );
}
