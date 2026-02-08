import { Stack, Typography } from "@mui/material";

export default function DashboardHeader() {
  return (
    <Stack direction="row" alignItems="center" justifyContent="center">
      <Typography variant="h5" fontWeight="bold">
        Panel de Pedidos
      </Typography>
    </Stack>
  );
}
