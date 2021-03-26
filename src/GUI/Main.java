package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI-main.fxml"));
        primaryStage.setTitle("CYK Algorithm");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("GUI/Style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static String rand = "";

    public static ArrayList<String> Rule;

    public void openHelp(){
        getHostServices().showDocument("https://drive.google.com/file/d/18T2TVVKJRUrJIYTm3UfXYYW3aszS12si/view?usp=sharing");
    }

    public void aboutPro(){
        getHostServices().showDocument("https://drive.google.com/file/d/1g13yZC8c47b3-xyjv3pXA1rSaD0OwEJE/view?usp=sharing");
    }
}
