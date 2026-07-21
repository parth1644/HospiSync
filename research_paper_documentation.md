# HospiSync — Research Paper Preparation Document

> **Document Type:** Research Paper Foundation Document  
> **Prepared By:** AI Research Documentation System  
> **Source:** Complete source code analysis of the HospiSync backend project  
> **Standard:** IEEE / Springer / Elsevier / Scopus publication guidelines  
> **Date Generated:** July 2026  

---

> [!IMPORTANT]
> All information in this document is **directly extracted** from the source code, README, and configuration files of the HospiSync project. Where information could not be confirmed from the source, it is explicitly marked as **"Information Required from Author"**.

---

## 1. Project Overview

### Project Title
**HospiSync: An Intelligent Hospital Load Forecasting and Inter-Hospital Patient Distribution System**

### Domain
- Healthcare Informatics
- Hospital Resource Management
- Distributed Health Systems
- Intelligent Decision Support Systems

### Problem Statement
Healthcare systems worldwide suffer from the critical problem of uneven patient distribution across hospital networks. When one hospital reaches overcapacity — resulting in deteriorating patient outcomes — nearby hospitals may simultaneously be operating well below their optimal utilization. This maldistribution arises due to the following systemic failures:

1. **No real-time inter-hospital visibility:** Hospital administrators have no centralized mechanism to view bed availability across nearby facilities.
2. **Manual, ad-hoc patient transfers:** Transfer decisions are made through phone calls, informal contacts, and manual coordination, causing dangerous delays in critical care scenarios.
3. **Absence of predictive capacity planning:** Hospitals cannot forecast patient load for the following day, making proactive resource management impossible.
4. **No automated escalation for rejected transfers:** When a receiving hospital rejects a transfer request, the sending hospital must restart the search manually with no system support.

### Motivation
The motivation for HospiSync arises from the well-documented global problem of hospital overcrowding and resource imbalance. Hospital overflow is associated with increased patient mortality, medical errors, staff burnout, and adverse clinical outcomes. Simultaneously, underutilized hospitals represent wasteful healthcare expenditure. A technology platform that creates a unified, intelligent coordination layer between hospitals — enabling data-driven, automated, time-sensitive patient transfers — has significant potential to address these challenges.

### Objectives
The primary objectives of the HospiSync system are:

1. To provide real-time, centralized visibility into bed occupancy across a network of registered hospitals.
2. To implement a scored, multi-factor hospital recommendation algorithm that identifies optimal transfer destinations considering distance, available beds, doctor availability, and utilization status.
3. To enable a structured, two-stage, time-bound patient transfer protocol with automated escalation on timeout or rejection.
4. To implement a statistical load forecasting model (7-day moving average) that predicts next-day patient admissions and triggers scarcity alerts when occupancy thresholds are exceeded.
5. To provide automated email notifications and in-app alerts for transfer events and data staleness.
6. To offer hospital administrators a guided setup wizard for configuring departments and bed capacities.
7. To enable doctor availability management integrated with the transfer confirmation workflow.

### Target Users
- **Primary:** Hospital Administrators (role: `HOSPITAL_ADMIN`) responsible for bed management, patient transfers, and operational oversight.
- **Secondary:** Healthcare network coordinators overseeing multi-hospital systems.
- **Tertiary:** Healthcare policy-makers and health system planners using aggregate data.

### Real-world Applications
- Public hospital networks in urban regions where multiple government hospitals serve overlapping catchment areas.
- Private hospital chains managing patient load across branches.
- Emergency medical services (EMS) requiring rapid identification of receiving hospitals.
- District or state health departments monitoring regional hospital capacity.

### Expected Impact
- Reduction in hospital overcrowding through intelligent, timely patient distribution.
- Reduction in time-to-transfer decision by replacing manual phone-based coordination with an algorithmic recommendation engine.
- Improved patient safety by ensuring that transfer requests reach confirmed receiving hospitals with assigned doctors within strict time limits (2-minute acknowledgement, 3-minute doctor confirmation).
- Reduced data staleness in hospital records through automated 24-hour reminder notifications.
- Proactive bed scarcity prevention through next-day forecasting alerts at ≥75% and ≥90% predicted occupancy thresholds.

---

## 2. Executive Summary

### What Problem It Solves
HospiSync addresses the critical operational challenge of uncoordinated patient distribution across hospital networks. Specifically, the system solves: (1) the absence of real-time cross-hospital bed visibility, (2) the inefficiency and danger of manual transfer coordination, (3) the lack of predictive capacity management, and (4) the absence of automated escalation when transfer requests fail.

### Why It Matters
Hospital overcrowding is a leading cause of preventable adverse patient events globally. According to health systems research, overcrowded emergency departments and wards are strongly associated with increased mortality, medication errors, and delayed care. A system that automates the identification of optimal transfer targets, enforces strict time-bound acknowledgement protocols, and escalates automatically when hospitals fail to respond represents a meaningful improvement to patient safety infrastructure.

### How the System Works
HospiSync is a full-stack, web-based hospital coordination platform with the following operational flow:

1. Hospitals register on the platform, providing their Government Hospital ID, geolocation (latitude/longitude), and administrative credentials.
2. Administrators complete a setup wizard to configure departments (e.g., ICU, General Ward, Emergency) and bed capacities.
3. The real-time dashboard continuously displays total beds, occupied beds, available beds, occupancy rate, and utilization status.
4. When a hospital is overloaded, the administrator uses the Recommendation Engine to search for nearby hospitals, filtered by department-specific bed requirements, distance, and doctor speciality.
5. A transfer request is initiated to the top-scored hospital. The receiving hospital has 2 minutes to acknowledge. If acknowledged, they have a further 3 minutes to assign a doctor and confirm. Failure at either stage triggers automatic escalation to the next available hospital.
6. Email notifications are dispatched at every transfer lifecycle event.
7. The statistical forecasting module computes a 7-day moving average of daily patient admissions to predict next-day patient load, computing RMSE and MAE for model evaluation.
8. Scheduled jobs run hourly to update doctor shift status and check for data staleness (>24 hours since last update).

### Technologies Used
Java 21, Spring Boot 3.2.1, Spring Security, JWT (JJWT 0.12.6), Spring Data JPA, Hibernate, MySQL, Spring Boot Mail, Spring Scheduler, HTML5, CSS3, Vanilla JavaScript, Leaflet.js, Chart.js, Docker, Maven, Lombok.

### Major Features
Authentication & Registration, Hospital Setup Wizard, Real-Time Dashboard, Bed Management, 7-Day Moving Average Load Forecasting, Multi-Factor Hospital Recommendation Engine (Haversine + Scoring), Two-Stage Timed Patient Transfer Protocol, Automatic Transfer Escalation, Doctor Availability Management, Automated Email Notifications, In-App Notification System, Scheduled Data Freshness Checks, Map Visualization.

### Expected Outcomes
A reduction in patient transfer decision time, improved load balancing across hospital networks, early warning on bed scarcity, and a replicable open-source architecture for hospital coordination systems.

---

## 3. Technical Stack

### Programming Languages
| Language | Version | Usage |
|---|---|---|
| Java | 21 | Backend business logic, REST APIs, scheduled tasks |
| JavaScript (ES6+) | N/A | Frontend logic, API integration, UI interactions |
| HTML5 | N/A | Web page structure |
| CSS3 | N/A | UI styling |
| Python | 3.x | Utility/data patching scripts (append_receiver_timer.py, append_sender.py, append_transfer.py, rewrite_transfer.py) |
| PowerShell | N/A | API testing scripts (test_api.ps1, test_auto_seed.ps1) |

### Frameworks
| Framework | Version | Purpose |
|---|---|---|
| Spring Boot | 3.2.1 | Application framework, auto-configuration |
| Spring Security | (via Spring Boot 3.2.1) | Authentication, authorization filter chain |
| Spring Data JPA | (via Spring Boot 3.2.1) | ORM, database abstraction |
| Spring Boot Mail | (via Spring Boot 3.2.1) | Email notification dispatch |
| Spring Scheduler | (via Spring Boot 3.2.1) | Cron jobs and fixed-rate tasks |
| Spring Validation | (via Spring Boot 3.2.1) | Bean validation (@Valid annotations) |

### Frontend
| Technology | Purpose |
|---|---|
| HTML5 | Page structure (index.html, register.html, dashboard.html, setup.html) |
| CSS3 / Vanilla CSS | Custom styling |
| Vanilla JavaScript | DOM manipulation, API calls, Chart rendering |
| Leaflet.js | Interactive map rendering with OpenStreetMap tiles |
| Chart.js | Bed occupancy charts, trend charts, donut charts, bar charts |

### Backend
| Technology | Purpose |
|---|---|
| Java 21 | Core language |
| Spring Boot 3.2.1 | Application server (embedded Tomcat) |
| Spring MVC (REST) | RESTful API layer |
| Spring Data JPA + Hibernate | Data persistence |
| Spring Security | Security filter chain |

### Database
| Technology | Detail |
|---|---|
| DBMS | MySQL 8+ |
| Database Name | `hospisync` |
| ORM | Hibernate (via Spring Data JPA) |
| DDL Strategy | `ddl-auto: update` (schema auto-update) |
| Driver | `com.mysql.cj.jdbc.Driver` |
| Default Dialect | `org.hibernate.dialect.MySQLDialect` |

### Libraries
| Library | Version | Purpose |
|---|---|---|
| Lombok | 1.18.30 | Boilerplate reduction (@Getter, @Setter, @Builder, @Data, @Slf4j) |
| Jackson (via Spring Boot) | N/A | JSON serialization/deserialization |
| SLF4J + Logback (via Spring Boot) | N/A | Application logging |

### Authentication
| Component | Detail |
|---|---|
| Mechanism | JSON Web Token (JWT) |
| Library | JJWT (io.jsonwebtoken) v0.12.6 |
| Algorithm | HMAC-SHA (Keys.hmacShaKeyFor) |
| Token Expiry | 86,400,000 ms (24 hours) |
| Claims Stored | email (subject), hospitalId, user_id, roles |
| Token Delivery | HTTP Authorization header as `Bearer <token>` |

### Security
| Component | Detail |
|---|---|
| Password Hashing | BCrypt (BCryptPasswordEncoder) |
| Session Management | Stateless (STATELESS SessionCreationPolicy) |
| CSRF | Disabled (JWT-based stateless API) |
| HTTP Basic Auth | Disabled |
| CORS | Configured via CorsConfigurationSource (AllowedOriginPatterns: *) |
| Request Authorization | JWT filter (JwtAuthFilter) before UsernamePasswordAuthenticationFilter |

### Cloud / Deployment
| Aspect | Detail |
|---|---|
| Containerization | Docker (Dockerfile present: Maven 3.9.9 + Eclipse Temurin JDK 21 base image) |
| Cloud Platform | Render (evidenced by PORT and CORS_ALLOWED_ORIGINS env vars in application.yaml) |
| ML Service URL | `${ML_SERVICE_URL:http://localhost:8000}` (configured but **Information Required from Author** — no active ML microservice found in current codebase) |

### Version Control
| Tool | Detail |
|---|---|
| VCS | Git |
| Repository | GitHub (github.com/sambodhit135/hospisync-backend) |

### Development Tools
| Tool | Purpose |
|---|---|
| Maven (mvnw 3.9+) | Build automation, dependency management |
| Spring Boot DevTools | Hot reload during development |
| IntelliJ IDEA (.idea directory present) | Primary IDE |
| VS Code (.vscode directory present) | Secondary editor |

### Testing Tools
| Tool | Status |
|---|---|
| Spring Boot Test (JUnit 5) | Dependency present in pom.xml (`spring-boot-starter-test`) |
| Spring Security Test | Dependency present (`spring-security-test`) |
| Actual test coverage | **Information Required from Author** — no test class files were found in `src/test` |
| PowerShell test scripts | Manual API test scripts (test_api.ps1, test_auto_seed.ps1) present at project root |
| Python test scripts | fetch_recommend_test.py present |

---

## 4. Existing System

### Current Solutions
Prior to platforms such as HospiSync, the following approaches were used for inter-hospital patient management:

1. **Manual telephone coordination:** When a hospital reached capacity, administrators would manually call neighbouring hospitals to inquire about availability. This process is time-consuming, error-prone, and dependent on personal relationships.
2. **Hospital Information Management Systems (HIMS):** Isolated within individual hospitals, HIMS platforms provide internal bed management but offer no inter-hospital communication or visibility.
3. **State/district health portals (India-specific context):** Some government health departments maintain static portals showing registered hospital bed counts. These portals are not real-time and do not provide transfer request functionality.
4. **WhatsApp/Email groups:** In practice, many hospital clusters share informal communication groups to broadcast availability — an entirely unstructured approach.

### Existing Workflow
1. A hospital's emergency department fills beyond capacity.
2. The on-duty doctor or administrator calls nearby hospitals one-by-one via phone to ask about bed availability.
3. If a receiving hospital confirms availability verbally, the transfer is arranged informally.
4. There is no digital record of the request, no time constraints on the response, no automated escalation, and no formal confirmation.

### Drawbacks
- No real-time data: Information is current only at the moment of a phone call.
- High latency in decision-making: Minutes or hours can be lost during manual coordination.
- No decision support: The sending hospital has no intelligence about which hospital to contact first.
- No accountability: Transfers leave no digital audit trail.
- No forecasting: Hospitals cannot predict when they will reach capacity and cannot proactively plan.

