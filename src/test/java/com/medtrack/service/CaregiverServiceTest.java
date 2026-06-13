package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.repository.CaregiverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaregiverServiceTest {

    private CaregiverService caregiverService;

    @BeforeEach
    void setUp() {
        caregiverService = new CaregiverService(new CaregiverRepository());
    }

    @Test
    @DisplayName("Deve remover cuidador sem medicamentos vinculados")
    void shouldDeleteCaregiverWithoutMedications() {
        Caregiver caregiver = caregiverService.addCaregiver("Cuidador Teste Remover");
        int id = caregiver.getId();
        boolean result = caregiverService.delete(id);
        assertTrue(result);
        assertTrue(caregiverService.findById(id).isEmpty());
    }

    @Test
    @DisplayName("Não deve remover cuidador inexistente")
    void shouldReturnFalseWhenCaregiverNotFound() {
        boolean result = caregiverService.delete(999999);
        assertFalse(result);
    }
}
