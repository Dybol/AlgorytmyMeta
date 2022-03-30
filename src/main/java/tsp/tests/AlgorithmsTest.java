package tsp.tests;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmsTest {

	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> values = new ArrayList<>();

	public void test() throws FileNotFoundException {
  
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		euc2dImporter.importGraph("instances/berlin52.tsp");
		euc2dImporter.importOptimalTour("instances/berlin52.opt.tour");
		Euc2dGraph graph = euc2dImporter.getGraph();
		KRandomAlgorithm algorithm = new KRandomAlgorithm(graph, 10);

		double avg = 0;
		for (int i = 100; i < 1000; i += 10) {
			for (int j = 1; j <= 50; j++) {
				algorithm.setK(i);
				Integer[] solution = algorithm.findSolution();
				double length = graph.pathLength(solution);
				avg += length;
			}
			System.out.println("Skonczone dla " + i);
			keys.add(i);
			values.add(avg / 50.0);
			avg = 0;
		}
		
		List<Double> valuesOpt = new ArrayList<>();
		double opt = graph.pathLength(graph.getOptimalPath());
		for (int i = 100; i < 1000; i += 10) {
			valuesOpt.add(opt);
		}
		Plot plot = Plot.create();

		plot.plot().add(keys, values);
		plot.title("k-random");
		plot.xlabel("k").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}

}
