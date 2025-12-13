import tableAvailabilityMock from "../mocks/tableAvailabilityMock";
import { API_URL, buildFetchHeader } from "../functions/localStorage";

export async function getTableAvailability(date, shiftId) {
  // uso el archvio mock por ahora
    return tableAvailabilityMock;

    // Cuando la BD funcione:
    /*
    const res = await fetch(`${API_URL}/tables/availability?date=${date}&shiftId=${shiftId}`, {
      method: "GET",
      headers: buildFetchHeader(),
    });
  
    if (!res.ok) throw new Error("Error al obtener disponibilidad");
  
    return res.json();
    */
}

export async function reserveTable(payload) {
    // Por ahora simulo éxito
    return { ok: true, message: "Reserva simulada (mock)" };

    // Cuando esté listo:
    /*
    const res = await fetch(`${API_URL}/table-reservations`, {
      method: "POST",
      headers: buildFetchHeader(),
      body: JSON.stringify(payload),
    });
  
    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || "Error al reservar");
    }
  
    return await res.json();
    */
}
