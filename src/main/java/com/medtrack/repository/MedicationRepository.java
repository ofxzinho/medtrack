package com.medtrack.repository;

import com.medtrack.model.Medication;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicationRepository {

    private final List<Medication> medications = new ArrayList<>();
    private int nextId = 1;


    public void add(Medication medication) {
        medications.add(medication);
    }


    public List<Medication> findAll() {
        return new ArrayList<>(medications);
    }


    public Optional<Medication> findById(int id) {
        return medications.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }


    public boolean removeById(int id) {
        return medications.removeIf(m -> m.getId() == id);
    }


    public int nextId() {
        return nextId++;
    }
}
