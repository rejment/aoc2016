import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-07)
 */
public class Day07 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day07.txt"));

        int part1 = 0;
        int part2 = 0;
        for (String input : inputs) {
            String[] parts = input.split("\\[|\\]");

            List<String> abas = new ArrayList<>();
            boolean abbaInBrackets = false;
            boolean abbaOutsideBrackets = false;
            boolean validPart2 = false;

            // in brackets
            for (int i = 1; i < parts.length; i += 2) {
                String part = parts[i];

                // find all the aba in brackets
                for (int c = 0; c < part.length() - 2; c++) {
                    if (part.charAt(c) == part.charAt(c + 2) && part.charAt(c) != part.charAt(c + 1)) {
                        abas.add(part.substring(c, c + 3));
                    }
                }

                // any abba in brackets
                for (int c = 0; c < part.length() - 3; c++) {
                    if (part.charAt(c) == part.charAt(c + 3) && part.charAt(c + 1) == part.charAt(c + 2) && part.charAt(c) != part.charAt(c + 1)) {
                        abbaInBrackets = true;
                    }
                }
            }

            // outside brackets
            for (int i = 0; i < parts.length; i += 2) {
                String part = parts[i];

                // any bab outside brackets
                for (String aba : abas) {
                    if (part.contains("" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1))) {
                        validPart2 = true;
                    }
                }

                // any abba outside brackets
                for (int c = 0; c < part.length() - 3; c++) {
                    if (part.charAt(c) == part.charAt(c + 3) && part.charAt(c + 1) == part.charAt(c + 2) && part.charAt(c) != part.charAt(c + 1)) {
                        abbaOutsideBrackets = true;
                    }
                }
            }

            if (abbaOutsideBrackets && !abbaInBrackets) part1++;
            if (validPart2) part2++;
        }

        System.out.println(part1);
        System.out.println(part2);
    }
}
