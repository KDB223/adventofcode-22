package day07;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Day07 {

    private static final Stack<String> dirStack = new Stack<>();
    private static final Stack<Integer> sizeStack = new Stack<>();
    private static final Map<String, Integer> map = new HashMap<>();
    private static int total = 0;
    private static final int TOTAL_DISK_SPACE = 70000000;
    private static final int REQD_FREE_SPACE = 30000000;


    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dayseven/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                partOne(line);
            }
            cleanup();
            System.out.println(total);
            System.out.println(partTwo());
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file: " + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            System.exit(1);
        }
    }

    private static int partTwo() {
        int availableFreeSpace = TOTAL_DISK_SPACE - map.get("[/]");
        int needToDelete = REQD_FREE_SPACE - availableFreeSpace;
        int min = Integer.MAX_VALUE;
        for (int size : map.values()) {
            if (size >= needToDelete) {
                min = Math.min(min, size);
            }
        }
        return min;
    }

    private static void cleanup() {
        while (!dirStack.isEmpty()) {
            popDir();
        }
    }

    private static void popDir() {
        int size = sizeStack.peek();
        map.put(dirStack.toString(), size);
        if (size <= 100000) {
            total += size;
        }

        sizeStack.pop();
        if (!sizeStack.isEmpty()) {
            // add subdir size to ongoing parent dir's running total
            int parent = sizeStack.pop();
            sizeStack.push(parent + size);
        }
        dirStack.pop();
    }

    private static void partOne(String line) {
        if (line.startsWith("$ cd ..")) {     // up
            // commit to map
            // pop from stacks
            popDir();
        } else if (line.startsWith("$ cd ")) { // in
            // push to stack
            String dir = line.split(" ")[2];
            dirStack.push(dir);
            sizeStack.push(0);
        } else if (!line.startsWith("$ ls") && !line.startsWith("dir ")) {   // listing
            // add to running total
            int runningTotal = sizeStack.pop();
            int size = Integer.parseInt(line.split(" ")[0]);
            runningTotal += size;
            sizeStack.push(runningTotal);
        }
    }
}
