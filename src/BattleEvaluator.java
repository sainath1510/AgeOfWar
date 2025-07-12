import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BattleEvaluator {
	private static final Map<String, Set<String>> advantageMap = new HashMap<>();

	public BattleEvaluator() {

	}

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

		if (ownPower > enemyPower)
			return 1;
		if (ownPower == enemyPower)
			return 0;
		return -1;
	}
}
