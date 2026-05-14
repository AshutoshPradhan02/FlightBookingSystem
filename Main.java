import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connected to MySQL successfully!");
        } else {
            System.out.println("❌ Failed to connect to MySQL.");
            return;
        }

        System.out.println("🎫 === Flight Booking System ===");

        String username = Utils.input("👤 Enter username: ");
        String password = Utils.input("🔐 Enter password: ");
        
        User user = Database.loginOrRegister(username, password); 
        if (user == null) {
            System.out.println("❌ Login failed.");
            return;
        }
        // Main system menu after successful login
        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1️⃣ View Flights");
            System.out.println("2️⃣ Book Flight");
            System.out.println("3️⃣ View My Bookings");
            System.out.println("4️⃣ Cancel Booking");
            // Admin specific options
            if (user.isAdmin()) {
                System.out.println("🔧 Admin Options:");
                System.out.println("5️⃣ Add Flight");
                System.out.println("6️⃣ View All Users");
                System.out.println("7️⃣ Remove a User");
                System.out.println("8️⃣ Exit");
            } else {
                System.out.println("5️⃣ Exit");
            }
            int choice = Integer.parseInt(Utils.input("➡️ Choose option: "));           
            switch (choice) {
                case 1:
                    Database.displayAvailableFlights();
                    break;
                case 2:
                    Database.bookFlight(user);
                    break;
                case 3:
                    Database.viewBookings(user);
                    break;
                case 4:
                    Database.cancelBooking(user);
                    break;
                case 5:
                    if (user.isAdmin()) {
                        Database.addFlight();
                    } else {
                        System.out.println("👋 Exiting... Have a safe journey!");
                        return;
                    }
                    break;
                case 6:
                    if (user.isAdmin()) {
                        Database.viewAllUsers();
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                    break;
                case 7:
                    if (user.isAdmin()) {
                        Database.removeUser();
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                    break;
                case 8:
                    if (user.isAdmin()) {
                        System.out.println("👋 Exiting... Have a safe journey!");
                        return;
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }
}
