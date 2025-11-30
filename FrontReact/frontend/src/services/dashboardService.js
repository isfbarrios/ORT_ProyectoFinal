import {
  API_URL
} from "../functions/localStorage"

export async function fetchBoardFromApi() {

  const res = await fetch(`${API_URL}/cart`, {
    headers: { "Content-Type": "application/json" }

  });

  if (!res.ok) {
    throw new Error("Error obteniendo los pedidos");
  }

  const data = await res.json();

  return data;   //  devolvemos el array como viene del backend
}
