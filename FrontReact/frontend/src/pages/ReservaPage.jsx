import { useDispatch, useSelector } from "react-redux";
import { setDate, setShift, fetchAvailability } from "../redux/features/tableReservationSlice";
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
    const { tables = [], selectedDate = "", selectedShiftId = null, loading = false } = tableReservationState || {};

    const handleBuscar = () => {
        if (!selectedDate || !selectedShiftId) return;
        dispatch(fetchAvailability(selectedDate, selectedShiftId));
    };

    return (
        <div className="container mt-4">

            <h2>Reservas</h2>

            {/* Selector de fecha */}
            <label className="form-label">Fecha:</label>
            <input
                type="date"
                className="form-control"
                onChange={(e) => dispatch(setDate(e.target.value))}
            />

            {/* Selector de turno */}
            <label className="form-label mt-3">Turno:</label>
            <select
                className="form-select"
                onChange={(e) => dispatch(setShift(Number(e.target.value)))}
            >
                <option value="">Seleccione un turno</option>
                {shifts.map((s) => (
                    <option key={s.id} value={s.id}>{s.label}</option>
                ))}
            </select>

            <button className="btn btn-primary mt-3" onClick={handleBuscar}>
                Buscar mesas
            </button>

            <hr />

            {loading && <p>Cargando disponibilidad...</p>}

            {!loading && tables.length > 0 && (
                <MesasGrid mesas={tables} turnoSeleccionado={selectedShiftId} />
            )}

        </div>
    );
};

export default ReservaPage;