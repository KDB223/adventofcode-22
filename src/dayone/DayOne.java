package dayone;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOne {
    private static int maxElf = 0;
    private static final List<Integer> calories = new ArrayList<>();
    private static int maxCalories = Integer.MIN_VALUE;
    private static int runningTotal = 0;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayone/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                partOne(line);
            }
            System.out.println("Max calorie elf:" + calories.get(maxElf));

            System.out.println(partTwo());

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static int partTwo() {
        calories.sort(Collections.reverseOrder());
        return calories.get(0) + calories.get(1) + calories.get(2);
    }

    private static void partOne(String line) {
        try {
            int cals = Integer.parseInt(line);
            runningTotal += cals;
        } catch (NumberFormatException e) {
            // Blank line
            //System.out.println(runningTotal);
            calories.add(runningTotal);
            if (runningTotal > maxCalories) {
                maxCalories = runningTotal;
                maxElf = calories.size() - 1;
            }
            runningTotal = 0;
        }
    }
}
