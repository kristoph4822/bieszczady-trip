package util;

public class Cost {

    private int start;
    private int end;
    private int cost;

    public Cost(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Cost(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (!(object instanceof Cost))
            return false;
        if (this == object)
            return true;

        Cost c = (Cost) object;
        if (this.start == c.end && this.end == c.start)
            return true;
        return this.start == c.start && this.end == c.end;

    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public int getCost() {
        return cost;
    }

}
