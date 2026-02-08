import { Outlet, useLocation } from "react-router-dom";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import NavBar from "./components/navigation/NavBar";
import CartModal from "./components/cart/CartModal";
import KitchenLayout from "./components/layout/KitchenLayout";
import { fetchCartAsync } from "./redux/features/cartSlice";
import { refreshToken, clearAuth } from "./services/auth";

export default function App() {
  const dispatch = useDispatch();
  const location = useLocation();

  const isPublicRoute =
    location.pathname === "/" ||
    location.pathname === "/login" ||
    location.pathname === "/register";

  const isKitchenRoute = location.pathname.startsWith("/kitchen");

  useEffect(() => {
    const refreshSession = async () => {
      try {
        await refreshToken();
      } catch (error) {
        console.error("Error refrescando sesión:", error);
        clearAuth();
      }
    };

    refreshSession();

    if (!isPublicRoute && !isKitchenRoute) {
      dispatch(fetchCartAsync());
    }
  }, [dispatch, isPublicRoute, isKitchenRoute]);

  if (isPublicRoute) {
    return <Outlet />;
  }

  if (isKitchenRoute) {
    return <KitchenLayout />;
  }

  return (
    <main className="app-shell">
      <NavBar />
      <CartModal />
      <Outlet />
    </main>
  );
}
