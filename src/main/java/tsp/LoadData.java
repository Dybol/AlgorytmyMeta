package tsp;

import tsp.algorithms.Algorithm;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.basic.KRandomAlgorithm;
import tsp.algorithms.basic.NearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
		test();
//		chooseOption();
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
				System.out.println("Podaj sciezke do optymalnego rozwiazania");
				String pathToTour = scanner.nextLine();
				fileImporter.importOptimalTour(pathToTour);
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
		System.out.println("1 - krandom");
		System.out.println("2 - basic neighbor");
		System.out.println("3 - extended neighbor");
		System.out.println("4 - 2opt");

		Algorithm algorithm;

		String alg = scanner.nextLine();

		switch (alg) {
			case "1" -> {
				System.out.println("Podaj k");
				String k = scanner.nextLine();
				algorithm = new KRandomAlgorithm(fileImporter.getGraph(), Integer.parseInt(k));
			}
			case "2" -> {
				System.out.println("Podaj punkt startowy");
				String start = scanner.nextLine();
				algorithm = new NearestNeighborAlgorithm(fileImporter.getGraph(), Integer.parseInt(start));
			}
			case "3" -> algorithm = new ExtendedNearestNeighborAlgorithm(fileImporter.getGraph());
			case "4" -> {
				System.out.println("Jak chcesz wylonic poczatkowa sciezke?");
				System.out.println("1 - losowe");
				System.out.println("2 - najblizszy sasiad (rozszerzony)");
				String choose = scanner.nextLine();
				switch (choose) {
					case "1" -> {
						System.out.println("Podaj k");
						String k = scanner.nextLine();
						KRandomAlgorithm kRandom = new KRandomAlgorithm(fileImporter.getGraph(), Integer.parseInt(k));
						fileImporter.getGraph().setCurrentPath(kRandom.findSolution());
					}
					case "2" -> {
						ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(fileImporter.getGraph());
						fileImporter.getGraph().setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
					}
				}
				algorithm = new Algorithm2Opt(fileImporter.getGraph());
			}
			default -> {
				System.out.println("blad");
				return;
			}
		}

		Integer[] sol = algorithm.findSolution();

		printSolution(sol);

		double pathLength = fileImporter.getGraph().pathLength(sol);
		System.out.println("Dlugosc sciezki: " + pathLength);
		if (fileImporter.getGraph().getOptimalPath() != null) {
			System.out.println("\nOptymalna sciezka : ");
			printSolution(fileImporter.getGraph().getOptimalPath());
			System.out.println("Dlugosc optymalnej sciezki: " + fileImporter.getGraph().pathLength(fileImporter.getGraph().getOptimalPath()));
			System.out.println("PRD: " + fileImporter.getGraph().PRD(sol));
		} else {
			ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(fileImporter.getGraph());
			fileImporter.getGraph().setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
			algorithm = new Algorithm2Opt(fileImporter.getGraph());

			fileImporter.getGraph().setOptimalPath(algorithm.findSolution());
			System.out.println("Dlugosc sub-optymalnej sciezki "
					+ "(wyznaczonej przez algorytm 2-OPT, \ndla którego początkowa ścieżka została wyznaczona"
					+ "przez rozszerzony algorytm najbliższego sąsiada): " + fileImporter.getGraph().pathLength(fileImporter.getGraph().getOptimalPath()));
			System.out.println("PRD (wyznaczone, używająć sub-optimum): " + fileImporter.getGraph().PRD(sol));
		}
		scanner.close();
	}

	private static void test() throws FileNotFoundException {
		FileImporter importer = new Euc2dImporter();
		importer.importGraph("instances/pr124.tsp");
		Graph graph = importer.getGraph();
		ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(importer.getGraph());
		graph.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph);
		Integer[] solution2Opt = algorithm2Opt.findSolution();
		graph.setCurrentPath(solution2Opt);
		System.out.println(graph.pathLength(solution2Opt));

		System.out.println("-----------------");

//		Tabu2OptWithVNS alg = new Tabu2OptWithVNS(graph, 50000, 7, 100);
//		graph.setCurrentPath(alg.findSolution());
//		printSolution(graph.getCurrentPath());
//		System.out.println(graph.pathLength(graph.getCurrentPath()));
//
//		graph.setCurrentPath(solution2Opt);

		System.out.println("With time: ");
		Tabu2OptWithVNS algWithTime = new Tabu2OptWithVNS(graph, false, null, 3 * 1000, 7, 100);
		graph.setCurrentPath(algWithTime.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		System.out.println("With counter: ");
		Tabu2OptWithVNS algWithCounter = new Tabu2OptWithVNS(graph, true, 500, null, 7, 100);
		graph.setCurrentPath(algWithCounter.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		System.out.println("end");

		Tabu2OptWithAspiration alg2 = new Tabu2OptWithAspiration(graph, 500, 7, 1000, 1);
		graph.setCurrentPath(alg2.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		System.out.println("---------------");

		Tabu2Opt alg3 = new Tabu2Opt(graph, 50000, 11, 1000, 1);
		graph.setCurrentPath(alg3.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		Tabu2Opt alg4 = new Tabu2Opt(graph, 50000, 11, 0.2, 500, 1);
		graph.setCurrentPath(alg4.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		Tabu2OptWithVNS alg5 = new Tabu2OptWithVNS(graph, 50000, 7, 1000);
		graph.setCurrentPath(alg5.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		Tabu2OptWithVNS alg6 = new Tabu2OptWithVNS(graph, 50000, 7, 0.2, 500);
		graph.setCurrentPath(alg6.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		Tabu2OptWithVNS alg7 = new Tabu2OptWithVNS(graph, 50000, 11, 1000);
		graph.setCurrentPath(alg7.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

		graph.setCurrentPath(solution2Opt);

		Tabu2OptWithVNS alg8 = new Tabu2OptWithVNS(graph, 50000, 11, 0.2, 500);
		graph.setCurrentPath(alg8.findSolution());
		printSolution(graph.getCurrentPath());
		System.out.println(graph.pathLength(graph.getCurrentPath()));

	}

	private static void printSolution(Integer[] sol) {
		System.out.print("Sciezka: ");
		for (Integer i : sol) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
