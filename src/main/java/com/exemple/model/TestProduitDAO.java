package com.exemple.model;

import java.sql.SQLException;
import java.util.List;

public class TestProduitDAO {
    public static void main(String[] args) {
        try {
            ProduitDAO produitDAO = new ProduitDAO();

            // Insert a new produit
            Produit newProduit = new Produit();
            newProduit.setNom("Product 1");
            newProduit.setPrix(100.0);
            produitDAO.save(newProduit);

            // Update an existing produit
            Produit existingProduit = produitDAO.getOne(1L);
            existingProduit.setPrix(200.0);
            produitDAO.update(existingProduit);

            // Delete a produit
            Produit produitToDelete = produitDAO.getOne(2L);
            produitDAO.delete(produitToDelete);

            // Select all produits
            List<Produit> produits = produitDAO.getAll();
            for (Produit produit : produits) {
                System.out.println(produit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
