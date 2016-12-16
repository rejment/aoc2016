import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author daniel.rejment@tacton.com (2016-12-16)
 */
public class Day16 {
    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("src/day16.txt")).get(0);

        System.out.println(brute(input, 272));
        System.out.println(brute(input, 35651584));
    }

    private static String brute(String a, int minlen) {
        while (a.length() < minlen) {
            StringBuilder b = new StringBuilder();
            for (int i = a.length() - 1; i >= 0; i--) b.append(a.charAt(i) == '0' ? '1' : '0');
            a = a + "0" + b.toString();
        }

        while (true) {
            StringBuilder cs = new StringBuilder();
            for (int i = 0; i < Math.min(a.length(), minlen - 1); i += 2) {
                cs.append(a.charAt(i) == a.charAt(i + 1) ? '1' : '0');
            }
            a = cs.toString();
            if (cs.length() % 2 == 1) return a;
        }
    }
}
