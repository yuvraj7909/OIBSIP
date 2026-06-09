import java.util.Scanner;

public class UI {

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print("  " + prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  [!]   Please enter a valid number."); }
        }
    }

    public static String readStr(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static void pause(Scanner sc) {
        System.out.print("\n  Press Enter to continue...");
        sc.nextLine();
    }

    public static void clearScreen() {
        // Works on most terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
