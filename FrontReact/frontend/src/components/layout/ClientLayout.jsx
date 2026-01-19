import { Box, Container } from "@chakra-ui/react";

export default function ClientLayout({ children }) {
  return (
    <Box bg="gray.50" minH="100vh">
      <Container maxW="md" p={4}>
        {children}
      </Container>
    </Box>
  );
}
