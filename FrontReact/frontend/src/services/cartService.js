// src/services/cartService.js
const API_URL = "http://localhost:8080/api";

export async function apiGetCart({ sessionId }) {
  const res = await fetch(`${API_URL}/cart`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      ...(sessionId ? { "X-Session-Id": sessionId } : {}), // -*
    },
  });

  if (!res.ok) {
    // no interpretamos el error acá, solo lo lanzamos
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener el carrito");
  }

  return {
    data: await res.json(),
    headers: res.headers, // por si arriba queremos leer X-Session-Id -*
  };
}

export async function apiAddItemToCart({ sessionId, menuItemId, quantity }) {
  const res = await fetch(`${API_URL}/cart/items`, {
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
