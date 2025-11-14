const API_URL = "http://localhost:8080";

export async function fetchBoardFromApi() {

  const res = await fetch(`${API_URL}/api/cart`, {
    headers: { "Content-Type": "application/json" }

  });

  if (!res.ok) {
    throw new Error("Error obteniendo los pedidos");
  }

  const data = await res.json();
  console.log("Respuesta del backend:", data);

  return data;   //  devolvemos el array como viene del backend
}
