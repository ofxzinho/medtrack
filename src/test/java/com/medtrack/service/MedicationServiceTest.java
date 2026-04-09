package com.medtrack.service;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.repository.CaregiverRepository;
import com.medtrack.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}
