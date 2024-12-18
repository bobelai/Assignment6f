import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Town_STUDENT_Test {
    private Town ta, tb, tc;

    @Before
    public void setUp() throws Exception {
        ta = new Town("Everwood");
        tb = new Town("Laketown");
        tc = new Town("Marigold");
    }

    @After
    public void tearDown() throws Exception {
        ta = null;
        tb = null;
        tc = null;
    }

    @Test
    public void testGetName() {
        assertEquals("Everwood", ta.getName());
        assertEquals("Laketown", tb.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue(ta.compareTo(tb) < 0);
        assertTrue(tc.compareTo(ta) > 0);
    }

    @Test
    public void testEquals() {
        Town copy = new Town("Everwood");
        assertTrue(ta.equals(copy));
        assertFalse(ta.equals(tb));
    }

    @Test
    public void testHashCode() {
        Town copy = new Town("Everwood");
        assertEquals(ta.hashCode(), copy.hashCode());
        assertNotEquals(ta.hashCode(), tb.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Everwood", ta.toString());
        assertEquals("Laketown", tb.toString());
    }

    @Test
    public void testCopyConstructor() {
        Town clone = new Town(tb);
        assertEquals("Laketown", clone.getName());
        assertTrue(tb.equals(clone));
    }
}
