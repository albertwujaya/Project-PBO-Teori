package com.example.healthapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private LocalDate birthDate;
    private Insurance insurance; // patient's insurance (could be null)
    private final List<Appointment> appointments = new ArrayList<>();

    public Patient(String id, String name, String email, LocalDate birthDate) {
        super(id, name, email);
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Insurance getInsurance() { return insurance; }
    public void setInsurance(Insurance insurance) { this.insurance = insurance; }

    public List<Appointment> getAppointments() { return appointments; }
    public void addAppointment(Appointment appt) { this.appointments.add(appt); }

    public boolean isEnrolled() { return insurance != null; }
}

