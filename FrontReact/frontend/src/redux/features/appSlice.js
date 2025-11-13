import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  user: null,
  isLoading: false,
}

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    
    setUser: (state, action) => {
      state.user = action.payload
    },

    clearUser: (state) => {
      state.user = null
    },

    setLoading: (state, action) => {
      state.isLoading = action.payload
    },
  },
})

export const { setUser, clearUser, setLoading } = appSlice.actions
export default appSlice.reducer
