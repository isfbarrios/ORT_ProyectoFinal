import {
  API_URL,
  buildFetchHeader
} from "../functions/localStorage"

export async function getMenuItemsByMenu(menuId) {
  const res = await fetch(`${API_URL}/menu_item/menu/${menuId}`, {
    method: "GET",
    headers: buildFetchHeader(),
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener los items del menú");
  }

  return await res.json();
}
