import java.util.*;
import java.io.*;

public class TestMap {

  /* -------------------------- */
  public  static void main(String[] args) {
  /* -------------------------- */

    String fileName = args[0]; 
    DrawMap display;

    TTEdge edge;
    int walls = 0;
    Iterator path;

    if (args.length != 2) {
	System.out.println("Usage: java TestMap mapFile TestNumber");
	System.exit(0);
    }

    display = new DrawMap(args[0]);


    try {

	TTMap TMap = new TTMap(fileName);
	TTGraph G = TMap.getGraph();
	boolean failed = false;
	int budget = TMap.getInitialMoney();
	int toll = TMap.getToll();
	int comp = TMap.getComp();

	RoadMap L = new RoadMap(fileName);
	path = L.findPath(L.getStartingNode(), L.getDestinationNode(), 
			  L.getInitialMoney());

	if (path == null || !path.hasNext()) {
            System.out.println("     FAILED TEST: Algorithm could not find path");
	    System.out.println("");
	    System.exit(0);
	}

        else {
            Node prev = (Node)path.next();
	    Node first = prev;
	    Node curr = null;

	    while (path.hasNext()) {
		curr = (Node)path.next();
		//display.drawEdge(prev,curr);

		if (!G.areAdjacent(G.getNode(prev.getName()),G.getNode(curr.getName())) && 
		    !G.areAdjacent(G.getNode(curr.getName()),G.getNode(prev.getName()))) {
		    if (!failed) {
			System.out.println("     FAILED TEST: path uses inexistedt edge");
			failed = true;
		    }
		}
		else {
		    edge = G.getEdge(G.getNode(prev.getName()),G.getNode(curr.getName()));
		    if (edge.getType() == 1) budget = budget - toll;
		    else if (edge.getType() == -1) budget = budget - comp;

		    if (budget < 0) {
			if (!failed) {
			    System.out.println("    FAILED TEST: path is too expensive");
			    failed = true;
			}
		    }
		}
		display.drawEdge(prev,curr);
		prev = curr;
	    }

	    if ((first.getName() != TMap.getStartingNode() && 
		 curr.getName() != TMap.getStartingNode()) ||
		(first.getName() != TMap.getDestinationNode() && 
		 curr.getName() != TMap.getDestinationNode()))
		    System.out.println("     FAILED TEST: path does not start and end at required rooms");
	    else    System.out.println("     TEST PASSED");

	    System.out.println("");
	     BufferedReader in = new BufferedReader(
					new InputStreamReader(System.in));
	     System.out.println("Press a key to continue");
	     String line = in.readLine();
	     System.exit(0);

	}

    }
    catch (MapException e) {
	System.out.println("     ERROR. Problem creating the map");
    }
    catch (GraphException gf) {
	System.out.println("     FAILED TEST: invalid path");
    }
    catch (IOException ie) {
	System.out.println("     ERROR reading input");
    }
    System.out.println(" ");

  }
}
