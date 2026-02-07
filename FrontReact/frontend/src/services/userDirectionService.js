import {
  API_URL,
  buildFetchHeader,
} from "../functions/localStorage";

export async function saveUserDirection(direction) {
  try {
    console.log("saveUserDirection direction:", direction);
    const response = await fetch(API_URL + "/user_direction/new_direction", {
      method: "POST",
      headers: buildFetchHeader(),
      body: JSON.stringify(direction),
      credentials: "include"
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

export async function getUserDirections() {
  try {
    const response = await fetch(API_URL + "/user_direction", {
      method: "GET",
      headers: buildFetchHeader(),
      credentials: "include"
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || "Error al obtener las direcciones del usuario");
    }

    return await response.json();
  }
  catch (error) {
    console.error("saveUserDirection error:", error);
    throw error;
  }
}

export async function deleteUserDirection(id) {
  try {
    const response = await fetch(API_URL + `/user_direction/${id}`, {
      method: "DELETE",
      headers: buildFetchHeader(),
      credentials: "include"
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || "Error al eliminar la dirección");
    }

    return await response.json();
  }
  catch (error) {
    console.error("deleteUserDirection error:", error);
    throw error;
  }
}