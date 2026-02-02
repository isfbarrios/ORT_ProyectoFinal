import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  Alert,
  AlertIcon,
  Box,
  Heading,
  Stack,
  Text,
} from "@chakra-ui/react";

import { clearAuth, getAuth } from "../services/auth";
import UserDirectionForm from "../components/UserDirectionForm";
import { saveUserDirectionAsync } from "../redux/features/userDirectionSlice";

export default function UserDirectionPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = getAuth();
  const { loading, error } = useSelector(
    (state) => state.userDirection
  );

  const [formData, setFormData] = useState({
    streetName: "",
    doorNumber: "",
    phone: "",
    comments: "",
    paymentMethod: "", // para mas adelamte
  });

  // Validación de autenticación 
  useEffect(() => {
    if (!auth?.isLogged) {
      clearAuth();
      navigate("/", { replace: true });
    }
  }, [auth, navigate]);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // para mas adelate paymentMethod
    const { paymentMethod, ...directionData } = formData;

    dispatch(saveUserDirectionAsync(directionData));
  };

  return (
    <Stack spacing={6}>
      <Box
        bg="white"
        borderWidth="1px"
        borderColor="orange.100"
        rounded="xl"
        p={{ base: 4, md: 6 }}
        boxShadow="sm"
      >
        <Heading size="lg" mb={2}>
          Dirección de entrega
        </Heading>
        <Text color="gray.500">
          Guarda una dirección para agilizar tus pedidos.
        </Text>
      </Box>

      <Box
        bg="white"
        borderWidth="1px"
        borderColor="orange.100"
        rounded="xl"
        p={{ base: 4, md: 6 }}
        boxShadow="sm"
      >
        {loading && (
          <Text color="gray.500" mb={3}>
            Guardando dirección...
          </Text>
        )}
        {error && (
          <Alert status="error" borderRadius="md" mb={4}>
            <AlertIcon />
            {error}
          </Alert>
        )}

        <UserDirectionForm
          formData={formData}
          onChange={handleChange}
          onSubmit={handleSubmit}
          loading={loading}
        />
      </Box>
    </Stack>
  );
}
