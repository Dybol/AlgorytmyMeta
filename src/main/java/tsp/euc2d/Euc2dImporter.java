package tsp.euc2d;

import tsp.FileImporter;
import tsp.InstanceGenerator;
import tsp.euc2d.model.Euc2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Euc2dImporter implements FileImporter, InstanceGenerator<Euc2d> {

	private final List<Euc2d> coordinatesList = new ArrayList<>();

	@Override
	public void importFile(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		if(!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);

		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(" ");
			try {
				coordinatesList.add(new Euc2d(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
			} catch (Exception ignored) {
			}
		}
	}

	public List<Euc2d> generateRandomInstances(int numberOfInstances, int maxValue) {
		List<Euc2d> instanceList = new ArrayList<>();
		Random random = new Random();

		for(int i = 0 ; i < numberOfInstances; i ++)
			instanceList.add(new Euc2d(random.nextInt(maxValue), random.nextInt(maxValue)));

		return instanceList;
	}
}
