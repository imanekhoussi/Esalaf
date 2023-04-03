package com.exemple.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO extends BaseDAO<Credit> {

    public CreditDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Credit credit) throws SQLException {
        String req = "INSERT INTO credit (id_produit, id_client) VALUES (?, ?,  ?);";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1, credit.getProduit_id());
        this.preparedStatement.setLong(2, credit.getClient_id());

        this.preparedStatement.execute();
    }



    @Override
    public void update(Credit credit) throws SQLException {
        String req = "UPDATE credit SET id_produit = ?, id_client = ?, qte = ?, date = ? WHERE id_credit = ?;";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1, credit.getProduit().getId());
        this.preparedStatement.setLong(2, credit.getClient().getId_client());
        // this.preparedStatement.setInt(3, credit.getQte());
        //this.preparedStatement.setDate(4, java.sql.Date.valueOf(credit.getDate()));

        this.preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Credit credit) throws SQLException {
        String req = "DELETE FROM credit WHERE id_credit= ?;";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1, credit.getCredit_id());

        this.preparedStatement.executeUpdate();
    }

    public void delete(long creditId) throws SQLException {
        String req = "DELETE FROM credit WHERE id_credit= ?;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, creditId);
        this.preparedStatement.executeUpdate();
    }

    @Override
    public Credit getOne(Long id) throws SQLException {
        String req = "SELECT credit.*, produit.nom AS produit_nom, client.nom AS client_nom " +
                "FROM credit " +
                "INNER JOIN produit ON credit.id_produit = produit.id_produit " +
                "INNER JOIN client ON credit.id_client = client.id_client " +
                "WHERE credit.id_credit = ?;";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1, id);

        this.resultSet = this.preparedStatement.executeQuery();

        if (this.resultSet.next()) {
            ProduitDAO produitDAO = new ProduitDAO();
            ClientDAO clientDAO = new ClientDAO();

            Produit produit = produitDAO.getOne(this.resultSet.getLong("id_produit"));
            Client client = clientDAO.getOne(this.resultSet.getLong("id_client"));
            String produitNom = this.resultSet.getString("produit_nom");
            String clientNom = this.resultSet.getString("client_nom");

            Credit credit = new Credit(
                    this.resultSet.getLong("id_credit"),
                    produit,
                    client,
                    this.resultSet.getInt("qte"),
                    this.resultSet.getDate("date").toLocalDate()
            );

            credit.getProduit().setNom(produitNom);
            credit.getClient().setNom(clientNom);

            return credit;
        } else {
            return null;
        }
    }
    public List<CreditWithNames> getAllWithNames() throws SQLException {
        List<CreditWithNames> credits = new ArrayList<>();

        String req = "SELECT credit.id_credit, produit.nom, client.nom, qte, date FROM credit " +
                "JOIN produit ON credit.id_produit = produit.id_produit " +
                "JOIN client ON credit.id_client = client.id_client;";

        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(req);

        while (this.resultSet.next()) {
            Long credit_id = this.resultSet.getLong(1);
            String productName = this.resultSet.getString(2);
            String clientName = this.resultSet.getString(3);
            int qte = this.resultSet.getInt(4);
            java.sql.Date date = this.resultSet.getDate(5);

            credits.add(new CreditWithNames(credit_id, productName, clientName, qte, date.toLocalDate()));
        }

        return credits;
    }


    @Override
    public List<Credit> getAll() throws SQLException {
        List<Credit> credits = new ArrayList<>();

        String req = "SELECT * FROM credit;";

        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(req);

        ProduitDAO produitDAO = new ProduitDAO();
        ClientDAO clientDAO = new ClientDAO();

        while (this.resultSet.next()) {
            Long credit_id = this.resultSet.getLong(1);
            Produit produit = produitDAO.getOne(this.resultSet.getLong(2));
            Client client = clientDAO.getOne(this.resultSet.getLong(3));
            int qte = this.resultSet.getInt(5);
            java.sql.Date date = this.resultSet.getDate(4);

            credits.add(new Credit(credit_id, produit, client, qte, date.toLocalDate()));
        }

        return credits;
    }

    public List<String[]> getCreditsTable() throws SQLException {
        List<String[]> creditsTable = new ArrayList<>();

        String req = "SELECT client.nom, SUM(qte * produit.prix), 'calculate' FROM credit " +
                "INNER JOIN produit ON credit.id_produit = produit.id_produit " +
                "INNER JOIN client ON credit.id_client = client.id_client " +
                "GROUP BY credit.id_client;";

        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(req);

        while (this.resultSet.next()) {
            String[] row = new String[3];
            row[0] = this.resultSet.getString(1); // client name
            row[1] = String.valueOf(this.resultSet.getDouble(2)); // total amount
            row[2] = "calculate"; // action
            creditsTable.add(row);
        }

        return creditsTable;
    }

}