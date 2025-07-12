import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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