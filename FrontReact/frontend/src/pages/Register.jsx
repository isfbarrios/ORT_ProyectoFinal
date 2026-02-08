import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Heading } from "@chakra-ui/react";
import PublicLayout from "../components/layout/PublicLayout";
import RegisterForm from "../components/auth/RegisterForm";
import { setLoading } from "../redux/features/appSlice";

export default function Register() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isLoading = useSelector((state) => state.app.isLoading);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleRegister = async (event) => {
    event.preventDefault();
    setErrorMsg("");
    dispatch(setLoading(true));

    try {
      navigate("/dashboard");
    } catch (error) {
      setErrorMsg(error.message || "Error al registrar usuario");
    } finally {
      dispatch(setLoading(false));
    }
  };

  return (
    <PublicLayout>
      <Heading size="lg" textAlign="center" mb={6}>
        Crear cuenta
      </Heading>

      <RegisterForm
        name={name}
        email={email}
        password={password}
        errorMsg={errorMsg}
        isLoading={isLoading}
        onNameChange={(event) => setName(event.target.value)}
        onEmailChange={(event) => setEmail(event.target.value)}
        onPasswordChange={(event) => setPassword(event.target.value)}
        onSubmit={handleRegister}
      />
    </PublicLayout>
  );
}
