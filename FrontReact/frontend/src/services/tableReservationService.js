import tableAvailabilityMock from "../mocks/tableAvailabilityMock";
import { API_URL, buildFetchHeader } from "../functions/localStorage";

export async function getTableAvailability() {
  // Cuando la BD funcione:
  const res = await fetch(`${API_URL}/table_reservation`, {
    method: "GET",
    headers: buildFetchHeader(),
  });

  if (!res.ok) throw new Error("Error al obtener disponibilidad");

  return res.json();
}

export async function reserveTable(payload) {
  console.log("ReserveTable payload:", payload);
  // Cuando esté listo:
  const res = await fetch(`${API_URL}/table_reservation/reserve`, {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({ tableId: 2, shiftId: 2, date: '2025-12-13', customerName: 'Fabri' }),
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Error al reservar");
  }

  return await res.json();
}
