package tsp.matrix;

import tsp.FileImporter;
import tsp.matrix.model.MatrixGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MatrixImporter implements FileImporter {

	private MatrixGraph graph;
	private Integer[][] cordTab;

	@Override
	public void importGraph(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		List<String> allLines = new ArrayList<>();

		if(!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine())
			allLines.add(scanner.nextLine());

		int dimension = Integer.parseInt(allLines.get(3).split(" ")[1]);

		cordTab = new Integer[dimension][dimension];

		List<Integer> allCoordinates = new ArrayList<>();

		//dodajemy wszystko do jednej listy zeby nie bylo problemu z nowymi liniami i splitowaniem
		for(String line: allLines.subList(7, allLines.size())) {
			String[] horizontalCords = line.split(" ");
			for(String cord: horizontalCords) {
				try {
					allCoordinates.add(Integer.parseInt(cord));
					//problemy ze spacjami
				} catch (Exception ignored){}
			}
		}

		int xCounter = 0;
		int yCounter = 0;
		for(Integer cord: allCoordinates) {
			cordTab[yCounter][xCounter] = cord;
			xCounter++;
			if(xCounter == dimension) {
				xCounter = 0;
				yCounter++;
				if(yCounter == dimension) break;
			}
		}
		graph = new MatrixGraph(cordTab);

		System.out.println("Zaimportowano z sukcesem!");
		printMatrix(cordTab, dimension);
		System.out.println("---------------------------");
	}

	@Override
	public void importOptimalTour(String pathToFile) throws FileNotFoundException {
		if(graph == null) {
			System.out.println("Nie zaimportowano grafu dla tej ścieżki!");
		}
		else {
			File file = new File(pathToFile);

			if(!file.exists())
				throw new FileNotFoundException();

			Scanner scanner = new Scanner(file);

			Integer[] optimalPath = new Integer[graph.getNodesCount()];
			int counter = 0;

			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] splitLine = line.split(" ");
				if(splitLine.length > 1) {
					for(int i = 0; i < splitLine.length; i++) {
						try {
							int verticeNo = Integer.parseInt(splitLine[0]);
							optimalPath[counter] = verticeNo;
							counter++;
						} catch (Exception ignored) {
						}
					}
				}
				else {
					try {
						int verticeNo = Integer.parseInt(splitLine[0]);
						if(verticeNo == -1) break;
						optimalPath[counter] = verticeNo;
						counter++;
					} catch (Exception ignored) {
					}
				}
			}
			scanner.close();

			graph.setOptimalPath(optimalPath);
		}	
	}
	
	public MatrixGraph getGraph() {
		return graph;
	}

	public void printMatrix(Integer[][] table, int dimension) {
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
}
