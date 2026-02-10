import { API_URL, buildFetchHeader } from "../functions/localStorage";


//GET items de la orden
export async function fetchOrderServiceFromApi(cartId) {

  const res = await fetch(`${API_URL}/cart_items/cart/${cartId}`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials: "include"
  });

  const data = await safeJsonStrict(res);

  if (!res.ok) {
    throw new Error(data?.message ?? "No se pudieron obtener los ítems de la orden");
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

  const data = await safeJsonLoose(res);

  if (!res.ok) {
    throw new Error(data?.message ?? "No se pudo actualizar el estado del pedido");
  }

  if (data?.state?.id != null && Number(data.state.id) !== Number(stateId)) {
    throw new Error("El servidor no confirmó el cambio de estado");
  }

  return data ?? true;
}

async function safeJsonStrict(res) {
  try {
    return await res.json();
  } catch {
    throw new Error("Respuesta inválida del servidor");
  }
}

async function safeJsonLoose(res) {
  try {
    return await res.json();
  } catch {
    return null;
  }
}