import {
  Alert,
  AlertIcon,
  Button,
  FormControl,
  FormLabel,
  Input,
  Text,
  VStack,
} from "@chakra-ui/react";
import { Link } from "react-router-dom";

export default function RegisterForm({
  name,
  email,
  password,
  errorMsg,
  isLoading,
  onNameChange,
  onEmailChange,
  onPasswordChange,
  onSubmit,
}) {
  return (
    <>
      <Text fontSize="sm" color="gray.500" textAlign="center" mb={4}>
        Registrate para comenzar
      </Text>

      <form onSubmit={onSubmit}>
        <VStack spacing={4}>
          <FormControl isRequired>
            <FormLabel color="orange.600">Nombre</FormLabel>
            <Input
              placeholder="Nombre completo"
              value={name}
              onChange={onNameChange}
              disabled={isLoading}
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel color="orange.600">Email</FormLabel>
            <Input
              type="email"
              placeholder="ej: usuario@mail.com"
              value={email}
              onChange={onEmailChange}
              disabled={isLoading}
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel color="orange.600">Contraseña</FormLabel>
            <Input
              type="password"
              placeholder="●●●●●●●●"
              value={password}
              onChange={onPasswordChange}
              disabled={isLoading}
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            />
          </FormControl>

          {errorMsg && (
            <Alert status="error" borderRadius="md">
              <AlertIcon />
              {errorMsg}
            </Alert>
          )}

          <Button
            type="submit"
            colorScheme="orange"
            size="lg"
            width="100%"
            isLoading={isLoading}
            loadingText="Registrando..."
          >
            Registrarse
          </Button>

          <Text fontSize="sm" textAlign="center" color="gray.500" mt={4}>
            ¿Ya tenés cuenta?{" "}
            <Text
              as={Link}
              to="/login"
              color="orange.400"
              fontWeight="semibold"
              display="inline"
            >
              Iniciar sesión
            </Text>
          </Text>

          <Text fontSize="sm" textAlign="center" mt={2}>
            <Text as={Link} to="/" color="gray.400" display="inline">
              Volver al inicio
            </Text>
          </Text>
        </VStack>
      </form>
    </>
  );
}
