import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author daniel.rejment@tacton.com (2016-12-06)
 */
public class Day06 {
    public static void main(String[] args) throws IOException {
        List<HashMap<String, Integer>> freqs = IntStream.range(0, 8).mapToObj(i -> new HashMap<String, Integer>()).collect(Collectors.toList());

        Files.readAllLines(Paths.get("src/day06.txt")).forEach(input -> {
            for (int i = 0; i < input.length(); i++) {
                freqs.get(i).compute(Character.toString(input.charAt(i)), (k, v) -> v == null ? 1 : v + 1);
            }
        });

        String part1 = "";
        String part2 = "";
        for (HashMap<String, Integer> freq : freqs) {
            Comparator<Map.Entry<String, Integer>> smallest = Map.Entry.comparingByValue();
            part1 += freq.entrySet().stream().sorted(smallest.reversed()).map(Map.Entry::getKey).findFirst().get();
            part2 += freq.entrySet().stream().sorted(smallest).map(Map.Entry::getKey).findFirst().get();
        }
        System.out.println(part1);
        System.out.println(part2);
    }
}
