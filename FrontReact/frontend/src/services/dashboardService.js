import { API_URL, buildFetchHeader } from "../functions/localStorage";
import { clearAuth } from "./auth";

export async function fetchBoardFromApi() {
  const res = await fetch(`${API_URL}/orders`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials: "include"
  });

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) {
    throw new Error("Error obteniendo los pedidos");
  }

  return await res.json();
}
