package tsp.euc2d;

import tsp.FileImporter;
import tsp.InstanceGenerator;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Euc2dImporter implements FileImporter, InstanceGenerator<List<Euc2d>> {

	private Euc2dGraph graph;

	@Override
	public void importGraph(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		if(!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);

		ArrayList<Euc2d> coordinatesList = new ArrayList<>();

		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ");
			try {
				coordinatesList.add(new Euc2d(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
			} catch (Exception ignored) {}
		}

		scanner.close();
		graph = new Euc2dGraph(coordinatesList);
	}

	public Euc2dGraph getGraph() {
		return graph;
	}

	public List<Euc2d> generateRandomInstances(int numberOfInstances, int maxValue) {
		List<Euc2d> instanceList = new ArrayList<>();
		Random random = new Random();

		for(int i = 0 ; i < numberOfInstances; i ++)
			instanceList.add(new Euc2d(random.nextInt(maxValue), random.nextInt(maxValue)));

		return instanceList;
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
}
