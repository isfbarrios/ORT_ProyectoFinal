const API_URL = "http://localhost:8080/api";

// ------------------------------------------------------
// HELPER PARA EL SESSION ID
// ------------------------------------------------------

const SESSION_KEY = "restaurant-session-id";

export function getSessionId() {
  return localStorage.getItem(SESSION_KEY) || null;
}

export function saveSessionIdFromHeaders(headers) {
  const sessionId = headers.get("X-Session-Id");
  if (sessionId) localStorage.setItem(SESSION_KEY, sessionId);
}

export function clearSessionId() {
  localStorage.removeItem(SESSION_KEY);
}



// ------------------------------------------------------
// 1) GET /api/session-cart
// ------------------------------------------------------

export async function apiGetCart({ sessionId }) {
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

export async function apiAddItemToCart({ sessionId, menuItemId, quantity }) {
  const res = await fetch(`${API_URL}/session-cart/items`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
    body: JSON.stringify({ menuItemId, quantity }),
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

export async function apiConfirmCart({ sessionId }) {
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

export async function apiCloseCart({ sessionId }) {
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
