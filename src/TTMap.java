import java.io.*;
import java.util.*;

public class TTMap {
    private TTGraph G;
    private TTNode start;
    private TTNode end;
    private Stack S;
    private int budget;
    private int toll, comp;

    public TTMap(String labFile) throws MapException{
	BufferedReader in;
	String line;
	int n, labWidth;
	int startNode, endNode;

	try {
	    in = new BufferedReader(new FileReader(labFile));
	    line = in.readLine();          // scale
	    line = in.readLine();          // start node
	    startNode = Integer.parseInt(line);
	    line = in.readLine();
	    endNode = Integer.parseInt(line);
	    line = in.readLine();
	    budget = Integer.parseInt(line);
	    line = in.readLine();
	    labWidth = Integer.parseInt(line);
	    line = in.readLine();          // length;
	    n = Integer.parseInt(line)*labWidth;
	    line = in.readLine();
	    toll = Integer.parseInt(line);
	    line = in.readLine();
	    comp = Integer.parseInt(line);

	    G = new TTGraph(n);
	    start = G.getNode(startNode);
	    end = G.getNode(endNode);
		
	    line = in.readLine();
            int pos = 0, i; 
	    boolean even = true;
	    while (line != null) {
		if (even) { //Process row of nodes
		    for (i = 0; i < line.length(); ++i) 
			switch (line.charAt(i)) {
			case 'T': G.insertEdge(G.getNode(pos+(i-1)/2),
					  G.getNode(pos+(i+1)/2),1);
			          break;
			case 'C': G.insertEdge(G.getNode(pos+(i-1)/2),
					  G.getNode(pos+(i+1)/2),-1);
			          break;
			case 'F': G.insertEdge(G.getNode(pos+(i-1)/2),
					  G.getNode(pos+(i+1)/2),0);
			          break;
			}
		}
		else {      //Process row of vertical free roads
		    for (i = 0; i < line.length(); ++i)
			switch(line.charAt(i)) {
			case 'F': G.insertEdge(G.getNode(pos+i/2),
					  G.getNode(pos+i/2+labWidth),0);
				  break;
			case 'T': G.insertEdge(G.getNode(pos+i/2),
					  G.getNode(pos+i/2+labWidth),1);
					  break;
			case 'C': G.insertEdge(G.getNode(pos+i/2),
					  G.getNode(pos+i/2+labWidth),-1);
					  break;
			}
		}
		even = !even;
		if (even) pos = pos + labWidth;
		line = in.readLine();
	    }
	}
	catch (IOException e) {
	    System.out.println("Error reading input file");
	    throw new MapException("Error reading file");
	}
	catch (GraphException f) {
	    System.out.println("Invalid lab "+f.getMessage());
	    throw new MapException("Invalid file");
	}
    }


    public TTGraph getGraph() {
	return G;
    }


    public int getToll() {return toll;}

    public int getComp() {return comp;}

    public int getStartingNode () {
	return start.getName();
    }
	
    public int getDestinationNode() {
	return end.getName();
    }
	
    public int getInitialMoney() {
	return budget;
    }
}
