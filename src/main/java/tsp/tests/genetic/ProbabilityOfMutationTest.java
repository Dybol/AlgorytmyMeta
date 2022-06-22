package tsp.tests.genetic;

import com.github.sh0nk.matplotlib4j.Plot;
import tsp.FileImporter;
import tsp.Graph;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.genetic.GeneticAlgorithm;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.tests.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProbabilityOfMutationTest implements Test {

	private final List<Double> keys = new ArrayList<>();
	private final List<Double> valuesGeneticsMutationSwap = new ArrayList<>();
	private final List<Double> valuesGeneticsMutationInvert = new ArrayList<>();
	private final List<Double> valuesGeneticsMutationInsert = new ArrayList<>();

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

		List<Double> valuesOpt = new ArrayList<>();
		double opt = graph.pathLength(graph.getOptimalPath());

		for (double i = 1.0; i <= 10; i += 1) {
			double avgPathLengthSwap = 0;
			double avgPathLengthInvert = 0;
			double avgPathLengthInsert = 0;
			for (int j = 0; j < 5; j++) {
				GeneticAlgorithm geneticAlgorithmMutationSwap = new GeneticAlgorithm(graph, 50, i, 1,
						2, 2, 1, 5000, null, true);
				//same as type of neighborhood for memetics
				avgPathLengthSwap += graph.pathLength(geneticAlgorithmMutationSwap.findSolution());

				GeneticAlgorithm geneticAlgorithmMutationInvert = new GeneticAlgorithm(graph, 50, i, 1,
						2, 1, 1, 5000, null, true);
				avgPathLengthInvert += graph.pathLength(geneticAlgorithmMutationInvert.findSolution());

				GeneticAlgorithm geneticAlgorithmMutationInsert = new GeneticAlgorithm(graph, 50, i, 1,
						2, 3, 1, 5000, null, true);
				avgPathLengthInsert += graph.pathLength(geneticAlgorithmMutationInsert.findSolution());
			}

			keys.add(i);
			valuesGeneticsMutationSwap.add(avgPathLengthSwap / 5);
			valuesGeneticsMutationInvert.add(avgPathLengthInvert / 5);
			valuesGeneticsMutationInsert.add(avgPathLengthInsert / 5);
			System.out.println("End dla i = " + i);
			valuesOpt.add(opt);
		}

		System.out.println(keys);
		System.out.println(valuesGeneticsMutationSwap);
		System.out.println(valuesGeneticsMutationInvert);
		System.out.println(valuesGeneticsMutationInsert);

		Plot plot = Plot.create();

		plot.plot().add(keys, valuesGeneticsMutationSwap).label("mutation swap");
		plot.plot().add(keys, valuesGeneticsMutationInvert).label("mutation invert");
		plot.plot().add(keys, valuesGeneticsMutationInsert).label("mutation insert");
		plot.plot().add(keys, valuesOpt).label("optimum");
		plot.title("different mutation probabilities");
		plot.xlabel("probability of mutation").build();
		plot.ylabel("best path length");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
	}
}
