package genericGraph;


public interface IGraph<T> {
	
	public static final int INF = 10000000;
	public void addVertex(Vertex<T> v)throws IllegalArgumentException;
	public void addEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException;
	public void addEdge(Vertex<T> v1, Vertex<T> v2, double w)throws IllegalArgumentException;
	public void removeEdge(Vertex<T> v1, Vertex<T> v2)throws IllegalArgumentException;
	public Vertex<T> getVertex(T valueVertex) throws IllegalArgumentException;
	public Iterable<Vertex<T>> getVertices();
	public double getWeightEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException;
	public void setWeightEdge(Vertex<T> v1, Vertex<T> v2, double w) throws IllegalArgumentException;
	public boolean areAdjacent(Vertex<T> v1, Vertex<T> v2);
	public void bfs(Vertex<T> startVertex);
	public void dfs();
	public void dijkstra(Vertex<T> s);
	public boolean bellmanford(Vertex<T> startVertex);
	public double[][] floydWarshall();
	public int getNumVertices();
	public int getNumEdges();
	
	
	
}
