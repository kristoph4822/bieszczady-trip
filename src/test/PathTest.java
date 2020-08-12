package test;

import org.junit.jupiter.api.Test;
import util.Path;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    @Test
    void compareTo() {

        Path p1 = new Path(new ArrayList<Integer>(), 10, 130);
        Path p2 = new Path(new ArrayList<Integer>(), 10, 120);
        Path p3 = new Path(new ArrayList<Integer>(), 5, 120);

        assertTrue(p1.compareTo(p2) > 0);
        assertTrue(p2.compareTo(p3) > 0);
    }
}