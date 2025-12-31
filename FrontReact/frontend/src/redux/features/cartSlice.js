import { createSlice } from "@reduxjs/toolkit";
import {
  apiAddItemToCart,
  apiGetCart,
  apiConfirmCart,
  apiCloseCart,
} from "../../services/cartService";

import {
  SESSION_ID,
  getFromLocalStorage,
  clearLocalStorage
} from "../../functions/localStorage"

const initialState = {
  items: [],
  totalAmount: 0,
  loading: false,
  error: null,
  isCartModalOpen: false,
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

    openCartModal(state) {
      state.isCartModalOpen = true;
    },

    closeCartModal(state) {
      state.isCartModalOpen = false;
    },
  },
});

export const {
  setCart,
  setCartLoading,
  setCartError,
  clearCartState,
  openCartModal,
  closeCartModal,
} = cartSlice.actions;

export default cartSlice.reducer;

/**
 * Cargar carrito actual (si existe).
 * Se llama desde un useEffect o cuando se abra el menú / carrito.
 */
export const fetchCartAsync = () => async (dispatch) => {
  dispatch(setCartLoading(true));
  dispatch(setCartError(null));

  try {
    const data = await apiGetCart();
    dispatch(setCart(data));
  }
  catch (error) {
    dispatch(setCartError(error.message));
  }
  finally {
    dispatch(setCartLoading(false));
  }
};

/**
 * Agregar ítem al carrito.
 */
export const addItemToCartAsync = (menuItemId, quantity) =>
  async (dispatch) => {
    dispatch(setCartLoading(true));
    dispatch(setCartError(null));

    try {
      const data = await apiAddItemToCart(menuItemId, quantity);

      dispatch(setCart(data));
    }
    catch (error) {
      dispatch(setCartError(error.message));
    }
    finally {
      dispatch(setCartLoading(false));
    }
  };

/**
 * Confirmar carrito:
 * - llama al backend (/api/session-cart/confirm)
 * - genera una Order real para el Dashboard
 * - limpia el carrito y la sesión local
 * Devuelve la OrderDTO para que el componente pueda, por ejemplo, navegar al dashboard.
 */
export const confirmCartAsync = () => async (dispatch) => {
  dispatch(setCartLoading(true));
  dispatch(setCartError(null));

  try {
    const sessionId = getFromLocalStorage(SESSION_ID);
    const data = await apiConfirmCart(sessionId);

    dispatch(clearCartState());
    //clearLocalStorage();

    return data; // OrderDTO
  }
  catch (error) {
    dispatch(setCartError(error.message));
    return null;
  }
  finally {
    dispatch(setCartLoading(false));
  }
};

/**
 * Cerrar carrito sin generar orden:
 * - llama al backend (/api/session-cart/close)
 * - limpia carrito y borra sessionId local
 */
export const closeCartAsync = () => async (dispatch) => {
  dispatch(setCartLoading(true));
  dispatch(setCartError(null));

  try {
    const sessionId = getFromLocalStorage(SESSION_ID);
    const data = await apiCloseCart(sessionId);

    console.log(data);

    dispatch(clearCartState());
    clearLocalStorage();
  }
  catch (error) {
    dispatch(setCartError(error.message));
  }
  finally {
    dispatch(setCartLoading(false));
  }
};
