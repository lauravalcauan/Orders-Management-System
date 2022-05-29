package Start;

import BLL.ClientBLL;
import BLL.EmployeeBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import Presentation.LoginController;
import Presentation.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;


public class Start extends Application {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());


    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loginLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Parent root= loginLoader.load();

        LoginController controller = loginLoader.getController();
        controller.setEmployeeBLL(new EmployeeBLL());
        controller.setClientBLL(new ClientBLL());
        controller.setProductBLL(new ProductBLL());
        controller.setOrderBLL(new OrderBLL());

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

