package com.controllers;

import java.util.Scanner;

public class ClientController {
    private Scanner scanner;
    
    public ClientController(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showClientMenu(){
        System.out.println("1-  Lister ses dettes non soldées avec l’option de voir les articles ou les paiements");
        System.out.println("2-  Faire une demande de Dette");
        System.out.println("3-  Filtrer demandes de dette par état(En Cours, ou Annuler)");
        System.out.println("4-  Envoyer une relance pour une demande de dette annuler");
        System.out.println("5- Quitter");
        return scanner.nextInt();
    }
}
