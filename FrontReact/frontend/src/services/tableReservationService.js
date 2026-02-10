import { API_URL, buildFetchHeader } from "../functions/localStorage";
import { clearAuth } from "./auth";

export async function getTableAvailability(date, shiftId) {
  const params = new URLSearchParams();
  if (date) params.append("date", date);
  if (shiftId !== undefined && shiftId !== null) params.append("shiftId", String(shiftId));

  // Cuando la BD funcione:
  const res = await fetch(`${API_URL}/table_reservation?${params.toString()}`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials: "include"
  });

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) throw new Error("Error al obtener disponibilidad");

  return res.json();
}

export async function reserveTable(payload) {
  // Cuando esté listo:
  const res = await fetch(`${API_URL}/table_reservation/reserve`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify(payload),
    credentials: "include"
  });

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Error al reservar");
  }

  return await res.json();
}
