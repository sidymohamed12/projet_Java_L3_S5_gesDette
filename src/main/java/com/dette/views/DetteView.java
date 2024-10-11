package com.dette.views;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.Article;
import com.dette.entities.Client;
import com.dette.entities.Detail;
import com.dette.entities.Dette;
import com.dette.enums.Etat;
import com.dette.enums.Role;
import com.dette.services.servicespe.IArticleService;
import com.dette.services.servicespe.IDetailService;
import com.dette.services.servicespe.IDetteService;
import com.dette.views.viewspe.IDetteView;

public class DetteView extends ViewImplement<Dette> implements IDetteView {

    private IArticleService articleService;
    private IDetailService detailService;
    private IDetteService detteService;

    public DetteView(Scanner scanner, IArticleService articleService, IDetailService detailService,
            IDetteService detteService) {
        super(scanner);
        this.articleService = articleService;
        this.detailService = detailService;
        this.detteService = detteService;
    }

    @Override
    public Dette saisie() {
        Dette dette = new Dette();
        dette.setDate(LocalDateTime.now());
        dette.setEtatD(Etat.encours);
        dette.setMontantVerser(0.0);
        dette.setMontantRestant(dette.getMontant());
        dette.setMontant(0.0);
        dette.setArchiver(false);
        dette.onPrePersist();
        return dette;
    }

    @Override
    public void createDetteClient(Client client) {
        Dette dette = saisie();
        dette.setClientD(client);
        client.addDettes(dette);
        do {
            articleService.findAll()
                    .stream()
                    .filter(art -> art.getQteStock() != 0)
                    .forEach(System.out::println);

            System.out.println("Choisissez l'article par son ref : ");
            Article article = articleService.getBy(scanner.nextLine());
            System.out.println(article);
            int qte;

            do {
                System.out.println("Choisissez la quantité de l'article");
                qte = scanner.nextInt();
            } while (qte > article.getQteStock() || qte <= 0);

            double montantArticle = qte * article.getPrix();
            dette.setMontant(dette.getMontant() + montantArticle);
            dette.setMontantRestant(dette.getMontant());

            Detail detail = new Detail();
            detail.setQteVendu(qte);
            detail.setMontantVendu(montantArticle);
            detail.setArticle(article);
            detail.setDette(dette); 
            detail.onPrePersist();
            detailService.create(detail);

            dette.addDetail(detail);

            System.out.println("Voulez-vous continuer ? ");
        } while (askToContinue());

        detteService.create(dette);
    }

    @Override
    public Dette getBy() {
        // detteService.findAll().forEach(System.out::println);
        // System.out.println("Entrez le telephone du client : ");
        // String tel = scanner.nextLine();
        // Dette dette = detteService.getBy(tel);
        // return dette;
        return null;
    }

    @Override
    public Etat saisiEtat() {
        int choix;
        do {
            System.out.println("veuillez selectionner l'etat");
            for (Etat etat : Etat.values()) {
                System.out.println(etat.getId() + "-" + etat.name());
            }
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > Role.values().length);

        return Etat.getEtatById(choix);
    }

    @Override
    public void listerDetteNonSolde() {
        detteService.findAll()
                .stream()
                .filter(dette -> dette.getMontant() != dette.getMontantVerser())
                .forEach(System.out::println);
    }

    @Override
    public void listerDetteSolde() {
        detteService.findAll()
                .stream()
                .filter(dette -> dette.getMontant() == dette.getMontantVerser())
                .forEach(System.out::println);
    }

    @Override
    public void filtrerDetteByEtat(Etat etat) {
        detteService.findAll()
                .stream()
                .filter(dette -> dette.getEtatD() == etat)
                .forEach(System.out::println);
    }

    @Override
    public Dette getById() {
        detteService.findAll().forEach(System.out::println);
        System.out.println("Entrez l'id de la dette : ");
        int id = scanner.nextInt();
        Dette dette = detteService.getById(id);
        if (dette != null) {
            return dette;
        } else {
            System.out.println("No dette found");
            return null;
        }
    }

    @Override
    public void archiverDette() {
        // listerDetteSolde();
        // Dette dette = getById();
        // if (dette != null && dette.getMontant()==dette.getMontantVerser()) {
        // detteService.modifier(dette);
        // }
    }

}
