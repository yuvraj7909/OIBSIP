import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private Map<Integer, Book> books = new LinkedHashMap<>();
    private Map<String, Member> members = new LinkedHashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private int nextBookId = 1;
    private int memberCounter = 1;

    public LibraryService() {
        loadSampleData();
    }

    // ================== BOOK CRUD ==================

    public Book addBook(String title, String author, String genre, String isbn, int year) {
        Book b = new Book(nextBookId++, title, author, genre, isbn, year);
        books.put(b.getId(), b);
        return b;
    }

    public boolean updateBook(int id, String title, String author, String genre) {
        Book b = books.get(id);
        if (b == null) return false;
        if (title != null && !title.isEmpty())  b.setTitle(title);
        if (author != null && !author.isEmpty()) b.setAuthor(author);
        if (genre != null && !genre.isEmpty())  b.setGenre(genre);
        return true;
    }

    public String removeBook(int id) {
        Book b = books.get(id);
        if (b == null) return "Book not found.";
        if (!b.isAvailable()) return "Cannot remove: book is currently issued.";
        books.remove(id);
        return "OK";
    }

    public List<Book> getAllBooks() { return new ArrayList<>(books.values()); }

    public List<Book> getAvailableBooks() {
        return books.values().stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    public List<Book> searchBooks(String kw) {
        String k = kw.toLowerCase();
        return books.values().stream().filter(b ->
            b.getTitle().toLowerCase().contains(k) ||
            b.getAuthor().toLowerCase().contains(k) ||
            b.getGenre().toLowerCase().contains(k) ||
            b.getIsbn().toLowerCase().contains(k))
            .collect(Collectors.toList());
    }

    public Book getBook(int id) { return books.get(id); }

    // ================== MEMBER CRUD ==================

    public Member registerMember(String name, String email, String phone, String password, Member.Role role) {
        String id = "M" + String.format("%03d", memberCounter++);
        Member m = new Member(id, name, email, phone, password, role);
        members.put(id, m);
        return m;
    }

    public boolean updateMember(String id, String name, String email, String phone) {
        Member m = members.get(id);
        if (m == null) return false;
        if (name != null && !name.isEmpty())   m.setName(name);
        if (email != null && !email.isEmpty()) m.setEmail(email);
        if (phone != null && !phone.isEmpty()) m.setPhone(phone);
        return true;
    }

    public String removeMember(String id) {
        Member m = members.get(id);
        if (m == null) return "Member not found.";
        if (m.getBooksCount() > 0) return "Cannot remove: member has " + m.getBooksCount() + " book(s) issued.";
        members.remove(id);
        return "OK";
    }

    public List<Member> getAllMembers() { return new ArrayList<>(members.values()); }

    public Member getMember(String id) { return members.get(id); }

    public Member login(String email, String password) {
        return members.values().stream()
            .filter(m -> m.getEmail().equalsIgnoreCase(email) && m.getPassword().equals(password))
            .findFirst().orElse(null);
    }

    // ================== ISSUE & RETURN ==================

    public String issueBook(int bookId, String memberId) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);
        if (book == null)   return "Book ID " + bookId + " not found.";
        if (member == null) return "Member ID " + memberId + " not found.";
        if (!book.isAvailable()) return "Book is already issued (due: " + book.getDueDate() + ").";
        if (member.getBooksCount() >= 3) return "Member has reached maximum limit of 3 books.";
        if (member.getTotalFine() > 0) return "Member has pending fine of Rs." + member.getTotalFine() + ". Please clear it first.";

        // If advance-booked by someone else, block
        if (book.getAdvanceBookedBy() != null && !book.getAdvanceBookedBy().equals(memberId)) {
            return "This book is advance-booked by Member " + book.getAdvanceBookedBy() + ".";
        }

        book.setAvailable(false);
        book.setIssuedToMemberId(memberId);
        book.setIssueDate(LocalDate.now());
        book.setDueDate(LocalDate.now().plusDays(Book.LOAN_DAYS));
        book.setAdvanceBookedBy(null); // clear advance booking
        member.addBook(String.valueOf(bookId));

        transactions.add(new Transaction(Transaction.Type.ISSUE, memberId, bookId, 0));
        return "OK";
    }

    public String returnBook(int bookId) {
        Book book = books.get(bookId);
        if (book == null)         return "Book not found.";
        if (book.isAvailable())   return "This book is not issued.";

        String memberId = book.getIssuedToMemberId();
        Member member = members.get(memberId);
        double fine = book.calculateFine();

        book.setAvailable(true);
        book.setIssuedToMemberId(null);
        book.setIssueDate(null);
        book.setDueDate(null);

        if (member != null) {
            member.removeBook(String.valueOf(bookId));
            if (fine > 0) member.addFine(fine);
        }

        transactions.add(new Transaction(Transaction.Type.RETURN, memberId, bookId, fine));
        return fine > 0 ? "FINE:" + fine : "OK";
    }

    // ================== ADVANCE BOOKING ==================

    public String advanceBook(int bookId, String memberId) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);
        if (book == null)   return "Book not found.";
        if (member == null) return "Member not found.";
        if (book.isAvailable()) return "Book is already available. Please issue it directly.";
        if (book.getAdvanceBookedBy() != null) return "Book already advance-booked by " + book.getAdvanceBookedBy() + ".";
        book.setAdvanceBookedBy(memberId);
        transactions.add(new Transaction(Transaction.Type.ADVANCE_BOOK, memberId, bookId, 0));
        return "OK";
    }

    // ================== FINE ==================

    public String payFine(String memberId) {
        Member m = members.get(memberId);
        if (m == null) return "Member not found.";
        if (m.getTotalFine() == 0) return "No pending fine.";
        double amt = m.getTotalFine();
        m.clearFine();
        transactions.add(new Transaction(Transaction.Type.FINE_PAID, memberId, 0, amt));
        return "PAID:" + amt;
    }

    // ================== REPORTS ==================

    public void reportIssuedBooks() {
        List<Book> issued = books.values().stream().filter(b -> !b.isAvailable()).collect(Collectors.toList());
        System.out.println("  Total issued: " + issued.size());
        if (issued.isEmpty()) return;
        printBookHeader();
        issued.forEach(System.out::println);
        printLine(100);
    }

    public void reportOverdueBooks() {
        List<Book> overdue = books.values().stream()
            .filter(b -> !b.isAvailable() && b.calculateFine() > 0)
            .collect(Collectors.toList());
        System.out.println("  Overdue books: " + overdue.size());
        if (overdue.isEmpty()) return;
        printBookHeader();
        overdue.forEach(System.out::println);
        printLine(100);
    }

    public void reportTransactions() {
        if (transactions.isEmpty()) { System.out.println("  No transactions yet."); return; }
        System.out.println("  Total transactions: " + transactions.size());
        printTxnHeader();
        transactions.forEach(System.out::println);
        printLine(68);
    }

    public void reportStats() {
        long available = books.values().stream().filter(Book::isAvailable).count();
        long issued    = books.size() - available;
        long overdue   = books.values().stream().filter(b -> !b.isAvailable() && b.calculateFine() > 0).count();
        long admins    = members.values().stream().filter(m -> m.getRole() == Member.Role.ADMIN).count();
        long users     = members.values().stream().filter(m -> m.getRole() == Member.Role.USER).count();
        double totalFines = members.values().stream().mapToDouble(Member::getTotalFine).sum();

        System.out.println("  +---------------------------------+");
        System.out.println("  |         LIBRARY STATISTICS       |");
        System.out.println("  +---------------------------------+");
        System.out.printf ("  |  Total Books     : %-13d |%n", books.size());
        System.out.printf ("  |  Available       : %-13d |%n", available);
        System.out.printf ("  |  Issued          : %-13d |%n", issued);
        System.out.printf ("  |  Overdue         : %-13d |%n", overdue);
        System.out.printf ("  |  Total Members   : %-13d |%n", members.size());
        System.out.printf ("  |  Admins          : %-13d |%n", admins);
        System.out.printf ("  |  Users           : %-13d |%n", users);
        System.out.printf ("  |  Pending Fines   : Rs.%-10.1f |%n", totalFines);
        System.out.printf ("  |  Transactions    : %-13d |%n", transactions.size());
        System.out.println("  +---------------------------------+");
    }

    // ================== PRINT HELPERS ==================

    public void printBookHeader() {
        printLine(100);
        System.out.printf("| %-4s | %-28s | %-18s | %-12s | %-4s | %-35s |%n",
                "ID", "Title", "Author", "Genre", "Year", "Status");
        printLine(100);
    }

    public void printMemberHeader() {
        printLine(96);
        System.out.printf("| %-6s | %-20s | %-25s | %-12s | %-6s | %-6s | %-8s |%n",
                "ID", "Name", "Email", "Phone", "Role", "Books", "Fine");
        printLine(96);
    }

    public void printTxnHeader() {
        printLine(68);
        System.out.printf("| %-4s | %-15s | %-6s | %-4s | %-16s | %-8s |%n",
                "ID", "Type", "MembID", "BookID", "DateTime", "Amount");
        printLine(68);
    }

    public static void printLine(int len) { System.out.println("-".repeat(len)); }

    // ================== SAMPLE DATA ==================

    private void loadSampleData() {
        // Admin
        Member admin = registerMember("Admin User", "admin@library.com", "9000000001", "admin123", Member.Role.ADMIN);

        // Users
        Member u1 = registerMember("Rahul Sharma",  "rahul@email.com",  "9876543210", "pass123", Member.Role.USER);
        Member u2 = registerMember("Priya Verma",   "priya@email.com",  "9988776655", "pass123", Member.Role.USER);
        Member u3 = registerMember("Amit Joshi",    "amit@email.com",   "9123456789", "pass123", Member.Role.USER);

        // Books
        addBook("The Great Gatsby",           "F. Scott Fitzgerald", "Fiction",    "978-0743273565", 1925);
        addBook("To Kill a Mockingbird",      "Harper Lee",          "Fiction",    "978-0061935466", 1960);
        addBook("1984",                       "George Orwell",       "Dystopian",  "978-0451524935", 1949);
        addBook("Clean Code",                 "Robert C. Martin",    "Technology", "978-0132350884", 2008);
        addBook("The Pragmatic Programmer",   "Andy Hunt",           "Technology", "978-0135957059", 1999);
        addBook("Harry Potter & Sorcerer",    "J.K. Rowling",        "Fantasy",    "978-0439708180", 1997);
        addBook("Sapiens",                    "Yuval Noah Harari",   "History",    "978-0062316097", 2011);
        addBook("Rich Dad Poor Dad",          "Robert Kiyosaki",     "Finance",    "978-1612680194", 1997);

        // Issue one book to show fine scenario
        issueBook(1, u1.getId());
        // Manually backdate to show overdue
        books.get(1).setIssueDate(LocalDate.now().minusDays(20));
        books.get(1).setDueDate(LocalDate.now().minusDays(6));

        // Advance booking demo
        advanceBook(1, u2.getId());
    }
}