<template>
  <div>
    <h2 style="margin-bottom: 20px; color: #2c3e50">🗺️ Danh sách Tour</h2>

    <!-- Search -->
    <div style="display: flex; gap: 10px; margin-bottom: 30px">
      <input
        v-model="keyword"
        @keyup.enter="handleSearch"
        placeholder="Tìm kiếm tour..."
        style="
          flex: 1;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
          font-size: 14px;
        "
      />
      <button
        @click="handleSearch"
        style="
          padding: 10px 20px;
          background: #3498db;
          color: white;
          border: none;
          border-radius: 4px;
          cursor: pointer;
        "
      >
        Tìm
      </button>
      <button
        @click="handleReset"
        style="
          padding: 10px 20px;
          background: #95a5a6;
          color: white;
          border: none;
          border-radius: 4px;
          cursor: pointer;
        "
      >
        Reset
      </button>
    </div>

    <!-- Loading -->
    <div
      v-if="tourStore.loading"
      style="text-align: center; padding: 40px; color: #666"
    >
      Đang tải...
    </div>

    <!-- Tour Grid -->
    <div
      v-else
      style="
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
        gap: 20px;
      "
    >
      <div
        v-for="tour in tourStore.tours"
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
            background: linear-gradient(135deg, #667eea, #764ba2);
            height: 160px;
            display: flex;
            align-items: center;
            justify-content: center;
          "
        >
          <span style="font-size: 48px">🏖️</span>
        </div>

        <div style="padding: 20px">
          <h3 style="margin-bottom: 8px; color: #2c3e50; font-size: 16px">
            {{ tour.tourName }}
          </h3>
          <p
            style="
              color: #666;
              font-size: 13px;
              margin-bottom: 12px;
              line-height: 1.5;
            "
          >
            {{ tour.description?.substring(0, 100) }}...
          </p>

          <div
            style="
              display: flex;
              justify-content: space-between;
              align-items: center;
              margin-bottom: 15px;
            "
          >
            <div>
              <span style="font-size: 12px; color: #999">Từ</span>
              <div style="color: #e74c3c; font-weight: bold; font-size: 18px">
                {{ formatPrice(getMinPrice(tour)) }}
              </div>
            </div>
            <div style="text-align: right; font-size: 12px; color: #666">
              <div>{{ tour.departures?.length || 0 }} lịch khởi hành</div>
            </div>
          </div>

          <RouterLink
            :to="`/tours/${tour.tourId}`"
            style="
              display: block;
              text-align: center;
              padding: 10px;
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

    <div
      v-if="!tourStore.loading && tourStore.tours.length === 0"
      style="text-align: center; padding: 60px; color: #666"
    >
      Không tìm thấy tour nào!
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useTourStore } from "@/stores/tourStore";

const tourStore = useTourStore();
const keyword = ref("");

onMounted(() => {
  tourStore.fetchTours();
});

function handleSearch() {
  if (keyword.value.trim()) {
    tourStore.searchTours(keyword.value);
  }
}

function handleReset() {
  keyword.value = "";
  tourStore.fetchTours();
}

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
