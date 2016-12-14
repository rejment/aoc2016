import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daniel.rejment@tacton.com (2016-12-14)
 */
public class Day14 {

    private static MessageDigest md5;
    private static Map<String, String> hashes;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String input = Files.readAllLines(Paths.get("src/day14.txt")).get(0);
        md5 = MessageDigest.getInstance("MD5");

        System.out.println(solve(input, 0));
        System.out.println(solve(input, 2016));
    }

    private static int solve(String input, int stretch) {
        Pattern pattern = Pattern.compile("(.)\\1\\1");
        hashes = new HashMap<>();
        int key = 0;
        for (int i = 0; ; i++) {
            Matcher matcher = pattern.matcher(getHash(input + i, stretch));
            if (matcher.find()) {
                String five = matcher.group().substring(0, 2) + matcher.group().substring(0, 3);
                for (int j = 1; j < 1000; j++) {
                    if (getHash(input + (i + j), stretch).contains(five) && (++key == 64)) return i;
                }
            }
        }
    }

    private static String getHash(String input, int stretch) {
        return hashes.computeIfAbsent(input, o -> {
            String result = DatatypeConverter.printHexBinary(md5.digest(input.getBytes())).toLowerCase();
            for (int x = 0; x < stretch; x++) {
                result = DatatypeConverter.printHexBinary(md5.digest(result.getBytes())).toLowerCase();
            }
            return result;
        });
    }
}
