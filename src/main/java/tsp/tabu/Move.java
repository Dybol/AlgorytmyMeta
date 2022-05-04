package tsp.tabu;

public class Move {

	private final Integer from;
	private final Integer to;

	public Move(Integer from, Integer to) {
		this.from = from;
		this.to = to;
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getTo() {
		return to;
	}
}
