import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author daniel.rejment@tacton.com (2016-12-24)
 */
public class Day24 {

    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day24.txt"));
        int x0 = 0, y0;
        for (y0 = 0; y0 < inputs.size(); y0++) {
            x0 = inputs.get(y0).indexOf('0');
            if (x0 != -1) break;
        }

        System.out.println(bfs(inputs, new int[]{0, x0, y0, 0}, false));
        System.out.println(bfs(inputs, new int[]{0, x0, y0, 0}, true));
    }

    private static int bfs(List<String> inputs, int[] initialState, boolean part2) {
        Comparator<Object> comparator = Comparator.comparingInt(a -> ((int[]) a)[1]).thenComparing(a -> ((int[]) a)[2]).thenComparingInt(a -> ((int[]) a)[3]);
        Set<int[]> seen = new TreeSet<>(comparator);
        Deque<int[]> queue = new ArrayDeque<>(Collections.singletonList(initialState));
        while (!queue.isEmpty()) {
            int[] state = queue.removeFirst();
            char here = inputs.get(state[2]).charAt(state[1]);
            if (here != '.') {
                state[3] |= 1 << (here - '0');
            }
            if (seen.contains(state)) continue;
            seen.add(state);

            if (state[3] == 255 && (!part2 || state[1] == initialState[1] && state[2] == initialState[2])) {
                return state[0];
            }

            int ds[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] d : ds) {
                if (inputs.get(state[2] + d[1]).charAt(state[1] + d[0]) != '#') {
                    queue.offerLast(new int[]{state[0] + 1, state[1] + d[0], state[2] + d[1], state[3]});
                }
            }
        }
        return 0;
    }
}
