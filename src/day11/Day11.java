package day11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day11 {
    static class Monkey {
        List<Long> items;
        String operation;
        int testDivisor;
        int monkeyTrue;
        int monkeyFalse;
        long inspections = 0;

        public Monkey(List<Long> items, String operation, int testDivisor, int monkeyTrue,
            int monkeyFalse) {
            this.items = items;
            this.operation = operation;
            this.testDivisor = testDivisor;
            this.monkeyTrue = monkeyTrue;
            this.monkeyFalse = monkeyFalse;
        }

        public long getInspections() {
            return inspections;
        }

        public long inspect(Long item) {
            inspections++;
            String[] splits = operation.split(" ");
            String op = splits[0];
            String opndString = splits[1];
            long opnd;
            if (opndString.equals("old")) {
                opnd = item;
            } else {
                opnd = Long.parseLong(opndString);
            }

            return switch (op) {
                case "*" -> item * opnd;
                case "/" -> item / opnd;
                case "+" -> item + opnd;
                case "-" -> item - opnd;
                default -> -1L;
            };
        }

        public Long getBoredWith(Long item) {
                return item / 3;
        }

        public int test(Long item) {
            if (item % testDivisor == 0) {
                return monkeyTrue;
            } else {
                return monkeyFalse;
            }
        }

        public List<Long> getItems() {
            return items;
        }
    }

    private static final List<String> input = new ArrayList<>();
    private static final List<Monkey> monkeys = new ArrayList<>();
    private static int divisorProduct = 1;

    public static void main(String[] args) {
        consumeInput();
        System.out.println(findMonkeyBusiness(20, true));
        System.out.println(findMonkeyBusiness(10000, false));
    }

    private static long findMonkeyBusiness(int rounds, boolean monkeysGetBored) {
        buildMonkeys();
        while (rounds-- > 0) {
            for (Monkey monkey : monkeys) {
                for (Long item : monkey.getItems()) {
                    long newItem = monkey.inspect(item) % divisorProduct;
                    if (monkeysGetBored) newItem = monkey.getBoredWith(newItem);
                    int newMonkey = monkey.test(newItem);
                    monkeys.get(newMonkey).getItems().add(newItem);
                }
                monkey.getItems().clear();
            }
        }

        PriorityQueue<Monkey> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o2.inspections, o1.inspections));
        pq.addAll(monkeys);
        return pq.poll().getInspections() * pq.poll().getInspections();
    }

    private static void buildMonkeys() {
        monkeys.clear();
        divisorProduct = 1;
        String items = null;
        String op = null;
        String test = null;
        String testTrue = null;
        String testFalse = null;
        for (String line : input) {
            if (line.isEmpty()) {
                monkeys.add(parseMonkey(items, op, test, testTrue, testFalse));
                continue;
            }

            if (line.startsWith("  Starting items:")) {
                items = line.substring("  Starting items:".length() - 1 + 2);
            } else if (line.startsWith("  Operation:")) {
                op = line.substring("  Operation: new = old ".length());
            } else if (line.startsWith("  Test:")) {
                test = line.substring("  Test: divisible by ".length());
            } else if (line.startsWith("    If true:")) {
                testTrue = line.substring("    If true: throw to monkey ".length());
            } else if (line.startsWith("    If false:")) {
                testFalse = line.substring("    If false: throw to monkey ".length());
            }
        }
        monkeys.add(parseMonkey(items, op, test, testTrue, testFalse));
    }

    private static Monkey parseMonkey(String itemsString, String opString, String testString,
        String testTrue, String testFalse) {

        List<Long> items = new ArrayList<>();
        int testDivisor = Integer.parseInt(testString);
        int monkeyTrue = Integer.parseInt(testTrue);
        int monkeyFalse = Integer.parseInt(testFalse);

        String[] splits = itemsString.split(", ");
        for (String split : splits) {
            items.add(Long.parseUnsignedLong(split));
        }

        divisorProduct *= testDivisor;

        return new Monkey(items, opString, testDivisor, monkeyTrue, monkeyFalse);
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day11/input.txt"))) {
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
