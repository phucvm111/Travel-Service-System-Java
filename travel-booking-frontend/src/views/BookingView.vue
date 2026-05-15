<template>
  <div style="max-width: 600px; margin: 0 auto">
    <h2 style="margin-bottom: 30px; color: #2c3e50">🎫 Đặt tour</h2>

    <div
      style="
        background: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      "
    >
      <div style="margin-bottom: 20px">
        <label style="display: block; margin-bottom: 6px; color: #555"
          >Họ</label
        >
        <input
          v-model="form.lastName"
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
          >Tên</label
        >
        <input
          v-model="form.firstName"
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
          >Số điện thoại</label
        >
        <input
          v-model="form.phone"
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
          >Gmail</label
        >
        <input
          v-model="form.gmail"
          type="email"
          style="
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
          "
        />
      </div>

      <div
        style="
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 15px;
          margin-bottom: 20px;
        "
      >
        <div>
          <label style="display: block; margin-bottom: 6px; color: #555"
            >Số người lớn</label
          >
          <input
            v-model.number="form.numberAdult"
            type="number"
            min="1"
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
            >Số trẻ em</label
          >
          <input
            v-model.number="form.numberChildren"
            type="number"
            min="0"
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

      <div style="margin-bottom: 20px">
        <label style="display: block; margin-bottom: 6px; color: #555"
          >Mã voucher (không bắt buộc)</label
        >
        <input
          v-model="form.voucherCode"
          placeholder="Nhập mã voucher nếu có"
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
          >Phương thức thanh toán</label
        >
        <select
          v-model.number="form.paymentMethodId"
          style="
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
          "
        >
          <option value="1">Wallet</option>
          <option value="2">Bank Transfer</option>
        </select>
      </div>

      <div style="margin-bottom: 20px">
        <label style="display: block; margin-bottom: 6px; color: #555"
          >Ghi chú</label
        >
        <textarea
          v-model="form.note"
          rows="3"
          style="
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
          "
        ></textarea>
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

      <button
        @click="handleBooking"
        :disabled="loading"
        style="
          width: 100%;
          padding: 14px;
          background: #e74c3c;
          color: white;
          border: none;
          border-radius: 4px;
          font-size: 16px;
          cursor: pointer;
        "
      >
        {{ loading ? "Đang xử lý..." : "✅ Xác nhận đặt tour" }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import { bookingApi } from "@/api/bookingApi";

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

const form = ref({
  departureId: parseInt(route.params.departureId),
  firstName: auth.user?.fullName?.split(" ").pop() || "",
  lastName: auth.user?.fullName?.split(" ").slice(0, -1).join(" ") || "",
  phone: "",
  gmail: auth.user?.gmail || "",
  numberAdult: 1,
  numberChildren: 0,
  voucherCode: "",
  paymentMethodId: 1,
  note: "",
});

const error = ref("");
const loading = ref(false);

async function handleBooking() {
  loading.value = true;
  error.value = "";
  try {
    const payload = {
      ...form.value,
      voucherCode: form.value.voucherCode || null,
    };
    const res = await bookingApi.create(payload);
    alert(
      `✅ Đặt tour thành công!\nMã booking: ${res.data.bookCode}\nTổng tiền: ${formatPrice(res.data.totalPrice)}`,
    );
    router.push("/my-bookings");
  } catch (e) {
    error.value = e.response?.data?.message || "Đặt tour thất bại!";
  } finally {
    loading.value = false;
  }
}

function formatPrice(price) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
}
</script>
