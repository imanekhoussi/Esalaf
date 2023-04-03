package com.exemple.model;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit> {
    public ProduitDAO() throws SQLException {
    }

    public void save(Produit object) throws SQLException {
        String req = "insert into produit (nom, prix) values (?, ?)";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1, object.getNom());
        this.preparedStatement.setDouble(2, object.getPrix());
        this.preparedStatement.execute();
    }

    public void update(Produit object) throws SQLException {
        String req = "update produit set nom=?, prix=? where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1, object.getNom());
        this.preparedStatement.setDouble(2, object.getPrix());
        this.preparedStatement.setLong(3, object.getId());
        this.preparedStatement.executeUpdate();
    }


    public void delete(Produit object) throws SQLException {
        String req = "delete from produit where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, object.getId());
        this.preparedStatement.executeUpdate();
    }

    public Produit getOne(Long id) throws SQLException {
        String req = "select * from produit where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();

        if (this.resultSet.next()) {
            return new Produit(this.resultSet.getLong(1), this.resultSet.getString(2), this.resultSet.getDouble(3));
        }

        return null;
    }

    public List<Produit> getAll() throws SQLException {
        List<Produit> produits = new ArrayList();
        String req = "select * from produit";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(req);

        while (this.resultSet.next()) {
            Produit produit = new Produit();
            produit.setId(this.resultSet.getLong("id_produit"));
            produit.setNom(this.resultSet.getString("nom"));
            produit.setPrix(this.resultSet.getDouble("prix"));
            produits.add(produit);
        }

        return produits;
    }
    public String getProductName(Long id) {
        String req = "SELECT nom FROM produit WHERE id_produit = ?;";
        try {
            this.preparedStatement = this.connection.prepareStatement(req);
            this.preparedStatement.setLong(1, id);
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                return this.resultSet.getString(1);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (this.resultSet != null) {
                    this.resultSet.close();
                }
                if (this.preparedStatement != null) {
                    this.preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
