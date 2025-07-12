import java.util.*;
import java.util.stream.Collectors;

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
