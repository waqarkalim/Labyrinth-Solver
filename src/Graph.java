import java.util.Iterator;
import java.util.ArrayList;

public class Graph implements GraphADT {

	private Node[] graph;
	private Edge[][] edges;
	ArrayList edgeList = new ArrayList();

	public Graph(int n) {
//		System.out.println("graph.length is " + n);
		graph = new Node[n];
		edges = new Edge[n][n];

		for (int i = 0; i < n; i++) {
			graph[i] = new Node(i);
			graph[i].setMark(false);
			for (int j = 0; j < n; j++) {
				edges[i][j] = new Edge(graph[i], graph[j], null);
				edges[j][i] = new Edge(graph[j], graph[i], null);
			}
		}
	}

	public void insertEdge(Node u, Node v, String edgeType) throws GraphException {
		if (!(isThere(u) && isThere(v)) || (edges[u.getName()][v.getName()].getType() != null) || (u.getName() == v.getName())) {
//			if (edges[u.getName()][v.getName()].getType() != null) {
//				System.out.println("Edges is not null");
//			} else if (!isThere(u)) {
//				System.out.println(u.getName() + " is not there");
//			} else if (!isThere(v)) {
//				System.out.println(v.getName() + " is not there");
//			}
//			System.out.println("u and v are " + u.getName() + " and " + v.getName());
			System.out.println("Graph exception in the insertEdge() method");

			throw new GraphException();
		}
		edges[u.getName()][v.getName()] = new Edge(u, v, edgeType);
		edges[v.getName()][u.getName()] = new Edge(v, u, edgeType);
	}

	public Node getNode(int name) {
		for (int i = 0; i < graph.length; i++) {
			if (graph[i].getName() == name) {
				return graph[i];
			}

		}
		System.out.println("Graph exception in the getNode() method");
		throw new GraphException();
	}

	public Iterator incidentEdges(Node u) throws GraphException {
//		System.out.println("Node u is Node " + u.getName());
		if (!(isThere(u))) {
			System.out.println("Graph exception in the incidentEdges() method");
			throw new GraphException();
		}

		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		
		boolean found = false;
		for (int i = 0; i < graph.length; i++) {
			if (u.getName() == i) {
				continue;
			}
//			System.out.println("The edgeType of Node " + u.getName() + " and Node " + i + " is " + edges[u.getName()][i].getType());
			if (edges[u.getName()][i].getType() != null) {
				found = true;
//				System.out.println("Mark of Node " + edges[u.getName()][i].secondEndpoint().getName() + " is " + Boolean.toString(edges[u.getName()][i].secondEndpoint().getMark()));
				edgeList.add(edges[u.getName()][i]);
			} 
		}
		if (!found) {
			throw new GraphException();
		}
		
		return edgeList.iterator();
		
//		int count = 0;
//		
//		for (int i = 0; i < graph.length; i++) {
//			if (edges[i][u.getName()] != null) {
//				count++;
//				edgeList.add(edges[i][u.getName()]);
//			}
//		}
//
//		if (count == 0) {
//			return null;
//		}
//		
//		Iterator iterator = edgeList.iterator();
//
//		return iterator;
	}

	public Edge getEdge(Node u, Node v) throws GraphException {
		
		if (!(isThere(u) && isThere(v)) || (edges[u.getName()][v.getName()] == null)) {
			System.out.println("Graph exception in the getEdge() method");
			throw new GraphException();
		}
		return edges[u.getName()][v.getName()];
	}

	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if (!(isThere(u) && isThere(v))) {
			System.out.println("Graph exception in the areAdjacent() method");
			throw new GraphException();
		}
		
//		System.out.println("Passing the Exception in areAdjacent() " + u.getName() + " " + v.getName());
//		System.out.println(edges[u.getName()][v.getName()].getType());
		if (edges[u.getName()][v.getName()] == null) {
			return false;
		}
		return true;
//		return (edges[u.getName()][v.getName()].getType() != null);
	}

	private boolean isThere(Node u) {
//		System.out.println("Checking if " + u.getName() + " is there or not");
		boolean found1 = ((u.getName() > 0) || (u.getName() < (graph.length-1)));
		
		boolean found2 = false;
		for (int i = 0; i < graph.length; i++) {
				if (graph[i].equals(u)) {
					found2 = true;
					break;
				}
		}
//		for (int i = 0; i < graph.length; i++) {
//			System.out.println(graph[i].getName());
//		}
//		System.out.println("found1 is " + found1 + " and found2 is " + found2);
		return (found1 || found2);
	}

}
