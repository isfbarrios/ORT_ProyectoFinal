import { Outlet } from "react-router-dom"; // muestro el cotenido de la ruta hija ej: log,home
import NavBar from "./components/NavBar"; 
import CartModal from "./components/CartModal"; 
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchCartAsync } from "./redux/features/cartSlice";

export default function App() {

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCartAsync());
  }, [dispatch]);

  return (
    <main style={{ padding: "1.5rem", maxWidth: 960, margin: "0 auto" }}>
      <NavBar />
      <CartModal />
      <Outlet />
    </main>
  );
}
