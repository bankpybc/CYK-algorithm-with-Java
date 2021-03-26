package CYKAlgorithm;

import GUI.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sun.text.normalizer.UCharacter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import java.util.Arrays;
import javafx.scene.control.TableColumn.CellDataFeatures;


public class CheckCNF {
    public static String startVariable = "";



    public static boolean checkleftRule(ArrayList<Character> rul) {
        boolean isleftRule = true;
        for (Character element : rul) {
            if (Character.isLowerCase(element.charValue())) {
                isleftRule = false;
                break;
            }

        }
        return isleftRule;
    }


    public static boolean checkRightRule(ArrayList<String[]> rul,ArrayList<Character> string) {
        boolean UpperVar, lowerChar = false;
        boolean checkRuleChar = true;

        for (String[] r : rul) {
            for (String element : r) {
                if (element.length() == 1) {
                    char str = element.charAt(0);
                    lowerChar = Character.isLowerCase(str);
                    //System.out.println("coverToChar : " + element.charAt(0));
                    if (!string.contains(element.charAt(0))){
                        string.add(element.charAt(0));
                    }
                    if (lowerChar == false) {
                        checkRuleChar = false;
                        break;
                    }
                } else if (element.length() == 2) {
                    int count = 0;
                    for (int i = 0; i < element.length(); i++) {
                        char ch = element.charAt(i);
                        if (!Character.isUpperCase(ch)) {
                            checkRuleChar = false;
                            break;
                        }
                    }
                } else {
                    checkRuleChar = false;
                }
            }
        }
        return checkRuleChar;
    }

    public static ArrayList<String> randomString(ArrayList a,ArrayList<CNF> cnf, int stringLenght) {  ///booktall edit 24/03/2021 "ทั้งหมดขอเมธอดนี้"
        String convertToString = "";
        for (int i = 0; i < a.size(); i++) {    /// Convert from arraylist to String
            convertToString += a.get(i).toString();
        }
        System.out.println("String from rule : " + convertToString);
        ArrayList<String> stingFromRule = new ArrayList<>();
        ArrayList<String> stingAcceptCYK = new ArrayList<>();  //edit 24/03/2021
        Random random = new Random();

        for (;;){
            String newString = "";
            if ((stingFromRule.size() == (Math.pow(a.size(),stringLenght)))){
                break;
            }
            for (int i=1 ;i<= stringLenght ;i++){
                char generate  = convertToString.charAt(random.nextInt(convertToString.length())); // random
                newString += generate;
            }
            if (!stingFromRule.contains(newString)){ // String Not Repeat will add to stingFromRule //edit 24/03/2021
                System.out.println("String Not Repeat : " + newString); //edit 24/03/2021
                //System.out.println("Check startVariable : " + startVariable);
                stingFromRule.add(newString); //add to stingFromRule //edit 24/03/2021
                CYK cyk = new CYK(cnf, newString);  //edit 24/03/2021
                cyk.cykAlgorithmRandom(); //edit 24/03/2021
                if (cyk.acceptStringRandom(startVariable)){//edit 24/03/2021
                    stingAcceptCYK.add(newString); //check stingAcceptCYK ,edit 24/03/2021
                }
            }
        }
        System.out.println("[Method Random] String Not Repeat : " + stingFromRule);
        System.out.println("[Method Random] String Accept CYK : " + stingAcceptCYK);
        System.out.println("[Method Random] Size String Accept CYK : " + stingAcceptCYK.size());
        System.out.println("[Method Random] Count Set of String Accept CYK : " + stingFromRule.size());
        return stingAcceptCYK;
    }

