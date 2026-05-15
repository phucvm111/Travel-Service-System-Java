import api from "./axios";

export const tourApi = {
  getAll: () => api.get("/tours"),
  getById: (id) => api.get(`/tours/${id}`),
  search: (keyword) => api.get(`/tours/search?keyword=${keyword}`),
  create: (data) => api.post("/tours", data),
  update: (id, data) => api.put(`/tours/${id}`, data),
  remove: (id) => api.delete(`/tours/${id}`),
};
