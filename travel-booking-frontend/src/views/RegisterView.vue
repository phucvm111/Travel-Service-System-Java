<template>
  <div
    style="
      max-width: 500px;
      margin: 60px auto;
      background: white;
      padding: 40px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    "
  >
    <h2 style="text-align: center; margin-bottom: 30px; color: #2c3e50">
      Đăng ký
    </h2>

    <div
      style="
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 15px;
        margin-bottom: 15px;
      "
    >
      <div>
        <label style="display: block; margin-bottom: 6px; color: #555"
          >Họ</label
        >
        <input
          v-model="form.lastName"
          placeholder="Nguyễn"
          style="
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
          "
        />
      </div>
      <div>
        <label style="display: block; margin-bottom: 6px; color: #555"
          >Tên</label
        >
        <input
          v-model="form.firstName"
          placeholder="Văn A"
          style="
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
          "
        />
      </div>
    </div>

    <div style="margin-bottom: 15px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Gmail</label
      >
      <input
        v-model="form.gmail"
        type="email"
        placeholder="example@gmail.com"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          box-sizing: border-box;
        "
      />
    </div>

    <div style="margin-bottom: 15px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Mật khẩu</label
      >
      <input
        v-model="form.password"
        type="password"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          box-sizing: border-box;
        "
      />
    </div>

    <div style="margin-bottom: 15px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Số điện thoại</label
      >
      <input
        v-model="form.phone"
        placeholder="0901234567"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          box-sizing: border-box;
        "
      />
    </div>

    <div style="margin-bottom: 20px">
      <label style="display: block; margin-bottom: 6px; color: #555"
        >Giới tính</label
      >
      <select
        v-model="form.gender"
        style="
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          box-sizing: border-box;
        "
      >
        <option value="Nam">Nam</option>
        <option value="Nữ">Nữ</option>
      </select>
    </div>

    <div
      v-if="error"
      style="
        background: #fee;
        color: #e74c3c;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 15px;
      "
    >
      {{ error }}
    </div>
    <div
      v-if="success"
      style="
        background: #efe;
        color: #27ae60;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 15px;
      "
    >
      {{ success }}
    </div>

    <button
      @click="handleRegister"
      :disabled="loading"
      style="
        width: 100%;
        padding: 12px;
        background: #27ae60;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
      "
    >
      {{ loading ? "Đang đăng ký..." : "Đăng ký" }}
    </button>

    <p
      style="text-align: center; margin-top: 20px; color: #666; font-size: 14px"
    >
      Đã có tài khoản?
      <RouterLink to="/login" style="color: #3498db">Đăng nhập</RouterLink>
    </p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { authApi } from "@/api/authApi";

const form = ref({
  firstName: "",
  lastName: "",
  gmail: "",
  password: "",
  phone: "",
  gender: "Nam",
});
const error = ref("");
const success = ref("");
const loading = ref(false);

async function handleRegister() {
  if (!form.value.gmail || !form.value.password || !form.value.firstName) {
    error.value = "Vui lòng nhập đầy đủ thông tin!";
    return;
  }
  loading.value = true;
  error.value = "";
  success.value = "";
  try {
    await authApi.register(form.value);
    success.value = "Đăng ký thành công! Vui lòng đăng nhập.";
    form.value = {
      firstName: "",
      lastName: "",
      gmail: "",
      password: "",
      phone: "",
      gender: "Nam",
    };
  } catch (e) {
    error.value = e.response?.data?.message || "Đăng ký thất bại!";
  } finally {
    loading.value = false;
  }
}
</script>
