import java.io.*;
import java.util.*;

public class Labyrinth {

	Graph graph;
	private int entrance[] = new int[2];
	private int exit[] = new int[2];
	
	private Stack<Node> pathstack = new Stack<Node>();
	

	private int scale, width, length, bbombs, abombs;

	
	Boolean markarray[];

	public Labyrinth(String inputFile) throws LabyrinthException {

		BufferedReader in;
		String line;

		int count = 0;
		int charcount = 0;
		String fileName;

		fileName = inputFile;

		Iterator<String> iter;

		String nodeType;
		String edgeType = null;

		String inputchar;
		int edgecount = 0;

		try {
			in = new BufferedReader(new FileReader(fileName));

			scale = Integer.valueOf(in.readLine());
			width = Integer.valueOf(in.readLine());
			length = Integer.valueOf(in.readLine());
			bbombs = Integer.valueOf(in.readLine());
			abombs = Integer.valueOf(in.readLine());

			graph = new Graph((length * width));
			markarray = new Boolean[length * width];

			System.out.println("No. of bbombs: " + bbombs);
			System.out.println("No. of abombs: " + abombs);
			
			for (int i = 0; i < length * width; i++) {
				markarray[i] = false;
			}

			for (int i = 0; i < (2 * length) - 1; i++) {
				line = in.readLine();
				System.out.println("______________________________________");
				for (int j = 0; j < (2 * width) - 1; j++) {
					System.out.println("(i, j) is (" + i + ", " + j + ")");
					inputchar = Character.toString(line.charAt(j));
					// System.out.println("---------- " + inputchar + " ----------");
					if (inputchar.equals("b")) {
						// System.out.println("Entrance put in");
						nodeType = "entrance";
						entrance[0] = i;
						entrance[1] = j;
					} else if (inputchar.equals("+")) {
						nodeType = "room";
					} else if (inputchar.equals("x")) {
						// System.out.println("\n\n\n\n-------------------------End node is at (" + i +
						// ", " + j + ")");
						nodeType = "exit";
						exit[0] = i;
						exit[1] = j;
					} else if (inputchar.equals("h")) {
						edgeType = "wall"; // "horizontal normal brick wall"; // "wall"
					} else if (inputchar.equals("H")) {
						edgeType = "thickWall"; // "horizontal thick brick wall"; // "thickWall"
					} else if (inputchar.equals("m")) {
						edgeType = "metalWall"; // "horizontal metal wall"; // "metalWall"
					} else if (inputchar.equals("v")) {
						edgeType = "wall"; // "vertical normal brick wall"; // "wall"
					} else if (inputchar.equals("V")) {
						edgeType = "thickWall"; // "vertical thick brick wall"; // "thickWall"
					} else if (inputchar.equals("M")) {
						edgeType = "metalWall"; // "vertical metal wall"; // "metalWall"
					} else if (inputchar.equals("-")) {
						edgeType = "corridor"; // "horizontal corridor"; // "corridor"
					} else if (inputchar.equals("|")) {
						edgeType = "corridor"; // "vertical corridor"; // "corridor"
					} else if (inputchar.equals(" ")) {
						edgeType = "rock"; // "unbreakable solid wall"; // "rock" // When the edge is NULL
					}

					// Horizontal line with the nodes
					if ((i % 2 == 0)) {
						if (j % 2 != 0) {

							System.out.println("1st (i, j) is (" + i + ", " + j + ")");

							Node u = new Node((i / 2) * (width) + ((j - 1) / 2));
							Node v = new Node((i / 2) * (width) + ((j + 1) / 2));

							u.setMark(false);
							v.setMark(false);

							graph.insertEdge(u, v, edgeType);
							System.out.println(
									edgeType + " edge added between Node " + u.getName() + " and Node " + v.getName());
							edgecount++;

						}
						// Horizontal line without the nodes
					} else {
						if ((j % 2) == 0) {

							System.out.println("2nd (i, j) is (" + i + ", " + j + ")");

							Node u = new Node(((i - 1) / 2) * (width) + (j / 2));
							Node v = new Node(((i + 1) / 2) * (width) + (j / 2));

							u.setMark(false);
							v.setMark(false);

							graph.insertEdge(u, v, edgeType);
							System.out.println(
									edgeType + " edge added between Node " + u.getName() + " and Node " + v.getName());
							edgecount++;
						}
					}
					System.out.println("Edge count is " + edgecount);
				}
			}

		} catch (IOException e) {
			System.out.println("Error reading input file: " + fileName);
		}
	}

