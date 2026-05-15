<template>
  <div>
    <h2 style="margin-bottom: 30px; color: #2c3e50">📋 Booking của tôi</h2>

    <div v-if="loading" style="text-align: center; padding: 60px; color: #666">
      Đang tải...
    </div>

    <div
      v-else-if="bookings.length === 0"
      style="text-align: center; padding: 60px; color: #666"
    >
      Bạn chưa có booking nào!
      <br /><br />
      <RouterLink to="/tours" style="color: #3498db"
        >Xem danh sách tour</RouterLink
      >
    </div>

    <div v-else>
      <div
        v-for="booking in bookings"
        :key="booking.bookId"
        style="
          background: white;
          border-radius: 8px;
          padding: 20px;
          margin-bottom: 15px;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        "
      >
        <div
          style="
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 15px;
          "
        >
          <div>
            <h3 style="color: #2c3e50; margin-bottom: 5px">
              {{ booking.tourName }}
            </h3>
            <span style="font-size: 12px; color: #999"
              >Mã: {{ booking.bookCode }}</span
            >
          </div>
          <span :style="statusStyle(booking.status)">
            {{
              booking.status === "confirmed" ? "✅ Đã xác nhận" : "❌ Đã huỷ"
            }}
          </span>
        </div>

        <div
          style="
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            font-size: 14px;
            color: #666;
            margin-bottom: 15px;
          "
        >
          <div>
            📅 {{ formatDate(booking.startDate) }} →
            {{ formatDate(booking.endDate) }}
          </div>
          <div>
            👥 {{ booking.numberAdult }} người lớn,
            {{ booking.numberChildren }} trẻ em
          </div>
          <div>💳 {{ booking.paymentMethod }}</div>
          <div>🎫 {{ booking.voucherCode || "Không dùng voucher" }}</div>
        </div>

        <div
          style="
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div style="font-size: 18px; font-weight: bold; color: #e74c3c">
            {{ formatPrice(booking.totalPrice) }}
          </div>
          <button
            v-if="booking.status === 'confirmed'"
            @click="handleCancel(booking.bookId)"
            style="
              padding: 8px 16px;
              background: #e74c3c;
              color: white;
              border: none;
              border-radius: 4px;
              cursor: pointer;
              font-size: 14px;
            "
          >
            Huỷ booking
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { bookingApi } from "@/api/bookingApi";

const bookings = ref([]);
const loading = ref(false);

onMounted(async () => {
  loading.value = true;
  try {
    const res = await bookingApi.getMyBookings();
    bookings.value = res.data;
  } finally {
    loading.value = false;
  }
});

async function handleCancel(bookId) {
  if (!confirm("Bạn có chắc muốn huỷ booking này?")) return;
  try {
    const res = await bookingApi.cancel(bookId);
    const index = bookings.value.findIndex((b) => b.bookId === bookId);
    if (index !== -1) bookings.value[index] = res.data;
    alert("Huỷ booking thành công!");
  } catch (e) {
    alert("Huỷ thất bại: " + (e.response?.data?.message || e.message));
  }
}

function statusStyle(status) {
  const base =
    "padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: bold;";
  return status === "confirmed"
    ? base + "background: #efe; color: #27ae60;"
    : base + "background: #fee; color: #e74c3c;";
}

function formatDate(date) {
  return new Date(date).toLocaleDateString("vi-VN");
}

function formatPrice(price) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
}
</script>
