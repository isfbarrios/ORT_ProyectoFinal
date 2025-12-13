import { createSlice } from "@reduxjs/toolkit";
import { getTableAvailability, reserveTable } from "../services/tableReservationService";

const initialState = {
    tables: [],
    selectedDate: null,
    selectedShiftId: null,
    loading: false,
    error: null,
    reservationSuccess: null,
};

const tableReservationSlice = createSlice({
    name: "tableReservation",
    initialState,
    reducers: {
        setDate: (state, action) => { state.selectedDate = action.payload; },
        setShift: (state, action) => { state.selectedShiftId = action.payload; },
        clearSuccess: (state) => { state.reservationSuccess = null; },
        clearError: (state) => { state.error = null; }
    },

    extraReducers: (builder) => {
        // No usamos createAsyncThunk, pero Redux Toolkit permite capturar "actions externas"
        builder
            .addCase("tableReservation/loading", (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase("tableReservation/setTables", (state, action) => {
                state.loading = false;
                state.tables = action.payload;
            })
            .addCase("tableReservation/error", (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
            .addCase("tableReservation/success", (state, action) => {
                state.loading = false;
                state.reservationSuccess = action.payload;
            });
    }
});

export const { setDate, setShift, clearSuccess, clearError } = tableReservationSlice.actions;
export default tableReservationSlice.reducer;

/* ==========================================================
   Async Actions Manuales
   ========================================================== */

export const fetchAvailability = (date, shiftId) => async (dispatch) => {
    dispatch({ type: "tableReservation/loading" });

    try {
        const data = await getTableAvailability(date, shiftId);
        dispatch({ type: "tableReservation/setTables", payload: data });
        
    } catch (err) {
        dispatch({ type: "tableReservation/error", payload: err.message });
    }
};

export const submitReservation = (payload) => async (dispatch) => {
    dispatch({ type: "tableReservation/loading" });

    try {
        const result = await reserveTable(payload);
        dispatch({ type: "tableReservation/success", payload: result });
    } catch (err) {
        dispatch({ type: "tableReservation/error", payload: err.message });
    }
};
