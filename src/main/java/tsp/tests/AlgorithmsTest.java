package tsp.tests;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmsTest {

	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> values = new ArrayList<>();

	public void test() {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));
		KRandomAlgorithm algorithm = new KRandomAlgorithm(graph, 10);

		double avg = 0;

		for (int i = 100; i < 30_000; i += 1000) {
			for (int j = 0; j < 20; j++) {
				algorithm.setK(i);
				Integer[] solution = algorithm.findSolution();
				double length = graph.pathLength(solution);
				avg += length;
			}
			System.out.println("Skonczone dla " + i);
			keys.add(i);
			values.add(avg / 10);
			avg = 0;
		}

		Plot plot = Plot.create();

		plot.plot().add(keys, values);
		plot.title("test");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}

}
