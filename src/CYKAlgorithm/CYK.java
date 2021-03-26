package CYKAlgorithm;

import GUI.*;
import com.sun.javafx.scene.control.skin.TableViewSkin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class CYK {

    private static int row_cell;
    private static int col_cell;
    private ArrayList<CNF> cnf= null;
    private static String stringName = null;
    public static int page = 0;
    private static String[][] cykTableCopy; // booktall edit
    public static ArrayList<BackUp> backUptable;
    private static String[] char_split;

    public CYK(ArrayList<CNF> cnf,String stringName){
        this.cnf = cnf;
        this.stringName = stringName;
    }

    public String[][] getcykTableCopy(){ return  cykTableCopy;}

    public ArrayList<CNF> getCnf() {
        return cnf;
    }

    public String getStringName() {
        return stringName;
    }

    private static Method columnToFitMethod;

    static {
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnToFitMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void cykAlgorithm(ScrollPane scrollpane, String[] character_split){
        System.out.println(stringName.length());
        String[][] cykTable = new String[stringName.length()][stringName.length()];

        int checknum0 = stringName.length()-1;
        for(int j=0;j<stringName.length();j++){
            for(int k=0;k<stringName.length();k++){
                if(k<= checknum0){
                    cykTable[j][k] = "";
                }else{
                    cykTable[j][k] = "-";
                }

            }
            checknum0--;
        }

        int checknum = stringName.length()-1;
        int i1 = -1,i2 = -1,j1 = -1,j2 = -1;
        //ArrayList<BackUp> backUptable = new ArrayList<>();
        int count = 0;

        for(int i=0;i<stringName.length();i++){
            for (int j=0;j<stringName.length();j++){
                boolean check = false;
                String stringCut = "";
                cykTable[i][j] = "";


                if(j <= checknum){
                    if(i==0){
                        //System.out.println(stringName.charAt(j));
                        for(int k=0;k<cnf.size();k++){
                            for(int l=0;l<cnf.get(k).getRuleRight().size();l++){
                                if(cnf.get(k).getRuleRight().get(l).equals(String.valueOf(stringName.charAt(j)))){
                                    stringCut = stringName.substring(j,j+1);
                                    cykTable[i][j] = cykTable[i][j]+cnf.get(k).getRuleLeft().toString();
                                    break;
                                }
                            }
                        }
                    }else{
                        System.out.println("////////////"+i);
                        i1 = 0;
                        j1 = j;
                        i2 = i-1;
                        j2 = j+1;
                        for(int k=0;k<i;k++){
                            System.out.println("i1 = "+i1+"   j1 = "+j1+"   i2 = "+i2+"   j2 = "+j2);
                            if(!cykTable[i1][j1].equals("") && !cykTable[i2][j2].equals("")){
                                String a = cykTable[i1][j1];
                                String b = cykTable[i2][j2];
                                String test = "";
                                ArrayList<String> c = new ArrayList<>();
                                ArrayList<String> d = new ArrayList<>();
                                System.out.println(a+"     "+b);
                                for(int l=0;l<a.length();l++){
                                    c.add(String.valueOf(a.charAt(l)));
                                }
                                for(int l=0;l<b.length();l++){
                                    d.add(String.valueOf(b.charAt(l)));
                                }
                                for(int l=0;l<a.length();l++){
                                    for(int m=0;m<b.length();m++){
                                        test = c.get(l)+d.get(m);
                                        for(int n=0;n<cnf.size();n++){
                                            for(int o=0;o<cnf.get(n).getRuleRight().size();o++){
                                                if(cnf.get(n).getRuleRight().get(o).equals(test)){
                                                    if(cykTable[i][j].equals("")){
                                                        cykTable[i][j] = cykTable[i][j]+cnf.get(n).getRuleLeft().toString();
                                                        check = true;
                                                    }else{
                                                        cykTable[i][j] = cykTable[i][j]+cnf.get(n).getRuleLeft().toString();
                                                        check = true;
                                                    }
                                                }
                                            }
                                        }if(check==false){
                                            cykTable[i][j] = "";
                                        }
                                    }
                                    stringCut = stringName.substring(j1,j2+1);
                                }
                            }
                            i1++;
                            i2--;
                            j2++;
                        }

                    }


                    System.out.println(stringCut);

                    System.out.println(i+"//////////"+j);
                    backUptable.add(new BackUp(cykTable,i,j,stringName,stringCut,cnf));
                    count++;


                }else{
                    cykTable[i][j] = "-";
                }



            }
            checknum--;
        }

        //System.out.println("/////////////////"+count);
        cykTableCopy = cykTable; /// booktall edit




        for(int i=0;i<backUptable.size();i++){
            for(int j=0;j<stringName.length();j++){
                for(int k=0;k<stringName.length();k++){
                    if(backUptable.get(i).getTable()[j][k].equals("")){
                        backUptable.get(i).getTable()[j][k] = "-";
                    }
                    System.out.print(backUptable.get(i).getTable()[j][k]+" ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }

        //backUptable.get(30).getInfo(4,1);

        char_split = character_split;
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(backUptable.get(0).getTable()));
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < character_split.length; i++) {
            TableColumn tc = new TableColumn(character_split[i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);

            table.getColumns().add(tc);
        }
        /*table.setRowFactory( tv -> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    String[] rowData = row.getItem();
                    System.out.println(rowData);
                }
            });
            return row ;
        });*/
        table.setItems(data);
        table.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(Change<?> c) {
                for (Object column : table.getColumns()) {
                    try {
                        columnToFitMethod.invoke(table.getSkin(), column, -1);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        table.getSelectionModel().setCellSelectionEnabled(true);
        /*ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells() ;

        selectedCells.addListener((ListChangeListener.Change<? extends TablePosition> change) -> {
            if (selectedCells.size() > 0) {
                TablePosition selectedCell = selectedCells.get(0);
                TableColumn column = selectedCell.getTableColumn();
                int rowIndex = selectedCell.getRow();
                Object dt = column.getCellObservableValue(rowIndex).getValue();
                System.out.println(dt);
            }
        });*/

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    try {
                        row_cell = table.getSelectionModel().getSelectedCells().get(0).getRow();
                        col_cell = table.getSelectionModel().getSelectedCells().get(0).getColumn();
                        Stage st = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/GUI-DetailCell.fxml"));
                        st.setTitle("Detail cell");
                        Scene scene = new Scene(root);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //table.setStyle("-fx-alignment: CENTER;");
        scrollpane.setContent(table);




        for(int i=0;i<stringName.length();i++){
            for (int j=0;j<stringName.length();j++){
                if(cykTable[i][j].equals("")){
                    cykTable[i][j] = "-";
                }
                System.out.print(cykTable[i][j]+" ");
            }
            System.out.println();
        }


    }

    public static int getRow() {
        return row_cell;
    }

    public static int getCol() {
        return col_cell;
    }

    public static void nextPage(ScrollPane scrollpane, Button next_button, Button back_button, Button finish_button) {
        page++;
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(backUptable.get(page).getTable()));
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < char_split.length; i++) {
            TableColumn tc = new TableColumn(char_split[i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);

            table.getColumns().add(tc);
        }
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setItems(data);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    try {
                        row_cell = table.getSelectionModel().getSelectedCells().get(0).getRow();
                        col_cell = table.getSelectionModel().getSelectedCells().get(0).getColumn();
                        Stage st = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/GUI-DetailCell.fxml"));
                        st.setTitle("Detail cell");
                        Scene scene = new Scene(root);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        scrollpane.setContent(table);

        if(page == backUptable.size()-1){
            next_button.setDisable(true);
            finish_button.setDisable(true);
        }

        if(page != 0) {
            finish_button.setDisable(false);
            back_button.setDisable(false);
        }

        if(page == backUptable.size()-1 && backUptable.get(page).getTable()[char_split.length-1][0].contains(CheckCNF.startVariable.trim())) {
            finish_button.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Is string Accept?");
            alert.setHeaderText("CNF accept this string.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if(page == backUptable.size()-1 && !backUptable.get(page).getTable()[char_split.length-1][0].contains(CheckCNF.startVariable.trim())){
            finish_button.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Is string Accept?");
            alert.setHeaderText("CNF Not accept this string.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    public static void backPage(ScrollPane scrollpane, Button next_button, Button back_button, Button finish_button) {
        page--;
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(backUptable.get(page).getTable()));
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < char_split.length; i++) {
            TableColumn tc = new TableColumn(char_split[i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);

            table.getColumns().add(tc);
        }
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setItems(data);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    try {
                        row_cell = table.getSelectionModel().getSelectedCells().get(0).getRow();
                        col_cell = table.getSelectionModel().getSelectedCells().get(0).getColumn();
                        Stage st = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/GUI-DetailCell.fxml"));
                        st.setTitle("Detail cell");
                        Scene scene = new Scene(root);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        scrollpane.setContent(table);

        if(page != 0){
            next_button.setDisable(false);
            finish_button.setDisable(false);
        }

        if(page == 0) {
            back_button.setDisable(true);
            finish_button.setDisable(false);
        }
    }

    public static void finishPage(ScrollPane scrollpane, Button next_button, Button back_button, Button finish_button) {
        page = backUptable.size()-1;
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(backUptable.get(page).getTable()));
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < char_split.length; i++) {
            TableColumn tc = new TableColumn(char_split[i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.setItems(data);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    try {
                        row_cell = table.getSelectionModel().getSelectedCells().get(0).getRow();
                        col_cell = table.getSelectionModel().getSelectedCells().get(0).getColumn();
                        Stage st = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/GUI-DetailCell.fxml"));
                        st.setTitle("Detail cell");
                        Scene scene = new Scene(root);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        scrollpane.setContent(table);

        back_button.setDisable(false);
        next_button.setDisable(true);
        finish_button.setDisable(true);
        if(backUptable.get(page).getTable()[char_split.length-1][0].contains(CheckCNF.startVariable.trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Is string Accept?");
            alert.setHeaderText("CNF accept this string.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Is string Accept?");
            alert.setHeaderText("CNF Not accept this string.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    public void cykAlgorithmRandom(){
        //System.out.println(stringName.length());
        String[][] cykTable = new String[stringName.length()][stringName.length()];
        int checknum0 = stringName.length()-1;
        for(int j=0;j<stringName.length();j++){
            for(int k=0;k<stringName.length();k++){
                if(k<= checknum0){
                    cykTable[j][k] = "";
                }else{
                    cykTable[j][k] = "-";
                }

            }
            checknum0--;
        }

        int checknum = stringName.length()-1;
        int i1 = -1,i2 = -1,j1 = -1,j2 = -1;
        backUptable = new ArrayList<>();
        int count = 0;

        for(int i=0;i<stringName.length();i++){
            for (int j=0;j<stringName.length();j++){
                boolean check = false;
                String stringCut = "";
                cykTable[i][j] = "";
                if(j <= checknum){
                    if(i==0){
                        //System.out.println(stringName.charAt(j));
                        for(int k=0;k<cnf.size();k++){
                            for(int l=0;l<cnf.get(k).getRuleRight().size();l++){
                                if(cnf.get(k).getRuleRight().get(l).equals(String.valueOf(stringName.charAt(j)))){
                                    stringCut = stringName.substring(j,j+1);
                                    cykTable[i][j] = cykTable[i][j]+cnf.get(k).getRuleLeft().toString();
                                    break;
                                }
                            }
                        }
                    }else{
                        //System.out.println("////////////"+i);
                        i1 = 0;
                        j1 = j;
                        i2 = i-1;
                        j2 = j+1;

                        for(int k=0;k<i;k++){
                            //System.out.println("i1 = "+i1+"   j1 = "+j1+"   i2 = "+i2+"   j2 = "+j2);
                            if(!cykTable[i1][j1].equals("") && !cykTable[i2][j2].equals("")){
                                String a = cykTable[i1][j1];
                                String b = cykTable[i2][j2];
                                String test = "";
                                ArrayList<String> c = new ArrayList<>();
                                ArrayList<String> d = new ArrayList<>();
                                //System.out.println(a+"     "+b);
                                for(int l=0;l<a.length();l++){
                                    c.add(String.valueOf(a.charAt(l)));
                                }
                                for(int l=0;l<b.length();l++){
                                    d.add(String.valueOf(b.charAt(l)));
                                }
                                for(int l=0;l<a.length();l++){
                                    for(int m=0;m<b.length();m++){
                                        test = c.get(l)+d.get(m);
                                        for(int n=0;n<cnf.size();n++){
                                            for(int o=0;o<cnf.get(n).getRuleRight().size();o++){
                                                if(cnf.get(n).getRuleRight().get(o).equals(test)){
                                                    if(cykTable[i][j].equals("")){
                                                        cykTable[i][j] = cykTable[i][j]+cnf.get(n).getRuleLeft().toString();
                                                        check = true;
                                                    }else{
                                                        cykTable[i][j] = cykTable[i][j]+cnf.get(n).getRuleLeft().toString();
                                                        check = true;
                                                    }
                                                }
                                            }
                                        }if(check==false){
                                            cykTable[i][j] = "";
                                        }
                                    }
                                    stringCut = stringName.substring(j1,j2+1);
                                }
                            }
                            i1++;
                            i2--;
                            j2++;
                        }

                    }

                    /*for(int aa=0;aa<stringName.length();aa++){
                        for (int bb=0;bb <stringName.length();bb++){
                            if(cykTable[aa][bb].equals("")){
                                cykTable[aa][bb] = "";
                            }
                            System.out.print(cykTable[aa][bb]+" ");
                        }
                        System.out.println();
                    }*/

                    count++;


                }else{
                    cykTable[i][j] = "-";
                }
            }
            checknum--;
        }
        //System.out.println("/////////////////"+count);
        cykTableCopy = cykTable; /// booktall edit


        /*for(int i=0;i<stringName.length();i++){
            for (int j=0;j<stringName.length();j++){
                if(cykTable[i][j].equals("")){
                    cykTable[i][j] = "";
                }
                System.out.print(cykTable[i][j]+" ");
            }
            System.out.println();
        }*/

    }

    public static boolean acceptStringRandom(String startVarible){ //booktall 24/03/2021
        int row = stringName.length();
        boolean status = false;
        System.out.println("StartVaribel : " + startVarible);
        System.out.println("Final state : " + cykTableCopy[row-1][0]);
        if (cykTableCopy[row-1][0].contains(startVarible.trim())){
            status =  true;
        }
        System.out.println("Status : " + status);
        return status;
    }
}
