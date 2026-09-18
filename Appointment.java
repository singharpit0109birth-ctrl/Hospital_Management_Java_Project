/**
 * Appointment.java
 * Represents an appointment linking a patient with a doctor.
 */
public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String date;
    private String status; // Scheduled, Completed, Cancelled

    public Appointment(int appointmentId, int patientId, int doctorId, String date, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
    }

    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    /** Converts the appointment record into a CSV line for file storage. */
    public String toFileString() {
        return appointmentId + "," + patientId + "," + doctorId + "," + date + "," + status;
    }

    /** Rebuilds an Appointment object from a stored CSV line. */
    public static Appointment fromFileString(String line) {
        String[] p = line.split(",");
        return new Appointment(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
                                Integer.parseInt(p[2]), p[3], p[4]);
    }

    @Override
    public String toString() {
        return String.format("Appt ID: %-4d | Patient ID: %-4d | Doctor ID: %-4d | Date: %-12s | Status: %s",
                appointmentId, patientId, doctorId, date, status);
    }
}
