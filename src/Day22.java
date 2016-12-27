import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author daniel.rejment@tacton.com (2016-12-22)
 */
public class Day22 {
    public static void main(String[] args) throws IOException {
        List<String[]> nodes = Files.lines(Paths.get("src/day22.txt")).skip(2).map(line -> line.replace("T", "").split("\\s+")).collect(Collectors.toList());

        int count = 0;
        int board[][] = new int[30][32];
        int state[] = {0, 0, 0, 31}; // 0:moves, 1:hole-x, 2:hole-y, 3:data-x
        for (int y = 0; y < 30; y++) {
            for (int x = 0; x < 32; x++) {
                String[] nodeA = nodes.get(x * 30 + y);
                for (String[] nodeB : nodes) {
                    if (nodeA != nodeB && !nodeA[2].equals("0") && Integer.parseInt(nodeA[2]) <= Integer.parseInt(nodeB[3])) {
                        count++;
                        board[y][x] = 1;
                    }
                }
                if (nodeA[2].equals("0")) {
                    state[1] = x;
                    state[2] = y;
                }
            }
        }
        System.out.println(count);
        System.out.println(bfs(state, board));
    }

    private static int bfs(int[] initialState, int[][] board) {
        Comparator<Object> comparator = Comparator.comparingInt(a -> ((int[]) a)[1]).thenComparing(a -> ((int[]) a)[2]).thenComparingInt(a -> ((int[]) a)[3]);
        Set<int[]> seen = new TreeSet<>(comparator);

        Deque<int[]> queue = new ArrayDeque<>(Collections.singleton(initialState));
        while (!queue.isEmpty()) {
            int[] state = queue.pollFirst();
            if (seen.contains(state)) continue;
            seen.add(state);

            if (state[3] == 0) {
                return state[0];
            }

            if (state[2] == 0 && state[1] == state[3] - 1) {
                queue.offerLast(new int[]{state[0] + 1, state[3], 0, state[1]});
            }

            int ds[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] d : ds) {
                int x = state[1] + d[0];
                int y = state[2] + d[1];
                if (x < 32 && x >= 0 && y < 30 && y >= 0 && board[y][x] != 0 && !(y == 0 && x == state[3])) {
                    queue.offerLast(new int[]{state[0] + 1, x, y, state[3]});
                }
            }
        }
        return -1;
    }
}
