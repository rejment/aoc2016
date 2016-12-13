import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author daniel.rejment@tacton.com (2016-12-13)
 */
public class Day13 {

    public static void main(String[] args) throws IOException, InterruptedException {
        int input = Integer.parseInt(Files.readAllLines(Paths.get("src/day13.txt")).get(0));
        BiFunction<Integer, Integer, Boolean> isWall = (x, y) -> (Long.bitCount((long) (x * x + 3 * x + 2 * x * y + y + y * y + input)) % 2) == 1;

        int grid[][] = new int[100][100];
        for (int[] aGrid : grid) Arrays.fill(aGrid, Integer.MAX_VALUE);
        grid[1][1] = 0;

        Set<int[]> seen = new TreeSet<>((Comparator<int[]>) (a, b) -> a[0] > b[0] ? 1 : (a[0] < b[0] ? -1 : (a[1] > b[1] ? 1 : (a[1] < b[1] ? -1 : 0))));
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{1, 1});
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

            if (x >= 0 && y >= 0 && !seen.contains(pos) && !isWall.apply(x, y)) {
                if (y > 0) {
                    grid[y - 1][x] = Math.min(grid[y - 1][x], grid[y][x] + 1);
                    queue.offer(new int[]{x, y - 1});
                }
                if (y < grid.length - 1) {
                    grid[y + 1][x] = Math.min(grid[y + 1][x], grid[y][x] + 1);
                    queue.offer(new int[]{x, y + 1});
                }
                if (x > 0) {
                    grid[y][x - 1] = Math.min(grid[y][x - 1], grid[y][x] + 1);
                    queue.offer(new int[]{x - 1, y});
                }
                if (x < grid.length - 1) {
                    grid[y][x + 1] = Math.min(grid[y][x + 1], grid[y][x] + 1);
                    queue.offer(new int[]{x + 1, y});
                }
                seen.add(pos);
            }
        }
        System.out.println(grid[39][31]);

        int part2 = 0;
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                if (!isWall.apply(x, y) && grid[y][x] <= 50) part2++;
            }
        }
        System.out.println(part2);
    }
}
