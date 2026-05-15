import api from "./axios";

export const authApi = {
  login: (gmail, password) => api.post("/auth/login", { gmail, password }),

  register: (data) => api.post("/auth/register", data),
};
