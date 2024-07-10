import edu.princeton.cs.algs4.Edge;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Loader loader = new Loader(args[0]);
        PathFinder pathFinder = new PathFinder(loader.graph, loader.locationKnight, loader.cols, loader.rows);
        // CHECK IF THERE IS PATH
        if (pathFinder.hasPathTo(loader.locationGold)) {
            // CALCULATE AND SAVE THE PATH
            Iterable<Edge> path = pathFinder.pathTo(loader.locationGold);

            // FORMAT OUTPUT RESULT
            List<String> pathStrings = new ArrayList<String>();
            for (Edge edge : path) {
                pathStrings.add(edge.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pathStrings.size() + " steps \n\n");
            for (int i = 0; i < pathStrings.size(); i++) {
                int delimiterIndex1 = pathStrings.get(i).indexOf('-');
                int delimiterIndex2 = pathStrings.get(i).indexOf(' ');

                if(i <= pathStrings.size() - 2){
                    stringBuilder.append("c" + (int) Math.ceil((float) Integer.parseInt(pathStrings.get(i).substring(0, delimiterIndex1)) / loader.cols) + "," + ((Integer.parseInt(pathStrings.get(i).substring(0, delimiterIndex1)) % loader.cols) + 1) + "->");
                }
                else {
                    stringBuilder.append("c" + (int) Math.ceil((float) Integer.parseInt(pathStrings.get(i).substring(0, delimiterIndex1)) / loader.cols) + "," + ((Integer.parseInt(pathStrings.get(i).substring(0, delimiterIndex1)) % loader.cols) + 1) + "->" +
                            "c" + (int) Math.ceil((float) Integer.parseInt(pathStrings.get(i).substring(delimiterIndex1 + 1, delimiterIndex2)) / loader.cols) + "," + ((Integer.parseInt(pathStrings.get(i).substring(delimiterIndex1 + 1, delimiterIndex2)) % loader.cols) + 1));
                }
            }
            System.out.println(stringBuilder.toString());
        } else {
            System.out.println("No path to the target.");
        }
    }
}