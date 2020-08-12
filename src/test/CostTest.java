package test;

import org.junit.jupiter.api.Test;
import util.Cost;

import static org.junit.jupiter.api.Assertions.*;

class CostTest {

    @Test
    void testEquals() {

        Cost c1 = new Cost(1, 2);
        Cost c2 = new Cost(1, 2, 30);
        Cost c3 = new Cost(2, 1);

        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c3));
        assertTrue(c2.equals(c3));
    }
}