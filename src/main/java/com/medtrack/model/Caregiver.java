package com.medtrack.model;

public class Caregiver {

    private final int id;
    private final String name;

    public Caregiver(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", id, name);
    }
}
