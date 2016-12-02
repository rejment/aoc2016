import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-02)
 */
public class Day02 {
    public static void main(String[] args) throws IOException {
        List<String> program = Files.readAllLines(Paths.get("src/day02.txt"));

        int part1[][] = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        System.out.println("Part 1: " + solve(program, part1, 1, 1));

        int part2[][] = {
                {0, 0, 1, 0, 0},
                {0, 2, 3, 4, 0},
                {5, 6, 7, 8, 9},
                {0, 10, 11, 12, 0},
                {0, 0, 13, 0, 0}
        };
        System.out.println("Part 2: " + solve(program, part2, 2, 0));
    }

    private static String solve(List<String> program, int[][] keypad, int x, int y) {
        String res = "";
        for (String line : program) {
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case 'U':
                        if (y > 0 && keypad[y - 1][x] != 0) y--;
                        break;
                    case 'D':
                        if (y < keypad.length - 1 && keypad[y + 1][x] != 0) y++;
                        break;
                    case 'L':
                        if (x > 0 && keypad[y][x - 1] != 0) x--;
                        break;
                    case 'R':
                        if (x < keypad[0].length - 1 && keypad[y][x + 1] != 0) x++;
                        break;
                }
            }
            res += Integer.toHexString(keypad[y][x]);
        }
        return res;
    }
}
