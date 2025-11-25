import { NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  openCartModal,
  fetchCartAsync,
} from "../redux/features/cartSlice";

const linkStyle = ({ isActive }) => ({
  textDecoration: "none",
  padding: "8px 12px",
  borderRadius: "8px",
  fontWeight: "600",
  backgroundColor: isActive ? "#dce8ff" : "transparent",
});

export default function NavBar() {
  const dispatch = useDispatch();
  const itemsCount = useSelector((state) => state.cart.items.length);
  const totalAmount = useSelector((state) => state.cart.totalAmount);

  const handleOpenCart = () => {
    dispatch(fetchCartAsync());
    dispatch(openCartModal());
  };

  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        gap: 12,
        marginBottom: 16,
      }}
    >
      {/* Links a páginas */}
      <div style={{ display: "flex", gap: 12 }}>
        <NavLink to="/" style={linkStyle}>
          Inicio
        </NavLink>
        <NavLink to="/login" style={linkStyle}>
          Login
        </NavLink>
        <NavLink to="/register" style={linkStyle}>
          Registro
        </NavLink>
        <NavLink to="/dashboard" style={linkStyle}>
          Dashboard
        </NavLink>
        <NavLink to="/menu" style={linkStyle}>
          Menu
        </NavLink>
      </div>

      {/* Botón de carrito, alineado a la derecha */}
      <button
        type="button"
        onClick={handleOpenCart}
        style={{
          padding: "8px 12px",
          borderRadius: "8px",
          border: "1px solid #1f2933",
          backgroundColor: "#1f2933",
          color: "#fff",
          fontWeight: 600,
          cursor: "pointer",
        }}
      >
        Carrito ({itemsCount}){totalAmount > 0 ? ` - $${totalAmount}` : ""}
      </button>
    </nav>
  );
}
