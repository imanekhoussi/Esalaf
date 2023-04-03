package com.example.esalaf;

import com.exemple.model.Credit;
import com.exemple.model.CreditDAO;
import com.exemple.model.Produit;
import com.exemple.model.ProduitDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
public class addCredit implements Initializable
         {
    @FXML
    private TextField Nom;

    @FXML
    private TextField Tele;

    @FXML
    private Label id_cli;

             @FXML
             private TextField qte;


    private long product_id;
    private long client_id;

             public void setSelecetdClientName(String name, String Tele, Long id) {
                 this.Nom.setText(name);
                 this.Tele.setText(Tele);
                 this.client_id = id;
                 this.id_cli.setText(String.valueOf(id));
             }

             public void setSelectedClientId(long id) {
                 this.client_id = id;
                 this.id_cli.setText(String.valueOf(id));
             }

             @FXML private MenuButton produit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ProduitDAO productDAO = new ProduitDAO();
            List<Produit> productList = productDAO.getAll();
            addProductsToMenu(productList);
        } catch (SQLException ex) {
            // Handle SQL exception
        }
    }

    private void addProductsToMenu(List<Produit> productList) {
        for (Produit product : productList) {
            MenuItem menuItem = new MenuItem(product.getNom());




            // Set the product id as the id property of the MenuItem
            menuItem.setId(String.valueOf(product.getId()));
            menuItem.setOnAction(event -> {
                // Get the selected product name
                String selectedProduct = ((MenuItem) event.getSource()).getText();
                // Set the text of the MenuButton to the selected product name
                produit.setText(selectedProduct);

                // Get the selected product id
                String selectedProductId = ((MenuItem) event.getSource()).getId();
                // Call a method with the selected product id
                handleProductSelection(selectedProductId);
            });
            produit.getItems().add(menuItem);
        }
    }
    private void handleProductSelection(String productId) {
        // Do something with the selected product id
        System.out.println("Selected product id: " + productId);
        long id_prod=Long.parseLong(productId);
        product_id=id_prod;
    }
    public void onSave(){
        Credit cred = new Credit( client_id ,product_id);

        try {
            CreditDAO creditdao = new CreditDAO();

            creditdao.save(cred);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}


