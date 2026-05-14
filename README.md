# Flight Booking System

A simple Java console application for booking flights with MySQL storage. Users can register, log in, view flights, book tickets, view their bookings, and cancel bookings. Admin users can also add flights, view users, and remove users.

## Features

- User login and automatic local-user registration
- Admin and local-user menu flows
- Flight listing and booking
- Booking history and cancellation
- Admin flight and user management
- MySQL-backed persistence through JDBC

## Requirements

- Java JDK 11 or newer
- MySQL Server
- MySQL Connector/J

The project currently includes `lib/mysql-connector-j-9.2.0.jar`, so it can be compiled directly from this folder.

## Database Setup

1. Start MySQL.
2. Run the schema file:

```sql
SOURCE database.sql;
```

If you are using the MySQL command line from this project folder, you can also run:

```bash
mysql -u root -p < database.sql
```

The default database is `flightdb`. The seed admin account is:

- Username: `admin`
- Password: `admin123`

## Configuration

`DBConnection.java` reads database settings from Java system properties first, then environment variables, then local defaults.

| Setting | Java property | Environment variable | Default |
| --- | --- | --- | --- |
| JDBC URL | `flight.db.url` | `FLIGHT_DB_URL` | `jdbc:mysql://localhost:3306/flightdb` |
| Username | `flight.db.user` | `FLIGHT_DB_USER` | `root` |
| Password | `flight.db.password` | `FLIGHT_DB_PASSWORD` | empty |

Example on Windows PowerShell:

```powershell
$env:FLIGHT_DB_PASSWORD = "your_mysql_password"
```

## Compile and Run

From the project folder:

```powershell
javac -cp "lib/mysql-connector-j-9.2.0.jar;." *.java
java -cp "lib/mysql-connector-j-9.2.0.jar;." Main
```

On macOS/Linux, use `:` instead of `;` in the classpath:

```bash
javac -cp "lib/mysql-connector-j-9.2.0.jar:." *.java
java -cp "lib/mysql-connector-j-9.2.0.jar:." Main
```

## Project Structure

```text
FlightBookingSystem/
├── Booking.java
├── Database.java
├── DBConnection.java
├── Flight.java
├── Main.java
├── User.java
├── Utils.java
├── database.sql
└── lib/
    └── mysql-connector-j-9.2.0.jar
```

## Notes

- Compiled `.class` files are ignored by Git and can be regenerated with `javac`.
- For a real production system, store hashed passwords instead of plain text.
