public class Booking {
    private int id;
    private Flight flight;

    public Booking(int id, Flight flight) {
        this.id = id;
        this.flight = flight;
    }

    public int getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void display() {
        System.out.println("🧾 Booking " + id + ": ");
        flight.display();
    }
}
