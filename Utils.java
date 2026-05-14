import java.util.Scanner;

public class Utils {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String input(String message) {
        System.out.print(message);
        return SCANNER.nextLine();
    }

    public static int inputInt(String message) {
        while (true) {
            String value = input(message);
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double inputDouble(String message) {
        while (true) {
            String value = input(message);
            try {
                return Double.parseDouble(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
