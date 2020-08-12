package engine;

import exceptions.InputFileException;
import util.Cost;
import util.Graph;
import util.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ShortestPathFinder {

    public static Graph transformGraph(Graph g, ArrayList<String> allPlaces, ArrayList<Integer> obPlaces, ArrayList<Cost> costs) {

        Graph newGraph = new Graph();
        newGraph.setStartingPoint(g.getStartingPoint());
        Graph.getAllPlaces().addAll(allPlaces);
        newGraph.getObligatoryPlaces().addAll(obPlaces);
        Graph.getPathsWithCost().addAll(costs);

        ArrayList<Integer> places = new ArrayList<>();

        if (g.getObligatoryPlaces().isEmpty()) {

            for (String s : Graph.getAllPlaces())
                places.add(Graph.getAllPlaces().indexOf(s));
        } else {
            places = g.getObligatoryPlaces();
        }

        for (int start : places) {

            ArrayList<Integer> q = new ArrayList<>();

            for (String s : Graph.getAllPlaces()) {
                q.add(Graph.getAllPlaces().indexOf(s));
            }

            int len = Graph.getAllPlaces().size();

            int[] d = new int[len];
            for (int i = 0; i < d.length; i++) {
                d[i] = i == start ? 0 : Integer.MAX_VALUE;
            }

            int[] p = new int[len];
            Arrays.fill(p, -1);

            while (!q.isEmpty()) {

                int closestPoint = -1;
                int min = Integer.MAX_VALUE;

                for (int i = 0; i < d.length; i++) {
                    if (d[i] < min && q.indexOf(i) != -1) {
                        min = d[i];
                        closestPoint = i;
                    }
                }

                if (closestPoint == -1)
                    throw new InputFileException("Błąd: wykryto niedostępne miejsca. Muszą istnieć połączenia między wszytskimi miejscami.");

                q.remove((Integer) closestPoint);


                for (int i = 0; i < len; i++) {
                    if (g.getGraph()[closestPoint][i] == null || q.indexOf(i) == -1)
                        continue;

                    if (d[i] > d[closestPoint] + g.getGraph()[closestPoint][i].getTime()) {
                        d[i] = d[closestPoint] + g.getGraph()[closestPoint][i].getTime();
                        p[i] = closestPoint;
                    }
                }

            }

            for (int i : places) {
                if (i == start)
                    continue;

                int cost = 0; //i tak liczymy koszty od nowa
                newGraph.setPath(new Path(findPath(p, start, i), cost, d[i]));
            }

        }

        return newGraph;
    }

    private static ArrayList<Integer> findPath(int[] p, int start, int end) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(end);
        int i = end;
        while (i != start) {
            i = p[i];
            path.add(i);
        }
        Collections.reverse(path);
        return path;
    }

}
