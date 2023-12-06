import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
    private HashMap<String, String> countryIDToCountryName = new HashMap<String, String>();   
    private HashMap<String, HashMap<String, Integer>> countryDistance= new HashMap<String, HashMap<String, Integer>>();
    private HashMap<String, Integer> countriesDistance = new HashMap<String, Integer>();
    private HashMap<String, String> cases = new HashMap<String, String>();
    Graph graphOfCountries = new Graph(country);

    public IRoadTrip(String[] args) {
        if (args.length == 0) {
            generateSpecialCases();
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
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=|;");
                String country1 = parts[0].trim(); // gets first country
                if(cases.containsKey(country1)){
                     country1 = cases.get(country1);
                 }
                HashMap<String, Integer> borderCountryList = new HashMap<String, Integer>(); // create a new HashMap for each country1

                for (int i = 1; i < parts.length; i++) {
                    String[] stripBorders = parts[i].trim().split("\\s+\\d[\\d,]*\\s+km");
                    String borderingCountry = stripBorders[0].trim();
                    if(cases.containsKey(borderingCountry)){
                        borderingCountry = cases.get(borderingCountry);
                    } 
                     if (borderingCountry != null) {
                        borderCountryList.put(borderingCountry, getDistance(country1, borderingCountry));
                    }
                }

                country.put(country1, borderCountryList);
               System.out.println(country1 + " " + borderCountryList);
               // System.out.println(cases);
            
            }
            //System.out.println(country);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    private void generateSpecialCases(){
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
        cases.put("Iran", "Iran (Persia)");
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
    }
    
    private static String getKey(Matcher matcher, int position) {
        // Move to the desired token position
        for (int i = 1; i < position; i++) {
            if (!matcher.find()) {
                return null;
            }
        }
        return matcher.group();
    }

    public int getDistance(String country1, String country2) {
        String country1ID = countryIDToCountryName.get(country1);
        String country2ID = countryIDToCountryName.get(country2);
        
        if (country1ID != null && country2ID != null) {
            HashMap<String, Integer> country1Distances = countryDistance.get(country1ID);
            
            if (country1Distances != null) {
                Integer distance = country1Distances.get(country2ID);
                
                if (distance != null) {
                //    System.out.println("Distance: " + distance);
                    return distance;
                }
            }
        }
        
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        Map<String, Integer> distancesFromStart = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distancesFromStart::get));

        // Initialize distances and queue
        for (String country : country.keySet()) {
            distancesFromStart.put(country, country.equals(country1) ? 0 : Integer.MAX_VALUE);
            previous.put(country, null);
            queue.add(country);
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(country2)) {
                // Found the shortest path to the destination
                List<String> path = new ArrayList<>();
                while (previous.get(current) != null) {
                    path.add(current);
                    current = previous.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (String neighbor : country.get(current).keySet()) {
                int newDistance = distancesFromStart.get(current) + country.get(current).get(neighbor);
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


    public void acceptUserInput() {
        try {
            // Get user input for start and end countries
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter the start country: ");
                String startCountry = scanner.nextLine().trim();

                System.out.print("Enter the end country: ");
                String endCountry = scanner.nextLine().trim();
        
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


    static class CountryDistancePair implements Comparable<CountryDistancePair> {
        private String country;
        private int distance;

        public CountryDistancePair(String country, int distance) {
            this.country = country;
            this.distance = distance;
        }

        public String getCountry() {
            return country;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(CountryDistancePair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}

