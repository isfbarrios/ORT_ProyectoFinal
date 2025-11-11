import { clearAuth, getAuth } from "../services/auth";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";

export default function Dashboard() {
  const navigate = useNavigate();
  const auth = getAuth();

  //Si no tiene permisos, lo llevo al login o home
  if (!auth && !auth.user) {
    clearAuth();
    navigate("/login", { replace: true });  
  }

  const [columns, setColumns] = useState({
    pendiente: [
        { id: 1, title: "Pedido #1" },
        { id: 2, title: "Pedido #2" }
    ],
    proceso: [],
    entregado: []
  });

  const onDragStart = (e, item, fromColumn) => {
    e.dataTransfer.setData("itemId", item.id);
    e.dataTransfer.setData("fromColumn", fromColumn);
  };

  const onDrop = (e, toColumn) => {
    const itemId = parseInt(e.dataTransfer.getData("itemId"));
    const fromColumn = e.dataTransfer.getData("fromColumn");

    // Si el drop es dentro de la misma columna → no hacemos nada
    if (fromColumn === toColumn) return;

    const item = columns[fromColumn].find(i => i.id === itemId);

    // Si no existe
    if (!item) return;

    // Quitarlo de la columna original
    const updatedFrom = columns[fromColumn].filter(i => i.id !== itemId);

    // Agregarlo a la columna destino
    const updatedTo = [...columns[toColumn], item];

    setColumns({
      ...columns,
      [fromColumn]: updatedFrom,
      [toColumn]: updatedTo
    });
  };

  const allowDrop = (e) => {
    e.preventDefault();
  };

  const columnStyle = {
    minHeight: "300px",
    background: "#f8f9fa",
    padding: "10px",
    borderRadius: "8px",
    border: "1px solid #dee2e6"
  };

  /*
  return (
    <div className="container">
      <h2>Dashboard</h2>
      <p className="text-muted">Bienvenido {auth?.user?.email}</p>
      <button className="btn btn-outline-danger" onClick={handleLogout}>
        Cerrar sesión
      </button>
    </div>
  );
  */
  return (
    <div className="container py-4">
        <h2 className="mb-4">Panel de Pedidos (Cocina)</h2>
        <div className="row">
            {/* Pendiente */}
            <div className="col-md-4">
                <h4>Pendiente</h4>
                <div
                    style={columnStyle}
                    onDrop={e => onDrop(e, "pendiente")}
                    onDragOver={allowDrop}
                >
                    {columns.pendiente.map(item => (
                        <div
                            key={item.id}
                            className="card mb-3 p-2"
                            draggable
                            onDragStart={e => onDragStart(e, item, "pendiente")}
                        >
                            {item.title}
                        </div>
                    ))}
                </div>
            </div>

            {/* En proceso */}
            <div className="col-md-4">
                <h4>En proceso</h4>
                <div
                    style={columnStyle}
                    onDrop={e => onDrop(e, "proceso")}
                    onDragOver={allowDrop}
                >
                    {columns.proceso.map(item => (
                        <div
                            key={item.id}
                            className="card mb-3 p-2"
                            draggable
                            onDragStart={e => onDragStart(e, item, "proceso")}
                        >
                            {item.title}
                        </div>
                    ))}
                </div>
            </div>

            {/* Entregado */}
            <div className="col-md-4">
                <h4>Entregado</h4>
                <div
                    style={columnStyle}
                    onDrop={e => onDrop(e, "entregado")}
                    onDragOver={allowDrop}
                >
                    {columns.entregado.map(item => (
                        <div
                            key={item.id}
                            className="card mb-3 p-2"
                            draggable
                            onDragStart={e => onDragStart(e, item, "entregado")}
                        >
                            {item.title}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    </div>
  );
}
