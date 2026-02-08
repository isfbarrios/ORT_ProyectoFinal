import { Typography } from "@mui/material";

export default function DashboardFeedback({ isLoading, error }) {
  return (
    <>
      {isLoading && <Typography>Cargando tablero...</Typography>}
      {error && <Typography color="error.main">{error}</Typography>}
    </>
  );
}
