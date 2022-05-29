package Presentation;

import BLL.ClientBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import DataAccess.ProductDAO;
import Model.Client;
import Model.Order;
import Model.Product;
import Presentation.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderController {

    private ProductBLL productBLL;
    private ClientBLL clientBLL;
    private OrderBLL orderBLL;

    @FXML
    TableView<Product> tableViewProducts;

    @FXML
    TableView<Client> tableViewClients;

    @FXML
    TableView<Order> tableViewOrders;

    @FXML
    Button buttonAdd;

    @FXML
    Button buttonDelete;

    @FXML
    TextField textFieldQuantity;


    @FXML
    void initialize() {
        configureProductTable();
        configureClientTable();
        configureOrderTable();
    }

    private void configureProductTable()
    {
        tableViewProducts.getColumns().clear();

        TableColumn<Product, String> idColumn = new TableColumn<>("Id");
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        TableColumn<Product, String> currentStockColumn = new TableColumn<>("CurrentStock");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currentStockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        tableViewProducts.getColumns().add(idColumn);
        tableViewProducts.getColumns().add(nameColumn);
        tableViewProducts.getColumns().add(priceColumn);
        tableViewProducts.getColumns().add(currentStockColumn);
    }

    private void configureClientTable() {
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

    private void configureOrderTable() {
        tableViewOrders.getColumns().clear();

        TableColumn<Order, String> idColumn = new TableColumn<>("Id");
        TableColumn<Order, String> idClientColumn = new TableColumn<>("IdClient");
        TableColumn<Order, String> idProductColumn = new TableColumn<>("IdProduct");
        TableColumn<Order, String> quantityColumn = new TableColumn<>("Quantity");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idClientColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableViewOrders.getColumns().add(idColumn);
        tableViewOrders.getColumns().add(idClientColumn);
        tableViewOrders.getColumns().add(idProductColumn);
        tableViewOrders.getColumns().add(quantityColumn);

    }

    @FXML
    public void handleAdd(){
        if (tableViewClients.getSelectionModel().isEmpty())
        {
            Util.showWarning("Add error", "Select a client from clients table");
        }
        else if (tableViewProducts.getSelectionModel().isEmpty())
        {
            Util.showWarning("Add error", "Select a product from products table");
        }
        else {
            try {
                int idClient = tableViewClients.getSelectionModel().getSelectedItem().getId();
                int idProduct = tableViewProducts.getSelectionModel().getSelectedItem().getId();
                int quantity = Integer.parseInt(textFieldQuantity.getText());
                orderBLL.insertOrder(new Order(idClient, idProduct, quantity));
                setProducts();
                setOrders();
            } catch (Exception ex) {
                Util.showWarning("Add error", ex.getMessage());
            }
        }
    }

    @FXML
    public void handleDelete(){
        if (tableViewOrders.getSelectionModel().isEmpty())
        {
            Util.showWarning("Delete error", "Select a order from orders table!");
        }
        else
        {
            int id = tableViewOrders.getSelectionModel().getSelectedItem().getId();
            Order order = orderBLL.findById(id);
            orderBLL.deleteOrder(order);
            tableViewProducts.getItems().remove(order);
            setOrders();
            setProducts();
        }
    }


    public void setProducts() {

        try {
            var products = productBLL.findAllProduct();
            tableViewProducts.getItems().clear();
            for (Product product : products) {
                tableViewProducts.getItems().add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void setOrders() {

        try {
            var orders = orderBLL.findAllOrders();
            tableViewOrders.getItems().clear();
            if (orders != null)
                for (Order order : orders) {
                    tableViewOrders.getItems().add(order);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setProductBLL(ProductBLL productBLL) {
        this.productBLL = productBLL;
    }

    public void setClientBLL(ClientBLL clientBLL) {
        this.clientBLL = clientBLL;
    }

    public void setOrderBLL(OrderBLL orderBLL) {
        this.orderBLL = orderBLL;
    }
}
