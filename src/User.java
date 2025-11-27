package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle User
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nom;
    private String telephone;
    private double solde;

    public User() { } // constructeur vide nécessaire pour Gson

    public User(String id, String nom, String telephone, double solde) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.solde = solde;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    @Override
    public String toString() {
        return String.format("ID:%s | %s | Tel:%s | Solde:%.2f", id, nom, telephone, solde);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}


