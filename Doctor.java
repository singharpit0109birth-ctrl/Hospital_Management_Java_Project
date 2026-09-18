/**
 * Doctor.java
 * Represents a doctor record in the Hospital Management System.
 */
public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String contact;

    public Doctor(int doctorId, String name, String specialization, String contact) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    public int getDoctorId() { return doctorId; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContact() { return contact; }

    /** Converts the doctor record into a CSV line for file storage. */
    public String toFileString() {
        return doctorId + "," + name + "," + specialization + "," + contact;
    }

    /** Rebuilds a Doctor object from a stored CSV line. */
    public static Doctor fromFileString(String line) {
        String[] p = line.split(",");
        return new Doctor(Integer.parseInt(p[0]), p[1], p[2], p[3]);
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d | Name: %-18s | Specialization: %-15s | Contact: %s",
                doctorId, name, specialization, contact);
    }
}
