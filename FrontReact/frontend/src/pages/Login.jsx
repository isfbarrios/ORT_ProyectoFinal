import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";

import { loginApi, saveAuth } from "../services/auth";
import { setUser, setLoading } from "../redux/features/appSlice";

export default function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const isLoading = useSelector((state) => state.app.isLoading);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    dispatch(setLoading(true));

    try {
      // 1) Llamo al backend
      // Leemos si viene userType en la URL (ej: ?userType=DELIVERY)
      // Si no viene, asumimos LOCAL
      const userType = searchParams.get("userType") || "LOCAL";

      const data = await loginApi({ email, password, userType });

      saveAuth(data);
      dispatch(setUser(data.user ?? data));

      navigate("/dashboard");
    }
    catch (error) {
      console.error("Error de login:", error);
      setErrorMsg(error.message || "Credenciales inválidas");
    }
    finally {
      dispatch(setLoading(false));
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center mt-5">
      <div className="card shadow p-4" style={{ width: "350px" }}>
        <h3 className="text-center mb-4">Iniciar Sesión</h3>
        <span>fbarrios@hotmail.com</span>
        <span>pass1234</span>
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label">Correo</label>
            <input
              type="email"
              className="form-control"
              placeholder="ej: usuario@mail.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={isLoading}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Contraseña</label>
            <input
              type="password"
              className="form-control"
              placeholder="●●●●●●●●"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              disabled={isLoading}
              required
            />
          </div>

          {errorMsg && (
            <div className="alert alert-danger py-2">{errorMsg}</div>
          )}

          <button
            type="submit"
            className="btn btn-primary w-100"
            disabled={isLoading}
          >
            {isLoading ? "Ingresando..." : "Ingresar"}
          </button>
        </form>
      </div>
    </div>
  );
}
