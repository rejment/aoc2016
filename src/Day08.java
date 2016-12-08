import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author daniel.rejment@tacton.com (2016-12-08)
 */
public class Day08 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day08.txt"));
        int pixels[][] = new int[6][50];
        for (String input : inputs) {
            if (input.startsWith("rect ")) {
                int[] xes = Stream.of(input.substring(5).split("x")).mapToInt(Integer::parseInt).toArray();
                for (int y=0; y<xes[1]; y++) {
                    for (int x=0; x<xes[0]; x++) {
                        pixels[y][x] = 1;
                    }
                }
            } else if (input.startsWith("rotate row y=")) {
                int[] xes = Stream.of(input.substring(13).split(" by ")).mapToInt(Integer::parseInt).toArray();
                for (int i=0; i<xes[1]; i++) {
                    int temp = pixels[xes[0]][49];
                    System.arraycopy(pixels[xes[0]], 0, pixels[xes[0]], 1, 49);
                    pixels[xes[0]][0] = temp;
                }
            } else if (input.startsWith("rotate column x=")) {
                int[] xes = Stream.of(input.substring(16).split(" by ")).mapToInt(Integer::parseInt).toArray();
                for (int i=0; i<xes[1]; i++) {
                    int temp = pixels[5][xes[0]];
                    for (int a=5; a>0; a--) {
                        pixels[a][xes[0]] = pixels[a-1][xes[0]];
                    }
                    pixels[0][xes[0]] = temp;
                }
            }
        }

        int sum = 0;
        for (int[] row : pixels) {
            for (int pixel : row) {
                sum += pixel;
                System.out.print(pixel == 0 ? " " : "*");
            }
            System.out.println();
        }

        System.out.println(sum);
    }
}
