import { useState } from "react";
import { Box } from "@chakra-ui/react";
import UploadMenuExcel from "../components/update-menu/UploadMenuExcel";
import UpdateMenuHeader from "../components/update-menu/UpdateMenuHeader";
import Menu from "./Menu";

export default function UpdateMenuByFile() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleImported = () => {
    setRefreshKey((prev) => prev + 1);
  };

  return (
    <Box maxW="960px" mx="auto" py={6} textAlign="center">
      <UpdateMenuHeader />
      <UploadMenuExcel onImported={handleImported} />

      <Box mt={8}>
        <Menu menuId={1} refreshKey={refreshKey} />
      </Box>
    </Box>
  );
}
