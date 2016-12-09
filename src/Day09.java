import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daniel.rejment@tacton.com (2016-12-09)
 */
public class Day09 {

    private static Pattern pattern = Pattern.compile("\\((\\d+)x(\\d+)\\)");

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("src/day09.txt")).get(0);

        System.out.println(expand(input, 0, input.length() - 1, false));
        System.out.println(expand(input, 0, input.length() - 1, true));
    }

    private static long expand(String input, int start, int end, boolean recurse) {
        Matcher matcher = pattern.matcher(input);
        long size = 0;
        while (true) {
            if (!matcher.find(start) || matcher.start() >= end) break;
            size += matcher.start() - start;
            int len = Integer.parseInt(matcher.group(1));
            int times = Integer.parseInt(matcher.group(2));
            long expanded = recurse ? expand(input, matcher.end(), matcher.end() + len - 1, true) : len;
            size += expanded * times;
            start = matcher.end() + len;
        }
        size += end - start + 1;
        return size;
    }
}
