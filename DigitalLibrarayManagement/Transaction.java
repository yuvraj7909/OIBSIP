import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type { ISSUE, RETURN, ADVANCE_BOOK, FINE_PAID }

    private static int counter = 1;
    private int txnId;
    private Type type;
    private String memberId;
    private int bookId;
    private LocalDateTime timestamp;
    private double amount; 

    public Transaction(Type type, String memberId, int bookId, double amount) {
        this.txnId = counter++;
        this.type = type;
        this.memberId = memberId;
        this.bookId = bookId;
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
    }

    public int getTxnId()       { return txnId; }
    public Type getType()       { return type; }
    public String getMemberId() { return memberId; }
    public int getBookId()      { return bookId; }
    public double getAmount()   { return amount; }

    @Override
    public String toString() {
        String dt = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        String amtStr = amount > 0 ? String.format("Rs.%.1f", amount) : "-";
        return String.format("| %-4d | %-15s | %-6s | %-4d | %-16s | %-8s |",
                txnId, type.name(), memberId, bookId, dt, amtStr);
    }
}
