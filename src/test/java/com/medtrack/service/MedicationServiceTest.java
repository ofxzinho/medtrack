package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.repository.CaregiverRepository;
import com.medtrack.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicationServiceTest {

    private MedicationService medicationService;
    private CaregiverService caregiverService;
    private int defaultCaregiverId;

    static class FakeCaregiverRepository extends CaregiverRepository {
        private final List<Caregiver> list = new ArrayList<>();
        private int nextId = 1;

        @Override
        public void save(Caregiver caregiver) {
            list.add(new Caregiver(nextId++, caregiver.getName()));
        }

        @Override
        public List<Caregiver> findAll() {
            return new ArrayList<>(list);
        }

        @Override
        public Optional<Caregiver> findById(int id) {
            return list.stream().filter(c -> c.getId() == id).findFirst();
        }
    }

    static class FakeMedicationRepository extends MedicationRepository {
        private final List<Medication> list = new ArrayList<>();
        private int nextId = 1;

        @Override
        public void save(Medication medication) {
            list.add(medication);
        }

        @Override
        public List<Medication> findAll() {
            return new ArrayList<>(list);
        }

        @Override
        public Optional<Medication> findById(int id) {
            return list.stream().filter(m -> m.getId() == id).findFirst();
        }

        @Override
        public boolean delete(int id) {
            return list.removeIf(m -> m.getId() == id);
        }

        @Override
        public void updateTaken(int id, boolean taken) {
            list.stream().filter(m -> m.getId() == id)
                    .findFirst().ifPresent(m -> m.setTaken(taken));
        }
    }

    @BeforeEach
    void setUp() {
        FakeCaregiverRepository caregiverRepo = new FakeCaregiverRepository();
        FakeMedicationRepository medicationRepo = new FakeMedicationRepository();

        caregiverService = new CaregiverService(caregiverRepo);
        medicationService = new MedicationService(medicationRepo, caregiverService);

        Caregiver c = new Caregiver(0, "Fábio Ruan");
        caregiverRepo.save(c);

        defaultCaregiverId = caregiverRepo.findAll().get(0).getId();
    }

    @Test
    @DisplayName("Deve cadastrar medicamento vinculado a um cuidador existente")
    void shouldAddMedicationSuccessfully() {
        Medication med = medicationService.addMedication("Losartana", "50mg", "08:00", defaultCaregiverId);

        assertNotNull(med);
        assertEquals("Losartana", med.getName());
        assertEquals("Fábio Ruan", med.getCaregiver().getName());
        assertFalse(med.isTaken());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar remédio para cuidador inexistente")
    void shouldThrowWhenCaregiverNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                medicationService.addMedication("Dipirona", "500mg", "10:00", 999)
        );
    }

    @Test
    @DisplayName("Deve marcar medicamento como tomado")
    void shouldMarkAsTaken() {
        Medication med = medicationService.addMedication("Omeprazol", "20mg", "07:00", defaultCaregiverId);

        boolean result = medicationService.markAsTaken(med.getId());

        assertTrue(result);
    }

    @Test
    @DisplayName("Deve remover medicamento corretamente")
    void shouldRemoveMedication() {
        Medication med = medicationService.addMedication("Vitamina C", "1g", "09:00", defaultCaregiverId);

        boolean removed = medicationService.remove(med.getId());

        assertTrue(removed);
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com campos vazios")
    void shouldThrowWhenFieldsAreEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                medicationService.addMedication("", "500mg", "08:00", defaultCaregiverId)
        );
    }
}
