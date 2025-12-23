// src/pages/MenuPage.jsx
import { useEffect, useState } from "react";
import { getMenuItemsByMenu } from "../services/menuService";
import UploadMenuExcel from "../components/UploadMenuExcel";

function MenuPage({ menuId = 1 }) {
  const [items, setItems] = useState([]);
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState(null);

  const cargarItems = async () => {
    try {
      setCargando(true);
      setError(null);
      const data = await getMenuItemsByMenu(menuId);
      setItems(data);
    } catch (err) {
      setError(err.message || "Error al cargar el menú.");
    } finally {
      setCargando(false);
    }
  };

  useEffect(() => {
    cargarItems();
  }, [menuId]);

  return (
    <div className="container mt-4">
      <h3>Carta (Menú {menuId})</h3>

      {cargando && <p>Cargando ítems...</p>}
      {error && <p className="text-danger">{error}</p>}

      {!cargando && !error && items.length === 0 && (
        <p>No hay ítems cargados para este menú.</p>
      )}

      {!cargando && items.length > 0 && (
        <ul className="list-group mb-3">
          {items.map((item) => (
            <li key={item.id} className="list-group-item d-flex justify-content-between">
              <span>
                <strong>{item.name}</strong>
                {item.description && (
                  <span className="text-muted"> — {item.description}</span>
                )}
              </span>
              <span>${item.basePrice}</span>
            </li>
          ))}
        </ul>
      )}

      {/* Componente de importación de Excel */}
      <UploadMenuExcel onImported={cargarItems} />
    </div>
  );
}

export default MenuPage;
