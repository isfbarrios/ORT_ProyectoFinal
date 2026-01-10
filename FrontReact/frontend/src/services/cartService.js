import {
  API_URL,
  buildFetchHeader,
  safeJson,
} from "../functions/localStorage";

// ===============================================
// GET /session_cart
// ===============================================
export async function apiGetCart() {
  const res = await fetch(`${API_URL}/session_cart`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials:"include"
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session_cart/items  (agregar item)
// ===============================================
export async function apiAddItemToCart(menuItemId, quantity = 1) {
  const res = await fetch(`${API_URL}/session_cart/items`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({ menuItemId, quantity }),
    credentials:"include"
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session_cart/confirm  (confirmar carrito)
// ===============================================
export async function apiConfirmCart() {
  const res = await fetch(`${API_URL}/session_cart/confirm`, {
    method: "POST",
    headers: buildFetchHeader(),
    credentials:"include"
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session_cart/close  (cerrar carrito)
// ===============================================
export async function apiCloseCart() {
  const res = await fetch(`${API_URL}/session_cart/close`, {
    method: "POST",
    headers: buildFetchHeader(),
    credentials:"include"
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}
