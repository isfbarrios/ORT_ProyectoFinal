import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { USER_TYPE, getFromLocalStorage } from "../functions/localStorage";
import { Heading } from "@chakra-ui/react";
import PublicLayout from "../components/layout/PublicLayout";
import LoginForm from "../components/auth/LoginForm";
import { loginApi, saveAuth } from "../services/auth";
import { setUser, setLoading } from "../redux/features/appSlice";

export default function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isLoading = useSelector((state) => state.app.isLoading);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleLogin = async (event) => {
    event.preventDefault();
    setErrorMsg("");
    dispatch(setLoading(true));

    try {
      const userType = getFromLocalStorage(USER_TYPE);
      const data = await loginApi({ username, password, userType });

      saveAuth(data);
      dispatch(setUser(data.user ?? data));

      const nextPath = userType === "COCINA" ? "/kitchen" : "/menu";
      navigate(nextPath);
    } catch (error) {
      setErrorMsg(error.message || "Credenciales inválidas");
    } finally {
      dispatch(setLoading(false));
    }
  };

  return (
    <PublicLayout>
      <Heading size="lg" textAlign="center" mb={6}>
        Iniciar sesión
      </Heading>

      <LoginForm
        username={username}
        password={password}
        errorMsg={errorMsg}
        isLoading={isLoading}
        onUsernameChange={(event) => setUsername(event.target.value)}
        onPasswordChange={(event) => setPassword(event.target.value)}
        onSubmit={handleLogin}
      />
    </PublicLayout>
  );
}
