public class TTNode {
    int name;
    boolean marked;

    public TTNode(int nodeName) {
	name = nodeName;
    }

    public int getName() {
	return name;
    }

    public void setName (int u) {
	name = u;
    }

    public void setMark(boolean mark) {
	marked = mark;
    }

    public boolean getMark() {
	return marked;
    }
}
