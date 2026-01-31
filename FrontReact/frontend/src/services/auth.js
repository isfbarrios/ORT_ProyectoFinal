import {
  KEY,
  API_URL,
  saveToLocalStorage,
  getFromLocalStorage,
  API_TOKEN,
  API_REFRESH_TOKEN,
  SESSION_ID,
} from "../functions/localStorage"

// guardo la session del usuario
export function saveAuth(data) {
  const session = {
    isLogged: true,
    user: data.user ?? data,
  };

  console.log('saveAuth ini');
  console.log(data);
  console.log('saveAuth end');

  saveToLocalStorage(KEY, session);

  // si el back me  devolvió sessionId, lo guardo para el carrito
  const accessToken = data?.accessToken;
  const refreshToken = data?.refreshToken;

  if (refreshToken) {
    saveToLocalStorage(API_REFRESH_TOKEN, String(refreshToken));
  }

  if (accessToken) {
    saveToLocalStorage(API_TOKEN, String(accessToken));
  }
}

// leo la sesion guardada
export function getAuth() {
  return getFromLocalStorage(KEY);
}

//borro la sesion guardada en storage
export function clearAuth() {
  localStorage.clear();
}

// MOCK login (luego lo vamos a remplazar por fetch al backend)
export async function loginApi({ username, password, userType }) {
  const res = await fetch(API_URL + "/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      nombreUsuario: username,
      contrasenia: password,
      type: userType
    }),
    credentials: "include"
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Credenciales inválidas");
  }
  // El backend devuelve JSON solo en caso de éxito (AuthResponseDTO)
  try {
    const data = await res.json();
    return data;
  }
  catch (error) {
    console.error("Error al parsear JSON:", error);
    throw new Error("Respuesta inválida del servidor");
  }
}

export async function refreshToken() {
  let accessToken = getFromLocalStorage(API_TOKEN);
  let refreshToken = getFromLocalStorage(API_REFRESH_TOKEN);

  if (!accessToken || !refreshToken) {
    throw new Error("No hay tokens disponibles para refrescar la sesión");
  }

  const res = await fetch(API_URL + "/auth/refresh", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      accessToken: accessToken,
      refreshToken: refreshToken
    }),
    credentials: "include"
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Error refrescando la sesión");
  }

  try {
    const data = await res.json();
    return data;
  }
  catch (error) {
    console.error("Error al parsear JSON:", error);
    throw new Error("Respuesta inválida del servidor");
  }
}

export async function closeSession() {
  clearAuth
}