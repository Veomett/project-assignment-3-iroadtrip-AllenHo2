import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.IOException; 
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 

/**
 * The IRoadTrip class represents a road trip planner that calculates the shortest path and distance between countries.
 * It uses a graph data structure to store the countries and their distances.
 */
public class IRoadTrip {

    private HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>();
  //  private HashMap<String, Integer> borderCountryList = new HashMap<String, Integer>();
    private HashMap<String, String> countryIDToCountryName = new HashMap<String, String>();   
    private HashMap<String, HashMap<String, Integer>> countryDistance= new HashMap<String, HashMap<String, Integer>>();
    private HashMap<String, Integer> countriesDistance = new HashMap<String, Integer>();

    //private HashMap<String, HashMap<String, Integer>> countryToCountryDistance = new HashMap<String, HashMap<String, Integer>>();
 //   private HashMap<String,String> specialCases = new HashMap<String,String>();
    //private int distance;
    Graph graphOfCountries = new Graph(country, countryDistance, countryIDToCountryName);

    public IRoadTrip(String[] args) {
        // Replace with your code
        if (args.length == 0) {
        try (BufferedReader reader = new BufferedReader(new FileReader("capdist.csv"))) {
            String line;
            int skipFirstLine = 0;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine == 0) {
                    skipFirstLine++;
                    continue;
                }
                String[] parts = line.split(",");
                String country1 = parts[1].trim();
                String country2 = parts[3].trim();
                Integer distance = Integer.parseInt(parts[4].trim());
                
                if (!countryDistance.containsKey(country1)) {
                    countriesDistance = new HashMap<String, Integer>();
                    countryDistance.put(country1, countriesDistance);
                } else {
                    countriesDistance = countryDistance.get(country1);
                }
                
                countriesDistance.put(country2, distance);
            }
            // System.out.println("WSM: " + countryDistance.get("WSM"));
            // System.out.println("Chinas: " + countryDistance.get("CHN"));
        //    System.out.println(countryDistance);
            } catch (IOException e) {
                    e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("state_name.tsv"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                     //   HashMap<String, String> countryIDToCountryName = new HashMap<String, String>();     
                     //this.countryIDToCountryName = new HashMap<String, String>();
                        Pattern pattern = Pattern.compile("\\b[\\S ]+\\b");
                        Matcher matcher = pattern.matcher(line);

                        if(matcher.find()){
                        String keyValue = getKey(matcher, 2);
                        String key = getKey(matcher, 2);
                            countryIDToCountryName.put(key, keyValue);         //Country ID to Country Name
                            // System.out.println("KeyGen: " + key);
                            // System.out.println("Value: " + countryIDToCountryName.get(key));
                           
                        }
                        // System.out.println("Hashmap of Country ID to Country Name: " + countryIDToCountryName);
                    }
        } catch (IOException e) {
          e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("borders.txt"))) {
           // HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>();
           //this.country = new HashMap<String, HashMap<String,Integer>>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=|;");
                String country1 = parts[0].trim(); // gets first country
                HashMap<String, Integer> borderCountryList = new HashMap<String, Integer>(); // create a new HashMap for each country1

                for (int i = 1; i < parts.length; i++) {
                    String[] stripBorders = parts[i].trim().split("\\s+\\d[\\d,]*\\s+km");
                    String borderingCountry = stripBorders[0].trim();

                    if (borderingCountry != null) {
                        borderCountryList.put(borderingCountry, getDistance(country1, borderingCountry));
                    }
                }

                country.put(country1, borderCountryList);
                System.out.println(country1 + " " + borderCountryList);
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

   // private Map
    private static String getKey(Matcher matcher, int position) {
        // Move to the desired token position
        for (int i = 1; i < position; i++) {
            if (!matcher.find()) {
                return null;
            }
        }
        return matcher.group();
    }
    // public HashMap<String, List<String>> graph() {
        
    // }

    public int getDistance(String country1, String country2) {
        String country1ID = countryIDToCountryName.get(country1);
        String country2ID = countryIDToCountryName.get(country2);
        
        if (country1ID != null && country2ID != null) {
            HashMap<String, Integer> country1Distances = countryDistance.get(country1ID);
            
            if (country1Distances != null) {
                Integer distance = country1Distances.get(country2ID);
                
                if (distance != null) {
                 //   System.out.println("Distance: " + distance);
                    return distance;
                }
            }
        }
        
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        if(country1 != null && country2 != null){
        List<String> shortestPath = graphOfCountries.dijkstra(country1, country2);
        return shortestPath;
        }
        return null;
    }


    public void acceptUserInput() {
        try {
          //  Graph g = new Graph();
           // g.setCountries(country, );
            // Example: Check if USA has a border with Canada
           // graphOfCountries.addBorder("USA", "Canada");
           graphOfCountries = new Graph(country, countryDistance, countryIDToCountryName);
            //xgraphOfCountries.addBorder("USA", "Mexico");

            // Example: Read distances information from file or other sources
            graphOfCountries.addDistance("USA", "Canada", countryDistance.get("USA").get("Canada"));
            graphOfCountries.addDistance("USA", "Mexico", 1500);

            // Get user input for start and end countries
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter the start country: ");
                String startCountry = scanner.nextLine().trim();

                System.out.print("Enter the end country: ");
                String endCountry = scanner.nextLine().trim();

                if(startCountry.equalsIgnoreCase("EXIT") || endCountry.equalsIgnoreCase("EXIT")){
                    scanner.close();
                    System.exit(1);
                }
                // Find the shortest path
        
                // Display the result
                System.out.println("Shortest path from " + startCountry + " to " + endCountry + ": " + findPath(startCountry, endCountry) + " distance is ");
            } catch (Exception e) {
                e.printStackTrace();
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

    public HashMap<String, String> getCountryIDToCountryName() {
        return countryIDToCountryName;
    }

    public HashMap<String, HashMap<String, Integer>> getCountryDistance() {
        return countryDistance;
    }

    // public HashMap<String, HashMap<String, Integer>> getCountryToCountryDistance() {
    //     return countryToCountryDistance;
    // }
}

