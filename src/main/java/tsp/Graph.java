package tsp;

public interface Graph {
	public double pathLength(int [] nodes);
	boolean isPathCorrect(int [] nodes);
	double PRD(int [] path);
	double calculateDistance(int from, int to);
}
