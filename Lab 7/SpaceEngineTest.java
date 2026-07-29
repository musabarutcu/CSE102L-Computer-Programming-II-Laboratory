import java.util.*;

public class SpaceEngineTest {
    public static void main(String[] args) {
        System.out.println("--- VPL SPACE ENGINE TEST STARTING ---");
        Universe universe = new Universe();

        // 1. Object Creation and Adding to Universe Test
        Planet earth = new Planet("Earth", 5.972e24, new Coordinate(100, 200), 6371);
        Planet mars = new Planet("Mars", 6.39e23, new Coordinate(400, 600), 3389);
        Star sun = new Star("Sun", 1.989e30, new Coordinate(0, 0), 696340);

        try {
            universe.addSpaceObject(earth);
            universe.addSpaceObject(mars);
            universe.addSpaceObject(sun);
            System.out.println("TEST 1 SUCCESS: Objects added to the universe.");
        } catch (CollisionException e) {
            System.out.println("TEST 1 FAILED: Unexpected collision!");
        }

        // 2. Exception Handling Test (Collision Simulation)
        Planet roguePlanet = new Planet("Rogue Planet", 1.0e20, new Coordinate(100, 200), 1000);
        try {
            universe.addSpaceObject(roguePlanet);
            System.out.println("TEST 2 FAILED: CollisionException was not thrown!");
        } catch (CollisionException e) {
            System.out.println("TEST 2 SUCCESS: Exception correctly caught. Error Message -> " + e.getMessage());
        }

        // 3. Generics and Math (Physics Engine) Test
        double distance = PhysicsEngine.calculateDistance(earth, mars);
        double gravity = PhysicsEngine.calculateGravity(earth, sun);
        
        System.out.printf("TEST 3 INFO: Distance between Earth and Mars: %.2f units\n", distance);
        System.out.printf("TEST 3 INFO: Gravitational Force between Earth and Sun: %.2e N\n", gravity);

        // 4. Comparable and Sorting Test (Based on distance to (0,0))
        System.out.println("\nTEST 4: Sorting Objects by Distance to Center");
        List<SpaceObject> sortedObjects = universe.getObjectsSortedByDistance();
        for (SpaceObject obj : sortedObjects) {
            System.out.println("- " + obj.getName() + " position: " + obj.getPosition());
        }
    }
}

class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class CollisionException extends Exception {
    public CollisionException(String message) {
        super(message);
    }
}

abstract class SpaceObject implements Comparable<SpaceObject> {
    private String name;
    private double mass;
    private Coordinate position;

    public SpaceObject(String name, double mass, Coordinate position) {
        this.name = name;
        this.mass = mass;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public Coordinate getPosition() {
        return position;
    }

    public abstract double calculateVolume();

    @Override
    public int compareTo(SpaceObject other) {
        double thisDist = Math.sqrt(Math.pow(this.position.getX(), 2) + Math.pow(this.position.getY(), 2));
        double otherDist = Math.sqrt(Math.pow(other.position.getX(), 2) + Math.pow(other.position.getY(), 2));
        return Double.compare(thisDist, otherDist);
    }
}

class Planet extends SpaceObject {
    private double radius;

    public Planet(String name, double mass, Coordinate position, double radius) {
        super(name, mass, position);
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {
        if (radius == 0) return 0.0;
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}

class Star extends SpaceObject {
    private double radius;

    public Star(String name, double mass, Coordinate position, double radius) {
        super(name, mass, position);
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {
        if (radius == 0) return 0.0;
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}

class PhysicsEngine {
    private static final double G = 6.674e-11;

    public static double calculateDistance(SpaceObject o1, SpaceObject o2) {
        double x1 = o1.getPosition().getX();
        double y1 = o1.getPosition().getY();
        double x2 = o2.getPosition().getX();
        double y2 = o2.getPosition().getY();
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double calculateGravity(SpaceObject o1, SpaceObject o2) {
        double distance = calculateDistance(o1, o2);
        
        if (distance == 0.0) return 0.0; 
        if (o1.getMass() == 0.0 || o2.getMass() == 0.0) return 0.0;
        
        return G * (o1.getMass() * o2.getMass()) / Math.pow(distance, 2);
    }
}

class Universe {
    private java.util.Map<Coordinate, SpaceObject> spatialGrid;

    public Universe() {
        this.spatialGrid = new java.util.HashMap<>();
    }

    public void addSpaceObject(SpaceObject obj) throws CollisionException {
        Coordinate pos = obj.getPosition();
        
        if (spatialGrid.containsKey(pos)) {
            SpaceObject existingObj = spatialGrid.get(pos);
            String errorMessage = "COLLISION WARNING: " + obj.getName() + 
                                  " object at coordinates " + pos.toString() + 
                                  " collided with " + existingObj.getName() + "!";
            throw new CollisionException(errorMessage);
        }
        
        spatialGrid.put(pos, obj);
    }

    public java.util.List<SpaceObject> getObjectsSortedByDistance() {
        java.util.List<SpaceObject> sortedObjects = new java.util.ArrayList<>(spatialGrid.values());
        java.util.Collections.sort(sortedObjects);
        return sortedObjects;
    }
}
