import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";

export default function RequireRole({ allowed = [] }) {
  const location = useLocation();
  const user = useSelector((state) => state.app.user);
  const userType = (user?.userType || user?.type || user?.role || "")
    .toString()
    .toUpperCase();

  if (allowed.length > 0 && !allowed.includes(userType)) {
    return <Navigate to="/menu" state={{ from: location }} replace />;
  }

  return <Outlet />;
}
