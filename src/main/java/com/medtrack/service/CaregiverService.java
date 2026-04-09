package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.repository.CaregiverRepository;
import java.util.List;
import java.util.Optional;

public class CaregiverService {

    private final CaregiverRepository repository;

    public CaregiverService(CaregiverRepository repository) {
        this.repository = repository;
    }

    public Caregiver addCaregiver(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do cuidador é obrigatório.");
        }

        Caregiver caregiver = new Caregiver(repository.getNextId(), name);
        repository.save(caregiver);
        return caregiver;
    }

    public List<Caregiver> listAll() {
        return repository.findAll();
    }

    public Optional<Caregiver> findById(int id) {
        return repository.findById(id);
    }
}
