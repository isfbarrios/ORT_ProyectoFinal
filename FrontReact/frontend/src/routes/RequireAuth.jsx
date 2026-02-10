import { Navigate, Outlet, useLocation } from "react-router-dom";
import { getAuth } from "../services/auth";

export default function RequireAuth() {
  const auth = getAuth();
  const location = useLocation();

  // Si no hay auth, o isLogged !== true → fuera
  if (!auth?.isLogged) {
    return <Navigate to="/" state={{ from: location }} replace />;
  }

  return <Outlet />;
}
