<template>
  <div>
    <h2 style="margin-bottom: 30px; color: #2c3e50">⚙️ Admin Dashboard</h2>

    <!-- Stats -->
    <div
      style="
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 20px;
        margin-bottom: 40px;
      "
    >
      <div
        v-for="stat in stats"
        :key="stat.label"
        :style="`background: ${stat.color}; padding: 25px; border-radius: 8px; color: white; text-align: center;`"
      >
        <div style="font-size: 32px; margin-bottom: 8px">{{ stat.icon }}</div>
        <div style="font-size: 28px; font-weight: bold; margin-bottom: 5px">
          {{ stat.value }}
        </div>
        <div style="font-size: 14px; opacity: 0.9">{{ stat.label }}</div>
      </div>
    </div>

    <!-- Tabs -->
    <div style="display: flex; gap: 10px; margin-bottom: 20px">
      <button
        v-for="tab in tabs"
        :key="tab.key"
        @click="activeTab = tab.key"
        :style="`padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px;
                background: ${activeTab === tab.key ? '#3498db' : '#ecf0f1'};
                color: ${activeTab === tab.key ? 'white' : '#666'};`"
      >
        {{ tab.label }}
      </button>
    </div>

    <!-- Tab: Users -->
    <div v-if="activeTab === 'users'">
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
                ID
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Họ tên
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Gmail
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Role
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
              v-for="user in users"
              :key="user.userId"
              style="border-top: 1px solid #f0f0f0"
            >
              <td style="padding: 12px; font-size: 14px">{{ user.userId }}</td>
              <td style="padding: 12px; font-size: 14px">
                {{ user.lastName }} {{ user.firstName }}
              </td>
              <td style="padding: 12px; font-size: 14px">{{ user.gmail }}</td>
              <td style="padding: 12px; font-size: 14px">
                {{ user.role?.roleName }}
              </td>
              <td style="padding: 12px">
                <span
                  :style="`padding: 3px 10px; border-radius: 20px; font-size: 12px;
                                    background: ${user.status === 1 ? '#efe' : '#fee'};
                                    color: ${user.status === 1 ? '#27ae60' : '#e74c3c'};`"
                >
                  {{ user.status === 1 ? "Active" : "Blocked" }}
                </span>
              </td>
              <td style="padding: 12px">
                <button
                  @click="toggleUser(user)"
                  :style="`padding: 5px 12px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px;
                                    background: ${user.status === 1 ? '#e74c3c' : '#27ae60'}; color: white;`"
                >
                  {{ user.status === 1 ? "Block" : "Unblock" }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Tab: Bookings -->
    <div v-if="activeTab === 'bookings'">
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
                Mã booking
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Khách hàng
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Tour
              </th>
              <th
                style="
                  padding: 12px;
                  text-align: left;
                  color: #666;
                  font-size: 13px;
                "
              >
                Tổng tiền
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
                Ngày đặt
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="booking in bookings"
              :key="booking.bookId"
              style="border-top: 1px solid #f0f0f0"
            >
              <td style="padding: 12px; font-size: 14px">
                {{ booking.bookCode }}
              </td>
              <td style="padding: 12px; font-size: 14px">
                {{ booking.firstName }} {{ booking.lastName }}
              </td>
              <td style="padding: 12px; font-size: 14px">
                {{ booking.departure?.tour?.tourName }}
              </td>
              <td
                style="
                  padding: 12px;
                  font-size: 14px;
                  color: #e74c3c;
                  font-weight: bold;
                "
              >
                {{ formatPrice(booking.totalPrice) }}
              </td>
              <td style="padding: 12px">
                <span
                  :style="`padding: 3px 10px; border-radius: 20px; font-size: 12px;
                                    background: ${booking.status === 1 ? '#efe' : '#fee'};
                                    color: ${booking.status === 1 ? '#27ae60' : '#e74c3c'};`"
                >
                  {{ booking.status === 1 ? "Confirmed" : "Cancelled" }}
                </span>
              </td>
              <td style="padding: 12px; font-size: 14px">
                {{ formatDate(booking.bookDate) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { adminApi } from "@/api/adminApi";

const activeTab = ref("users");
const tabs = [
  { key: "users", label: "👥 Quản lý User" },
  { key: "bookings", label: "📋 Quản lý Booking" },
];

const stats = ref([
  { label: "Tổng Users", value: 0, icon: "👥", color: "#3498db" },
  { label: "Tổng Tours", value: 0, icon: "🗺️", color: "#9b59b6" },
  { label: "Tổng Bookings", value: 0, icon: "📋", color: "#e67e22" },
  { label: "Doanh thu", value: "0đ", icon: "💰", color: "#27ae60" },
]);

const users = ref([]);
const bookings = ref([]);

onMounted(async () => {
  await loadStats();
  await loadUsers();
  await loadBookings();
});

async function loadStats() {
  try {
    const res = await adminApi.getStats();
    stats.value[0].value = res.data.totalUsers;
    stats.value[1].value = res.data.totalTours;
    stats.value[2].value = res.data.totalBookings;
    stats.value[3].value = formatPrice(res.data.totalRevenue);
  } catch (e) {
    console.error("Load stats error:", e);
  }
}

async function loadUsers() {
  try {
    const res = await adminApi.getAllUsers();
    users.value = res.data;
  } catch (e) {
    console.error("Load users error:", e.response?.status, e.response?.data);
  }
}

async function loadBookings() {
  try {
    const res = await adminApi.getAllBookings();
    bookings.value = res.data;
  } catch (e) {
    console.error("Load bookings error:", e.response?.status, e.response?.data);
  }
}

async function toggleUser(user) {
  if (
    !confirm(`${user.status === 1 ? "Block" : "Unblock"} user ${user.gmail}?`)
  )
    return;
  try {
    await adminApi.toggleUserStatus(user.userId);
    user.status = user.status === 1 ? 0 : 1;
  } catch (e) {
    alert("Thao tác thất bại!");
  }
}

function formatPrice(price) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price || 0);
}

function formatDate(date) {
  return date ? new Date(date).toLocaleDateString("vi-VN") : "";
}
</script>
