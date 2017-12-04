public class TTEdge {
    private TTNode first, second;
    int  type;
    String label;

    public TTEdge(TTNode u, TTNode v, int t) {
	first = u;
	second = v;
	type = t;
    }

    public TTNode firstEndpoint() {return first;}

    public TTNode secondEndpoint() {return second;}

    public int getType() {return type;}

    public void setLabel(String lab) {
	label = lab;
    }

    public String getLabel() {
	return label;
    }
}
