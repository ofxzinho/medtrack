package com.medtrack;

import com.medtrack.cli.CLI;
import com.medtrack.repository.CaregiverRepository;
import com.medtrack.repository.MedicationRepository;
import com.medtrack.service.CaregiverService;
import com.medtrack.service.MedicationService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MedicationRepository medRepo = new MedicationRepository();
        CaregiverRepository caregiverRepo = new CaregiverRepository();

        CaregiverService caregiverService = new CaregiverService(caregiverRepo);
        MedicationService medService = new MedicationService(medRepo, caregiverService);

        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI(medService, caregiverService, scanner);
        cli.start();
    }
}
