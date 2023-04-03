package com.example.esalaf;
import com.exemple.model.Produit;
import com.exemple.model.ProduitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class ProduitController implements Initializable{

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;
    @FXML
    private TableView<Produit> prod_tab;

    @FXML
    private TableColumn<Produit, Long> col_id;

    @FXML
    private TableColumn<Produit, String> col_name;

    @FXML
    private TableColumn<Produit, Double> col_price;
    @FXML
    private TableColumn<Produit, Void> col_actions;

    public void UpdateTable() {
        // Set the cell value factories for each column
        col_id.setCellValueFactory(new PropertyValueFactory<Produit, Long>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        col_price.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));

        // Add the column with edit and delete buttons
        col_actions.setCellFactory(
                new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
                    @Override
                    public TableCell<Produit, Void> call(TableColumn<Produit, Void> column) {
                        return new TableCell<Produit, Void>() {
                            private final Button editButton = new Button("Edit");
                            private final Button deleteButton = new Button("Delete");

                            {
                                editButton.setOnAction(event -> {
                                    Produit produit = getTableView().getItems().get(getIndex());
                                    editProduit(produit);
                                });


                                deleteButton.setOnAction(event -> {
                                    Produit produit = getTableView().getItems().get(getIndex());
                                    try {
                                        ProduitDAO produitDAO = new ProduitDAO();
                                        produitDAO.delete(produit);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    UpdateTable();
                                });

                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox buttons = new HBox(editButton, deleteButton);
                                    setGraphic(buttons);
                                }
                            }
                        };
                    }
                }
        );

        // Set the items in the table
        prod_tab.setItems(getDataProducts());
    }
    private void editProduit(Produit produit) {
        // Create a new dialog box to edit the produit details
        Dialog<Produit> dialog = new Dialog<>();
        dialog.setTitle("Edit Produit");

        // Set the button types for the dialog box
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a form with text fields for the produit details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nom = new TextField(produit.getNom());
        TextField prix = new TextField(Double.toString(produit.getPrix()));

        gridPane.add(new Label("Nom:"), 0, 0);
        gridPane.add(nom, 1, 0);
        gridPane.add(new Label("Prix:"), 0, 1);
        gridPane.add(prix, 1, 1);


        dialog.getDialogPane().setContent(gridPane);

        // Set the result converter for the dialog box
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    Double prixValue = Double.parseDouble(prix.getText());
                    return new Produit(produit.getId(), nom.getText(), prixValue);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        // Show the dialog box and wait for the user to close it
        Optional<Produit> result = dialog.showAndWait();

        // If the user clicked save, update the produit details in the database and refresh the table
        result.ifPresent(updatedProduit -> {
            try {
                ProduitDAO proddao = new ProduitDAO();
                proddao.update(updatedProduit);
                UpdateTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @FXML
    protected void onAddButtonClick() {
        Produit prod = new Produit(nom.getText(), Double.parseDouble(prix.getText()));
        prod.setId((long) (prod_tab.getItems().size() + 1)); // set the ID for the new product

        // create the edit and delete buttons for the new row
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        HBox buttons = new HBox(editButton, deleteButton);

        // add event handlers for the edit and delete buttons
        editButton.setOnAction(event -> {
            editProduit(prod);
        });
        deleteButton.setOnAction(event -> {
            try {
                ProduitDAO produitDAO = new ProduitDAO();
                produitDAO.delete(prod);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            UpdateTable();
        });

        // add the new row to the table
        prod_tab.getItems().add(prod);
        prod_tab.refresh();
    }

    public static ObservableList<Produit> getDataProducts(){

        ProduitDAO produitDAO = null;

        ObservableList<Produit> listfx = FXCollections.observableArrayList();

        try {
            produitDAO = new ProduitDAO();
            for(Produit ettemp : produitDAO.getAll())
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
    }
}
