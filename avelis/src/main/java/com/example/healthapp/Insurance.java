package com.example.healthapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Insurance model - stores insurance enrollment info for a patient
 */
public class Insurance {
    public enum Tier { BASIC, STANDARD, PREMIUM }
    
    private final String id;
    private final String patientId;
    private final Tier tier;
    private final LocalDate enrollmentDate;
    private final List<Claim> claims = new ArrayList<>();

    public Insurance(String id, String patientId, Tier tier, LocalDate enrollmentDate) {
        this.id = id;
        this.patientId = patientId;
        this.tier = tier;
        this.enrollmentDate = enrollmentDate;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public Tier getTier() { return tier; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    
    public List<Claim> getClaims() { return claims; }
    public void addClaim(Claim claim) { claims.add(claim); }

    @Override
    public String toString() {
        return String.format("Insurance[id=%s,patient=%s,tier=%s,date=%s,claims=%d]", 
            id, patientId, tier, enrollmentDate, claims.size());
    }
}
