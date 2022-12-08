package boilerplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayX {
    private static final List<String> input = new ArrayList<>();

    public static void main(String[] args) {
        consumeInput();
        partOne();
        partTwo();
        // TODO: Print output
    }

    private static void partOne() {
        // TODO: Process input
    }

    private static void partTwo() {
        // TODO: Process input
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayx/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }
}
