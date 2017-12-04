import java.io.*;
import java.util.*;

public class Labyrinth {

	Graph graph;
	private int entrance[] = new int[2];
	private int exit[] = new int[2];

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
		Stack<Node> pathstack = new Stack<Node>();
		boolean isTherePath = path(startNode.getName(), endNode, bbombs, abombs, pathstack);

		if (pathstack.iterator() == null) {
			return null;
		} else {
			return pathstack.iterator();
		}
	}

	private boolean path(int startName, Node endNode, int bbombs, int abombs, Stack<Node> pathstack) {
		System.out.println("________________________________________________________________________");
		// System.out.println("Current node is Node " + current.getName() + " the mark
		// is " + Boolean.toString(current.getMark()));
		// graph.getNode(current.getName()).setMark(true);

		Node current = graph.getNode(startName);

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

		System.out.println("The mark of Node 0 is " + Boolean.toString(graph.getNode(0).getMark()));

		if (endNode.getName() == current.getName()) {
			System.out.println("Labyrinth solved");
			return true;
		}

		Iterator<Edge> iter = graph.incidentEdges(current);

		while (iter.hasNext()) {

			Edge edge = iter.next();
			if (!iter.hasNext()) {
				System.out.println("Iter does not have a next");
			}
			System.out.println("Current node is Node " + current.getName() + " " + markarray[current.getName()]
					+ " and secondEndpoint is Node " + edge.secondEndpoint().getName() + " "
					+ markarray[edge.secondEndpoint().getName()]);

			// if (isMarked(edge.secondEndpoint())) {
			if (markarray[edge.secondEndpoint().getName()] == true) {
				continue;
			} else {
				System.out.println("Enters here");
				if (path(edge.secondEndpoint().getName(), endNode, bbombs, abombs, pathstack)) {
					return true;
				}
			}
		}
		pathstack.pop();
		return false;
	}

	private boolean isMarked(Node u) {
		return (u.getMark());
	}
}

// The Solve() method kinda works, it moves and avoids the already marked (Node
// doesn't get set to marked??) nodes,
// but it moves too linearly, need to fix that by putting rules such as: we have
// limited bombs and by that find the path
