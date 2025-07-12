import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class BattleSimulator {
	BattleEvaluator be = new BattleEvaluator();

	public Optional<List<Platoon>> findWinningArrangement(List<Platoon> own, List<Platoon> opponent) {
		boolean[] used = new boolean[own.size()];
		List<Platoon> arrangement = new ArrayList<>();

		return backtrack(own, opponent, used, arrangement, 0);
	}

	private Optional<List<Platoon>> backtrack(List<Platoon> own, List<Platoon> opponent, boolean[] used,
			List<Platoon> current, int depth) {
		if (depth == own.size()) {
			int wins = 0;
			for (int i = 0; i < own.size(); i++) {
				if (be.evaluate(current.get(i), opponent.get(i)) == 1) {
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
				if (result.isPresent())
					return result;

				current.remove(current.size() - 1);
				used[i] = false;
			}
		}

		return Optional.empty();
	}
}