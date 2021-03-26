package CYKAlgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveAndOpen {

    public static void saveRule(ArrayList<String> rule, String path) {

        try {
            Path file = Paths.get(path);
            BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);

            for (int i = 0; i < rule.size(); i++) {
                writer.write(rule.get(i));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public static ArrayList<String> openRule(String path) {
        ArrayList<String> input = new ArrayList<>();
        try {
            Path file = Paths.get(path);
            BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            String line = null;

            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        for (int i = 0; i < input.size(); i++) {
            System.out.println(input.get(i));
        }
        return input;
    }


}
