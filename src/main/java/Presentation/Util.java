package Presentation;

import javafx.scene.control.Alert;


public class Util {
    /**
     * @param header
     * @param content
     */
    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MPP chat");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
}
