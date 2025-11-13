import { configureStore } from '@reduxjs/toolkit'
//import appReducer from './features/appSlice'
import dashboardReducer from "./features/dashboardSlice";

export const store = configureStore({
  reducer: {
   // app: appReducer,\
   dashboard: dashboardReducer,
  },
})
