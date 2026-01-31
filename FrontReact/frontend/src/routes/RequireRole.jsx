import { Navigate, Outlet, useLocation } from "react-router-dom";
//import { useSelector } from "react-redux";
import { USER_TYPE, getFromLocalStorage } from "../functions/localStorage"

export default function RequireRole({ allowed = [] }) {
  const location = useLocation();
  //const user = useSelector((state) => state.app.user);
  const userType = getFromLocalStorage(USER_TYPE);

  if (allowed.length > 0 && !allowed.includes(userType)) {
    return <Navigate to="/menu" state={{ from: location }} replace />;
  }

  return <Outlet />;
}
