import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Road_STUDENT_Test {
    private Road r1, r2, r3, r4;
    private Town t1, t2, t3, t4;

    @Before
    public void setUp() throws Exception {
        t1 = new Town("Rosehill");
        t2 = new Town("Greenland");
        t3 = new Town("Brookvale");
        t4 = new Town("Fairview");
        r1 = new Road(t1, t2, 12, "GrassyWay");
        r2 = new Road(t1, t2, "LeafyLane");
        r3 = new Road(t2, t3, 5, "MoonDrive");
        r4 = new Road(t3, t4, 10, "StarPath");
    }

    @After
    public void tearDown() throws Exception {
        r1 = null;
        r2 = null;
        r3 = null;
        r4 = null;
        t1 = null;
        t2 = null;
        t3 = null;
        t4 = null;
    }

    @Test
    public void testContains() {
        assertTrue(r1.contains(t1));
        assertTrue(r1.contains(t2));
        assertFalse(r1.contains(t3));
    }

    @Test
    public void testGetSource() {
        assertEquals(t1, r1.getSource());
        assertEquals(t1, r2.getSource());
    }

    @Test
    public void testGetDestination() {
        assertEquals(t2, r1.getDestination());
        assertEquals(t2, r2.getDestination());
    }

    @Test
    public void testGetWeight() {
        assertEquals(12, r1.getWeight());
        assertEquals(1, r2.getWeight());
        assertEquals(5, r3.getWeight());
    }

    @Test
    public void testGetName() {
        assertEquals("GrassyWay", r1.getName());
        assertEquals("LeafyLane", r2.getName());
    }

    @Test
    public void testEquals() {
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(r3));
        assertFalse(r3.equals(r4));
    }

    @Test
    public void testCompareTo() {
        assertTrue(r1.compareTo(r2) < 0);
        assertTrue(r3.compareTo(r4) < 0);
    }

    @Test
    public void testToString() {
        assertEquals("Rosehill via GrassyWay to Greenland 12 mi", r1.toString());
    }
}
