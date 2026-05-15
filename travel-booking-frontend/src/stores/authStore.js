import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { authApi } from "@/api/authApi";

export const useAuthStore = defineStore("auth", () => {
  const token = ref(localStorage.getItem("token") || null);

  let savedUser = null;
  try {
    const raw = localStorage.getItem("user");
    if (raw) savedUser = JSON.parse(raw);
  } catch (e) {
    localStorage.removeItem("user");
  }
  const user = ref(savedUser);

  const isLoggedIn = computed(() => !!token.value);
  const isAgent = computed(() => user.value?.role === "Agent");
  const isTourist = computed(
    () =>
      user.value?.role === "Tourist" ||
      user.value?.role === "TouristAuthenticate",
  );
  const isAdmin = computed(() => user.value?.role === "Admin");

  async function login(gmail, password) {
    const res = await authApi.login(gmail, password);
    token.value = res.data.token;
    user.value = {
      userId: res.data.userId,
      gmail: res.data.gmail,
      fullName: res.data.fullName,
      role: res.data.role,
    };
    localStorage.setItem("token", token.value);
    localStorage.setItem("user", JSON.stringify(user.value));
  }

  function logout() {
    token.value = null;
    user.value = null;
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  }

  return {
    token,
    user,
    isLoggedIn,
    isAgent,
    isTourist,
    isAdmin,
    login,
    logout,
  };
});
