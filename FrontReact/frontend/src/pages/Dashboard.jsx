import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAuth, getAuth } from "../services/auth";
import { useNavigate } from "react-router-dom";
import { Box, Stack } from "@mui/material";
import Board from "../components/dashboard/Board";
import DashboardHeader from "../components/dashboard/DashboardHeader";
import DashboardFeedback from "../components/dashboard/DashboardFeedback";
import { setBoard, setLoading, setError } from "../redux/features/dashboardSlice";
import { fetchBoardFromApi } from "../services/dashboardService";

export default function Dashboard() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = getAuth();
  const { isLoading, error } = useSelector((state) => state.dashboard);

  useEffect(() => {
    if (!auth?.isLogged) {
      clearAuth();
      navigate("/", { replace: true });
    }
  }, [auth, navigate]);

  useEffect(() => {
    const cargarTablero = async () => {
      try {
        dispatch(setLoading(true));
        const data = await fetchBoardFromApi();
        dispatch(setBoard(data));
      } catch (err) {
        dispatch(setError(err.message));
      } finally {
        dispatch(setLoading(false));
      }
    };

    cargarTablero();
  }, [dispatch]);

  return (
    <Box sx={{ minHeight: "100%", display: "flex", flexDirection: "column", gap: 2 }}>
      <DashboardHeader />

      <DashboardFeedback isLoading={isLoading} error={error} />

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
