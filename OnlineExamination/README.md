# 🎓 Online Examination System

A complete **Desktop-based Online Examination System** built using **Core Java** and **Java Swing** for GUI.

---


> Login Screen → Dashboard → MCQ Exam → Results

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 Login System | Secure username & password authentication |
| 👤 Update Profile | Edit name, email, phone number |
| 🔑 Change Password | Secure password update with validation |
| 📝 MCQ Exam | Multiple choice questions with A/B/C/D options |
| ⏱️ Timer & Auto Submit | Countdown timer, auto-submits when time is up |
| 🧭 Question Navigator | Jump to any question using palette |
| 📊 Results | Score %, correct/wrong/skipped count |
| 📋 Answer Key | Full answer review after submission |
| 🚪 Logout | Session close with confirmation |

---

## 🛠️ Technologies Used

- **Language:** Java (JDK 8+)
- **GUI:** Java Swing
- **IDE:** VS Code / IntelliJ IDEA / Eclipse
- **Build:** Manual compilation using `javac`

---

## 📁 Project Structure

```
OnlineExam/
├── src/
│   └── exam/
│       ├── Main.java                  → Entry point
│       ├── AppController.java         → Navigation controller
│       ├── DataStore.java             → Users & exam data
│       ├── User.java                  → User model
│       ├── Exam.java                  → Exam model
│       ├── Question.java              → Question model
│       ├── Theme.java                 → Dark theme colors & fonts
│       ├── LoginPanel.java            → Login screen
│       ├── DashboardPanel.java        → Dashboard/home screen
│       ├── ProfilePanel.java          → Profile & password update
│       ├── ExamPanel.java             → MCQ exam with timer
│       ├── ExamSelectionDialog.java   → Exam picker dialog
│       └── ResultPanel.java           → Results & answer review
├── out/                               → Compiled class files
├── run.bat                            → Windows build & run script
├── run.sh                             → Linux/Mac build & run script
└── README.md
```

---

## ▶️ How to Run

### ✅ Requirements
- Java JDK 8 or higher
- Verify installation:
```bash
java -version
javac -version
```

### 🪟 Windows
Open terminal in `OnlineExam` folder and run:
```powershell
mkdir out
javac -d out src/exam/Question.java src/exam/User.java src/exam/Exam.java src/exam/DataStore.java src/exam/Theme.java src/exam/AppController.java src/exam/LoginPanel.java src/exam/DashboardPanel.java src/exam/ProfilePanel.java src/exam/ExamPanel.java src/exam/ExamSelectionDialog.java src/exam/ResultPanel.java src/exam/Main.java
java -cp out exam.Main
```

### 🐧 Linux / macOS
```bash
cd OnlineExam
chmod +x run.sh
./run.sh
```

---

## 🔐 Demo Credentials

| Username | Password | Name |
|---|---|---|
| admin | admin123 | Admin User |
| student | student1 | Ravi Sharma |
| rahul | rahul123 | Rahul Verma |

---

## 📚 Available Exams

| Exam | Questions | Time Limit |
|---|---|---|
| 📖 General Knowledge | 10 | 10 Minutes |
| 💻 Computer Science | 10 | 15 Minutes |
| ➗ Mathematics | 10 | 20 Minutes |

---

## 🎮 How to Use

1. **Login** with any demo credentials
2. **Dashboard** pe available exams dekhein
3. Koi bhi **exam select** karein
4. **MCQ questions** ke answers select karein
5. **Question Navigator** se kisi bhi question pe jump karein
6. **Submit** karein ya timer khatam hone pe auto-submit hoga
7. **Results** mein score aur answer key dekhein
8. **Profile** update karein ya password change karein

---

👨‍💻 Author
Yuvraj Bordiya

GitHub: @yuvraj7909
Email: bordiyayuvraj@gmail.com



---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
