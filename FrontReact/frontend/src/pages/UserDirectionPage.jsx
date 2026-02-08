import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Alert, AlertIcon, Box, Stack, Text } from "@chakra-ui/react";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { clearAuth, getAuth } from "../services/auth";
import UserDirectionForm from "../components/user-direction/UserDirectionForm";
import UserDirectionHeaderCard from "../components/user-direction/UserDirectionHeaderCard";
import UserDirectionsList from "../components/user-direction/UserDirectionsList";
import { saveUserDirectionAsync } from "../redux/features/userDirectionSlice";
import { deleteUserDirection, getUserDirections } from "../services/userDirectionService";

export default function UserDirectionPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = getAuth();
  const { loading, error } = useSelector((state) => state.userDirection);

  const [formData, setFormData] = useState({
    streetName: "",
    doorNumber: "",
    phone: "",
    comments: "",
    paymentMethod: "",
  });

  const [directions, setDirections] = useState([]);
  const [loadingDirections, setLoadingDirections] = useState(false);
  const [errorDirections, setErrorDirections] = useState(null);

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

  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const directionData = { ...formData };
    const result = await dispatch(saveUserDirectionAsync(directionData));

    if (!result?.error) {
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
      setDirections((prev) => prev.filter((direction) => direction.id !== id));

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
      <UserDirectionHeaderCard />

      <Box
        bg="white"
        borderWidth="1px"
        borderColor="orange.100"
        rounded="xl"
        p={{ base: 4, md: 6 }}
        boxShadow="sm"
      >
        {loading && <Text color="gray.500" mb={3}>Guardando dirección...</Text>}

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

      {loadingDirections && <Text color="gray.500">Cargando direcciones...</Text>}

      {errorDirections && (
        <Alert status="error" borderRadius="md">
          <AlertIcon />
          {errorDirections}
        </Alert>
      )}

      {!loadingDirections && !errorDirections && (
        <UserDirectionsList directions={directions} onDelete={handleDelete} />
      )}
    </Stack>
  );
}
