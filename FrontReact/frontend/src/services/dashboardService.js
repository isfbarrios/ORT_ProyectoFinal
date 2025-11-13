const API_URL = "http://localhost:8080"; 

export async function fetchBoardFromApi() {
  const res = await fetch(`${API_URL}/api/cart`, {
    headers: {
      "Content-Type": "application/json",
      // Authorization: `Bearer ${token}`, // por ahora no
    },
  });

  if (!res.ok) {
    throw new Error("No se pudo obtener el tablero");
  }

  const data = await res.json();

  console.log("Respuesta del backend:", data);

  return data;
}
