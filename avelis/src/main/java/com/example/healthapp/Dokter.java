package com.example.healthapp;

public class Dokter {
    private final String id;
    private String name;
    private String poliId; // which poli this dokter works in
    private String providerId; // optional: which provider (clinic/hospital) the doctor is associated with

    public Dokter(String id, String name, String poliId, String providerId) {
        this.id = id;
        this.name = name;
        this.poliId = poliId;
        this.providerId = providerId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPoliId() { return poliId; }
    public void setPoliId(String poliId) { this.poliId = poliId; }
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    @Override
    public String toString() {
        return String.format("Dokter[id=%s,name=%s,poli=%s]", id, name, poliId);
    }
}
