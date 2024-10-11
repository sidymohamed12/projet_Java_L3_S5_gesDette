package com.controllers;

import java.util.Scanner;

import com.dette.core.Controller;
import com.dette.entities.Client;
import com.dette.entities.Dette;
import com.dette.entities.Payement;
import com.dette.enums.Etat;
import com.dette.services.servicespe.IClientService;
import com.dette.services.servicespe.IPayementService;
import com.dette.views.viewspe.IArticleView;
import com.dette.views.viewspe.IClientView;
import com.dette.views.viewspe.IDetteView;
import com.dette.views.viewspe.IPayementView;

public class BoutiquierController implements Controller {
    private Scanner scanner;
    private IClientView clientView;
    private IClientService clientService;
    private IDetteView detteView;
    private IArticleView articleView;
    private IPayementView payementView;
    private IPayementService payementService;

    public BoutiquierController(Scanner scanner,
            IClientView clientView, IClientService clientService,
            IDetteView detteView, IArticleView articleView,
            IPayementView payementView, IPayementService payementService) {
        this.scanner = scanner;
        this.clientView = clientView;
        this.clientService = clientService;
        this.detteView = detteView;
        this.articleView = articleView;
        this.payementView = payementView;
        this.payementService = payementService;
    }

    // 6 ET 7 NON FAIT & 8 non terminer

    @Override
    public int menu() {
        System.out.println("1- Créer un client");
        System.out.println("2- Lister les clients ayant un compte (avec cumul des montants dus) et pouvoir filtrer");
        System.out.println("3- Lister les clients sans un compte");
        System.out.println("4- Rechercher un client par son téléphone");
        System.out.println("5- Créer une dette pour un client");
        System.out.println("6- Enregistrer un paiement pour une dette");
        System.out.println(
                "7- Lister les dettes non soldées d'un client avec l'option de voir les articles ou les paiements");
        System.out.println(
                "8- Lister les demandes de dette (filtrer par En Cours ou Annuler) , voir les articles d'une dette et enfin valider ou refuser la dette.");
        System.out.println("9- Quitter");
        return scanner.nextInt();
    }

    @Override
    public void execute() {
        int choix;
        do {
            switch (choix = menu()) {
                case 1 -> {
                    scanner.nextLine();
                    Client client = clientView.saisie();
                    clientService.create(client);
                    clientView.linkClientUser(client);
                }
                case 2 -> {
                    clientView.listerClientUser(true);
                }
                case 3 -> {
                    clientView.listerClientUser(false);
                }
                case 4 -> {
                    scanner.nextLine();
                    clientView.searchByTelephone();
                }
                case 5 -> {
                    // clientService.findAll().forEach(System.out::println);
                    System.out.println(" choisissez le client ");
                    scanner.nextLine();
                    Client client = clientView.getBy();
                    System.out.println(client);
                    detteView.createDetteClient(client);
                }
                case 6 -> {
                    Payement payement = payementView.saisie();
                    if (payement != null) {
                        payementService.create(payement);
                    } 
                }
                case 8 -> {
                    Etat etat = detteView.saisiEtat();
                    detteView.filtrerDetteByEtat(etat);
                    System.out.println("voir détail d'une dette ?");
                    if (detteView.askToContinue()) {
                        Dette dette = detteView.getById();
                        articleView.listerArticleDette(dette);
                    }

                }
                case 9 -> {
                    System.out.println("deconnection done !");
                }
            }
        } while (choix != 9);
    }

}
