import { clearAuth, getAuth } from "../services/auth";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();
  const auth = getAuth();

  const handleLogout = () => {
    clearAuth();
    navigate("/login", { replace: true });
  };

  return (
    <div className="container">
      <h2>Dashboard</h2>
      <p className="text-muted">Bienvenido {auth?.user?.email}</p>
      <button className="btn btn-outline-danger" onClick={handleLogout}>
        Cerrar sesión
      </button>
    </div>
  );
}
