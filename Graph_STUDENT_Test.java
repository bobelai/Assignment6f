import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Graph_STUDENT_Test {
    private GraphInterface<Town,Road> graph;
    private Town[] town;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        town = new Town[12];
        for (int i = 1; i < 12; i++) {
            town[i] = new Town("City_" + i);
            graph.addVertex(town[i]);
        }
        graph.addEdge(town[1], town[2], 3, "Street_1");
        graph.addEdge(town[1], town[3], 7, "Street_2");
        graph.addEdge(town[1], town[5], 9, "Street_3");
        graph.addEdge(town[3], town[7], 2, "Street_4");
        graph.addEdge(town[3], town[8], 4, "Street_5");
        graph.addEdge(town[4], town[8], 6, "Street_6");
        graph.addEdge(town[6], town[9], 7, "Street_7");
        graph.addEdge(town[9], town[10],4, "Street_8");
        graph.addEdge(town[8], town[10],2, "Street_9");
        graph.addEdge(town[5], town[10],5, "Street_10");
        graph.addEdge(town[10], town[11],3,"Street_11");
        graph.addEdge(town[2], town[11],8,"Street_12");
    }

    @After
    public void tearDown() throws Exception {
        graph = null;
        town = null;
    }

    @Test
    public void testGetEdge() {
        assertEquals(new Road(town[2], town[11],8, "Street_12"), graph.getEdge(town[2], town[11]));
        assertEquals(new Road(town[3], town[7],2, "Street_4"), graph.getEdge(town[3], town[7]));
    }

    @Test
    public void testAddEdge() {
        assertEquals(false, graph.containsEdge(town[3], town[5]));
        graph.addEdge(town[3], town[5], 1, "Street_13");
        assertEquals(true, graph.containsEdge(town[3], town[5]));
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("City_12");
        assertEquals(false, graph.containsVertex(newTown));
        graph.addVertex(newTown);
        assertEquals(true, graph.containsVertex(newTown));
    }

    @Test
    public void testContainsEdge() {
        assertEquals(true, graph.containsEdge(town[2], town[11]));
        assertEquals(false, graph.containsEdge(town[3], town[5]));
    }

    @Test
    public void testContainsVertex() {
        assertEquals(true, graph.containsVertex(new Town("City_2")));
        assertEquals(false, graph.containsVertex(new Town("City_12")));
    }

    @Test
    public void testEdgeSet() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> roadArrayList = new ArrayList<>();
        for(Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Street_1",  roadArrayList.get(0));
        assertEquals("Street_10", roadArrayList.get(1));
        assertEquals("Street_11", roadArrayList.get(2));
        assertEquals("Street_12", roadArrayList.get(3));
        assertEquals("Street_2",  roadArrayList.get(4));
        assertEquals("Street_8",  roadArrayList.get(10));
    }

    @Test
    public void testEdgesOf() {
        Set<Road> roads = graph.edgesOf(town[1]);
        ArrayList<String> roadArrayList = new ArrayList<>();
        for(Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Street_1", roadArrayList.get(0));
        assertEquals("Street_2", roadArrayList.get(1));
        assertEquals("Street_3", roadArrayList.get(2));
    }

    @Test
    public void testRemoveEdge() {
        assertEquals(true, graph.containsEdge(town[2], town[11]));
        graph.removeEdge(town[2], town[11], 8, "Street_12");
        assertEquals(false, graph.containsEdge(town[2], town[11]));
    }

    @Test
    public void testRemoveVertex() {
        assertEquals(true, graph.containsVertex(town[2]));
        graph.removeVertex(town[2]);
        assertEquals(false, graph.containsVertex(town[2]));
    }

    @Test
    public void testVertexSet() {
        Set<Town> vertexSet = graph.vertexSet();
        assertTrue(vertexSet.contains(town[1]));
        assertTrue(vertexSet.contains(town[10]));
        assertTrue(vertexSet.contains(town[11]));
        assertTrue(vertexSet.contains(town[2]));
        assertTrue(vertexSet.contains(town[3]));
    }

    @Test
    public void testTown_1ToTown_11() {
        String beginTown = "City_1", endTown = "City_11";
        Town beginIndex = null, endIndex = null;
        Set<Town> towns = graph.vertexSet();
        Iterator<Town> iterator = towns.iterator();
        while(iterator.hasNext()) {
            Town t = iterator.next();
            if(t.getName().equals(beginTown))
                beginIndex = t;
            if(t.getName().equals(endTown))
                endIndex = t;
        }
        if(beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("City_1 via Street_1 to City_2 3 mi", path.get(0).trim());
            assertEquals("City_2 via Street_12 to City_11 8 mi", path.get(1).trim());
        } else {
            fail("town names are not valid");
        }
    }

    @Test
    public void testTown1ToTown_10() {
        String beginTown = "City_1", endTown = "City_10";
        Town beginIndex = null, endIndex = null;
        for(Town t : graph.vertexSet()) {
            if(t.getName().equals(beginTown))
                beginIndex = t;
            if(t.getName().equals(endTown))
                endIndex = t;
        }
        if(beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("City_1 via Street_2 to City_3 7 mi", path.get(0).trim());
            assertEquals("City_3 via Street_5 to City_8 4 mi", path.get(1).trim());
            assertEquals("City_8 via Street_9 to City_10 2 mi", path.get(2).trim());
        } else {
            fail("town names are not valid");
        }
    }

    @Test
    public void testTown_4ToTown_11() {
        String beginTown = "City_4", endTown = "City_11";
        Town beginIndex = null, endIndex = null;
        for(Town t : graph.vertexSet()) {
            if(t.getName().equals(beginTown))
                beginIndex = t;
            if(t.getName().equals(endTown))
                endIndex = t;
        }
        if(beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("City_4 via Street_6 to City_8 6 mi", path.get(0).trim());
            assertEquals("City_8 via Street_9 to City_10 2 mi", path.get(1).trim());
            assertEquals("City_10 via Street_11 to City_11 3 mi", path.get(2).trim());
        } else {
            fail("town names are not valid");
        }
    }
}
