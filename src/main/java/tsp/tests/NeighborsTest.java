package tsp.tests;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.algorithms.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.NearestNeighborAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

import java.util.ArrayList;
import java.util.List;

public class NeighborsTest implements Test {

	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> values = new ArrayList<>();
	private final List<Double> valuesExtended = new ArrayList<>();

	@Override
	public void test() {

		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));


		double avgNearest = 0;
		double avgExtended = 0;

		for (int i = 0; i < 100; i++) {
			NearestNeighborAlgorithm nearestNeighborAlgorithm = new NearestNeighborAlgorithm(graph, i + 1);
			ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph);
			Integer[] nearest = nearestNeighborAlgorithm.findSolution();
			Integer[] extended = extendedNearestNeighborAlgorithm.findSolution();
			double nearestLength = graph.pathLength(nearest);
			double extendedLength = graph.pathLength(extended);
			avgNearest += nearestLength;
			avgExtended += extendedLength;
			System.out.println("Skonczone dla " + i);
			keys.add(i);
			values.add(avgNearest);
			valuesExtended.add(avgExtended);

			avgNearest = 0;
			avgExtended = 0;
		}

		Plot plot = Plot.create();

		plot.plot().add(keys, values).label("normal");
		plot.plot().add(keys, valuesExtended).label("extended");
		plot.title("nearest neighbors comparison based on different start position");
		plot.xlabel("tries").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}
}

