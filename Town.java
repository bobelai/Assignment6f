/**
 * this class represents a town with a name. it implements comparable so it can be used in sorting
 * and priority queues.
 *
 * @author Leul Belay
 */
import java.util.Objects;

public class Town implements Comparable<Town> {

    /**
     * the name of this town
     */
    private String name;

    /**
     * creates a new town with a given name
     *
     * @param name the name of the town
     */
    public Town(String name) {
        this.name = name;
    }

    /**
     * copy constructor that creates a new town with the same name
     *
     * @param templateTown the existing town to copy from
     */
    public Town(Town templateTown) {
        this.name = templateTown.name;
    }

    /**
     * gets the name of the town
     *
     * @return the town name
     */
    public String getName() {
        return name;
    }

    /**
     * compares two towns by name
     *
     * @param o the other town
     * @return negative, zero, or positive if this town's name is lexicographically before,
     * equal to, or after the other town's name
     */
    @Override
    public int compareTo(Town o) {
        return this.name.compareTo(o.name);
    }

    /**
     * checks if two towns have the same name
     *
     * @param obj the other object
     * @return true if they have the same name, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Town other = (Town) obj;
        return name.equals(other.name);
    }

    /**
     * returns the hashcode for this town
     *
     * @return the integer hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * returns the town name as a string
     *
     * @return the town's name
     */
    @Override
    public String toString() {
        return name;
    }
}
