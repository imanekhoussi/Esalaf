package com.exemple.model;

public class Produit {
    private Long id;
    private String nom;
    private double prix;


    public Produit() {
    }
    public Produit(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }
    public Produit(Long id, String nom, double prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                "DH" ;
    }
}
