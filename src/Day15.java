import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author daniel.rejment@tacton.com (2016-12-15)
 */
public class Day15 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day15.txt"));
        int[] pos = new int[7];
        int[] off = new int[7];

        for (int i = 0; i < inputs.size(); i++) {
            String[] split = inputs.get(i).split("Disc #| has | positions; at time=0, it is at position |\\.");
            pos[i] = Integer.parseInt(split[2]);
            off[i] = Integer.parseInt(split[3]);
        }
        pos[6] = 11; // Disc #7 has 11 positions; at time=0, it is at position 0.
        off[6] = 0;
        System.out.println(solve(pos, off, 6));
        System.out.println(solve(pos, off, 7));
    }

    private static int solve(int[] pos, int[] off, int discs) {
        for (int i = 0; ; i++) {
            int finali = i;
            if (IntStream.range(0, discs).allMatch(disc -> (off[disc] + finali + disc + 1) % pos[disc] == 0)) return i;
        }
    }
}
