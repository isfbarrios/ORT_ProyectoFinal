import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { useDispatch, useSelector } from "react-redux";

import { getMenuItemsByMenu } from "../services/menuService";
import {
  addItemToCartAsync,
  fetchCartAsync,
} from "../redux/features/cartSlice";

export default function Menu({ menuId = 1 }) {
  const dispatch = useDispatch();
  const cart = useSelector((state) => state.cart);

  const [items, setItems] = useState([]);
  const [loadingMenu, setLoadingMenu] = useState(true);
  const [errorMenu, setErrorMenu] = useState(null);

  // Cargar items del menú
  useEffect(() => {
    const loadMenu = async () => {
      setLoadingMenu(true);
      setErrorMenu(null);

      try {
        const data = await getMenuItemsByMenu(menuId);
        setItems(data);
      } catch (err) {
        console.error("Error cargando menú:", err);
        setErrorMenu(err.message || "Error al cargar el menú");
      } finally {
        setLoadingMenu(false);
      }
    };

    loadMenu();
  }, [menuId]);

  // Cargar carrito (si existe sesión)
  useEffect(() => {
    dispatch(fetchCartAsync());
  }, [dispatch]);

  const handleAddToCart = (menuItemId) => {
    dispatch(addItemToCartAsync(menuItemId, 1));
  };

  if (loadingMenu || cart.loading) {
    return (
      <div className="container mt-4 text-center">
        <div className="spinner-border" role="status"></div>
      </div>
    );
  }

  if (errorMenu) {
    return (
      <div className="container mt-4">
        <div className="alert alert-danger text-center">{errorMenu}</div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-center">Menú del Restaurante</h2>

      {/* Cards */}
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
                      {item.basePrice !== undefined && item.basePrice !== null
                        ? `$${item.basePrice.toFixed(2)}`
                        : "—"}
                    </p>

                    <button
                      className="btn btn-primary w-100"
                      onClick={() => handleAddToCart(item.id)}
                    >
                      Añadir al pedido
                    </button>
                  </div>
                </div>
              </div>
            </motion.div>
          </div>
        ))}

        {items.length === 0 && (
          <div className="col-12">
            <p className="text-center">
              No hay ítems cargados para este menú.
            </p>
          </div>
        )}
      </div>

      {/* Tabla debajo, como vista más administrativa */}
      <div className="table-responsive mt-4">
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
                  {item.basePrice !== undefined && item.basePrice !== null
                    ? `$${item.basePrice.toFixed(2)}`
                    : "—"}
                </td>
                <td>
                  <button
                    className="btn btn-primary w-100"
                    onClick={() => handleAddToCart(item.id)}
                  >
                    Añadir al pedido
                  </button>
                </td>
              </tr>
            ))}

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
