package com.medtrack.model;
public class Medication {

    private final int id;
    private final String name;
    private final String dosage;
    private final String scheduleTime;
    private final Caregiver caregiver;
    private boolean taken;

    public Medication(int id, String name, String dosage, String scheduleTime, Caregiver caregiver) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.scheduleTime = scheduleTime;
        this.caregiver = caregiver;
        this.taken = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        String status = taken ? "✔ Tomado" : "✘ Pendente";
        return String.format("[%d] %s (%s) às %s | Cuidador: %s | %s",
                id, name, dosage, scheduleTime, caregiver.getName(), status);
    }
}
