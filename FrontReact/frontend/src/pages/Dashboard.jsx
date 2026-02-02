import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAuth, getAuth } from "../services/auth";
import { useNavigate } from "react-router-dom";
import { Box, Stack, Typography } from "@mui/material";

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
  // Validación de autenticación
  useEffect(() => {

    if (!auth?.isLogged) {
      clearAuth();
      navigate("/", { replace: true });

    }
  }, [auth, navigate]);


  // Cargar tablero desde backend
  useEffect(() => {

    async function cargarTablero() {
      try {
        dispatch(setLoading(true));

        const data = await fetchBoardFromApi(); //  devuelve SOLO array de pedidos

        console.log(data);

        dispatch(setBoard(data));              // le paso el array cdirecto

      } catch (err) {
        dispatch(setError(err.message));
      } finally {
        dispatch(setLoading(false));
      }
    }

    cargarTablero();
  }, [dispatch])

  return (
    <Box sx={{ minHeight: "100%", display: "flex", flexDirection: "column", gap: 2 }}>
      <Stack direction="row" alignItems="center" justifyContent="space-between">
        <Typography variant="h5" fontWeight="bold">
          Panel de Pedidos
        </Typography>
      </Stack>

      {isLoading && <Typography>Cargando tablero...</Typography>}
      {error && (
        <Typography color="error.main">
          {error}
        </Typography>
      )}

      <Box
        sx={{
          p: 2,
          bgcolor: "common.white",
          borderRadius: 2,
          border: "1px solid",
          borderColor: "divider",
          minHeight: "75vh",
          overflowX: "auto",
        }}
      >
        <Board />
      </Box>
    </Box>
  );
}
