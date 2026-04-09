package com.medtrack.cli;

import com.medtrack.model.Medication;
import com.medtrack.service.MedicationService;
import java.util.List;
import java.util.Scanner;


public class CLI {

    private final MedicationService service;
    private final Scanner scanner;


    public CLI(MedicationService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }


    public void start() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║       MedTrack v1.0.0      ║");
        System.out.println("║  Controle de Medicamentos  ║");
        System.out.println("╚════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addMedication();
                case "2" -> listMedications();
                case "3" -> markTaken();
                case "4" -> removeMedication();
                case "5" -> {
                    System.out.println("\nAté logo! Cuide-se.");
                    running = false;
                }
                default -> System.out.println("\n⚠ Opção inválida. Tente novamente.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Cadastrar medicamento");
        System.out.println("2. Listar medicamentos");
        System.out.println("3. Marcar como tomado");
        System.out.println("4. Remover medicamento");
        System.out.println("5. Sair");
        System.out.print("Escolha: ");
    }

    private void addMedication() {
        System.out.print("\nNome do medicamento: ");
        String name = scanner.nextLine();
        System.out.print("Dosagem (ex: 500mg): ");
        String dosage = scanner.nextLine();
        System.out.print("Horário (ex: 08:00): ");
        String time = scanner.nextLine();

        try {
            Medication med = service.addMedication(name, dosage, time);
            System.out.println("\n✔ Medicamento cadastrado: " + med);
        } catch (IllegalArgumentException e) {
            System.out.println("\n⚠ Erro: " + e.getMessage());
        }
    }

    private void listMedications() {
        List<Medication> list = service.listAll();
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
            if (service.markAsTaken(id)) {
                System.out.println("✔ Medicamento marcado como tomado.");
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
            if (service.remove(id)) {
                System.out.println("✔ Medicamento removido.");
            } else {
                System.out.println("⚠ Medicamento não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
        }
    }
}
