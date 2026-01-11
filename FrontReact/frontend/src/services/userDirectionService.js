import {
  buildFetchHeader
} from "../functions/localStorage"

/*
  Crear o actualizar la dirección del usuario logueado
*/

export async function saveUserDirection(direction) {
  try {
    const response = await fetch("/api/user/direction", {
      method: "POST",
      headers: buildFetchHeader(),
      body: JSON.stringify(direction),
      credentials: 'include'
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || "Error al guardar la dirección");
    }

    return await response.json();
  }
  catch (error) {
    console.error("saveUserDirection error:", error);
    throw error;
  }
}
