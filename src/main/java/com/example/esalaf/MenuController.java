package com.example.esalaf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;





    public class MenuController {

        @FXML
        private Label menuLabel;

        @FXML
        private MenuBar menuBar;

        @FXML
        private Menu clientsMenu;

        @FXML
        private MenuItem viewClientsMenuItem;

        @FXML
        private Menu productsMenu;

        @FXML
        private MenuItem viewProductsMenuItem;

        @FXML
        private Menu creditsMenu;

        @FXML
        private MenuItem viewCreditsMenuItem;

        @FXML
        void viewClients(ActionEvent event)throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clients.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

        @FXML
        void viewProducts(ActionEvent event)  throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("produits.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

        @FXML
        void viewCredits(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("credits.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

