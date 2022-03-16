package tsp;

public interface Graph {
	double pathLength(Integer [] nodes);
	boolean isPathCorrect(Integer [] nodes);
	double PRD(Integer [] path);
	double calculateDistance(int from, int to);
	void printCurrentPath();
	Integer[] getCurrentPath();
	void setCurrentPath(Integer [] nodes);
	void printMatrix();
}
