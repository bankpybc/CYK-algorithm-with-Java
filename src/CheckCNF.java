package sample;

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


public class CheckCNF {



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

    public static String randomString(ArrayList a,int stringLenght){
        String convertToString = "";
        for (int i = 0 ;i<a.size() ;i++){    /// Convert from arraylist to String
            convertToString += a.get(i).toString();
        }
        System.out.println("String from rule : " + convertToString);
        Random rnd = new Random();
        String newString = ""; /// เก็บสายอักขระใหม่ ที่มีความยาวเท่ากับทstringLenghtตามพารามืเตอร์รับเข้ามา
        for (int i=1 ;i<= stringLenght ;i++){
            char random  = convertToString.charAt(rnd.nextInt(convertToString.length())); // random
            newString += random;
            System.out.print(i + " : " + random + ", "); /// ปริ้นสตริงแต่ละรอบที่สุ่มได้
        }
        System.out.println("\nNewString : "  + newString); /// ปริ้นสายอักขระใหม่ที่สุ่มได้ โดยความยาวของสายอักขระมีค่าเท่ากับค่าที่เราอินพุต
        return newString;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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

        SaveAndOpen saveOpen = new SaveAndOpen();
        ArrayList<String> Rule = saveOpen.openRule("C:\\Dontri File\\Works(Dontri)\\Dontri\\2-2563\\Theory of computation\\CYK\\src\\sample\\test.txt");
        for (int count = 0; ;count++ ) {

            String rule = Rule.get(count);
            rule = rule.trim();
            if (rule.equals("0")) {
                break;
            } else {
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
            int stringLenght = sc.nextInt();
            for(int i=0;i<leftHandRule.size();i++){
                ArrayList<String> temp = new ArrayList<>();
                    String test = "";
                    for (int k=0;k<rightHandRule.get(i).length;k++){
                        temp.add(rightHandRule.get(i)[k]);
                    }
                cnf.add(new CNF('S',leftHandRule.get(i),temp));
            }
            //CYK cyk = new CYK(cnf,randomString(string,stringLenght));
            CYK cyk = new CYK(cnf,"aaabbbcc");

            cyk.cykAlgorithm();

        }else {
            System.out.println("ส่งค่าไม่ได้");
        }
    }
}

/*
S -> AB
A -> CD | CF
B -> c | EB
C -> a
D -> b
E -> c
F -> AD
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