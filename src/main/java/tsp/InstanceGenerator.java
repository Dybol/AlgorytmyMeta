package tsp;

public interface InstanceGenerator<T> {
	T generateRandomInstances(int numberOfInstances, int maxValue);
}
