package test;

import engine.ShortestHamiltonianCycleFinder;
import engine.ShortestPathFinder;
import io.FileReader;
import org.junit.jupiter.api.Test;
import util.Graph;
import util.Path;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestHamiltonianCycleFinderTest {

    @Test
    void getShortestHamiltonianCycle1() throws IOException {

        FileReader fr = new FileReader("test/configTest6.txt");
        fr.readFromFile();
        Graph g = fr.getGraph();
        g.setStartingPoint(Graph.getAllPlaces().get(0)); //punkt statowy to A
        g = ShortestPathFinder.transformGraph(g, Graph.getAllPlaces(), g.getObligatoryPlaces(), Graph.getPathsWithCost());
        Path shortest = ShortestHamiltonianCycleFinder.getShortestHamiltonianCycle(g);

        assertEquals(shortest.getTime(), 840);
        assertEquals(shortest.getCost(), 1);
    }
}