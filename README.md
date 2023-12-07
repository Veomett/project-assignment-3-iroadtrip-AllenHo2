# CS 245 (Fall 2023) - Assignment 3 - IRoadTrip

Can you plan the path for a road trip from one country to another?

Change the java source code, but do not change the data files. See Canvas for assignment details.



# Simple ReadMe
## Project 3: International Road Trip

## Abstract
My IRoadTrip project receives user input and finds the shortest distance between 2 countries.

## Table of Contents

- [CountryHashMap](#CountryHashMap)
- [FindingPath](#FindingPath)
- [Connect](#connect)


  
## CountryHashMap

```java
private HashMap<String, HashMap<String, Integer>> country = new HashMap<String, HashMap<String, Integer>>();
```

My country HashMap reads in the borders file. During that process, I used to translate the border.txt names into IDs through the state_name's HashMap

```java
private HashMap<String, String> countryNameToCountryID = new HashMap<String, String>(); 
```

I then use these ID's to get the corresponding country & their adjacent country's distance from the capdist.csv's nested HashMap

```java
 private HashMap<String, HashMap<String, Integer>> countryDistance= new HashMap<String, HashMap<String, Integer>>();
 private HashMap<String, Integer> countriesDistance = new HashMap<String, Integer>();
```
I then send the distance back to the country HashMap and insert the correct distance for each corresponding country-adjacentCountry pair.

## FindingPath

<li>I load my country graph into the findPath method and begin incorporating Dijkstra's Algorithm on my HashMap graph</li>
<li>I created a Node Class that would be assigned a country's name and distance.</li>
<li>I then set all the distances to infinity/Integer.MAX_VALUE except for the source node</li>
<li>I then put these nodes into a minHeap by comparing the weights/distances of each</li>
<li>Finally, my findPath method spits out all of the previous nodes visited as a list</li>

Sample Snippet:

```java
 public List<String> findPath (String country1, String country2) {
        Set<String> visited = new HashSet<>(); 
        PriorityQueue<Node> minHeap = new PriorityQueue<>(); 
        HashMap<String, Integer> distances = new HashMap<>(); 
        HashMap<String, String> previous = new HashMap<>(); 

        for (String node : country.keySet()) { 
            distances.put(node, Integer.MAX_VALUE);
        }
}
```

## Connect
<a href="https://www.linkedin.com/in/allen-ho-b67a6725b/"><img width="58" alt="Linkedin_icon" src="https://github.com/AllenHo2/project02-Elevator/assets/112123839/38209676-0df8-4cdf-a99e-e172deb63854" href="https://www.linkedin.com/in/allen-ho-b67a6725b/"></img></a>
<a href="https://github.com/AllenHo2"> <img width="58" alt="Github_icon" src="https://github.com/AllenHo2/project02-Elevator/assets/112123839/e56b00ce-0fb2-4ee1-bde1-2aec3c393ecd" href="https://github.com/AllenHo2"></img></a>
