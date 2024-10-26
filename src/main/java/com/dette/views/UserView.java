package com.dette.views;

import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.User;
import com.dette.enums.Role;
import com.dette.services.servicespe.IUserService;
import com.dette.views.viewspe.IUserView;

public class UserView extends ViewImplement<User> implements IUserView {
    private IUserService userService;

    public UserView(Scanner scanner, IUserService userService) {
        super(scanner);
        this.userService = userService;
    }

    public User saisie() {
        User user = new User();
        int choix;
        System.out.println("entrer le login : ");
        user.setLogin(scanner.nextLine());
        System.out.println("entrer le mdp : ");
        user.setPassword(scanner.nextLine());
        do {
            System.out.println("1- admin");
            System.out.println("2- boutiquier");
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > 2);
        user.setRole(Role.values()[choix - 1]);
        user.setEtat(true);
        return user;
    }

    @Override
    public Role saisieRoleUser() {
        int choix;
        do {
            System.out.println("veuillez selectionner le role");
            for (Role role : Role.values()) {
                System.out.println((role.ordinal() + 1) + "-" + role.name());
            }
            // Arrays.stream(Role.values())
            // .forEach(role -> System.out.println((role.ordinal() + 1) + " - " +
            // role.name()));
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > Role.values().length);

        return Role.values()[choix - 1];
    }

    @Override
    public User getBy() {
        userService.findAll().forEach(System.out::println);
        System.out.println("Entrez le login du compte user à activer : ");
        String login = scanner.nextLine();
        User user = userService.getBy(login);
        if (user != null) {
            return user;
        } else {
            System.out.println("User not found");
            return null;
        }

    }

    @Override
    public void setEtatUser(boolean status) {
        User user = getBy();
        if (user != null) {
            if (status) {
                if (!user.getEtat()) {
                    user.setEtat(true);
                    user.onPreUpdated();
                    userService.modifier(user);
                } else {
                    System.out.println("User déjà activé");
                }
            } else {
                if (user.getEtat()) {
                    user.setEtat(false);
                    user.onPreUpdated();
                    userService.modifier(user);
                } else {
                    System.out.println("User déjà désactivé");
                }
            }
        } else {
            System.out.println("updating failed");
        }

    }

    @Override
    public User authentification() {

        System.out.println("Entrez le login du compte : ");
        String login = scanner.nextLine();

        System.out.println("entrez le mdp : ");
        String password = scanner.nextLine();

        User user = userService.connexion(login, password);

        if (user != null) {
            System.out.println("Authentification réussie ! ");
            return user;
        } else {
            System.out.println("Échec de l'authentification. Vérifiez votre login et mot de passe.");
            return null;
        }

    }

    @Override
    public void listerUserbyRole() {
        Role role = saisieRoleUser();
        userService.findAll()
                .stream()
                .filter(user -> user.getRole() == role)
                .forEach(System.out::println);
    }

    @Override
    public void listerUserActif() {
        userService.findAll()
                .stream()
                .filter(user -> user.getEtat() == true)
                .forEach(System.out::println);
    }
}
