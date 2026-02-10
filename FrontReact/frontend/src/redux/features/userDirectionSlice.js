import { createSlice } from "@reduxjs/toolkit";
import { saveUserDirection } from "../../services/userDirectionService";

const initialState = {
  direction: null,
  loading: false,
  error: null,
};

const userDirectionSlice = createSlice({
  name: "userDirection",
  initialState,

  reducers: {

    saveDirectionStart(state) {
      state.loading = true;
      state.error = null;

    },
    saveDirectionSuccess(state, action) {

      state.loading = false;
      state.direction = action.payload;

    },
    saveDirectionError(state, action) {

      state.loading = false;
      state.error = action.payload;

    },
  },
});

export const {
  saveDirectionStart,
  saveDirectionSuccess,
  saveDirectionError,

} = userDirectionSlice.actions;

export default userDirectionSlice.reducer;

export const saveUserDirectionAsync = (direction) => async (dispatch) => {
  dispatch(saveDirectionStart());

  try {
    const data = await saveUserDirection(direction);
    dispatch(saveDirectionSuccess(data));
    return { data };
  } catch (error) {
    dispatch(saveDirectionError(error.message));
    return { error: error.message };
  }
};
