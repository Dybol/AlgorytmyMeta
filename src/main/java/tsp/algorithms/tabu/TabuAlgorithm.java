package tsp.algorithms.tabu;

import tsp.Graph;
import tsp.algorithms.Algorithm;
import tsp.tabu.Move;

public abstract class TabuAlgorithm implements Algorithm {

	private final Graph graph;

	private int counter = 0;
	private int tabPointer;

	//in millis!
	private Integer maxExecutionTime = 10 * 1_000;
	private Integer maxCounter = 100000;

	private Boolean stopOnCounter = true;
	private Integer tenure = 7;

	private Move[] tabuArray = new Move[tenure];

	private int stagnationCounter;
	private int maxStagnationCounter = 1000;
	private double stagnationMultiplier = 0.0;
	private int stagnationConstant = 1000;

	public TabuAlgorithm(Graph graph, Integer maxExecutionTime, Integer maxCounter, Boolean stopOnCounter, Integer tenure, int maxStagnationCounter) {
		this.graph = graph;
		this.maxExecutionTime = maxExecutionTime;
		this.maxCounter = maxCounter;
		this.stopOnCounter = stopOnCounter;
		this.tenure = tenure;
		this.maxStagnationCounter = maxStagnationCounter;
		tabuArray = new Move[tenure];
	}

	public TabuAlgorithm(Graph graph, Integer maxExecutionTime, Integer maxCounter, Boolean stopOnCounter, Integer tenure, Double stagnationMultiplier, Integer stagnationConstant) {
		this.graph = graph;
		this.maxCounter = maxCounter;
		this.tenure = tenure;
		this.maxExecutionTime = maxExecutionTime;
		this.stopOnCounter = stopOnCounter;
		this.stagnationConstant = stagnationConstant;
		this.stagnationMultiplier = stagnationMultiplier;
		this.maxStagnationCounter = (int) Math.floor(stagnationCounter * stagnationMultiplier + stagnationConstant);
		tabuArray = new Move[tenure];
	}

	public TabuAlgorithm(Graph graph, Integer maxCounter, Integer tenure, int stagnationConstant) {
		this.graph = graph;
		this.maxCounter = maxCounter;
		this.tenure = tenure;
		this.stagnationConstant = stagnationConstant;
		tabuArray = new Move[tenure];
	}

	public TabuAlgorithm(Graph graph, Integer maxCounter, Integer tenure, int maxStagnationCounter, Integer maxExecutionTime) {
		this.graph = graph;
		this.maxCounter = maxCounter;
		this.tenure = tenure;
		this.maxStagnationCounter = maxStagnationCounter;
		this.maxExecutionTime = maxExecutionTime;
		tabuArray = new Move[tenure];
	}

	public TabuAlgorithm(Graph graph, boolean stopOnCounter, Integer tenure, int maxStagnationCounter, double stagnationMultiplier, int stagnationConstant) {
		this.graph = graph;
		this.stopOnCounter = stopOnCounter;
		this.tenure = tenure;
		this.maxStagnationCounter = maxStagnationCounter;
		this.stagnationMultiplier = stagnationMultiplier;
		this.stagnationConstant = stagnationConstant;
		tabuArray = new Move[tenure];
	}

	public TabuAlgorithm(Graph graph) {
		this.graph = graph;
		counter = 0;
	}

	public Integer[] insert(Integer[] tab, int from, int to) {
		Integer[] tabPom = tab.clone();
		Integer toInsert = tabPom[from];
		if (from < to) {
			for (int i = from; i < to; i++) {
				tabPom[i] = tabPom[i + 1];
			}
			tabPom[to] = toInsert;
		} else if (from > to) {
			for (int i = from; i > to; i--) {
				tabPom[i] = tabPom[i - 1];
			}
			tabPom[to] = toInsert;
		}
		return tabPom;
	}

	public Integer[] invert(Integer[] tab, int from, int to) {
		Integer[] tabPom = new Integer[tab.length];
		for (int i = 0; i < tab.length; i++) {
			if (i < from || i > to) {
				tabPom[i] = tab[i];
			} else {
				tabPom[i] = tab[to - (i - from)];
			}
		}
		return tabPom;
	}

	public Integer[] swap(Integer[] tab, int from, int to) {
		Integer[] tabPom = tab.clone();
		Integer help = tabPom[from];
		tabPom[from] = tabPom[to];
		tabPom[to] = help;
		return tabPom;
	}

	public boolean stopCriterion(long now) {
		return stopOnCounter ? counter >= maxCounter : System.currentTimeMillis() - now > maxExecutionTime;
	}

	public void addOnTabuList(Move move) {
		getTabuArray()[tabPointer] = move;
		increaseTabPointer();
	}

	public boolean isOnTabuList(int i, int j) {
		for (Move move : getTabuArray()) {
			if (move != null) {
				if (move.getFrom() == i && move.getTo() == j)
					return true;
				//fragment kodu sprawdzający szczególny przypadek:
				//invert(i,i+1) = invert(i+1,i) = swap(i,i+1) = swap(i+1, i) = insert(i, i+1) = insert(i+1, i)
				if (i == j + 1 || i == j - 1)
					if (move.getFrom() == j && move.getTo() == i)
						return true;
			}
		}
		return false;
	}

	// ------------------ getters and setters -------------------------------

	public Graph getGraph() {
		return graph;
	}

	public Integer getMaxExecutionTime() {
		return maxExecutionTime;
	}

	public Integer getMaxCounter() {
		return maxCounter;
	}

	public Boolean getStopOnCounter() {
		return stopOnCounter;
	}

	public Integer getTenure() {
		return tenure;
	}

	public Move[] getTabuArray() {
		return tabuArray;
	}

	public int getMaxStagnationCounter() {
		return maxStagnationCounter;
	}

	public int getStagnationConstant() {
		return stagnationConstant;
	}

	public void setMaxExecutionTime(Integer maxExecutionTime) {
		this.maxExecutionTime = maxExecutionTime;
	}

	public void setMaxCounter(Integer maxCounter) {
		this.maxCounter = maxCounter;
	}

	public void setStopOnCounter(Boolean stopOnCounter) {
		this.stopOnCounter = stopOnCounter;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public void setTabuArray(Move[] tabuArray) {
		this.tabuArray = tabuArray;
	}

	public void setMaxStagnationCounter(int maxStagnationCounter) {
		this.maxStagnationCounter = maxStagnationCounter;
	}

	public void setStagnationMultiplier(double stagnationMultiplier) {
		this.stagnationMultiplier = stagnationMultiplier;
	}

	public void setStagnationConstant(int stagnationConstant) {
		this.stagnationConstant = stagnationConstant;
	}

	public void increaseCounter() {
		this.counter++;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getTabPointer() {
		return tabPointer;
	}

	public void setTabPointer(int tabPointer) {
		this.tabPointer = tabPointer;
	}

	public void increaseTabPointer() {
		tabPointer = (tabPointer + 1) % tenure;
	}

	public double getStagnationMultiplier() {
		return stagnationMultiplier;
	}

	public int getStagnationCounter() {
		return stagnationCounter;
	}

	public void setStagnationCounter(int stagnationCounter) {
		this.stagnationCounter = stagnationCounter;
	}

	public void increaseStagnationCounter() {
		this.stagnationCounter++;
	}

	public void resetStagnationCounter() {
		this.stagnationCounter = 0;
	}
}
