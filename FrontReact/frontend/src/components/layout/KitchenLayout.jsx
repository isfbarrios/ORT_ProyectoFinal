import { Outlet } from "react-router-dom";
import { AppBar, Box, CssBaseline, Toolbar, Typography } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import NavBar from "../navigation/NavBar";

export default function KitchenLayout() {
  return (
    <ThemeProvider theme={createTheme()}>
      <CssBaseline />
      <Box sx={{ minHeight: "100vh", bgcolor: "grey.100" }}>
        <AppBar position="sticky" elevation={1} sx={{ bgcolor: "#FFF3E0" }}>
          <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
            <Typography variant="h6" fontWeight="bold" color="#000">
              Cocina
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Panel operativo
            </Typography>
          </Toolbar>
        </AppBar>
        <Box sx={{ p: 2, minHeight: "calc(100vh - 64px)" }}>
          <NavBar />
          <Outlet />
        </Box>
      </Box>
    </ThemeProvider>
  );
}
