import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author daniel.rejment@tacton.com (2016-12-11)
 */
public class Day11 {

    public static void main(String[] args) throws IOException {

        List<String> inputs = Files.readAllLines(Paths.get("src/day11.txt"));

        Pattern pattern = Pattern.compile("(\\w+)(?:-compatible)? (generator|microchip)");
        List<String> names = new ArrayList<>();
        int part1[] = new int[2 + 5 * 2];
        for (int floor = 0; floor < inputs.size(); floor++) {
            Matcher matcher = pattern.matcher(inputs.get(floor));
            while (matcher.find()) {
                int stateslot = names.indexOf(matcher.group(1));
                if (stateslot == -1) {
                    stateslot = names.size();
                    names.add(matcher.group(1));
                }
                part1[2 + stateslot * 2 + (matcher.group(2).equals("microchip") ? 1 : 0)] = floor;
            }
        }
        int part2[] = new int[part1.length + 4];
        System.arraycopy(part1, 0, part2, 0, part1.length);

        System.out.println(bfs(part1, new HashSet<>()));
        System.out.println(bfs(part2, new HashSet<>()));
    }

    private static int bfs(int[] initialState, HashSet<String> memo) {
        Deque<int[]> states = new ArrayDeque<>(Collections.singleton(initialState));
        while (!states.isEmpty()) {
            int[] state = states.poll();
            int depth = state[1];
            state[1] = -1;
            String key = Arrays.toString(state);
            state[1] = depth;

            if (IntStream.of(state).skip(2).allMatch(i -> i == 3)) return depth;

            if (!memo.contains(key) && !invalidState(state)) {
                // move 2 up
                if (state[0] < 3) {
                    for (int a = 2; a < state.length - 1; a++) {
                        if (state[a] == state[0]) {
                            for (int b = a + 1; b < state.length; b++) {
                                if (state[b] == state[0]) {
                                    int newstate[] = Arrays.copyOf(state, state.length);
                                    newstate[0]++;
                                    newstate[1]++;
                                    newstate[a]++;
                                    newstate[b]++;
                                    states.offer(newstate);
                                }
                            }
                        }
                    }
                }
                // move 1 up
                if (state[0] < 3) {
                    for (int a = 2; a < state.length; a++) {
                        if (state[a] == state[0]) {
                            int newstate[] = Arrays.copyOf(state, state.length);
                            newstate[0]++;
                            newstate[1]++;
                            newstate[a]++;
                            states.offer(newstate);
                        }
                    }
                }
                // move 1 down
                if (state[0] > 0) {
                    for (int a = 2; a < state.length; a++) {
                        if (state[a] == state[0]) {
                            int newstate[] = Arrays.copyOf(state, state.length);
                            newstate[0]--;
                            newstate[1]++;
                            newstate[a]--;
                            states.offer(newstate);
                        }
                    }
                }
            }
            memo.add(key);
        }
        return -1;
    }

    private static boolean invalidState(int[] state) {
        if (state[0] < 0 || state[0] > 3) return true;
        for (int floor = 0; floor < 4; floor++) {
            boolean hasAnyGenerator = false;
            boolean hasUnpairedChip = false;
            for (int i = 0; i < (state.length - 2) / 2; i++) {
                if (state[2 + i * 2] == floor) hasAnyGenerator = true;
                if (state[2 + i * 2 + 1] == floor && state[2 + i * 2] != floor) hasUnpairedChip = true;
                if (hasAnyGenerator && hasUnpairedChip) return true;
            }
        }
        return false;
    }
}
