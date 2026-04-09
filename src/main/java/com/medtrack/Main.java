package com.medtrack;

import com.medtrack.cli.CLI;
import com.medtrack.repository.MedicationRepository;
import com.medtrack.service.MedicationService;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        MedicationRepository repository = new MedicationRepository();
        MedicationService service = new MedicationService(repository);
        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI(service, scanner);
        cli.start();
    }
}