### Limitations
- Single-hospital scope of existing HIMS solutions.
- Dependence on individual human availability (e.g., administrator must be available to answer calls).
- No scoring mechanism to compare hospital suitability.
- No SLA enforcement (no timeouts or escalation if a hospital fails to respond).

### Challenges
- Data integration across heterogeneous hospital systems.
- Motivating hospital staff to update digital records in real time.
- Handling emergency transfer cases that require sub-minute decision cycles.
- Maintaining trust and adoption across competing healthcare providers.

---

## 5. Proposed System

### Complete Working
HospiSync is a centralized, web-based hospital coordination platform that provides a unified view of bed availability across registered hospitals and automates the patient transfer coordination process. The system operates as follows:

1. **Registration & Onboarding:** Hospitals register with a Government Hospital ID, email, contact details, and geolocation. A 3-step setup wizard guides them to configure departments and bed capacities.
2. **Live Dashboard:** Administrators view real-time metrics (total, occupied, available beds; occupancy rate; utilization status). Visual charts (trend chart, donut chart, bar chart) and a Leaflet.js map of the hospital network are displayed.
3. **Load Forecasting:** A 7-day moving average model processes daily admission counts to predict next-day patient load. RMSE and MAE are computed and displayed. Alerts are triggered at ≥75% and ≥90% predicted occupancy.
4. **Recommendation Engine:** When overflow is anticipated, the administrator searches for hospitals using the multi-factor scoring algorithm. The system ranks hospitals by a composite score = (availableBeds × 3) + doctorScore − (distance × 2) + utilizationBonus.
5. **Two-Stage Transfer Protocol:** A transfer request is created with a 2-minute Stage 1 deadline (acknowledgement). Upon acknowledgement, a 3-minute Stage 2 deadline (doctor assignment + confirmation) begins. Failure at either stage triggers automated escalation.
6. **Automated Escalation:** If a transfer fails, the system automatically creates a new transfer request to the next-best hospital (excluding all previously tried hospitals), using the recommendation engine.
7. **Doctor Management:** Each hospital manages its doctor roster with specialities, shift schedules, and patient load limits. Doctor availability is automatically updated hourly by the ShiftScheduler.
8. **Notification System:** Real-time in-app notifications and email alerts are dispatched for all transfer lifecycle events (request created, acknowledged, approved, rejected) and data staleness events.

### Features (see Section 14 for detailed table)
- JWT-secured registration and login
- Government Hospital ID verification (unique constraint)
- Geolocation-based hospital registration
- 3-step setup wizard
- Real-time bed occupancy dashboard
- 7-day moving average forecasting with RMSE/MAE
- Multi-factor scored recommendation engine
- Two-stage timed transfer protocol
- Automated escalation on timeout or rejection
- Doctor availability and shift management
- In-app and email notifications
- Data freshness enforcement (24-hour staleness alerts)
- Interactive hospital map (Leaflet.js)
- Docker containerization

### Advantages
- Eliminates manual phone-based transfer coordination.
- Enforces time constraints on transfer responses (SLA enforcement).
- Provides decision-support through a scored recommendation.
- Automates escalation, ensuring no transfer request is left unresolved without system action.
- Provides early warning through statistical forecasting.

### Improvements Over Existing Systems
| Dimension | Existing Systems | HospiSync |
|---|---|---|
| Visibility | Single-hospital, manual | Multi-hospital, real-time dashboard |
| Transfer Initiation | Manual phone calls | Structured digital request |
| Decision Support | None | Multi-factor scored recommendation |
| Escalation | Manual | Fully automated, with tried-hospital tracking |
| Forecasting | None | 7-day moving average with RMSE/MAE |
| SLA Enforcement | None | 2-min + 3-min timed stages |
| Notifications | None | Email + in-app |
| Audit Trail | None | Full transfer history with timestamps |

---

## 6. Research Gap

### Problems Not Solved by Existing Research
1. **Automated inter-hospital patient transfer with SLA enforcement:** Existing hospital management systems address internal bed tracking but do not provide a network-level, time-bounded transfer protocol with automatic escalation.
2. **Real-time multi-factor recommendation for patient transfer:** Most resource allocation models in literature address scheduling and routing separately; a unified scoring model combining distance (Haversine), bed availability, doctor availability, and utilization status has limited precedent in operational platforms.
3. **Integration of forecasting alerts with transfer recommendations:** Most forecasting studies in healthcare produce standalone predictions; HospiSync integrates the forecast output directly into the operational workflow (triggering scarcity alerts and informing transfer urgency).
4. **Doctor-integrated transfer confirmation workflow:** Transfer systems in literature rarely account for the doctor assignment step as a formal, time-constrained stage of the transfer protocol.
5. **Automatic escalation with tried-hospital tracking:** The problem of automatically navigating a hospital network upon repeated rejections or timeouts — while tracking all previously tried hospitals — is not commonly addressed in existing platforms.

### Problems Solved by This Project
- Real-time network-wide bed visibility for registered hospitals.
- Scored, ranked, and filterable hospital recommendation.
- Two-stage, time-bounded transfer protocol with digital tracking.
- Automated transfer escalation with JSON-tracked trial history.
- Statistical next-day patient load forecasting with threshold-based alerts.
- Automated doctor shift-aware availability management.

### Novel Contributions
1. **Two-Stage Timed Transfer Protocol with Automatic Escalation:** A structured handshake protocol with independent timeouts (Stage 1: 2 min acknowledgement; Stage 2: 3 min doctor confirmation) and a scheduler-driven escalation mechanism that tracks hospital-tried history as a JSON array — this combination is a novel operational contribution.
2. **Composite Hospital Scoring Algorithm:** A weighted formula combining available beds (×3), Haversine distance (×-2), doctor capacity score (×2, with presence/on-call bonus of 40/20 points), and utilization bonus (+15 for UNDERUTILIZED) provides a novel, multi-dimensional hospital ranking for patient transfer purposes.
3. **Split Transfer Planning:** When no single hospital can satisfy all bed requirements, the system generates an allocation plan distributing patients across multiple hospitals — a novel operational feature not commonly found in related platforms.
4. **Statistical Forecasting Integrated with Operational Alerts:** The 7-day MA model directly produces in-app notifications and scarcity alerts tied to occupancy thresholds (≥75%, ≥90%), integrating predictive output into the operational dashboard.

### Innovation
- The use of a **JSON-serialized tried-hospital tracking list** within the Transfer entity (`hospitalsTried`) for deduplication during escalation is a pragmatic engineering innovation.
- **Deduplicated notification generation:** The system checks for existing unread notifications of the same type before creating new ones, updating trigger count and timestamp instead — reducing notification noise.
- **Shift-aware doctor availability:** The hourly ShiftScheduler automatically transitions doctors between PRESENT, ON_CALL, and OFF_DUTY states based on shift configuration, removing the need for manual status updates.

### Suggested Additional Research Contributions (if novelty needs strengthening)
1. Comparative study of scoring algorithms for hospital recommendation (weighted scoring vs. machine learning-based ranking).
2. Formal evaluation of the two-stage timed protocol against a control group using manual coordination.
3. Analysis of escalation chain length distribution (how many hops are required on average before a transfer is confirmed?).
4. Simulation study of the forecasting model accuracy using real patient admission datasets.
5. User study measuring administrator satisfaction and decision time reduction compared to manual coordination.

---

## 7. Detailed Methodology

### Module 1: Authentication & Registration Module

**Purpose:** To securely register hospitals and manage user sessions using JWT-based stateless authentication.

**Inputs:**
- Registration: `hospitalName`, `govId` (unique), `email`, `password`, `contactNumber`, `address`, `latitude`, `longitude`
- Login: `email`, `password`

**Processing:**
- On registration: The email uniqueness is validated (`userRepository.existsByEmail`). The hospital record is created and the user record is created with a BCrypt-hashed password. A JWT token is generated containing `email`, `hospitalId`, `userId`, and `role`.
- On login: The user is retrieved by email. The submitted password is verified against the BCrypt hash. A new JWT is generated.
- JWT contains claims: subject (email), hospitalId (Long), user_id (Long), roles (List<String>). Token expiry: 24 hours.
- The `JwtAuthFilter` intercepts every HTTP request (except public endpoints), extracts the `Authorization: Bearer <token>` header, validates the token, and populates the Spring Security context.

**Outputs:**
- `AuthResponse`: JWT token, email, hospital ID, hospital name, setupCompleted flag.

**Algorithms Used:**
- BCrypt password hashing.
- HMAC-SHA JWT signing (Keys.hmacShaKeyFor).

**Advantages:** Stateless authentication removes server-side session storage. BCrypt provides computationally expensive password hashing resistant to brute force.

**Limitations:** Token revocation is not supported (no token blacklist). A compromised token remains valid until expiry (24 hours).

---

### Module 2: Hospital Setup & Configuration Module

**Purpose:** To guide new hospitals through a 3-step onboarding wizard for configuring departments and bed capacities.

**Inputs:**
- `SetupRequest`: `hospitalId`, list of `SetupItem` objects (name, icon, beds).

**Processing:**
- Any existing `BedCategory` records for the hospital are deleted (clean slate).
- New `BedCategory` records are created for each department submitted (ICU, Daycare, General Ward, Child Care, Essential Care, Emergency, Cardiology, Neurology, or custom).
- The hospital's `setupCompleted` flag is set to `true`.

**Outputs:**
- Hospital record updated with `setupCompleted = true`.
- BedCategory records created for each department.

**Algorithms Used:** None (CRUD operation).

**Advantages:** Ensures hospitals cannot access transfer and recommendation features without completing configuration. Custom departments allow flexibility beyond the 8 predefined types.

**Limitations:** The setup can only be completed once in the current implementation (re-doing setup deletes all existing categories, which would also discard historical bed data associations).

---

### Module 3: Real-Time Dashboard Module

**Purpose:** To provide administrators with an instant overview of their hospital's operational status.

**Inputs:**
- `hospitalId` (from path variable).

**Processing:**
- Retrieves all `BedCategory` records for the hospital.
- Computes: `totalBeds` = sum of `totalCapacity`; `occupiedBeds` = sum of `occupiedBeds`; `availableBeds` = max(0, totalBeds - occupiedBeds); `occupancyRate` = (occupiedBeds / totalBeds) × 100.
- Determines `utilizationStatus`: OVERUTILIZED (≥85%), MODERATE (≥60%), UNDERUTILIZED (<60%).
- Computes `lastUpdatedAgo` string representation (e.g., "2 hours ago").

**Outputs:**
- `DashboardResponse`: hospitalId, name, totalBeds, occupiedBeds, availableBeds, occupancyRate, utilizationStatus, categories (per-department breakdown), lastUpdated, lastUpdatedAgo.

**Algorithms Used:** Arithmetic aggregation.

**Advantages:** Provides actionable operational status in a single API call. The utilization status thresholds (60%, 85%) enable quick visual classification.

**Limitations:** The 24-hour data freshness check is separate from the dashboard; stale data is not flagged inline on the dashboard API response.

---

### Module 4: Bed Management Module

**Purpose:** To allow administrators to view, update, add, and delete department-level bed records.

**Inputs:**
- GET: `hospitalId`
- POST/PUT: `hospitalId`, `categoryId` (for PUT/DELETE), `BedCategoryRequest` (categoryName, totalCapacity, occupiedBeds)
- DELETE: `hospitalId`, `categoryId`

**Processing:**
- `getCategories`: Retrieves all BedCategory records for the hospital sorted by `categoryId`.
- `addCategory`: Validates that the category name is unique per hospital. Creates a new BedCategory record.
- `updateCategory`: Validates that `occupiedBeds` does not exceed `totalCapacity`. Updates the record and sets `hospital.lastUpdated`.
- `deleteCategory`: Removes the category; prevents deletion if it would leave the hospital with zero categories.
- `updateOccupiedBeds` (via BedUpdateRequest): Batch updates for multiple categories simultaneously.

**Outputs:**
- BedCategory list or confirmation response.

**Algorithms Used:** Validation logic (boundary checks for bed counts).

**Advantages:** Real-time updates allow the occupancy status visible on the dashboard and recommendation engine to reflect current conditions.

**Limitations:** No historical audit trail of individual bed count changes is maintained within the `BedCategory` table (historical data is tracked separately via `BedRecord` and `PatientAdmission` tables).

---

### Module 5: Load Forecasting Module

**Purpose:** To predict next-day patient admission count and trigger proactive alerts based on projected occupancy.

**Inputs:**
- `hospitalId`
- Last 7 days of daily patient admission counts (from `patient_admissions_v2` table, queried via `PatientAdmissionRepository.findDailyCountsLast7Days`)

**Processing:**
1. Fetches daily admission counts for the last 7 days from the database.
2. Computes the 7-day Simple Moving Average (SMA): `predictedPatients = sum(daily_counts) / count`.
3. Computes evaluation metrics:
   - MAE = Σ|actual - predicted| / n
   - RMSE = √(Σ(actual - predicted)² / n)
4. Determines scarcity alert level based on `predictedOccupancy / totalCapacity × 100`:
   - ≥90%: CRITICAL alert (creates/updates SCARCITY_WARNING notification).
   - ≥75%: WARNING alert.

