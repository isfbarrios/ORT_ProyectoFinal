import {
  Alert,
  AlertIcon,
  Box,
  Button,
  FormControl,
  FormLabel,
  Input,
  Select,
  Stack,
} from "@chakra-ui/react";

const shifts = [
  { id: 1, label: "19:00 - 20:00" },
  { id: 2, label: "20:00 - 21:00" },
  { id: 3, label: "21:00 - 22:00" },
  { id: 4, label: "22:00 - 23:00" },
  { id: 5, label: "23:00 - 24:00" },
];

export default function ReservationFiltersCard({
  selectedDate,
  selectedShiftId,
  loading,
  error,
  reservationSuccess,
  onDateChange,
  onShiftChange,
  onSearch,
}) {
  return (
    <Box borderWidth="1px" borderColor="orange.100" rounded="xl" p={{ base: 4, md: 6 }}>
      <Stack spacing={4}>
        <Stack direction={{ base: "column", md: "row" }} spacing={4}>
          <FormControl>
            <FormLabel>Fecha</FormLabel>
            <Input
              type="date"
              value={selectedDate || ""}
              onChange={onDateChange}
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            />
          </FormControl>

          <FormControl>
            <FormLabel>Turno</FormLabel>
            <Select
              placeholder="Seleccione un turno"
              value={selectedShiftId || ""}
              onChange={onShiftChange}
              bg="orange.50"
              borderColor="orange.200"
              focusBorderColor="orange.400"
              _hover={{ borderColor: "orange.300" }}
            >
              {shifts.map((shift) => (
                <option key={shift.id} value={shift.id}>
                  {shift.label}
                </option>
              ))}
            </Select>
          </FormControl>
        </Stack>

        <Button
          colorScheme="orange"
          alignSelf="flex-start"
          onClick={onSearch}
          isDisabled={!selectedDate || !selectedShiftId}
          isLoading={loading}
          loadingText="Buscando..."
        >
          Buscar mesas
        </Button>

        {error && (
          <Alert status="error" borderRadius="md">
            <AlertIcon />
            {error}
          </Alert>
        )}

        {reservationSuccess && (
          <Alert status="success" borderRadius="md">
            <AlertIcon />
            Reserva confirmada.
          </Alert>
        )}
      </Stack>
    </Box>
  );
}
