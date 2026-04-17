
### API Contracts

# 👤 User APIs
GET    /api/v1/users
POST   /api/v1/users
PUT    /api/v1/users/{userId}/disable

---

# 💻 Device APIs
GET    /api/v1/devices
GET    /api/v1/devices/{id}
POST   /api/v1/devices

---

# 🔄 Assignment APIs
POST   /api/v1/assignments
PUT    /api/v1/assignments/{assignmentId}/return

---

# 📊 Report APIs
GET    /api/v1/reports/average-device-age
GET    /api/v1/reports/user-devices
GET    /api/v1/reports/eol-devices