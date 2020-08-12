package test;

import engine.ShortestPathFinder;
import io.FileReader;
import org.junit.jupiter.api.Test;
import util.Cost;
import util.Graph;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathFinderTest {

    @Test
    void transformGraph() throws IOException {

        FileReader fr = new FileReader("test/configTest6.txt");
        fr.readFromFile();
        Graph g = fr.getGraph();
        g = ShortestPathFinder.transformGraph(g, Graph.getAllPlaces(), g.getObligatoryPlaces(), Graph.getPathsWithCost());

        assertEquals(g.getGraph()[2][0].getTime(), 270);
        assertEquals(g.getGraph()[2][0].getCourse().size(), 3);
    }

}