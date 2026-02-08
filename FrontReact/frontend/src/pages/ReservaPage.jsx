import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Stack } from "@chakra-ui/react";
import {
  setDate,
  setShift,
  fetchAvailability,
  clearSuccess,
} from "../redux/features/tableReservationSlice";

import ReservationHeaderCard from "../components/reserva/ReservationHeaderCard";
import ReservationFiltersCard from "../components/reserva/ReservationFiltersCard";
import ReservationAvailability from "../components/reserva/ReservationAvailability";

export default function ReservaPage() {
  const dispatch = useDispatch();
  const tableReservationState = useSelector((state) => state.tableReservation);

  const {
    tables = [],
    selectedDate = "",
    selectedShiftId = null,
    loading = false,
    error,
    reservationSuccess,
  } = tableReservationState || {};

  const handleBuscar = () => {
    if (!selectedDate || !selectedShiftId) {
      return;
    }

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
      <ReservationHeaderCard />

      <ReservationFiltersCard
        selectedDate={selectedDate}
        selectedShiftId={selectedShiftId}
        loading={loading}
        error={error}
        reservationSuccess={reservationSuccess}
        onDateChange={(event) => dispatch(setDate(event.target.value))}
        onShiftChange={(event) => dispatch(setShift(Number(event.target.value)))}
        onSearch={handleBuscar}
      />

      <ReservationAvailability
        loading={loading}
        tables={tables}
        selectedDate={selectedDate}
        selectedShiftId={selectedShiftId}
      />
    </Stack>
  );
}
