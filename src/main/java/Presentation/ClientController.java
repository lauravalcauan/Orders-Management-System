package Presentation;

import BLL.ClientBLL;
import DataAccess.ClientDAO;
import Model.Client;
import Presentation.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    private ClientBLL clientBLL;

    @FXML
    TableView<Client> tableViewClients;

    @FXML
    Button buttonAdd;

    @FXML
    Button buttonDelete;

    @FXML
    Button buttonEdit;

    @FXML
    TextField textFieldName;

    @FXML
    TextField textFieldAddress;

    @FXML
    TextField textFieldEmail;

    @FXML
    TextField textFieldAge;


    @FXML
    void initialize() {
        tableViewClients.getColumns().clear();

        TableColumn<Client, String> idColumn = new TableColumn<>("Id");
        TableColumn<Client, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Client, String> addressColumn = new TableColumn<>("Address");
        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Client, String> ageColumn = new TableColumn<>("Age");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewClients.getColumns().add(idColumn);
        tableViewClients.getColumns().add(nameColumn);
        tableViewClients.getColumns().add(addressColumn);
        tableViewClients.getColumns().add(emailColumn);
        tableViewClients.getColumns().add(ageColumn);

    }

    @FXML
    public void handleAdd(){
        try
        {
            String name = textFieldName.getText();
            String address = textFieldAddress.getText();
            String email = textFieldEmail.getText();
            int age = Integer.parseInt(textFieldAge.getText());
            Client client = new Client(name, address, email, age);
            clientBLL.insertClient(client);
            setClients();
        }
        catch (Exception ex)
        {
            Util.showWarning("Add error", ex.getMessage());
        }
    }

    @FXML
    public void handleDelete(){
        if (tableViewClients.getSelectionModel().isEmpty())
        {
            Util.showWarning("Delete error", "Select a client from table!");
        }
        else
        {
            Client client = tableViewClients.getSelectionModel().getSelectedItem();
            clientBLL.deleteClient(client.getId());
            tableViewClients.getItems().remove(client);
        }
    }

    @FXML
    public void handleEdit(){
        if (tableViewClients.getSelectionModel().isEmpty())
        {
            Util.showWarning("Edit error", "Select a client from table!");
        }
        try
        {
            Client prevClient = tableViewClients.getSelectionModel().getSelectedItem();
            String name = textFieldName.getText();
            String address = textFieldAddress.getText();
            String email = textFieldEmail.getText();
            int age = Integer.parseInt(textFieldAge.getText());
            Client client = new Client(name, address, email, age);
            client.setId(prevClient.getId());
            clientBLL.editClient(client);
            setClients();
        }
        catch (Exception ex)
        {
            Util.showWarning("Update error", ex.getMessage());
        }
    }

    public void setClients() {

        try {
            var clients = clientBLL.findAllClient();
            tableViewClients.getItems().clear();
            for (Client client : clients) {
                tableViewClients.getItems().add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setClientBLL(ClientBLL clientBLL) {
        this.clientBLL = clientBLL;
    }
}
