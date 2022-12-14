package day14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day14 {
    enum Type { WALL, SAND }
    static class Particle {
        Type type;
        int x;
        int y;

        public Particle(Type type, int x, int y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Particle)) {
                return false;
            }
            Particle particle = (Particle) o;
            return x == particle.x && y == particle.y && type == particle.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, x, y);
        }
    }

    private static final List<String> input = new ArrayList<>();
    private static Set<Particle> grid;
    private static final int SAND_SOURCE_X = 500;
    private static final int SAND_SOURCE_Y = 0;
    private static int MAX_Y = 0;

    public static void main(String[] args) {
        consumeInput();
        buildWalls();
        int partOne = partOne();
        System.out.println(partOne);
        System.out.println(partOne + partTwo());
    }

    private static int partOne() {
        int sands = 0;
        while (simulateSand(SAND_SOURCE_X, SAND_SOURCE_Y)) {
            sands++;
        }
        return sands;
    }

    private static int partTwo() {
        // build floor
        buildWall("0," + (MAX_Y + 2), "1000," + (MAX_Y + 2));

        int sands = 0;
        while (simulateSand(SAND_SOURCE_X, SAND_SOURCE_Y)) {
            sands++;
        }
        return sands + 1;
    }

    private static void buildWalls() {
        grid = new HashSet<>();
        for (String line : input) {
            String[] coords = line.split(" -> ");
            for (int i = 0; i < coords.length - 1; i++) {
                buildWall(coords[i], coords[i + 1]);
            }
        }
    }

    // Returns true if sand particle came to rest
    private static boolean simulateSand(int x, int y) {
        int nextY = findNextY(x, y);
        if (nextY > MAX_Y + 2) {
            // falling forever
            return false;
        }

        // Blocked by either wall or sand
        int nextX = findNextX(x, nextY);
        if (nextX != x) {
            // Simulate from new position again
            return simulateSand(nextX, nextY + 1);
        }

        // Sand has settled
        grid.add(new Particle(Type.SAND, nextX, nextY));

        // Return true if not settled at source
        return nextX != SAND_SOURCE_X || nextY != SAND_SOURCE_Y;
    }

    private static int findNextX(int sandX, int sandY) {
        // Try to move diagonally
        int diagY = sandY + 1;
        int leftDiagX = sandX - 1;
        int rightDiagX = sandX + 1;

        Particle sandLeft = new Particle(Type.SAND, leftDiagX, diagY);
        Particle wallLeft = new Particle(Type.WALL, leftDiagX, diagY);
        boolean blockedLeft = grid.contains(sandLeft) || grid.contains(wallLeft);

        Particle sandRight = new Particle(Type.SAND, rightDiagX, diagY);
        Particle wallRight = new Particle(Type.WALL, rightDiagX, diagY);
        boolean blockedRight = grid.contains(sandRight) || grid.contains(wallRight);

        if (blockedLeft && blockedRight) {
            // Settle
            return sandX;
        }
        if (blockedLeft) {
            return rightDiagX;
        }
        return leftDiagX;
    }

    private static int findNextY(int sandX, int sandY) {
        // Check straight down
        for (int i = sandY + 1; i <= MAX_Y + 2; i++) {
            Particle sand = new Particle(Type.SAND, sandX, i);
            Particle wall = new Particle(Type.WALL, sandX, i);
            boolean blocked = (grid.contains(sand) || grid.contains(wall));
            if (blocked) {
                return i - 1;
            }
        }
        return MAX_Y + 3;
    }

    private static void buildWall(String src, String dst) {
        String[] srcSplits = src.split(",");
        String[] dstSplits = dst.split(",");
        int srcX = Integer.parseInt(srcSplits[0]);
        int srcY = Integer.parseInt(srcSplits[1]);
        int dstX = Integer.parseInt(dstSplits[0]);
        int dstY = Integer.parseInt(dstSplits[1]);
        MAX_Y = Math.max(MAX_Y, srcY);
        MAX_Y = Math.max(MAX_Y, dstY);

        if (srcX > dstX) {
            int temp = dstX;
            dstX = srcX;
            srcX = temp;
        }

        if (srcY > dstY) {
            int temp = dstY;
            dstY = srcY;
            srcY = temp;
        }

        if (srcY == dstY) {
            // Horizontal wall
            for (int i = srcX; i <= dstX; i++) {
                grid.add(new Particle(Type.WALL, i, dstY));
            }
        } else if (srcX == dstX) {
            // Vertical wall
            for (int i = srcY; i <= dstY; i++) {
                grid.add(new Particle(Type.WALL, srcX, i));
            }
        }
    }


    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day14/input.txt"))) {
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
