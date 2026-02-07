import {
  API_URL,
  buildFetchHeader,
} from "../functions/localStorage";
import { clearAuth } from "./auth";

export async function saveUserDirection(direction) {
  try {
    console.log("saveUserDirection direction:", direction);
    const res = await fetch(API_URL + "/user_direction/new_direction", {
      method: "POST",
      headers: buildFetchHeader(),
      body: JSON.stringify(direction),
      credentials: "include"
    });

    if (res.status === 401) {
      clearAuth();
      window.location.href = "/";
      throw new Error("Unauthorized");
    }

    if (!res.ok) {
      const error = await res.text();
      throw new Error(error || "Error al guardar la dirección");
    }

    return await res.json();
  }
  catch (error) {
    console.error("saveUserDirection error:", error);
    throw error;
  }
}

export async function getUserDirections() {
  try {
    const res = await fetch(API_URL + "/user_direction", {
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
      const error = await res.text();
      throw new Error(error || "Error al obtener las direcciones del usuario");
    }

    return await res.json();
  }
  catch (error) {
    console.error("saveUserDirection error:", error);
    throw error;
  }
}

export async function deleteUserDirection(id) {
  try {
    const res = await fetch(API_URL + `/user_direction/${id}`, {
      method: "DELETE",
      headers: buildFetchHeader(),
      credentials: "include"
    });

    if (res.status === 401) {
      clearAuth();
      window.location.href = "/";
      throw new Error("Unauthorized");
    }

    if (!res.ok) {
      const error = await res.text();
      throw new Error(error || "Error al eliminar la dirección");
    }

    return await res.json();
  }
  catch (error) {
    console.error("deleteUserDirection error:", error);
    throw error;
  }
}