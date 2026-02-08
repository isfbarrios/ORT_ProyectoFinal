import { Box, Text } from "@chakra-ui/react";
import MesasGrid from "./MesaGrid";

export default function ReservationAvailability({
  loading,
  tables,
  selectedDate,
  selectedShiftId,
}) {
  return (
    <Box>
      {loading && <Text color="gray.500">Cargando disponibilidad...</Text>}

      {!loading && tables.length > 0 && (
        <MesasGrid mesas={tables} turnoSeleccionado={selectedShiftId} />
      )}

      {!loading && tables.length === 0 && selectedDate && selectedShiftId && (
        <Text color="gray.500">No hay mesas disponibles para ese turno.</Text>
      )}
    </Box>
  );
}
