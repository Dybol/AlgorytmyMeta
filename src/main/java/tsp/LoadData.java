package tsp;

import tsp.euc2d.Euc2dImporter;

import java.io.FileNotFoundException;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		euc2dImporter.importFile("pr107.tsp");

		//TODO: Jak przechowywac rozne dane?
		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
	}
}
