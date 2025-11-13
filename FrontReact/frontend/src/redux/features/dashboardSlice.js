import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  columns: [],       // vienen del back
  orders: {},        // { [id]: pedido }
  isLoading: false,
  error: null,
};

const dashboardSlice = createSlice({
  name: "dashboard",
  initialState,
  reducers: {

     setBoard: (state, action) => {
      // espero recibir 
      const { columns, orders } = action.payload;
    
      // como vienen del back
      state.columns = columns || [];

        state.orders = {};
      (orders || []).forEach((o) => {
        state.orders[o.id] = o;
      });
    },

    // para refresacra solo los pedidos 
    setOrders: (state, action) => {

      state.orders = {};
      (action.payload || []).forEach((o) => {
        state.orders[o.id] = o;
      });
    },

     // mover pedido entre columnas (drag & drop)
    moveOrder: (state, action) => {
    const { orderId, sourceColId, targetColId } = action.payload;

    // Busco el pedido
    const order = state.orders[orderId];
    if (!order) return;

    // Busco la columna origen y destino
    const sourceCol = state.columns.find(col => col.id === sourceColId);
    const targetCol = state.columns.find(col => col.id === targetColId);
    if (!sourceCol || !targetCol) return;

    // Saco el pedido de la columna origen
    sourceCol.orderIds = sourceCol.orderIds.filter(id => id !== orderId);

    // Lo agrego al final de la columna destino
    targetCol.orderIds.push(orderId);

    //  Actualizo el estado del pedido
    order.status = targetColId;
    },

    updateOrder: (state, action) => {
      const { id, changes } = action.payload || {};

      if (!id || !changes) return;
      if (!state.orders[id]) return;

      state.orders[id] = {
        ...state.orders[id],
        ...changes,
      };
    },

    setLoading: (state, action) => {
      state.isLoading = action.payload;
    },


      setError: (state, action) => {
      state.error = action.payload;
    },

    resetDashboard: () => initialState,

  }

  });

  export const {
  setBoard,
  setOrders,
  moveOrder,
  updateOrder,
  setLoading,
  setError,
  resetDashboard,
} = dashboardSlice.actions;

export default dashboardSlice.reducer;