import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daniel.rejment@tacton.com (2016-12-21)
 */
public class Day21 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day21.txt"));
        System.out.println(encode(inputs, "abcdefgh"));

        for (String candidate : perms("", "abcdefgh", new ArrayList<>())) {
            if (encode(inputs, candidate).equals("fbgdceah")) {
                System.out.println(candidate);
                break;
            }
        }

    }

    private static String encode(List<String> inputs, String code) {
        List<Integer> chars = code.chars().boxed().collect(Collectors.toList());

        for (String input : inputs) {
            String[] split = input.split(" ");
            if (input.startsWith("move position")) {
                chars.add(Integer.parseInt(split[5]), chars.remove(Integer.parseInt(split[2])));
            } else if (input.startsWith("swap position")) {
                Collections.swap(chars, Integer.parseInt(split[2]), Integer.parseInt(split[5]));
            } else if (input.startsWith("swap letter")) {
                Collections.swap(chars, chars.indexOf((int) split[2].charAt(0)), chars.indexOf((int) split[5].charAt(0)));
            } else if (input.startsWith("reverse positions")) {
                Collections.reverse(chars.subList(Integer.parseInt(split[2]), Integer.parseInt(split[4]) + 1));
            } else if (input.startsWith("rotate based ")) {
                int idx = chars.indexOf((int) input.charAt(35));
                Collections.rotate(chars, (1 + idx + (idx >= 4 ? 1 : 0)));
            } else if (input.startsWith("rotate")) {
                Collections.rotate(chars, Integer.parseInt(split[2]) * ("left".equals(split[1]) ? -1 : 1));
            }
        }

        return chars.stream().map(a -> Character.toString((char) a.intValue())).collect(Collectors.joining());
    }

    private static List<String> perms(String prefix, String str, List<String> permutations) {
        if (str.isEmpty()) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                perms(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1), permutations);
            }
        }
        return permutations;
    }
}
