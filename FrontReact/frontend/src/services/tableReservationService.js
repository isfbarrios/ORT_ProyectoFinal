import { API_URL, buildFetchHeader } from "../functions/localStorage";

export async function getTableAvailability() {
  // Cuando la BD funcione:
  const res = await fetch(`${API_URL}/table_reservation`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials:"include"
  });

  if (!res.ok) throw new Error("Error al obtener disponibilidad");

  return res.json();
}

export async function reserveTable(payload) {
  // Cuando esté listo:
  const res = await fetch(`${API_URL}/table_reservation/reserve`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify(payload),
    credentials:"include"
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Error al reservar");
  }

  return await res.json();
}
