package daysix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DaySix {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/daysix/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println((process(line, 4)));
                System.out.println((process(line, 14)));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static int process(String line, int size) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(line.charAt(i), map.getOrDefault(line.charAt(i), 0) + 1);
        }

        if (map.size() == size) return size;

        int l = 0;
        int r = size - 1;
        while (r < line.length()) {
            r++;
            char lc = line.charAt(l);
            char rc = line.charAt(r);

            map.put(rc, map.getOrDefault(rc, 0) + 1);
            map.put(lc, map.getOrDefault(lc, 0) - 1);

            if (map.get(lc) <= 0) map.remove(lc);

            l++;

            if (map.size() == size) {
                return r + 1;
            }
        }
        return -1;
    }
}
