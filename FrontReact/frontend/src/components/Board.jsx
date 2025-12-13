// src/components/dashboard/Board.jsx
import { useSelector } from "react-redux";
import Column from "./Column";

export default function Board() {
  const { columns } = useSelector((state) => state.dashboard);

  if (!columns || columns.length === 0)
    return <p>No hay columnas cargadas...</p>;

  return (
    <div
      className="d-flex gap-3 p-3 overflow-auto"
      style={{ minHeight: "80vh" }}
    >
      {columns.map((col) => (
        <Column key={col.id} column={col} />
      ))}
    </div>
  );
}