**Outputs:**
- `ForecastResponse`: `hospitalId`, `predictedPatients`, `method` (e.g., "7-day Moving Average"), `dataPointsUsed`, `historicalData` (list of DataPoint), `modelUsed` ("STATISTICAL_MA"), `mae`, `rmse`, `scarcityAlert` (boolean), `alertMessage`.

**Algorithms Used:** 7-Day Simple Moving Average, MAE, RMSE.

**Advantages:** Computationally inexpensive, interpretable, and deterministic. Does not require an external ML service. Deduplication of scarcity notifications prevents alert fatigue.

**Limitations:** The SMA does not account for seasonality, trends, weekday/weekend patterns, or external events. For hospitals with fewer than 7 days of historical data, the window is shortened.

---

### Module 6: Hospital Recommendation Engine

**Purpose:** To identify and rank the best available hospitals for patient transfer, based on multiple weighted criteria.

**Inputs:**
- `hospitalId` (source hospital)
- `maxDistance` (km, default 50 km)
- `speciality` (optional doctor speciality filter)
- `req-<category>` parameters (bed requirements per category, e.g., `req-ICU=3`)
- `excludeHospitalIds` (list of hospital IDs already tried, for escalation)

**Processing:**
1. Retrieves all registered hospitals except the source.
2. Excludes hospitals in `excludeHospitalIds`.
3. Computes Haversine distance for each candidate hospital.
4. Filters hospitals beyond `maxDistance` (default 50 km, user-configurable to 25 km in UI).
5. Applies **strict bed filtering**: hospitals with zero matching beds for the explicitly requested categories are excluded entirely.
6. If `speciality` is specified, filters hospitals to only those with available (non-OFF_DUTY) doctors of that speciality.
7. Computes composite score per hospital:
   - `bedScore = totalAvailableBeds × 3`
   - `distanceScore = -(distance × 2)`
   - `utilizationBonus = +15 if UNDERUTILIZED, else 0`
   - `doctorScore = (doctorRemainingCapacity × 2) - (doctorCurrentLoad × 1) + 40 (PRESENT) or +20 (ON_CALL)`
   - `totalScore = bedScore + doctorScore + distanceScore + utilizationBonus`
8. Sorts candidates by descending score.
9. Returns hospitals satisfying all requirements as "single match" recommendations (up to 10).
10. If no single hospital satisfies all requirements, generates a **split transfer plan** distributing patient counts across multiple hospitals.

**Outputs:**
- List of `RecommendationResponse`: hospital details, score, distance, estimated travel time, available beds, occupancy rate, utilization status, best available doctor name/speciality/remaining capacity, `maxTransferablePatients`, `isNearCapacity`, `capacityWarning`, `splitTransferPlan`.

**Algorithms Used:** Haversine formula for geographic distance, composite weighted scoring function.

**Advantages:** Multi-dimensional ranking. Doctor availability is directly integrated. Split transfer planning handles the case where no single hospital has sufficient capacity.

**Limitations:** The scoring weights (3, 2, 15, 2, 40, 20) are empirically chosen and not validated against clinical outcomes data. Travel time is estimated assuming a fixed speed of 40 km/h.

---

### Module 7: Patient Transfer Management Module (Two-Stage Protocol)

**Purpose:** To manage the complete lifecycle of patient transfer requests with time-bound stages and automatic escalation.

**Inputs (Transfer Creation):**
- `fromHospitalId`, `toHospitalId`, `patientCount`, `bedAllocations` (Map<String, Integer>), `priority` (EMERGENCY / CRITICAL / NORMAL)

**Processing (Transfer Lifecycle):**

**Stage 1 — PENDING (acknowledgeBy = createdAt + 2 min):**
- Transfer created; receiving hospital notified via in-app notification and email.
- Hospital B must call `PUT /api/transfer/{id}/acknowledge` within 2 minutes.
- If timeout: `TransferEscalationScheduler` (runs every 30 seconds) sets stage to `TIMEOUT_STAGE1`, calls `escalateToNextHospital()`.

**Stage 2 — ACKNOWLEDGED (confirmBy = acknowledgedAt + 3 min):**
- Hospital B confirms by assigning a doctor via `PUT /api/transfer/{id}/confirm` with `doctorId`.
- Bed occupancy at the receiving hospital is updated immediately on confirmation.
- If timeout: stage set to `TIMEOUT_STAGE2`, escalation triggered.

**Escalation Logic:**
- Parses `hospitalsTried` JSON array to retrieve IDs of hospitals already tried.
- Adds current `toHospital.id` to tried list.
- Searches remaining candidates for a hospital with sufficient beds.
- Creates a new Transfer to the next candidate with `attemptNumber + 1`.
- If no candidates remain: creates `WARNING` notification "No available hospital found."

**Rejection:**
- Hospital B calls `PUT /api/transfer/{id}/reject`.
- Stage set to `REJECTED`; escalation triggered immediately.

**Completion:**
- Hospital A calls status update with `COMPLETED` for an `APPROVED` transfer.

**Outputs:**
- Transfer entity with stage, status, timestamps, assigned doctor ID.
- Notifications and emails dispatched at each transition.

**Algorithms Used:** State machine transition logic; BFS-style escalation over the hospital network.

**Advantages:** Enforces strict SLAs. Automated escalation removes the burden from the sending hospital. Complete digital audit trail.

**Limitations:** The 2-minute and 3-minute timeouts are hardcoded, not configurable per hospital or transfer priority. Split transfer escalation (where transfer is split across multiple hospitals and all must be tracked simultaneously) is not currently supported.

---

### Module 8: Doctor Management Module

**Purpose:** To maintain a roster of doctors per hospital, with speciality, shift schedule, patient load, and availability status, integrated into the transfer confirmation workflow.

**Inputs:**
- Doctor record: `name`, `email`, `phone`, `speciality`, `qualification`, `experienceYears`, `safeLimit` (default 12), `shiftStart`, `shiftEnd`, `workDays`.

**Processing:**
- `ShiftScheduler` runs every hour.
- For each doctor: checks if the current day matches `workDays` and if the current time is within `[shiftStart, shiftEnd]`.
- Sets `availabilityType` to `PRESENT` (on shift) or `OFF_DUTY` (off shift); updates `isAvailable` flag.
- `ON_CALL` status is preserved if manually set (i.e., not overwritten by the scheduler).

**Outputs:**
- Doctor availability status updated in database.
- Recommendation Engine uses doctor availability and remaining capacity (safeLimit - currentPatientCount) in the scoring formula.

**Algorithms Used:** Time-based shift matching.

**Advantages:** Removes manual status management. Integration with the recommendation engine and transfer confirmation workflow ensures doctors are assigned only when available and within safe patient limits.

**Limitations:** `ON_CALL` must be set manually and is not automatically transitioned. There is no mechanism for a doctor to self-update their status through the platform.

---

### Module 9: Notification Module

**Purpose:** To deliver real-time in-app alerts and email notifications for system events.

**Inputs:**
- Triggered by: transfer lifecycle events, data freshness check (>24 hours stale), scarcity alerts (forecasting module).

**Processing:**
- `NotificationService.createNotification()` creates a `Notification` record linked to the target hospital.
- **Deduplication:** Before creating a new notification of type `BED_UPDATE_WARNING` or `SCARCITY_WARNING`, the system checks for an existing unread notification of the same type. If found, it increments `triggerCount` and updates `lastTriggered` instead of creating a new record.
- `EmailService` sends `SimpleMailMessage` emails via SMTP (Gmail) using Spring Boot Mail.

**Outputs:**
- `Notification` database record.
- Email dispatched to hospital administrator.

**Algorithms Used:** Deduplication via database lookup.

**Advantages:** Deduplication reduces notification fatigue. Email + in-app dual channel increases reliability.

**Limitations:** Email delivery depends on external SMTP availability. No push notifications or WebSocket-based real-time delivery to the browser. The frontend must poll `/api/notifications/{hospitalId}` to check for new notifications.

---

### Module 10: Scheduling Module

**Purpose:** To execute automated background tasks on a scheduled basis.

**Schedulers:**

| Scheduler | Frequency | Function |
|---|---|---|
| `TransferEscalationScheduler` | Every 30 seconds (`fixedDelay=30000`) | Checks for Stage 1 (PENDING) and Stage 2 (ACKNOWLEDGED) transfers that have passed their deadline; triggers escalation |
| `DataFreshnessScheduler` | Every 1 hour (`fixedRate=3600000`) | Finds hospitals with `lastUpdated` older than 24 hours; sends email + in-app reminder |
| `ShiftScheduler` | Every 1 hour (`fixedDelay=3600000`) | Updates doctor availability based on shift schedule |
| `PatientEscalationScheduler` | **Information Required from Author** — file exists but content not fully reviewed |
| `DataSeeder` | **Information Required from Author** — startup seeder; full content not reviewed |

---

## 8. System Architecture

### Architecture Overview
HospiSync follows a **monolithic full-stack architecture** with an embedded frontend (HTML/CSS/JS served as static resources from Spring Boot) and a RESTful backend. The database is an external MySQL instance.

### Component Description

```
┌─────────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                                 │
│  Browser (Chrome/Firefox/Edge)                                        │
│  ┌───────────────────────────────────────────────────────────────┐  │
│  │ index.html  register.html  setup.html  dashboard.html          │  │
│  │ Leaflet.js (Maps)   Chart.js (Charts)   Vanilla JS (Logic)     │  │
│  └───────────────────────────────────────────────────────────────┘  │
└───────────────────────────┬─────────────────────────────────────────┘
                            │ HTTP/REST (JSON)
                            │ Authorization: Bearer <JWT>
┌───────────────────────────▼─────────────────────────────────────────┐
│                      SPRING BOOT APPLICATION                          │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  Security Layer                                               │   │
│  │  JwtAuthFilter → SecurityConfig (STATELESS, BCrypt, CORS)    │   │
│  └──────────────────────────────────────────────────────────────┘   │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  Controller Layer (REST API)                                  │   │
│  │  AuthController  HospitalController  BedCategoryController    │   │
│  │  ForecastController  RecommendationController                 │   │
│  │  TransfersController  NotificationController                  │   │
│  │  DoctorController  PatientController                          │   │
│  └──────────────────────────────────────────────────────────────┘   │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  Service Layer (Business Logic)                               │   │
│  │  AuthService  HospitalService  BedCategoryService             │   │
│  │  ForecastService  RecommendationService  TransferService       │   │
│  │  NotificationService  EmailService  DoctorService             │   │
│  │  PatientService  PatientRecommendService                      │   │
│  └──────────────────────────────────────────────────────────────┘   │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  Scheduler Layer (Background Tasks)                           │   │
│  │  TransferEscalationScheduler (30s)                            │   │
│  │  DataFreshnessScheduler (1h)  ShiftScheduler (1h)             │   │
│  │  PatientEscalationScheduler  DataSeeder                       │   │
│  └──────────────────────────────────────────────────────────────┘   │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  Repository Layer (Spring Data JPA)                           │   │
│  │  HospitalRepository  BedCategoryRepository  TransferRepository│   │
│  │  UserRepository  DoctorRepository  NotificationRepository     │   │
│  │  BedRecordRepository  PatientAdmissionRepository              │   │
│  │  ForecastResultRepository  PatientRequestRepository           │   │
│  └──────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬──────────────────┬──────────────────────┘
                            │                  │
          ┌─────────────────▼──┐    ┌──────────▼──────────┐
          │   MySQL Database    │    │   Gmail SMTP Server  │
          │   (hospisync)       │    │   (Email Alerts)     │
          └────────────────────┘    └─────────────────────┘
```

### Architecture Step-by-Step

1. **Client makes an HTTP request** (e.g., login, fetch dashboard) to the Spring Boot server running on port 8080 (or configured PORT env var).
2. **JwtAuthFilter** intercepts the request. For protected endpoints, it extracts the `Authorization: Bearer <token>` header, calls `JwtUtil.validateToken()`, and if valid, loads the security context with the hospital's email/ID.
3. **Request is routed** by Spring MVC to the appropriate `@RestController`.
4. **Controller** validates input (where `@Valid` is applied) and delegates to the corresponding `@Service`.
5. **Service** executes business logic — querying `Repository` interfaces (which translate to JPA/Hibernate SQL queries against MySQL), applying algorithms (Haversine, SMA, scoring), and calling other services.
6. **Response** is serialized to JSON by Jackson and returned via HTTP.
7. **Scheduled tasks** run independently on their defined schedules in background threads, executing business logic without an HTTP trigger.
8. **Frontend** receives JSON responses, renders charts (Chart.js), maps (Leaflet.js), and updates the DOM accordingly.

### External APIs / Services
| Service | Purpose |
|---|---|
| OpenStreetMap (via Leaflet.js CDN) | Tile provider for the hospital map visualization |
| Gmail SMTP | Email delivery via Spring Boot Mail |
| ML Service URL (`http://localhost:8000`) | Configured but no active ML microservice found — **Information Required from Author** |

---

## 9. Workflow

### Complete Execution Flow

**Step 1 — Hospital Registration**
- Administrator navigates to `register.html`.
- Fills in hospital details; browser detects geolocation via browser API (Leaflet.js used for map display and coordinate selection).
- Submits `POST /api/auth/register` → JWT token returned.
- `setupCompleted = false` → redirect to `setup.html`.

**Step 2 — Department Setup**
- Administrator completes 3-step wizard: (a) Select/add departments, (b) Set bed capacities per department, (c) Confirm.
- Submits `POST /api/hospital/setup-complete` → `setupCompleted = true`.
- Redirect to `dashboard.html`.

