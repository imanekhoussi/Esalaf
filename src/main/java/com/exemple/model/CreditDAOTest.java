package com.exemple.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CreditDAOTest {

    public static void main(String[] args) {

        try {
            // create a credit object
            Produit produit = new Produit(1L, "Produit 1", 100.0);
            Client client = new Client(1L, "Client 1", "1234567890");
            Credit credit = new Credit(0L, produit, client, 10, LocalDate.now());

            // create a credit DAO object
            CreditDAO creditDAO = new CreditDAO();

            // save credit
            creditDAO.save(credit);

            // get all credits
            List<Credit> credits = creditDAO.getAll();

            // print all credits
            for (Credit c : credits) {
                System.out.println(c);
            }

            // update credit
            credit.setQte(20);
            creditDAO.update(credit);

            // get credit by id
            Credit retrievedCredit = creditDAO.getOne(credit.getCredit_id());

            // delete credit
            creditDAO.delete(retrievedCredit);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
