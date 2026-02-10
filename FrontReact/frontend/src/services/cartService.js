import {
  API_URL,
  buildFetchHeader,
  getFromLocalStorage,
  safeJson,
  TABLE_ID
} from "../functions/localStorage";
import { clearAuth } from "./auth";

// ===============================================
// GET /session_cart
// ===============================================
export async function apiGetCart() {
  const tableId = getFromLocalStorage(TABLE_ID);
  const res = await fetch(`${API_URL}/session_cart/table/${tableId}`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials: "include"
  });

  const data = await safeJson(res);

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session_cart/items  (agregar item)
// ===============================================
export async function apiAddItemToCart(menuItemId, quantity = 1) {
  const tableId = getFromLocalStorage(TABLE_ID);
  const res = await fetch(`${API_URL}/session_cart/items`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({ menuItemId, quantity, tableId }),
    credentials: "include"
  });

  const data = await safeJson(res);

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session_cart/confirm  (confirmar carrito)
// ===============================================
export async function apiConfirmCart() {
  console.log("apiConfirmCart called");
  const res = await fetch(`${API_URL}/session_cart/confirm`, {
    method: "POST",
    headers: buildFetchHeader(),
    credentials: "include"
  });

  const data = await safeJson(res);

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /bill/create  (cerrar carrito)
// ===============================================
export async function apiCloseCart(payload) {
  const res = await fetch(`${API_URL}/bill/create`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify(payload),
    credentials: "include"
  });

  try {
    const data = await res.json(res);

    if (res.status === 401) {
      clearAuth();
      window.location.href = "/";
      throw new Error("Unauthorized");
    }

    if (!res.ok) throw new Error(data.message);

    return data;
  }
  catch (error) {
    console.error("Error al parsear JSON:", error);
    throw new Error("Respuesta inválida del servidor");
  }
}


export async function apiProcessBill(payload) {
  console.log(payload);
  const res = await fetch(`${API_URL}/bill/process`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify(payload),
    credentials: "include"
  });

  try {
    const data = await res.json(res);

    if (res.status === 401) {
      clearAuth();
      window.location.href = "/";
      throw new Error("Unauthorized");
    }

    if (!res.ok) throw new Error(data.message);

    return data;
  }
  catch (error) {
    console.error("Error al parsear JSON:", error);
    throw new Error("Respuesta inválida del servidor");
  }
}