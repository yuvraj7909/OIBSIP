import java.util.ArrayList;
import java.util.List;

public class Member {
    public enum Role { ADMIN, USER }

    private String id;       // e.g. "M001"
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private List<String> issuedBookIds;
    private double totalFine;

    public Member(String id, String name, String email, String phone, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.issuedBookIds = new ArrayList<>();
        this.totalFine = 0;
    }

    public String getId()               { return id; }
    public String getName()             { return name; }
    public String getEmail()            { return email; }
    public String getPhone()            { return phone; }
    public String getPassword()         { return password; }
    public Role getRole()               { return role; }
    public List<String> getIssuedBookIds() { return issuedBookIds; }
    public double getTotalFine()        { return totalFine; }
    public int getBooksCount()          { return issuedBookIds.size(); }

    public void setName(String n)       { this.name = n; }
    public void setEmail(String e)      { this.email = e; }
    public void setPhone(String p)      { this.phone = p; }
    public void setPassword(String p)   { this.password = p; }
    public void addFine(double f)       { this.totalFine += f; }
    public void clearFine()             { this.totalFine = 0; }

    public void addBook(String bookId)  { issuedBookIds.add(bookId); }
    public void removeBook(String bookId) { issuedBookIds.remove(bookId); }

    public String toTableRow() {
        return String.format("| %-6s | %-20s | %-25s | %-12s | %-6s | %-6s | %-8s |",
                id, truncate(name, 20), truncate(email, 25), phone,
                role.name(), getBooksCount() + " bk", totalFine > 0 ? "Rs." + totalFine : "None");
    }

    private String truncate(String s, int max) {
        return s != null && s.length() > max ? s.substring(0, max - 2) + ".." : s;
    }

    @Override
    public String toString() { return toTableRow(); }
}
