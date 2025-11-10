package com.example.healthapp;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private final String id;
    private final String patientId;
    private final String doctorId; // explicit doctor id
    private LocalDateTime dateTime;
    private String reason;
    private boolean checkedIn = false;

    public Appointment(String patientId, String doctorId, LocalDateTime dateTime, String reason) {
        this.id = UUID.randomUUID().toString();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.reason = reason;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public boolean isCheckedIn() { return checkedIn; }
    public void setCheckedIn(boolean checkedIn) { this.checkedIn = checkedIn; }

    @Override
    public String toString() {
    return String.format("Appointment[id=%s,patient=%s,doctor=%s,date=%s,reason=%s,checkedIn=%s]",
        id, patientId, doctorId, dateTime, reason, checkedIn);
    }
}
