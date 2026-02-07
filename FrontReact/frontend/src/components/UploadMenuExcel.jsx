// src/components/UploadMenuExcel.jsx
import { useRef, useState } from "react";
import {
  Box,
  Button,
  Heading,
  Input,
  Stack,
  Text,
} from "@chakra-ui/react";
import { updateMenuByFile } from "../services/menuService";

function UploadMenuExcel({ onImported }) {
  const fileInputRef = useRef(null);
  const [loading, setLoading] = useState(false);
  const [mensaje, setMensaje] = useState(null);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje(null);
    setError(null);

    const file = fileInputRef.current?.files?.[0];
    if (!file) {
      setError("Seleccioná un archivo Excel.");
      return;
    }

    try {
      setLoading(true);
      const formData = new FormData();
      formData.append("file", file);

      const respuesta = await updateMenuByFile(formData);
      setMensaje(
        typeof respuesta === "string" ? respuesta : "Menú importado correctamente."
      );

      // si el padre pasa un callback para refrescar la lista, lo ejecutamos
      if (typeof onImported === "function") {
        onImported();
      }
    } catch (err) {
      setError(err.message || "Error al importar el menú.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      borderWidth="1px"
      borderColor="orange.100"
      bg="white"
      rounded="lg"
      p={4}
      boxShadow="sm"
      maxW="520px"
      mx="auto"
      textAlign="center"
    >
      <Stack spacing={3} align="center">
        <Box>
          <Heading size="sm" mb={1}>
            Cargar menú desde Excel
          </Heading>
          <Text color="gray.500" fontSize="sm">
            Formato esperado: columnas Nombre, Descripción, Precio y Tipo.
          </Text>
        </Box>

        <form onSubmit={handleSubmit}>
          <Stack spacing={3} align="center">
            <Input
              type="file"
              ref={fileInputRef}
              accept=".xlsx"
              variant="filled"
              bg="orange.50"
              focusBorderColor="orange.400"
              maxW="420px"
            />

            <Button
              type="submit"
              colorScheme="orange"
              alignSelf="center"
              isLoading={loading}
              loadingText="Importando..."
              mt={2}
            >
              Importar menú
            </Button>
          </Stack>
        </form>

        {mensaje && (
          <Text color="green.600" fontSize="sm">
            {mensaje}
          </Text>
        )}

        {error && (
          <Text color="red.500" fontSize="sm">
            {error}
          </Text>
        )}
      </Stack>
    </Box>
  );
}

export default UploadMenuExcel;
