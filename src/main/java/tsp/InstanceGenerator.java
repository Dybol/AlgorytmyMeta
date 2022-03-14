package tsp;

import java.util.List;

public interface InstanceGenerator<T> {
	List<T> generateRandomInstances(int numberOfInstances, int maxValue);
}
