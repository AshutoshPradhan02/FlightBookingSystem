import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    static Connection conn = DBConnection.getConnection();

    public static User loginOrRegister(String username, String password) {
        try {
            PreparedStatement check = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=? ORDER BY role = 'admin' DESC LIMIT 1");
            check.setString(1, username);
            check.setString(2, password);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                return new User(id, username, role);
            }

            PreparedStatement exists = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            exists.setString(1, username);
            ResultSet rs2 = exists.executeQuery();
            if (rs2.next()) {
                System.out.println("Incorrect password.");
                return null;
            }

            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, 'local')",
                Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, username);
            insert.setString(2, password);
            insert.executeUpdate();

            ResultSet keys = insert.getGeneratedKeys();
            if (keys.next()) {
                System.out.println("Registration successful. Welcome, " + username + " (local)");
                return new User(keys.getInt(1), username, "local");
            }
        } catch (Exception e) {
            System.out.println("Login/Register error: " + e.getMessage());
        }
        return null;
    }

    public static void displayAvailableFlights() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM flights");
            System.out.println("\nAvailable Flights:");
            while (rs.next()) {
                new Flight(
                    rs.getInt("id"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("time"),
                    rs.getDouble("price")
                ).display();
            }
        } catch (Exception e) {
            System.out.println("Flight loading error: " + e.getMessage());
        }
    }

    public static void bookFlight(User user) {
        int id = Utils.inputInt("Enter Flight ID to book: ");
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bookings (user_id, flight_id) VALUES (?, ?)");
            ps.setInt(1, user.getId());
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Booking confirmed!");
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public static void viewBookings(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT b.id AS booking_id, f.* FROM bookings b JOIN flights f ON b.flight_id = f.id WHERE b.user_id=?");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("\nYour Bookings:");
            while (rs.next()) {
                new Booking(
                    rs.getInt("booking_id"),
                    new Flight(
                        rs.getInt("id"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("time"),
                        rs.getDouble("price")
                    )
                ).display();
            }
        } catch (Exception e) {
            System.out.println("View bookings error: " + e.getMessage());
        }
    }

    public static void cancelBooking(User user) {
        int id = Utils.inputInt("Enter Booking ID to cancel: ");
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM bookings WHERE id=? AND user_id=?");
            ps.setInt(1, id);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            System.out.println("Booking cancelled!");
        } catch (Exception e) {
            System.out.println("Cancel failed: " + e.getMessage());
        }
    }

    public static void addFlight() {
        String from = Utils.input("From City: ");
        String to = Utils.input("To City: ");
        String time = Utils.input("Time: ");
        double price = Utils.inputDouble("Price: ");

        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO flights (from_city, to_city, time, price) VALUES (?, ?, ?, ?)");
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, time);
            ps.setDouble(4, price);
            ps.executeUpdate();
            System.out.println("Flight added!");
        } catch (Exception e) {
            System.out.println("Add flight failed: " + e.getMessage());
        }
    }

    public static void viewAllUsers() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, username, role FROM users");

            System.out.println("\nRegistered Users:");
            while (rs.next()) {
                System.out.printf("ID: %d | Username: %s | Role: %s%n",
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role"));
            }
        } catch (Exception e) {
            System.out.println("Error viewing users: " + e.getMessage());
        }
    }

    public static void removeUser() {
        int id = Utils.inputInt("Enter user ID to remove: ");
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("User removed successfully.");
            } else {
                System.out.println("No user found with that ID.");
            }
        } catch (Exception e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }
}
