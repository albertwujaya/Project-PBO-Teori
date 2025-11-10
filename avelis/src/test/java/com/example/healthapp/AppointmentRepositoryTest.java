package com.example.healthapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppointmentRepositoryTest {

    @Test
    public void testCreateAndStoreAppointment() {
        InMemoryRepository<Patient> patientRepo = new InMemoryRepository<>();
        InMemoryRepository<Poli> poliRepo = new InMemoryRepository<>();
        InMemoryRepository<Dokter> dokterRepo = new InMemoryRepository<>();
        InMemoryRepository<Appointment> apptRepo = new InMemoryRepository<>();

        // create patient
        Patient p = new Patient("TP001", "Test User", "test@example.com", LocalDate.of(1990,1,1));
        patientRepo.save(p.getId(), p);

        // create poli and dokter
        Poli poli = new Poli("TP-POLI", "Test Poli", "desc");
        poliRepo.save(poli.getId(), poli);
        Dokter dk = new Dokter("TP-D01", "Dr Test", poli.getId(), null);
        dokterRepo.save(dk.getId(), dk);
        poli.addDoctorId(dk.getId());
        poliRepo.save(poli.getId(), poli);

        // create appointment
        LocalDateTime dt = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(10,30));
        Appointment appt = new Appointment(p.getId(), dk.getId(), dt, "Checkup");

        // store
        apptRepo.save(appt.getId(), appt);
        p.addAppointment(appt);
        patientRepo.save(p.getId(), p);

        // assertions
        Appointment loaded = apptRepo.findById(appt.getId()).orElse(null);
        assertNotNull(loaded, "Appointment should be present in repository");
        assertEquals(dk.getId(), loaded.getDoctorId(), "Doctor id should match");

        Patient loadedPatient = patientRepo.findById(p.getId()).orElse(null);
        assertNotNull(loadedPatient, "Patient should be present");
        assertTrue(loadedPatient.getAppointments().stream().anyMatch(a -> a.getId().equals(appt.getId())), "Patient should have the appointment attached");
    }
}
