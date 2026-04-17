# Domain Model

## Entities

### User
- id (PK)
- username
- password
- status (ACTIVE, DISABLED)
- createdAt

### Device
- id (PK)
- brand
- model
- cpu
- ram
- manufactureYear
- status (AVAILABLE, ASSIGNED, RETIRED)

### Assignment
- id (PK)
- userId (FK → User)
- deviceId (FK → Device)
- assignedDate
- returnedDate

## Relationships
- User → Assignment (1:N)
- Device → Assignment (1:N)

## Key Design Decisions
- Assignment tracks device history
- Device is not directly linked to User
- Supports audit & reporting

## Derived Logic

### Device Age
age = currentYear - manufactureYear

### EOL Definition
EOL = 5 years
Approaching EOL = age >= 4