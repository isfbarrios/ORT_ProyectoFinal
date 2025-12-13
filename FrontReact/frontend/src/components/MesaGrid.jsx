import MesaCard from "./MesaCard";

const MesasGrid = ({ mesas, turnoSeleccionado }) => {
    return (
        <div className="d-flex flex-wrap gap-3 mt-4">
            {mesas.map((mesa) => (
                <MesaCard key={mesa.id} mesa={mesa} turno={turnoSeleccionado} />
            ))}
        </div>
    );
};

export default MesasGrid;
