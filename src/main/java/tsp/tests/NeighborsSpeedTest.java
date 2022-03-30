package tsp.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

import tsp.algorithms.Algorithm2Opt;
import tsp.algorithms.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.KRandomAlgorithm;
import tsp.algorithms.NearestNeighborAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class NeighborsSpeedTest {

	private final List<Integer> keysEuclid = new ArrayList<>();
	private final List<Double> valuesEuclidNormal = new ArrayList<>();
	private final List<Double> valuesEuclidExtended = new ArrayList<>();
	private final List<Integer> keysTSP = new ArrayList<>();
	private final List<Double> valuesTSPNormal = new ArrayList<>();
	private final List<Double> valuesTSPExtended = new ArrayList<>();
	
	public void normalVsExtended() throws IOException {/*
		FileWriter fileWriter = new FileWriter("wyniki_porowananie_sasiadow_euclid.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph; 
		for(int n = 50; n <= 500; n+=25) {
			long timeElapsedExNeigh = 0;
			long timeElapsedNeigh = 0;
			
			for(int r = 0; r < 100; r++) {
				graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(n, n));
				ExtendedNearestNeighborAlgorithm neighborEx = new ExtendedNearestNeighborAlgorithm(graph);
				NearestNeighborAlgorithm neighbor = new NearestNeighborAlgorithm(graph,1);
				Instant start = Instant.now();
				neighbor.findSolution();
				Instant finish = Instant.now();
				timeElapsedNeigh += Duration.between(start, finish).toNanos();
				start = Instant.now();
				neighborEx.findSolution();
				finish = Instant.now();
				timeElapsedExNeigh += Duration.between(start, finish).toMillis();
			}

			double timeElapsedPerOneExNeigh = (double)timeElapsedExNeigh / 100.0;
			double timeElapsedPerOneNeigh = (double)timeElapsedNeigh / 100000000.0;
			keysEuclid.add(n);
			valuesEuclidExtended.add(timeElapsedPerOneExNeigh);
			valuesEuclidNormal.add(timeElapsedPerOneNeigh);
			printWriter.printf("%d; %f; %f\n", n, timeElapsedPerOneExNeigh, timeElapsedPerOneNeigh);
			System.out.println(n);
		}
		printWriter.close();
		fileWriter.close();
		Plot plot = Plot.create();

		plot.plot().add(keysEuclid, valuesEuclidExtended).label("extended");
		plot.plot().add(keysEuclid, valuesEuclidNormal).label("normal, starting node = 1");
		plot.title("Average time of finding path by extended and normal NearestNeighborAlgorithm, for a random Euc2d graph");
		plot.xlabel("n - number of nodes in a graph").build();
		plot.ylabel("time [ms] (avg from 100 random graphs)");
		plot.legend();
		
		try {
			plot.show();
		} catch (Throwable ignored) {
		}

		*/
		
		FileWriter fileWriterTSP = new FileWriter("wyniki_porownanie_sasiadow_TSP.txt");
	    PrintWriter printWriterTSP = new PrintWriter(fileWriterTSP);
		TSPMatrixImporter TSPImporter = new TSPMatrixImporter();
		MatrixGraph graphTSP;
		for(int n = 50; n <= 500; n+=25) {
			long timeElapsedExNeigh = 0;
			long timeElapsedNeigh = 0;

			for(int r = 0; r < 100; r++) {
				graphTSP = new MatrixGraph(TSPImporter.generateRandomInstances(n, n));
				ExtendedNearestNeighborAlgorithm neighborEx = new ExtendedNearestNeighborAlgorithm(graphTSP);
				NearestNeighborAlgorithm neighbor = new NearestNeighborAlgorithm(graphTSP,1);
				Instant start = Instant.now();
				neighbor.findSolution();
				Instant finish = Instant.now();
				timeElapsedNeigh += Duration.between(start, finish).toNanos();
				start = Instant.now();
				neighborEx.findSolution();
				finish = Instant.now();
				timeElapsedExNeigh += Duration.between(start, finish).toMillis();
			}

			double timeElapsedPerOneExNeigh = (double)timeElapsedExNeigh / 100.0;
			double timeElapsedPerOneNeigh = (double)timeElapsedNeigh / 100000000.0;
			keysTSP.add(n);
			valuesTSPExtended.add(timeElapsedPerOneExNeigh);
			valuesTSPNormal.add(timeElapsedPerOneNeigh);
			printWriterTSP.printf("%d; %f; %f\n", n, timeElapsedPerOneExNeigh, timeElapsedPerOneNeigh);
			System.out.println(n);
		}
		Plot plotTSP = Plot.create();

		plotTSP.plot().add(keysTSP, valuesTSPExtended).label("extended");
		plotTSP.plot().add(keysTSP, valuesTSPNormal).label("normal, starting node = 1");
		plotTSP.title("Average time of finding path by extended and normal NearestNeighborAlgorithm, for a random TSPMatrix graph");
		plotTSP.xlabel("n - number of nodes in a graph").build();
		plotTSP.ylabel("time [ms] (avg from 100 random graphs)");
		plotTSP.legend();
		printWriterTSP.close();
		fileWriterTSP.close();
		try {
			plotTSP.show();
		} catch (Throwable ignored) {
		}

	}
}
