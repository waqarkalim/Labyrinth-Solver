public class Edge {
	
	private Node u;
	private Node v;
	private String type;
	
	
	public Edge(Node u, Node v, String type) {
		this.u = u;
		this.v = v;
		this.type = type;
	}
	
	public Node firstEndpoint() {
		return this.u;
	}
	
	public Node secondEndpoint() {
		return this.v;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
