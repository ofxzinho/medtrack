package com.medtrack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.medtrack.model.Caregiver;
import com.medtrack.repository.CaregiverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaregiverServiceTest {

    private CaregiverService caregiverService;
    private FakeCaregiverRepository fakeRepo;

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

        @Override
        public boolean delete(int id) {
            return list.removeIf(c -> c.getId() == id);
        }
    }

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeCaregiverRepository();
        caregiverService = new CaregiverService(fakeRepo);
    }

    @Test
    @DisplayName("Deve remover cuidador sem medicamentos vinculados")
    void shouldDeleteCaregiverWithoutMedications() {
        caregiverService.addCaregiver("Cuidador Teste Remover");


        int id = fakeRepo.findAll().get(0).getId();

        boolean result = caregiverService.delete(id);
        assertTrue(result);

        assertTrue(fakeRepo.findById(id).isEmpty());
    }

    @Test
    @DisplayName("Não deve remover cuidador inexistente")
    void shouldReturnFalseWhenCaregiverNotFound() {
        boolean result = caregiverService.delete(999999);
        assertFalse(result);
    }
}
