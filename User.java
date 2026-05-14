public class User {
    private int id;
    private String username;
    private String role;
    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getRole() {
        return role;
    }
    // Optional: if you ever want to change role dynamically
    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    public boolean isLocal() {
        return "local".equalsIgnoreCase(role);
    }
    @Override
    public String toString() {
        return "User [ID=" + id + ", Username=" + username + ", Role=" + role + "]";
    }
}
