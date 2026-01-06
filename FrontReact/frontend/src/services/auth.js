import {
  KEY,
  API_URL,
  saveToLocalStorage,
  getFromLocalStorage,
  clearLocalStorage,
  removeFromLocalStorage,
  API_TOKEN,
  SESSION_ID,
  buildFetchHeader
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
  const token = data?.token;
  if (token) {
    console.log('saveAuth.sessionId: ' + token);

    saveToLocalStorage(API_TOKEN, String(token));
    saveToLocalStorage(SESSION_ID, String(data.user.sessionId));
    //TODO: Si no tenemos el token, deberiamos cerrar sesion
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
export async function loginApi({ email, password, userType }) {
  const res = await fetch(API_URL + "/users/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      mail: email,
      password: password,
      userType: userType
    })
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "Credenciales inválidas");
  }

  return data;
}

export async function sessionRenew() {
  const res = await fetch(API_URL + "/users/session_renew", {
    method: "POST",
    headers: buildFetchHeader(),
    body: JSON.stringify({
      sessionId: getFromLocalStorage(SESSION_ID)
    })
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    clearLocalStorage();
    //TODO: Ver si puedo redireccionar al login
    throw new Error(data.message || "Credenciales inválidas");
  }

  return data;
}