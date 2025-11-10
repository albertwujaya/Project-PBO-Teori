package com.example.healthapp;

import java.util.ArrayList;
import java.util.List;

public class Poli {
    private final String id;
    private String name;
    private String description;
    private final List<String> doctorIds = new ArrayList<>();

    public Poli(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getDoctorIds() { return doctorIds; }
    public void addDoctorId(String dokterId) { if (!doctorIds.contains(dokterId)) doctorIds.add(dokterId); }

    @Override
    public String toString() {
        return String.format("Poli[id=%s,name=%s]", id, name);
    }
}
