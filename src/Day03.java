import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author daniel.rejment@tacton.com (2016-12-03)
 */
public class Day03 {
    public static void main(String[] args) throws IOException {
        List<String> program = Files.readAllLines(Paths.get("src/day03.txt"));

        int part1 = program.stream().mapToInt(s -> {
            int[] ints = Stream.of(s.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
            return isPossible(ints[0], ints[1], ints[2]);
        }).sum();
        System.out.println(part1);

        int part2 = 0;
        List<int[]> arrays = program.stream().map(s ->
                Stream.of(s.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray()
        ).collect(Collectors.toList());
        for (int i = 0; i < arrays.size(); i += 3) {
            part2 += isPossible(arrays.get(i)[0], arrays.get(i + 1)[0], arrays.get(i + 2)[0]);
            part2 += isPossible(arrays.get(i)[1], arrays.get(i + 1)[1], arrays.get(i + 2)[1]);
            part2 += isPossible(arrays.get(i)[2], arrays.get(i + 1)[2], arrays.get(i + 2)[2]);
        }
        System.out.println(part2);
    }

    private static int isPossible(int x0, int x1, int x2) {
        boolean a = x0 + x1 > x2;
        boolean b = x0 + x2 > x1;
        boolean c = x1 + x2 > x0;
        return a && b && c ? 1 : 0;
    }
}
