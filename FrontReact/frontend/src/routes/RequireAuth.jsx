import { Navigate, Outlet, useLocation } from "react-router-dom";
import { getAuth } from "../services/auth";

export default function RequireAuth() {
  const auth = getAuth();
  const location = useLocation();

  if (!auth) {
    // Si no hay sesión guardada, redirige al login
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Si está logueado, deja continuar
  return <Outlet />;
}
