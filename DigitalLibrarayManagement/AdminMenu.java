import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private LibraryService svc;
    private Scanner sc;

    public AdminMenu(LibraryService svc, Scanner sc) {
        this.svc = svc;
        this.sc = sc;
    }

    public void show(Member admin) {
        boolean back = false;
        while (!back) {
            System.out.println("\n  +======================================+");
            System.out.printf ("  |    ADMIN PANEL  [%s]%s|%n", admin.getName(),
                    " ".repeat(Math.max(0, 19 - admin.getName().length())));
            System.out.println("  +======================================+");
            System.out.println("  |  BOOKS                               |");
            System.out.println("  |   1. Add Book                        |");
            System.out.println("  |   2. Update Book                     |");
            System.out.println("  |   3. Remove Book                     |");
            System.out.println("  |   4. List All Books                  |");
            System.out.println("  |   5. Search Books                    |");
            System.out.println("  +======================================+");
            System.out.println("  |  MEMBERS                             |");
            System.out.println("  |   6. Register Member                 |");
            System.out.println("  |   7. Update Member                   |");
            System.out.println("  |   8. Remove Member                   |");
            System.out.println("  |   9. List All Members                |");
            System.out.println("  +======================================+");
            System.out.println("  |  CIRCULATION                         |");
            System.out.println("  |  10. Issue Book to Member            |");
            System.out.println("  |  11. Return Book                     |");
            System.out.println("  |  12. Collect Fine                    |");
            System.out.println("  +======================================+");
            System.out.println("  |  REPORTS                             |");
            System.out.println("  |  13. Issued Books Report             |");
            System.out.println("  |  14. Overdue Books Report            |");
            System.out.println("  |  15. Transaction History             |");
            System.out.println("  |  16. Library Statistics              |");
            System.out.println("  +======================================+");
            System.out.println("  |   0. Logout                          |");
            System.out.println("  +======================================+");

            int ch = UI.readInt(sc, "Choice: ");
            switch (ch) {
                case 1  -> addBook();
                case 2  -> updateBook();
                case 3  -> removeBook();
                case 4  -> listAllBooks();
                case 5  -> searchBooks();
                case 6  -> registerMember();
                case 7  -> updateMember();
                case 8  -> removeMember();
                case 9  -> listAllMembers();
                case 10 -> issueBook();
                case 11 -> returnBook();
                case 12 -> collectFine();
                case 13 -> { System.out.println("\n  -- Issued Books --"); svc.reportIssuedBooks(); UI.pause(sc); }
                case 14 -> { System.out.println("\n  -- Overdue Books --"); svc.reportOverdueBooks(); UI.pause(sc); }
                case 15 -> { System.out.println("\n  -- Transaction History --"); svc.reportTransactions(); UI.pause(sc); }
                case 16 -> { System.out.println(); svc.reportStats(); UI.pause(sc); }
                case 0  -> back = true;
                default -> System.out.println("  [X]  Invalid choice.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n  -- Add New Book --");
        String title  = UI.readStr(sc, "  Title  : ");
        String author = UI.readStr(sc, "  Author : ");
        String genre  = UI.readStr(sc, "  Genre  : ");
        String isbn   = UI.readStr(sc, "  ISBN   : ");
        int year      = UI.readInt(sc, "  Year   : ");
        Book b = svc.addBook(title, author, genre, isbn, year);
        System.out.println("  [OK]  Book added! ID = " + b.getId());
        UI.pause(sc);
    }

    private void updateBook() {
        listAllBooks();
        int id = UI.readInt(sc, "  Enter Book ID to update (0=cancel): ");
        if (id == 0) return;
        System.out.println("  (Press Enter to keep current value)");
        String title  = UI.readStr(sc, "  New Title  : ");
        String author = UI.readStr(sc, "  New Author : ");
        String genre  = UI.readStr(sc, "  New Genre  : ");
        System.out.println(svc.updateBook(id, title, author, genre) ? "  [OK]  Updated!" : "  [X]  Book not found.");
        UI.pause(sc);
    }

    private void removeBook() {
        listAllBooks();
        int id = UI.readInt(sc, "  Enter Book ID to remove: ");
        String res = svc.removeBook(id);
        System.out.println(res.equals("OK") ? "  [OK]  Book removed!" : "  [X]  " + res);
        UI.pause(sc);
    }

    private void listAllBooks() {
        System.out.println();
        List<Book> list = svc.getAllBooks();
        if (list.isEmpty()) { System.out.println("  No books."); return; }
        svc.printBookHeader();
        list.forEach(System.out::println);
        LibraryService.printLine(100);
    }

    private void searchBooks() {
        String kw = UI.readStr(sc, "  Search: ");
        List<Book> res = svc.searchBooks(kw);
        if (res.isEmpty()) { System.out.println("  No results."); return; }
        svc.printBookHeader();
        res.forEach(System.out::println);
        LibraryService.printLine(100);
        UI.pause(sc);
    }

    private void registerMember() {
        System.out.println("\n  -- Register Member --");
        String name  = UI.readStr(sc, "  Name     : ");
        String email = UI.readStr(sc, "  Email    : ");
        String phone = UI.readStr(sc, "  Phone    : ");
        String pass  = UI.readStr(sc, "  Password : ");
        System.out.print("  Role (1=Admin, 2=User): ");
        int r = UI.readInt(sc, "");
        Member.Role role = (r == 1) ? Member.Role.ADMIN : Member.Role.USER;
        Member m = svc.registerMember(name, email, phone, pass, role);
        System.out.println("  [OK]  Member registered! ID = " + m.getId());
        UI.pause(sc);
    }

    private void updateMember() {
        listAllMembers();
        String id = UI.readStr(sc, "  Enter Member ID to update: ");
        System.out.println("  (Press Enter to keep current value)");
        String name  = UI.readStr(sc, "  New Name  : ");
        String email = UI.readStr(sc, "  New Email : ");
        String phone = UI.readStr(sc, "  New Phone : ");
        System.out.println(svc.updateMember(id, name, email, phone) ? "  [OK]  Updated!" : "  [X]  Member not found.");
        UI.pause(sc);
    }

    private void removeMember() {
        listAllMembers();
        String id = UI.readStr(sc, "  Enter Member ID to remove: ");
        String res = svc.removeMember(id);
        System.out.println(res.equals("OK") ? "  [OK]  Member removed!" : "  [X]  " + res);
        UI.pause(sc);
    }

    private void listAllMembers() {
        System.out.println();
        List<Member> list = svc.getAllMembers();
        svc.printMemberHeader();
        list.forEach(System.out::println);
        LibraryService.printLine(96);
    }

    private void issueBook() {
        listAllBooks();
        int bookId = UI.readInt(sc, "  Book ID  : ");
        listAllMembers();
        String memberId = UI.readStr(sc, "  Member ID: ");
        String res = svc.issueBook(bookId, memberId);
        if (res.equals("OK")) {
            Book b = svc.getBook(bookId);
            System.out.println("  [OK]  Issued! Due date: " + b.getDueDate());
        } else {
            System.out.println("  [X]  " + res);
        }
        UI.pause(sc);
    }

    private void returnBook() {
        svc.reportIssuedBooks();
        int bookId = UI.readInt(sc, "  Book ID to return: ");
        String res = svc.returnBook(bookId);
        if (res.startsWith("FINE:")) {
            System.out.println("  [OK]  Returned! Fine charged: Rs." + res.substring(5));
        } else if (res.equals("OK")) {
            System.out.println("  [OK]  Book returned successfully!");
        } else {
            System.out.println("  [X]  " + res);
        }
        UI.pause(sc);
    }

    private void collectFine() {
        listAllMembers();
        String id = UI.readStr(sc, "  Member ID: ");
        String res = svc.payFine(id);
        if (res.startsWith("PAID:")) {
            System.out.println("  [OK]  Fine of Rs." + res.substring(5) + " collected!");
        } else {
            System.out.println("  [X]  " + res);
        }
        UI.pause(sc);
    }
}
