import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author daniel.rejment@tacton.com (2016-12-04)
 */
public class Day04 {
    public static void main(String[] args) throws IOException {
        List<String> rooms = Files.readAllLines(Paths.get("src/day04.txt"));

        int part1 = 0;
        int part2 = 0;
        for (String room : rooms) {
            int sectorId = Integer.parseInt(room.substring(1 + room.lastIndexOf('-'), room.indexOf("[")));
            char[] roomCharacters = room.substring(0, room.lastIndexOf('-')).toCharArray();
            String decrypted = "";
            Map<String, Integer> letterCount = new TreeMap<>();
            for (char aChar : roomCharacters) {
                if (aChar == '-') {
                    decrypted += " ";
                } else {
                    letterCount.compute(Character.toString(aChar), (k, v) -> v == null ? 1 : v + 1);
                    decrypted += Character.toString((char) ((((aChar - 'a') + sectorId) % 26) + 'a'));
                }
            }

            Comparator<Map.Entry<String, Integer>> byCount = Comparator.comparingInt(Map.Entry::getValue);
            Comparator<Map.Entry<String, Integer>> byCountAndLexical = byCount.reversed().thenComparing(Map.Entry::getKey);
            String checksum = letterCount.entrySet().stream().sorted(byCountAndLexical).map(Map.Entry::getKey).limit(5).collect(Collectors.joining(""));
            if (room.endsWith("[" + checksum + "]")) {
                part1 += sectorId;
                if (decrypted.equals("northpole object storage")) {
                    part2 = sectorId;
                }
            }
        }

        System.out.println(part1);
        System.out.println(part2);
    }
}
