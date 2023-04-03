package com.exemple.model;

import java.time.LocalDate;

// Java beans (Entity)
public class Credit {

    private Long credit_id= 1L;
    private Long produit_id;
    private Long client_id;
    private Produit produit;
    private Client client;
    private String productName;
    private String clientName;
    private int qte;
    private LocalDate date;

    public Credit() {
    }

    public Credit(Long credit_id,Long produit_id) {
        this.qte=1;
        this.produit_id = produit_id;
        this.client_id=client_id;

    }


    public Credit(Long credit_id, Produit produit, Client client, int qte, LocalDate date) {
        this.credit_id = credit_id;
        this.produit = produit;
        this.client = client;
        this.qte = qte;
        this.date = date;
    }
    public Credit(Produit produit, Client client, String productName, String clientName, int qte, LocalDate date) {
        this.produit = produit;
        this.client = client;
        this.productName = productName;
        this.clientName = clientName;
        this.qte = qte;
        this.date = date;
    }

    public Credit(Long credit_id, Produit produit, Client client, String productName, String clientName, int qte, LocalDate date) {
        this.credit_id = credit_id;
        this.produit = produit;
        this.client = client;
        this.productName = productName;
        this.clientName = clientName;
        this.qte = qte;
        this.date = date;
    }

    public Credit(int qte, LocalDate date) {
        this.qte = qte;
        this.date = date;
    }

    public Credit(Long creditId, int qte, LocalDate date) {
        this.qte = qte;
        this.date = date;
        this.credit_id = credit_id;

    }

    public Long getProduit_id() {
        return produit_id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public Long getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(Long credit_id) {
        this.credit_id = credit_id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduitId(Produit produit) {
        this.produit = produit;
    }

    public Client getClient() {
        return client;
    }

    public void setClientId(Client client) {
        this.client = client;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Long getClientId() {
        if (client != null) {
            return client.getId_client();
        } else {
            return null;
        }
    }
    public Long getProduitId() {
        return this.produit.getId();
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "credit_id=" + credit_id +
                ", produit=" + produit +
                ", client=" + client +
                ", qte=" + qte +
                ", date=" + date +
                '}';
    }
}
