import { createSlice } from "@reduxjs/toolkit";
import { updateOrderState } from "../../services/orderService";

// Columnas fijas del tablero
const BASE_COLUMNS = [
  { id: 1, title: "Pendiente" },
  { id: 2, title: "En preparacion" },
  { id: 3, title: "Listo" },
  { id: 4, title: "Entregado" },
];

const initialState = {
  columns: [],       // columnas del tablero (a partir de BASE_COLUMNS)
  orders: {},        // { [id]: pedido }
  isLoading: false,
  error: null,
};

const insertAt = (list, item, index) => {
  const next = list.slice();
  const safeIndex = index == null || index < 0 ? next.length : index;
  next.splice(safeIndex, 0, item);
  return next;
};

const dashboardSlice = createSlice({
  name: "dashboard",
  initialState,
  reducers: {
    // Recibe directamente el array de pedidos que viene del backend
    setBoard: (state, action) => {
      console.log('dashboardSlice - setBoard action.payload:', action.payload);
      const ordersArray = action.payload || [];

      //  Guardar pedidos en un objeto indexado por id
      state.orders = {};
      ordersArray.forEach((o) => {
        state.orders[o.id] = o;
      });

      //  Construir columnas fijas + orderIds según state.id (nuevo payload del backend)
      state.columns = BASE_COLUMNS.map((col) => ({
        ...col,
        orderIds: ordersArray
          .filter((o) => o.state?.id === col.id)
          .map((o) => o.id),
      }));

    },

    // Para refrescar solo pedidos, pero manteniendo la lógica de columnas fijas
    setOrders: (state, action) => {
      console.log('dashboardSlice - setOrders action.payload:', action.payload);
      const ordersArray = action.payload || [];

      state.orders = {};
      ordersArray.forEach((o) => {
        state.orders[o.id] = o;
      });

      state.columns = BASE_COLUMNS.map((col) => ({
        ...col,
        orderIds: ordersArray
          .filter((o) => o.state?.id === col.id)
          .map((o) => o.id),
      }));
    },

    // mover pedido entre columnas (drag & drop) solo a nivel front
    moveOrder: (state, action) => {
      let { orderId, sourceColId, targetColId, targetIndex } = action.payload;

      // Fuerzo todo a número por las dudas
      orderId = Number(orderId);
      sourceColId = Number(sourceColId);
      targetColId = Number(targetColId);

      console.log("orderId", orderId);

      const order = state.orders[orderId];
      if (!order) return;

      const sourceCol = state.columns.find(
        (col) => Number(col.id) === sourceColId
      );
      const targetCol = state.columns.find(
        (col) => Number(col.id) === targetColId
      );

      if (!sourceCol || !targetCol) return;

      // Quitar de origen
      sourceCol.orderIds = (sourceCol.orderIds || []).filter(
        (id) => Number(id) !== orderId
      );

      // Agregar a destino
      if (!targetCol.orderIds) {
        targetCol.orderIds = [];
      }
      if (!targetCol.orderIds.some((id) => Number(id) === orderId)) {
        targetCol.orderIds = insertAt(targetCol.orderIds, orderId, targetIndex);
      }

      // Actualizar el "estado" del pedido en memoria (state en lugar de cartState)
      if (order.state) {
        order.state = {
          ...order.state,
          id: targetColId,
        };
      } else {
        order.state = { id: targetColId };
      }
    },

    reorderColumn: (state, action) => {
      const { columnId, orderIds } = action.payload || {};
      if (!columnId || !Array.isArray(orderIds)) return;
      const column = state.columns.find(
        (col) => Number(col.id) === Number(columnId)
      );
      if (!column) return;
      column.orderIds = orderIds;
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
  },
});

export const {
  setBoard,
  setOrders,
  moveOrder,
  reorderColumn,
  updateOrder,
  setLoading,
  setError,
  resetDashboard,
} = dashboardSlice.actions;

export const syncOrderStatus =
  ({ orderId, targetColId }) =>
  async () => {
    await updateOrderState(orderId, targetColId);
    return true;
  };

export default dashboardSlice.reducer;
