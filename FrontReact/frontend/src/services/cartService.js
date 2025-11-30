import {
  API_URL,
  SESSION_KEY,
  getFromLocalStorage
} from "../functions/localStorage"


// ------------------------------------------------------
// 1) GET /api/session-cart
// ------------------------------------------------------

export async function apiGetCart(sessionId) {
  const res = await fetch(`${API_URL}/session-cart`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "Error al obtener el carrito");
  }

  return data;
}

// ------------------------------------------------------
// 2) POST /api/session-cart/items
// ------------------------------------------------------

export async function apiAddItemToCart(menuItemId, quantity) {
  let sessionId = getFromLocalStorage(SESSION_KEY);
  const res = await fetch(`${API_URL}/session-cart/items`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
    body: JSON.stringify({
      menuItemId: menuItemId,
      quantity: quantity
    }),
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "No pudimos agregar tu pedido");
  }

  return data;
}

// ------------------------------------------------------
// 3) POST /api/session-cart/confirm
// ------------------------------------------------------

export async function apiConfirmCart(sessionId) {
  const res = await fetch(`${API_URL}/session-cart/confirm`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "No se pudo confirmar el carrito");
  }

  return data;
  
}

// ------------------------------------------------------
// 4) POST /api/session-cart/close
// ------------------------------------------------------

export async function apiCloseCart(sessionId) {
  const res = await fetch(`${API_URL}/session-cart/close`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "No se pudo cerrar el carrito");
  }

  return data;
}
