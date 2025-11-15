import { createSlice } from "@reduxjs/toolkit";
import { apiAddItemToCart, apiGetCart } from "../../services/cartService";

const SESSION_KEY = "restaurant-session-id";

const initialState = {
  items: [],
  totalAmount: 0,
  loading: false,
  error: null,
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {

    setCart(state, action) {
      state.items = action.payload.items || [];
      state.totalAmount = action.payload.totalAmount || 0;
      state.error = null;

    },

    setCartLoading(state, action) {
      state.loading = action.payload;

    },

    setCartError(state, action) {
      state.error = action.payload;

    },

    clearCartState(state) {
      state.items = [];
      state.totalAmount = 0;
      state.error = null;

    },

  },
});

export const { setCart, setCartLoading, setCartError, clearCartState } =
  cartSlice.actions;

export default cartSlice.reducer;

// ---------------------------
// aca van funciones async o manejo de storage. funciones que no son reducers
/*  
   - manejo de sessionId

   - lectura de headers

   - guardar en localStorage

   - actualizar Redux

    - manejar loading / error

*/

function getSessionId() {
  return localStorage.getItem(SESSION_KEY) || null;
}

function saveSessionIdFromHeaders(headers) {
  const sessionId = headers.get("X-Session-Id");
  if (sessionId) {
    localStorage.setItem(SESSION_KEY, sessionId);
  }
}

/**
 * Cargar carrito actual (si existe).  
 * Se llama desde un useEffect o cuando se abra el menú.
 */
export const fetchCartAsync = () => async (dispatch) => {
  dispatch(setCartLoading(true));
  dispatch(setCartError(null));

  try {
    const sessionId = getSessionId();
    const { data, headers } = await apiGetCart({ sessionId });

    saveSessionIdFromHeaders(headers);
    dispatch(setCart(data));
  } catch (error) {
    // Acá decidís qué hacer con el error
    dispatch(setCartError(error.message));
  } finally {
    dispatch(setCartLoading(false));
  }
};

/**
 * Agregar ítem al carrito.
 */
export const addItemToCartAsync =
  (menuItemId, quantity = 1) =>
  async (dispatch) => {
    dispatch(setCartLoading(true));
    dispatch(setCartError(null));

    try {
      const sessionId = getSessionId();
      const { data, headers } = await apiAddItemToCart({
        sessionId,
        menuItemId,
        quantity,
      });

      saveSessionIdFromHeaders(headers);
      dispatch(setCart(data));
    } catch (error) {
      dispatch(setCartError(error.message));
    } finally {
      dispatch(setCartLoading(false));
    }
};

