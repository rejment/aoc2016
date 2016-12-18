import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author daniel.rejment@tacton.com (2016-12-18)
 */
public class Day18 {
    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("src/day18.txt")).get(0);

        System.out.println(count(input, 40));
        System.out.println(count(input, 400000));
    }

    private static int count(String input, int rows) {
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == '.') count++;
        }
        for (int i = 0; i < rows - 1; i++) {
            StringBuilder next = new StringBuilder();
            for (int c = 0; c < input.length(); c++) {
                boolean safe = isSafe(input, c);
                if (safe) count++;
                next.append(safe ? '.' : '^');
            }
            input = next.toString();
        }
        return count;
    }

    private static boolean isSafe(String input, int i) {
        boolean leftSafe = i == 0 || input.charAt(i - 1) == '.';
        boolean rightSafe = i == input.length() - 1 || input.charAt(i + 1) == '.';
        boolean centerSafe = input.charAt(i) == '.';
        if (!leftSafe && !centerSafe && rightSafe) return false;
        if (!rightSafe && !centerSafe && leftSafe) return false;
        if (rightSafe && centerSafe && !leftSafe) return false;
        if (!rightSafe && centerSafe && leftSafe) return false;
        return true;
    }
}
