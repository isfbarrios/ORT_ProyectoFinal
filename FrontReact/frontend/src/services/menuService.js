// src/services/menuService.js
const API_URL = "http://localhost:8080/api";

export async function getMenuItemsByMenu(menuId) {
  const res = await fetch(`${API_URL}/menu_item/menu/${menuId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener los items del menú");
  }

  return await res.json();
}
