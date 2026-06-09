import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private int year;
    private boolean isAvailable;
    private String issuedToMemberId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String advanceBookedBy; // member ID who advance-booked

    public static final int LOAN_DAYS = 14;
    public static final double FINE_PER_DAY = 2.0; // Rs. 2 per day

    public Book(int id, String title, String author, String genre, String isbn, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.year = year;
        this.isAvailable = true;
    }

    // Getters
    public int getId()                  { return id; }
    public String getTitle()            { return title; }
    public String getAuthor()           { return author; }
    public String getGenre()            { return genre; }
    public String getIsbn()             { return isbn; }
    public int getYear()                { return year; }
    public boolean isAvailable()        { return isAvailable; }
    public String getIssuedToMemberId() { return issuedToMemberId; }
    public LocalDate getIssueDate()     { return issueDate; }
    public LocalDate getDueDate()       { return dueDate; }
    public String getAdvanceBookedBy()  { return advanceBookedBy; }

    // Setters
    public void setTitle(String t)          { this.title = t; }
    public void setAuthor(String a)         { this.author = a; }
    public void setGenre(String g)          { this.genre = g; }
    public void setAvailable(boolean v)     { this.isAvailable = v; }
    public void setIssuedToMemberId(String m) { this.issuedToMemberId = m; }
    public void setIssueDate(LocalDate d)   { this.issueDate = d; }
    public void setDueDate(LocalDate d)     { this.dueDate = d; }
    public void setAdvanceBookedBy(String m){ this.advanceBookedBy = m; }

    public double calculateFine() {
        if (isAvailable || dueDate == null) return 0;
        LocalDate today = LocalDate.now();
        if (!today.isAfter(dueDate)) return 0;
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
        return overdueDays * FINE_PER_DAY;
    }

    public String getStatusDisplay() {
        if (isAvailable) {
            return advanceBookedBy != null ? "Available (Booked by M-" + advanceBookedBy + ")" : "Available";
        }
        double fine = calculateFine();
        return "Issued [Due: " + dueDate + (fine > 0 ? " | Fine: Rs." + fine : "") + "]";
    }

    public String toTableRow() {
        return String.format("| %-4d | %-28s | %-18s | %-12s | %-4d | %-35s |",
                id, truncate(title, 28), truncate(author, 18), truncate(genre, 12), year, truncate(getStatusDisplay(), 35));
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 2) + ".." : s;
    }

    @Override
    public String toString() { return toTableRow(); }
}