    public static void checkRule(ArrayList<String> Rule, String text_string, ScrollPane scrollpane){
        ArrayList<CNF> cnf = new ArrayList<>();
        ArrayList<Character> leftHandRule = new ArrayList<>();
        ArrayList<String[]> rightHandRule = new ArrayList<>();
        ArrayList<Character> errorRule = new ArrayList<>();

        ArrayList<Character> string = new ArrayList<>();
        boolean FoundBlankInTheleftRule = false;
        boolean FoundBlankInTheRightRule = false;
        boolean FoundRuleLeftHasMulti = false;
        boolean FoundSpecialCharacterLeftRule = false;
        boolean trueleftRule = false;
        boolean trueRightRule = false;

        for (int count = 0;count<Rule.size();count++ ) {

            String rule = Rule.get(count);
            rule = rule.trim();

                String[] mssplit = rule.split("->");
                for (int i = 0; i < rule.length(); i++) {
                    if (i == 0) {
                        char Ru = rule.charAt(0);
                        leftHandRule.add(Ru);  // กฏตัวเเรก
                    }
                }
                if (mssplit.length == 1) {
                    String ch = (String) mssplit[0];
                    FoundBlankInTheRightRule = true;
                    errorRule.add(ch.charAt(0));
                } else {
                    if (mssplit[0].length() == 0) {
                        //System.out.println("\nERROR : Found blank in the rule");
                        FoundBlankInTheleftRule = true;
                        break;
                    }
                    if (mssplit[0].trim().length() > 1) {  /// booktall edit Check lenght > 1  AA -> AB
                        FoundRuleLeftHasMulti = true;
                        //System.out.println("\nERROR : Found LeftRule have lenght > 1");
                        break;
                    }
                    if (count == 0) {
                        startVariable = mssplit[0]; ///booktall edit 24/03/2021 *********
                    }
                    String convertToString = mssplit[0].toString();
                    int character = convertToString.charAt(0);
                    if (character >= 65 && character <= 90 || character == 48) {
                        String RightRule = (String) mssplit[1];
                        StringBuilder renew = new StringBuilder(RightRule);
                        for (int i = 0; i < renew.length(); i++) {
                            if (renew.charAt(i) == (char) 124) {
                                renew.setCharAt(i, ',');
                            }
                        }
                        //System.out.print(mssplit[0] + " " + mssplit[1]);
                        //System.out.println();

                        String[] RightRuleSplit = renew.toString().split(",");
                        String box[] = new String[RightRuleSplit.length];
                        for (int i = 0; i < RightRuleSplit.length; i++) {
                            box[i] = RightRuleSplit[i].trim();
                        }
                        rightHandRule.add(box);
                    } else {
                        FoundSpecialCharacterLeftRule = true;
                        //System.out.println("\nERROR : Found special Character");
                        break;
                    }
                }
            }

        System.out.println();
        System.out.println("ERROR : Found blank in the left rule : " + FoundBlankInTheleftRule);
        System.out.println("ERROR : Found special Character in left rule: " + FoundSpecialCharacterLeftRule);
        System.out.println("ERROR : Found LeftRule have multi var : " + FoundRuleLeftHasMulti);
        if (FoundBlankInTheleftRule || FoundSpecialCharacterLeftRule || FoundRuleLeftHasMulti) {
            System.out.println("Check Rule left : " + !checkleftRule(leftHandRule));
        } else {
            System.out.println("Check Rule left : " + checkleftRule(leftHandRule));
            trueleftRule=true;

        }
        System.out.println();
        System.out.println("ERROR : Found blank in the right rule : " + FoundBlankInTheRightRule);
        System.out.println("ERROR : Found special Character in Right rule: " + !checkRightRule(rightHandRule,string)); //ใช้เเทนการเช็คอักขระพิเศษทางขวา
        if (FoundBlankInTheRightRule || !checkRightRule(rightHandRule,string)) {
            System.out.println("Check Rule Right : " + checkRightRule(rightHandRule,string));
        }else {
            System.out.println("Check Rule Right : " + checkRightRule(rightHandRule,string));
            trueRightRule=true;
        }

        if(trueleftRule==true && trueRightRule==true){
            System.out.println(string);
            System.out.println("ส่งค่าได้");
            System.out.print("กรุณากรอกความยาวของสายอักขระ");
            for(int i=0;i<leftHandRule.size();i++){
                ArrayList<String> temp = new ArrayList<>();
                    String test = "";
                    for (int k=0;k<rightHandRule.get(i).length;k++){
                        temp.add(rightHandRule.get(i)[k]);
                    }
                cnf.add(new CNF(startVariable.charAt(0),leftHandRule.get(i),temp));
            }
           // CYK cyk = new CYK(cnf,randomString(string,stringLenght));
            CYK cyk = new CYK(cnf,text_string);

            String[] character_split = text_string.split("");
            cyk.cykAlgorithm(scrollpane, character_split);

        }else {
            System.out.println("ส่งค่าไม่ได้");
        }
    }

