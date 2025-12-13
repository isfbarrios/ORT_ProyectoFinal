import { useState } from "react";
import ModalReserva from "./ModalReserva";

const MesaCard = ({ mesa, turno }) => {

    const [show, setShow] = useState(false);

    const shift = mesa.shifts.find(s => s.id.shiftId === turno);
    const estado = shift?.state?.id;

    const color = estado === 1 ? "green" : "red"; // 1 = libre

    return (
        <>
            <div
                className="p-3 border rounded text-center"
                style={{ width: "140px", cursor: "pointer", backgroundColor: color }}
                onClick={() => estado === 1 && setShow(true)}
            >
                <h5>{mesa.name}</h5>
                <p>{mesa.chairsAmount} sillas</p>
            </div>

            {show && (
                <ModalReserva mesa={mesa} turno={turno} onClose={() => setShow(false)} />
            )}
        </>
    );
};

export default MesaCard;
