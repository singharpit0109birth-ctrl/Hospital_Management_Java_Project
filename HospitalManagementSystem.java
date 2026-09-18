import java.io.*;
import java.util.*;

/**
 * HospitalManagementSystem.java
 * Main driver class. Provides a console menu to manage patients, doctors,
 * appointments and generate a simple bill. Data is persisted to text files
 * inside the "data" folder so records survive between runs.
 */
public class HospitalManagementSystem {

    static final String DATA_DIR = "data";
    static final String PATIENT_FILE = DATA_DIR + "/patients.txt";
    static final String DOCTOR_FILE = DATA_DIR + "/doctors.txt";
    static final String APPOINTMENT_FILE = DATA_DIR + "/appointments.txt";

    static List<Patient> patients = new ArrayList<>();
    static List<Doctor> doctors = new ArrayList<>();
    static List<Appointment> appointments = new ArrayList<>();

    static int nextPatientId = 1;
    static int nextDoctorId = 1;
    static int nextAppointmentId = 1;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new File(DATA_DIR).mkdirs();
        loadAllData();
        seedDoctorsIfEmpty();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: patientMenu(); break;
                case 2: doctorMenu(); break;
                case 3: appointmentMenu(); break;
                case 4: generateBill(); break;
                case 5:
                    saveAllData();
                    System.out.println("\nAll data saved. Thank you for using the system!");
                    running = false;
                    break;
                default: System.out.println("Invalid choice. Try again.\n");
            }
        }
        sc.close();
    }

    // ---------------------- MAIN MENU ----------------------
    static void printMainMenu() {
        System.out.println("\n========== HOSPITAL MANAGEMENT SYSTEM ==========");
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Generate Bill");
        System.out.println("5. Save & Exit");
        System.out.println("=================================================");
    }

    // ---------------------- PATIENT MENU ----------------------
    static void patientMenu() {
        System.out.println("\n---- Patient Management ----");
        System.out.println("1. Add Patient\n2. View All Patients\n3. Search Patient by ID");
        System.out.println("4. Update Patient\n5. Discharge (Delete) Patient\n6. Back");
        int c = readInt("Enter choice: ");
        switch (c) {
            case 1: addPatient(); break;
            case 2: viewPatients(); break;
            case 3: searchPatient(); break;
            case 4: updatePatient(); break;
            case 5: deletePatient(); break;
            case 6: return;
            default: System.out.println("Invalid choice.");
        }
    }

    static void addPatient() {
        sc.nextLine();
        System.out.print("Enter patient name: ");
        String name = sc.nextLine();
        int age = readInt("Enter age: ");
        sc.nextLine();
        System.out.print("Enter gender (M/F/O): ");
        String gender = sc.nextLine();
        System.out.print("Enter disease/reason for admission: ");
        String disease = sc.nextLine();

        viewDoctors();
        int docId = readInt("Assign Doctor ID: ");
        if (findDoctor(docId) == null) {
            System.out.println("No such doctor. Patient will be added as unassigned (0).");
            docId = 0;
        }

        String date = "18-09-2026"; // admission date (demo static; replace with real date if needed)
        Patient p = new Patient(nextPatientId++, name, age, gender, disease, docId, date);
        patients.add(p);
        System.out.println("Patient added successfully with ID " + p.getPatientId());
    }

    static void viewPatients() {
        if (patients.isEmpty()) { System.out.println("No patients found."); return; }
        System.out.println("\n-- Patient List --");
        for (Patient p : patients) System.out.println(p);
    }

    static void searchPatient() {
        int id = readInt("Enter Patient ID: ");
        Patient p = findPatient(id);
        System.out.println(p != null ? p : "Patient not found.");
    }

    static void updatePatient() {
        int id = readInt("Enter Patient ID to update: ");
        Patient p = findPatient(id);
        if (p == null) { System.out.println("Patient not found."); return; }
        sc.nextLine();
        System.out.print("Enter new disease/status (leave blank to keep same): ");
        String disease = sc.nextLine();
        if (!disease.isBlank()) p.setDisease(disease);
        System.out.println("Patient record updated.");
    }

    static void deletePatient() {
        int id = readInt("Enter Patient ID to discharge/delete: ");
        boolean removed = patients.removeIf(p -> p.getPatientId() == id);
        System.out.println(removed ? "Patient discharged successfully." : "Patient not found.");
    }

    // ---------------------- DOCTOR MENU ----------------------
    static void doctorMenu() {
        System.out.println("\n---- Doctor Management ----");
        System.out.println("1. Add Doctor\n2. View All Doctors\n3. Search Doctor by ID\n4. Back");
        int c = readInt("Enter choice: ");
        switch (c) {
            case 1: addDoctor(); break;
            case 2: viewDoctors(); break;
            case 3: searchDoctor(); break;
            case 4: return;
            default: System.out.println("Invalid choice.");
        }
    }

    static void addDoctor() {
        sc.nextLine();
        System.out.print("Enter doctor name: ");
        String name = sc.nextLine();
        System.out.print("Enter specialization: ");
        String spec = sc.nextLine();
        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();
        Doctor d = new Doctor(nextDoctorId++, name, spec, contact);
        doctors.add(d);
        System.out.println("Doctor added successfully with ID " + d.getDoctorId());
    }

    static void viewDoctors() {
        if (doctors.isEmpty()) { System.out.println("No doctors found."); return; }
        System.out.println("\n-- Doctor List --");
        for (Doctor d : doctors) System.out.println(d);
    }

    static void searchDoctor() {
        int id = readInt("Enter Doctor ID: ");
        Doctor d = findDoctor(id);
        System.out.println(d != null ? d : "Doctor not found.");
    }

    // ---------------------- APPOINTMENT MENU ----------------------
    static void appointmentMenu() {
        System.out.println("\n---- Appointment Management ----");
        System.out.println("1. Book Appointment\n2. View All Appointments\n3. Cancel Appointment\n4. Back");
        int c = readInt("Enter choice: ");
        switch (c) {
            case 1: bookAppointment(); break;
            case 2: viewAppointments(); break;
            case 3: cancelAppointment(); break;
            case 4: return;
            default: System.out.println("Invalid choice.");
        }
    }

    static void bookAppointment() {
        int pid = readInt("Enter Patient ID: ");
        if (findPatient(pid) == null) { System.out.println("Patient not found."); return; }
        int did = readInt("Enter Doctor ID: ");
        if (findDoctor(did) == null) { System.out.println("Doctor not found."); return; }
        sc.nextLine();
        System.out.print("Enter appointment date (dd-mm-yyyy): ");
        String date = sc.nextLine();
        Appointment a = new Appointment(nextAppointmentId++, pid, did, date, "Scheduled");
        appointments.add(a);
        System.out.println("Appointment booked with ID " + a.getAppointmentId());
    }

    static void viewAppointments() {
        if (appointments.isEmpty()) { System.out.println("No appointments found."); return; }
        System.out.println("\n-- Appointment List --");
        for (Appointment a : appointments) System.out.println(a);
    }

    static void cancelAppointment() {
        int id = readInt("Enter Appointment ID to cancel: ");
        Appointment a = appointments.stream().filter(x -> x.getAppointmentId() == id).findFirst().orElse(null);
        if (a == null) { System.out.println("Appointment not found."); return; }
        a.setStatus("Cancelled");
        System.out.println("Appointment cancelled.");
    }

    // ---------------------- BILLING ----------------------
    static void generateBill() {
        int pid = readInt("Enter Patient ID to generate bill for: ");
        Patient p = findPatient(pid);
        if (p == null) { System.out.println("Patient not found."); return; }

        double roomCharge = 800.0;      // per day
        double doctorFee = 500.0;       // flat consultation fee
        double medicineCharge = 350.0;  // flat estimate
        int days = readInt("Enter number of days admitted: ");

        double total = (roomCharge * days) + doctorFee + medicineCharge;

        System.out.println("\n============ HOSPITAL BILL ============");
        System.out.println("Patient: " + p.getName() + " (ID: " + p.getPatientId() + ")");
        System.out.println("Room Charges (" + days + " day/s): Rs. " + (roomCharge * days));
        System.out.println("Doctor Consultation Fee: Rs. " + doctorFee);
        System.out.println("Medicine Charges: Rs. " + medicineCharge);
        System.out.println("-----------------------------------------");
        System.out.println("TOTAL AMOUNT: Rs. " + total);
        System.out.println("=========================================");
    }

    // ---------------------- HELPERS ----------------------
    static Patient findPatient(int id) {
        for (Patient p : patients) if (p.getPatientId() == id) return p;
        return null;
    }

    static Doctor findDoctor(int id) {
        for (Doctor d : doctors) if (d.getDoctorId() == id) return d;
        return null;
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    static void seedDoctorsIfEmpty() {
        if (doctors.isEmpty()) {
            doctors.add(new Doctor(nextDoctorId++, "Dr. A. Sharma", "General Physician", "9876543210"));
            doctors.add(new Doctor(nextDoctorId++, "Dr. R. Verma", "Cardiologist", "9876501234"));
            doctors.add(new Doctor(nextDoctorId++, "Dr. S. Nair", "Orthopedic", "9876512345"));
        }
    }

    // ---------------------- FILE I/O ----------------------
    static void loadAllData() {
        patients.clear(); doctors.clear(); appointments.clear();
        loadFromFile(PATIENT_FILE, line -> {
            Patient p = Patient.fromFileString(line);
            patients.add(p);
            if (p.getPatientId() >= nextPatientId) nextPatientId = p.getPatientId() + 1;
        });
        loadFromFile(DOCTOR_FILE, line -> {
            Doctor d = Doctor.fromFileString(line);
            doctors.add(d);
            if (d.getDoctorId() >= nextDoctorId) nextDoctorId = d.getDoctorId() + 1;
        });
        loadFromFile(APPOINTMENT_FILE, line -> {
            Appointment a = Appointment.fromFileString(line);
            appointments.add(a);
            if (a.getAppointmentId() >= nextAppointmentId) nextAppointmentId = a.getAppointmentId() + 1;
        });
    }

    interface LineHandler { void handle(String line); }

    static void loadFromFile(String path, LineHandler handler) {
        File f = new File(path);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) handler.handle(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading " + path + ": " + e.getMessage());
        }
    }

    static void saveAllData() {
        saveToFile(PATIENT_FILE, patients.stream().map(Patient::toFileString).toList());
        saveToFile(DOCTOR_FILE, doctors.stream().map(Doctor::toFileString).toList());
        saveToFile(APPOINTMENT_FILE, appointments.stream().map(Appointment::toFileString).toList());
    }

    static void saveToFile(String path, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing " + path + ": " + e.getMessage());
        }
    }
}
