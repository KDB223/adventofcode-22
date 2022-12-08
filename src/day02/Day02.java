package day02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Day02 {
    private static int total;
    private static int totalNew;

    private static final Map<String, Integer> scores = Map.of(
        "A", 1,     // rock
        "B", 2,         // paper
        "C", 3,         // scissors
        "X", 1,         // rock
        "Y", 2,         // paper
        "Z", 3              // scissors
    );

    private static final Map<String, Integer> results = Map.of(
        "X", 0,
        "Y", 3,
        "Z", 6
    );

    public static final Map<String, String> mapA = Map.of(
        "X", "Z",
        "Y", "X",
        "Z", "Y"
    );
    public static final Map<String, String> mapB = Map.of(
        "X", "X",
        "Y", "Y",
        "Z", "Z"
    );
    public static final Map<String, String> mapC = Map.of(
        "X", "Y",
        "Y", "Z",
        "Z", "X"
    );

    public static final Map<String, Map<String, String>> moveMap = Map.of(
        "A", mapA,
        "B", mapB,
        "C", mapC
    );

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/daytwo/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splits = line.split(" ");
                partOne(splits[0], splits[1]);
                partTwo(splits[0], splits[1]);
            }
            System.out.println(total);
            System.out.println(totalNew);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static void partTwo(String opp, String res) {
        Map<String, String> moves = moveMap.get(opp);
        totalNew += results.get(res);                       // result
        totalNew += scores.get(moves.get(res));            // move
    }

    private static void partOne(String opp, String pla) {
        int score = scores.get(pla);

        if (pla.equals("X") && opp.equals("C")) {
            score += 6;
        } else if (!(pla.equals("Z") && opp.equals("A"))) {
            int diff = score - scores.get(opp);
            if (diff > 0) {     // Win
                score += 6;
            } else if (diff == 0) {
                score += 3;     // Draw
            }
        }
        total += score;
    }
}
