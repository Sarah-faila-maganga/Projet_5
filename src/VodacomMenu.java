package menus;

import models.User;
import models.Transaction;
import services.UserService;
import services.TransactionService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Menu console principal (Vodacom/M-PESA style)
 */
public class VodacomMenu {

    private final UserService userService;
    private final TransactionService transactionService;
    private final Scanner sc = new Scanner(System.in);

    public VodacomMenu() {
        this.userService = new UserService();
        this.transactionService = new TransactionService();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== VODACASH (VODACOM) ===");
            System.out.println("1. Créer utilisateur");
            System.out.println("2. Déposer");
            System.out.println("3. Retirer");
            System.out.println("4. Transférer");
            System.out.println("5. Lister utilisateurs");
            System.out.println("6. Lister transactions");
            System.out.println("7. Modifier utilisateur");
            System.out.println("8. Supprimer utilisateur");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");

            String line = sc.nextLine();
            switch (line) {
                case "1" -> createUser();
                case "2" -> deposit();
                case "3" -> withdraw();
                case "4" -> transfer();
                case "5" -> listUsers();
                case "6" -> listTransactions();
                case "7" -> updateUser();
                case "8" -> deleteUser();
                case "0" -> {
                    System.out.println("Au revoir !");
                    running = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void createUser() {
        System.out.print("ID utilisateur: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) id = UUID.randomUUID().toString();

        System.out.print("Nom: ");
        String nom = sc.nextLine();

        System.out.print("Téléphone: ");
        String tel = sc.nextLine();

        User u = new User(id, nom, tel, 0.0);
        userService.create(u);
        System.out.println("Utilisateur créé: " + u);
    }

    private void deposit() {
        System.out.print("Téléphone: ");
        String tel = sc.nextLine();

        User u = userService.getByPhone(tel);
        if (u == null) { System.out.println("Utilisateur introuvable."); return; }

        System.out.print("Montant: ");
        double m = readDouble();
        u.setSolde(u.getSolde() + m);
        userService.update(u.getId(), u);

        Transaction t = new Transaction("T-"+UUID.randomUUID(), "depot", m, null, tel);
        transactionService.create(t);
        System.out.println("Dépôt effectué.");
    }

    private void withdraw() {
        System.out.print("Téléphone: ");
        String tel = sc.nextLine();

        User u = userService.getByPhone(tel);
        if (u == null) { System.out.println("Utilisateur introuvable."); return; }

        System.out.print("Montant: ");
        double m = readDouble();
        if (u.getSolde() < m) { System.out.println("Solde insuffisant."); return; }

        u.setSolde(u.getSolde() - m);
        userService.update(u.getId(), u);

        Transaction t = new Transaction("T-"+UUID.randomUUID(), "retrait", m, tel, null);
        transactionService.create(t);
        System.out.println("Retrait effectué.");
    }

    private void transfer() {
        System.out.print("Votre téléphone: ");
        String from = sc.nextLine();
        System.out.print("Téléphone destinataire: ");
        String to = sc.nextLine();

        User uFrom = userService.getByPhone(from);
        User uTo = userService.getByPhone(to);

        if (uFrom == null || uTo == null) { System.out.println("Expéditeur ou destinataire introuvable."); return; }

        System.out.print("Montant: ");
        double m = readDouble();
        if (uFrom.getSolde() < m) { System.out.println("Solde insuffisant."); return; }

        uFrom.setSolde(uFrom.getSolde() - m);
        uTo.setSolde(uTo.getSolde() + m);
        userService.update(uFrom.getId(), uFrom);
        userService.update(uTo.getId(), uTo);

        Transaction t = new Transaction("T-"+UUID.randomUUID(), "transfert", m, from, to);
        transactionService.create(t);
        System.out.println("Transfert effectué.");
    }

    private void listUsers() {
        List<User> users = userService.read();
        if (users.isEmpty()) System.out.println("Aucun utilisateur.");
        else users.forEach(System.out::println);
    }

    private void listTransactions() {
        List<Transaction> txs = transactionService.read();
        if (txs.isEmpty()) System.out.println("Aucune transaction.");
        else txs.forEach(System.out::println);
    }

    private void updateUser() {
        System.out.print("ID utilisateur à modifier: ");
        String id = sc.nextLine();
        User u = userService.getById(id);
        if (u == null) { System.out.println("Utilisateur introuvable."); return; }
        System.out.print("Nouveau nom (" + u.getNom() + "): ");
        String nom = sc.nextLine();
        if (!nom.isEmpty()) u.setNom(nom);
        System.out.print("Nouveau téléphone (" + u.getTelephone() + "): ");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) u.setTelephone(tel);
        System.out.print("Nouveau solde (" + u.getSolde() + "): ");
        String s = sc.nextLine();
        if (!s.isEmpty()) {
            try { u.setSolde(Double.parseDouble(s)); } catch (NumberFormatException e) { System.out.println("Valeur solde invalide."); }
        }
        userService.update(id, u);
        System.out.println("Utilisateur mis à jour.");
    }

    private void deleteUser() {
        System.out.print("ID utilisateur à supprimer: ");
        String id = sc.nextLine();
        userService.delete(id);
        System.out.println("Si l'utilisateur existait, il est supprimé.");
    }

    private double readDouble() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Montant invalide, réessaye: ");
            }
        }
    }
}



