package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modèle Transaction
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String type; // "depot", "retrait", "transfert"
    private double montant;
    private String fromPhone; // optionnel : expéditeur (pour transfert)
    private String toPhone;   // optionnel : destinataire (pour transfert)
    private String dateIso;   // stocke date en ISO string

    public Transaction() { }

    public Transaction(String id, String type, double montant, String fromPhone, String toPhone) {
        this.id = id;
        this.type = type;
        this.montant = montant;
        this.fromPhone = fromPhone;
        this.toPhone = toPhone;
        this.dateIso = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public double getMontant() { return montant; }
    public String getFromPhone() { return fromPhone; }
    public String getToPhone() { return toPhone; }
    public String getDateIso() { return dateIso; }

    @Override
    public String toString() {
        if ("transfert".equalsIgnoreCase(type)) {
            return String.format("%s | TRANSFERT | %.2f | %s -> %s | %s", id, montant, fromPhone, toPhone, dateIso);
        } else {
            return String.format("%s | %s | %.2f | %s", id, type.toUpperCase(), montant, dateIso);
        }
    }
}


