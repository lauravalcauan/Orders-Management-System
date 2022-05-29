package Presentation;

import BLL.ClientBLL;
import BLL.EmployeeBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import Model.Employee;
import Presentation.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    private EmployeeBLL employeeBLL;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    @FXML
    Button buttonConnect;

    @FXML
    void initialize()
    {
    }

    /**
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void handleConnect(ActionEvent actionEvent) throws Exception {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        Employee employee = employeeBLL.findEmployee(username);

        if (employee != null)
        {
            if (!Objects.equals(employee.getPassword(), password))
            {
                Util.showWarning("Authentication failure", "Incorrect password!");
            }
            else {
                FXMLLoader main = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root = main.load();

                MainController controller = main.getController();
                controller.setClientBLL(clientBLL);
                controller.setProductBLL(productBLL);
                controller.setOrderBLL(orderBLL);

                Stage stage = new Stage();
                stage.setTitle("Application");
                stage.setScene(new Scene(root));
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        else
        {
            Util.showWarning("Authentication failure", "Nonexistent employee!");
        }
    }

    public void setEmployeeBLL(EmployeeBLL employeeBLL) {
        this.employeeBLL = employeeBLL;
    }

    public void setClientBLL(ClientBLL clientBLL) {
        this.clientBLL = clientBLL;
    }

    public void setProductBLL(ProductBLL productBLL) {
        this.productBLL = productBLL;
    }

    public void setOrderBLL(OrderBLL orderBLL) {
        this.orderBLL = orderBLL;
    }
}
