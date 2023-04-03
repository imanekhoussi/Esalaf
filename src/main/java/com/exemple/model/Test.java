package com.exemple.model;

import java.sql.SQLException;
import java.util.List;

public class Test {

    public static void main(String[] args) {


        try {
                // entity
                Client cli  = new Client(0l,"nahid","9009990099");

                //Transacatio
                ClientDAO clidao = new ClientDAO();

                // save trasanction
                clidao.save(cli);


             List<Client> mylist =  clidao.getAll();

            for (Client temp :mylist) {

                System.out.println(temp.toString());

            }






        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
