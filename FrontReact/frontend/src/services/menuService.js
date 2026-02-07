import {
  API_URL,
  buildFetchHeader,
  getFromLocalStorage
} from "../functions/localStorage";
import { clearAuth } from "./auth";

export async function getMenuItemsByMenu(menuId) {
  const res = await fetch(`${API_URL}/menu_item/menu/${menuId}`, {
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
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener los items del menú");
  }

  return await res.json();
}

export async function updateMenuByFile(formData) {
  const token = getFromLocalStorage("API_TOKEN");
  const headers = {};

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${API_URL}/menu/import`, {
    method: "PUT",
    headers: headers,
    body: formData,
    credentials: "include"
  });

  if (res.status === 401) {
    clearAuth();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al actualizar el menú");
  }

  const text = await res.text().catch(() => "");
  if (!text) return "";
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}
