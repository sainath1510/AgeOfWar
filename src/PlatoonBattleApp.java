import java.util.*;
import java.util.stream.Collectors;

class Platoon {
    private final String type;
    private final int size;

    public Platoon(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public Platoon(String representation) {
        String[] parts = representation.split("#");
        this.type = parts[0];
        this.size = Integer.parseInt(parts[1]);
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public double effectiveStrengthAgainst(Platoon opponent, Map<String, Set<String>> advantageMap) {
        Set<String> advantages = advantageMap.getOrDefault(this.type, new HashSet<>());
        if (advantages.contains(opponent.type)) {
            return this.size * 2.0;
        }
        return this.size;
    }

    @Override
    public String toString() {
        return type + "#" + size;
    }
}

class BattleEvaluator {
    private static final Map<String, Set<String>> advantageMap = new HashMap<>();

    static {
        advantageMap.put("Militia", new HashSet<>(Arrays.asList("Spearmen", "LightCavalry")));
        advantageMap.put("Spearmen", new HashSet<>(Arrays.asList("LightCavalry", "HeavyCavalry")));
        advantageMap.put("LightCavalry", new HashSet<>(Arrays.asList("FootArcher", "CavalryArcher")));
        advantageMap.put("HeavyCavalry", new HashSet<>(Arrays.asList("Militia", "FootArcher", "LightCavalry")));
        advantageMap.put("CavalryArcher", new HashSet<>(Arrays.asList("Spearmen", "HeavyCavalry")));
        advantageMap.put("FootArcher", new HashSet<>(Arrays.asList("Militia", "CavalryArcher")));
    }

    public static int evaluate(Platoon own, Platoon enemy) {
        double ownPower = own.effectiveStrengthAgainst(enemy, advantageMap);
        double enemyPower = enemy.effectiveStrengthAgainst(own, advantageMap);

        if (ownPower > enemyPower) return 1;
        if (ownPower == enemyPower) return 0;
        return -1;
    }
}


class BattleSimulator {
    public Optional<List<Platoon>> findWinningArrangement(List<Platoon> own, List<Platoon> opponent) {
        boolean[] used = new boolean[own.size()];
        List<Platoon> arrangement = new ArrayList<>();

        return backtrack(own, opponent, used, arrangement, 0);
    }

    private Optional<List<Platoon>> backtrack(List<Platoon> own, List<Platoon> opponent, boolean[] used, List<Platoon> current, int depth) {
        if (depth == own.size()) {
            int wins = 0;
            for (int i = 0; i < own.size(); i++) {
                if (BattleEvaluator.evaluate(current.get(i), opponent.get(i)) == 1) {
                    wins++;
                }
            }
            if (wins >= 3) {
                return Optional.of(new ArrayList<>(current));
            }
            return Optional.empty();
        }

        for (int i = 0; i < own.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(own.get(i));

                Optional<List<Platoon>> result = backtrack(own, opponent, used, current, depth + 1);
                if (result.isPresent()) return result;

                current.remove(current.size() - 1);
                used[i] = false;
            }
        }

        return Optional.empty();
    }
}

public class PlatoonBattleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Platoon> myPlatoons = parseInput(scanner.nextLine());
        List<Platoon> enemyPlatoons = parseInput(scanner.nextLine());

        BattleSimulator simulator = new BattleSimulator();
        Optional<List<Platoon>> result = simulator.findWinningArrangement(myPlatoons, enemyPlatoons);

        if (result.isPresent()) {
            System.out.println(joinPlatoons(result.get()));
        } else {
            System.out.println("There is no chance of winning");
        }
    }

    private static List<Platoon> parseInput(String line) {
        String[] parts = line.split(";");
        List<Platoon> platoons = new ArrayList<>();
        for (String part : parts) {
            platoons.add(new Platoon(part));
        }
        return platoons;
    }

    private static String joinPlatoons(List<Platoon> platoons) {
        return String.join(";", platoons.stream().map(Platoon::toString).collect(Collectors.toList()));
    }
}
