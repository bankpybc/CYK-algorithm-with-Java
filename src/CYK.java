package sample;

import java.util.ArrayList;

public class CYK {

    private ArrayList<CNF> cnf= null;
    private String stringName = null;

    public CYK(ArrayList<CNF> cnf,String stringName){
        this.cnf = cnf;
        this.stringName = stringName;
    }

    public ArrayList<CNF> getCnf() {
        return cnf;
    }

    public String getStringName() {
        return stringName;
    }

    public void cykAlgorithm(){
        System.out.println(stringName.length());
        String[][] cykTable = new String[stringName.length()][stringName.length()];

        for(int j=0;j<stringName.length();j++){
            for(int k=0;k<stringName.length();k++){
                cykTable[j][k] = "";
            }
        }

        int checknum = stringName.length()-1;
        int i1 = -1,i2 = -1,j1 = -1,j2 = -1;
        ArrayList<BackUp> backUptable = new ArrayList<>();
        int count = 0;

        for(int i=0;i<stringName.length();i++){
            for (int j=0;j<stringName.length();j++){
                cykTable[i][j] = "";


                if(j <= checknum){
                    if(i==0){
                        //System.out.println(stringName.charAt(j));
                        for(int k=0;k<cnf.size();k++){
                            for(int l=0;l<cnf.get(k).getRuleRight().size();l++){
                                if(cnf.get(k).getRuleRight().get(l).equals(String.valueOf(stringName.charAt(j)))){
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
                                                    cykTable[i][j] = cykTable[i][j]+cnf.get(n).getRuleLeft().toString();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            i1++;
                            i2--;
                            j2++;
                        }

                    }


                    backUptable.add(new BackUp(cykTable));
                    count++;


                }else{
                    cykTable[i][j] = "-";
                }



            }
            checknum--;
        }

        System.out.println("/////////////////"+count);




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

}
