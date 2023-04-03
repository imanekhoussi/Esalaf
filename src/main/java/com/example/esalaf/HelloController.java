package com.example.esalaf;

import com.exemple.model.Client;
import com.exemple.model.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController  {
/*    @FXML
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
    protected void onSaveButtonClick(){

        Client cli = new Client(0l , nom.getText() , tele.getText());

        try {
            ClientDAO clidao = new ClientDAO();

            clidao.save(cli);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UpdateTable();

    }



    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Client,Long>("id_client"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));

        col_tele.setCellValueFactory(new PropertyValueFactory<Client,String>("telepehone"));


        mytab.setItems(getDataClients());
    }

    public static ObservableList<Client> getDataClients(){

        ClientDAO clidao = null;

        ObservableList<Client> listfx = FXCollections.observableArrayList();

        try {
            clidao = new ClientDAO();
            for(Client ettemp : clidao.getAll())
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
*/
}
