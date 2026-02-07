import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  Alert,
  AlertIcon,
  Box,
  Heading,
  IconButton,
  Stack,
  Table,
  Tbody,
  Td,
  Text,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { CloseIcon } from "@chakra-ui/icons";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

import { clearAuth, getAuth } from "../services/auth";
import UserDirectionForm from "../components/UserDirectionForm";
import { saveUserDirectionAsync } from "../redux/features/userDirectionSlice";
import {
  deleteUserDirection,
  getUserDirections,
} from "../services/userDirectionService";

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
  const [directions, setDirections] = useState([]);
  const [loadingDirections, setLoadingDirections] = useState(false);
  const [errorDirections, setErrorDirections] = useState(null);

  // Validación de autenticación 
  useEffect(() => {
    if (!auth?.isLogged) {
      clearAuth();
      navigate("/", { replace: true });
    }
  }, [auth, navigate]);

  const loadDirections = async () => {
    try {
      setLoadingDirections(true);
      setErrorDirections(null);
      const data = await getUserDirections();
      setDirections(data.directions || []);
    } catch (err) {
      setErrorDirections(err.message || "Error al obtener direcciones");
    } finally {
      setLoadingDirections(false);
    }
  };

  useEffect(() => {
    loadDirections();
  }, []);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // para mas adelate paymentMethod
    const { paymentMethod, ...directionData } = formData;

    const action = await dispatch(saveUserDirectionAsync(directionData));
    if (!action.error) {
      await loadDirections();
      setFormData({
        streetName: "",
        doorNumber: "",
        phone: "",
        comments: "",
        paymentMethod: "",
      });
      Swal.fire({
        icon: "success",
        title: "Dirección guardada",
        text: "Se guardó correctamente.",
        customClass: {
          popup: "swal-brand-popup",
          confirmButton: "swal-brand-button",
        },
        buttonsStyling: false,
        confirmButtonText: "OK",
      });
    }
  };

  const handleDelete = async (id) => {
    try {
      setErrorDirections(null);
      const result = await Swal.fire({
        title: "¿Eliminar dirección?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
        customClass: {
          popup: "swal-brand-popup",
          confirmButton: "swal-brand-button",
        },
        buttonsStyling: false,
      });

      if (!result.isConfirmed) {
        return;
      }

      await deleteUserDirection(id);
      setDirections((prev) => prev.filter((d) => d.id !== id));
      Swal.fire({
        icon: "success",
        title: "Dirección eliminada",
        text: "Se eliminó correctamente.",
        customClass: {
          popup: "swal-brand-popup",
          confirmButton: "swal-brand-button",
        },
        buttonsStyling: false,
        confirmButtonText: "OK",
      });
    } catch (err) {
      setErrorDirections(err.message || "Error al eliminar dirección");
    }
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

      <Box
        bg="white"
        borderWidth="1px"
        borderColor="orange.100"
        rounded="xl"
        p={{ base: 4, md: 6 }}
        boxShadow="sm"
      >
        <Heading size="md" mb={4}>
          Mis direcciones
        </Heading>

        {loadingDirections && (
          <Text color="gray.500" mb={3}>
            Cargando direcciones...
          </Text>
        )}

        {errorDirections && (
          <Alert status="error" borderRadius="md" mb={4}>
            <AlertIcon />
            {errorDirections}
          </Alert>
        )}

        {!loadingDirections && directions.length === 0 && (
          <Text color="gray.500">No tenés direcciones guardadas.</Text>
        )}

        {directions.length > 0 && (
          <Table size="sm">
            <Thead>
              <Tr>
                <Th>Calle</Th>
                <Th>Puerta</Th>
                <Th>Teléfono</Th>
                <Th>Comentarios</Th>
                <Th textAlign="right"></Th>
              </Tr>
            </Thead>
            <Tbody>
              {directions.map((d) => (
                <Tr key={d.id}>
                  <Td>{d.streetName}</Td>
                  <Td>{d.doorNumber}</Td>
                  <Td>{d.phone}</Td>
                  <Td>{d.comments}</Td>
                  <Td textAlign="right">
                    <IconButton
                      aria-label="Eliminar dirección"
                      icon={<CloseIcon />}
                      size="sm"
                      variant="ghost"
                      colorScheme="red"
                      onClick={() => handleDelete(d.id)}
                    />
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        )}
      </Box>
    </Stack>
  );
}