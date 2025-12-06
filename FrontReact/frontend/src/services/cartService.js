import {
  API_URL,
  SESSION_KEY,
  getFromLocalStorage,
  buildFetchHeader,
} from "../functions/localStorage";


// ===============================================
// Helper: sessionId
// ===============================================
function getSession() {
  return getFromLocalStorage(SESSION_KEY);
}

// ===============================================
// Helper: parse JSON seguro
// ===============================================
async function safeJson(res) {
  const text = await res.text();
  try {
    return JSON.parse(text);
  } catch {
    return { message: text || "Respuesta inválida del servidor" };
  }
}

// ===============================================
// GET /session-cart
// ===============================================
export async function apiGetCart() {
  const sessionId = getSession();

  const res = await fetch(`${API_URL}/session-cart`, {
    method: "GET",
    headers: {
      ...buildFetchHeader(false),
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session-cart/items  (agregar item)
// ===============================================
export async function apiAddItemToCart(menuItemId, quantity = 1) {
  const sessionId = getSession();

  const res = await fetch(`${API_URL}/session-cart/items`, {
    method: "POST",
    headers: {
      ...buildFetchHeader(false),
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
    body: JSON.stringify({ menuItemId, quantity }),
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session-cart/confirm  (confirmar carrito)
// ===============================================
export async function apiConfirmCart() {
  const sessionId = getSession();

  const res = await fetch(`${API_URL}/session-cart/confirm`, {
    method: "POST",
    headers: {
      ...buildFetchHeader(false),
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}

// ===============================================
// POST /session-cart/close  (cerrar carrito)
// ===============================================
export async function apiCloseCart() {
  const sessionId = getSession();

  const res = await fetch(`${API_URL}/session-cart/close`, {
    method: "POST",
    headers: {
      ...buildFetchHeader(false),
      ...(sessionId ? { "X-Session-Id": sessionId } : {}),
    },
  });

  const data = await safeJson(res);
  if (!res.ok) throw new Error(data.message);

  return data;
}
