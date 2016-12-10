import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author daniel.rejment@tacton.com (2016-12-10)
 */
public class Day10 {

    public static class Bot {
        public String name;
        public List<Integer> chips = new ArrayList<>();
        public List<String> instructions = new ArrayList<>();

        public Bot(String name) {
            this.name = name;
        }

        public void addChip(int i) {
            chips.add(i);
            chips.sort(Integer::compare);
            run();
        }

        public void addInstruction(String instruction) {
            instructions.add(instruction);
            run();
        }

        public void run() {
            if (chips.size() >= 2) {
                while (!instructions.isEmpty()) {
                    Integer low = chips.remove(0);
                    Integer hi = chips.remove(chips.size() - 1);

                    if (low == 17 && hi == 61) {
                        System.out.println(name);
                    }

                    String instruction = instructions.remove(0);
                    String[] split = instruction.split(" gives low to | and high to ");

                    getBot(split[1]).addChip(low);
                    getBot(split[2]).addChip(hi);
                }
            }
        }
    }

    public static Map<String, Bot> bots = new TreeMap<>();

    public static Bot getBot(String targetBot) {
        return bots.computeIfAbsent(targetBot, Bot::new);
    }

    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("src/day10.txt"));
        for (String input : inputs) {
            if (input.startsWith("bot")) {
                getBot(input.substring(0, input.indexOf(" ", 4))).addInstruction(input);
            } else if (input.startsWith("value")) {
                String[] split = input.split("value | goes to ");
                getBot(split[2]).addChip(Integer.parseInt(split[1]));
            }
        }

        System.out.println(getBot("output 0").chips.get(0) * getBot("output 1").chips.get(0) * getBot("output 2").chips.get(0));
    }
}
