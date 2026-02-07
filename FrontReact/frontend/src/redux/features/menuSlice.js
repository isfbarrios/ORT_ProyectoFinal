import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { updateMenuByFile } from "../../services/menuService";

export const updateMenuByFileAsync = createAsyncThunk(
  "menu/updateMenuByFile",
  async (formData, { rejectWithValue }) => {
    try {
      const data = await updateMenuByFile(formData);

      if (data?.message) {
        throw new Error(data.message);
      }

      return data;
    } catch (error) {
      return rejectWithValue(error.message || "Error al actualizar el menú");
    }
  }
);

const initialState = {
  loading: false,
  error: null,
  success: false,
  data: null,
};

const menuSlice = createSlice({
  name: "menu",
  initialState,
  reducers: {
    clearMenuMessage: (state) => {
      state.error = null;
      state.success = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(updateMenuByFileAsync.pending, (state) => {
        state.loading = true;
        state.error = null;
        state.success = false;
      })
      .addCase(updateMenuByFileAsync.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
        state.data = action.payload;
      })
      .addCase(updateMenuByFileAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
        state.success = false;
      });
  },
});

export const { clearMenuMessage } = menuSlice.actions;
export default menuSlice.reducer;