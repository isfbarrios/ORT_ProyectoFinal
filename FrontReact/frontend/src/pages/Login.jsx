import { useState } from "react";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { loginApi, saveAuth } from "../services/auth";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/dashboard";

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMsg("");

    if (!email || !password) {
      setErrorMsg("Completá email y contraseña");
      return;
    }

    try {
      setLoading(true);
      const data = await loginApi({ email, password });
      saveAuth(data);
      navigate(from, { replace: true });
    } catch (err) {
      setErrorMsg(err.message || "Error al iniciar sesión");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{ maxWidth: 400 }}>
      <h2 className="mb-3">Login</h2>
      {errorMsg && <div className="alert alert-danger">{errorMsg}</div>}

      <form onSubmit={handleSubmit} className="vstack gap-3">
        <div>
          <label className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            placeholder="tu@mail.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div>
          <label className="form-label">Contraseña</label>
          <input
            type="password"
            className="form-control"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button className="btn btn-primary" type="submit" disabled={loading}>
          {loading ? "Ingresando..." : "Ingresar"}
        </button>
      </form>

      <p className="mt-3 text-muted">
        ¿No tenés cuenta? <Link to="/register">Registrate</Link>
      </p>
    </div>
  );
}
