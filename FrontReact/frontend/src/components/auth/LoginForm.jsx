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

export default function LoginForm({
  username,
  password,
  errorMsg,
  isLoading,
  onUsernameChange,
  onPasswordChange,
  onSubmit,
}) {
  return (
    <>
      <Text fontSize="sm" color="gray.500" textAlign="center" mb={4}>
        Accedé a tu cuenta para continuar
      </Text>

      <form onSubmit={onSubmit}>
        <VStack spacing={4}>
          <FormControl>
            <FormLabel color="orange.600">Usuario</FormLabel>
            <Input
              type="text"
              placeholder="jperez"
              value={username}
              onChange={onUsernameChange}
              disabled={isLoading}
              required
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            />
          </FormControl>

          <FormControl>
            <FormLabel color="orange.600">Contraseña</FormLabel>
            <Input
              type="password"
              placeholder="●●●●●●●●"
              value={password}
              onChange={onPasswordChange}
              disabled={isLoading}
              required
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
            loadingText="Ingresando..."
          >
            Ingresar
          </Button>

          <Text fontSize="sm" textAlign="center" color="gray.500" mt={4}>
            ¿No tenés cuenta?{" "}
            <Text
              as={Link}
              to="/register"
              color="orange.400"
              fontWeight="semibold"
              display="inline"
            >
              Registrate
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
