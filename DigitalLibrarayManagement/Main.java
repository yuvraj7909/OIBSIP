import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static LibraryService svc = new LibraryService();

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.setErr(new PrintStream(System.err, true, "UTF-8"));

        printBanner();
        System.out.println("  System ready. Sample data loaded.\n");
        System.out.println("  +------------------------------------------+");
        System.out.println("  |  Default Credentials:                    |");
        System.out.println("  |  Admin -> admin@library.com / admin123   |");
        System.out.println("  |  User  -> rahul@email.com  / pass123     |");
        System.out.println("  +------------------------------------------+");
        UI.pause(sc);

        boolean running = true;
        while (running) {
            printWelcome();
            int choice = UI.readInt(sc, "Choice: ");

            switch (choice) {
                case 1 -> {
                    Member logged = doLogin();
                    if (logged != null) {
                        if (logged.getRole() == Member.Role.ADMIN) {
                            new AdminMenu(svc, sc).show(logged);
                        } else {
                            new UserMenu(svc, sc).show(logged);
                        }
                    }
                }
                case 2 -> quickRegister();
                case 0 -> {
                    System.out.println("\n  Thank you for using Digital Library Management System. Goodbye!\n");
                    running = false;
                }
                default -> System.out.println("  [!] Invalid option.");
            }
        }
        sc.close();
    }

    static void printWelcome() {
        System.out.println("\n  +======================================+");
        System.out.println("  |     WELCOME TO DIGITAL LIBRARY       |");
        System.out.println("  +======================================+");
        System.out.println("  |   1. Login                           |");
        System.out.println("  |   2. New User Registration           |");
        System.out.println("  |   0. Exit                            |");
        System.out.println("  +======================================+");
    }

    static Member doLogin() {
        System.out.println("\n  -- Login --");
        String email = UI.readStr(sc, "  Email    : ");
        String pass  = UI.readStr(sc, "  Password : ");
        Member m = svc.login(email, pass);
        if (m == null) {
            System.out.println("  [X] Invalid email or password.");
            UI.pause(sc);
            return null;
        }
        System.out.println("  [OK] Welcome, " + m.getName() + "! [" + m.getRole() + "]");
        return m;
    }

    static void quickRegister() {
        System.out.println("\n  -- New User Registration --");
        String name  = UI.readStr(sc, "  Full Name : ");
        String email = UI.readStr(sc, "  Email     : ");
        String phone = UI.readStr(sc, "  Phone     : ");
        String pass  = UI.readStr(sc, "  Password  : ");
        Member m = svc.registerMember(name, email, phone, pass, Member.Role.USER);
        System.out.println("  [OK] Registration successful! Your ID: " + m.getId());
        System.out.println("  You can now login with your email and password.");
        UI.pause(sc);
    }

    static void printBanner() {
        System.out.println();
        System.out.println("  +================================================+");
        System.out.println("  |                                                |");
        System.out.println("  |    DIGITAL LIBRARY MANAGEMENT SYSTEM          |");
        System.out.println("  |                 Version 2.0                    |");
        System.out.println("  |                                                |");
        System.out.println("  |   Features:  Book Management                  |");
        System.out.println("  |              Member Management                 |");
        System.out.println("  |              Issue / Return / Fine             |");
        System.out.println("  |              Advance Booking                   |");
        System.out.println("  |              Report Generation                 |");
        System.out.println("  |              Admin & User Roles                |");
        System.out.println("  |                                                |");
        System.out.println("  +================================================+");
        System.out.println();
    }
}