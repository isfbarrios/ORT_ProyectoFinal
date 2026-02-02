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
    credentials: "include"
  });

  const data = await safeJson(res);
  console.log("apiGetCart data:", data);
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
    credentials: "include"
  });

  const data = await safeJson(res);
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
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /bill/create  (cerrar carrito)
// ===============================================
export async function apiCloseCart(cartId, paymentMethodId) {
  const res = await fetch(`${API_URL}/bill/create`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({
      cartId: cartId,
      paymentMethodId: paymentMethodId
    }),
    credentials: "include"
  });

  try {
    const data = await res.json(res);

    if (!res.ok) throw new Error(data.message);

    return data;
  }
  catch (error) {
    console.error("Error al parsear JSON:", error);
    throw new Error("Respuesta inválida del servidor");
  }
}
