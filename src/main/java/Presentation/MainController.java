package Presentation;

import BLL.ClientBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections;
import java.lang.reflect.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

import javax.swing.*;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;

public class MainController {

    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    private ObservableList<ObservableList> data;

    /**
     * @throws Exception
     */
//    @FXML
//    void handleManageClients() throws Exception {
//        FXMLLoader clientsView = new FXMLLoader(getClass().getClassLoader().getResource("client.fxml"));
//        Parent root = clientsView.load();
//
//        ClientController controller = clientsView.getController();
//        controller.setClientBLL(clientBLL);
//        controller.setClients();
//
//
//        Stage stage = new Stage();
//        stage.setTitle("Manage clients");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//    /**
//     * @throws IOException
//     */
//    @FXML
//    void handleManageProducts() throws IOException {
//        FXMLLoader productsView = new FXMLLoader(getClass().getClassLoader().getResource("product.fxml"));
//        Parent root = productsView.load();
//
//        ProductController controller = productsView.getController();
//        controller.setProductBLL(productBLL);
//        controller.setProducts();
//
//
//        Stage stage = new Stage();
//        stage.setTitle("Manage products");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//    /**
//     * @throws IOException
//     */
//    @FXML
//    void handleManageOrders() throws IOException {
//        FXMLLoader ordersView = new FXMLLoader(getClass().getClassLoader().getResource("order.fxml"));
//        Parent root = ordersView.load();
//
//        OrderController controller = ordersView.getController();
//        controller.setClientBLL(clientBLL);
//        controller.setProductBLL(productBLL);
//        controller.setOrderBLL(orderBLL);
//        controller.setClients();
//        controller.setProducts();
//        controller.setOrders();
//
//
//        Stage stage = new Stage();
//        stage.setTitle("Manage orders");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }

   void handleTable(int tableType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from");
        if(tableType == 0) {
            sb.append(clientBLL);
        }
        else if(tableType == 1) {
            sb.append(productBLL);
        }
        else if(tableType == 2) {
            sb.append(orderBLL);
        }
//        FXMLLoader selectedView = new FXMLLoader(getClass().getClassLoader().getResource(sb));
//        Parent root = sb.load();
//
//        Stage stage = new Stage();
//        stage.setTitle(sb);
//        Stage.setScene(new Scene(root));
//        Stage.show();

        data = FXCollections.observableArrayList();
        try{
            Connection con = ConnectionFactory.getConnection();
            ResultSet rs = con.createStatement().executeQuery(String.valueOf(sb));

            for(int i = 0; i< rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
                System.out.println("Column["+i+"]");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tableView.setItems(data);


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    }
//    public void setClientBLL(ClientBLL clientBLL) {
//        this.clientBLL = clientBLL;
//    }
//
//    public void setProductBLL(ProductBLL productBLL) {
//        this.productBLL = productBLL;
//    }
//
//    public void setOrderBLL(OrderBLL orderBLL) {
//        this.orderBLL = orderBLL;
//    }




