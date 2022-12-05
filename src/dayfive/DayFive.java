package dayfive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class DayFive {
    private static final List<Deque<Character>> stacks = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            stacks.add(new ArrayDeque<>());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayfive/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    buildState(line);
                } else {
                    if (!line.startsWith("m")) continue;
                    //move(line);   // Part One
                    move2(line);    // Part Two
                }
            }
            stacks.forEach(s -> System.out.print(s.peekLast()));
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static void move(String line) {
        String[] splits = line.split(" ");
        int n = Integer.parseInt(splits[1]);
        int src = Integer.parseInt(splits[3]) - 1;
        int dst = Integer.parseInt(splits[5]) - 1;
        while (n-- > 0) {
            char c = stacks.get(src).removeLast();
            stacks.get(dst).offerLast(c);
        }
    }

    private static void move2(String line) {
        String[] splits = line.split(" ");
        int n = Integer.parseInt(splits[1]);
        int src = Integer.parseInt(splits[3]) - 1;
        int dst = Integer.parseInt(splits[5]) - 1;
        List<Character> list = new ArrayList<>();
        while (n-- > 0) {
            char c = stacks.get(src).removeLast();
            list.add(c);
        }
        Collections.reverse(list);
        stacks.get(dst).addAll(list);
    }

    private static void buildState(String line) {
        for (int i = 1; i < line.length(); i += 4) {
            char c = line.charAt(i);
            if (Character.isAlphabetic(c)) {
                stacks.get(i / 4).offerFirst(c);
            }
        }
    }
}
