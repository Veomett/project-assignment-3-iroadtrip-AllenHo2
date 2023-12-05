import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException; // Add this import statement
import java.sql.Array;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;
public class Graph {    
    private HashMap<String, HashMap<String, Integer>> countries;  // Hashmap for countries/nodes
    private HashMap<String, HashMap<String, Integer>> distances;  // Hashmap for distances between countries
    private HashMap<String, HashMap<String, Integer>> borders;  

    public Graph(HashMap<String, HashMap<String, Integer>> countries, HashMap<String, HashMap<String, Integer>> distances, HashMap<String, String> countryIDToCountryName) {
        this.countries = new HashMap<>();
        this.distances = new HashMap<>();
        this.borders = new HashMap<>();
    }

    public void addCountry(String country) {
        if (!countries.containsKey(country)) {
            countries.put(country, new HashMap<>());
            distances.put(country, new HashMap<>());
            borders.put(country, new HashMap<>());
        }
    }
    public void addDistance(String country1, String country2, int distance) {
        addCountry(country1);
        addCountry(country2);

        // Add edge with distance
        distances.get(country1).put(country2, distance);
        distances.get(country2).put(country1, distance);
    }

    public void addBorder(String country, String neighbor) {
        addCountry(country);
        addCountry(neighbor);

        // Add the neighboring country
        borders.get(country).put(neighbor, 0);
        borders.get(neighbor).put(country, 0);
    }

    public List<String> dijkstra(String start, String end) {
        Map<String, Integer> distancesFromStart = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distancesFromStart::get));

        // Initialize distances and queue
        for (String country : countries.keySet()) {
            distancesFromStart.put(country, country.equals(start) ? 0 : Integer.MAX_VALUE);
            previous.put(country, null);
            queue.add(country);
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                // Found the shortest path to the destination
                List<String> path = new ArrayList<>();
                while (previous.get(current) != null) {
                    path.add(current);
                    current = previous.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (String neighbor : distances.get(current).keySet()) {
                int newDistance = distancesFromStart.get(current) + distances.get(current).get(neighbor);
                if (newDistance < distancesFromStart.get(neighbor)) {
                    distancesFromStart.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    // Reorder the priority queue to reflect the updated distances
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return Collections.emptyList();  // No path found
    }

    public Map<String, HashMap<String, Integer>> getCountries() {
        return countries;
    }

    public HashMap<String, HashMap<String, Integer>> getDistances() {
        return distances;
    }

    public Map<String, HashMap<String, Integer>> getBorders() {
        return borders;
    }
    
    public Map<String, HashMap<String, Integer>> setCountries(HashMap<String, HashMap<String, Integer>> countries) {
        return this.countries = countries;
    }
    public Map<String, HashMap<String, Integer>> setDistances(HashMap<String, HashMap<String, Integer>> distances) {
        return this.distances = distances;
    }
    public Map<String, HashMap<String, Integer>> setBorders(HashMap<String, HashMap<String, Integer>> borders) {
        return this.borders = borders;
    }

    // public void displayGraph() {
    //     for (String country : countries.keySet()) {
    //         System.out.println("Country " + country + ":");
    //         System.out.println("  Neighboring countries:");
    //         for (String neighbor : borders.get(country).keySet()) {
    //             System.out.println("    " + neighbor);
    //         }
    //         System.out.println("  Distances to other countries:");
    //         for (Map.Entry<String, Integer> entry : distances.get(country).entrySet()) {
    //             System.out.println("    " + entry.getKey() + " (Distance: " + entry.getValue() + " km)");
    //         }
    //         System.out.println();
    //     }
    // }
}


