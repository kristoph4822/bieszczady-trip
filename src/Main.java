import java.io.IOException;

import engine.ShortestHamiltonianCycleFinder;
import exceptions.ArgumentException;
import exceptions.InputFileException;
import io.FileWriter;
import io.FileReader;
import engine.ShortestPathFinder;

import util.Graph;
import util.Path;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0)
            throw new ArgumentException("Nie podano argumentów wywołania.");

        if (args.length == 1)
            throw new ArgumentException("Nie podano ID miejsca początkowego.");

        FileReader fr = args.length >= 3 ? new FileReader(args[0], args[2]) : new FileReader(args[0]);

        try {
            fr.readFromFile();
        } catch (IOException e) {
            throw new InputFileException("Nie można otworzyć pliku wejściowego.");
        }

        if (Graph.getAllPlaces().indexOf(args[1]) == -1)
            throw new ArgumentException("Podano nieznane ID miejsca jako argument.");

        fr.getGraph().setStartingPoint(args[1]);

        Graph g = fr.getGraph();
        g = ShortestPathFinder.transformGraph(g, Graph.getAllPlaces(), g.getObligatoryPlaces(), Graph.getPathsWithCost());
        Path shortest = ShortestHamiltonianCycleFinder.getShortestHamiltonianCycle(g);

        try {
            FileWriter.writeToFile(shortest);
        } catch (IOException e) {
            throw new InputFileException("Nie można otworzyć pliku wyjściowego.");
        }
    }


}
