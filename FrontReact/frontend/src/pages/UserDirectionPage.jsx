import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { clearAuth, getAuth } from "../services/auth";
import UserDirectionForm from "../components/UserDirectionForm";
import { saveUserDirectionAsync } from "../redux/features/userDirectionSlice";

export default function UserDirectionPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = getAuth();
  const { loading, error } = useSelector(
    (state) => state.userDirection
  );

  const [formData, setFormData] = useState({
    streetName: "",
    doorNumber: "",
    phone: "",
    comments: "",
    paymentMethod: "", // para mas adelamte
  });

  // Validación de autenticación 
  useEffect(() => {
    if (!auth?.isLogged) {
      clearAuth();
      navigate("/login", { replace: true });
    }
  }, [auth, navigate]);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // para mas adelate paymentMethod
    const { paymentMethod, ...directionData } = formData;

    dispatch(saveUserDirectionAsync(directionData));
  };

  return (
    <div className="container-fluid py-4" style={{ minHeight: "100vh" }}>
      <h2 className="mb-4">Dirección de entrega</h2>

      {loading && <p>Guardando dirección...</p>}
      {error && <p className="text-danger">{error}</p>}

      <div
        className="p-3"
        style={{
          background: "#ffffff",
          borderRadius: "10px",
          border: "1px solid #ddd",
          minHeight: "60vh",
        }}
      >
        <UserDirectionForm
          formData={formData}
          onChange={handleChange}
          onSubmit={handleSubmit}
          loading={loading}
        />
      </div>
    </div>
  );
}