**Step 3 — Dashboard View**
- Frontend calls `GET /api/hospital/{id}/dashboard` → renders bed stats, occupancy rate, utilization status.
- Frontend calls `GET /api/hospital/map-data` → renders Leaflet.js map with all registered hospitals.
- Frontend calls `GET /api/forecast/{id}` → renders forecast chart, predicted patients, RMSE, MAE.
- Frontend calls `GET /api/notifications/{id}` (via polling) → renders notification bell badge.

**Step 4 — Bed Update**
- Administrator updates occupied beds via dashboard.
- Calls `PUT /api/bed-categories/{hospitalId}/{categoryId}` → occupiedBeds updated; `lastUpdated` refreshed.

**Step 5 — Find Transfer Destination**
- Administrator opens recommendation panel.
- Selects department requirements (e.g., ICU: 3, General Ward: 5), max distance, speciality.
- Frontend calls `GET /api/recommend/{hospitalId}?req-ICU=3&req-General Ward=5&maxDistance=25`.
- Backend runs Haversine + scoring → returns ranked list with scores, distances, travel times, doctor info.
- Administrator selects the top-scored hospital.

**Step 6 — Initiate Transfer**
- Administrator calls `POST /api/transfer/request` with `fromHospitalId`, `toHospitalId`, `patientCount`, `bedAllocations`, `priority`.
- Backend validates: no duplicate active transfer; bed capacity at receiving hospital; source ≠ destination.
- Transfer created with stage=PENDING; `acknowledgeBy = now + 2 min`.
- Email + in-app notification sent to receiving hospital.

**Step 7 — Receiving Hospital Acknowledges (Stage 1)**
- Receiving hospital administrator sees notification.
- Calls `PUT /api/transfer/{id}/acknowledge` (within 2 minutes).
- Stage → ACKNOWLEDGED; `confirBy = acknowledgedAt + 3 min`.
- Sending hospital notified: "Hospital B is checking doctor availability."

**Step 8 — Receiving Hospital Confirms with Doctor (Stage 2)**
- Receiving administrator selects an available doctor from their roster.
- Calls `PUT /api/transfer/{id}/confirm` with `doctorId` (within 3 minutes).
- Transfer stage → APPROVED; bed occupancy updated at receiving hospital; `assignedDoctorId` set.
- Sending hospital notified: "Transfer APPROVED — Dr. X assigned."
- Email confirmation sent.

**Step 9 — Transfer Completion**
- Once patients physically arrive, sending hospital marks the transfer COMPLETED via `PUT /api/transfer/{id}/status` with `status: COMPLETED`.

**Step 10 — Automated Escalation (if Stage 1 or Stage 2 times out)**
- `TransferEscalationScheduler` runs every 30 seconds.
- Finds PENDING transfers past `acknowledgeBy` → sets `TIMEOUT_STAGE1`; calls `escalateToNextHospital()`.
- Finds ACKNOWLEDGED transfers past `confirmBy` → sets `TIMEOUT_STAGE2`; calls `escalateToNextHospital()`.
- Escalation: current hospital added to `hospitalsTried`; next-best hospital selected; new Transfer created with `attemptNumber + 1`.
- If no hospitals remain → WARNING notification: "No available hospital found."

---

## 10. Algorithms Used

### Algorithm 1: Haversine Formula (Geographic Distance Calculation)

**Purpose:** Calculate the great-circle distance between two hospitals given their latitude/longitude coordinates.

**Why Selected:** Hospitals are geographically dispersed. Euclidean distance is inaccurate for geo-coordinates. Haversine correctly accounts for Earth's curvature and is computationally efficient for the distances involved (<100 km).

**Mathematical Explanation:**
```
Given:
  lat1, lon1 = latitude/longitude of source hospital (in degrees)
  lat2, lon2 = latitude/longitude of target hospital (in degrees)
  R = 6371.0 km (Earth's mean radius)

Steps:
  Δlat = toRadians(lat2 - lat1)
  Δlon = toRadians(lon2 - lon1)

  a = sin²(Δlat/2) + cos(lat1_rad) × cos(lat2_rad) × sin²(Δlon/2)

  c = 2 × atan2(√a, √(1-a))

  distance = R × c   (in km)
```

**Time Complexity:** O(1) — constant number of mathematical operations.

**Space Complexity:** O(1).

**Advantages:** Accurate for distances on Earth's surface. No external library or API required (pure math).

**Limitations:** Does not account for road network topology or actual travel routes. Travel time is approximated by `distance / 40 km/h × 60 min`.

**Possible Alternatives:** Google Maps Distance Matrix API (provides actual road distance and traffic-aware travel time), OSRM (Open Source Routing Machine), straight-line Euclidean distance (less accurate).

---

### Algorithm 2: Composite Hospital Scoring Algorithm

**Purpose:** Rank candidate hospitals for patient transfer based on multiple weighted criteria.

**Why Selected:** A single criterion (e.g., distance alone or beds alone) is insufficient for optimal transfer decisions. The composite scoring allows simultaneous optimization across patient safety (beds), access (distance), doctor readiness, and system load.

**Mathematical Explanation:**
```
Score = (availableBeds × 3) + doctorScore - (distance × 2) + utilizationBonus

Where:
  availableBeds = totalCapacity - occupiedBeds (across all categories)
  distance = Haversine distance in km

  doctorScore (if speciality requested):
    = (doctorRemainingCapacity × 2) - (doctorCurrentLoad × 1)
    + 40 (if PRESENT) or +20 (if ON_CALL)
    doctorRemainingCapacity = safeLimit - currentPatientCount

  utilizationBonus = 15 if utilizationStatus == "UNDERUTILIZED", else 0

  utilizationStatus:
    OVERUTILIZED  if occupancyRate ≥ 85%
    MODERATE      if occupancyRate ≥ 60%
    UNDERUTILIZED if occupancyRate < 60%
```

**Time Complexity:** O(n) where n = number of registered hospitals (excluding source).

**Space Complexity:** O(n).

**Advantages:** Transparent, interpretable, and configurable. Directly encodes clinical priorities (available beds > distance > utilization).

**Limitations:** Weight coefficients (3, 2, 15, 2, 40, 20) are heuristically assigned without empirical validation. Does not account for hospital specialization levels, patient acuity match, or ambulance availability.

**Possible Alternatives:** Machine learning-based ranking (e.g., learning-to-rank with gradient boosted trees), multi-objective optimization (Pareto-front approaches), Analytic Hierarchy Process (AHP).

---

### Algorithm 3: 7-Day Simple Moving Average (Patient Load Forecasting)

**Purpose:** Predict the expected number of patient admissions for the next day.

**Why Selected:** SMA is computationally inexpensive, requires no ML infrastructure, is easily explainable to clinical staff, and is appropriate when historical data is limited (e.g., a newly registered hospital with only a few days of records).

**Mathematical Explanation:**
```
Given:
  x₁, x₂, ..., xₙ = daily patient admission counts for last n days (max 7)

Predicted patients for tomorrow:
  P = (1/n) × Σᵢ₌₁ⁿ xᵢ

Evaluation metrics:
  MAE  = (1/n) × Σᵢ₌₁ⁿ |xᵢ - P|
  RMSE = √((1/n) × Σᵢ₌₁ⁿ (xᵢ - P)²)

Scarcity alert thresholds:
  Predicted occupancy rate = (P / totalCapacity) × 100
  ≥ 90%: CRITICAL alert
  ≥ 75%: WARNING alert
```

**Time Complexity:** O(n), n ≤ 7.

**Space Complexity:** O(n).

**Advantages:** No hyperparameter tuning. No training data requirements. Automatically adapts window size when fewer than 7 days of data are available.

**Limitations:** Does not model trend, seasonality, or external factors (holidays, epidemics). Equal weight given to all historical days regardless of recency. Unsuitable for hospitals with highly variable admission patterns.

**Possible Alternatives:** Exponentially Weighted Moving Average (EWMA), ARIMA, SARIMA, Facebook Prophet, LSTM-based deep learning forecasting.

---

### Algorithm 4: Split Transfer Planning Algorithm

**Purpose:** When no single hospital can satisfy all patient transfer requirements, distribute patients across multiple hospitals.

**Why Selected:** Patient overflow may involve multiple bed categories (e.g., 3 ICU + 5 General Ward). If no single hospital can accommodate all categories, a split plan prevents the entire transfer from failing.

**Mathematical Explanation:**
```
Inputs:
  requirements = {category: count}       (e.g., {ICU: 3, General: 5})
  hospitals = ranked list by composite score

Algorithm:
  remainingRequirements = copy(requirements)
  plan = []

  For each hospital H in ranked order:
    if all remainingRequirements are satisfied: break
    allocationsForH = {}
    For each (category, needed) in remainingRequirements:
      avail = H.getAvailableBedsByCategory(category)
      allocate = min(avail, needed)
      allocationsForH[category] = allocate
      remainingRequirements[category] -= allocate
    if allocationsForH is not empty:
      plan.append({hospital: H, allocations: allocationsForH})

  Attach plan to top-ranked hospital's response object
```

**Time Complexity:** O(h × c) where h = hospitals, c = distinct bed categories.

**Advantages:** Maximizes the probability of accommodating all patients even when no single hospital is sufficient.

**Limitations:** Executing a split transfer requires coordination across multiple hospitals simultaneously, which the current UI and backend transfer creation endpoint does not fully support as a single atomic operation.

---

## 11. Database Design

### Database Name
`hospisync`

### Tables

#### Table 1: `users`
| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `email` | VARCHAR | NOT NULL, UNIQUE |
| `password` | VARCHAR | NOT NULL (BCrypt hash) |
| `role` | VARCHAR | NOT NULL (HOSPITAL_ADMIN) |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id (OneToOne, CASCADE ALL) |
| `created_at` | DATETIME | Auto-set on @PrePersist |

#### Table 2: `hospitals`
| Column | Type | Constraints |
|---|---|---|
| `hospital_id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_name` | VARCHAR | NOT NULL |
| `gov_id` | VARCHAR | NOT NULL, UNIQUE |
| `email` | VARCHAR | NOT NULL |
| `contact_number` | VARCHAR | — |
| `address` | TEXT | — |
| `latitude` | DOUBLE | NOT NULL |
| `longitude` | DOUBLE | NOT NULL |
| `last_updated` | DATETIME | Auto-set on @PrePersist, @PreUpdate |
| `setup_completed` | BIT(1) | NOT NULL, DEFAULT 0 |
| `created_at` | DATETIME | NOT NULL, NOT UPDATABLE |

#### Table 3: `bed_categories`
| Column | Type | Constraints |
|---|---|---|
| `category_id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id |
| `category_name` | VARCHAR | NOT NULL |
| `icon` | VARCHAR | NOT NULL, DEFAULT '🛏️' |
| `total_capacity` | INT | NOT NULL, DEFAULT 0 |
| `occupied_beds` | INT | NOT NULL, DEFAULT 0 |
| `future_reserved_beds` | INT | NOT NULL, DEFAULT 0 |
| `created_at` | DATETIME | Auto-set |

#### Table 4: `bed_records`
| Column | Type | Constraints |
|---|---|---|
| `record_id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id |
| `icu_occupied` | INT | NOT NULL, DEFAULT 0 |
| `decare_occupied` | INT | NOT NULL, DEFAULT 0 |
| `general_occupied` | INT | NOT NULL, DEFAULT 0 |
| `childcare_occupied` | INT | NOT NULL, DEFAULT 0 |
| `essential_occupied` | INT | NOT NULL, DEFAULT 0 |
| `timestamp` | DATETIME | NOT NULL, Auto-set |

#### Table 5: `patient_admissions_v2`
| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id |
| `date` | DATE | NOT NULL |
| `admission_count` | INT | NOT NULL |

#### Table 6: `patient_transfers`
| Column | Type | Constraints |
|---|---|---|
| `transfer_id` | BIGINT | PK, AUTO_INCREMENT |
| `from_hospital_id` | BIGINT | FK → hospitals.hospital_id, NOT NULL |
| `to_hospital_id` | BIGINT | FK → hospitals.hospital_id, NOT NULL |
| `patient_count` | INT | NOT NULL |
| `bed_allocations` | JSON | Stored as JSON map {category: count} |
| `status` | VARCHAR | NOT NULL (PENDING/APPROVED/REJECTED/COMPLETED) |
| `priority` | VARCHAR | NOT NULL (EMERGENCY/CRITICAL/NORMAL) |
| `stage` | VARCHAR | NOT NULL (PENDING/ACKNOWLEDGED/APPROVED/REJECTED/TIMEOUT_STAGE1/TIMEOUT_STAGE2) |
| `acknowledge_by` | DATETIME | Stage 1 deadline |
| `acknowledged_at` | DATETIME | Actual Stage 1 completion time |
| `confirm_by` | DATETIME | Stage 2 deadline |
| `confirmed_at` | DATETIME | Actual Stage 2 completion time |
| `assigned_doctor_id` | BIGINT | Doctor assigned at Stage 2 |
| `attempt_number` | INT | Escalation attempt count (1-based) |
| `hospitals_tried` | TEXT | JSON array of previously tried hospital IDs |
| `created_at` | DATETIME | Auto-set |
| `approved_at` | DATETIME | — |
| `completed_at` | DATETIME | — |

