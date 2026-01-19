import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useSearchParams, Link } from "react-router-dom";
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
import { loginApi, saveAuth } from "../services/auth";
import { setUser, setLoading } from "../redux/features/appSlice";

export default function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const isLoading = useSelector((state) => state.app.isLoading);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    dispatch(setLoading(true));

    try {
      const userType = searchParams.get("userType") || "LOCAL";

      const data = await loginApi({ email, password, userType });

      saveAuth(data);
      dispatch(setUser(data.user ?? data));

      const resolvedType =
        (data?.user?.type || data?.user?.userType || data?.type || "")
          .toString()
          .toUpperCase();
      const nextPath = resolvedType === "COCINA" ? "/dashboard" : "/menu";
      navigate(nextPath);
    } catch (error) {
      console.error("Error de login:", error);
      setErrorMsg(error.message || "Credenciales inválidas");
    } finally {
      dispatch(setLoading(false));
    }
  };

  return (
    <PublicLayout>
      <Heading size="lg" textAlign="center" mb={6}>
        Iniciar sesión
      </Heading>

      <Text fontSize="sm" color="gray.500" textAlign="center" mb={4}>
        Accedé a tu cuenta para continuar
      </Text>

      <form onSubmit={handleLogin}>
        <VStack spacing={4}>
          <FormControl>
            <FormLabel>Correo</FormLabel>
            <Input
              type="email"
              placeholder="ej: usuario@mail.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={isLoading}
              required
            />
          </FormControl>

          <FormControl>
            <FormLabel>Contraseña</FormLabel>
            <Input
              type="password"
              placeholder="●●●●●●●●"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              disabled={isLoading}
              required
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
