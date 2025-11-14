const API_URL = "http://localhost:8080";

//Get items de la orden 
export async function fetchOrderServiceFromApi(cartId) {

  const res = await fetch(`${API_URL}/api/cart_items/cart/${cartId}`, {
    headers: { "Content-Type": "application/json" }
    
  });

  if (!res.ok) {
    throw new Error("No se pudieron obtener los ítems de la orden");
  }

   const data = await res.json();
   console.log("Items de la orden", cartId, data);
   return data; 
}

// POST (actualizo) estado del pedido
export async function updateOrderState(orderId, stateId) {

  const res = await fetch(`${API_URL}/api/orders/update_state`, {
    method: "POST",                 
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      "orderId" :orderId, 
      "orderStateId" :stateId }),
  });

  if (!res.ok) {
    throw new Error("No se pudo actualizar el estado del pedido");
  }

  return true;
}
