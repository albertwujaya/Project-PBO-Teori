package com.example.healthapp;

import java.util.ArrayList;
import java.util.List;

public class Policy {
    public enum Status { ACTIVE, SUSPENDED, CANCELLED }
    private final String policyNumber;
    private final String holderId; // patient id
    private Status status;
    private final List<Claim> claims = new ArrayList<>();

    public Policy(String policyNumber, String holderId) {
        this.policyNumber = policyNumber;
        this.holderId = holderId;
        this.status = Status.ACTIVE;
    }

    public String getPolicyNumber() { return policyNumber; }
    public String getHolderId() { return holderId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<Claim> getClaims() { return claims; }
    public void addClaim(Claim claim) { this.claims.add(claim); }

    @Override
    public String toString() {
        return String.format("Policy[number=%s,holder=%s,status=%s,claims=%d]", policyNumber, holderId, status, claims.size());
    }
}
