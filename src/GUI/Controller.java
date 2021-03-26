package GUI;

import CYKAlgorithm.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private String path = null;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Text number_page;

    @FXML
    private Button next_button;

    @FXML
    private Button back_button;

    @FXML
    private Button finish_button;

    @FXML
    private TextArea input_cnf;

    @FXML
    private TextField input_string;

    @FXML
    private void closeButtonEvent(ActionEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        next_button.setDisable(true);
        back_button.setDisable(true);
        finish_button.setDisable(true);
        scrollpane.setDisable(true);
    }

    @FXML
    public void solve_press(ActionEvent event) {
        if(input_cnf.getText().equals(null) || input_cnf.getText().equals("") || input_string.getText().equals(null) || input_string.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Please insert all field");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            CYK.page = 0;
            next_button.setDisable(false);
            finish_button.setDisable(false);
            scrollpane.setDisable(false);
            scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            CYK.backUptable = new ArrayList<>();
            ArrayList<String> rule = new ArrayList<>();
            String[] split_rule = input_cnf.getText().split("\n");
            for (String r : split_rule){
                rule.add(r);
            }
            CheckCNF.checkRule(rule, input_string.getText(), scrollpane);
            number_page.setText((CYK.page+1) + "/" + CYK.backUptable.size());
        }
    }


    @FXML
    public void new_rule(ActionEvent event) {
        input_cnf.setText("S ->");
        path = null;
        CYK.page = 0;
    }

    @FXML
    public void save(ActionEvent event) {
        if (path != null){
            File file_exists = new File(path);
            if(file_exists.exists()){
                file_exists.delete();
            }
            ArrayList<String> rule = new ArrayList<>();
            for (String input : input_cnf.getText().split("\n")){
                rule.add(input);
            }
            SaveAndOpen.saveRule(rule, path);
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as File...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File directory = fileChooser.showSaveDialog(null);
            ArrayList<String> rule = new ArrayList<>();
            if (directory != null){
                for (String input : input_cnf.getText().split("\n")){
                    rule.add(input);
                }
                SaveAndOpen.saveRule(rule, directory.getPath());
            }
            path = directory.getPath();
        }
    }

    @FXML
    public void save_as(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as File...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File directory = fileChooser.showSaveDialog(null);
        ArrayList<String> rule = new ArrayList<>();
        if (directory != null){
            for (String input : input_cnf.getText().split("\n")){
                rule.add(input);
            }
            SaveAndOpen.saveRule(rule, directory.getPath());
        }
        path = directory.getPath();

    }

    /*private void save_option(){
        if (path != null){
            File file_exists = new File(path);
            if(file_exists.exists()){
                file_exists.delete();
            }
            ArrayList<String> rule = new ArrayList<>();
                for (String input : input_cnf.getText().split("\n")){
                    rule.add(input);
                }
                SaveAndOpen.saveRule(rule, path);
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as File...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File directory = fileChooser.showSaveDialog(null);
            ArrayList<String> rule = new ArrayList<>();
            if (directory != null){
                for (String input : input_cnf.getText().split("\n")){
                    rule.add(input);
                }
                SaveAndOpen.saveRule(rule, directory.getPath());
            }
            path = directory.getPath();
        }
    }*/

    @FXML
    public void open(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String rule = "";
            for(String input : SaveAndOpen.openRule(selectedFile.getPath())){
                rule = rule + "" + input +"\n";
            }
            input_cnf.setText(rule);
            path = selectedFile.getPath();
        }
    }

    @FXML
    public void next_table(ActionEvent event){
        CYK.nextPage(scrollpane, next_button, back_button, finish_button);
        number_page.setText((CYK.page+1) + "/" + CYK.backUptable.size());
    }

    @FXML
    public void back_table(ActionEvent event){
        CYK.backPage(scrollpane, next_button, back_button, finish_button);
        number_page.setText((CYK.page+1) + "/" + CYK.backUptable.size());
    }

    @FXML
    public void finish_table(ActionEvent event) {
        CYK.finishPage(scrollpane, next_button, back_button, finish_button);
        number_page.setText((CYK.page+1) + "/" + CYK.backUptable.size());
    }

    @FXML
    public void random_press(ActionEvent event) throws IOException {
        if(input_cnf.getText().equals(null) || input_cnf.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Please insert CNF field");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
                ArrayList<String> rule = new ArrayList<>();
                String[] split_rule = input_cnf.getText().split("\n");
                for (String r : split_rule){
                    rule.add(r);
                }
                Main.Rule = rule;

                Stage st = new Stage();
                Parent rt = FXMLLoader.load(getClass().getResource("GUI-random.fxml"));
                st.setTitle("Random String");
                Scene scene = new Scene(rt);
                st.setScene(scene);
                st.setResizable(false);
                st.show();
        }
    }

    @FXML
    public void help(ActionEvent event) {
        Main help_cyk = new Main();
        help_cyk.openHelp();
    }

    @FXML
    public void about(ActionEvent event) {
        Main about_cyk = new Main();
        about_cyk.aboutPro();
    }
}
