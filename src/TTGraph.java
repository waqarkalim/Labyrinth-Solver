import java.util.*;

public class TTGraph {
  private int n;
  private Vector V;
  private Vector List;

  /* ------------------------- */
  public TTGraph(int vertices) {
  /* ------------------------- */
    n = vertices;
    V = new Vector();
    List = new Vector();
    for (int i = 0; i < n; ++i) {
       V.add(new TTNode(i));
       List.add(new Vector());
    }
  }

  /* ------------------------------------------------------ */
  public void insertEdge(TTNode nodeu, TTNode nodev, int type) 
                         throws GraphException {
  /* ------------------------------------------------------ */
      int u = nodeu.getName();
      int v = nodev.getName();
       if (u >= n || v >= n || v < 0 || u < 0)
          throw new GraphException("Invalid node");

       TTEdge e = new TTEdge(nodeu,nodev,type);
       Vector adjacent = (Vector)List.get(u);
       adjacent.add(e);
       adjacent = (Vector)List.get(v);
       adjacent.add(e);
  }

  /* ------------------------------------------------------ */
  public TTNode getNode(int u) throws GraphException {
  /* ------------------------------------------------------ */

      if (u < 0 || u > n) throw new GraphException("invalid node");
      return (TTNode)V.get(u);
  }

  /* ------------------------------------------------------ */
  public Iterator incidentEdges(TTNode u) throws GraphException {
  /* ------------------------------------------------------ */
      int nodeu = u.getName();
      if (nodeu < 0 || nodeu >= n) 
        throw new GraphException("Invalid node");
      return ((Vector)List.get(nodeu)).iterator();   
  }

  /* ------------------------------------------------------ */
  public TTEdge getEdge(TTNode u, TTNode v) throws GraphException {
  /* ------------------------------------------------------ */
      int nodeu, nodev;

      nodeu = u.getName();
      nodev = v.getName();
      if (nodeu >= n || nodev >= n || nodeu < 0 || nodev < 0)
        throw new GraphException("Invalid node");

      Vector adj = (Vector)List.get(nodeu);
      for (int i = 0; i < adj.size(); ++i)
        if (((TTEdge)adj.get(i)).firstEndpoint() == v ||
             ((TTEdge)adj.get(i)).secondEndpoint() == v) 
           return (TTEdge)adj.get(i);

      throw new GraphException("Invalid edge");
  }

  /* ------------------------------------------------------ */
  public boolean areAdjacent(TTNode u, TTNode v) throws GraphException {
  /* ------------------------------------------------------ */
      int nodeu, nodev;

      nodeu = u.getName();
      nodev = v.getName();
      if (nodeu >= n || nodev >= n || nodeu < 0 || nodev < 0)
        throw new GraphException("Invalid node");

      Vector adj = (Vector)List.get(nodeu);
      for (int i = 0; i < adj.size(); ++i)
        if (((TTEdge)adj.get(i)).firstEndpoint() == v ||
             ((TTEdge)adj.get(i)).secondEndpoint() == v) 
           return true;

      return false;
  }
}
