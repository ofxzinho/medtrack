package com.medtrack.cli;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.service.CaregiverService;
import com.medtrack.service.MedicationService;
import java.util.List;
import java.util.Scanner;


public class CLI {

    private final MedicationService medService;
    private final CaregiverService caregiverService;
    private final Scanner scanner;

    public CLI(MedicationService medService, CaregiverService caregiverService, Scanner scanner) {
        this.medService = medService;
        this.caregiverService = caregiverService;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║         MEDTRACK v1.1.0            ║");
        System.out.println("║   Monitoramento de Saúde Familiar  ║");
        System.out.println("╚════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addCaregiver();
                case "2" -> listCaregivers();
                case "3" -> addMedication();
                case "4" -> listMedications();
                case "5" -> markTaken();
                case "6" -> removeMedication();
                case "7" -> {
                    System.out.println("\nAté logo! Cuide-se.");
                    running = false;
                }
                default -> System.out.println("\n⚠ Opção inválida.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Cadastrar Cuidador");
        System.out.println("2. Listar Cuidadores");
        System.out.println("3. Cadastrar Medicamento");
        System.out.println("4. Listar Medicamentos");
        System.out.println("5. Marcar como Tomado");
        System.out.println("6. Remover Medicamento");
        System.out.println("7. Sair");
        System.out.print("Escolha: ");
    }

    private void addCaregiver() {
        System.out.print("\nNome do Cuidador: ");
        String name = scanner.nextLine();
        try {
            Caregiver c = caregiverService.addCaregiver(name);
            System.out.println("✔ Cuidador cadastrado com ID: " + c.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Erro: " + e.getMessage());
        }
    }

    private void listCaregivers() {
        List<Caregiver> list = caregiverService.listAll();
        if (list.isEmpty()) {
            System.out.println("\nNenhum cuidador cadastrado.");
        } else {
            System.out.println("\n--- CUIDADORES ---");
            list.forEach(System.out::println);
        }
    }

    private void addMedication() {
        if (caregiverService.listAll().isEmpty()) {
            System.out.println("\n⚠ Erro: Cadastre um cuidador primeiro (Opção 1).");
            return;
        }

        System.out.print("\nID do Cuidador responsável: ");
        int caregiverId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nome do medicamento: ");
        String name = scanner.nextLine();
        System.out.print("Dosagem: ");
        String dosage = scanner.nextLine();
        System.out.print("Horário (ex: 08:00): ");
        String time = scanner.nextLine();

        try {
            Medication med = medService.addMedication(name, dosage, time, caregiverId);
            System.out.println("\n✔ Medicamento cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n⚠ Erro: " + e.getMessage());
        }
    }

    private void listMedications() {
        List<Medication> list = medService.listAll();
        if (list.isEmpty()) {
            System.out.println("\nNenhum medicamento cadastrado.");
        } else {
            System.out.println("\n--- MEDICAMENTOS ---");
            list.forEach(System.out::println);
        }
    }

    private void markTaken() {
        System.out.print("\nID do medicamento tomado: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (medService.markAsTaken(id)) {
                System.out.println("✔ Dose confirmada.");
            } else {
                System.out.println("⚠ Medicamento não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
        }
    }

    private void removeMedication() {
        System.out.print("\nID do medicamento a remover: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (medService.remove(id)) {
                System.out.println("✔ Removido com sucesso.");
            } else {
                System.out.println("⚠ ID não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
        }
    }
}
