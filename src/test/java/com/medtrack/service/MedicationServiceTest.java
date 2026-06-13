package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.repository.CaregiverRepository;
import com.medtrack.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicationServiceTest {

    private MedicationService medicationService;
    private CaregiverService caregiverService;
    private int defaultCaregiverId;

    @BeforeEach
    void setUp() {
        CaregiverRepository caregiverRepo = new CaregiverRepository();
        MedicationRepository medicationRepo = new MedicationRepository();

        caregiverService = new CaregiverService(caregiverRepo);

        medicationService = new MedicationService(medicationRepo, caregiverService);

        Caregiver c = caregiverService.addCaregiver("Fábio Ruan");
        defaultCaregiverId = c.getId();
    }

    @Test
    @DisplayName("Deve cadastrar medicamento vinculado a um cuidador existente")
    void shouldAddMedicationSuccessfully() {
        Medication med = medicationService.addMedication("Losartana", "50mg", "08:00", defaultCaregiverId);

        assertNotNull(med);
        assertEquals("Losartana", med.getName());
        assertEquals("Fábio Ruan", med.getCaregiver().getName()); // Verifica se o vínculo funcionou
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
        assertTrue(med.isTaken());
    }

    @Test
    @DisplayName("Deve remover medicamento corretamente")
    void shouldRemoveMedication() {
        Medication med = medicationService.addMedication("Vitamina C", "1g", "09:00", defaultCaregiverId);

        boolean removed = medicationService.remove(med.getId());

        assertTrue(removed);
        assertTrue(medicationService.listAll().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com campos vazios")
    void shouldThrowWhenFieldsAreEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                medicationService.addMedication("", "500mg", "08:00", defaultCaregiverId)
        );
    }

    @Test
    @DisplayName("Deve editar medicamento existente")
    void shouldUpdateMedication() {
        Medication med = medicationService.addMedication("Losartana", "50mg", "08:00", defaultCaregiverId);
        boolean result = medicationService.update(med.getId(), "Novo Nome", "100mg", "10:00");
        assertTrue(result);
    }

    @Test
    @DisplayName("Não deve editar medicamento inexistente")
    void shouldReturnFalseWhenMedicationNotFound() {
        boolean result = medicationService.update(999999, "X", "X", "X");
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao editar com campos vazios")
    void shouldThrowWhenUpdateFieldsAreEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                medicationService.update(1, "", "100mg", "10:00")
        );
    }

    @Test
    @DisplayName("Deve contar medicamentos por cuidador")
    void shouldCountMedicationsByCaregiver() {
        medicationService.addMedication("Med1", "10mg", "08:00", defaultCaregiverId);
        medicationService.addMedication("Med2", "20mg", "12:00", defaultCaregiverId);
        int count = medicationService.countByCaregiverId(defaultCaregiverId);
        assertEquals(2, count);
    }
}
