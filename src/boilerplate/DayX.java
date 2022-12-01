package boilerplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DayX {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayx/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // TODO: Process line
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
