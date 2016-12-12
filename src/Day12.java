import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-12)
 */
public class Day12 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day12.txt"));

        System.out.println(execute(inputs, new int[]{0, 0, 0, 0}));
        System.out.println(execute(inputs, new int[]{0, 0, 1, 0}));
    }

    private static int execute(List<String> inputs, int[] regs) {
        for (int pc = 0; pc < inputs.size(); ) {
            String[] split = inputs.get(pc).split(" ");
            switch (split[0]) {
                case "cpy":
                    regs[split[2].charAt(0) - 'a'] = getValue(regs, split[1]);
                    break;
                case "inc":
                    regs[split[1].charAt(0) - 'a']++;
                    break;
                case "dec":
                    regs[split[1].charAt(0) - 'a']--;
                    break;
                case "jnz":
                    pc += getValue(regs, split[1]) != 0 ? (Integer.parseInt(split[2]) - 1) : 0;
                    break;
            }
            pc++;
        }
        return regs[0];
    }

    private static int getValue(int[] regs, String s) {
        return s.matches("-?\\d+") ? Integer.parseInt(s) : regs[s.charAt(0) - 'a'];
    }
}
