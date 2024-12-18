
/**
 * this class represents a road connecting two towns. it stores the source town, destination town,
 * distance, and name. it treats the road as undirected.
 *
 * @author Leul Belay
 */
import java.util.Objects;

public class Road implements Comparable<Road> {

    /**
     * the starting town
     */
    private Town source;

    /**
     * the ending town
     */
    private Town destination;

    /**
     * the distance of the road
     */
    private int weight;

    /**
     * the name of the road
     */
    private String name;

    /**
     * creates a road between two towns with a specific distance and name
     *
     * @param source the first town
     * @param destination the second town
     * @param weight the distance of the road
     * @param name the name of the road
     */
    public Road(Town source, Town destination, int weight, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }

    /**
     * creates a road between two towns with a default distance of 1
     *
     * @param source the first town
     * @param destination the second town
     * @param name the name of the road
     */
    public Road(Town source, Town destination, String name) {
        this(source, destination, 1, name);
    }

    /**
     * checks if this road connects the given town
     *
     * @param town the town to check
     * @return true if the road has that town as source or destination
     */
    public boolean contains(Town town) {
        return source.equals(town) || destination.equals(town);
    }

    /**
     * gets the destination town
     *
     * @return the destination town
     */
    public Town getDestination() {
        return destination;
    }

    /**
     * gets the source town
     *
     * @return the source town
     */
    public Town getSource() {
        return source;
    }

    /**
     * gets the road name
     *
     * @return the road name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the distance of this road
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * compares roads by their names
     *
     * @param other the other road
     * @return negative, zero, or positive if this road's name is lexicographically before,
     * equal to, or after the other road's name
     */
    @Override
    public int compareTo(Road other) {
        return this.name.compareTo(other.name);
    }

    /**
     * checks if two roads are the same. two roads are considered the same if they connect the same towns
     * regardless of direction
     *
     * @param obj the other object
     * @return true if they connect the same towns, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road other = (Road) obj;
        boolean sameDirect = source.equals(other.source) && destination.equals(other.destination);
        boolean oppositeDirect = source.equals(other.destination) && destination.equals(other.source);
        return sameDirect || oppositeDirect;
    }

    /**
     * returns the hashcode for this road
     *
     * @return an integer hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination, name);
    }

    /**
     * returns a string representation in the format "source via name to destination weight mi"
     *
     * @return the string representation of the road
     */
    @Override
    public String toString() {
        return source + " via " + name + " to " + destination + " " + weight + " mi";
    }
}
