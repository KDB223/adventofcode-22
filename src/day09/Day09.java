package day09;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day09 {
    static class Knot {
        int x;
        int y;

        public Knot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final List<String> input = new ArrayList<>();
    private static List<Knot> knots;
    private static int hx = 0, hy = 0;
    private static int tx = 0, ty = 0;

    public static void main(String[] args) {
        consumeInput();
        System.out.println(solve(2));
        System.out.println(solve(10));
    }

    private static int solve(int n) {
        knots = new ArrayList<>();
        IntStream.range(0, n)
            .forEach(i -> knots.add(new Knot(0, 0)));

        Set<String> positions = new HashSet<>();
        positions.add("0:0");


        for (String line : input) {
            String[] splits = line.split(" ");
            char dir = splits[0].charAt(0);
            int num = Integer.parseInt(splits[1]);

            while (num-- > 0) {
                switch (dir) {
                    case 'R' -> moveRight(knots.get(0), knots.get(1));
                    case 'D' -> moveDown(knots.get(0), knots.get(1));
                    case 'L' -> moveLeft(knots.get(0), knots.get(1));
                    case 'U' -> moveUp(knots.get(0), knots.get(1));
                }
                for (int i = 2; i < n; i++) {
                    follow(knots.get(i - 1), knots.get(i));
                }
                positions.add(knots.get(n - 1).x + ":" + knots.get(n - 1).y);
            }
        }
        return positions.size();
    }

    private static void follow(Knot head, Knot tail) {
        if (head.x > tail.x + 1) {
            // Head moved rightwards
            tail.x++;
            if (head.y > tail.y) {
                // Head moved diagonally upwards
                tail.y++;
            } else if (head.y < tail.y) {
                // Head moved diagonally downwards
                tail.y--;
            }
        } else if (head.y < tail.y - 1) {
            // Head moved downwards
            tail.y--;
            if (head.x > tail.x) {
                // Head moved diagonally rightwards
                tail.x++;
            } else if (head.x < tail.x) {
                // Head moved diagonally leftwards
                tail.x--;
            }
        } else if (head.x < tail.x - 1) {
            // Head moved leftwards
            tail.x--;
            if (head.y > tail.y) {
                // Head moved diagonally upwards
                tail.y++;
            } else if (head.y < tail.y) {
                // Head moved diagonally downwards
                tail.y--;
            }
        } else if (head.y > tail.y + 1) {
            // Head moved upwards
            tail.y++;
            if (head.x > tail.x) {
                // Head moved diagonally rightwards
                tail.x++;
            } else if (head.x < tail.x) {
                // Head moved diagonally leftwards
                tail.x--;
            }
        }
    }

    private static void moveRight(Knot head, Knot tail) {
        if (head.x > tail.x) {
            // tail needs to move to old head
            tail.x = head.x;
            tail.y = head.y;
        }
        head.x++;
    }

    private static void moveUp(Knot head, Knot tail) {
        if (head.y > tail.y) {
            // tail needs to move to old head
            tail.x = head.x;
            tail.y = head.y;
        }
        head.y++;

    }

    private static void moveLeft(Knot head, Knot tail) {
        if (head.x < tail.x) {
            // tail needs to move to old head
            tail.x = head.x;
            tail.y = head.y;
        }
        head.x--;

    }

    private static void moveDown(Knot head, Knot tail) {
        if (head.y < tail.y) {
            // tail needs to move to old head
            tail.x = head.x;
            tail.y = head.y;
        }
        head.y--;
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day09/input.txt"))) {
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
