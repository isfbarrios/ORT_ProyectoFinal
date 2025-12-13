import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { submitReservation } from "../redux/features/tableReservationSlice";

const ModalReserva = ({ mesa, turno, onClose }) => {

    const dispatch = useDispatch();
    const { selectedDate } = useSelector((state) => state.tableReservation);

    const [nombre, setNombre] = useState("");

    const handleReservar = () => {
        dispatch(
            submitReservation({
                tableId: mesa.id,
                shiftId: turno,
                date: selectedDate,
                customerName: nombre
            })
        );
        onClose();
    };

    return (
        <div className="modal-backdrop" style={{
            position: "fixed", top: 0, left: 0, right: 0, bottom: 0,
            backgroundColor: "rgba(0,0,0,0.5)", display: "flex",
            justifyContent: "center", alignItems: "center"
        }}>
            <div className="bg-white p-4 rounded shadow" style={{ width: "300px" }}>

                <h4>Reservar {mesa.name}</h4>
                <p>Turno: {turno}</p>

                <label>Nombre:</label>
                <input
                    type="text"
                    className="form-control"
                    onChange={(e) => setNombre(e.target.value)}
                />

                <button className="btn btn-success mt-3 w-100" onClick={handleReservar}>
                    Confirmar
                </button>

                <button className="btn btn-secondary mt-2 w-100" onClick={onClose}>
                    Cancelar
                </button>

            </div>
        </div>
    );
};

export default ModalReserva;
