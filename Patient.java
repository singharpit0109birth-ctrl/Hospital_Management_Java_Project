/**
 * Patient.java
 * Represents a patient record in the Hospital Management System.
 */
public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String disease;
    private int assignedDoctorId;
    private String admissionDate;

    public Patient(int patientId, String name, int age, String gender,
                    String disease, int assignedDoctorId, String admissionDate) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.assignedDoctorId = assignedDoctorId;
        this.admissionDate = admissionDate;
    }

    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getDisease() { return disease; }
    public int getAssignedDoctorId() { return assignedDoctorId; }
    public String getAdmissionDate() { return admissionDate; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setAssignedDoctorId(int id) { this.assignedDoctorId = id; }

    /** Converts the patient record into a CSV line for file storage. */
    public String toFileString() {
        return patientId + "," + name + "," + age + "," + gender + "," +
               disease + "," + assignedDoctorId + "," + admissionDate;
    }

    /** Rebuilds a Patient object from a stored CSV line. */
    public static Patient fromFileString(String line) {
        String[] p = line.split(",");
        return new Patient(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]),
                            p[3], p[4], Integer.parseInt(p[5]), p[6]);
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d | Name: %-18s | Age: %-3d | Gender: %-7s | Disease: %-15s | Doctor ID: %-4d | Admitted: %s",
                patientId, name, age, gender, disease, assignedDoctorId, admissionDate);
    }
}