    public static void checkRule(ArrayList<String> Rule, int number_random){
        ArrayList<CNF> cnf = new ArrayList<>();
        ArrayList<Character> leftHandRule = new ArrayList<>();
        ArrayList<String[]> rightHandRule = new ArrayList<>();
        ArrayList<Character> errorRule = new ArrayList<>();

        ArrayList<Character> string = new ArrayList<>();
        boolean FoundBlankInTheleftRule = false;
        boolean FoundBlankInTheRightRule = false;
        boolean FoundRuleLeftHasMulti = false;
        boolean FoundSpecialCharacterLeftRule = false;
        boolean trueleftRule = false;
        boolean trueRightRule = false;

        for (int count = 0;count<Rule.size();count++ ) {

            String rule = Rule.get(count);
            rule = rule.trim();

            String[] mssplit = rule.split("->");
            for (int i = 0; i < rule.length(); i++) {
                if (i == 0) {
                    char Ru = rule.charAt(0);
                    leftHandRule.add(Ru);  // กฏตัวเเรก
                }
            }
            if (mssplit.length == 1) {
                String ch = (String) mssplit[0];
                FoundBlankInTheRightRule = true;
                errorRule.add(ch.charAt(0));
            } else {
                if (mssplit[0].length() == 0) {
                    //System.out.println("\nERROR : Found blank in the rule");
                    FoundBlankInTheleftRule = true;
                    break;
                }
                if (mssplit[0].trim().length() > 1) {  /// booktall edit Check lenght > 1  AA -> AB
                    FoundRuleLeftHasMulti = true;
                    //System.out.println("\nERROR : Found LeftRule have lenght > 1");
                    break;
                }

                if (count == 0) {
                    startVariable = mssplit[0]; ///booktall edit 24/03/2021 *********
                }
                String convertToString = mssplit[0].toString();
                int character = convertToString.charAt(0);
                if (character >= 65 && character <= 90 || character == 48) {
                    String RightRule = (String) mssplit[1];
                    StringBuilder renew = new StringBuilder(RightRule);
                    for (int i = 0; i < renew.length(); i++) {
                        if (renew.charAt(i) == (char) 124) {
                            renew.setCharAt(i, ',');
                        }
                    }
                    //System.out.print(mssplit[0] + " " + mssplit[1]);
                    //System.out.println();

                    String[] RightRuleSplit = renew.toString().split(",");
                    String box[] = new String[RightRuleSplit.length];
                    for (int i = 0; i < RightRuleSplit.length; i++) {
                        box[i] = RightRuleSplit[i].trim();
                    }
                    rightHandRule.add(box);
                } else {
                    FoundSpecialCharacterLeftRule = true;
                    //System.out.println("\nERROR : Found special Character");
                    break;
                }
            }
        }

        System.out.println();
        System.out.println("ERROR : Found blank in the left rule : " + FoundBlankInTheleftRule);
        System.out.println("ERROR : Found special Character in left rule: " + FoundSpecialCharacterLeftRule);
        System.out.println("ERROR : Found LeftRule have multi var : " + FoundRuleLeftHasMulti);
        if (FoundBlankInTheleftRule || FoundSpecialCharacterLeftRule || FoundRuleLeftHasMulti) {
            System.out.println("Check Rule left : " + !checkleftRule(leftHandRule));
        } else {
            System.out.println("Check Rule left : " + checkleftRule(leftHandRule));
            trueleftRule=true;

        }
        System.out.println();
        System.out.println("ERROR : Found blank in the right rule : " + FoundBlankInTheRightRule);
        System.out.println("ERROR : Found special Character in Right rule: " + !checkRightRule(rightHandRule,string)); //ใช้เเทนการเช็คอักขระพิเศษทางขวา
        if (FoundBlankInTheRightRule || !checkRightRule(rightHandRule,string)) {
            System.out.println("Check Rule Right : " + checkRightRule(rightHandRule,string));
        }else {
            System.out.println("Check Rule Right : " + checkRightRule(rightHandRule,string));
            trueRightRule=true;
        }

        if(trueleftRule==true && trueRightRule==true){
            System.out.println(string);
            System.out.println("ส่งค่าได้");
            System.out.print("กรุณากรอกความยาวของสายอักขระ");
            for(int i=0;i<leftHandRule.size();i++){
                ArrayList<String> temp = new ArrayList<>();
                String test = "";
                for (int k=0;k<rightHandRule.get(i).length;k++){
                    temp.add(rightHandRule.get(i)[k]);
                }
                cnf.add(new CNF(startVariable.charAt(0),leftHandRule.get(i),temp));
            }
            // CYK cyk = new CYK(cnf,randomString(string,stringLenght));
            int stringLenght = number_random; /// ปกติต้อง input เข้ามาตรงนี้เลย fix ไว้ก่อนว่า ขนาดตัวอักษรเท่ากับ 5 ,///booktall edit 24/03/2021 *************************
            ArrayList<String> stringRandomAccept = new ArrayList<>();    ///booktall edit 24/03/2021
            stringRandomAccept = randomString(string,cnf,stringLenght);
            Main.rand = "";
            for(String s : stringRandomAccept){
                Main.rand = Main.rand + s +"\n";
            }
            //System.out.println("String Accept CYK : " + Main.rand); ///booktall edit 24/03/2021


        }else {
            System.out.println("ส่งค่าไม่ได้");
        }
    }
}

/*
c
0
 */

/*
S -> AB
A -> CD | @@
B -> c | b
C -> a
D -> b
E -> c
F -> AD
0
 */