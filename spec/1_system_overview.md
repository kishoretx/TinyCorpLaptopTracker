# Tiny Corp Laptop Tracker – System Overview

## Objective
Build a lightweight asset management system using AI-assisted development.

## Users
- Service Desk Employee
- Service Desk Manager

## Core Features
- User login (JWT-based)
- Create/disable user
- Create device
- Assign device to user
- Mark device returned

## Reports
- Average age of devices
- Users with assigned devices
- Devices approaching EOL

## Constraints
- JWT Authentication
- REST APIs
- Relational Database

## Architecture
- Backend: REST API (Spring Boot / Node)
- Database: H2 (in-memory for development)
- Auth: External JWT provider

## Design Principles
- Normalize data
- Track assignment history
- Avoid direct User → Device coupling