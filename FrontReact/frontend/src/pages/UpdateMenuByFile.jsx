import { useState } from "react";
import { Box, Heading, Stack, Text } from "@chakra-ui/react";
import UploadMenuExcel from "../components/UploadMenuExcel";
import Menu from "./Menu";

function UpdateMenuByFile() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleImported = () => {
    setRefreshKey((prev) => prev + 1);
  };

  return (
    <Box maxW="960px" mx="auto" py={6} textAlign="center">
      <Stack spacing={2} mb={6} align="center">
        <Heading size="lg">Actualizar menú por Excel</Heading>
        <Text color="gray.500">
          Cargá un archivo `.xlsx` para actualizar el menú del restaurante.
        </Text>
      </Stack>

      <UploadMenuExcel onImported={handleImported} />

      <Box mt={8}>
        <Menu menuId={1} refreshKey={refreshKey} />
      </Box>
    </Box>
  );
}

export default UpdateMenuByFile;
