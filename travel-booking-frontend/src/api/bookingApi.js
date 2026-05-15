import api from "./axios";

export const bookingApi = {
  create: (data) => api.post("/bookings", data),
  getMyBookings: () => api.get("/bookings/my"),
  getById: (id) => api.get(`/bookings/${id}`),
  cancel: (id) => api.put(`/bookings/${id}/cancel`),
};
