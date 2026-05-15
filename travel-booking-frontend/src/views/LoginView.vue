<template>
  <div
    style="
      max-width: 400px;
      margin: 80px auto;
      background: white;
      padding: 40px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    "
  >
    <h2 style="text-align: center; margin-bottom: 30px; color: #2c3e50">
      Đăng nhập
    </h2>

    <div style="margin-bottom: 20px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Gmail</label
      >
      <input
        v-model="gmail"
        type="email"
        placeholder="example@gmail.com"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          font-size: 14px;
          box-sizing: border-box;
        "
      />
    </div>

    <div style="margin-bottom: 20px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Mật khẩu</label
      >
      <input
        v-model="password"
        type="password"
        placeholder="••••••••"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          font-size: 14px;
          box-sizing: border-box;
        "
      />
    </div>

    <div
      v-if="error"
      style="
        background: #fee;
        color: #e74c3c;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 15px;
        font-size: 14px;
      "
    >
      {{ error }}
    </div>

    <button
      @click="handleLogin"
      :disabled="loading"
      style="
        width: 100%;
        padding: 12px;
        background: #3498db;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
      "
    >
      {{ loading ? "Đang đăng nhập..." : "Đăng nhập" }}
    </button>

    <p
      style="text-align: center; margin-top: 20px; color: #666; font-size: 14px"
    >
      Chưa có tài khoản?
      <RouterLink to="/register" style="color: #3498db">Đăng ký</RouterLink>
    </p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

const auth = useAuthStore();
const router = useRouter();

const gmail = ref("");
const password = ref("");
const error = ref("");
const loading = ref(false);

async function handleLogin() {
  if (!gmail.value || !password.value) {
    error.value = "Vui lòng nhập đầy đủ thông tin!";
    return;
  }
  loading.value = true;
  error.value = "";
  try {
    await auth.login(gmail.value, password.value);
    router.push("/");
  } catch (e) {
    error.value = "Gmail hoặc mật khẩu không đúng!";
  } finally {
    loading.value = false;
  }
}
</script>
