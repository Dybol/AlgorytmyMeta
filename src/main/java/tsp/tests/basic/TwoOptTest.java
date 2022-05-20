package tsp.tests.basic;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.tests.Test;

import java.util.ArrayList;
import java.util.List;

public class TwoOptTest implements Test {

	private final List<Double> firstList = new ArrayList<>();
	private final List<Double> secondList = new ArrayList<>();
	private final List<Integer> keys = new ArrayList<>();

	@Override
	public void test() {
		Euc2dImporter euc2dImporter = new Euc2dImporter();

		for (int i = 0; i <= 100; i += 5) {
			Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));
			ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph);
			Integer[] pathFromExtendedAlg = extendedNearestNeighborAlgorithm.findSolution();

			graph.setCurrentPath(generateRandomPath(100));
			Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph);
			double firstLen = graph.pathLength(algorithm2Opt.findSolution());

			graph.setCurrentPath(pathFromExtendedAlg);
			double secondLen = graph.pathLength(algorithm2Opt.findSolution());

			System.out.println(firstLen);
			System.out.println(secondLen);

			firstList.add(firstLen);
			secondList.add(secondLen);
			keys.add(i);
		}
		Plot plot = Plot.create();

		plot.plot().add(keys, firstList).label("2opt with start path from nearest neighbor").build();
		plot.plot().add(keys, secondList).label("2opt with random start path").build();

		plot.xlabel("number of instances").build();
		plot.ylabel("path length").build();

		plot.title("2opt with different start data");

		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}

	public Integer[] generateRandomPath(int n) {
		Integer[] tab = new Integer[n];
		for (int i = 0; i < n; i++) {
			tab[i] = i + 1;
		}
		return tab;
	}
}
