import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAuth, getAuth } from "../services/auth";
import { useNavigate } from "react-router-dom";

import Board from "../components/Board";

import {
  setBoard,
  setLoading,
  setError,
} from "../redux/features/dashboardSlice";

import { fetchBoardFromApi } from "../services/dashboardService";

export default function Dashboard() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = getAuth();
  const { isLoading, error } = useSelector((state) => state.dashboard);

  // Validación de autenticación
  useEffect(() => {

    //if (!auth || !auth.user) {
      clearAuth();
      navigate("/login", { replace: true });
    //}

  }, [auth, navigate]);

  // Cargar tablero desde backend
  useEffect(() => {
    async function cargarTablero() {
      try {
        dispatch(setLoading(true));

        const data = await fetchBoardFromApi(); // cuando habilitemos el token va en los parametros
        dispatch(setBoard(data));

      } catch (err) {
        dispatch(setError(err.message));

      } finally {
        dispatch(setLoading(false));
      }
    }

    cargarTablero();

  }, [dispatch]);

  return (
  <div className="container-fluid py-4" style={{ minHeight: "100vh" }}>
    <h2 className="mb-4">Panel de Pedidos</h2>

    {isLoading && <p>Cargando tablero...</p>}
    {error && <p className="text-danger">{error}</p>}

 
    <div
      className="p-3"
      style={{
        background: "#ffffff",
        borderRadius: "10px",
        border: "1px solid #ddd",
        minHeight: "75vh",
        overflowX: "auto",
      }}
    >
      <Board />
    </div>
  </div>
);
}
