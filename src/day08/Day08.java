package day08;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day08 {
    private static final List<String> input = new ArrayList<>();
    private static int[][] grid;

    public static void main(String[] args) {
        consumeInput();
        createGrid();
        System.out.println((partOne()));
        System.out.println((partTwo()));
    }

    private static void createGrid() {
        grid = new int[input.size()][input.get(0).length()];
        int x = 0;
        int y = 0;

        for (String line : input) {
            for (char c : line.toCharArray()) {
                int num = Integer.parseInt(c + "");
                grid[x][y++] = num;
            }
            x++;
            y = 0;
        }
    }

    private static int partOne() {
        int count = input.size() * 2 + (input.get(0).length() - 2) * 2;

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (check(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean check(int[][] grid, int x, int y) {
        // up
        boolean top = true;
        Integer prev = null;
        for (int i = x; i >= 0; i--) {
            if (prev == null) {
                prev = grid[i][y];
                continue;
            }
            if (prev == grid[x][y] && grid[i][y] >= prev || grid[i][y] > prev) {
                top = false;
            }
        }

        // right
        boolean right = true;
        prev = null;
        for (int j = y; j < grid[0].length; j++) {
            if (prev == null) {
                prev = grid[x][j];
                continue;
            }
            if (prev == grid[x][y] && grid[x][j] >= prev || grid[x][j] > prev) {
                right = false;
            }
        }

        // down
        boolean down = true;
        prev = null;
        for (int i = x; i < grid.length; i++) {
            if (prev == null) {
                prev = grid[i][y];
                continue;
            }
            if (prev == grid[x][y] && grid[i][y] >= prev || grid[i][y] > prev) {
                down = false;
            }
        }

        // left
        boolean left = true;
        prev = null;
        for (int j = y; j >= 0; j--) {
            if (prev == null) {
                prev = grid[x][j];
                continue;
            }
            if (prev == grid[x][y] && grid[x][j] >= prev || grid[x][j] > prev) {
                left = false;
            }
        }
        return top || right || down || left;
    }

    private static int partTwo() {
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                int score = score(grid, i, j);
                max = Math.max(score, max);
            }
        }
        return max;
    }

    private static int score(int[][] grid, int x, int y) {
        int curr = grid[x][y];
        int top = 0;
        for (int i = x - 1; i >= 0; i--) {
            top++;
            if (grid[i][y] >= curr) {
                break;
            }
        }

        // right
        int right = 0;
        for (int j = y + 1; j < grid[0].length; j++) {
            right++;
            if (grid[x][j] >= curr) {
                break;
            }
        }

        // down
        int down = 0;
        for (int i = x + 1; i < grid.length; i++) {
            down++;
            if (grid[i][y] >= curr) {
                break;
            }
        }

        // left
        int left = 0;
        for (int j = y - 1; j >= 0; j--) {
            left++;
            if (grid[x][j] >= curr) {
                break;
            }
        }

        return top * left * right * down;
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day08/input.txt"))) {
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
