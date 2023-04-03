package com.example.esalaf;

import com.exemple.model.Credit;
import com.exemple.model.CreditDAO;
import com.exemple.model.ClientDAO;
import com.exemple.model.ProduitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.exemple.model.CreditWithNames;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class CreditsController implements Initializable {


    @FXML
    private TableView<CreditWithNames> creditsTable;


    @FXML
    private TableColumn<CreditWithNames, String> clientNameColumn;

    @FXML
    private TableColumn<CreditWithNames, String> productNameColumn;

    @FXML
    private TableColumn<CreditWithNames, Integer> creditAmountColumn;

    @FXML
    private TableColumn<CreditWithNames, LocalDate> creditDateColumn;
    @FXML
    private TableColumn<CreditWithNames, Void> actionsColumn;



    public CreditsController() throws SQLException {
    }

    public String getProductName(Long id) {
        ProduitDAO produitDAO;
        try {
            produitDAO = new ProduitDAO();
            return produitDAO.getProductName(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClientName(Long id) {
        ClientDAO clientDAO;
        try {
            clientDAO = new ClientDAO();
            return clientDAO.getClientName(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    ClientDAO clientDAO = new ClientDAO();
    ProduitDAO produitDAO = new ProduitDAO();


    public void updateTable() {
        try {
            // Update client name column
            clientNameColumn.setCellValueFactory(cellData -> {
                CreditWithNames credit = cellData.getValue();
                String clientName = credit.getClientName();
                return new SimpleStringProperty(String.valueOf(clientName));
            });

            // Update product name column
            productNameColumn.setCellValueFactory(cellData -> {
                CreditWithNames credit = cellData.getValue();
                String productName = credit.getProductName();
                return new SimpleStringProperty(String.valueOf(productName));
            });

            // Update credit amount and date columns
            creditAmountColumn.setCellValueFactory(new PropertyValueFactory<CreditWithNames, Integer>("qte"));
            creditDateColumn.setCellValueFactory(new PropertyValueFactory<CreditWithNames, LocalDate>("date"));
            actionsColumn.setCellFactory(new Callback<TableColumn<CreditWithNames, Void>, TableCell<CreditWithNames, Void>>() {
                @Override
                public TableCell<CreditWithNames, Void> call(TableColumn<CreditWithNames, Void> param) {
                    return new TableCell<CreditWithNames, Void>() {
                        private final Button editButton = new Button("Edit");
                        private final Button deleteButton = new Button("Delete");

                        {
                            // Add action event handlers for the edit and delete buttons
                            editButton.setOnAction(event -> {
                                CreditWithNames credit = getTableView().getItems().get(getIndex());
                                // Handle edit action here
                            });

                            deleteButton.setOnAction(event -> {
                                Credit credit = getTableView().getItems().get(getIndex());
                                try {
                                    // Get the credit_id of the selected Credit object
                                    Long creditId = credit.getCredit_id();
                                    // Delete the credit with the given credit_id
                                    if (creditId != null) { // add null check here
                                        // Delete the credit with the given credit_id
                                        CreditDAO creditDAO = new CreditDAO();
                                        System.out.println("hy");
                                        creditDAO.delete(creditId);

                                        // Update the table view
                                        updateTable();
                                    } else {
                                        System.out.println("Credit ID is null"); // add error handling for null credit_id value
                                    }} catch (SQLException e) {
                                    // Handle exception
                                    System.err.println("Error deleting credit: " + e.getMessage());
                                    // Display error message to user
                                }
                            });

                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                // Add edit and delete buttons to cell
                                HBox hbox = new HBox(10);
                                hbox.getChildren().addAll(editButton, deleteButton);
                                setGraphic(hbox);
                            }
                        }
                    };
                }
            });
            // Set table data
            creditsTable.setItems(getCreditData());
        } catch (NullPointerException e) {
            // Handle exception
            System.err.println("Error updating table: " + e.getMessage());
            // Display error message to user
            // You could use an Alert dialog or some other means of displaying the error message
        }
    }

    public static ObservableList<CreditWithNames> getCreditData() {
        CreditDAO creditDAO = null;
        ObservableList<CreditWithNames> credits = FXCollections.observableArrayList();
        try {
            creditDAO = new CreditDAO();
            for (CreditWithNames credit : creditDAO.getAllWithNames()) {
                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return credits;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
    }
}