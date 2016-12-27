import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author daniel.rejment@tacton.com (2016-12-25)
 */
public class Day25 {
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day25.txt"));

        for (int a = 0; a < 100000; a++) {
            if (execute(inputs, new int[]{a, 0, 0, 0})) {
                System.out.println(a);
                break;
            }
        }
    }

    private static boolean execute(List<String> inputs, int[] regs) {
        int counter = 0;
        for (int pc = 0; pc < inputs.size(); ) {
            String[] split = inputs.get(pc).split(" ");
            switch (split[0]) {
                case "out":
                    if (getValue(regs, split[1]) != (counter & 1)) return false;
                    if (counter++ == 32) return true;
                    break;
                case "cpy":
                    regs[split[2].charAt(0) - 'a'] = getValue(regs, split[1]);
                    if (inputs.get(pc + 1).startsWith("inc") &&
                            inputs.get(pc + 2).startsWith("dec") &&
                            inputs.get(pc + 3).startsWith("jnz") &&
                            inputs.get(pc + 4).startsWith("dec") &&
                            inputs.get(pc + 5).startsWith("jnz")) {
                        String[] inc = inputs.get(pc + 1).split(" ");
                        String[] dec = inputs.get(pc + 2).split(" ");
                        String[] dec2 = inputs.get(pc + 4).split(" ");
                        regs[inc[1].charAt(0) - 'a'] += regs[dec[1].charAt(0) - 'a'] * regs[dec2[1].charAt(0) - 'a'];
                        regs[dec[1].charAt(0) - 'a'] = regs[dec2[1].charAt(0) - 'a'] = 0;
                        pc = pc + 6;
                        continue;
                    }
                    break;
                case "inc":
                    regs[split[1].charAt(0) - 'a']++;
                    break;
                case "mul":
                    regs[0] = regs[1] * regs[3];
                    regs[1] = regs[3] = 0;
                    break;
                case "dec":
                    regs[split[1].charAt(0) - 'a']--;
                    break;
                case "tgl":
                    int idx = pc + getValue(regs, split[1]);
                    if (idx < 0 || idx > inputs.size() - 1) break;
                    String[] s = inputs.get(idx).split(" ");
                    if (s.length == 3 && s[0].equals("jnz")) {
                        s[0] = "cpy";
                    } else if (s.length == 3) {
                        s[0] = "jnz";
                    } else if (s.length == 2 && s[0].equals("inc")) {
                        s[0] = "dec";
                    } else if (s.length == 2) {
                        s[0] = "inc";
                    }
                    inputs.set(idx, String.join(" ", s));
                    break;
                case "jnz":
                    pc += getValue(regs, split[1]) != 0 ? (getValue(regs, split[2]) - 1) : 0;
                    break;
            }
            pc++;
        }
        return false;
    }

    private static int getValue(int[] regs, String s) {
        return s.matches("-?\\d+") ? Integer.parseInt(s) : regs[s.charAt(0) - 'a'];
    }
}
