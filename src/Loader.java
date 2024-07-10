// TITLE: Loader.java
// AUTHOR: IHSAN ARDA CAGLAYAN

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public EdgeWeightedGraph graph;
    public int locationKnight;
    public int locationGold;
    public int rows;
    public int cols;

    public Loader(String filepath) {
        // DIGEST FILE
        List<String> lines = new ArrayList<String>();

        try (FileInputStream fis = new FileInputStream(filepath);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // GET CONFIGS
        String cfg = lines.get(0);
        int delimiterIndex = cfg.indexOf(' ');
        rows = Integer.parseInt(cfg.substring(0, delimiterIndex));
        cols = Integer.parseInt(cfg.substring(delimiterIndex + 1));

        // BUILD GRAPH
        // THE GRAPH WE DECLARED IS EdgeWeightedGraph. IT IS UNDIRECTED GRAPHY BY DEFINITION HOWEVER IT HAS WEIGHT DEFINITION.
        // IF WE PUT 0 AS A WEIGHT FOR ALL EDGES, WE GET UNDIRECTED UNWEIGHTED GRAPH
        graph = new EdgeWeightedGraph(rows * cols);

        // HORIZONTAL EDGE BUILDING
        //  c00 c01.. c0m
        //   .         .
        //   .         .
        //   .         .
        //   .         .
        //  cn0 ..... cnm
        // BASICALLY, WE ARE ESTABLISHING RELATION BETWEEN ALL cnm <--> cn(m+1)
        for (int i = 1; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length() - 1; j++) {
                if (lines.get(i).charAt(j) != 'T' && lines.get(i).charAt(j + 1) != 'T') {
                    int indexNormalizer = (i - 1) * (lines.get(i).length());
                    graph.addEdge(new Edge(indexNormalizer + j, indexNormalizer + j + 1, 0));
                }
            }
        }

        // VERTICAL EDGE BUILDING
        //  c00 c01.. c0m
        //   .         .
        //   .         .
        //   .         .
        //   .         .
        //  cn0 ..... cnm
        // BASICALLY, WE ARE ESTABLISHING RELATION BETWEEN ALL cnm <--> c(n+1)m
        for (int i = 1; i < lines.size() - 1; i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) != 'T' && lines.get(i + 1).charAt(j) != 'T') {
                    int indexNormalizer1 = (i - 1) * (lines.get(i).length());
                    int indexNormalizer2 = (i) * (lines.get(i).length());
                    graph.addEdge(new Edge(indexNormalizer1 + j, indexNormalizer2 + j, 0));
                }
            }
        }

        // SAVE KNIGHT LOCATION
        for (int i = 1; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'K') {
                    locationKnight = ((i - 1) * (lines.get(i).length())) + j;
                }
            }
        }

        // SAVE GOLD LOCATION
        for (int i = 1; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'G') {
                    locationGold = ((i - 1) * (lines.get(i).length())) + j;
                }
            }
        }
    }
}
