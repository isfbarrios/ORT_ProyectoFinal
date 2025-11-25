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

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener el carrito");
  }

  return {
    data: await res.json(),
    headers: res.headers,
  };
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

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al agregar ítem al carrito");
  }

  return {
    data: await res.json(),
    headers: res.headers,
  };
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

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "No se pudo confirmar el carrito");
  }

  return {
    data: await res.json(), // OrderDTO
    headers: res.headers,
  };
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

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "No se pudo cerrar el carrito");
  }

  return {
    ok: true,
    headers: res.headers,
  };
}
