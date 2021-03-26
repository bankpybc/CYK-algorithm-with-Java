package GUI;

import CYKAlgorithm.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RandomController implements Initializable {

    @FXML
    private TextArea string_random;

    @FXML
    private TextField number;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void random_press(ActionEvent event) {
        if(isNumeric(number.getText()) && Integer.parseInt(number.getText()) <= 15 && Integer.parseInt(number.getText()) > 0){
            CheckCNF.checkRule(Main.Rule, Integer.parseInt(number.getText()));
            string_random.setText(Main.rand);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please specify less than 15 and more than 0");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
