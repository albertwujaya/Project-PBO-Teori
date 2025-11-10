package com.example.healthapp;

import java.time.LocalDate;
import java.util.Optional;

public class InsuranceService {
    private final InMemoryRepository<Patient> patientRepo;
    private final InMemoryRepository<Provider> providerRepo;

    public InsuranceService(InMemoryRepository<Patient> pRepo,
                            InMemoryRepository<Provider> providerRepo) {
        this.patientRepo = pRepo;
        this.providerRepo = providerRepo;
    }

    public Insurance enroll(String patientId, Insurance.Tier tier) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        String insuranceId = "INS-" + patientId + "-" + System.currentTimeMillis();
        Insurance insurance = new Insurance(insuranceId, patientId, tier, LocalDate.now());
        patient.setInsurance(insurance);
        patientRepo.save(patient.getId(), patient);
        return insurance;
    }

    public Claim submitClaim(String patientId, double amount, LocalDate date, String providerId, String description) {
        Patient p = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        if (!p.isEnrolled()) throw new IllegalStateException("Patient not enrolled in insurance");
        Insurance insurance = p.getInsurance();
        if (!providerRepo.exists(providerId)) throw new IllegalArgumentException("Provider not found: " + providerId);
        Claim claim = new Claim(insurance.getId(), amount, date, providerId, description);
        insurance.addClaim(claim);
        patientRepo.save(p.getId(), p);
        return claim;
    }

    public Optional<Claim> reviewClaim(String patientId, String claimId, boolean approve) {
        Patient p = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        if (!p.isEnrolled()) throw new IllegalStateException("Patient not enrolled");
        Insurance insurance = p.getInsurance();
        return insurance.getClaims().stream()
                .filter(c -> c.getId().equals(claimId))
                .findFirst()
                .map(c -> {
                    c.setState(approve ? Claim.State.APPROVED : Claim.State.REJECTED);
                    patientRepo.save(p.getId(), p);
                    return c;
                });
    }
}
