package com.medtrack.repository;

import com.medtrack.model.Caregiver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaregiverRepository {

    private final List<Caregiver> caregivers = new ArrayList<>();
    private int currentId = 1;

    public int getNextId() {
        return currentId++;
    }

    public void save(Caregiver caregiver) {
        caregivers.add(caregiver);
    }

    public List<Caregiver> findAll() {
        return new ArrayList<>(caregivers);
    }

    public Optional<Caregiver> findById(int id) {
        return caregivers.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }
}

