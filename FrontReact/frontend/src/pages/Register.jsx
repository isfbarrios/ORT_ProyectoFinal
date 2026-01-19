import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import {
  Button,
  Input,
  FormControl,
  FormLabel,
  VStack,
  Alert,
  AlertIcon,
  Heading,
  Text,
} from "@chakra-ui/react";

import PublicLayout from "../components/layout/PublicLayout";
// import { registerApi, saveAuth } from "../services/auth";
import { setUser, setLoading } from "../redux/features/appSlice";

export default function Register() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isLoading = useSelector((state) => state.app.isLoading);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    dispatch(setLoading(true));

    try {
      // TODO: activar cuando esté el backend
      // const data = await registerApi({ name, email, password });
      // saveAuth(data);
      // dispatch(setUser(data.user ?? data));

      navigate("/dashboard");
    } catch (error) {
      console.error("Error de registro:", error);
      setErrorMsg(error.message || "Error al registrar usuario");
    } finally {
      dispatch(setLoading(false));
    }
  };

  return (
    <PublicLayout>
      <Heading size="lg" textAlign="center" mb={6}>
        Crear cuenta
      </Heading>

      <Text fontSize="sm" color="gray.500" textAlign="center" mb={4}>
        Registrate para comenzar
      </Text>

      <form onSubmit={handleRegister}>
        <VStack spacing={4}>
          <FormControl isRequired>
            <FormLabel>Nombre</FormLabel>
            <Input
              placeholder="Nombre completo"
              value={name}
              onChange={(e) => setName(e.target.value)}
              disabled={isLoading}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Email</FormLabel>
            <Input
              type="email"
              placeholder="ej: usuario@mail.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={isLoading}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Contraseña</FormLabel>
            <Input
              type="password"
              placeholder="●●●●●●●●"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              disabled={isLoading}
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

          {/* Navegación cruzada */}
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
            <Text
              as={Link}
              to="/"
              color="gray.400"
              display="inline"
            >
              Volver al inicio
            </Text>
          </Text>
        </VStack>
      </form>
    </PublicLayout>
  );
}
