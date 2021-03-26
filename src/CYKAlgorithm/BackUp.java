package CYKAlgorithm;

import java.util.ArrayList;

public class BackUp {
    private String[][] table;
    private int i = 0;
    private int j = 0;
    private String stringName = null;
    private String stringCut = null;
    private ArrayList<CNF> cnf = null;

    public BackUp(String[][] table, int i, int j, String stringName, String stringCut,ArrayList<CNF> cnf){
        this.table = new String[table.length][table[0].length];
        this.i = i;
        this.j = j;
        this.stringName = stringName;
        this.stringCut = stringCut;
        this.cnf = cnf;
        for (int x = 0; x < table.length; x++){
            for (int y = 0; y < table[0].length; y++){
                this.table[x][y] = table[x][y];
            }
        }
        this.table[i][j] = "--> "+table[i][j]+" <--";
    }

    public String[][] getTable() {
        return table;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public String getInfo(int ii , int jj) {
        String text_return = "";
        if (ii <= stringName.length() && jj <= stringName.length() && !table[ii][jj].equals("") && !table[ii][jj].equals("-")) {
            String text = "";
            String cut = "";
            if(ii==0){ // first line
                for(int k=0;k<cnf.size();k++){ // count rule
                    for(int l=0;l<cnf.get(k).getRuleRight().size();l++){ //length rule
                        if(cnf.get(k).getRuleRight().get(l).equals(String.valueOf(stringName.charAt(jj)))){ // put result
                            text = text + stringName.substring(jj,jj+1)+" Have "+ stringName.substring(jj,jj+1) +" is "+cnf.get(k).getRuleLeft()+"  \n";
                            break;
                        }
                    }
                }

            }else{ // other line

                int i1 = 0;
                int j1 = jj;
                int i2 = ii-1;
                int j2 = jj+1;
                System.out.println("i1 = "+i1+"   j1 = "+j1+"   i2 = "+i2+"   j2 = "+j2);

                for(int k=0;k<ii;k++){
                    cut = stringName.substring(j1,j2+1);
                    i1++;
                    i2--;
                    j2++;
                }
                System.out.println(cut);
                i1 = 0;
                j1 = jj;
                i2 = ii-1;
                j2 = jj+1;

                for(int k=0;k<ii;k++){
                    String a = table[i1][j1];
                    String b = table[i2][j2];
                    String test = "";
                    ArrayList<String> c = new ArrayList<>();
                    ArrayList<String> d = new ArrayList<>();
                    //System.out.println(a+"     "+b);
                    for(int l=0;l<a.length();l++){// print rule
                        c.add(String.valueOf(a.charAt(l)));
                    }
                    for(int l=0;l<b.length();l++){
                        d.add(String.valueOf(b.charAt(l)));
                    }


                    for(int l=0;l<a.length();l++){
                        Boolean check = false;
                        for(int m=0;m<b.length();m++){
                            test = c.get(l)+d.get(m); // ต่อ
                            //System.out.println(test);
                            for(int n=0;n<cnf.size();n++){ //check
                                for(int o=0;o<cnf.get(n).getRuleRight().size();o++){
                                    if(cnf.get(n).getRuleRight().get(o).equals(test)){
                                        text = text+cut.substring(0, k + 1) + "   " + cut.substring(k + 1, cut.length())+" Have "+ test +" is "+cnf.get(n).getRuleLeft()+"  \n";
                                        check = true;
                                        break; //
                                    }
                                }
                            }if(check == false){
                                text = text+cut.substring(0, k + 1) + "   " + cut.substring(k + 1, cut.length())+" Have "+ test +" is -"+" \n";
                            }
                        }
                    }

                    i1++; //row box 1
                    i2--; //row box 2
                    j2++; //col box 2
                }
            }
            //System.out.println(text);
            text_return = text;
        }
        return text_return;
    }
}
