import { API_URL, buildFetchHeader } from "../functions/localStorage";
import { clearAuth } from "./auth";

//GET items de la orden
export async function fetchOrderServiceFromApi(cartId) {

  const res = await fetch(`${API_URL}/cart_items/cart/${cartId}`, {
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

  if (!res.ok) {
    throw new Error(data.message || "No se pudieron obtener los ítems de la orden");
  }

  console.log("fetchOrderServiceFromApi - data:", data);

  return data;
}

// POST actualizar estado
export async function updateOrderState(orderId, stateId) {

  const res = await fetch(`${API_URL}/orders/update_state`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({
      orderId: orderId,
      orderStateId: stateId
    }),
    credentials: "include"
  });

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) {
    throw new Error("No se pudo actualizar el estado del pedido");
  }

  return true;
}

async function safeJson(res) {
  try {
    return await res.json();
  } catch {
    throw new Error("Respuesta inválida del servidor");
  }
}

