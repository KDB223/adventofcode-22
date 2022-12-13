package day12;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day12 {
    static class Pos {
        int x;
        int y;
        int steps;

        public Pos(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }
    }

    private static final List<String> input = new ArrayList<>();
    private static char[][] grid;
    private static int startX, startY;

    public static void main(String[] args) {
        consumeInput();
        buildGrid();
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private static void buildGrid() {
        grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            int sIndex = input.get(i).indexOf('S');
            if (sIndex != -1) {
                startY = sIndex;
                startX = i;
            }
            grid[i] = input.get(i).toCharArray();
        }
    }

    private static int partOne() {
        return bfs(new Pos(startX, startY, 0));
    }

    private static int bfs(Pos startPoint) {
        Queue<Pos> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        q.offer(startPoint);
        visited.add(startPoint.toString());

        while (!q.isEmpty()) {
            Pos pos = q.poll();

            char curr = grid[pos.x][pos.y];
            if (curr == 'S') curr = 'a';
            if (curr == 'E') return pos.steps;

            for (int[] dir : dirs) {
                int nextX = pos.x + dir[0];
                int nextY = pos.y + dir[1];
                if (!isValid(nextX, nextY) || visited.contains(nextX + ":" + nextY))
                    continue;

                char next = grid[nextX][nextY];
                if (next == 'E') next = 'z';

                if (next - curr > 1)
                    continue;

                Pos nextPos = new Pos(nextX, nextY, pos.steps + 1);
                visited.add(nextPos.toString());
                q.offer(nextPos);
            }
        }
        return Integer.MAX_VALUE;
    }

    private static int dfs(int x, int y, int steps, Set<String> visited) {
        char curr = grid[x][y];
        if (curr == 'E') return steps;

        if (curr == 'S') curr = 'a';
        int min = Integer.MAX_VALUE;
        for (int[] dir : dirs) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (!isValid(nextX, nextY) || visited.contains(nextX + ":" + nextY))
                continue;

            char next = grid[nextX][nextY];
            if (next == 'E') next = 'z';
            if (next - curr > 1)
                continue;

            visited.add(nextX + ":" + nextY);
            min = Math.min(min, dfs(nextX, nextY, steps + 1, visited));
            visited.remove(nextX + ":" + nextY);
        }
        return min;
    }

    private static boolean isValid(int x, int y) {
        return (x < grid.length && y < grid[0].length && x >= 0 && y >= 0);
    }

    static int[][] dirs = new int[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private static int partTwo() {
        Set<Pos> startPoints = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char curr = grid[i][j];
                if (curr == 'S' || curr == 'a')
                    startPoints.add(new Pos(i, j, 0));
            }
        }
        int min = Integer.MAX_VALUE;
        for (Pos startPoint : startPoints) {
            min = Math.min(min, bfs(startPoint));
        }
        return min;
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day12/input.txt"))) {
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
