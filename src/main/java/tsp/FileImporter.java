package tsp;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileImporter {

	private Graph graph;

	public abstract void importGraph(String pathToFile) throws FileNotFoundException;

	public void importOptimalTour(String pathToFile) throws FileNotFoundException {
		if (graph == null) {
			System.out.println("Nie zaimportowano grafu dla tej ścieżki!");
		} else {
			File file = new File(pathToFile);

			if (!file.exists())
				throw new FileNotFoundException();

			Scanner scanner = new Scanner(file);

			Integer[] optimalPath = new Integer[graph.getNodesCount()];
			int counter = 0;
			int lineno = 1;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] splitLine = line.split(" ");
				if (splitLine.length > 1 && lineno > 4) {
					for (int i = 0; i < splitLine.length; i++) {
						try {
							int verticeNo = Integer.parseInt(splitLine[i]);
							optimalPath[counter] = verticeNo;
							counter++;
						} catch (Exception ignored) {
						}
					}
				} else if (lineno > 4){
					try {
						int verticeNo = Integer.parseInt(splitLine[0]);
						if (verticeNo == -1) break;
						optimalPath[counter] = verticeNo;
						counter++;
					} catch (Exception ignored) {
					}
				}
				lineno++;
			}
			scanner.close();

			graph.setOptimalPath(optimalPath);
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
