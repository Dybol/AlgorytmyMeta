package tsp.tests;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.algorithms.Algorithm2Opt;
import tsp.algorithms.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.KRandomAlgorithm;
import tsp.algorithms.NearestNeighborAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("DuplicatedCode")
public class DifferentAlgorithmsComparisonTest implements Test {
	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> valuesKRandom = new ArrayList<>();
	private final List<Double> valuesNearest = new ArrayList<>();
	private final List<Double> valuesExtended = new ArrayList<>();
	private final List<Double> values2Opt = new ArrayList<>();

	@Override
	public void test() {

		Euc2dImporter euc2dImporter = new Euc2dImporter();
		for (int i = 0; i < 6; i++) {
			Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));
			graph.setCurrentPath(generatePath());
			NearestNeighborAlgorithm nearestNeighborAlgorithm = new NearestNeighborAlgorithm(graph, new Random().nextInt(100) + 1);
			ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph);
			KRandomAlgorithm kRandomAlgorithm = new KRandomAlgorithm(graph, 10_000);
			Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph);

			Integer[] kRandomSol = kRandomAlgorithm.findSolution();
			Integer[] alg2OptSol = algorithm2Opt.findSolution();
			Integer[] nearestSol = nearestNeighborAlgorithm.findSolution();
			Integer[] extendedNearestSol = extendedNearestNeighborAlgorithm.findSolution();

			System.out.println("Skonczone dla " + i);
			keys.add(i);
			values2Opt.add(graph.pathLength(alg2OptSol));
			valuesKRandom.add(graph.pathLength(kRandomSol));
			valuesNearest.add(graph.pathLength(nearestSol));
			valuesExtended.add(graph.pathLength(extendedNearestSol));
		}

		Plot plot = Plot.create();

		plot.plot().add(keys, valuesKRandom).label("krandom");
		plot.plot().add(keys, values2Opt).label("2opt");
		plot.plot().add(keys, valuesNearest).label("normal");
		plot.plot().add(keys, valuesExtended).label("extended");
		plot.title("different alg comparison for the same data");
		plot.xlabel("tries").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}

	public Integer[] generatePath() {
		Integer[] tab = new Integer[100];
		for (int i = 0; i < 100; i++) {
			tab[i] = i + 1;
		}
		return tab;
	}

	public void testAgainstKnownSolution() throws FileNotFoundException {
		Euc2dImporter importer = new Euc2dImporter();
		importer.importGraph("instances/pr76.tsp");
		importer.importOptimalTour("instances/pr76.opt.tour");


		Euc2dGraph graph = importer.getGraph();
		List<Double> optimalLengths = new ArrayList<>();

		Integer[] tab = new Integer[graph.getNodesCount()];
		for (int i = 0; i < graph.getNodesCount(); i++) {
			tab[i] = i + 1;
		}

		graph.setCurrentPath(tab);

		for (int i = 0; i < 50; i++) {
//			graph.setCurrentPath(generatePath());
			NearestNeighborAlgorithm nearestNeighborAlgorithm = new NearestNeighborAlgorithm(graph, new Random().nextInt(graph.getNodesCount()) + 1);
			ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph);
			KRandomAlgorithm kRandomAlgorithm = new KRandomAlgorithm(graph, 10_000);
			Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph);

			Integer[] kRandomSol = kRandomAlgorithm.findSolution();
			Integer[] alg2OptSol = algorithm2Opt.findSolution();
			Integer[] nearestSol = nearestNeighborAlgorithm.findSolution();
			Integer[] extendedNearestSol = extendedNearestNeighborAlgorithm.findSolution();

			System.out.println("Skonczone dla " + i);
			keys.add(i);
			values2Opt.add(graph.pathLength(alg2OptSol));
			valuesKRandom.add(graph.pathLength(kRandomSol));
			valuesNearest.add(graph.pathLength(nearestSol));
			valuesExtended.add(graph.pathLength(extendedNearestSol));
			optimalLengths.add(graph.pathLength(graph.getOptimalPath()));
		}

		Plot plot = Plot.create();

		plot.plot().add(keys, valuesKRandom).label("krandom");
		plot.plot().add(keys, values2Opt).label("2opt");
		plot.plot().add(keys, valuesNearest).label("normal");
		plot.plot().add(keys, valuesExtended).label("extended");
		plot.plot().add(keys, optimalLengths).label("optimal");
		plot.title("different alg comparison for the same data");
		plot.xlabel("tries").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}
}
