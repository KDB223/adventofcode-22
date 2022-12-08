package day04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day04 {

    private static int total;
    private static int overlaps;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayfour/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                partOne(line);
                partTwo(line);
            }
            System.out.println(total);
            System.out.println(overlaps);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static void partOne(String line) {
        String[] splits = line.split(",");
        String splitsA = splits[0];
        String splitsB = splits[1];
        int aStart = Integer.parseInt(splitsA.split("-")[0]);
        int aEnd = Integer.parseInt(splitsA.split("-")[1]);
        int bStart = Integer.parseInt(splitsB.split("-")[0]);
        int bEnd = Integer.parseInt(splitsB.split("-")[1]);

        Set<Integer> setA = new HashSet<>();
        for (int i = aStart; i <= aEnd; i++) {
            setA.add(i);
        }
        Set<Integer> setB = new HashSet<>();
        for (int i = bStart; i <= bEnd; i++) {
            setB.add(i);
        }

        if (setA.size() < setB.size()) {
             for (int a : setA) {
                 if (!setB.contains(a)) {
                     return;
                 }
             }
        } else {
            for (int b : setB) {
                if (!setA.contains(b)) {
                    return;
                }
            }
        }

        total++;
    }

    private static void partTwo(String line) {
        String[] splits = line.split(",");
        String splitsA = splits[0];
        String splitsB = splits[1];
        int aStart = Integer.parseInt(splitsA.split("-")[0]);
        int aEnd = Integer.parseInt(splitsA.split("-")[1]);
        int bStart = Integer.parseInt(splitsB.split("-")[0]);
        int bEnd = Integer.parseInt(splitsB.split("-")[1]);

        Set<Integer> setA = new HashSet<>();
        for (int i = aStart; i <= aEnd; i++) {
            setA.add(i);
        }
        for (int i = bStart; i <= bEnd; i++) {
            if (setA.contains(i)) {
                overlaps++;
                return;
            }
        }
    }

}
