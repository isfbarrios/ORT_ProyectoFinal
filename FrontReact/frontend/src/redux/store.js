import { configureStore } from '@reduxjs/toolkit'
import appReducer from './features/appSlice'
import dashboardReducer from "./features/dashboardSlice";
import cartReducer from "./features/cartSlice";
import tableReservationReducer from "./features/tableReservationSlice";

export const store = configureStore({
  reducer: {
    app: appReducer,
    dashboard: dashboardReducer,
    cart: cartReducer,
    tableReservation: tableReservationReducer
  },
})