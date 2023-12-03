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

public class IRoadTrip {

    public IRoadTrip(String[] args) {
        // Replace with your code
        if (args.length == 1) {
        try (BufferedReader reader = new BufferedReader(new FileReader("borders.txt"))) {
            HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=|;");
                String country1 = parts[0].trim();
                HashMap<String, Integer> borderCountryList = new HashMap<String, Integer>();
                for(int i = 1; i < parts.length; i++){
                String borderingCountry = parts[i].trim();
                    if (borderCountryList == null) {
                        country.put(country1, borderCountryList);
                    } else if(borderingCountry != null){
                    borderCountryList.put(borderingCountry, 0);
                    }
                country.put(country1, borderCountryList);
                }
                System.out.println(country1 + " " + borderCountryList ); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else if (args.length == 2){
        try (BufferedReader reader = new BufferedReader(new FileReader("state_name.tsv"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        HashMap<String, String> countryIDToCountryName = new HashMap<String, String>();     
                        Pattern pattern = Pattern.compile("\\b[\\S ]+\\b");
                        Matcher matcher = pattern.matcher(line);

                        if(matcher.find()){
                        String key = getKey(matcher, 2);
                        String keyValue = getKey(matcher, 2);
                            countryIDToCountryName.put(key, keyValue);
                            System.out.println("KeyGen: " + key);
                            System.out.println("Value: " + countryIDToCountryName.get(key));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
    } else if (args.length == 0){
            try (BufferedReader reader = new BufferedReader(new FileReader("capdist.csv"))) {
             HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>();
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
                String distance = parts[4].trim();
                HashMap<String, Integer> countriesDistance = new HashMap<String, Integer>();
                countriesDistance.put(country2, Integer.parseInt(distance));
                country.put(country1, countriesDistance);
                System.out.println(country1 + " " + countriesDistance ); 
            }
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }
    }

    private static String getKey(Matcher matcher, int position) {
        // Move to the desired token position
        for (int i = 1; i < position; i++) {
            if (!matcher.find()) {
                // Handle the case where there are not enough tokens
                return null;
            }
        }
        return matcher.group();
    }
    // public HashMap<String, List<String>> graph() {
        
    // }

    public int getDistance (String country1, String country2) {
        // Replace with your code
        
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        int distance = getDistance(country1, country2);
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
    }


    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);
        // FileReader fr=null; 
        // try
        // { 
        //     fr = new FileReader("borders.txt"); 
        // } 
        // catch (FileNotFoundException fe) 
        // { 
        //     System.out.println("File not found"); 
        // } 
        
  
        a3.acceptUserInput();
    }

}

