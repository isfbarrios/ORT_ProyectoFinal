import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Select,
  Stack,
  Text,
  Alert,
  AlertIcon,
  Badge,
} from "@chakra-ui/react";
import {
  setDate,
  setShift,
  fetchAvailability,
  clearSuccess,
} from "../redux/features/tableReservationSlice";
import MesasGrid from "../components/MesaGrid";

const shifts = [
    { id: 1, label: "19:00 - 20:00" },
    { id: 2, label: "20:00 - 21:00" },
    { id: 3, label: "21:00 - 22:00" },
    { id: 4, label: "22:00 - 23:00" },
    { id: 5, label: "23:00 - 24:00" },
];

const ReservaPage = () => {

    const dispatch = useDispatch();
    const tableReservationState = useSelector((state) => state.tableReservation);

    // Validación de seguridad
    const {
        tables = [],
        selectedDate = "",
        selectedShiftId = null,
        loading = false,
        error,
        reservationSuccess,
    } = tableReservationState || {};

    const handleBuscar = () => {
        if (!selectedDate || !selectedShiftId) return;
        dispatch(fetchAvailability(selectedDate, selectedShiftId));
    };

    useEffect(() => {
        if (!reservationSuccess) return;
        const timer = setTimeout(() => {
            dispatch(clearSuccess());
        }, 10000);
        return () => clearTimeout(timer);
    }, [dispatch, reservationSuccess]);

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
                <Stack spacing={2}>
                    <Heading size="lg">Reservas</Heading>
                    <Text color="gray.500">
                        Elegí fecha y turno, luego seleccioná tu mesa.
                    </Text>
                    <HStack spacing={3} wrap="wrap">
                        <Badge colorScheme="green">Disponible</Badge>
                        <Badge colorScheme="red">Ocupada</Badge>
                    </HStack>
                </Stack>
            </Box>

            <Box borderWidth="1px" borderColor="orange.100" rounded="xl" p={{ base: 4, md: 6 }}>
                <Stack spacing={4}>
                    <Stack direction={{ base: "column", md: "row" }} spacing={4}>
                        <FormControl>
                            <FormLabel>Fecha</FormLabel>
                            <Input
                                type="date"
                                value={selectedDate || ""}
                                onChange={(e) => dispatch(setDate(e.target.value))}
                            />
                        </FormControl>

                        <FormControl>
                            <FormLabel>Turno</FormLabel>
                            <Select
                                placeholder="Seleccione un turno"
                                value={selectedShiftId || ""}
                                onChange={(e) => dispatch(setShift(Number(e.target.value)))}
                            >
                                {shifts.map((s) => (
                                    <option key={s.id} value={s.id}>
                                        {s.label}
                                    </option>
                                ))}
                            </Select>
                        </FormControl>
                    </Stack>

                    <Button
                        colorScheme="orange"
                        alignSelf="flex-start"
                        onClick={handleBuscar}
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

            <Box>
                {loading && <Text color="gray.500">Cargando disponibilidad...</Text>}

                {!loading && tables.length > 0 && (
                    <MesasGrid mesas={tables} turnoSeleccionado={selectedShiftId} />
                )}

                {!loading && tables.length === 0 && selectedDate && selectedShiftId && (
                    <Text color="gray.500">No hay mesas disponibles para ese turno.</Text>
                )}
            </Box>
        </Stack>
    );
};

export default ReservaPage;
