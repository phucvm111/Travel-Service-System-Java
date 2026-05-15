import { defineStore } from "pinia";
import { ref } from "vue";
import { tourApi } from "@/api/tourApi";

export const useTourStore = defineStore("tour", () => {
  const tours = ref([]);
  const currentTour = ref(null);
  const loading = ref(false);

  async function fetchTours() {
    loading.value = true;
    try {
      const res = await tourApi.getAll();
      tours.value = res.data;
    } finally {
      loading.value = false;
    }
  }

  async function fetchTourById(id) {
    loading.value = true;
    try {
      const res = await tourApi.getById(id);
      currentTour.value = res.data;
    } finally {
      loading.value = false;
    }
  }

  async function searchTours(keyword) {
    loading.value = true;
    try {
      const res = await tourApi.search(keyword);
      tours.value = res.data;
    } finally {
      loading.value = false;
    }
  }

  return {
    tours,
    currentTour,
    loading,
    fetchTours,
    fetchTourById,
    searchTours,
  };
});
