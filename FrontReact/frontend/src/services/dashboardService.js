import { API_URL, buildFetchHeader } from "../functions/localStorage";

export async function fetchBoardFromApi() {
  const res = await fetch(`${API_URL}/cart`, {
    method: "GET",
    headers: buildFetchHeader(false) // no requiere token
  });

  if (!res.ok) {
    throw new Error("Error obteniendo los pedidos");
  }

  return await res.json();
}
