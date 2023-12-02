import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.IOException; // Add this import statement

public class IRoadTrip {

    public IRoadTrip(String[] args) {
        // Replace with your code
        HashMap<String, List<String>> country = new HashMap<String, List<String>>();
     //   HashMap<String, Integer> countriesBorder = new HashMap<String, Integer>();
        if (args.length == 0) {
        try (BufferedReader reader = new BufferedReader(new FileReader("borders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=|;");
                String country1 = parts[0].trim();
                ArrayList<String> borderCountryList = new ArrayList<>();
                for(int i = 1; i < parts.length; i++){
                    //parts = re.split(pattern, parts[i].trim);
                    //String borderCountry = parts[i].trim();
               // borderCountryList = country.put(country1, parts[i].trim());
                   // borderCountryList = country.get(parts[i].trim());
                    // borderCountryList.add(borderCountry);
                    // if (borderCountryList == null) {
                    //     borderCountry = parts[1];
                    //     country.put(country1, borderCountryList);
                    // } 
                if (borderCountryList == null) {
                    //borderCountry = parts[1];
                    country.put(country1, borderCountryList);
                } 
                borderCountryList.add(parts[i].trim());
                country.put(country1, borderCountryList);
                }
                
             //   String borderCountry = parts[1].trim();

            

             //   int distance = Integer.parseInt(parts[2]);

               // HashMap<String, Integer> countriesBorder = country.get(country1);

               // countriesBorder.put(country2, distance);

                System.out.println(country1 + " " + borderCountryList ); 
               // System.out.println(country1.get(borderCountry));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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

