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

		ArrayList<Euc2d> coordinatesList = new ArrayList<Euc2d>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ");
			try {
				coordinatesList.add(new Euc2d(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
			} catch (Exception ignored) {
			}
		}
		scanner.close();
		graph = new Euc2dGraph(coordinatesList);
	}

	public Euc2dGraph getGraph() {
		return graph;
		System.out.println(coordinatesList);
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
		File file = new File(pathToFile);

		if(!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);

		int[] optimal_path = new int[graph.getNodesCount()];
		int counter = 0;

		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ");
			try {
				int vertice_no = Integer.parseInt(splitLine[0]);
				if(vertice_no == -1) break;
				optimal_path[counter] = vertice_no;
				counter++;
			} catch (Exception ignored) {
			}
		}
		scanner.close();

		graph.setOptimalPath(optimal_path);
	}
}
