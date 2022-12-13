package day13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Day13 {

    private static final List<String> input = new ArrayList<>();

    public static void main(String[] args) {
        consumeInput();
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private static List<Object> buildList(String line) {
        List<Object> list = new ArrayList<>();
        Stack<List<Object>> stack = new Stack<>();
        char[] charArray = line.toCharArray();
        for (int i = 1; i < charArray.length - 1; i++) {
            char c = charArray[i];
            if (c == ',') {
                continue;
            }
            if (c == '[') {
                stack.push(new ArrayList<>());
                continue;
            }
            if (c == ']') {
                List<Object> stackTop = stack.pop();
              if (!stack.isEmpty()) {
                stack.peek().add(stackTop);
              } else {
                list.add(stackTop);
              }
                continue;
            }
            String next = String.valueOf(charArray[i + 1]);
          if (!Character.isDigit(next.charAt(0))) {
            next = "";
          } else {
            i++;
          }
            Integer num = Integer.parseInt(c + next);
          if (!stack.isEmpty()) {
            stack.peek().add(num);
          } else {
            list.add(num);
          }
        }
        return list;
    }

    private static int partOne() {
        Queue<List<Object>> queue = new ArrayDeque<>();
        int total = 0;
        int i = 1;
        for (String line : input) {
            if (line.isEmpty()) {
                if (comparePackets(queue)) {
                    total += i++;
                } else {
                    i++;
                }
                continue;
            }
            queue.offer(buildList(line));
        }
      if (comparePackets(queue)) {
        total += i;
      }
        return total;
    }

    private static boolean comparePackets(Queue<List<Object>> queue) {
      if (queue.isEmpty()) {
        return false;
      }
        List<Object> left = queue.poll();
        List<Object> right = queue.poll();
        return compare(left, right) != -1;
    }

    /*
    More concise version of compareLists
     */
    private static int compare(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return Integer.compare((Integer) left, (Integer) right) * -1;
        }
        if (left instanceof Integer) {
            return compare(List.of(left), right);
        }
        if (right instanceof Integer) {
            return compare(left, List.of(right));
        }

        // both are lists
        List<Integer> leftList = (List<Integer>) left;
        List<Integer> rightList = (List<Integer>) right;
        for (int i = 0; i < leftList.size(); i++) {
            if (i >= rightList.size()) {
                // Ran out of objects on the right
                return -1;
            }
            int res = compare(leftList.get(i), rightList.get(i));
          if (res != 0) {
            return res;
          }
        }

        // Ran out of objects on the left. Compare sizes of lists to determine whether to continue or not
        return compare(leftList.size(), rightList.size());
    }

    private static int compareLists(List<Object> left, List<Object> right) {
        for (int i = 0; i < left.size(); i++) {
            if (i >= right.size()) {
                // Ran out of objects on the right
                return -1;
            }
            Object oLeft = left.get(i);
            Object oRight = right.get(i);

            if (oLeft instanceof Integer) {
                if (oRight instanceof Integer) {
                    if ((int) oLeft > (int) oRight) {
                        return -1;
                    } else if (((int) oLeft < (int) oRight)) {
                        return 1;
                    }
                } else if (oRight instanceof List) {
                    List<Object> oLeftList = new ArrayList<>();
                    oLeftList.add(oLeft);
                    int res = compareLists(oLeftList, (List<Object>) oRight);
                  if (res != 0) {
                    return res;
                  }
                }
            } else if (oLeft instanceof List) {
                if (oRight instanceof Integer) {
                    List<Object> oRightList = new ArrayList<>();
                    oRightList.add(oRight);
                    int res = compareLists((List<Object>) oLeft, oRightList);
                  if (res != 0) {
                    return res;
                  }
                } else if (oRight instanceof List) {
                    int res = compareLists((List<Object>) oLeft, (List<Object>) oRight);
                  if (res != 0) {
                    return res;
                  }
                }
            }
        }
        return left.size() == right.size() ? 0 : Integer.compare(left.size(), right.size()) * -1;
    }

    private static int partTwo() {
        List<List<Object>> list = new ArrayList<>();
        for (String line : input) {
          if (line.isEmpty()) {
            continue;
          }
            list.add(buildList(line));
        }
        list.add(List.of(List.of(2)));
        list.add(List.of(List.of(6)));
        list.sort((a, b) -> compare(b, a));
        int res = 1;
        for (int j = 0, listSize = list.size(); j < listSize; j++) {
            List<Object> i = list.get(j);
            if (i.equals(List.of(List.of(6))) || i.equals(List.of(List.of(2)))) {
                res *= j + 1;
            }
        }
        return res;
    }

    private static void consumeInput() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day13/input.txt"))) {
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
