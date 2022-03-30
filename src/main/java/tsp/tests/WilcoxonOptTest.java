package tsp.tests;

import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

import tsp.algorithms.Algorithm2Opt;
import tsp.algorithms.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

public class WilcoxonOptTest {
	double ExOPT[] = new double [100];
	double opt[] = new double [100];
	
	public void test() {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		for(int i = 0; i < 100; i++) {
			Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));
			KRandomAlgorithm rand = new KRandomAlgorithm(graph, 10);
			ExtendedNearestNeighborAlgorithm neigh = new ExtendedNearestNeighborAlgorithm(graph);
			Algorithm2Opt alg = new Algorithm2Opt(graph);
			graph.setCurrentPath(neigh.findSolution());
			graph.setOptimalPath(alg.findSolution());
			ExOPT[i] = 0.0;
			graph.setCurrentPath(rand.initRandomArray(graph.getNodesCount()));
			opt[i] = graph.PRD(alg.findSolution());
			System.out.println(opt[i]);
		}
		WilcoxonSignedRankTest test = new WilcoxonSignedRankTest();
		double pLevel = test.wilcoxonSignedRankTest(ExOPT, opt, false);
		System.out.println(pLevel);
	}
}
