import java.util.Scanner;

public class Utils {
    public static String input(String message) {
        System.out.print(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
