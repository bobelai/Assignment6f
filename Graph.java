/**
 * this class represents an undirected graph of towns and roads.
 * it implements dijkstra's shortest path algorithm.
 *
 * @author Leul Belay
 */
import java.util.*;

public class Graph implements GraphInterface<Town, Road> {

    /**
     * adjacency map stores each town along with a set of roads that connect it to other towns
     */
    private Map<Town, Set<Road>> adjacencyMap;

    /**
     * distances holds the shortest known distance from the source town to each other town
     */
    private Map<Town, Integer> distances;

    /**
     * previousvertices holds the town that comes before the current town on the shortest path
     */
    private Map<Town, Town> previousVertices;

    /**
     * creates an empty graph using a hashmap-based adjacency map
     */
    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    /**
     * returns the road that directly connects the sourcevertex to the destinationvertex
     *
     * @param sourceVertex the starting town
     * @param destinationVertex the ending town
     * @return the road connecting them or null if none
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        Set<Road> edges = adjacencyMap.get(sourceVertex);
        if (edges != null) {
            for (Road road : edges) {
                if (road.contains(destinationVertex)) {
                    return road;
                }
            }
        }
        return null;
    }

    /**
     * creates a new road between two towns already in the graph
     *
     * @param sourceVertex the starting town
     * @param destinationVertex the ending town
     * @param weight the distance of the road
     * @param description the road name
     * @return the created road or null if it cannot be created
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);
        adjacencyMap.get(sourceVertex).add(newRoad);
        adjacencyMap.get(destinationVertex).add(newRoad);
        return newRoad;
    }

    /**
     * adds a new town to the graph
     *
     * @param v the town to add
     * @return true if the town is added, false if it already exists
     */
    @Override
    public boolean addVertex(Town v) {
        if (adjacencyMap.containsKey(v)) {
            return false;
        }
        adjacencyMap.put(v, new HashSet<>());
        return true;
    }

    /**
     * checks if there is an edge (road) between two towns
     *
     * @param sourceVertex one town
     * @param destinationVertex another town
     * @return true if a road exists between them
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        return (getEdge(sourceVertex, destinationVertex) != null);
    }

    /**
     * checks if the graph contains the given town
     *
     * @param v the town
     * @return true if the town is found, false otherwise
     */
    @Override
    public boolean containsVertex(Town v) {
        return adjacencyMap.containsKey(v);
    }

    /**
     * returns a set of all roads in the graph
     *
     * @return a set of roads
     */
    @Override
    public Set<Road> edgeSet() {
        Set<Road> allEdges = new HashSet<>();
        for (Set<Road> edges : adjacencyMap.values()) {
            allEdges.addAll(edges);
        }
        return allEdges;
    }

    /**
     * returns a set of all roads that touch the given town
     *
     * @param vertex the town
     * @return set of roads connected to that town
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        return adjacencyMap.get(vertex);
    }

    /**
     * removes the road matching the parameters and returns it
     *
     * @param sourceVertex the first town
     * @param destinationVertex the second town
     * @param weight the distance
     * @param description the road name
     * @return the removed road or null if not found
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road road = getEdge(sourceVertex, destinationVertex);
        if (road != null) {
            adjacencyMap.get(sourceVertex).remove(road);
            adjacencyMap.get(destinationVertex).remove(road);
            return road;
        }
        return null;
    }

    /**
     * removes a town and all roads connected to it
     *
     * @param v the town to remove
     * @return true if the town was removed, false otherwise
     */
    @Override
    public boolean removeVertex(Town v) {
        if (!adjacencyMap.containsKey(v)) {
            return false;
        }
        adjacencyMap.remove(v);
        for (Set<Road> roads : adjacencyMap.values()) {
            roads.removeIf(r -> r.contains(v));
        }
        return true;
    }

    /**
     * returns a set of all towns in the graph
     *
     * @return set of towns
     */
    @Override
    public Set<Town> vertexSet() {
        return adjacencyMap.keySet();
    }

    /**
     * builds an arraylist of roads that represents the shortest path
     * between sourcevertex and destinationvertex using dijkstra's algorithm
     *
     * @param sourceVertex the start town
     * @param destinationVertex the end town
     * @return arraylist of road strings in the format "town1 via roadName to town2 distance mi"
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        dijkstraShortestPath(sourceVertex);

        ArrayList<String> path = new ArrayList<>();
        Town current = destinationVertex;

        while (current != null && !current.equals(sourceVertex)) {
            Town previous = previousVertices.get(current);
            if (previous == null) {
                break;
            }
            Road road = getEdge(previous, current);
            path.add(0, previous.getName() + " via " + road.getName() + " to "
                    + current.getName() + " " + road.getWeight() + " mi");
            current = previous;
        }
        return path;
    }

    /**
     * performs dijkstra's shortest path algorithm from a source town
     *
     * @param sourceVertex the town to start from
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        distances = new HashMap<>();
        previousVertices = new HashMap<>();

        for (Town t : adjacencyMap.keySet()) {
            distances.put(t, Integer.MAX_VALUE);
            previousVertices.put(t, null);
        }
        distances.put(sourceVertex, 0);

        PriorityQueue<Town> priorityQueue = new PriorityQueue<>(new Comparator<Town>() {
            @Override
            public int compare(Town t1, Town t2) {
                return Integer.compare(distances.get(t1), distances.get(t2));
            }
        });
        priorityQueue.add(sourceVertex);

        while (!priorityQueue.isEmpty()) {
            Town current = priorityQueue.poll();
            for (Road r : edgesOf(current)) {
                Town neighbor = (r.getSource().equals(current)) ? r.getDestination() : r.getSource();
                int newDist = distances.get(current) + r.getWeight();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousVertices.put(neighbor, current);

                    priorityQueue.remove(neighbor);
                    priorityQueue.add(neighbor);
                }
            }
        }
    }
}
