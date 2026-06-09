import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private LibraryService svc;
    private Scanner sc;

    public UserMenu(LibraryService svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void show(Member user) {
        boolean back = false;
        while (!back) {
            System.out.println("\n  +======================================+");
            System.out.printf ("  |    USER PORTAL  [%s]%s|%n", user.getName(),
                    " ".repeat(Math.max(0, 18 - user.getName().length())));
            System.out.println("  +======================================+");
            System.out.printf ("  |  Books Issued : %-3d  Fine: Rs.%-6.1f |%n",
                    user.getBooksCount(), user.getTotalFine());
            System.out.println("  +======================================+");
            System.out.println("  |   1. Browse All Books                |");
            System.out.println("  |   2. Search Books                    |");
            System.out.println("  |   3. View Available Books            |");
            System.out.println("  |   4. Issue a Book                    |");
            System.out.println("  |   5. Return a Book                   |");
            System.out.println("  |   6. Advance Book Reservation        |");
            System.out.println("  |   7. My Issued Books                 |");
            System.out.println("  |   8. Pay Fine                        |");
            System.out.println("  |   9. Send Email Query                |");
            System.out.println("  +======================================+");
            System.out.println("  |   0. Logout                          |");
            System.out.println("  +======================================+");

            int ch = UI.readInt(sc, "Choice: ");
            switch (ch) {
                case 1 -> browseBooks();
                case 2 -> searchBooks();
                case 3 -> availableBooks();
                case 4 -> issueBook(user);
                case 5 -> returnBook(user);
                case 6 -> advanceBook(user);
                case 7 -> myBooks(user);
                case 8 -> payFine(user);
                case 9 -> emailQuery(user);
                case 0 -> back = true;
                default -> System.out.println("  [X]  Invalid choice.");
            }
        }
    }

    private void browseBooks() {
        System.out.println("\n  -- All Books --");
        List<Book> list = svc.getAllBooks();
        svc.printBookHeader();
        list.forEach(System.out::println);
        LibraryService.printLine(100);
        UI.pause(sc);
    }

    private void searchBooks() {
        String kw = UI.readStr(sc, "  Search (title/author/genre/isbn): ");
        List<Book> res = svc.searchBooks(kw);
        if (res.isEmpty()) { System.out.println("  No results found."); UI.pause(sc); return; }
        System.out.println("  Found " + res.size() + " result(s):");
        svc.printBookHeader();
        res.forEach(System.out::println);
        LibraryService.printLine(100);
        UI.pause(sc);
    }

    private void availableBooks() {
        System.out.println("\n  -- Available Books --");
        List<Book> list = svc.getAvailableBooks();
        if (list.isEmpty()) { System.out.println("  No books available right now."); UI.pause(sc); return; }
        svc.printBookHeader();
        list.forEach(System.out::println);
        LibraryService.printLine(100);
        UI.pause(sc);
    }

    private void issueBook(Member user) {
        availableBooks();
        int bookId = UI.readInt(sc, "  Enter Book ID to issue: ");
        String res = svc.issueBook(bookId, user.getId());
        if (res.equals("OK")) {
            Book b = svc.getBook(bookId);
            System.out.println("  [OK]  Book issued! Please return by: " + b.getDueDate());
        } else {
            System.out.println("  [X]  " + res);
        }
        UI.pause(sc);
    }

    private void returnBook(Member user) {
        myBooks(user);
        int bookId = UI.readInt(sc, "  Enter Book ID to return: ");
        String res = svc.returnBook(bookId);
        if (res.startsWith("FINE:")) {
            System.out.println("  [OK]  Returned. Fine of Rs." + res.substring(5) + " has been added to your account.");
        } else if (res.equals("OK")) {
            System.out.println("  [OK]  Book returned successfully. Thank you!");
        } else {
            System.out.println("  [X]  " + res);
        }
        UI.pause(sc);
    }

    private void advanceBook(Member user) {
        System.out.println("\n  -- Advance Reservation (Issued Books Only) --");
        List<Book> list = svc.getAllBooks();
        svc.printBookHeader();
        list.stream().filter(b -> !b.isAvailable()).forEach(System.out::println);
        LibraryService.printLine(100);
        int bookId = UI.readInt(sc, "  Enter Book ID to reserve: ");
        String res = svc.advanceBook(bookId, user.getId());
        System.out.println(res.equals("OK") ? "  [OK]  Reservation confirmed! You'll get it when returned." : "  [X]  " + res);
        UI.pause(sc);
    }

    private void myBooks(Member user) {
        System.out.println("\n  -- Your Issued Books --");
        if (user.getIssuedBookIds().isEmpty()) {
            System.out.println("  You have no books currently issued.");
            UI.pause(sc);
            return;
        }
        svc.printBookHeader();
        user.getIssuedBookIds().forEach(idStr -> {
            Book b = svc.getBook(Integer.parseInt(idStr));
            if (b != null) System.out.println(b);
        });
        LibraryService.printLine(100);
        UI.pause(sc);
    }

    private void payFine(Member user) {
        if (user.getTotalFine() == 0) {
            System.out.println("  [OK]  You have no pending fines!");
        } else {
            System.out.println("  Your pending fine: Rs." + user.getTotalFine());
            String confirm = UI.readStr(sc, "  Pay now? (y/n): ");
            if (confirm.equalsIgnoreCase("y")) {
                String res = svc.payFine(user.getId());
                System.out.println(res.startsWith("PAID:") ? "  [OK]  Rs." + res.substring(5) + " paid!" : "  [X]  " + res);
            }
        }
        UI.pause(sc);
    }

    private void emailQuery(Member user) {
        System.out.println("\n  -- Send Email Query to Admin --");
        System.out.println("  From  : " + user.getEmail());
        String subject = UI.readStr(sc, "  Subject : ");
        String message = UI.readStr(sc, "  Message : ");
        System.out.println("\n  +================================+");
        System.out.println("  |     EMAIL PREVIEW              |");
        System.out.println("  +================================+");
        System.out.printf ("  |  To      : admin@library.com   |%n");
        System.out.printf ("  |  From    : %-20s  |%n", user.getEmail().length() > 20 ? user.getEmail().substring(0,20) : user.getEmail());
        System.out.printf ("  |  Subject : %-20s  |%n", subject.length() > 20 ? subject.substring(0,20) : subject);
        System.out.println("  +================================+");
        System.out.println("  [OK]  Query submitted! Admin will respond to " + user.getEmail());
        System.out.println("  [i]   (In production, SMTP would send a real email via JavaMail API)");
        UI.pause(sc);
    }
}
