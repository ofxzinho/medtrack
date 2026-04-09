package com.medtrack.service;

import com.medtrack.model.Medication;
import com.medtrack.repository.MedicationRepository;
import java.util.List;
import java.util.Optional;

public class MedicationService {

    private final MedicationRepository repository;

    public MedicationService(MedicationRepository repository) {
        this.repository = repository;
    }

    public Medication addMedication(String name, String dosage, String scheduleTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do medicamento não pode ser vazio.");
        }
        if (dosage == null || dosage.isBlank()) {
            throw new IllegalArgumentException("Dosagem não pode ser vazia.");
        }
        if (scheduleTime == null || scheduleTime.isBlank()) {
            throw new IllegalArgumentException("Horário não pode ser vazio.");
        }

        Medication medication = new Medication(
                repository.nextId(), name.trim(), dosage.trim(), scheduleTime.trim()
        );
        repository.add(medication);
        return medication;
    }


    public List<Medication> listAll() {
        return repository.findAll();
    }


    public boolean markAsTaken(int id) {
        Optional<Medication> found = repository.findById(id);
        found.ifPresent(m -> m.setTaken(true));
        return found.isPresent();
    }

    public boolean remove(int id) {
        return repository.removeById(id);
    }
}
