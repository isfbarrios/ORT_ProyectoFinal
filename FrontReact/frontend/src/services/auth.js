import {
  KEY,
  API_URL,
  saveToLocalStorage,
  getFromLocalStorage,
  removeFromLocalStorage,
  API_TOKEN,
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
  if (accessToken) {
    console.log('saveAuth.sessionId: ' + accessToken);

    saveToLocalStorage(API_TOKEN, String(accessToken));
    //saveToLocalStorage(SESSION_ID, String(data.user.sessionId));
    //TODO: Si no tenemos el accessToken, deberiamos cerrar sesion
  }
}

// leo la sesion guardada
export function getAuth() {
  return getFromLocalStorage(KEY);
}

//borro la sesion guardada en storage
export function clearAuth() {
  removeFromLocalStorage(KEY);
  removeFromLocalStorage(API_TOKEN);
  removeFromLocalStorage(SESSION_ID);
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
  catch {
    throw new Error("Respuesta inválida del servidor");
  }
}