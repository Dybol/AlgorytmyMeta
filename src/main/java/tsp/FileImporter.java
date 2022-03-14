package tsp;


import java.io.FileNotFoundException;

public interface FileImporter {
	void importGraph(String pathToFile) throws FileNotFoundException;
	void importOptimalTour(String pathToFile) throws FileNotFoundException;
}