#### Table 7: `doctor`
| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id, NOT NULL |
| `name` | VARCHAR | NOT NULL |
| `email` | VARCHAR | — |
| `phone` | VARCHAR | — |
| `speciality` | VARCHAR | NOT NULL |
| `qualification` | VARCHAR | — |
| `experience_years` | INT | — |
| `is_available` | BOOLEAN | DEFAULT true |
| `current_patient_count` | INT | DEFAULT 0 |
| `safe_limit` | INT | DEFAULT 12 |
| `availability_type` | VARCHAR | DEFAULT 'PRESENT' (PRESENT/ON_CALL/OFF_DUTY) |
| `shift_start` | VARCHAR | DEFAULT '08:00' |
| `shift_end` | VARCHAR | DEFAULT '16:00' |
| `work_days` | VARCHAR | DEFAULT 'MON,TUE,WED,THU,FRI' |
| `created_at` | DATETIME | Auto-set, NOT UPDATABLE |

#### Table 8: `notifications`
| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id, NOT NULL |
| `message` | TEXT | NOT NULL |
| `type` | VARCHAR | NOT NULL (DATA_REMINDER/TRANSFER_REQUEST/SYSTEM/INFO/SUCCESS/WARNING/EMERGENCY) |
| `notification_type` | VARCHAR | Semantic type (BED_UPDATE_WARNING/SCARCITY_WARNING) |
| `is_read` | BOOLEAN | NOT NULL, DEFAULT false |
| `trigger_count` | INT | DEFAULT 1 |
| `last_triggered` | DATETIME | — |
| `created_at` | DATETIME | Auto-set |

#### Table 9: `forecast_results`
| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `hospital_id` | BIGINT | FK → hospitals.hospital_id, NOT NULL |
| `forecast_6h` | DOUBLE | — |
| `forecast_12h` | DOUBLE | — |
| `forecast_24h` | DOUBLE | — |
| `rmse` | DOUBLE | — |
| `mae` | DOUBLE | — |
| `model_used` | VARCHAR | — |
| `created_at` | DATETIME | Auto-set |

#### Table 10: `patient_requests` *(from PatientRequestRepository)*
> **Information Required from Author** — The `PatientRequest.java` model exists but was not fully reviewed. Column details are partially unavailable.

### Relationships (Entity-Relationship Summary)
- `users` (1) ↔ (1) `hospitals` — OneToOne with CASCADE ALL
- `hospitals` (1) ↔ (N) `bed_categories` — OneToMany
- `hospitals` (1) ↔ (N) `bed_records` — OneToMany
- `hospitals` (1) ↔ (N) `patient_admissions_v2` — OneToMany
- `hospitals` (1) ↔ (N) `patient_transfers` (from and to) — OneToMany (bidirectional via from_hospital_id and to_hospital_id)
- `hospitals` (1) ↔ (N) `doctor` — OneToMany
- `hospitals` (1) ↔ (N) `notifications` — OneToMany
- `hospitals` (1) ↔ (N) `forecast_results` — OneToMany

### Normalization
The schema is in **Third Normal Form (3NF)**:
- Each table has a single primary key.
- No partial dependencies (no composite PKs in fact tables).
- No transitive dependencies — all non-key attributes depend only on the primary key.
- `bed_allocations` in `patient_transfers` is stored as a JSON column (denormalized for flexibility); this is an intentional design choice using `JsonMapConverter`.

### ER Diagram Availability
> **Diagrams Required:** No ER diagram image was found in the project repository. The following ER diagram should be created:
> - **Entities:** User, Hospital, BedCategory, BedRecord, PatientAdmission, Transfer, Doctor, Notification, ForecastResult
> - **Relationships:** as described above
> - **Key attributes per entity:** as described in the table definitions above
> - **Tool recommendation:** Draw.io, Lucidchart, or MySQL Workbench reverse-engineer from live schema

---

## 12. API Documentation

### Base URL
`http://localhost:8080` (or configured Render URL in production)

### Authentication Header (for protected endpoints)
`Authorization: Bearer <JWT_TOKEN>`

---

### 12.1 Auth APIs (`/api/auth`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 1 | `/api/auth/register` | POST | `RegisterRequest` (JSON: hospitalName, govId, email, password, contactNumber, address, latitude, longitude) | `AuthResponse` (token, email, id, hospitalName, setupCompleted) | None | Register new hospital + create user |
| 2 | `/api/auth/login` | POST | `AuthRequest` (JSON: email, password) | `AuthResponse` | None | Login, receive JWT |
| 3 | `/api/auth/verify` | GET | — | `{valid: true, hospital: email}` | JWT | Verify token validity |

---

### 12.2 Hospital APIs (`/api/hospital`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 4 | `/api/hospital/{id}` | GET | Path: hospitalId | `Hospital` entity | JWT | Get hospital details |
| 5 | `/api/hospital/{id}/dashboard` | GET | Path: hospitalId | `DashboardResponse` (totalBeds, occupiedBeds, availableBeds, occupancyRate, utilizationStatus, categories, lastUpdated) | JWT | Real-time dashboard data |
| 6 | `/api/hospital/{id}/details` | GET | Path: hospitalId, Query: fromHospitalId (optional) | `HospitalDetailResponse` (includes distance/travel if fromHospitalId provided) | JWT | Detailed hospital view with optional distance |
| 7 | `/api/hospital/map-data` | GET | — | List of `MapDataResponse` (id, name, lat, lon, occupancyRate, utilizationStatus) | JWT | All hospitals for map rendering |
| 8 | `/api/hospital/setup-complete` | POST | `SetupRequest` (hospitalId, departments list) | `{message: "Setup completed"}` | JWT | Complete onboarding setup |

---

### 12.3 Bed Category APIs (`/api/bed-categories`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 9 | `/api/bed-categories/{hospitalId}` | GET | Path: hospitalId | List of `BedCategory` | JWT | Get all bed categories |
| 10 | `/api/bed-categories/names` | GET | — | List of String (category names) | JWT | Get all category names |
| 11 | `/api/bed-categories/{hospitalId}` | POST | Path: hospitalId, Body: `BedCategoryRequest` | `{message, categoryId, categoryName}` | JWT | Add new bed category |
| 12 | `/api/bed-categories/{hospitalId}/{categoryId}` | PUT | Path: hospitalId, categoryId; Body: `BedCategoryRequest` | Updated category info | JWT | Update bed category |
| 13 | `/api/bed-categories/{hospitalId}/{categoryId}` | DELETE | Path: hospitalId, categoryId | `{message}` | JWT | Delete bed category |

---

### 12.4 Forecast APIs (`/api/forecast`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 14 | `/api/forecast/{hospitalId}` | GET | Path: hospitalId | `ForecastResponse` (predictedPatients, method, dataPointsUsed, historicalData, mae, rmse, scarcityAlert, alertMessage) | JWT | Get load forecast |
| 15 | `/api/forecast/next-day/{hospitalId}` | GET | Path: hospitalId | Same as above | JWT | Next-day forecast (same logic, separate endpoint) |

---

### 12.5 Recommendation APIs (`/api/recommend`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 16 | `/api/recommend/{hospitalId}` | GET | Path: hospitalId, Query: maxDistance (optional), speciality (optional), req-{category}={count} (multiple), excludeHospitalIds (optional) | List of `RecommendationResponse` (id, name, score, distance, travelTime, availableBeds, occupancyRate, utilizationStatus, doctorInfo, splitTransferPlan) | JWT | Get ranked hospital recommendations |

---

### 12.6 Transfer APIs (`/api/transfer`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 17 | `/api/transfer/request` | POST | `TransferRequest` (fromHospitalId, toHospitalId, patientCount, bedAllocations, priority) | `Transfer` entity | JWT | Create new transfer request |
| 18 | `/api/transfer/outgoing/{hospitalId}` | GET | Path: hospitalId | List of `Transfer` | JWT | Get outgoing transfers |
| 19 | `/api/transfer/incoming/{hospitalId}` | GET | Path: hospitalId | List of `Transfer` | JWT | Get incoming transfers |
| 20 | `/api/transfer/{id}/status` | PUT | Path: transferId; Body: `{status: APPROVED/REJECTED/COMPLETED}`; Header: Authorization | `{message, transferId, status}` | JWT | Update transfer status |
| 21 | `/api/transfer/{id}/acknowledge` | PUT | Path: transferId; Header: Authorization | `{message, transferId, stage, confirmBy}` | JWT | Stage 1: Acknowledge transfer |
| 22 | `/api/transfer/{id}/confirm` | PUT | Path: transferId; Body: `{doctorId}`; Header: Authorization | `{message, transferId, stage}` | JWT | Stage 2: Confirm with doctor |
| 23 | `/api/transfer/{id}/reject` | PUT | Path: transferId; Header: Authorization | `{message, transferId, stage}` | JWT | Reject transfer (triggers escalation) |
| 24 | `/api/transfer/{id}/status` | GET | Path: transferId; Header: Authorization | `TransferStatusDTO` (stage, status, deadlines, nextHospital recommendation) | JWT | Poll transfer status |
| 25 | `/api/transfer/incoming/active` | GET | Header: Authorization | List of active `Transfer` (PENDING/ACKNOWLEDGED) | JWT | Active incoming transfers for current hospital |
| 26 | `/api/transfer/incoming/pending/{hospitalId}` | GET | Path: hospitalId | List of active `Transfer` | JWT | Active incoming transfers by hospitalId |

---

### 12.7 Notification APIs (`/api/notifications`)

| # | Endpoint | Method | Input | Output | Auth | Description |
|---|---|---|---|---|---|---|
| 27 | `/api/notifications/{hospitalId}` | GET | Path: hospitalId | `{notifications: [...], unreadCount: N}` | JWT | Get notifications with unread count |
| 28 | `/api/notifications/{notificationId}/read` | PUT | Path: notificationId | `{message}` | JWT | Mark notification as read |
| 29 | `/api/notifications/read-all/{hospitalId}` | PUT | Path: hospitalId | `{message}` | JWT | Mark all notifications as read |

---

### Response Codes
| Code | Meaning | When Used |
|---|---|---|
| 200 OK | Success | Successful GET, PUT, DELETE |
| 400 Bad Request | Client error | Validation failures, business rule violations |
| 401 Unauthorized | Authentication failure | Missing/invalid/expired JWT |
| 404 Not Found | Resource not found | Hospital/transfer not found |
| 500 Internal Server Error | Server-side error | Uncaught exceptions |

---

## 13. Security

### Authentication
JWT-based stateless authentication. On login/register, a JWT is issued containing `email`, `hospitalId`, `userId`, and `roles`. The token is included in the `Authorization: Bearer <token>` header on all subsequent requests.

### Authorization
- All API endpoints except `/api/auth/**`, static resources (`/*.html`, `/css/**`, `/js/**`, `/static/**`), and certain patient-facing endpoints (`/api/patient/request`, `/api/patient/request/*/status`, `/api/patient/recommend`) require a valid JWT.
- Role: `HOSPITAL_ADMIN` is the only role currently in use.
- Within transfer and notification operations, hospital identity is extracted from the JWT claims (not from path parameters) to prevent horizontal privilege escalation (a hospital cannot approve another hospital's transfer by guessing the transfer ID — the receiving hospital's ID must match the JWT claim).

### Password Encryption
BCrypt hashing via `BCryptPasswordEncoder`. BCrypt is an adaptive hashing function designed for password storage — it is slow by design, resistant to brute force and rainbow table attacks.

### JWT
- Library: JJWT v0.12.6 (io.jsonwebtoken)
- Signing: HMAC-SHA with a configurable secret key (`${app.jwt.secret}`, externalized via environment variable).
- Token Expiry: 86,400,000 ms (24 hours). The `JwtUtil.validateToken()` method catches `JwtException` to detect expired or tampered tokens.

### Validation
- `@Valid` annotation with Jakarta Validation constraints used on `AuthController` for `RegisterRequest` and `AuthRequest`.
- Business rule validation in service layer (e.g., bed count validation in transfer capacity checks, occupancy boundary checks in bed updates).

### SQL Injection Prevention
Spring Data JPA with Hibernate ORM is used throughout. All database queries are executed via JPA repository methods (`findById`, `findAll`, `findBy...`) or JPQL named queries — no raw SQL string concatenation. Hibernate uses parameterized queries internally, preventing SQL injection.

### XSS Protection
The application serves only API endpoints returning JSON (no server-side HTML rendering via Thymeleaf or JSP). Vanilla JS frontend is served as static files; however, no explicit output encoding library is implemented on the frontend. **Information Required from Author** — whether the frontend JavaScript sanitizes any user-provided input before rendering to the DOM.

### CSRF Protection
CSRF is explicitly disabled (`csrf.disable()`) in `SecurityConfig`. This is appropriate for stateless REST APIs secured by JWT, as CSRF attacks rely on session cookies, which this application does not use.

### CORS Configuration
`CorsConfiguration` allows `AllowedOriginPatterns(*)` with credentials enabled. This is suitable for development; in production, `CORS_ALLOWED_ORIGINS` should be restricted to the specific frontend domain.

### Additional Security Notes
- The `gov_id` field in the `hospitals` table has a `UNIQUE` constraint, preventing duplicate hospital registrations under the same Government ID.
- Sensitive configuration (DB credentials, JWT secret, mail credentials) are externalized via environment variables (`${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`, `${JWT_SECRET}`, `${MAIL_USERNAME}`, `${MAIL_PASSWORD}`).

