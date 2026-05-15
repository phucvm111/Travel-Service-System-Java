<template>
  <div>
    <!-- Hero -->
    <div
      style="
        background: linear-gradient(135deg, #667eea, #764ba2);
        padding: 80px 40px;
        border-radius: 8px;
        text-align: center;
        color: white;
        margin-bottom: 40px;
      "
    >
      <h1 style="font-size: 42px; margin-bottom: 15px">🌏 Khám phá Việt Nam</h1>
      <p style="font-size: 18px; opacity: 0.9; margin-bottom: 30px">
        Đặt tour du lịch dễ dàng, nhanh chóng
      </p>
      <RouterLink
        to="/tours"
        style="
          background: white;
          color: #667eea;
          padding: 14px 40px;
          border-radius: 30px;
          text-decoration: none;
          font-weight: bold;
          font-size: 16px;
        "
      >
        Xem tất cả tour
      </RouterLink>
    </div>

    <!-- Tours nổi bật -->
    <h2 style="margin-bottom: 20px; color: #2c3e50">🔥 Tour nổi bật</h2>

    <div
      v-if="tourStore.loading"
      style="text-align: center; padding: 40px; color: #666"
    >
      Đang tải...
    </div>

    <div
      v-else
      style="
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 20px;
      "
    >
      <div
        v-for="tour in tourStore.tours.slice(0, 3)"
        :key="tour.tourId"
        style="
          background: white;
          border-radius: 8px;
          overflow: hidden;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        "
      >
        <div
          style="
            background: linear-gradient(135deg, #f093fb, #f5576c);
            height: 140px;
            display: flex;
            align-items: center;
            justify-content: center;
          "
        >
          <span style="font-size: 48px">🏖️</span>
        </div>

        <div style="padding: 20px">
          <h3 style="margin-bottom: 8px; color: #2c3e50; font-size: 15px">
            {{ tour.tourName }}
          </h3>
          <div
            style="
              color: #e74c3c;
              font-weight: bold;
              font-size: 16px;
              margin-bottom: 15px;
            "
          >
            Từ {{ formatPrice(getMinPrice(tour)) }}
          </div>
          <RouterLink
            :to="`/tours/${tour.tourId}`"
            style="
              display: block;
              text-align: center;
              padding: 8px;
              background: #3498db;
              color: white;
              border-radius: 4px;
              text-decoration: none;
              font-size: 14px;
            "
          >
            Xem chi tiết
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useTourStore } from "@/stores/tourStore";

const tourStore = useTourStore();

onMounted(() => {
  tourStore.fetchTours();
});

function getMinPrice(tour) {
  if (!tour.departures || tour.departures.length === 0) return 0;
  return Math.min(...tour.departures.map((d) => d.adultPrice));
}

function formatPrice(price) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
}
</script>
