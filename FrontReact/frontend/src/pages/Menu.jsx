import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";

// Componente principal
export default function MenuItemsView({ menuId = 1 }) {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`http://localhost:8080/api/menu_item/menu/${menuId}`)
      .then((response) => response.json())
      .then((data) => {
        setItems(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error obteniendo menú:", err);
        setLoading(false);
      });
  }, [menuId]);

  if (loading) {
    return (
      <div className="container mt-4 text-center">
        <div className="spinner-border" role="status"></div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-center">Menú del Restaurante</h2>

      <div className="row g-3">
        {items.map((item) => (
          <div className="col-12 col-md-6 col-lg-4" key={item.id}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3 }}
            >
              <div className="card shadow-sm h-100">
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">{item.name}</h5>

                  {item.description && (
                    <p className="card-text text-muted">
                      {item.description}
                    </p>
                  )}

                  <div className="mt-auto">
                    <p className="fw-bold fs-5">
                      ${item.basePrice?.toFixed(2) ?? "—"}
                    </p>

                    <button className="btn btn-primary w-100">
                      Añadir al pedido
                    </button>
                  </div>
                </div>
              </div>
            </motion.div>
          </div>
        ))}
      </div>
      <div className="table-responsive">
        <table className="table table-striped table-bordered align-middle">
          <thead className="table-dark">
            <tr>
              <th style={{ width: "50px" }}>ID</th>
              <th>Nombre</th>
              <th>Descripción</th>
              <th style={{ width: "120px" }}>Precio</th>
              <th style={{ width: "150px" }}></th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td className="fw-semibold">{item.name}</td>
                <td>{item.description || "—"}</td>
                <td className="fw-bold">
                  {item.basePrice
                    ? `$${item.basePrice.toFixed(2)}`
                    : "—"}
                </td>
                <td>
                  <button className="btn btn-primary w-100">
                    Añadir al pedido
                  </button>
                </td>
              </tr>
            ))}

            {/* Si no hay items */}
            {items.length === 0 && (
              <tr>
                <td colSpan="5" className="text-center p-4">
                  No hay items cargados para este menú.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
