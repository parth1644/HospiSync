# 🏥 HospiSync — Intelligent Hospital Load Forecasting & Distribution System

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![License](https://img.shields.io/badge/license-MIT-green)

> A full-stack hospital management platform that enables real-time bed tracking, intelligent patient transfer recommendations, load forecasting, and inter-hospital coordination — built to reduce patient overflow and optimize healthcare resource distribution.

---

## 📌 Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Overview](#api-overview)
- [Screenshots](#screenshots)
- [Future Improvements](#future-improvements)

---

## 📖 About the Project

HospiSync is a web-based hospital coordination system designed to solve the critical problem of uneven patient distribution across hospitals. When one hospital is overloaded while another nearby is underutilized, HospiSync helps administrators find the best available hospital, initiate patient transfers, and track real-time bed availability — all from a single dashboard.

**Problem it solves:**
- Hospitals have no visibility into nearby hospitals' capacity
- Patient transfers are done manually without any smart recommendations
- No centralized system to forecast future patient load

**HospiSync addresses all of this** with an intelligent recommendation engine, real-time dashboards, and a statistical load forecasting model.

---

## ✨ Features

### 🔐 Authentication & Registration
- Hospital registration with Government Hospital ID verification
- Auto geolocation detection with interactive map (Leaflet.js)
- JWT-based secure login and session management
- Role-based access (Admin)

### 🏗️ Hospital Setup Wizard
- 3-step guided onboarding: Departments → Capacities → Finish
- Select from predefined departments: ICU, Daycare, General Ward, Child Care, Essential Care, Emergency, Cardiology, Neurology
- Add custom departments as needed
- Configure bed capacity per department

### 📊 Real-Time Dashboard
- Live stats: Total Beds, Occupied Beds, Available Beds, Occupancy Rate
- Hospital status indicator (Underutilized / Normal / Overloaded)
- Bed Occupancy Trend chart (last 24 hours)
- Bed Distribution donut chart
- Live map showing nearby registered hospitals
- Category-wise occupancy bar chart per department

### 🛏️ Bed Management
- View and update occupied beds per department in real time
- Edit/delete department configurations
- One-click "Update Bed Data" for quick updates

### 📈 Load Forecasting
- 7-day moving average model for patient admission prediction
- Tomorrow's expected patient count using statistical prediction
- Forecast Trend chart with historical vs. predicted data
- RMSE and MAE model evaluation metrics displayed

### 🤖 Smart Hospital Recommendations
- AI-powered nearby hospital search with scoring algorithm
- Filter by department-specific bed requirements (ICU, Daycare, General Ward, Child Care)
- Set maximum search distance (default: 25 km)
- Recommendation score based on available beds, distance, and utilization status
- Shows distance, travel time, available beds, and utilization status

### 🚑 Patient Transfer System
- Initiate patient transfer requests to recommended hospitals
- Incoming transfer request management (Approve / Reject)
- Transfer status tracking: Pending → Approved → Completed
- Full outgoing transfer history with timestamps

### 🔔 Notifications
- Real-time notification bell for transfer requests and system updates

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 21, Spring Boot 3.2.1 |
| **Security** | Spring Security, JWT (jjwt 0.12.6) |
| **Database** | MySQL, Spring Data JPA (Hibernate) |
| **Email** | Spring Boot Mail |
| **Scheduler** | Spring Scheduler (cron jobs) |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript |
| **Maps** | Leaflet.js (OpenStreetMap) |
| **Charts** | Chart.js |
| **Containerization** | Docker |
| **Build Tool** | Maven |
| **Utilities** | Lombok, Spring Validation |

---

## 📁 Project Structure

```
Hospisync-backend/
├── src/
│   └── main/
│       ├── java/hospital/Hospisync_backend/
│       │   ├── controller/       # REST API controllers
│       │   ├── dto/              # Data Transfer Objects
│       │   ├── model/            # JPA Entity classes
│       │   ├── repository/       # Spring Data JPA repositories
│       │   ├── scheduler/        # Cron jobs & scheduled tasks
│       │   ├── security/         # JWT filter, Spring Security config
│       │   ├── service/          # Business logic layer
│       │   └── utils/            # Helper/utility classes
│       └── resources/
│           ├── static/
│           │   ├── css/          # Stylesheets
│           │   └── js/           # JavaScript files
│           ├── dashboard.html    # Main dashboard
│           ├── index.html        # Login page
│           ├── register.html     # Hospital registration
│           ├── setup.html        # Department setup wizard
│           └── application.yaml  # App configuration
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- MySQL 8+
- Docker (optional)

### 1. Clone the Repository

```bash
git clone https://github.com/parth1644/HospiSync.git
cd HospiSync
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE hospisync;
```

Update `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hospisync
    username: your_mysql_username
    password: your_mysql_password
  jpa:
    hibernate:
      ddl-auto: update
  mail:
    host: smtp.gmail.com
    username: your_email@gmail.com
    password: your_app_password

jwt:
  secret: your_jwt_secret_key
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start at: `http://localhost:8080`

### 4. Run with Docker

```bash
docker build -t hospisync-backend .
docker run -p 8080:8080 hospisync-backend
```

---

## 🔌 API Overview

| Module | Endpoint Prefix | Description |
|---|---|---|
| Auth | `/api/auth/` | Register, Login, JWT |
| Hospital | `/api/hospital/` | Hospital CRUD, setup |
| Beds | `/api/beds/` | Bed management per department |
| Forecast | `/api/forecast/` | Load forecasting data |
| Recommendations | `/api/recommendations/` | Smart hospital search |
| Transfer | `/api/transfer/` | Patient transfer requests |
| Notifications | `/api/notifications/` | Notification management |

---

## 📸 Screenshots

### Real-Time Dashboard
Experience live monitoring of bed occupancy, trend analysis, and regional capacity maps.
![Dashboard Overview](./screenshots/dashboard.png)

### Secure Registration
Hospital onboarding with automated geolocation detection and verified Government ID tracking.
![Hospital Registration](./screenshots/register.png)

### Intelligent Setup Wizard
Customizable 3-step onboarding flow to define departments and exact bed capacities.
![Setup Wizard Step 1](./screenshots/setup_step1.png)
![Setup Wizard Step 2](./screenshots/setup_step2.png)

---

## 🔮 Future Improvements

- [ ] Add Swagger / OpenAPI documentation
- [ ] Mobile-responsive design
- [ ] Role-based access for Doctors and Nurses
- [ ] Real-time WebSocket notifications
- [ ] Integration with ambulance dispatch systems
- [ ] Advanced ML-based forecasting (replacing moving average)
- [ ] Multi-language support
- [ ] Export reports as PDF

---

## 👨‍💻 Author

**Parth** — Full Stack Developer  
📧 [GitHub Profile](https://github.com/parth1644)

---

## 📄 License

This project is licensed under the MIT License.

---

> ⭐ If you found this project useful, please consider giving it a star on GitHub!