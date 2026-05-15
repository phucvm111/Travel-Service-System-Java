import api from "./axios";

export const adminApi = {
  getStats: () => api.get("/admin/stats"),
  getAllUsers: () => api.get("/admin/users"),
  getAllBookings: () => api.get("/admin/bookings"),
  toggleUserStatus: (userId) => api.put(`/admin/users/${userId}/toggle`),
};
