package sample;

public class BackUp {
    private String[][] table;

    public BackUp(String[][] table){
        this.table = new String[table.length][table[0].length];

        for (int x = 0; x < table.length; x++)
            for (int y = 0; y < table[0].length; y++)
                this.table[x][y] = table[x][y];
    }

    public String[][] getTable() {
        return table;
    }
}
