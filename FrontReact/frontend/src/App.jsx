import { Outlet, useLocation } from "react-router-dom";
import NavBar from "./components/NavBar";
import CartModal from "./components/CartModal";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchCartAsync } from "./redux/features/cartSlice";
import KitchenLayout from "./components/layout/KitchenLayout";

export default function App() {
  const dispatch = useDispatch();
  const location = useLocation();

  const isPublicRoute =
    location.pathname === "/" ||
    location.pathname === "/login" ||
    location.pathname === "/register";
  const isKitchenRoute = location.pathname.startsWith("/kitchen");

  useEffect(() => {
    // Solo cargamos carrito si NO estamos en rutas públicas
    if (!isPublicRoute && !isKitchenRoute) {
      dispatch(fetchCartAsync());
    }
  }, [dispatch, isPublicRoute, isKitchenRoute]);

  // RUTAS PÚBLICAS (sin layout global)
  if (isPublicRoute) {
    return <Outlet />;
  }

  if (isKitchenRoute) {
    return <KitchenLayout />;
  }

  // RUTAS PRIVADAS (layout completo)
  return (
    <main style={{ padding: "1.5rem", maxWidth: 960, margin: "0 auto" }}>
      <NavBar />
      <CartModal />
      <Outlet />
    </main>
  );
}
