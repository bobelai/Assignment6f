/**
 * this class manages the town graph by allowing the user to add towns and roads,
 * retrieve roads, and find shortest paths between towns.
 * it also reads the towns and roads from a file to populate the graph.
 *
 * @author Leul Belay
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TownGraphManager implements TownGraphManagerInterface {

    /**
     * the underlying graph that stores towns and roads
     */
    private Graph graph;

    /**
     * creates a towngraphmanager by instantiating a new graph
     */
    public TownGraphManager() {
        graph = new Graph();
    }

    /**
     * adds a road connecting two towns with the given weight (distance) and name
     *
     * @param town1 the first town
     * @param town2 the second town
     * @param weight the distance of the road
     * @param roadName the name of the road
     * @return true if the road was successfully added
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        return graph.addEdge(new Town(town1), new Town(town2), weight, roadName) != null;
    }

    /**
     * gets the road name that directly connects two towns
     *
     * @param town1 the first town
     * @param town2 the second town
     * @return the road name or null if there is no direct road
     */
    @Override
    public String getRoad(String town1, String town2) {
        Road road = graph.getEdge(new Town(town1), new Town(town2));
        if (road != null) {
            return road.getName();
        }
        return null;
    }

    /**
     * adds a town to the graph
     *
     * @param v the name of the town
     * @return true if the town was added, false if it already exists
     */
    @Override
    public boolean addTown(String v) {
        return graph.addVertex(new Town(v));
    }

    /**
     * returns the town object for the given name
     *
     * @param name the town's name
     * @return the town object or null if not found
     */
    @Override
    public Town getTown(String name) {
        for (Town t : graph.vertexSet()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * checks if the graph contains a town
     *
     * @param v the name of the town
     * @return true if the graph contains the town, false otherwise
     */
    @Override
    public boolean containsTown(String v) {
        return graph.containsVertex(new Town(v));
    }

    /**
     * checks if there is a road connection between two towns
     *
     * @param town1 the first town name
     * @param town2 the second town name
     * @return true if a road connects the towns, false otherwise
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return graph.containsEdge(new Town(town1), new Town(town2));
    }

    /**
     * returns a sorted list of all road names in the graph
     *
     * @return arraylist of sorted road names
     */
    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> roads = new ArrayList<>();
        for (Road r : graph.edgeSet()) {
            roads.add(r.getName());
        }
        Collections.sort(roads);
        return roads;
    }

    /**
     * deletes a specific road connection between two towns
     *
     * @param town1 name of the first town
     * @param town2 name of the second town
     * @param roadName the name of the road
     * @return true if the road was removed, false otherwise
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        Town source = new Town(town1);
        Town destination = new Town(town2);

        Road road = graph.getEdge(source, destination);
        if (road != null && road.getName().equals(roadName)) {
            Road removed = graph.removeEdge(source, destination, road.getWeight(), road.getName());
            return (removed != null);
        }
        return false;
    }

    /**
     * deletes a town from the graph
     *
     * @param v the name of the town
     * @return true if the town was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteTown(String v) {
        return graph.removeVertex(new Town(v));
    }

    /**
     * returns a sorted list of all town names in the graph
     *
     * @return arraylist of sorted town names
     */
    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> towns = new ArrayList<>();
        for (Town t : graph.vertexSet()) {
            towns.add(t.getName());
        }
        Collections.sort(towns);
        return towns;
    }

    /**
     * finds the shortest path between two towns
     *
     * @param town1 name of the start town
     * @param town2 name of the destination town
     * @return arraylist of roads in the path format "town1 via roadName to town2 distance mi"
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        return graph.shortestPath(new Town(town1), new Town(town2));
    }

    /**
     * reads a file line by line to populate the town graph with roads and towns
     *
     * @param file the file to read
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void populateTownGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length != 3) continue;

            String[] roadDetails = parts[0].split(",");
            if (roadDetails.length != 2) continue;

            String roadName = roadDetails[0].trim();
            int distance = Integer.parseInt(roadDetails[1].trim());
            String townName1 = parts[1].trim();
            String townName2 = parts[2].trim();

            addTown(townName1);
            addTown(townName2);
            addRoad(townName1, townName2, distance, roadName);
        }
        scanner.close();
    }
}