---

## 14. Feature List

| # | Feature | Description | Technology Used | Benefits |
|---|---|---|---|---|
| 1 | Hospital Registration | Register with Gov ID, geolocation, email, password | Spring Boot, BCrypt, JPA, MySQL | Verified, geolocated hospital onboarding |
| 2 | JWT Authentication | Secure, stateless login with 24-hour token | Spring Security, JJWT 0.12.6, BCrypt | Scalable, sessionless security |
| 3 | 3-Step Setup Wizard | Configure departments and bed capacities on first login | Spring Boot, JPA | Ensures all hospitals are fully configured |
| 4 | Real-Time Dashboard | Live total/occupied/available beds, occupancy rate, utilization status | Spring Boot, Chart.js, Leaflet.js | Instant operational awareness |
| 5 | Bed Management | Update occupied beds per department; add/edit/delete categories | Spring Boot REST API, JPA | Maintains accurate real-time data |
| 6 | Occupancy Trend Chart | 24-hour bed occupancy history visualization | Chart.js, BedRecord table | Visual trend analysis |
| 7 | Hospital Map | Leaflet.js interactive map of all registered hospitals with status indicators | Leaflet.js, OpenStreetMap | Geographic situational awareness |
| 8 | 7-Day Moving Average Forecast | Predict next-day patient admissions; compute RMSE and MAE | Java math, PatientAdmission table | Proactive capacity planning |
| 9 | Scarcity Alert | Notification + in-app alert when predicted occupancy ≥75% or ≥90% | Spring Boot Notification system | Early warning for overflow prevention |
| 10 | Multi-Factor Recommendation Engine | Score-ranked list of nearby hospitals for patient transfer | Haversine formula, Composite scoring, JPA | Data-driven transfer decision support |
| 11 | Bed-Type Filtering | Filter recommendations by specific department requirements (ICU, General Ward, etc.) | Spring Boot query params, JPA | Department-specific matching |
| 12 | Doctor Speciality Filter | Filter recommendations by required doctor speciality | DoctorRepository JPA queries | Ensures appropriate clinical resources |
| 13 | Split Transfer Planning | Distribute patients across multiple hospitals when no single one is sufficient | Custom allocation algorithm | Maximizes patient accommodation |
| 14 | Two-Stage Transfer Protocol | Stage 1: 2-min acknowledgement; Stage 2: 3-min doctor confirmation | Spring Boot, JPA, TransferEscalationScheduler | SLA enforcement, accountability |
| 15 | Automatic Transfer Escalation | Auto-create transfer to next hospital on timeout or rejection | TransferEscalationScheduler (30s cron), JSON tried-list tracking | Zero manual intervention on failure |
| 16 | Transfer Priority Levels | EMERGENCY / CRITICAL / NORMAL priority classification | Transfer entity, notification routing | Appropriate urgency signaling |
| 17 | Doctor Roster Management | Add/edit/remove doctors with speciality, shift schedule, patient load | DoctorController, DoctorService | Enables doctor-aware recommendation |
| 18 | Automated Shift Management | Hourly scheduler updates doctor availability based on shift times and work days | ShiftScheduler, Spring @Scheduled | Eliminates manual status updates |
| 19 | In-App Notification System | Bell icon with unread count; categorized notifications | NotificationService, JPA | Real-time awareness without email dependency |
| 20 | Email Notifications | Automated emails on transfer events and data staleness | Spring Boot Mail, Gmail SMTP | Multi-channel alert redundancy |
| 21 | Data Freshness Enforcement | Hourly check; reminder email + notification if data not updated in 24 hours | DataFreshnessScheduler, Spring @Scheduled | Maintains data quality |
| 22 | Notification Deduplication | Existing unread notifications of same type are updated, not duplicated | NotificationRepository lookup | Prevents notification fatigue |
| 23 | Transfer History | Full outgoing and incoming transfer history with timestamps and status | TransferRepository, JPA | Audit trail and accountability |
| 24 | Docker Support | Fully Dockerized via Dockerfile (Maven + JDK 21) | Docker | Easy deployment and portability |

---

## 15. Screens Required (for Research Paper)

| # | Screenshot Name | Purpose | Description | Importance |
|---|---|---|---|---|
| 1 | Hospital Registration Page | Show onboarding process | Registration form with hospital name, Gov ID, email, contact, address, and interactive geolocation map | High — demonstrates the onboarding flow |
| 2 | Login Page | Show authentication UI | Minimal login screen with email/password and JWT authentication | Medium |
| 3 | Department Setup Wizard — Step 1 | Show department selection | Step 1: checkbox selection of predefined departments (ICU, General Ward, etc.) + custom add | High — demonstrates the setup innovation |
| 4 | Department Setup Wizard — Step 2 | Show capacity configuration | Step 2: bed capacity entry for each selected department | High |
| 5 | Main Dashboard — Overview | Show real-time monitoring | Full dashboard view: stats cards (total/occupied/available beds, occupancy %), utilization badge | High — key system output |
| 6 | Dashboard — Trend Chart | Show historical occupancy | 24-hour bed occupancy trend line chart (Chart.js) | Medium |
| 7 | Dashboard — Donut Chart | Show bed distribution | Donut chart showing distribution across departments | Medium |
| 8 | Dashboard — Hospital Map | Show geo-visualization | Leaflet.js map with color-coded hospital markers (green/yellow/red by utilization) | High |
| 9 | Load Forecasting Panel | Show forecasting output | Predicted patients for tomorrow, method used, RMSE, MAE, historical data chart | Very High — core research contribution |
| 10 | Scarcity Alert Notification | Show alert system | In-app notification showing CRITICAL or WARNING scarcity alert triggered by forecast | High |
| 11 | Recommendation Results | Show recommendation engine | Ranked list of hospitals with score, distance, travel time, available beds, doctor info | Very High — core research contribution |
| 12 | Split Transfer Plan | Show split planning | Recommendation card showing split allocation across multiple hospitals | High |
| 13 | Transfer Request Form | Show transfer initiation | Modal/form to set patient count, bed allocations per category, priority level | High |
| 14 | Transfer Status — Pending | Show transfer lifecycle | Transfer card showing PENDING stage with acknowledge countdown timer | High |
| 15 | Transfer Status — Acknowledged | Show Stage 2 | Transfer card showing ACKNOWLEDGED stage with confirm countdown timer | High |
| 16 | Transfer Status — Approved | Show completion | Transfer card showing APPROVED with assigned doctor name | High |
| 17 | Transfer Escalation Notification | Show escalation | Notification showing "looking for next available hospital" after rejection/timeout | High |
| 18 | Incoming Transfer Request | Show receiver side | Incoming transfer panel with Acknowledge / Confirm with Doctor / Reject buttons | High |
| 19 | Doctor Management Panel | Show doctor roster | List of doctors with speciality, shift, availability status, patient count vs. safe limit | Medium |
| 20 | Transfer History | Show audit trail | Table of past transfers with status badges, timestamps, hospital names | Medium |

> **Note:** Screenshots 1–4 are partially available in the `screenshots/` directory (dashboard.png, register.png, setup_step1.png, setup_step2.png). All others need to be captured from a running instance of the application.

---

## 16. Performance Evaluation

The following performance metrics should be measured and reported in the research paper. No actual measurement results are available from the current codebase — all of the following represent **experiments that must be conducted**.

### 16.1 Functional Metrics

| Metric | Description | Measurement Method |
|---|---|---|
| Recommendation Accuracy | Do scored recommendations match clinical expectations for optimal transfer? | Expert review panel or simulation against known-optimal assignments |
| Forecasting Accuracy (MAE) | Mean Absolute Error of 7-day MA prediction | Computed in ForecastService; should be collected over ≥30 days of data |
| Forecasting Accuracy (RMSE) | Root Mean Square Error of 7-day MA | Same as above |
| Escalation Success Rate | % of transfers successfully placed after at least one rejection/timeout | Count from transfer records with attemptNumber > 1 |
| Transfer Completion Rate | % of initiated transfers that reach COMPLETED status | Database analysis of transfer table |

### 16.2 System Performance Metrics

| Metric | Description | Target |
|---|---|---|
| API Response Time (p50/p95) | Median and 95th percentile response times for key endpoints | <200ms p50, <500ms p95 |
| Dashboard Load Time | Time to render complete dashboard | <1 second |
| Recommendation Engine Latency | Time for `/api/recommend` with 50 hospitals | <500ms |
| Escalation Scheduler Latency | Time from deadline expiry to escalation completion | ≤30 seconds (scheduler interval) |
| Concurrent User Support | Max simultaneous active sessions before degradation | Load test with JMeter or k6 |
| Database Query Performance | Query execution time for recommendation, forecast, and transfer status APIs | Measure via Hibernate SQL logging |
| CPU Usage Under Load | CPU % during peak concurrent requests | Monitor via Docker stats or APM |
| Memory Usage | JVM heap usage under normal and peak load | JVM metrics (jconsole / actuator) |
| Throughput | API requests per second under sustained load | JMeter load test |
| Availability / Uptime | System uptime percentage | Monitor over 30-day deployment |

### 16.3 Experiments to Conduct

1. **Benchmark Test:** Measure API response times for each endpoint under single-user and concurrent-user scenarios (10, 50, 100 concurrent users using JMeter).
2. **Forecasting Accuracy Test:** Deploy the system with real or synthetic admission data for ≥30 days; collect daily MAE and RMSE values; compare against EWMA baseline.
3. **Recommendation Scoring Validation:** Generate 20 test scenarios with known-optimal hospital assignments; compare algorithm recommendations to ground truth.
4. **Escalation Timing Test:** Create transfers with forced timeouts; measure time from deadline to completed escalation.
5. **Database Scalability Test:** Test with 10, 50, 100, 500 registered hospitals; measure recommendation engine latency growth.
6. **User Study:** Administer a System Usability Scale (SUS) questionnaire to 20+ hospital administrators; measure task completion time for a simulated transfer scenario.

---

## 17. Experimental Setup

### Hardware
> **Information Required from Author** — The specific hardware used for development and/or deployment is not documented in the source code.

*Recommended documentation format:*
| Component | Specification |
|---|---|
| Development Machine CPU | [e.g., Intel Core i7-12700H, 14 cores] |
| Development Machine RAM | [e.g., 16 GB DDR5] |
| Storage | [e.g., 512 GB NVMe SSD] |
| Deployment Server CPU | [e.g., Render.com 0.5 vCPU or custom] |
| Deployment Server RAM | [e.g., 512 MB - 2 GB] |

### Software
| Component | Version | Source |
|---|---|---|
| Operating System (Dev) | **Information Required from Author** | |
| JDK | Eclipse Temurin 21 (from Dockerfile) | maven:3.9.9-eclipse-temurin-21 |
| Maven | 3.9.9 (from Dockerfile) | |
| Spring Boot | 3.2.1 | pom.xml |
| Spring Security | (managed by Spring Boot 3.2.1) | |
| Spring Data JPA | (managed by Spring Boot 3.2.1) | |
| JJWT | 0.12.6 | pom.xml |
| Lombok | 1.18.30 | pom.xml |
| MySQL | 8+ (from README) | |
| Docker | Latest | Dockerfile |
| Browser (Testing) | Chrome / Firefox / Edge | README |
| IDE | IntelliJ IDEA (primary), VS Code (secondary) | .idea and .vscode dirs |

### Framework / Dependency Versions Summary
| Dependency | Version |
|---|---|
| `spring-boot-starter-parent` | 3.2.1 |
| `jjwt-api` | 0.12.6 |
| `jjwt-impl` | 0.12.6 |
| `jjwt-jackson` | 0.12.6 |
| `lombok` | 1.18.30 |
| `mysql-connector-j` | (managed by Spring Boot) |
| `spring-boot-devtools` | (managed by Spring Boot) |

---

## 18. Results Required (Checklist)

The following results tables and graphs are required before the paper can be submitted for publication. None are currently available from the source code and must be experimentally generated.

### Must-Have Results
- [ ] **API Response Time Table** — Median and 95th percentile response times for each major endpoint under 1, 10, 50 concurrent users
- [ ] **Recommendation Engine Accuracy Table** — Comparison of algorithm recommendations vs. expert-labelled optimal assignments for 20 test scenarios
- [ ] **Forecasting MAE and RMSE Table** — Daily MAE and RMSE values for the 7-day MA model over a 30-day period
- [ ] **Forecasting Comparison Chart** — Historical vs. Predicted patient count line chart for at least 7 days
- [ ] **Transfer Lifecycle Timeline** — Average time per transfer stage (PENDING → ACKNOWLEDGED → APPROVED/TIMEOUT) across 50 test transfers
- [ ] **Escalation Success Rate Table** — Number of attempts required to complete a transfer (1 attempt, 2 attempts, 3+ attempts) as a percentage
- [ ] **System Throughput Graph** — API requests per second vs. response time under increasing load (from JMeter/k6 load test)
- [ ] **Comparison Table with Existing Systems** — Feature-by-feature comparison (see Section 19)

