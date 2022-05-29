package Presentation;

import BLL.ProductBLL;
import DataAccess.ProductDAO;
import Model.Product;
import Presentation.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class ProductController {

    private ProductBLL productBLL;

    @FXML
    TableView<Product> tableViewProducts;

    @FXML
    Button buttonAdd;

    @FXML
    Button buttonDelete;

    @FXML
    Button buttonEdit;

    @FXML
    TextField textFieldName;

    @FXML
    TextField textFieldPrice;

    @FXML
    TextField textFieldCurrentStock;


    @FXML
    void initialize() {
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

    @FXML
    public void handleAdd(){
        try
        {
            String name = textFieldName.getText();
            Float price = Float.parseFloat(textFieldPrice.getText());
            int currentStock = Integer.parseInt(textFieldCurrentStock.getText());
            Product product = new Product(name, price, currentStock);
            productBLL.insertProduct(product);
            setProducts();
        }
        catch (Exception ex)
        {
            Util.showWarning("Add error", ex.getMessage());
        }
    }

    @FXML
    public void handleDelete(){
        if (tableViewProducts.getSelectionModel().isEmpty())
        {
            Util.showWarning("Delete error", "Select a product from table!");
        }
        else
        {
            Product product = tableViewProducts.getSelectionModel().getSelectedItem();
            productBLL.deleteProduct(product.getId());
            tableViewProducts.getItems().remove(product);
        }
    }

    @FXML
    public void handleEdit(){
        if (tableViewProducts.getSelectionModel().isEmpty())
        {
            Util.showWarning("Edit error", "Select a product from table!");
        }
        try
        {
            Product prevProduct = tableViewProducts.getSelectionModel().getSelectedItem();
            String name = textFieldName.getText();
            Float price = Float.parseFloat(textFieldPrice.getText());
            int currentStock = Integer.parseInt(textFieldCurrentStock.getText());
            Product product = new Product(name, price, currentStock);
            product.setId(prevProduct.getId());
            productBLL.editProduct(product);
            setProducts();
        }
        catch (Exception ex)
        {
            Util.showWarning("Edit error", ex.getMessage());
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


    public void setProductBLL(ProductBLL productBLL) {
        this.productBLL = productBLL;
    }
}
