package com.dette.views;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.Dette;
import com.dette.entities.Payement;
import com.dette.enums.Etat;
import com.dette.services.servicespe.IDetteService;
import com.dette.services.servicespe.IPayementService;
import com.dette.views.viewspe.IPayementView;

public class PayementView extends ViewImplement<Payement> implements IPayementView {

    private IDetteService detteService;
    private IPayementService payementService;
    public PayementView(Scanner scanner, IPayementService payementService, IDetteService detteService) {
        super(scanner);
        this.detteService = detteService;
        this.payementService = payementService;
    }

    @Override
    public Payement saisie() {

        detteService.findAll().stream().filter(dette -> dette.getEtatD() == Etat.accepter).forEach(System.out::println);
        System.out.println("choisissez la dette à payer par id");
        Dette dette = detteService.getById(scanner.nextInt());
        double montantRestant = dette.getMontant()-dette.getMontantVerser();
        if (dette != null && dette.getEtatD() == Etat.accepter && dette.getMontant() != dette.getMontantVerser()) {
            Payement payement = new Payement();
            payement.setDate(LocalDateTime.now());
            System.out.println("Montant Restant : " + montantRestant);
            Double verser ;
            do {
                System.out.println("entrez le montant à payer : ");
                verser = scanner.nextDouble();
            } while (verser <= 0 || verser > montantRestant);

            payement.setMontant(verser);

            dette.addPayement(payement);
            dette.setMontantVerser(dette.getMontantVerser() + verser);
            dette.onPreUpdatet();
            detteService.modifier(dette);

            payement.setDette(dette);
            payement.onPrePersist();
            return payement;
        } else {
            System.out.println("dette not confirmed");
        }
        return null;
    }

    @Override
    public Payement getBy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }

    @Override
    public void listePayementsDette(Dette dette) {
        payementService.getPayementsDette(dette).forEach(System.out::println);
    }

}
