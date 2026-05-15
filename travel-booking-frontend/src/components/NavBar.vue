<template>
  <nav
    style="
      background: #2c3e50;
      padding: 15px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    "
  >
    <RouterLink
      to="/"
      style="
        color: white;
        text-decoration: none;
        font-size: 20px;
        font-weight: bold;
      "
    >
      🌏 Travel Booking
    </RouterLink>

    <div style="display: flex; gap: 15px; align-items: center">
      <RouterLink to="/tours" style="color: white; text-decoration: none"
        >Tours</RouterLink
      >

      <template v-if="auth.isLoggedIn">
        <RouterLink
          v-if="auth.isAdmin"
          to="/admin"
          style="color: #f39c12; text-decoration: none; font-weight: bold"
        >
          Admin Dashboard
        </RouterLink>
        <RouterLink
          v-if="auth.isAgent"
          to="/agent"
          style="color: #2ecc71; text-decoration: none; font-weight: bold"
        >
          Agent Dashboard
        </RouterLink>
        <RouterLink
          to="/my-bookings"
          style="color: white; text-decoration: none"
        >
          Booking của tôi
        </RouterLink>
        <span style="color: #aaa">{{ auth.user?.fullName }}</span>
        <button
          @click="handleLogout"
          style="
            background: #e74c3c;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
          "
        >
          Đăng xuất
        </button>
      </template>

      <template v-else>
        <RouterLink
          to="/login"
          style="
            background: #3498db;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
            text-decoration: none;
          "
        >
          Đăng nhập
        </RouterLink>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { useAuthStore } from "@/stores/authStore";
import { useRouter } from "vue-router";

const auth = useAuthStore();
const router = useRouter();

function handleLogout() {
  auth.logout();
  router.push("/login");
}
</script>