### Recommended Results
- [ ] **CPU and Memory Usage Graph** — Under simulated load (10, 50, 100 concurrent users)
- [ ] **Recommendation Score Distribution Histogram** — Distribution of scores assigned by the algorithm across 100 test hospitals
- [ ] **User Satisfaction SUS Score** — System Usability Scale rating from ≥20 participants
- [ ] **Task Completion Time Comparison** — Time to complete a transfer using HospiSync vs. manual phone coordination (simulated experiment)
- [ ] **Notification Delivery Latency** — Time from trigger event to in-app notification visibility
- [ ] **Data Freshness Compliance Rate** — % of hospitals maintaining data updated within 24 hours after deploying the freshness scheduler

---

## 19. Comparison with Existing Systems

| Feature | Manual Phone Coordination | Isolated HIMS | Government Health Portals | HospiSync (Proposed) |
|---|---|---|---|---|
| Multi-hospital Visibility | ❌ None | ❌ Single hospital | ⚠️ Static data only | ✅ Real-time, multi-hospital |
| Bed Availability Granularity | ❌ Verbal estimate | ✅ Detailed per category | ⚠️ Aggregate only | ✅ Per-department, real-time |
| Transfer Initiation | ❌ Phone call | ❌ Not supported | ❌ Not supported | ✅ Digital, structured |
| Transfer SLA Enforcement | ❌ No time limits | ❌ Not supported | ❌ Not supported | ✅ 2-min + 3-min deadlines |
| Automatic Escalation | ❌ Manual restart | ❌ Not supported | ❌ Not supported | ✅ Fully automatic with tried-list |
| Decision Support / Ranking | ❌ None | ❌ None | ❌ None | ✅ Multi-factor scored ranking |
| Load Forecasting | ❌ None | ⚠️ Some HIMS have basic stats | ❌ None | ✅ 7-day MA with RMSE/MAE |
| Doctor Availability Integration | ❌ Manual | ⚠️ Internal scheduling only | ❌ None | ✅ Shift-aware, integrated into transfer |
| Email Notifications | ❌ None | ⚠️ Limited | ❌ None | ✅ All transfer lifecycle events |
| In-App Notifications | ❌ None | ⚠️ Internal only | ❌ None | ✅ Real-time with deduplication |
| Audit Trail | ❌ No digital record | ✅ Internal records | ❌ Limited | ✅ Full transfer history with timestamps |
| Geolocation-based Search | ❌ None | ❌ None | ⚠️ Map view only | ✅ Haversine distance-based ranking |
| Split Transfer Planning | ❌ None | ❌ None | ❌ None | ✅ Automatic multi-hospital allocation |
| Open-Source / Extensible | — | ❌ Usually proprietary | ❌ Government-owned | ✅ MIT Licensed, GitHub |
| Docker Deployment | — | ❌ Vendor-managed | — | ✅ Fully containerized |

---

## 20. Strengths

1. **Comprehensive Transfer Protocol:** The two-stage timed handshake protocol with automatic escalation is a complete solution for the transfer coordination problem — from request to doctor assignment to completion or fallback.
2. **Automated Escalation with History Tracking:** The JSON-based `hospitalsTried` tracking ensures the system never loops back to a previously rejected hospital during escalation.
3. **Multi-Factor Scoring Algorithm:** The composite scoring formula is multi-dimensional, balancing patient safety (beds), distance (travel time), doctor readiness, and system load balance in a single interpretable score.
4. **Split Transfer Planning:** The algorithm handles the edge case where no single hospital can satisfy all requirements — a feature absent from comparable platforms.
5. **Statistical Forecasting with Operational Integration:** The forecast is not just an analytical tool; it directly generates actionable alerts (SCARCITY_WARNING notifications) that appear on the dashboard.
6. **Doctor-Aware Transfer Confirmation:** The integration of doctor availability, speciality, and safe patient limits into the recommendation and transfer confirmation workflow is clinically meaningful.
7. **Notification Deduplication:** The system intelligently avoids duplicate notifications, reducing alarm fatigue for hospital administrators.
8. **Automated Data Quality Enforcement:** The hourly DataFreshnessScheduler proactively reminds hospitals to update stale data, improving system-wide data integrity.
9. **Automated Shift Management:** The ShiftScheduler eliminates manual doctor availability management, reducing administrative overhead.
10. **Full-Stack Completeness:** The system covers the complete workflow from registration to transfer completion, including maps, charts, notifications, and email — all within a single deployable unit.
11. **Docker and Cloud-Ready:** The Dockerfile and Render-compatible environment variable configuration make the system easily deployable.
12. **Open-Source MIT License:** Freely available for adoption, extension, and research.

---

## 21. Limitations

1. **Token Revocation Not Implemented:** A stolen JWT remains valid for 24 hours with no blacklisting mechanism.
2. **Forecast Model Simplicity:** The 7-day Simple Moving Average does not model seasonality, trends, or external factors. For hospitals with volatile admission patterns, accuracy will be poor.
3. **Hardcoded Transfer Timeouts:** The 2-minute (Stage 1) and 3-minute (Stage 2) deadlines are hardcoded and not configurable per hospital priority or emergency type.
4. **CSRF Disabled:** While appropriate for JWT APIs, CSRF protection being disabled could be a concern if the application is ever extended to support browser-based session authentication.
5. **CORS Allows All Origins in Development:** `AllowedOriginPatterns(*)` is configured; production restriction is left to an environment variable that may not be set.
6. **No Real-Time Push Notifications:** The notification system requires polling; no WebSocket or Server-Sent Events (SSE) is implemented.
7. **Single Role (HOSPITAL_ADMIN):** The system does not currently support differentiated roles for doctors, nurses, or patients.
8. **Split Transfer Not Atomically Executed:** The split transfer algorithm generates a plan but does not create multiple transfer requests simultaneously or track them as a coordinated operation.
9. **Travel Time Estimation Simplistic:** Travel time is calculated as `distance / 40 km/h × 60 min` — a fixed average speed that does not account for road type, traffic, or time of day.
10. **No Swagger/OpenAPI Documentation:** Noted as a future improvement in the README — makes third-party API integration more difficult.
11. **Limited Test Coverage:** Test directory exists in Maven structure but no test classes were found. Absence of unit and integration tests is a significant software quality gap.
12. **ML Service URL Configured but Unused:** `app.ml-service.url` is configured, suggesting an ML microservice was planned but not implemented. The current forecast uses only statistical methods.
13. **No Mobile Responsiveness:** Noted as a future improvement in the README.

---

## 22. Future Scope

1. **Advanced ML Forecasting:** Replace the 7-day SMA with ARIMA, SARIMA, or LSTM-based models for improved accuracy on seasonal and trend-aware admission prediction.
2. **WebSocket-Based Real-Time Notifications:** Replace polling-based notifications with WebSocket push notifications for immediate delivery.
3. **Role-Based Access Control Expansion:** Add roles for Doctors (view assigned transfers, update patient count), Nurses (update bed occupancy), and Patients (track their transfer request status).
4. **Mobile Application:** Develop a React Native or Flutter mobile app for on-the-go access by hospital administrators.
5. **OpenAPI/Swagger Documentation:** Auto-generate interactive API documentation using Springdoc OpenAPI.
6. **Ambulance Dispatch Integration:** Integrate with ambulance management systems to coordinate patient transport from the transfer approval event.
7. **PDF Report Export:** Allow administrators to export bed occupancy reports, transfer history, and forecast summaries as PDF documents.
8. **Multi-Language Support:** Localization for regional languages (Hindi, Tamil, Marathi, etc.) to support adoption in regional government hospitals.
9. **Configurable Transfer Timeouts:** Allow priority-specific timeout configuration (e.g., EMERGENCY transfers → 1-minute acknowledgement, NORMAL → 5 minutes).
10. **Atomic Split Transfer Execution:** Implement multi-transfer request creation and tracking for split transfer plans.
11. **Blockchain Audit Trail:** Use blockchain for immutable recording of transfer events for regulatory compliance and legal accountability.
12. **Integration with National Health Portals:** API-based integration with government HIMS or ABDM (Ayushman Bharat Digital Mission) for real-time data synchronization.
13. **Comprehensive Unit and Integration Testing:** Implement full test coverage using JUnit 5, Mockito, and Spring Boot Test.
14. **Bed Occupancy Prediction by Department:** Extend forecasting to predict department-level (ICU, General Ward) occupancy, not just total admission count.

---

## 23. Keywords

The following 15–20 keywords are recommended for academic database indexing:

1. Hospital Resource Management
2. Inter-Hospital Patient Transfer
3. Bed Occupancy Monitoring
4. Healthcare Load Balancing
5. Hospital Recommendation System
6. Transfer Escalation Protocol
7. Patient Load Forecasting
8. Moving Average Forecasting
9. Haversine Distance
10. Hospital Coordination System
11. JWT Authentication
12. Spring Boot Healthcare Application
13. Real-Time Bed Tracking
14. Clinical Decision Support System
15. Doctor Availability Management
16. Hospital Information System
17. Emergency Patient Distribution
18. Geolocation-Based Healthcare
19. Healthcare Network Optimization
20. SLA-Enforced Transfer Protocol

---

## 24. Possible Research Titles (≥20)

1. **HospiSync: A Multi-Factor Scoring and Two-Stage SLA-Enforced Protocol for Automated Inter-Hospital Patient Transfer**
2. **An Intelligent Hospital Coordination Platform for Real-Time Bed Management, Load Forecasting, and Automated Patient Distribution**
3. **Design and Implementation of a Scored Recommendation Engine for Inter-Hospital Patient Transfer with Automatic Escalation**
4. **A Two-Stage Timed Transfer Protocol with Automatic Hospital Escalation for Reducing Patient Transfer Delays in Overloaded Healthcare Systems**
5. **HospiSync: Integrating Statistical Load Forecasting with Real-Time Bed Visibility for Proactive Hospital Capacity Management**
6. **A Composite Scoring Algorithm for Optimal Hospital Selection in Emergency Patient Transfer Scenarios**
7. **Real-Time Inter-Hospital Coordination Using JWT-Secured REST APIs and Automated Escalation Mechanisms**
8. **A Full-Stack Web Platform for Hospital Network Load Balancing Using Haversine-Based Proximity Scoring and Moving Average Forecasting**
9. **Automated Patient Transfer Orchestration with Doctor-Availability Integration and SLA Enforcement in Distributed Hospital Networks**
10. **Reducing Hospital Overcrowding Through Intelligent Patient Distribution: A Case Study of the HospiSync System**
11. **A Statistical Approach to Next-Day Patient Admission Forecasting in Hospital Resource Planning Systems**
12. **Design of a Split Transfer Planning Algorithm for Multi-Hospital Patient Distribution in Capacity-Constrained Scenarios**
13. **Automated Notification and Escalation Mechanisms for Real-Time Hospital Network Coordination**
14. **Towards a Unified Hospital Coordination Architecture: Real-Time Monitoring, Forecasting, and Transfer Management**
15. **Evaluating the Effectiveness of Multi-Factor Hospital Ranking Algorithms for Patient Transfer Decision Support**
16. **A Geolocation-Aware Hospital Recommendation System with Doctor Speciality and Bed Category Filtering**
17. **HospiSync: An Open-Source, Dockerized Platform for Hospital Bed Management and Cross-Hospital Patient Transfer Coordination**
18. **Integrating Moving Average Forecasting with Threshold-Based Alerting for Proactive Hospital Bed Scarcity Prevention**
19. **A Review of Hospital Load Balancing Systems and the Design of an Automated Two-Stage Transfer Protocol**
20. **From Manual Coordination to Automated Escalation: A Systems Approach to Solving Hospital Overflow in Urban Healthcare Networks**
21. **Shift-Aware Doctor Availability Management Integrated with Real-Time Patient Transfer Confirmation Workflows**
22. **Performance Evaluation of a Stateless JWT-Secured REST Architecture for Multi-Hospital Healthcare Coordination**

---

## 25. Research Questions

The following research questions are addressed or directly supported by the HospiSync project:

1. **RQ1:** How can a composite scoring algorithm combining geographic distance, bed availability, doctor availability, and utilization status improve the quality of inter-hospital patient transfer decisions compared to single-criterion selection?
2. **RQ2:** What is the impact of enforcing time-bound acknowledgement and confirmation stages (two-stage protocol) on the speed and reliability of inter-hospital patient transfer coordination?
3. **RQ3:** How effectively does an automated escalation mechanism — with tried-hospital tracking — resolve patient transfer requests in hospital networks where individual hospitals may reject or fail to respond?
4. **RQ4:** To what extent can a 7-day Simple Moving Average model accurately predict next-day patient admissions in hospital settings, as measured by MAE and RMSE?
5. **RQ5:** At what point does the 7-day MA forecasting model's accuracy degrade, and what alternative forecasting methods would provide superior performance for hospitals with seasonal or trend-driven admission patterns?
6. **RQ6:** How does the integration of doctor availability (speciality, shift schedule, current patient load, safe limit) into the recommendation engine affect the clinical appropriateness of transfer suggestions?
7. **RQ7:** Can split transfer planning — distributing patients across multiple hospitals when no single hospital can accommodate all requirements — significantly increase the overall transfer success rate in capacity-constrained networks?
8. **RQ8:** How does real-time, automated bed data staleness enforcement (through scheduled reminders and notifications) impact the accuracy of system-wide bed occupancy data over time?
9. **RQ9:** What is the system performance (response time, throughput, scalability) of a monolithic Spring Boot architecture serving a multi-hospital coordination platform under varying concurrent user loads?
10. **RQ10:** Can a unified, open-source hospital coordination platform built on commodity cloud infrastructure provide sufficient reliability and performance for real-world hospital network deployment?

