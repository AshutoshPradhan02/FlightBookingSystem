public class Flight {
    public int id;
    public String fromCity, toCity, time;
    public double price;

    public Flight(int id, String fromCity, String toCity, String time, double price) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.time = time;
        this.price = price;
    }

    public void display() {
        System.out.println("Flight " + id + ": " + fromCity + " -> " + toCity
            + " | " + time + " | Rs. " + price);
    }
}
