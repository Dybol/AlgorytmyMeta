package tsp.tests.tabu;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.FileImporter;
import tsp.Graph;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.tests.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MaxCounterTest implements Test {

	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> valuesTabu2Opt = new ArrayList<>();
	private final List<Double> valuesTabu2OptWithVNS = new ArrayList<>();
	private final List<Double> valuesTabu2OptWithAspiration = new ArrayList<>();

	@Override
	public void test() throws FileNotFoundException {

		FileImporter importer = new TSPMatrixImporter();
		importer.importGraph("instances/bays29.tsp");
		importer.importOptimalTour("instances/bays29.opt.tour");
		Graph graph = importer.getGraph();

		ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph);
		graph.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph);
		Integer[] solution2Opt = algorithm2Opt.findSolution();
		graph.setCurrentPath(solution2Opt);


		for (int i = 10000; i <= 100_000; i += 10000) {
			Tabu2Opt tabu2Opt = new Tabu2Opt(graph, i, 7, 5000, 1);
			Tabu2OptWithVNS tabu2OptWithVNS = new Tabu2OptWithVNS(graph, i, 7, 5000);
			Tabu2OptWithAspiration tabu2OptWithAspiration = new Tabu2OptWithAspiration(graph, i, 11, 5000, 1);

			keys.add(i);
			valuesTabu2Opt.add(graph.pathLength(tabu2Opt.findSolution()));
			valuesTabu2OptWithAspiration.add(graph.pathLength(tabu2OptWithAspiration.findSolution()));
			valuesTabu2OptWithVNS.add(graph.pathLength(tabu2OptWithVNS.findSolution()));
			System.out.println("End dla i = " + i);
		}

		List<Double> valuesOpt = new ArrayList<>();
		double opt = graph.pathLength(graph.getOptimalPath());
		for (int i = 10000; i <= 100_000; i += 10000) {
			valuesOpt.add(opt);
		}
		Plot plot = Plot.create();

		plot.plot().add(keys, valuesTabu2Opt).label("basic tabu 2opt");
		plot.plot().add(keys, valuesTabu2OptWithVNS).label("tabu 2opt with VNS");
		plot.plot().add(keys, valuesTabu2OptWithAspiration).label("tabu 2opt with aspiration");
		plot.plot().add(keys, valuesOpt);
		plot.title("different max counters");
		plot.xlabel("max counter").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}
}
