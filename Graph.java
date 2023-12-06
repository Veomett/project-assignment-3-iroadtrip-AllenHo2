// import java.io.BufferedReader;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException; // Add this import statement
// import java.sql.Array;
// import java.util.*;
// import static java.lang.Integer.MAX_VALUE;
// public class Graph {    
//     private HashMap<String, HashMap<String, Integer>> country;  // Hashmap for country/nodes


//     public Graph(HashMap<String, HashMap<String, Integer>> country) {
//         this.country = new HashMap<>();
//     }
// // Dijkstra's algorithm implementation with tracking visited nodes
//     public List<String> dijkstra(HashMap<String, HashMap<String, Integer>> country, String startCountry, String destCountry) {
//         PriorityQueue<Node> minHeap = new PriorityQueue<>();
//         minHeap.add(new Node(startCountry, 0));
//         HashMap<String, Integer> distances = new HashMap<>();
//         for (String node : country.keySet()) {
//             distances.put(node, Integer.MAX_VALUE);
//         }
//         distances.put(startCountry, 0);
//         List<String> visitedNodes = new ArrayList<>();

//         // Process nodes in the priority queue
//         while (!minHeap.isEmpty()) {
//             Node currentNode = minHeap.poll();
//             String current = currentNode.node;
//             // Add the current node to the visited nodes list
//             visitedNodes.add(current);
//             // If the destination node is reached, stop the algorithm
//             if (current.equals(destCountry)) {
//                 break;
//             }
//             // Update distances for adjacent nodes
//             if (country.containsKey(current)) {
//                 for (Map.Entry<String, Integer> entry : country.get(current).entrySet()) {
//                     String neighbor = entry.getKey();
//                     int weight = entry.getValue();
//                     int newDistance = distances.get(current) + weight;

//                     // Update distance if a shorter path is found
//                     if (newDistance < distances.get(neighbor)) {
//                         distances.put(neighbor, newDistance);
//                         minHeap.add(new Node(neighbor, newDistance));
//                     }
//                 }
//             }
//         }

//         return visitedNodes;
//         }

//     // public void addEdge(String country1, String country2, int distance) {
//     //     Edge edge = new Edge(country1, country2, distance);
//     //     // Add the edge to your data structure or perform any other necessary operations
//     // }


//     // public void dijkstra(HashMap<String, HashMap<String, Integer>> country, String startCountry, String destCountry){
//     //   // Initialize the priority queue for nodes with their distances
//     //   PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
//     //   minHeap.add(new Node(startCountry, 0));
//     // }

//     // public void loadData(HashMap<String, HashMap<String, Integer>> country) {
//     //     for (Map.Entry<String, HashMap<String, Integer>> entry : country.entrySet()) {
//     //         String countryKey = entry.getKey();
//     //         addCountry(countryKey);

//     //         for (Map.Entry<String, Integer> neighborEntry : entry.getValue().entrySet()) {
//     //             String neighbor = neighborEntry.getKey();
//     //             int distance = neighborEntry.getValue();
//     //             addCountry(neighbor);

//     //             // Add edge with distance
//     //             country.get(countryKey).put(neighbor, distance);
//     //             country.get(neighbor).put(countryKey, distance);

//     //             // Add the neighboring country
//     //             country.get(countryKey).put(neighbor, 0);
//     //             country.get(neighbor).put(countryKey, 0);
//     //         }
//     //     }
//     // }

//     // public void addCountry(String countryKey) {
//     //     if (!country.containsKey(countryKey)) {
//     //         country.put(countryKey, new HashMap<>());
//     //     }
//     // }
//     // public void addDistance(String country1, String country2, int distance) {
//     //     addCountry(country1);
//     //     addCountry(country2);

//     //     // Add edge with distance
//     //     country.get(country1).put(country2, distance);
//     //     country.get(country2).put(country1, distance);
//     // }

//     // public void addBorder(String countryKey, String neighbor) {
//     //     addCountry(countryKey);
//     //     addCountry(neighbor);

//     //     // Add the neighboring country
//     //     country.get(countryKey).put(neighbor, 0);
//     //     country.get(neighbor).put(countryKey, 0);
//     // }

//     // public List<String> dijkstra(String start, String end) {
//     //     Map<String, Integer> distancesFromStart = new HashMap<>();
//     //     Map<String, String> previous = new HashMap<>();
//     //     PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distancesFromStart::get));

//     //     // Initialize distances and queue
//     //     for (String country : country.keySet()) {
//     //         distancesFromStart.put(country, country.equals(start) ? 0 : Integer.MAX_VALUE);
//     //         previous.put(country, null);
//     //         queue.add(country);
//     //     }

//     //     while (!queue.isEmpty()) {
//     //         String current = queue.poll();
//     //         if (current.equals(end)) {
//     //             // Found the shortest path to the destination
//     //             List<String> path = new ArrayList<>();
//     //             while (previous.get(current) != null) {
//     //                 path.add(current);
//     //                 current = previous.get(current);
//     //             }
//     //             Collections.reverse(path);
//     //             return path;
//     //         }

//     //         for (String neighbor : country.get(current).keySet()) {
//     //             int newDistance = distancesFromStart.get(current) + country.get(current).get(neighbor);
//     //             if (newDistance < distancesFromStart.get(neighbor)) {
//     //                 distancesFromStart.put(neighbor, newDistance);
//     //                 previous.put(neighbor, current);
//     //                 // Reorder the priority queue to reflect the updated distances
//     //                 queue.remove(neighbor);
//     //                 queue.add(neighbor);
//     //             }
//     //         }
//     //     }

//     //     return Collections.emptyList();  // No path found
//     // }

//     // public Map<String, HashMap<String, Integer>> setCountry(HashMap<String, HashMap<String, Integer>> country) {
//     //     return this.country = country;
//     // }

        
// }


// // public class Node implements Comparable<Node> {
// //         String name;
// //         int distance;

// //         Node(String name, int distance) {
// //             this.name = name;
// //         }

// //         @Override
// //         public int compareTo(Node other) {
// //             return Integer.compare(this.distance, other.distance);
// //         }
// //     }
// //     public class Edge implements Comparable<Edge>{
// //         String source;
// //         String dest;
// //         int weight;
// //         Edge(){
// //             source = null;
// //             dest = null;
// //             weight = 0;
// //         }
// //         Edge(String v1, String v2, int w){
// //             source = v1;
// //             dest = v2;
// //             weight = w;
// //         }
// //         public int compareTo(Edge e){// needed for Kruskal's algorithm
// //             return this.weight - e.weight;
// //         }
// //     }