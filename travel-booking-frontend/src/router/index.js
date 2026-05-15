import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

const routes = [
  { path: "/", name: "Home", component: () => import("@/views/HomeView.vue") },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/LoginView.vue"),
    meta: { guestOnly: true },
  },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("@/views/AdminView.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/agent",
    name: "Agent",
    component: () => import("@/views/AgentView.vue"),
    meta: { requiresAuth: true, requiresAgent: true },
  },

  {
    path: "/register",
    name: "Register",
    component: () => import("@/views/RegisterView.vue"),
    meta: { guestOnly: true },
  },
  {
    path: "/tours",
    name: "TourList",
    component: () => import("@/views/TourListView.vue"),
  },
  {
    path: "/tours/:id",
    name: "TourDetail",
    component: () => import("@/views/TourDetailView.vue"),
  },
  {
    path: "/booking/:departureId",
    name: "Booking",
    component: () => import("@/views/BookingView.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/my-bookings",
    name: "MyBookings",
    component: () => import("@/views/MyBookingsView.vue"),
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const auth = useAuthStore();
  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    next("/login");
  } else if (to.meta.guestOnly && auth.isLoggedIn) {
    next("/");
  } else if (to.meta.requiresAdmin && !auth.isAdmin) {
    next("/");
  } else if (to.meta.requiresAgent && !auth.isAgent) {
    next("/");
  } else {
    next();
  }
});

export default router;
