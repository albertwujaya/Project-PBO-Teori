package com.example.healthapp;

import java.time.LocalDate;
import java.util.UUID;

public class Claim {
    public enum State { SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, PAID }

    private final String id;
    private final String policyNumber;
    private final double amount;
    private final LocalDate date;
    private State state;
    private final String providerId;
    private final String description;

    public Claim(String policyNumber, double amount, LocalDate date, String providerId, String description) {
        this.id = UUID.randomUUID().toString();
        this.policyNumber = policyNumber;
        this.amount = amount;
        this.date = date;
        this.state = State.SUBMITTED;
        this.providerId = providerId;
        this.description = description;
    }

    public String getId() { return id; }
    public String getPolicyNumber() { return policyNumber; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
    public String getProviderId() { return providerId; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("Claim[id=%s,policy=%s,amount=%.2f,date=%s,state=%s,provider=%s]",
                id, policyNumber, amount, date, state, providerId);
    }
}
