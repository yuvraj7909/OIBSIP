# 📚 Digital Library Management System

A complete **Digital Library Management System** built in **Core Java** as a Console Application. It supports two roles — **Admin** and **User** — with full book management, member management, issue/return system, fine generation, advance booking, and report generation.

---

## 🖥️ Demo Preview

```
  +================================================+
  |    DIGITAL LIBRARY MANAGEMENT SYSTEM          |
  |                 Version 2.0                    |
  +================================================+

  +======================================+
  |     WELCOME TO DIGITAL LIBRARY       |
  +======================================+
  |   1. Login                           |
  |   2. New User Registration           |
  |   0. Exit                            |
  +======================================+
```

---

## ✨ Features

### 👑 Admin Module
| Feature | Description |
|---|---|
| Book Management | Add, Update, Remove, Search books |
| Member Management | Register, Update, Remove members |
| Issue Book | Issue book to member with due date |
| Return Book | Return book with auto fine calculation |
| Collect Fine | Collect pending fines from members |
| Reports | Issued books, Overdue books report |
| Transaction History | Full log of all operations |
| Library Statistics | Dashboard with all counts |

### 👤 User Module
| Feature | Description |
|---|---|
| Browse Books | View all books with availability status |
| Search Books | Search by title, author, genre, ISBN |
| Issue Book | Issue up to 3 books at a time |
| Return Book | Return book (fine added if overdue) |
| Advance Booking | Reserve an issued book |
| Pay Fine | Pay pending overdue fine |
| Email Query | Send query to admin |

---

## 🏗️ Project Structure

```
Digital Library Management/
│
├── src/
│   ├── Main.java              # Entry point + Login + Menu
│   ├── LibraryService.java    # Core business logic
│   ├── AdminMenu.java         # Admin panel (all admin operations)
│   ├── UserMenu.java          # User portal
│   ├── Book.java              # Book model + fine calculation
│   ├── Member.java            # Member model (ADMIN/USER roles)
│   ├── Transaction.java       # Transaction log
│   └── UI.java                # Console input/output helpers
│
├── out/                       # Compiled .class files (auto-generated)
└── README.md
```

---

## ⚙️ Prerequisites

- **Java JDK 17** or higher
- Download: [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)

Verify installation:
```bash
java -version
javac -version
```

---

## 🚀 How to Run

### Step 1 — Clone the Repository
```bash
git clone https://github.com/your-username/digital-library-management.git
cd digital-library-management
```

### Step 2 — Compile
```bash
mkdir out
javac -d out src/*.java
```

### Step 3 — Run
```bash
java -cp out Main
```

> **Windows users:** Use `src\*.java` instead of `src/*.java`
```bash
javac -d out src\*.java
java -cp out Main
```

---

## 🔑 Default Login Credentials

| Role | Email | Password |
|---|---|---|
| Admin | admin@library.com | admin123 |
| User | rahul@email.com | pass123 |
| User | priya@email.com | pass123 |
| User | amit@email.com | pass123 |


---

## 📋 Business Rules

- Each member can issue maximum **3 books** at a time
- Loan period: **14 days**
- Fine: **Rs. 2 per day** after due date
- Cannot issue new book if **pending fine** exists
- Cannot remove member with **active issued books**
- **Advance booking** reserves a book when it gets returned

---

## 📸 Sample Output

### Admin Panel
```
  +====================================+
  |  ADMIN PANEL [Admin User]          |
  +====================================+
  |  1. List All Books                 |
  |  2. Add Book                       |
  |  3. Search Books                   |
  |  4. Issue Book                     |
  |  5. Return Book                    |
  |  6. List Members                   |
  |  7. Reports / Statistics           |
  |  0. Logout                         |
  +====================================+
```

### Library Statistics
```
  +----------------------------------+
  |       LIBRARY STATISTICS         |
  +----------------------------------+
  |  Total Books    : 8              |
  |  Available      : 7              |
  |  Issued         : 1              |
  |  Overdue        : 1              |
  |  Total Members  : 4              |
  |  Pending Fines  : Rs.12          |
  |  Transactions   : 5              |
  +----------------------------------+
```

---

## 🛠️ Technologies Used

- **Language:** Core Java (JDK 17+)
- **Type:** Console Application
- **Concepts Used:**
  - Object Oriented Programming (OOP)
  - Collections Framework (HashMap, ArrayList)
  - Java Streams & Lambda
  - Enum (Role: ADMIN/USER, Transaction Types)
  - Date & Time API (LocalDate)

---

## 👨‍💻 Author

**Yuvraj Bordiya**
- GitHub: [@yuvraj7909](https://github.com/yuvraj7909/OIBSIP.git)
- Email: bordiyayuvraj@gmail.com

---

> ⭐ If you found this project helpful, please give it a star!
