package util;

import java.util.ArrayList;

public class Path implements Comparable<Path> {

    private ArrayList<Integer> course;
    private int cost;
    private int time;

    public Path(ArrayList<Integer> course, int cost, int time) {
        this.course = course;
        this.cost = cost;
        this.time = time;
    }

    @Override
    public int compareTo(Path p) {
        if (this.time == p.time) {
            return Integer.compare(this.cost, p.cost);
        }
        return this.time - p.time;
    }

    int getStart() {
        return course.get(0);
    }

    int getEnd() {
        return course.get(course.size() - 1);
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Integer> getCourse() {
        return course;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < course.size(); i++) {
            sb.append(Graph.getAllPlaces().get(course.get(i)));
            sb.append(i == course.size() - 1 ? "\n" : " -> ");
        }
        sb.append("Czas: ").append(time / 60).append(" godzin ").append(time % 60).append(" minut\n");
        sb.append("Koszt: ").append(cost).append(" zł");

        return sb.toString();
    }
}


