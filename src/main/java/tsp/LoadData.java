package tsp;

import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.genetic.GeneticAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.algorithms.tabu.TabuAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
//		chooseOption();
		testGenetic();
		
	}

	
//	public static void testCrossover() {
//		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100);
//		List<Pair<Integer[], Integer[]>> parents = geneticAlgorithm.generateParents();
//		for (Pair<Integer[], Integer[]> p : parents) {
//			printSolution(p.getFirst());
//			printSolution(p.getSecond());
//			System.out.println("---------");
//		}
//		List<Integer[]> crossover = geneticAlgorithm.crossover(parents);
//		for (Integer[] c : crossover) {
//			int sum = 0;
//			printSolution(c);
//			for (int x : c) {
//				sum += x;
//			}
//			System.out.println(sum);
//		}
//	}
	
	public static void testGenetic() throws FileNotFoundException {
		FileImporter lowerDiagImporter = new Euc2dImporter();
		lowerDiagImporter.importGraph("instances/eil101.tsp");
//		lowerDiagImporter.importOptimalTour("instances/gr120.opt.tour");
		Graph graph1 = lowerDiagImporter.getGraph();
		graph1.setOptimalPathLength(629.0);
		GeneticAlgorithm alg = new GeneticAlgorithm(graph1, 50, 0.20, 1.0, 1, 2, 1, 1000*300, 100, true);
		alg.findSolution();
	}
	

	private static void chooseOption() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Wczytaj rodzaj problemu:");
		System.out.println("1 - euc2d");
		System.out.println("2 - atsp matrix");
		System.out.println("3 - tsp matrix");

		String problemNumber = scanner.nextLine();
		FileImporter fileImporter;

		switch (problemNumber) {
			case "1" -> fileImporter = new Euc2dImporter();
			case "2" -> fileImporter = new ATSPMatrixImporter();
			case "3" -> {
				System.out.println("Chcesz wczytac full matrix, czy lower diag?");
				System.out.println("1 - lower diag");
				System.out.println("2 - full matrix");

				String choose = scanner.nextLine();
				if (choose.equals("1"))
					fileImporter = new LowerDiagRowImporter();
				else if (choose.equals("2"))
					fileImporter = new TSPMatrixImporter();
				else {
					System.out.println("bledny wybor!");
					return;
				}
			}
			default -> {
				System.out.println("blad");
				return;
			}
		}

		System.out.println("Aby wczytac instancje, wybierz 1.");
		System.out.println("Aby wygenerowac instancje, wybierz 2.");

		String num = scanner.nextLine();
		if (num.equals("1")) {
			System.out.println("Podaj sciezke do pliku");
			String path = scanner.nextLine();
			fileImporter.importGraph(path);

			System.out.println("Chcesz zaimportowac optymalna sciezke?");
			System.out.println("1 - tak");
			System.out.println("2 - nie");
			String choose = scanner.nextLine();
			if (choose.equals("1")) {

				System.out.println("Chcesz wprowadzic optymalna dlugosc recznie, czy korzystajac z pliku?");
				System.out.println("1 - recznie");
				System.out.println("2 - z pliku");
				String optPathLengthChoose = scanner.nextLine();

				if (optPathLengthChoose.equals("1")) {
					System.out.println("Podaj optymalna dlugosc");
					String optPathLength = scanner.nextLine();
					fileImporter.getGraph().setOptimalPathLength(Double.parseDouble(optPathLength));
				} else if (optPathLengthChoose.equals("2")) {
					System.out.println("Podaj sciezke do optymalnego rozwiazania");
					String pathToTour = scanner.nextLine();
					fileImporter.importOptimalTour(pathToTour);
				}

			}

		} else if (num.equals("2")) {
			System.out.println("Podaj rozmiar grafu");
			String numOfInstances = scanner.nextLine();
			System.out.println("Podaj maksymalna wartosc");
			String maxValue = scanner.nextLine();
			if (fileImporter instanceof Euc2dImporter) {
				Graph graph = new Euc2dGraph(((Euc2dImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			} else if (fileImporter instanceof ATSPMatrixImporter) {
				Graph graph = new MatrixGraph(((ATSPMatrixImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			} else {
				Graph graph = new MatrixGraph(((TSPMatrixImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			}
		} else {
			System.out.println("Zly numer");
			return;
		}

		System.out.println("Wybierz algorytm");
		System.out.println("1 - tabu 2opt");
		System.out.println("2 - tabu 2opt with Aspiration");
		System.out.println("3 - tabu 2opt with VNS");

		TabuAlgorithm algorithm;

		String alg = scanner.nextLine();

		System.out.println("Co ma byc kryterium stopu?");
		System.out.println("1 - ilosc iteracji");
		System.out.println("2 - czas dzialania algorytmu");

		String stop = scanner.nextLine();

		String iterations = null;
		String millis = null;
		if ("1".equals(stop)) {
			System.out.println("Podaj liczbe maksymalnych iteracji");
			iterations = scanner.nextLine();
		} else if ("2".equals(stop)) {
			System.out.println("Podaj ilosc ms, po jakiej program ma sie skonczyc");
			millis = scanner.nextLine();
		}

		System.out.println("Podaj dlugosc listy tabu");
		String tenure = scanner.nextLine();

		System.out.println("Podaj maksymalna ilosc iteracji dla stagnacji");
		String maxStagnationCounter = scanner.nextLine();

		Integer maxIterations = iterations == null ? null : Integer.parseInt(iterations);
		Integer maxMillis = millis == null ? null : Integer.parseInt(millis);

		String neighbourhoodType = "1";

		System.out.println("Podaj czynnik, przez ktory chcesz pomnozyc (domyslnie 0)");

		String factor = scanner.nextLine();

		if (alg.equals("1") || alg.equals("2")) {
			// 1 - invert
			// 2 - swap
			// 3 - insert
			System.out.println("Podaj typ sasiedzctwa:");
			System.out.println("1 - invert");
			System.out.println("2 - swap");
			System.out.println("3 - insert");
			neighbourhoodType = scanner.nextLine();
		}


		fileImporter.getGraph().setCurrentPath(new ExtendedNearestNeighborAlgorithm(fileImporter.getGraph()).findSolution());

		switch (alg) {
			case "1" -> algorithm = new Tabu2Opt(fileImporter.getGraph(), maxMillis, maxIterations, stop.equals("1"), Integer.parseInt(tenure), Integer.parseInt(maxStagnationCounter), Integer.parseInt(neighbourhoodType));
			case "2" -> algorithm = new Tabu2OptWithAspiration(fileImporter.getGraph(), maxMillis, maxIterations, stop.equals("1"), Integer.parseInt(tenure), Integer.parseInt(maxStagnationCounter), Integer.parseInt(neighbourhoodType));
			case "3" -> algorithm = new Tabu2OptWithVNS(fileImporter.getGraph(), maxMillis, maxIterations, stop.equals("1"), Integer.parseInt(tenure), Integer.parseInt(maxStagnationCounter));
			default -> {
				System.out.println("blad");
				return;
			}
		}

		algorithm.setStagnationMultiplier(Double.parseDouble(factor));

		System.out.println("Algorytm sie uruchamia..............");

		Integer[] sol = algorithm.findSolution();

		printSolution(sol);

		double pathLength = fileImporter.getGraph().pathLength(sol);
		System.out.println("Dlugosc sciezki: " + pathLength);
		if (fileImporter.getGraph().getOptimalPath() != null) {
			System.out.println("\nOptymalna sciezka : ");
			printSolution(fileImporter.getGraph().getOptimalPath());
			System.out.println("Dlugosc optymalnej sciezki: " + fileImporter.getGraph().getOptimalPathLength());
			System.out.println("PRD: " + fileImporter.getGraph().PRD(sol));
		}
		scanner.close();
	}

	private static void printSolution(Integer[] sol) {
		System.out.print("Sciezka: ");
		for (Integer i : sol) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
