<template>
  <div>
    <h2 style="margin-bottom: 30px; color: #2c3e50">🏢 Agent Dashboard</h2>

    <div
      style="
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
      "
    >
      <h3 style="color: #2c3e50">Danh sách Tour của tôi</h3>
      <button
        @click="showForm = true"
        style="
          padding: 10px 20px;
          background: #27ae60;
          color: white;
          border: none;
          border-radius: 4px;
          cursor: pointer;
        "
      >
        + Thêm Tour
      </button>
    </div>

    <!-- Form thêm/sửa tour -->
    <div
      v-if="showForm"
      style="
        background: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
      "
    >
      <h3 style="margin-bottom: 20px">
        {{ editingTour ? "Sửa Tour" : "Thêm Tour mới" }}
      </h3>

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
            >Tên tour</label
          >
          <input
            v-model="form.tourName"
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
            >Số ngày</label
          >
          <input
            v-model.number="form.numberOfDay"
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
            >Điểm khởi hành</label
          >
          <input
            v-model="form.startPlace"
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
            >Điểm đến</label
          >
          <input
            v-model="form.endPlace"
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
          >Giới thiệu tour</label
        >
        <textarea
          v-model="form.tourIntroduce"
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

      <div style="display: flex; gap: 10px">
        <button
          @click="handleSave"
          :disabled="saving"
          style="
            padding: 10px 24px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
          "
        >
          {{ saving ? "Đang lưu..." : "Lưu" }}
        </button>
        <button
          @click="resetForm"
          style="
            padding: 10px 24px;
            background: #95a5a6;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
          "
        >
          Huỷ
        </button>
      </div>
    </div>

    <!-- Danh sách tour -->
    <div
      style="
        background: white;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
      "
    >
      <table style="width: 100%; border-collapse: collapse">
        <thead>
          <tr style="background: #f8f9fa">
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Tên tour
            </th>
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Số ngày
            </th>
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Điểm đi
            </th>
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Điểm đến
            </th>
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Trạng thái
            </th>
            <th
              style="
                padding: 12px;
                text-align: left;
                color: #666;
                font-size: 13px;
              "
            >
              Thao tác
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="tour in tours"
            :key="tour.tourId"
            style="border-top: 1px solid #f0f0f0"
          >
            <td style="padding: 12px; font-size: 14px; font-weight: 500">
              {{ tour.tourName }}
            </td>
            <td style="padding: 12px; font-size: 14px">
              {{ tour.numberOfDay }} ngày
            </td>
            <td style="padding: 12px; font-size: 14px">
              {{ tour.startPlace }}
            </td>
            <td style="padding: 12px; font-size: 14px">{{ tour.endPlace }}</td>
            <td style="padding: 12px">
              <span
                :style="`padding: 3px 10px; border-radius: 20px; font-size: 12px;
                                background: ${tour.status === 1 ? '#efe' : '#fee'};
                                color: ${tour.status === 1 ? '#27ae60' : '#e74c3c'};`"
              >
                {{ tour.status === 1 ? "Active" : "Inactive" }}
              </span>
            </td>
            <td style="padding: 12px; display: flex; gap: 8px">
              <button
                @click="handleEdit(tour)"
                style="
                  padding: 5px 12px;
                  background: #3498db;
                  color: white;
                  border: none;
                  border-radius: 4px;
                  cursor: pointer;
                  font-size: 12px;
                "
              >
                Sửa
              </button>
              <button
                @click="handleDelete(tour.tourId)"
                style="
                  padding: 5px 12px;
                  background: #e74c3c;
                  color: white;
                  border: none;
                  border-radius: 4px;
                  cursor: pointer;
                  font-size: 12px;
                "
              >
                Xoá
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { tourApi } from "@/api/tourApi";

const tours = ref([]);
const showForm = ref(false);
const editingTour = ref(null);
const saving = ref(false);

const form = ref({
  tourName: "",
  numberOfDay: 1,
  startPlace: "",
  endPlace: "",
  tourIntroduce: "",
});

onMounted(async () => {
  await loadTours();
});

async function loadTours() {
  try {
    const res = await tourApi.getAll();
    tours.value = res.data;
  } catch (e) {
    console.error(e);
  }
}

function handleEdit(tour) {
  editingTour.value = tour;
  form.value = {
    tourName: tour.tourName,
    numberOfDay: tour.numberOfDay,
    startPlace: tour.startPlace,
    endPlace: tour.endPlace,
    tourIntroduce: tour.tourIntroduce,
  };
  showForm.value = true;
}

async function handleSave() {
  saving.value = true;
  try {
    if (editingTour.value) {
      await tourApi.update(editingTour.value.tourId, form.value);
    } else {
      await tourApi.create(form.value);
    }
    await loadTours();
    resetForm();
    alert("Lưu thành công!");
  } catch (e) {
    alert("Lưu thất bại: " + (e.response?.data?.message || e.message));
  } finally {
    saving.value = false;
  }
}

async function handleDelete(tourId) {
  if (!confirm("Xoá tour này?")) return;
  try {
    await tourApi.remove(tourId);
    await loadTours();
    alert("Xoá thành công!");
  } catch (e) {
    alert("Xoá thất bại!");
  }
}

function resetForm() {
  showForm.value = false;
  editingTour.value = null;
  form.value = {
    tourName: "",
    numberOfDay: 1,
    startPlace: "",
    endPlace: "",
    tourIntroduce: "",
  };
}
</script>
