import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author daniel.rejment@tacton.com (2016-12-05)
 */
public class Day05 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String in = Files.readAllLines(Paths.get("src/day05.txt")).get(0);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        int index = 0;
        String part1 = "";
        char[] part2 = new char[8];
        int bits = 0;
        while (true) {
            String hash = DatatypeConverter.printHexBinary(md5.digest((in + index).getBytes()));
            index++;
            if (hash.startsWith("00000")) {
                if (part1.length() < 8) {
                    part1 += hash.charAt(5);
                }

                int pos = hash.charAt(5) - '0';
                if (pos < 8) {
                    if ((bits & (1 << pos)) == 0) {
                        part2[pos] = hash.charAt(6);
                        bits |= (1 << pos);
                        if (bits == 0b11111111) {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(part1);
        System.out.println(part2);
    }
}
