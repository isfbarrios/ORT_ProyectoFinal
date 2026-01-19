import { Box, Container, Text } from "@chakra-ui/react";

export default function PublicLayout({ children }) {
  return (
    <Box
      minH="100vh"
      bg="gray.50"
      display="flex"
      alignItems="center"
      justifyContent="center"
    >
      <Container maxW="sm" p={6}>
        <Text
          textAlign="center"
          fontSize="2xl"
          fontWeight="bold"
          mb={6}
        >
          Plato Fuerte
        </Text>

        {children}
      </Container>
    </Box>
  );
}
