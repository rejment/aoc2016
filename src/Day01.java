import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @author daniel.rejment@tacton.com (2016-12-01)
 */
public class Day01 {
    public static void main(String[] args) throws IOException {
        String instructions = Files.readAllLines(Paths.get("src/day01.txt")).get(0);

        Integer firstOverlapDistance = null;
        Set<String> visitedPositions = new HashSet<>();

        int x = 0, y = 0;
        int dx = 0, dy = 1;

        for (String instruction : instructions.split(", ")) {
            int rotationDirection = (instruction.charAt(0) == 'R') ? 1 : -1;
            int temp = dx;
            dx = rotationDirection * dy;
            dy = (-rotationDirection) * temp;

            for (int step = 0; step < Integer.parseInt(instruction.substring(1)); step++) {
                x += dx;
                y += dy;
                String position = (x + "," + y);
                if (firstOverlapDistance == null && visitedPositions.contains(position)) {
                    firstOverlapDistance = distance(x, y);
                } else {
                    visitedPositions.add(position);
                }
            }
        }
        int distanceFromStart = distance(x, y);
        System.out.println("Part 1: " + distanceFromStart);
        System.out.println("Part 2: " + firstOverlapDistance);
    }

    private static int distance(int x, int y) {
        return Math.abs(x) + Math.abs(y);
    }
}
