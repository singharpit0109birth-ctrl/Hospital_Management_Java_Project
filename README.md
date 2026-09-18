# 🏥 Hospital Management System (Java)

A console-based **Hospital Management System** built in core Java using Object-Oriented Programming and file handling. The system allows hospital staff to manage patients, doctors, appointments, and generate bills — with all data saved to disk so nothing is lost between runs.

## ✨ Features

- **Patient Management** — Add, view, search, update, and discharge patients
- **Doctor Management** — Add, view, and search doctor records
- **Appointment Management** — Book, view, and cancel appointments between patients and doctors
- **Billing** — Auto-generates a bill based on room charges, doctor fee, and medicine cost
- **Persistent Storage** — All records are saved as `.txt` files in the `data/` folder using Java File I/O, so data survives after the program closes

## 🛠️ Tech Stack

| Component        | Technology            |
|-------------------|------------------------|
| Language          | Java (JDK 17+)         |
| Storage           | File handling (CSV-style `.txt` files) |
| Interface         | Console / Command Line |
| Concepts Used     | OOP, Collections, Lambdas, Streams, Exception Handling |

## 📂 Project Structure

```
HospitalManagementSystem/
├── src/
│   ├── Patient.java                  # Patient entity class
│   ├── Doctor.java                   # Doctor entity class
│   ├── Appointment.java              # Appointment entity class
│   └── HospitalManagementSystem.java # Main class with menu & logic
├── data/                             # Auto-generated storage files
│   ├── patients.txt
│   ├── doctors.txt
│   └── appointments.txt
└── README.md
```

## ▶️ How to Run

```bash
# 1. Navigate into the source folder
cd HospitalManagementSystem/src

# 2. Compile all files
javac *.java

# 3. Run the program
java HospitalManagementSystem
```

> The `data/` folder is created automatically on first run and pre-loaded with 3 sample doctors.

## 📋 Sample Menu Flow

```
========== HOSPITAL MANAGEMENT SYSTEM ==========
1. Patient Management
2. Doctor Management
3. Appointment Management
4. Generate Bill
5. Save & Exit
=================================================
```

## 🎯 Learning Outcomes

This project demonstrates:
- Class design and encapsulation (Patient, Doctor, Appointment)
- Use of `ArrayList` and `Stream` API for data handling
- File reading/writing for persistent storage
- Modular, menu-driven program design
- Basic exception handling for invalid input

## 🚀 Future Enhancements

- Migrate storage to a MySQL database via JDBC
- Add a GUI using JavaFX or Swing
- Add login/authentication for admin, doctor, and receptionist roles
- Export bills and reports as PDF

## 👤 Author

Submitted as a Java programming project.
