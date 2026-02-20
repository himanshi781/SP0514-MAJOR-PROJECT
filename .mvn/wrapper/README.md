
# Smart Attendance Fraud Detection System
 ## OVERVIEW

The Smart Attendance Fraud Detection System is a backend-focused solution designed to prevent proxy and remote attendance fraud using network validation and behavioral analysis.

The system ensures attendance is marked only when users are connected to an authorized network (campus WiFi simulation) and assigns fraud scores for suspicious activity.

Instead of building a traditional frontend, the project uses FastAPI with Swagger UI as an interactive interface to test and demonstrate backend APIs.

## TECH STACK
- **Backend**:-
Java 17,
Spring Boot,
Spring Data JPA,
MySQL

- **Interface Layer**:-
FastAPI (SwaggerUI)


- **API Testing & Documentation**:-
Swagger UI (FastAPI auto-generated docs)

- **Architecture**:- 
    Swagger UI (FastAPI)
            ↓
    FastAPI Interface Layer
            ↓
    Spring Boot Backend
            ↓
    Fraud Detection Engine
            ↓
    MySQL Database

- **Features**:-
    Create users, 
    Update users, 
    Delete users, 
    Retrieve user list

- **WiFi-Based Attendance Validation**:-
Attendance is allowed only when:
    Network IP belongs to trusted range
    Connected to authorized WiFi (SSID simulation).
    This prevents proxy attendance and remote misuse.

- **Attendance Management**:-
    Retrieve attendance history, 
    Store metadata (IP, SSID, timestamp, device ID), 
    Mark attendance

- **Fraud Detection**:-
        Rapid attendance marking, 
        Multiple device usage, 
        Network mismatch, 
        Behavioral anomalies, 
        Each attendance entry receives a Fraud Score (0–100).

- **Analytics**:-
    Suspicious activity insights, 
    Attendance behavior monitoring, 
    Fraud percentage per user

## SETUP INSTRUCTIONS

1️⃣ *Clone repository*:-
git clone https://github.com/himanshi781/SP0514-MAJOR-PROJECT
cd smart-attendance-fraud-system

2️⃣ *Setup database*:-
CREATE DATABASE attendance_db;

3️⃣ *Run Spring Boot*:-
mvn spring-boot:run

4️⃣ Open Swagger UI:-
http://localhost:8080/swagger-ui/index.html#/

- **Fraud Prevention Strategy**

    Network-based geofencing, 
    Multi-layer validation (SSID + IP), 
    Behavioral analysis, 
    Fraud scoring

- **Future Enhancements**

    Face recognition validation, 
    GPS-based attendance, 
    Device fingerprinting, 
    Real-time fraud alerts, 
    Admin analytics dashboard

