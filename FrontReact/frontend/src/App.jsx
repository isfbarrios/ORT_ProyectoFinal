import { Outlet } from "react-router-dom"; // muestro el cotenido de la ruta hija ej: log,home
import NavBar from "./components/NavBar"; 

export default function App() {
  return (
    <main style={{ padding: "1.5rem", maxWidth: 960, margin: "0 auto" }}>
      <NavBar />
      <CartModal />
      <Outlet />
    </main>
  );
}
