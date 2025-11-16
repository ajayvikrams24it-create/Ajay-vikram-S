# Police Records Management System

A compact desktop Java application for managing police criminal records with a secure login, colorful UI, and MySQL-backed storage. This project is a demonstration/prototype and includes a GUI written using AWT/Swing styles and direct JDBC access via MySQL Connector/J.

---

## Features

- Secure login screen (default admin credentials: `admin` / `police123`) — change before production
- Colorful, modern GUI dashboard with:
  - Add, update, delete criminal records
  - Search by name
  - Record details view (formatted)
  - Live statistics panel
- MySQL integration for persistent storage
- Simple DAO (`src.dao.CriminalDAO`) and model (`src.models.CriminalRecord`)

---

## Requirements

- Java JDK 11 or newer (verify with `java -version`)
- MySQL server (tested with 8.x)
- MySQL Connector/J (9.5.0 included in `lib/mysql-connector-j-9.5.0/`)

---

## Database Setup

Run the following SQL statements in your MySQL client to create the database and required table:

```sql
CREATE DATABASE police_records;
USE police_records;

CREATE TABLE criminal_records (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  age INT,
  crime_type VARCHAR(255),
  crime_date DATE,
  address TEXT,
  phone VARCHAR(50),
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Notes:
- Update `src/DatabaseConnection.java` to set the correct `USERNAME` and `PASSWORD` for your DB.
- `DatabaseConnection` uses JDBC URL: `jdbc:mysql://localhost:3306/police_records`. Update as needed.

---

## Compile and Run (Windows / PowerShell)

Open PowerShell in the project root (where `src/` and `lib/` exist) and run:

```powershell
# Compile all source files into the bin folder
javac -d bin -cp "lib/mysql-connector-j-9.5.0/*" src\*.java

# Run the application — the login screen is the recommended entry point
java -cp "bin;lib/mysql-connector-j-9.5.0/*" src.LoginSystem

# If `PoliceRecordsSystem.main` launches the app (or you prefer to run it directly)
# java -cp "bin;lib/mysql-connector-j-9.5.0/*" src.PoliceRecordsSystem
```

If you see compile errors relating to Swing (`BorderFactory`, `Box`) or `setOpaque`, the project mixes AWT and Swing; consider editing imports or replacing `Panel` with `JPanel` in relevant files.

---

## Default Credentials (Development only)

- Username: `admin`
- Password: `police123`

Change these credentials in `src/LoginSystem.java` and integrate a secure authentication mechanism (hashing, salted storage) for production.

---

## Development Notes & Security

- This project stores DB credentials in plain text in `src/DatabaseConnection.java`. Change this to use environment variables or a config file with restricted permissions.
- Avoid committing production DB passwords to source control.
- Consider migrating UI to Swing/JavaFX consistently; mixing AWT and Swing can lead to toolkit issues.
- Use prepared statements (already in `CriminalDAO`) — good.
- Sanitize inputs when adding and updating records to avoid SQL injection or bad data (current prepared statements address this for SQL injection at least).

---

## Directory structure

- `src/` — Java source (main UI, DAO, models)
- `lib/` — third-party libraries (MySQL Connector/J included)
- `bin/` — compiled classes (created after `javac -d bin`)

---

## Contribution & Next Steps

- Improve the authentication flow (persistent user store with hashed passwords)
- Add better error handling & user feedback for DB failures
- Add unit tests and integration tests for DAOs
- Convert GUI code to consistent Swing or JavaFX for improved look-and-feel

---

## License

This repository does not include a license file. Clarify licensing before sharing publicly.

---

If you want, I can create a `README` variant with more deployment steps (Docker + MySQL), or add a `setup.sql` script to the repo for automatic DB creation. Which would you prefer next?
