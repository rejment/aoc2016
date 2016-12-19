import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author daniel.rejment@tacton.com (2016-12-19)
 */
public class Day19 {
    public static void main(String[] args) throws IOException {
        int input = Integer.parseInt(Files.readAllLines(Paths.get("src/day19.txt")).get(0));

        int sieve[] = new int[input];
        Arrays.fill(sieve, 1);
        int turn = 0;
        while (true) {
            while (sieve[turn] == 0) turn = (turn + 1) % sieve.length;
            int next = (turn + 1) % sieve.length;
            while (sieve[next] == 0) next = (next + 1) % sieve.length;
            sieve[turn] += sieve[next];
            sieve[next] = 0;
            if (sieve[turn] == input) {
                System.out.println(turn + 1);
                break;
            }
            turn = (next + 1) % sieve.length;
        }

        LinkedList<Integer> firsthalf = new LinkedList<>();
        LinkedList<Integer> secondhalf = new LinkedList<>();
        for (int i = 1; i <= input; i++) secondhalf.add(i);
        while (firsthalf.size() + secondhalf.size() > 1) {
            while (firsthalf.size() <= secondhalf.size()) firsthalf.addLast(secondhalf.removeFirst());
            firsthalf.removeLast();
            secondhalf.addLast(firsthalf.removeFirst());
        }
        System.out.println(secondhalf.get(0));
    }
}
