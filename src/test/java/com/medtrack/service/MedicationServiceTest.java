package com.medtrack.service;

import com.medtrack.model.Medication;
import com.medtrack.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicationServiceTest {

    private MedicationService service;

    @BeforeEach
    void setUp() {
        service = new MedicationService(new MedicationRepository());
    }

    @Test
    @DisplayName("Deve cadastrar medicamento com dados válidos")
    void shouldAddMedicationSuccessfully() {
        Medication med = service.addMedication("Paracetamol", "500mg", "08:00");

        assertNotNull(med);
        assertEquals("Paracetamol", med.getName());
        assertEquals("500mg", med.getDosage());
        assertEquals("08:00", med.getScheduleTime());
        assertFalse(med.isTaken());
    }

    @Test
    @DisplayName("Deve listar todos os medicamentos cadastrados")
    void shouldListAllMedications() {
        service.addMedication("Paracetamol", "500mg", "08:00");
        service.addMedication("Ibuprofeno", "400mg", "12:00");

        List<Medication> list = service.listAll();

        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Deve marcar medicamento como tomado")
    void shouldMarkMedicationAsTaken() {
        Medication med = service.addMedication("Omeprazol", "20mg", "07:00");

        boolean result = service.markAsTaken(med.getId());

        assertTrue(result);
        assertTrue(med.isTaken());
    }

    @Test
    @DisplayName("Deve remover medicamento existente")
    void shouldRemoveMedication() {
        Medication med = service.addMedication("Losartana", "50mg", "20:00");

        boolean removed = service.remove(med.getId());

        assertTrue(removed);
        assertTrue(service.listAll().isEmpty());
    }


    @Test
    @DisplayName("Deve lançar exceção ao cadastrar medicamento com nome vazio")
    void shouldThrowWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMedication("", "500mg", "08:00"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar medicamento com dosagem vazia")
    void shouldThrowWhenDosageIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMedication("Paracetamol", "", "08:00"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar medicamento com horário vazio")
    void shouldThrowWhenTimeIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMedication("Paracetamol", "500mg", ""));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar medicamento com nome nulo")
    void shouldThrowWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMedication(null, "500mg", "08:00"));
    }


    @Test
    @DisplayName("Deve retornar false ao tentar marcar ID inexistente")
    void shouldReturnFalseWhenMarkingNonExistentId() {
        boolean result = service.markAsTaken(999);
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover ID inexistente")
    void shouldReturnFalseWhenRemovingNonExistentId() {
        boolean result = service.remove(999);
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há medicamentos")
    void shouldReturnEmptyListWhenNoMedications() {
        List<Medication> list = service.listAll();
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("IDs gerados devem ser únicos e sequenciais")
    void shouldGenerateUniqueIds() {
        Medication first = service.addMedication("Med A", "10mg", "08:00");
        Medication second = service.addMedication("Med B", "20mg", "12:00");

        assertNotEquals(first.getId(), second.getId());
    }
}
