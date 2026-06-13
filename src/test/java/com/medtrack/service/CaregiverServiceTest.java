package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.repository.CaregiverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Deve cadastrar cuidador com nome válido")
    void shouldAddCaregiverSuccessfully() {
        Caregiver caregiver = caregiverService.addCaregiver("Ana Lima");
        assertNotNull(caregiver);
        assertEquals("Ana Lima", caregiver.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com nome nulo")
    void shouldThrowWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                caregiverService.addCaregiver(null));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com nome em branco")
    void shouldThrowWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                caregiverService.addCaregiver("   "));
    }

    @Test
    @DisplayName("Deve listar todos os cuidadores")
    void shouldListAllCaregivers() {
        caregiverService.addCaregiver("Ana Lima");
        caregiverService.addCaregiver("Carlos Melo");
        assertEquals(2, caregiverService.listAll().size());
    }

    @Test
    @DisplayName("Deve encontrar cuidador pelo ID")
    void shouldFindCaregiverById() {
        caregiverService.addCaregiver("Beatriz Souza");
        Optional<Caregiver> found = caregiverService.findById(1);
        assertTrue(found.isPresent());
        assertEquals("Beatriz Souza", found.get().getName());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para ID inexistente")
    void shouldReturnEmptyWhenIdNotFound() {
        assertTrue(caregiverService.findById(999).isEmpty());
    }

    @Test
    @DisplayName("Deve remover cuidador existente")
    void shouldDeleteCaregiverSuccessfully() {
        caregiverService.addCaregiver("Cuidador Teste");
        boolean result = caregiverService.delete(1);
        assertTrue(result);
        assertTrue(caregiverService.findById(1).isEmpty());
    }

    @Test
    @DisplayName("Não deve remover cuidador inexistente")
    void shouldReturnFalseWhenCaregiverNotFound() {
        boolean result = caregiverService.delete(999999);
        assertFalse(result);
    }
}