---

## 26. Novel Contributions

| # | Contribution | Type | Description |
|---|---|---|---|
| 1 | Two-Stage Timed Transfer Protocol | **Engineering + Practical** | A structured, time-bounded, two-phase handshake protocol (Stage 1: 2-min acknowledgement; Stage 2: 3-min doctor confirmation) with scheduler-enforced deadlines — directly addresses the absence of SLA enforcement in manual transfer coordination |
| 2 | Composite Multi-Factor Hospital Scoring Algorithm | **Engineering + Research** | A novel weighted scoring formula combining available beds, Haversine distance, doctor score (with availability-type bonus), and utilization status to rank hospitals for patient transfer — integrates clinical, geographic, and operational dimensions in a single interpretable score |
| 3 | Automatic Transfer Escalation with JSON-based Tried-Hospital Tracking | **Engineering** | When a transfer fails (timeout or rejection), the system automatically creates a new transfer to the next-best hospital, maintaining a JSON-serialized list of all previously tried hospital IDs within the Transfer entity to prevent repeated attempts |
| 4 | Split Transfer Planning Algorithm | **Engineering + Practical** | When no single hospital can satisfy all bed-type requirements, a greedy allocation algorithm distributes patients across multiple hospitals — a novel operational feature enabling transfer completion in capacity-constrained environments |
| 5 | Statistical Forecasting Integrated with Operational Alert Workflow | **Research + Practical** | The 7-day Moving Average forecast is directly wired into the notification and alert system, triggering SCARCITY_WARNING notifications at ≥75% and ≥90% predicted occupancy — integrating predictive analytics into the operational workflow |
| 6 | Shift-Aware Doctor Availability Engine Integrated with Transfer Confirmation | **Engineering + Practical** | Hourly scheduler automatically updates doctor availability based on shift schedules and work days; available doctors (PRESENT/ON_CALL) are surfaced in the recommendation engine and required for transfer confirmation — creating a clinically aware transfer system |
| 7 | Deduplicated Notification System with Trigger Count Tracking | **Engineering** | Rather than creating duplicate notifications for repeated events (e.g., repeated data staleness), the system identifies existing unread notifications of the same type, increments a `triggerCount`, and updates `lastTriggered` — preventing notification fatigue while preserving event history |

---

## 27. Literature Review Requirements

### Research Topics to Survey
1. Hospital resource management and bed occupancy optimization
2. Inter-hospital patient transfer systems and protocols
3. Clinical decision support systems (CDSS) for emergency medicine
4. Forecasting methods for patient admission prediction (time series, ML)
5. Hospital overcrowding: causes, impacts, and mitigation strategies
6. Location-based recommendation systems in healthcare
7. Geospatial algorithms for healthcare facility selection (Haversine, Dijkstra, A*)
8. Healthcare information systems (HIS/HIMS) architecture
9. REST API security in healthcare (OAuth 2.0, JWT, HIPAA/GDPR compliance)
10. Multi-criteria decision making (MCDM) in healthcare resource allocation

### Keywords for Literature Search
- "inter-hospital patient transfer system"
- "hospital bed management real-time"
- "hospital load balancing"
- "patient transfer decision support"
- "emergency department overcrowding"
- "hospital recommendation system"
- "patient admission forecasting"
- "moving average hospital occupancy"
- "healthcare coordination platform"
- "clinical decision support emergency"
- "hospital capacity planning"
- "geolocation hospital recommendation"

### Databases to Search
- IEEE Xplore (primary — for engineering/systems papers)
- PubMed / MEDLINE (primary — for clinical and health informatics papers)
- Scopus (comprehensive coverage)
- Google Scholar (breadth and grey literature)
- ACM Digital Library (computing/HCI papers)
- Springer SpringerLink (healthcare informatics journals)
- Elsevier ScienceDirect (Journal of Biomedical Informatics, IJMI)

### Minimum Number of Papers Required
- **Minimum 30 references** for an IEEE/Springer conference paper.
- **Minimum 50 references** for a journal article (Elsevier, Scopus-indexed).
- Recommend: ≥15 papers from the last 5 years (2020–2025) for "recent work" credibility.

### Recent Trends (2020–2025)
1. AI/ML-based patient admission prediction (LSTM, Transformer models)
2. COVID-19-driven innovations in hospital capacity management and inter-facility coordination
3. Digital twin models for hospital operations simulation
4. Federated learning for privacy-preserving patient data sharing across hospitals
5. Real-time hospital bed tracking using IoT sensors
6. Blockchain for healthcare audit trails and patient record integrity
7. National Health Stack / ABDM integration for unified hospital data in India
8. Ambulance dispatch optimization combined with destination hospital selection

### Research Gaps to Highlight
- Absence of unified platforms combining forecasting + recommendation + timed transfer protocols in a single system.
- Limited work on automatic escalation chains in inter-hospital transfer networks.
- Lack of doctor-availability integration in existing transfer recommendation systems.
- Insufficient evaluation of composite scoring algorithms for hospital recommendation compared to single-criterion baselines.
- No open-source, production-ready reference implementations for the end-to-end hospital coordination problem.

---

## 28. References Required (Checklist)

### Research Papers (Minimum 15)
- [ ] Papers on hospital overcrowding causes and impacts (Hoot & Aronsky, 2008 is a foundational reference)
- [ ] Papers on inter-hospital patient transfer decision-making
- [ ] Papers on emergency department crowding and patient safety
- [ ] Papers on bed management information systems
- [ ] Papers on patient admission forecasting (time series, ML methods)
- [ ] Papers on moving average methods applied to healthcare data
- [ ] Papers on geolocation-based healthcare facility recommendation
- [ ] Papers on multi-criteria decision support in healthcare
- [ ] Papers on clinical decision support systems for emergency care
- [ ] Papers on hospital load balancing algorithms

### Survey Papers (Minimum 3)
- [ ] Survey of hospital resource management systems
- [ ] Survey of patient transfer coordination methods
- [ ] Survey of predictive analytics in healthcare

### Conference Papers (Minimum 5)
- [ ] IEEE EMBC, ICHI, or HEALTHINF papers on hospital coordination
- [ ] ACM CHI or CSCW papers on healthcare coordination tools
- [ ] Papers from AMIA Annual Symposium on clinical decision support

### Journals (Minimum 10)
- [ ] Journal of the American Medical Informatics Association (JAMIA)
- [ ] Journal of Biomedical Informatics
- [ ] International Journal of Medical Informatics (IJMI)
- [ ] Applied Clinical Informatics
- [ ] Health Informatics Journal
- [ ] BMC Medical Informatics and Decision Making

### Government Reports (Minimum 2)
- [ ] National Health Mission (NHM) India — Hospital occupancy statistics
- [ ] WHO reports on hospital overcrowding and patient safety
- [ ] ABDM (Ayushman Bharat Digital Mission) framework documentation

### Standards
- [ ] HL7 FHIR standard (for future interoperability reference)
- [ ] ISO 13606 (Electronic Health Record Communication standard)
- [ ] HIPAA / IT Act 2000 (India) for healthcare data privacy

### Official Documentation
- [ ] Spring Boot 3.2.1 documentation (spring.io)
- [ ] JJWT v0.12.6 GitHub documentation
- [ ] MySQL 8.0 Reference Manual
- [ ] Docker Official Documentation
- [ ] Leaflet.js Documentation
- [ ] Chart.js Documentation

---

## 29. Missing Information

The following information could not be extracted from the available source code, README, or configuration files. The author must provide this information before a publishable paper can be completed.

| # | Information Needed | Why It Is Needed | Priority |
|---|---|---|---|
| 1 | Actual experimental results (API response times, MAE, RMSE values, escalation rates) | Section 16 (Performance Evaluation) and Section 18 (Results) require actual measured values — synthetic data cannot be used in a publication | **High** |
| 2 | Hardware specification of development and deployment machines | Section 17 (Experimental Setup) requires hardware context for result reproducibility | **High** |
| 3 | Operating system version used for development | Required for complete experimental setup documentation | **Medium** |
| 4 | Screenshot of the Recommendation Engine results in action | Section 15 screenshot #11 — a core contribution screenshot needed for the paper | **High** |
| 5 | Screenshot of the Transfer Status / Two-Stage Protocol screens (Pending, Acknowledged, Approved) | Sections 15 #14, #15, #16 — essential for demonstrating the core protocol contribution | **High** |
| 6 | Screenshot of the Load Forecasting panel with actual data | Section 15 #9 — key research contribution visualization | **High** |
| 7 | Whether the ML Service URL (`http://localhost:8000`) corresponds to an actual deployed ML microservice | If an ML component exists, it must be documented in the technical stack and methodology | **High** |
| 8 | Full content of `PatientEscalationScheduler.java` and `DataSeeder.java` | These schedulers may contain additional algorithms or logic relevant to the methodology | **Medium** |
| 9 | Full content of `PatientController.java`, `PatientService.java`, `PatientRecommendService.java` | There appears to be a patient-facing recommendation and request module (not the hospital admin module) — this may represent an additional novel contribution | **Medium** |
| 10 | Full content of `PatientRequest.java` model | Related to the above — the patient request table schema is incomplete | **Medium** |
| 11 | Database ER diagram | Needed for Section 11 — no diagram file was found in the repository | **High** |
| 12 | System architecture diagram | A formal architecture diagram (UML deployment diagram or component diagram) is required for the paper | **High** |
| 13 | Unit test results or test coverage report | Section 17 / testing — required to demonstrate software engineering quality | **Medium** |
| 14 | Number of hospitals registered in pilot/demo deployment | Needed to contextualize system evaluation and scalability claims | **Medium** |
| 15 | Whether the system has been deployed and used in a real hospital environment (pilot study) | If a real deployment exists, it significantly strengthens the practical contribution claim | **High** |
| 16 | Author's institutional affiliation, supervisor name, and university | Required for IEEE/journal paper authorship block | **High** |
| 17 | Ethical approval or IRB approval (if real patient data was used) | All health informatics publications require ethical clearance documentation if human data is involved | **High** |
| 18 | Exact scoring weight derivation rationale (why 3, 2, 15 were chosen for the scoring formula) | Reviewers will ask for empirical or theoretical justification of scoring coefficients | **Medium** |
| 19 | Patient-side module description (`/api/patient/` endpoints) | The security config allows public access to patient request endpoints — this suggests a patient-facing use case not documented in the README | **Medium** |
| 20 | Current GitHub star/fork count and community adoption metrics | Useful for practical impact statement | **Low** |

---

## 30. Readiness Report

The following ratings are based solely on the information extractable from the source code, README, and configuration. They represent the current state of the project's documentation and development completeness for publication purposes.

| Section | Rating (/ 10) | Justification |
|---|---|---|
| Problem Statement | 8 / 10 | Clearly articulated in README and code structure; needs formal academic framing |
| Objectives | 8 / 10 | Well-defined through feature set; needs formal enumeration with measurable outcomes |
| Methodology | 7 / 10 | Core algorithms extractable from source code; missing documentation for patient module and partial schedulers |
| Novelty | 7 / 10 | Two-stage protocol + escalation + split planning are genuinely novel; requires comparison with prior art to solidify claims |
| Architecture | 6 / 10 | Logical architecture understood from code; formal diagram (UML/component) is missing |
| Experimental Results | 1 / 10 | No experiments conducted; no performance metrics collected; this is the largest gap |
| Evaluation | 2 / 10 | RMSE and MAE computed in code but no actual values recorded; no user study or benchmarking |
| References | 0 / 10 | No references exist yet; a complete literature survey must be conducted |
| Publication Readiness | 3 / 10 | The engineering work is strong; the research validation (experiments, evaluation, related work) is entirely absent |
| Overall Readiness | 5 / 10 | Excellent engineering foundation with well-structured, novel features, but research methodology, evaluation, and literature review are not yet conducted |

### Publication Readiness Percentage

**Overall Readiness: ~42%**

> The HospiSync project represents a technically well-implemented and feature-rich final year project with several genuinely novel engineering contributions. However, its readiness for publication in an IEEE, Springer, Elsevier, or Scopus-indexed journal is currently estimated at **42%**. The primary gaps are:
> 1. **No experimental results** (0% complete) — no performance benchmarks, no forecasting accuracy measurements, no user studies.
> 2. **No formal literature review or references** (0% complete).
> 3. **Missing formal diagrams** — ER diagram, architecture diagram, sequence diagrams, flowcharts (approximately 20% complete based on 4 screenshots available).
> 4. **Missing formal academic writing** — the README and code comments must be transformed into IEEE-format structured writing.
>
> **Recommended immediate next steps:**
> 1. Conduct all experiments described in Section 16 (at minimum: API response time, forecasting MAE/RMSE, escalation success rate).
> 2. Capture all screenshots listed in Section 15 from a running deployment.
> 3. Create ER diagram, architecture diagram, and flowchart diagrams.
> 4. Conduct a literature survey and collect ≥30 references.
> 5. Write the formal paper sections (Introduction, Related Work, Methodology, Results, Conclusion) using this document as the foundation.

---

*End of Research Paper Preparation Document*

---

> **Document Integrity Statement:** All technical details in this document are extracted directly from the source code files, configuration files, and README of the HospiSync project as accessed on July 14, 2026. No results, references, or experiments have been fabricated. All sections marked "Information Required from Author" represent genuine gaps that must be addressed by the author before publication.
