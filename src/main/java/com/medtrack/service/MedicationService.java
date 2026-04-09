package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.repository.MedicationRepository;
import java.util.List;
import java.util.Optional;

public class MedicationService {

    private final MedicationRepository repository;
    private final CaregiverService caregiverService;

    public MedicationService(MedicationRepository repository, CaregiverService caregiverService) {
        this.repository = repository;
        this.caregiverService = caregiverService;
    }

    public Medication addMedication(String name, String dosage, String scheduleTime, int caregiverId) {
        if (isInvalid(name) || isInvalid(dosage) || isInvalid(scheduleTime)) {
            throw new IllegalArgumentException("Nome, dosagem e horário são obrigatórios.");
        }

        Optional<Caregiver> caregiverOpt = caregiverService.findById(caregiverId);
        if (caregiverOpt.isEmpty()) {
            throw new IllegalArgumentException("Erro: Cuidador não encontrado com ID " + caregiverId);
        }

        int id = repository.getNextId();
        Medication medication = new Medication(id, name, dosage, scheduleTime, caregiverOpt.get());
        repository.save(medication);
        return medication;
    }

    private boolean isInvalid(String s) {
        return s == null || s.isBlank();
    }

    public List<Medication> listAll() {
        return repository.findAll();
    }

    public boolean markAsTaken(int id) {
        Optional<Medication> med = repository.findById(id);
        if (med.isPresent()) {
            med.get().setTaken(true);
            return true;
        }
        return false;
    }

    public boolean remove(int id) {
        return repository.delete(id);
    }
}
