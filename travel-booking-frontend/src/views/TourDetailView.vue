<template>
  <div v-if="tourStore.loading" style="text-align: center; padding: 60px">
    Đang tải...
  </div>

  <div v-else-if="tourStore.currentTour">
    <!-- Header -->
    <div
      style="
        background: linear-gradient(135deg, #667eea, #764ba2);
        padding: 40px;
        border-radius: 8px;
        color: white;
        margin-bottom: 30px;
      "
    >
      <h1 style="margin-bottom: 10px">{{ tourStore.currentTour.tourName }}</h1>
      <p style="opacity: 0.9">{{ tourStore.currentTour.description }}</p>
    </div>

    <!-- Departures -->
    <h3 style="margin-bottom: 20px; color: #2c3e50">📅 Lịch khởi hành</h3>

    <div
      v-if="activeDepartures.length === 0"
      style="color: #666; padding: 20px"
    >
      Hiện không có lịch khởi hành nào!
    </div>

    <div
      v-for="dep in activeDepartures"
      :key="dep.departureId"
      style="
        background: white;
        border-radius: 8px;
        padding: 20px;
        margin-bottom: 15px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        display: flex;
        justify-content: space-between;
        align-items: center;
      "
    >
      <div>
        <div style="font-weight: bold; color: #2c3e50; margin-bottom: 8px">
          📅 {{ formatDate(dep.startDate) }} → {{ formatDate(dep.endDate) }}
        </div>
        <div style="font-size: 14px; color: #666">
          👥 Còn {{ dep.availableSeat }} chỗ
        </div>
        <div style="font-size: 14px; color: #666; margin-top: 4px">
          Người lớn:
          <strong style="color: #e74c3c">{{
            formatPrice(dep.adultPrice)
          }}</strong>
          &nbsp;|&nbsp; Trẻ em:
          <strong style="color: #e74c3c">{{
            formatPrice(dep.childPrice)
          }}</strong>
        </div>
      </div>

      <div>
        <span
          v-if="dep.availableSeat === 0"
          style="
            padding: 10px 20px;
            background: #95a5a6;
            color: white;
            border-radius: 4px;
            font-size: 14px;
          "
        >
          Hết chỗ
        </span>
        <RouterLink
          v-else
          :to="`/booking/${dep.departureId}`"
          style="
            padding: 10px 20px;
            background: #e74c3c;
            color: white;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
          "
        >
          Đặt ngay
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useTourStore } from "@/stores/tourStore";

const route = useRoute();
const tourStore = useTourStore();

onMounted(() => {
  tourStore.fetchTourById(route.params.id);
});

const activeDepartures = computed(() => {
  return (
    tourStore.currentTour?.departures?.filter((d) => d.status === "active") ||
    []
  );
});

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
