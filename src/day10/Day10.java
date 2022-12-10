package day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Day10 {
    private static final List<String> input = new ArrayList<>();

    public static void main(String[] args) {
        consumeInput();
        System.out.println(partOne());
        partTwo();
    }

    private static int partOne() {
        Set<Integer> CYCLES = Set.of(20, 60, 100, 140, 180, 220);

        int total = 0;
        int cycle = 1;
        int x = 1;

        for (String line : input) {
            String[] splits = line.split(" ");
            String cmd = splits[0];

            if (CYCLES.contains(cycle)) {
                total += x * cycle;
            }

            if (cmd.equals("noop")) {
                cycle++;
            } else {
                cycle++;
                if (CYCLES.contains(cycle)) {
                    total += x * cycle;
                }
                cycle++;
                int val = Integer.parseInt(splits[1]);
                x += val;
            }
        }

        if (CYCLES.contains(cycle)) {
            total += x * cycle;
        }

        return total;
    }

    private static void partTwo() {
        int x = 1;
        char[][] grid = new char[6][40];
        for (int i = 0; i < 6; i++) {
            Arrays.fill(grid[i], '.');
        }
        int i = 0;
        int cycle = 1;

        draw(grid, cycle, x);

        while (i < input.size()) {
            String line = input.get(i++);
            String[] splits = line.split(" ");
            String cmd = splits[0];

            if (cmd.equals("noop")) {
                cycle++;
                draw(grid, cycle, x);
            } else {
                cycle++;
                draw(grid, cycle, x);

                int val = Integer.parseInt(splits[1]);
                x += val;

                cycle++;
                draw(grid, cycle, x);
            }
        }

        for (char[] row : grid) {
            for (int j = 0; j < 40; j++) {
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }

    private static void draw(char[][] grid, int cycle, int x) {
        cycle--;
        int row = cycle / 40;
        int col = cycle % 40;

        if (x == col || x - 1 == col || x + 1 == col)
            grid[row][col] = '#';
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day10/input.txt"))) {
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
