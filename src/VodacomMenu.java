package menus;

import java.util.Scanner;
import models.User;
import models.Transaction;
import services.UserService;
import services.TransactionService;

public class AirtelMenu {

    private UserService userService = new UserService();
    private TransactionService transactionService = new TransactionService();

    public void afficherMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== VODACASH (VODACOM) ===");
        System.out.println("1. Créer utilisateur");
        System.out.println("2. Déposer argent");
        System.out.println("3. Retirer argent");
        System.out.println("4. Transférer argent");
        System.out.println("5. Liste utilisateurs");
        System.out.println("6. Liste transactions");
        System.out.print("Choix : ");

        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1 -> creerUser(sc);
            case 2 -> depot(sc);
            case 3 -> retrait(sc);
            case 4 -> transfert(sc);
            case 5 -> afficherUsers();
            case 6 -> afficherTransactions();
            default -> System.out.println("Choix invalide !");
        }
    }

    private void creerUser(Scanner sc) {
        System.out.print("ID : ");
        String id = sc.nextLine();
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Téléphone : ");
        String tel = sc.nextLine();

        User user = new User(id, nom, tel, 0);
        userService.create(user);
        System.out.println("Utilisateur créé !");
    }

    private void depot(Scanner sc) {
        System.out.print("Téléphone : ");
        String tel = sc.nextLine();

        User user = userService.getByPhone(tel);
        if (user == null) {
            System.out.println("Utilisateur introuvable !");
            return;
        }

        System.out.print("Montant : ");
        double montant = sc.nextDouble();

        user.setSolde(user.getSolde() + montant);
        transactionService.create(new Transaction("T" + Math.random(), "depot", montant));

        System.out.println("Dépôt réussi !");
    }

    private void retrait(Scanner sc) {
        System.out.print("Téléphone : ");
        String tel = sc.nextLine();

        User user = userService.getByPhone(tel);
        if (user == null) {
            System.out.println("Utilisateur introuvable !");
            return;
        }

        System.out.print("Montant : ");
        double montant = sc.nextDouble();

        if (user.getSolde() < montant) {
            System.out.println("Solde insuffisant !");
            return;
        }

        user.setSolde(user.getSolde() - montant);
        transactionService.create(new Transaction("T" + Math.random(), "retrait", montant));

        System.out.println("Retrait réussi !");
    }

    private void transfert(Scanner sc) {
        System.out.print("Votre téléphone : ");
        String tel1 = sc.nextLine();
        System.out.print("Téléphone destinataire : ");
        String tel2 = sc.nextLine();

        User u1 = userService.getByPhone(tel1);
        User u2 = userService.getByPhone(tel2);

        if (u1 == null || u2 == null) {
            System.out.println("Utilisateur introuvable !");
            return;
        }

        System.out.print("Montant : ");
        double montant = sc.nextDouble();

        if (u1.getSolde() < montant) {
            System.out.println("Solde insuffisant !");
            return;
        }

        u1.setSolde(u1.getSolde() - montant);
        u2.setSolde(u2.getSolde() + montant);

        transactionService.create(new Transaction("T" + Math.random(), "transfert", montant));

        System.out.println("Transfert réussi !");
    }

    private void afficherUsers() {
        for (User u : userService.read()) {
            System.out.println(u);
        }
    }

    private void afficherTransactions() {
        for (Transaction t : transactionService.read()) {
            System.out.println(t);
        }
    }
}

