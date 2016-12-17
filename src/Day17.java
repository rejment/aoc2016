import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-17)
 */
public class Day17 {

    static class State {
        int x = 0;
        int y = 0;
        String path = "";

        public State(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String input = Files.readAllLines(Paths.get("src/day17.txt")).get(0);

        List<String> paths = getPaths(input);
        System.out.println(paths.get(0));
        System.out.println(paths.get(paths.size() - 1).length());
    }

    private static List<String> getPaths(String input) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        List<String> paths = new ArrayList<>();
        Deque<State> queue = new ArrayDeque<>();
        queue.addLast(new State(0, 0, ""));
        while (!queue.isEmpty()) {
            State state = queue.removeFirst();
            if (state.x == 3 && state.y == 3) {
                paths.add(state.path);
                continue;
            }
            String hash = DatatypeConverter.printHexBinary(md5.digest((input + state.path).getBytes())).toLowerCase();
            if (state.y > 0 && !isClosed(hash.charAt(0))) {
                queue.addLast(new State(state.x, state.y - 1, state.path + "U"));
            }
            if (state.y < 3 && !isClosed(hash.charAt(1))) {
                queue.addLast(new State(state.x, state.y + 1, state.path + "D"));
            }
            if (state.x > 0 && !isClosed(hash.charAt(2))) {
                queue.addLast(new State(state.x - 1, state.y, state.path + "L"));
            }
            if (state.x < 3 && !isClosed(hash.charAt(3))) {
                queue.addLast(new State(state.x + 1, state.y, state.path + "R"));
            }
        }
        return paths;
    }

    private static boolean isClosed(char c) {
        return c >= '0' && c <= '9' || c == 'a';
    }
}
