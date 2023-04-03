package com.exemple.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends  BaseDAO<Client> {
    public ClientDAO() throws SQLException {

        super();
    }


    // mapping objet --> relation
    @Override
    public void save(Client object) throws SQLException {

        String req = "insert into client (nom , telephone) values (? , ?) ;";


        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setString(1 , object.getNom());
        this.preparedStatement.setString(2 , object.getTelepehone());


        this.preparedStatement.execute();

    }

    public void update(Client client) throws SQLException {
        String query = "UPDATE client SET nom=?, telephone=? WHERE id_client=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getTelepehone());
            preparedStatement.setLong(3, client.getId_client());

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void delete(Client object) throws SQLException {
        String req = "delete from client where id_client = ? ;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, object.getId_client());
        this.preparedStatement.execute();
    }


    @Override
    public Client getOne(Long id) throws SQLException {
        return null;
    }


    // mapping relation --> objet
    @Override
    public List<Client> getAll() throws SQLException{

        List<Client> mylist = new ArrayList<Client>();
        String req = " select * from client" ;


        this.statement = this.connection.createStatement();

       this.resultSet =  this.statement.executeQuery(req);

       while (this.resultSet.next()){

           mylist.add( new Client(this.resultSet.getLong(1) , this.resultSet.getString(2),
                   this.resultSet.getString(3)));


       }

        return mylist;
    }
    public String getClientName(Long id) throws SQLException {
        String query = "SELECT client_name FROM clients WHERE id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String clientName = null;

        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                clientName = resultSet.getString("client_name");
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        // Debug print statement
        System.out.println("getClientName: id=" + id + ", clientName=" + clientName);

        return clientName;
    }


}
