import { API_URL, buildFetchHeader } from "../functions/localStorage";

export async function fetchBoardFromApi() {
  const res = await fetch(`${API_URL}/orders`, {
    method: "GET",
    headers: buildFetchHeader()
  });

  if (!res.ok) {
    throw new Error("Error obteniendo los pedidos");
  }

  return await res.json();
}
