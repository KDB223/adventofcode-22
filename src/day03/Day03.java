package day03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day03 {
    private static int total;
    private static final List<char[]> list = new ArrayList<>();
    private static int totalNew;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/daythree/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                partOme(line.toCharArray());
                partTwo(line.toCharArray());
            }
            process();
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

    private static void partTwo(char[] sack) {
        // if (list.size() == 3)
            process();

        if (list.size() < 3) {
            list.add(sack);
        }
    }

    private static void process() {
        if (Day03.list.size() != 3) return;
        Map<Character, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < Day03.list.size(); i++) {
            char[] listSack = Day03.list.get(i);
            for (char c : listSack) {
                Set<Integer> sackSet = map.getOrDefault(c, new HashSet<>());
                sackSet.add(i);
                map.put(c, sackSet);
            }
        }
        System.out.println(map);
        for (char key : map.keySet()) {
            if (map.get(key).size() == 3) {
                if (Character.isUpperCase(key)) {
                    totalNew += key - 'A' + 27;
                } else {
                    totalNew += key - 'a' + 1;
                }
                break;
            }
        }

        Day03.list.clear();
    }

    private static void partOme(char[] sack) {
        Set<Character> set = new HashSet<>();
        int priority = 0;

        for (int i = 0; i < sack.length / 2; i++) {
            set.add(sack[i]);
        }

        for (int i = sack.length / 2; i < sack.length; i++) {
            if  (set.contains(sack[i])) {
                if (Character.isUpperCase(sack[i])) {
                    priority = sack[i] - 'A' + 27;
                } else {
                    priority = sack[i] - 'a' + 1;
                }
                break;
            }
        }
        total += priority;
    }
}
