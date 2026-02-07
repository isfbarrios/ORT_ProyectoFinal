import { Button, VStack, Heading, Text, Box } from "@chakra-ui/react";
import { useSearchParams, Link } from "react-router-dom";
import { USER_TYPE, saveToLocalStorage, TABLE_ID } from "../functions/localStorage";


export default function Home() {

  const [searchParams] = useSearchParams();

  try {
    const userType = searchParams.get("userType")?.toString().toUpperCase() || "LOCAL";

    const tableId = searchParams.get("tableId") || "-1";

    saveToLocalStorage(USER_TYPE, userType);
    saveToLocalStorage(TABLE_ID, tableId);
    
  }
  catch (error) {
    console.error("Error rendering Home page:", error);
  }

  return (
    <Box
      minH="100vh"
      backgroundImage="url('/plato-fuerte-bg.jpg')"
      backgroundSize="cover"
      backgroundPosition="center"
      position="relative"
    >
      {/* Overlay oscuro */}
      <Box
        position="absolute"
        top={0}
        left={0}
        right={0}
        bottom={0}
        bg="blackAlpha.700"
        backdropFilter="blur(2px)"
      />

      {/* Contenido */}
      <Box
        position="relative"
        zIndex={1}
        minH="100vh"
        display="flex"
        alignItems="center"
        justifyContent="center"
        px={6}
      >
        <Box maxW="sm" w="100%" textAlign="center">
          <Heading color="white" mb={4}>
            Plato Fuerte
          </Heading>

          <Text color="gray.200" mb={8}>
            Ingresá o creá una cuenta para continuar
          </Text>

          <VStack spacing={4}>
            <Button
              as={Link}
              to="/login"
              colorScheme="orange"
              size="lg"
              width="100%"
            >
              Iniciar sesión
            </Button>

            <Button
              as={Link}
              to="/register"
              variant="outline"
              color="white"
              borderColor="white"
              _hover={{ bg: "whiteAlpha.200" }}
              size="lg"
              width="100%"
            >
              Registrarse
            </Button>
          </VStack>
        </Box>
      </Box>
    </Box>
  );
}
