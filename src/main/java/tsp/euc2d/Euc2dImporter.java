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

public class Euc2dImporter extends FileImporter implements InstanceGenerator<List<Euc2d>> {

	@Override
	public void importGraph(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		if (!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);

		ArrayList<Euc2d> coordinatesList = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ");
			try {
				coordinatesList.add(new Euc2d(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
			} catch (Exception ignored) {
				try {
					Integer x = (int) Math.round(Double.parseDouble(splitLine[1]));
					Integer y = (int) Math.round(Double.parseDouble(splitLine[2]));
					coordinatesList.add(new Euc2d(x, y));
				} catch (Exception ignored2) {
					
				}
			}
		}

		scanner.close();
		setGraph(new Euc2dGraph(coordinatesList));
	}

	@Override
	public Euc2dGraph getGraph() {
		return (Euc2dGraph) super.getGraph();
	}

	public List<Euc2d> generateRandomInstances(int numberOfInstances, int maxValue) {
		List<Euc2d> instanceList = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < numberOfInstances; i++)
			instanceList.add(new Euc2d(random.nextInt(maxValue), random.nextInt(maxValue)));

		return instanceList;
	}

}
