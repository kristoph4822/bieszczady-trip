package engine;

import util.Cost;
import util.Graph;
import util.Path;

import java.util.ArrayList;
import java.util.Collections;

public class ShortestHamiltonianCycleFinder {

    public static Path getShortestHamiltonianCycle(Graph g) {

        ArrayList<Integer> places = new ArrayList<>();

        if (g.getObligatoryPlaces().isEmpty()) {

            for (String s : Graph.getAllPlaces()) {
                places.add(Graph.getAllPlaces().indexOf(s));
            }

        } else {

            places.addAll(g.getObligatoryPlaces());
        }

        places.remove((Integer) g.getStartingPoint());

        ArrayList<Path> pathList = new ArrayList<>();
        permute(g, places, places.size(), pathList);

        return getMin(pathList);
    }


    private static void swap(ArrayList<Integer> course, int i, int j) {

        int tmp = course.get(i);
        course.set(i, course.get(j));
        course.set(j, tmp);

    }


    private static void permute(Graph g, ArrayList<Integer> course, int n, ArrayList<Path> pathList) {

        if (n == 1) {
            pathList.add(getPath(g, course));

        } else {
            for (int i = 0; i < n; i++) {
                permute(g, course, n - 1, pathList);

                if (n % 2 == 1) {
                    swap(course, 0, n - 1);

                } else {
                    swap(course, i, n - 1);
                }
            }
        }
    }

    private static Path getPath(Graph g, ArrayList<Integer> course) {

        int j = 1;
        int time = 0;
        int cost;

        ArrayList<Integer> course2 = new ArrayList<>();
        ArrayList<Cost> costs = new ArrayList<>();

        course2.add(g.getStartingPoint());
        course2.addAll(course);
        course2.add(g.getStartingPoint());

        ArrayList<Integer> finalCourse = new ArrayList<>();

        for (int i = 0; i < course2.size() - 1; i++) {

            int s = course2.get(i);
            int e = course2.get(j);

            time += g.getGraph()[s][e].getTime();

            int h = 1;
            ArrayList<Integer> course3 = g.getGraph()[s][e].getCourse();

            for (int k = 0; k < course3.size() - 1; k++) {

                int sc = course3.get(k);
                int ec = course3.get(h);
                Cost c1 = new Cost(sc, ec);

                if (!costs.contains(c1) && Graph.getPathsWithCost().indexOf(c1) != -1) {
                    int price = Graph.getPathsWithCost().get(Graph.getPathsWithCost().indexOf(c1)).getCost();
                    c1.setCost(price);
                    costs.add(c1);
                }

                h++;
            }

            finalCourse.addAll(g.getGraph()[s][e].getCourse());

            if (j < course2.size() - 1) //usuń końcówkę by uniknąć powtórzeń miejsc
                finalCourse.remove(finalCourse.size() - 1);
            j++;
        }

        cost = sumCost(costs);

        return new Path(finalCourse, cost, time);
    }

    private static Path getMin(ArrayList<Path> pathList) {
        return Collections.min(pathList);
    }

    private static int sumCost(ArrayList<Cost> costs) {

        int sum = 0;
        for (Cost c : costs) {
            sum += c.getCost();
        }
        return sum;
    }
}


