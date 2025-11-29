import {
  API_URL
} from "../functions/localStorage"


//Get items de la orden 
export async function fetchOrderServiceFromApi(cartId) {

  const res = await fetch(`${API_URL}/api/cart_items/cart/${cartId}`, {
    headers: { "Content-Type": "application/json" }

  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "No se pudieron obtener los ítems de la orden");
  }

  return data;
}

// POST (actualizo) estado del pedido
export async function updateOrderState(orderId, stateId) {

  const res = await fetch(`${API_URL}/api/orders/update_state`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      "orderId": orderId,
      "orderStateId": stateId
    }),
  });

  if (!res.ok) {
    throw new Error("No se pudo actualizar el estado del pedido");
  }

  return true;
}
