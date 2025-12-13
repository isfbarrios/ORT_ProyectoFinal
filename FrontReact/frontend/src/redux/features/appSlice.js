import { createSlice } from '@reduxjs/toolkit'
import { getAuth, saveAuth, clearAuth } from '../../services/auth'


const stored = getAuth();

const initialState = {
  user: stored?.user || null,
  isLoading: false,
};


const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {

    setUser: (state, action) => {
      state.user = action.payload;
      saveAuth({ user: action.payload });
    },

    clearUser: (state) => {
      state.user = null;
      clearAuth();
    },

    setLoading: (state, action) => {
      state.isLoading = action.payload
    },
  },
})

export const { setUser, clearUser, setLoading } = appSlice.actions
export default appSlice.reducer