	public Graph getGraph() throws LabyrinthException {
		return graph;
	}

	public Iterator solve() {

		Node startNode = new Node(((entrance[0] / 2) * width) + (entrance[1] / 2));
		Node endNode = new Node(((exit[0] / 2) * width) + (exit[1] / 2));

		System.out.println("Entrance node is Node " + startNode.getName());
		System.out.println("End node is Node " + endNode.getName());

		// return null;
		boolean isTherePath = path(startNode.getName(), endNode, bbombs, abombs, pathstack);

		if (!(pathstack.iterator().hasNext())) {
			return null;
		}  else if (pathstack.iterator() == null) {
			return null;
		} else {
			return pathstack.iterator();
		}
	}

//	private boolean path(int startName, int endName, int bbombs, int abombs) {
//		System.out.println("___________________________________________________________________________________");
//		System.out.println("No. of bbombs is " + bbombs + " and the No. of abombs is " + abombs);
//		
//		
//		Node startNode = graph.getNode(startName);
//		Node endNode = graph.getNode(endName);
//		
//		
//		boolean bbombMarker = false;
//		boolean abombMarker = false;
//		boolean canGo = false;
//		boolean corridor = false;
//		Stack tempStack;
//		startNode.setMark(true);
//		markarray[startName] = true;
//		
//		pathstack.push(startNode);
//		
//		if (startName == endName) {
//			System.out.println("Found exit");
//			return true;
//		} else {
//			
//			Iterator<Edge> edges = graph.incidentEdges(startNode);			
//			
//			while (edges.hasNext()) {
//				Edge tempEdge = edges.next();
//				
//				System.out.println("Current node is Node " + startNode.getName() + " " + markarray[startNode.getName()] + " and secondEndpoint is Node " + tempEdge.secondEndpoint().getName() + " " + markarray[tempEdge.secondEndpoint().getName()]);
//				
//				if (markarray[tempEdge.secondEndpoint().getName()] == true) {
//					continue;
//				}
//				
//				System.out.println("Edge between Node " + startNode.getName() + " and Node " + tempEdge.secondEndpoint().getName() + " is " + tempEdge.getType());
//				if (tempEdge.getType() == "corridor") {
//					corridor = true;
//				} else if ((tempEdge.getType() == "wall") && (bbombs > 0)) {
//					canGo = true;
//				} else if ((tempEdge.getType() == "thickWall") && (bbombs > 1)) {
//					canGo = true;
//				} else if ((tempEdge.getType() == "metalWall") && (abombs > 0)) {
//					canGo = true;
//				}
//				
//				System.out.println("canGo is " + canGo + " corridor is " + corridor);
//				if (canGo || corridor) {
//					
//					Node tempNode = tempEdge.secondEndpoint();
//					
//					if (tempEdge.getType() == "wall") {
//						System.out.println("Wall Blown!!!");
//						bbombs = bbombs - 1;
//					} else if (tempEdge.getType() == "thickWall") {
//						System.out.println("Thick wall Blown!!!");
//						bbombs = bbombs - 2;
//					} else if (tempEdge.getType() == "metalWall") {
//						System.out.println("Metal wall Blown!!!");
//						abombs = abombs - 1;
//					}
//					
//					if (markarray[tempNode.getName()] == false) {
//						if (path(tempNode.getName(), endName, bbombs, abombs)) {
//							return true;
//						}
//					}
//				}
//				markarray[tempEdge.secondEndpoint().getName()] = false;
//				tempEdge.secondEndpoint().setMark(false);
//				System.out.println("Node " + pathstack.pop().getName() + " is being removed");
//				System.out.println("--------------- Going back a call ---------------");
//			}
//			
//		}
//		return false;
//		
//	}
	
	
	private boolean path(int startName, Node endNode, int bbombs, int abombs, Stack<Node> pathstack) {
		System.out.println("________________________________________________________________________");
		// graph.getNode(current.getName()).setMark(true);

		boolean wallBlown = false;
		boolean thickWallBlown = false;
		boolean metalWallBlown = false;
		
		Edge edgeList[] = new Edge[4];
		int count = 0;
		Node current = graph.getNode(startName);

		boolean canGo = false;
		
		current.setMark(true);
		markarray[current.getName()] = true;

		if (!graph.getNode(current.getName()).equals(current)) {
			System.out.println(
					"Not equal\nCurrent is " + current + " and graph.getNode() is " + graph.getNode(current.getName()));
		}

		System.out.println("Mark changed to true");
		System.out.println(
				"Current node is Node " + current.getName() + " the mark is " + Boolean.toString(current.getMark()));

		pathstack.push(current);

//		System.out.println("The mark of Node 0 is " + Boolean.toString(graph.getNode(0).getMark()));

		

		Iterator<Edge> iter = graph.incidentEdges(current);
		Edge edge = null;
		while (iter.hasNext()) {

			edge = iter.next();
//			edgeList[count] = edge;
			count++;
			
			System.out.println("Current node is Node " + current.getName() + " " + markarray[current.getName()]
					+ " and secondEndpoint is Node " + edge.secondEndpoint().getName() + " "
					+ markarray[edge.secondEndpoint().getName()]);
			if (markarray[edge.secondEndpoint().getName()] == true) {
				continue;
			}
			
			if (!iter.hasNext()) {
				System.out.println("Iter for Node " + current.getName() + " does not have a next");
			}
			System.out.println("No. of bbombs are " + bbombs + " and the No. of abombs are " + abombs);
			System.out.println("Going to check for canGo, the edgeType is "+ edge.getType());
			if (edge.getType() == "corridor") {
				System.out.println("Walking through a corridor between Node " + edge.firstEndpoint().getName() + " and Node " + edge.secondEndpoint().getName());
				canGo = true;
			} else if ((edge.getType() == "wall") && (bbombs > 0)) {
				bbombs = bbombs -1 ;
				System.out.println("BLOWS UP A NORMAL WALL!! between Node " + edge.firstEndpoint().getName() + " and Node " + edge.secondEndpoint().getName());
				canGo = true;
				wallBlown = true;
			} else if ((edge.getType() == "thickWall") && (bbombs > 1)) {
				bbombs = bbombs - 2;
				System.out.println("BLOWS UP A THICK NORMAL WALL!! between Node " + edge.firstEndpoint().getName() + " and Node " + edge.secondEndpoint().getName());
				canGo = true;
				thickWallBlown = true;
			} else if ((edge.getType() == "metalWall") && (abombs > 0)) {
				abombs = abombs - 1;
				System.out.println("BLOWS UP A METAL WALL!! between Node " + edge.firstEndpoint().getName() + " and Node " + edge.secondEndpoint().getName());
				canGo = true;
				metalWallBlown = true;
			} else {
				canGo = false;
			}
			
			if ((endNode.getName() == current.getName()) && (canGo)) {
				System.out.println("Labyrinth solved");
				return true;
			} else if (markarray[edge.secondEndpoint().getName()] == true) {
				continue;
			} else if (!canGo) { 
				continue;
			} else {
				System.out.println("Enters here");
				if (path(edge.secondEndpoint().getName(), endNode, bbombs, abombs, pathstack)) {
					return true;
				}
				System.out.println("Now at Node " + current.getName());
				if (wallBlown) {
					bbombs = bbombs + 1;
				} else if (thickWallBlown) {
					bbombs = bbombs + 2;
				} else if (metalWallBlown) {
					abombs = abombs + 1;
				}
			}
			
		}
		if (pathstack.size() == 0) {
			System.out.println("path stack is empty");
		} else {
			System.out.println("Path stack size is " + pathstack.size());
		}
		if (wallBlown) {
			System.out.println("It's one of these------------- Wall Blown");
			bbombs = bbombs + 1;
		} else if (thickWallBlown) {
			System.out.println("It's one of these------------- Thick Wall Blown");
			bbombs = bbombs + 2;
		} else if (metalWallBlown) {
			System.out.println("It's one of these------------- Metal Wall Blown");
			abombs = abombs + 1;
		}
		Node removed = pathstack.pop();
		wallBlown = false;
		thickWallBlown = false;
		metalWallBlown = false;
		graph.getNode(removed.getName()).setMark(false);
		markarray[removed.getName()] = false;
		System.out.println("-------------- Going back a call --------------");
		return false;
	}
}

// The Solve() method kinda works, it moves and avoids the already marked (Node
// doesn't get set to marked??) nodes,
// but it moves too linearly, need to fix that by putting rules such as: we have
// limited bombs and by that find the path
