import { NavLink } from "react-router-dom";

const linkStyle = ({ isActive }) => ({
  textDecoration: "none",
  padding: "8px 12px",
  borderRadius: "8px",
  fontWeight: "600",
  backgroundColor: isActive ? "#dce8ff" : "transparent",
});

export default function NavBar() {
  return (
    <nav style={{ display: "flex", gap: 12, marginBottom: 16 }}>
      <NavLink to="/" style={linkStyle}>Inicio</NavLink>
      <NavLink to="/login" style={linkStyle}>Login</NavLink>
      <NavLink to="/register" style={linkStyle}>Registro</NavLink>
      <NavLink to="/dashboard" style={linkStyle}>Dashboard</NavLink>
      <NavLink to="/menu" style={linkStyle}>Menu</NavLink>
    </nav>
  );
}
