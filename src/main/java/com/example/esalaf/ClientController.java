package com.example.esalaf;

import com.exemple.model.Client;
import com.exemple.model.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TextField nom;

    @FXML
    private TextField tele;

    @FXML
    private TableView<Client> mytab;

    @FXML
    private TableColumn<Client, Long> col_id;

    @FXML
    private TableColumn<Client, String> col_nom;

    @FXML
    private TableColumn<Client, String> col_tele;

    @FXML
    private TableColumn<Client, Void> col_actions;
    @FXML
    private TableColumn<Client, Double> col_addcredit;


    @FXML
    protected void onSaveButtonClick() {
        Client cli = new Client( nom.getText(), tele.getText());

        try {
            ClientDAO clidao = new ClientDAO();

            clidao.save(cli);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create the edit and delete buttons
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button addCreditButton = new Button("Add Credit");
        addCreditButton.setOnAction(event -> {

        });
        editButton.setOnAction(event -> {
            editClient(cli);
        });

        deleteButton.setOnAction(event -> {
            try {
                ClientDAO clidao = new ClientDAO();
                clidao.delete(cli);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            UpdateTable();
        });

        // Create an HBox to hold the buttons
        HBox buttons = new HBox(editButton, deleteButton, addCreditButton);

        // Add the HBox to the table view
        mytab.getItems().add(cli);
        mytab.setFixedCellSize(25);
        mytab.prefHeightProperty().bind(mytab.fixedCellSizeProperty().multiply(mytab.getItems().size()).add(30));
        mytab.setMinHeight(200);
        mytab.setMaxHeight(400);
        mytab.setPrefWidth(200);

        mytab.setPlaceholder(new Label("No data available"));
        mytab.setEditable(false);

        mytab.setRowFactory(tv -> new TableRow<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        });
    }

    public void UpdateTable() {
        // Set the cell value factories for each column
        col_id.setCellValueFactory(new PropertyValueFactory<Client, Long>("id_client"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Client, String>("telepehone"));

        // Add the column with edit and delete buttons
        col_actions.setCellFactory(
                new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
                    @Override
                    public TableCell<Client, Void> call(TableColumn<Client, Void> column) {
                        return new TableCell<Client, Void>() {
                            private final Button editButton = new Button("Edit");
                            private final Button deleteButton = new Button("Delete");

                            {
                                editButton.setOnAction(event -> {
                                    Client client = getTableView().getItems().get(getIndex());
                                    editClient(client);
                                });


                                deleteButton.setOnAction(event -> {
                                    Client client = getTableView().getItems().get(getIndex());
                                    try {
                                        ClientDAO clidao = new ClientDAO();
                                        clidao.delete(client);
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
        col_addcredit.setCellFactory(new Callback<TableColumn<Client, Double>, TableCell<Client, Double>>() {
            @Override
            public TableCell<Client, Double> call(TableColumn<Client, Double> column) {
                return new TableCell<Client, Double>() {
                    private final Button addCreditButton = new Button("Add Credit");

                    {
                        addCreditButton.setOnAction(event -> {
                            Client client = getTableView().getItems().get(getIndex());

                            // Load the FXML file for the new interface
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCredit.fxml"));
                            System.out.println(getClass().getResource("addCredit.fxml"));

                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            // Get the controller associated with the new FXML file
                            addCredit controller = loader.getController();
                            Client selectedClient = (Client) mytab.getSelectionModel().getSelectedItem();
                            // Set the selected client data as properties of the controller
                            controller.setSelecetdClientName(client.getNom(), client.getTelepehone(),client.getId_client());

                            // Create a new stage to display the new interface
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Add Credit");

                            // Show the new interface
                            stage.show();
                        });
                    }
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(addCreditButton);
                        }
                    }
                };
            }
        });
        // Set the items in the table
        mytab.setItems(getDataClients());
    }
    private void editClient(Client client) {
        // Create a new dialog box to edit the client details
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Edit Client");

        // Set the button types for the dialog box
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a form with text fields for the client details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nom = new TextField(client.getNom());
        TextField tele = new TextField(client.getTelepehone());

        gridPane.add(new Label("Nom:"), 0, 0);
        gridPane.add(nom, 1, 0);
        gridPane.add(new Label("Téléphone:"), 0, 1);
        gridPane.add(tele, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Set the result converter for the dialog box
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Client(client.getId_client(), nom.getText(), tele.getText());
            }
            return null;
        });

        // Show the dialog box and wait for the user to close it
        Optional<Client> result = dialog.showAndWait();

        // If the user clicked save, update the client details in the database and refresh the table
        result.ifPresent(updatedClient -> {
            try {
                ClientDAO clidao = new ClientDAO();
                clidao.update(updatedClient);
                UpdateTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static ObservableList<Client> getDataClients() {

        ClientDAO clidao = null;

        ObservableList<Client> listfx = FXCollections.observableArrayList();

        try {
            clidao = new ClientDAO();
            for (Client ettemp : clidao.getAll())
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add the column for edit and delete buttons
        col_actions.setCellValueFactory(new PropertyValueFactory<>(""));

        UpdateTable();
    }
}
