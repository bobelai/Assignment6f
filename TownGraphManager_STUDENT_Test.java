import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManager_STUDENT_Test {
    private TownGraphManagerInterface graph;
    private String[] town;

    @Before
    public void setUp() throws Exception {
        graph = new TownGraphManager();
        town = new String[12];
        for (int i = 1; i < 12; i++) {
            town[i] = "City_" + i;
            graph.addTown(town[i]);
        }
        graph.addRoad(town[1], town[2], 3, "Street_1");
        graph.addRoad(town[1], town[3], 7, "Street_2");
        graph.addRoad(town[1], town[5], 9, "Street_3");
        graph.addRoad(town[3], town[7], 2, "Street_4");
        graph.addRoad(town[3], town[8], 4, "Street_5");
        graph.addRoad(town[4], town[8], 6, "Street_6");
        graph.addRoad(town[6], town[9], 7, "Street_7");
        graph.addRoad(town[9], town[10], 4, "Street_8");
        graph.addRoad(town[8], town[10], 2, "Street_9");
        graph.addRoad(town[5], town[10], 5, "Street_10");
        graph.addRoad(town[10], town[11], 3, "Street_11");
        graph.addRoad(town[2], town[11], 8, "Street_12");
    }

    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testAddRoad() {
        ArrayList<String> roads = graph.allRoads();
        assertEquals("Street_1", roads.get(0));
        assertEquals("Street_10", roads.get(1));
        assertEquals("Street_11", roads.get(2));
        assertEquals("Street_12", roads.get(3));
        graph.addRoad(town[4], town[11], 1, "Street_13");
        roads = graph.allRoads();
        assertEquals("Street_1", roads.get(0));
        assertEquals("Street_10", roads.get(1));
        assertEquals("Street_11", roads.get(2));
        assertEquals("Street_12", roads.get(3));
        assertEquals("Street_13", roads.get(4));
    }

    @Test
    public void testGetRoad() {
        assertEquals("Street_12", graph.getRoad(town[2], town[11]));
        assertEquals("Street_4", graph.getRoad(town[3], town[7]));
    }

    @Test
    public void testAddTown() {
        assertEquals(false, graph.containsTown("City_12"));
        graph.addTown("City_12");
        assertEquals(true, graph.containsTown("City_12"));
    }

    @Test
    public void testDisjointGraph() {
        assertEquals(false, graph.containsTown("City_12"));
        graph.addTown("City_12");
        ArrayList<String> path = graph.getPath(town[1], "City_12");
        assertFalse(path.size() > 0);
    }

    @Test
    public void testContainsTown() {
        assertEquals(true, graph.containsTown("City_2"));
        assertEquals(false, graph.containsTown("City_12"));
    }

    @Test
    public void testContainsRoadConnection() {
        assertEquals(true, graph.containsRoadConnection(town[2], town[11]));
        assertEquals(false, graph.containsRoadConnection(town[3], town[5]));
    }

    @Test
    public void testAllRoads() {
        ArrayList<String> roads = graph.allRoads();
        assertEquals("Street_1", roads.get(0));
        assertEquals("Street_10", roads.get(1));
        assertEquals("Street_11", roads.get(2));
    }

    @Test
    public void testDeleteRoadConnection() {
        assertEquals(true, graph.containsRoadConnection(town[2], town[11]));
        graph.deleteRoadConnection(town[2], town[11], "Street_12");
        assertEquals(false, graph.containsRoadConnection(town[2], town[11]));
    }

    @Test
    public void testDeleteTown() {
        assertEquals(true, graph.containsTown("City_2"));
        graph.deleteTown(town[2]);
        assertEquals(false, graph.containsTown("City_2"));
    }

    @Test
    public void testAllTowns() {
        ArrayList<String> roads = graph.allTowns();
        assertEquals("City_1", roads.get(0));
        assertEquals("City_10", roads.get(1));
        assertEquals("City_11", roads.get(2));
        assertEquals("City_2", roads.get(3));
    }

    @Test
    public void testGetPath() {
        ArrayList<String> path = graph.getPath(town[1], town[11]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_1 via Street_1 to City_2 3 mi", path.get(0).trim());
        assertEquals("City_2 via Street_12 to City_11 8 mi", path.get(1).trim());
    }

    @Test
    public void testGetPathA() {
        ArrayList<String> path = graph.getPath(town[1], town[10]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_1 via Street_2 to City_3 7 mi", path.get(0).trim());
        assertEquals("City_3 via Street_5 to City_8 4 mi", path.get(1).trim());
        assertEquals("City_8 via Street_9 to City_10 2 mi", path.get(2).trim());
    }

    @Test
    public void testGetPathB() {
        ArrayList<String> path = graph.getPath(town[1], town[6]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_1 via Street_2 to City_3 7 mi", path.get(0).trim());
        assertEquals("City_3 via Street_5 to City_8 4 mi", path.get(1).trim());
        assertEquals("City_8 via Street_9 to City_10 2 mi", path.get(2).trim());
        assertEquals("City_10 via Street_8 to City_9 4 mi", path.get(3).trim());
        assertEquals("City_9 via Street_7 to City_6 7 mi", path.get(4).trim());
    }
}
