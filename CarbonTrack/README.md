# Carbon Track — Household Carbon Footprint Calculator with Challenges

Java + DBMS mini project using:
- Spring Boot (Spring Initializr)
- IntelliJ IDEA
- MySQL via XAMPP
- Postman
- HTML, CSS, JavaScript

## Requirements
1. JDK 17+
2. IntelliJ IDEA
3. XAMPP (start MySQL)
4. Maven (or use IntelliJ's bundled Maven)
5. Postman (optional, for API testing)

## 1. Create the database
Start **MySQL** in XAMPP, then open phpMyAdmin or MySQL client.

Run:
```sql
CREATE DATABASE carbon_track;
```

The application automatically creates/updates the tables using JPA.

Default database configuration is in:
`src/main/resources/application.properties`

Default:
- host: localhost
- port: 3306
- database: carbon_track
- username: root
- password: empty

If your XAMPP MySQL has a password, change `spring.datasource.password`.

## 2. Run in IntelliJ
1. Extract the ZIP.
2. Open the `CarbonTrack` folder in IntelliJ IDEA.
3. Wait for Maven dependencies to download.
4. Run `CarbonTrackApplication.java`.
5. Open:
   http://localhost:8080

The frontend is served by Spring Boot from `src/main/resources/static`.

## 3. Test with Postman
Base URL:
`http://localhost:8080/api`

### Add household calculation
POST `/api/calculations`
Content-Type: application/json

```json
{
  "householdName": "My Home",
  "electricityKwh": 120,
  "lpgKg": 5,
  "petrolLitres": 20,
  "dieselLitres": 0,
  "waterLitres": 3000,
  "wasteKg": 10
}
```

### Get all calculations
GET `/api/calculations`

### Get one calculation
GET `/api/calculations/1`

### Delete one
DELETE `/api/calculations/1`

### Get challenges
GET `/api/challenges`

### Get completed challenges
GET `/api/challenges/completed`

### Toggle challenge
POST `/api/challenges/1/toggle`

## Calculation formula
The demo uses simple educational emission factors:
- Electricity: 0.82 kg CO₂e / kWh
- LPG: 3.00 kg CO₂e / kg
- Petrol: 2.31 kg CO₂e / litre
- Diesel: 2.68 kg CO₂e / litre
- Water: 0.0003 kg CO₂e / litre
- Waste: 0.50 kg CO₂e / kg

These factors are configurable in `CarbonCalculatorService.java` and are intended for an academic prototype, not an official carbon accounting standard.

## Project structure
```text
CarbonTrack/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/carbontrack/
        │   ├── CarbonTrackApplication.java
        │   ├── controller/
        │   ├── dto/
        │   ├── model/
        │   ├── repository/
        │   └── service/
        └── resources/
            ├── application.properties
            └── static/
                ├── index.html
                ├── style.css
                └── app.js
```
