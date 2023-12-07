import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException; 
import java.util.regex.Pattern;
import java.util.regex.Matcher; 

public class IRoadTrip {

    private HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>(); //Main graph
    private HashMap<String, String> countryNameToCountryID = new HashMap<String, String>();   //Converts country name to country ID
    private HashMap<String, HashMap<String, Integer>> countryDistance= new HashMap<String, HashMap<String, Integer>>(); //Stores the ID to distances between countries
    private HashMap<String, Integer> countriesDistance = new HashMap<String, Integer>(); //Stores the distances between adjacent countries
    private HashMap<String, String> cases = new HashMap<String, String>(); //Stores edge cases

    public IRoadTrip(String[] args) {
        if (args.length == 3) { // if there are 3 arguments, read the files
            generateSpecialCases(); //generate edge cases
            try (BufferedReader reader = new BufferedReader(new FileReader(args[1]))) { // reads the capdist.csv file
                String line;
                int skipFirstLine = 0;
                while ((line = reader.readLine()) != null) { //skips the first line of the csv file (numa,ida,numb,idb,kmdist,midist)
                    if (skipFirstLine == 0) {
                        skipFirstLine++;
                        continue;
                    }
                    String[] parts = line.split(","); //split by commas
                    String country1 = parts[1].trim(); // gets first country
                    String country2 = parts[3].trim(); // gets second country
                    Integer distance = Integer.parseInt(parts[4].trim()); // gets distance between countries
                    
                    if (!countryDistance.containsKey(country1)) { // if country1 is not in the hashmap, add it
                        countriesDistance = new HashMap<String, Integer>();
                        countryDistance.put(country1, countriesDistance);
                    } else { // if country1 is in the hashmap, get the hashmap of country1
                        countriesDistance = countryDistance.get(country1);
                    }
                    
                    countriesDistance.put(country2, distance); // add country2 and distance to the hashmap of country1
                }
            } catch (IOException e) {
                        e.printStackTrace();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(args[2]))) { //reads the state_name.tsv file
                        String line; 
                        while ((line = reader.readLine()) != null) {    //Reads the file line by line
                            Pattern pattern = Pattern.compile("\\b[\\S ]+\\b"); 
                            Matcher matcher = pattern.matcher(line);

                            if(matcher.find()){  //Finds the country name
                            String keyValue = findKey(matcher, 2); //Country ID
                            String key = findKey(matcher, 2); //Country Name
                                countryNameToCountryID.put(key, keyValue);         //Country ID to Country Name
                            
                            }
                        }
            } catch (IOException e) {
            e.printStackTrace();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) { // reads the borders.txtfile
                String line;
                while ((line = reader.readLine()) != null) { // reads the file line by line
                    String[] parts = line.split("=|;");
                    String country1 = parts[0].trim(); // gets first country
                    if(cases.containsKey(country1)){ // if country1 is in the cases hashmap, get the country1 value
                        country1 = cases.get(country1);
                    }
                    HashMap<String, Integer> borderCountryList = new HashMap<String, Integer>(); // create a new HashMap for each country1

                    for (int i = 1; i < parts.length; i++) { // loop through the line being read
                        String[] stripBorders = parts[i].trim().split("\\s+\\d[\\d,]*\\s+km");
                        String borderingCountry = stripBorders[0].trim(); // gets the bordering country
                        if(cases.containsKey(borderingCountry)){ // if borderingCountry is in the cases hashmap, get the borderingCountry value
                            borderingCountry = cases.get(borderingCountry);
                        } 
                        if (borderingCountry != null) { // if borderingCountry is not null, add it to the hashmap of country1
                            int length = getDistance(country1, borderingCountry);
                            if(length == -1){ // if the distance between country1 and borderingCountry is -1, leave in null space & just put the bordering country into the hashmap
                                continue;
                            }
                            borderCountryList.put(borderingCountry, getDistance(country1, borderingCountry)); // add borderingCountry and distance to the hashmap of country1
                        }
                    }

                    country.put(country1, borderCountryList); // add country1 and its hashmap to the country hashmap
                System.out.println(country1 + " " + borderCountryList); // shows all countries and their borders

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid number of arguments.");
            System.exit(0);
        }
    }

    private void generateSpecialCases(){ //generates edge cases
        cases.put("United States", "United States of America");
        cases.put("US", "United States of America");
        cases.put("Czechia", "Czech Republic");
        cases.put("Turkey (Turkiye)", "Turkey (Ottoman Empire");
        cases.put("Turkey", "Turkey (Ottoman Empire");
        cases.put("Korea, South", "Korea, Republic of");
        cases.put("Korea, North", "Korea, People's Republic of");
        cases.put("Timor-Leste", "East Timor");
        cases.put("Cabo Verde", "Cape Verde");
        cases.put("Cote d'Ivoire", "Ivory Coast");
        cases.put("Gambia, The", "Gambia");
        cases.put("Bahamas, The", "Bahamas");
        cases.put("Czechia", "Czech Republic");
        cases.put("North Macedonia", "Macedonia (Former Yugoslav Republic of");
        cases.put("Belarus", "Belarus (Byelorussia");
        cases.put("Macedonia", "Macedonia (Former Yugoslav Republic of");
        cases.put("The Central African Republic", "Central African Republic");
        cases.put("Congo, Democratic Republic of the", "Congo, Democratic Republic of (Zaire");
        cases.put("Congo, Republic of the", "Congo");
        cases.put("The Republic of the Congo", "Congo");
        cases.put("Democratic Republic of the Congo", "Congo, Democratic Republic of (Zaire");
        cases.put("Russia", "Russia (Soviet Union)");
        cases.put("The Slovak Republic", "Slovakia");
        cases.put("Zimbabwe", "Zimbabwe (Rhodesia");
        cases.put("Iran", "Iran (Persia");
        cases.put("Botswana 0.15 km", "Botswana");
        cases.put("Zambia 0.15 km", "Zambia");
        cases.put("Denmark (Greenland) 1.3 km", "Denmark");
        cases.put("Canada 1.3 km", "Canada");
        cases.put("Gibraltar 1.2 km", "Gibraltar");
        cases.put("Holy See (Vatican City) 3.4 km", "Holy See (Vatican City)");
        cases.put("Denmark (Greenland)", "Denmark");
        cases.put("Yemen", "Yemen (Arab Republic of Yemen");
        cases.put("Cambodia", "Cambodia (Kampuchea");
        cases.put("Tanzania", "Tanzania/Tanganyika");
        cases.put("Vietnam", "Vietnam, Democratic Republic of");
        cases.put("The Solomon Islands", "Solomon Islands");
        cases.put("UK", "United Kingdom");
        cases.put("Germany", "German Federal Republic");
        cases.put("Spain (Ceuta)", "Spain");
        cases.put("Morocco (Cueta)", "Morocco");
        cases.put("Spain 1.2 km", "Spain");
        cases.put("Italy", "Italy/Sardinia"); 
        cases.put("Russia (Kaliningrad)", "Russia (Soviet Union");
        cases.put("Russia", "Russia (Soviet Union");
        cases.put("Burkina Faso", "Burkina Faso (Upper Volta");
    }
    
    private static String findKey(Matcher matcher, int position) { //finds Country name to Country ID
        // Move to the desired token position
        for (int i = 1; i < position; i++) {
            if (!matcher.find()) {
                return null;
            }
        }
        return matcher.group();
    }

    public int getDistance(String country1, String country2) { //gets the distance between two countries
        String country1ID = countryNameToCountryID.get(country1); //converts country name to country ID
        String country2ID = countryNameToCountryID.get(country2); 
        
        if (country1ID != null && country2ID != null) { //if both countries are in the hashmap, get the distance between them
            HashMap<String, Integer> country1Distances = countryDistance.get(country1ID);
            
            if (country1Distances != null) { //if the distance is not null, return the distance
                Integer distance = country1Distances.get(country2ID);
                
                if (distance != null) {
                    return distance;
                }
            }
        }
        return -1; //if the distance is null, return -1
    }

    public List<String> findPath (String country1, String country2) {
        Set<String> visited = new HashSet<>(); //Hashset so that the visited nodes would not be repeated
        PriorityQueue<Node> minHeap = new PriorityQueue<>(); //minHeap to get the minimum distance
        HashMap<String, Integer> distances = new HashMap<>(); //HashMap to store the distances of each node
        HashMap<String, String> previous = new HashMap<>(); //HashMap to store the previous node of each node

        for (String node : country.keySet()) { //create nodes for every country and set the distance to infinity
            distances.put(node, Integer.MAX_VALUE);
        }
    
        distances.put(country1, 0); //initialize the distance of the start node to 0
        minHeap.add(new Node(country1, 0)); //add the start node to the priority queue
        List<String> visitedNodes = new ArrayList<>(); //List of visited nodes for printing later 
    
        // Process nodes in the priority queue
        while (!minHeap.isEmpty()) { 
            Node currentNode = minHeap.poll(); //get the node with the minimum distance
            String current = currentNode.node; //get the country of the node with the minimum distance

            if(distances.get(current) == 1){ //if the distance is 1, it means that the country is a border country
                continue;
            }

            if (!visited.contains(current)) { //if the node has not been visited, add it to the visited set
                visited.add(current);
            } else {
                continue; //if the node has been visited, skip it
            }
    
            if (country.containsKey(current)) { //if the country is in the hashmap, get its neighbors
                for (Map.Entry<String, Integer> neighbor : country.get(current).entrySet()) { //loop through the neighbors
                    String adjacentCountry = neighbor.getKey(); //get the neighbor
                    int weight = neighbor.getValue(); //get the distance between the source country and its neighbor
                    int newDistance = distances.get(current) + weight; //calculate the weight between the source country and its neighbor
    
                    if (newDistance < distances.getOrDefault(adjacentCountry, Integer.MAX_VALUE)) { //if the new distance is less than the current distance, update the distance
                        distances.put(adjacentCountry, newDistance); //update the distance
                        minHeap.add(new Node(adjacentCountry, newDistance)); //add the neighbor to the priority queue
                        previous.put(adjacentCountry, current); //update the previous node
                    }
                }
            }
            if (current.equals(country2)) { //if the current node is the destination node then finish the algorithm
                break;
            }
        }
        while (!country2.equals(country1)) { //loop through the previous nodes to get the path
            String prevCountry = previous.get(country2); //get the previous node
            if(distances.get(country2) == null || distances.get(prevCountry) == null){ //if the distance is null, it means that there is no path
                return null;
            }
            int distance = distances.get(country2) - distances.get(prevCountry); //calculate the distance between the current node and the previous node
            visitedNodes.add(prevCountry + " --> " + country2 + " (" + distance + " km.)"); //add the path to the list
            country2 = prevCountry; //update the current node to the previous node and continue iteration
        }
        Collections.reverse(visitedNodes); //reverse the list to get the correct path

        return visitedNodes; //return the list of paths
    }

    public void acceptUserInput() { 
            // Get user input for start and end countries
            try (Scanner scanner = new Scanner(System.in)) { //scanner to get user input
            generateSpecialCases(); //generate edge cases
                while(true){ //loop until the user enters EXIT
                System.out.print("Enter the start country: "); 
                String startCountry = scanner.nextLine().trim(); //get the start country
                if(cases.containsKey(startCountry)){ //if the start country is in the edge cases hashmap, translate and convert
                    startCountry = cases.get(startCountry);
                }
                if(startCountry.equals("EXIT")){ //if the user enters EXIT, exit the program
                    break;
                } else if (!country.containsKey(startCountry)){  //if the start country is not in the hashmap, ask the user to enter a valid country
                    System.out.println("Invalid country name. Please enter a valid country name.");
                    continue;
                }

                System.out.print("Enter the end country: ");
                String endCountry = scanner.nextLine().trim(); //get the end country
                if(cases.containsKey(endCountry)){ //if the end country is in the edge cases hashmap, translate and convert
                    endCountry = cases.get(endCountry);
                }
                if(endCountry.equals("EXIT")){ //if the user enters EXIT, exit the program
                    break;
                } else if (!country.containsKey(endCountry)){  //if the end country is not in the hashmap, ask the user to enter a valid country
                    System.out.println("Invalid country name. Please enter a valid country name.");
                    continue;
                }
                if(findPath(startCountry, endCountry) == null){ //if the path is null, it means that there is no path
                    System.out.println("No path found.");
                    continue;
                }
        
                // Display the result
                System.out.println("Shortest path from " + startCountry + " to " + endCountry + " is " + findPath(startCountry, endCountry));
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();
    }

    public HashMap<String, HashMap<String, Integer>> getCountry() {
        return country;
    }

public class Node implements Comparable<Node> { //Node class to store the country and its distance
    String node;
    int distance;

    Node(String node, int distance) { //node constructor
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) { //compare the distance between two nodes
        return Integer.compare(this.distance, other.distance);
    }
}

}
