import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { ChakraProvider } from "@chakra-ui/react";
import { Provider } from "react-redux";
import { store } from "./redux/store";

//  Bootstrap por ahora lo dejamos, luego borrar
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles/global.css";

import App from "./App.jsx";
import Home from "./pages/Home.jsx";
import Login from "./pages/Login.jsx";
import QR from "./pages/QR.jsx";
import Register from "./pages/Register.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Menu from "./pages/Menu.jsx";
import ReservaPage from "./pages/ReservaPage.jsx";
import RequireAuth from "./routes/RequireAuth.jsx";
import RequireRole from "./routes/RequireRole.jsx";
import UserDirectionPage from "./pages/UserDirectionPage.jsx";
import Checkout from "./pages/Checkout.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <Provider store={store}>
      <ChakraProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<App />}>
              <Route index element={<Home />} />
              <Route path="login" element={<Login />} />
              <Route path="register" element={<Register />} />
              <Route path="qr" element={<QR value="userType=DELIVERY" />} />
              <Route path="directions" element={<UserDirectionPage />} />
              <Route path="add_direction" element={<UserDirectionPage />} />

              {/* Rutas protegidas */}
              <Route element={<RequireAuth />}>
                <Route element={<RequireRole allowed={["COCINA"]} />}>
                  <Route path="kitchen" element={<Dashboard />} />
                  <Route path="dashboard" element={<Navigate to="/kitchen" replace />} />
                </Route>
                <Route path="menu" element={<Menu />} />
                <Route path="reserva" element={<ReservaPage />} />
                <Route path="checkout" element={<Checkout />} />
              </Route>
            </Route>
          </Routes>
        </BrowserRouter>
      </ChakraProvider>
    </Provider>
  </React.StrictMode>
);
