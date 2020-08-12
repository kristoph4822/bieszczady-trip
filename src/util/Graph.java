package util;

import java.util.ArrayList;

public class Graph {

    private static int MAX_PLACES = 40;

    private Path[][] graph;
    private int startingPoint;

    private static ArrayList<String> allPlaces;
    private ArrayList<Integer> obligatoryPlaces;
    private static ArrayList<Cost> pathsWithCost;

    public Graph() {
        graph = new Path[MAX_PLACES][MAX_PLACES];
        allPlaces = new ArrayList<>();
        obligatoryPlaces = new ArrayList<>();
        startingPoint = -1;
        pathsWithCost = new ArrayList<>();
    }

    public void setPath(Path p) {
        graph[p.getStart()][p.getEnd()] = p;
    }

    public Path[][] getGraph() {
        return graph;
    }

    public static ArrayList<String> getAllPlaces() {
        return allPlaces;
    }

    public ArrayList<Integer> getObligatoryPlaces() {
        return obligatoryPlaces;
    }

    public void setStartingPoint(String start) {
        this.startingPoint = allPlaces.indexOf(start);
        if (!obligatoryPlaces.isEmpty() && obligatoryPlaces.indexOf(startingPoint) == -1) {
            obligatoryPlaces.add(startingPoint);
        }
    }

    public void setStartingPoint(int i) {
        this.startingPoint = i;
    }

    public int getStartingPoint() {
        return startingPoint;
    }

    public static ArrayList<Cost> getPathsWithCost() {
        return pathsWithCost;
    }

    public static int getMaxPlaces() {
        return MAX_PLACES;
    }


}
