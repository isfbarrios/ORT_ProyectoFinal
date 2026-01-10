import {
  API_URL,
  buildFetchHeader
} from "../functions/localStorage"

export async function getMenuItemsByMenu(menuId) {
  const res = await fetch(`${API_URL}/menu_item/menu/${menuId}`, {
    method: "GET",
    headers: buildFetchHeader(),
    credentials:"include"
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || "Error al obtener los items del menú");
  }

  return await res.json();
}

// Importar menú desde un archivo Excel
export async function uploadMenuExcelService(file) {
  const formData = new FormData();
  formData.append("file", file);

  const res = await fetch(`${API_URL}/menu/import`, {
    method: "POST",
    body: formData,
  });

  const text = await res.text().catch(() => "");

  if (!res.ok) {
    throw new Error(text || "No se pudo importar el menú");
  }

  // el backend devuelve un String ("Menú importado correctamente.")
  return text || "Menú importado correctamente.";
}
