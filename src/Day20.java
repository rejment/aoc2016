import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-20)
 */
public class Day20 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day20.txt"));
        inputs.sort(Comparator.comparingLong(x -> Long.parseLong(x.split("-")[0])));

        long firstfree = 0;
        long freecount = 0;
        long maxto = 0;
        for (String input : inputs) {
            String[] split = input.split("-");
            long lo = Long.parseLong(split[0]);
            long hi = Long.parseLong(split[1]);
            if (lo <= firstfree && hi >= firstfree) {
                firstfree = hi + 1;
            }

            if (lo > maxto + 1) {
                freecount += lo - maxto - 1;
            }
            maxto = Math.max(maxto, hi);
        }

        System.out.println(firstfree);
        System.out.println(freecount);
    }
}
