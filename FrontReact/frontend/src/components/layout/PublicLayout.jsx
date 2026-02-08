import { Box, Container, Text } from "@chakra-ui/react";

export default function PublicLayout({ children }) {
  return (
    <Box
      minH="100vh"
      bg="transparent"
      display="flex"
      alignItems="center"
      justifyContent="center"
      px={4}
    >
      <Container maxW="sm" p={0}>
        <Box className="surface-card" p={{ base: 5, md: 6 }}>
          <Text
            textAlign="center"
            fontSize="2xl"
            fontWeight="bold"
            mb={6}
          >
            Plato Fuerte
          </Text>

          {children}
        </Box>
      </Container>
    </Box>
  );
}
