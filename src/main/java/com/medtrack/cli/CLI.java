package com.medtrack.cli;

import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import com.medtrack.service.CaregiverService;
import com.medtrack.service.MedicationService;
import com.medtrack.service.OpenFdaService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CLI {

    private final MedicationService medService;
    private final CaregiverService caregiverService;
    private final OpenFdaService openFdaService;
    private final Scanner scanner;

    public CLI(MedicationService medService, CaregiverService caregiverService,
               OpenFdaService openFdaService, Scanner scanner) {
        this.medService = medService;
        this.caregiverService = caregiverService;
        this.openFdaService = openFdaService;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║          MEDTRACK v1.2.0           ║");
        System.out.println("║   Monitoramento de Saúde Familiar  ║");
        System.out.println("╚════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addCaregiver();
                case "2" -> listCaregivers();
                case "3" -> removeCaregiver();
                case "4" -> addMedication();
                case "5" -> listMedications();
                case "6" -> editMedication();
                case "7" -> markTaken();
                case "8" -> removeMedication();
                case "9" -> searchFda();
                case "0" -> {
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
        System.out.println("3. Remover Cuidador");
        System.out.println("4. Cadastrar Medicamento");
        System.out.println("5. Listar Medicamentos");
        System.out.println("6. Editar Medicamento");
        System.out.println("7. Marcar como Tomado");
        System.out.println("8. Remover Medicamento");
        System.out.println("9. Consultar Medicamento na FDA");
        System.out.println("0. Sair");
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

    private void removeCaregiver() {
        System.out.print("\nID do cuidador a remover: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Caregiver> caregiverOpt = caregiverService.findById(id);
            if (caregiverOpt.isEmpty()) {
                System.out.println("⚠ Cuidador não encontrado.");
                return;
            }
            int count = medService.countByCaregiverId(id);
            if (count > 0) {
                System.out.println("⚠ Não é possível remover. Existem " + count
                        + " medicamento(s) vinculado(s) a este cuidador.");
                return;
            }
            caregiverService.delete(id);
            System.out.println("✔ Cuidador removido com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
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
            medService.addMedication(name, dosage, time, caregiverId);
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

    private void editMedication() {
        System.out.print("\nID do medicamento a editar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Medication> medOpt = medService.findById(id);
            if (medOpt.isEmpty()) {
                System.out.println("⚠ Medicamento não encontrado.");
                return;
            }
            Medication current = medOpt.get();

            System.out.print("Novo nome (atual: " + current.getName() + "): ");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                name = current.getName();
            }

            System.out.print("Nova dosagem (atual: " + current.getDosage() + "): ");
            String dosage = scanner.nextLine();
            if (dosage.isBlank()) {
                dosage = current.getDosage();
            }

            System.out.print("Novo horário (atual: " + current.getScheduleTime() + "): ");
            String time = scanner.nextLine();
            if (time.isBlank()) {
                time = current.getScheduleTime();
            }

            if (medService.update(id, name, dosage, time)) {
                System.out.println("✔ Medicamento atualizado com sucesso.");
            } else {
                System.out.println("⚠ Medicamento não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
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

    private void searchFda() {
        System.out.print("\nNome do medicamento para consultar na FDA (ex: losartana): ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("⚠ Nome inválido.");
            return;
        }
        System.out.println("🔍 Consultando base da FDA...");
        String resultado = openFdaService.buscarMedicamento(nome);
        System.out.println(resultado);
    }
}
