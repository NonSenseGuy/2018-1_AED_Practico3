package genericGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
/**
 * @author Alejandro Barrera Lozano
 * Adjacency list graph
 * This graph implementation can be used as:
 * Simple graph
 * Directed graph
 * Simple weighted graph
 * Directed weighted graph
 * 
 *
 * @param <T>
 */
public class GraphAdjList<T> implements IGraph<T>{
	private int numVertices;
	private int numEdges;
	private final HashMap<Vertex<T>,Set<Edge<T>>> adjList;
	private final boolean isDirected;
	private final boolean isWeighted;
	private Vertex<T> refVertex;
	private int time;
	
	public GraphAdjList(boolean isDirected, boolean isWeighted) {
		adjList = new HashMap<>();
		numVertices = 0;
		numEdges = 0;
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
	}
	
	/**
	 * RefVertex -> Last vertex used as the start vertex of a shortest path algorithm
	 * @return refVertex
	 */
	public Vertex<T> getRefVertex(){
		return refVertex;
	}
	/**
	 * numVertices -> Amount of vertices in the graph
	 * @return numVertices 
	 */
	@Override
	public int getNumVertices() {
		return numVertices;
	}
	/**
	 * numEdges -> Amount of edges between each vertex in the graph
	 * @return numEdges
	 */
	@Override
	public int getNumEdges() {
		return numEdges;
	}
	/**
	 * adjList -> Adjacent list graph representation, Hash map that contains every vertex with all of its associations
	 * @return
	 */
	public HashMap<Vertex<T>, Set<Edge<T>>> getAdjList() {
		return adjList;
	}
	/**
	 * isDirected -> Shows if the graph is directed or not
	 * @return isDirected
	 */
	public boolean isDirected() {
		return isDirected;
	}
	/**
	 * isWeighted -> Shows if the edges of the graph are weighted or not
	 * In case that is false the weight of all the edges will be 1.0
	 * @return isWeighted
	 */
	public boolean isWeighted() {
		return isWeighted;
	}
	
	/**
	 * Add a vertex to the graph
	 * @param element, unique id of the new Vertex
	 */
	public void addVertex(T element) {
		addVertex(new Vertex<T>(element));
	}
	/**
	 * Add a vertex to the graph
	 * @param vertex, already instantiated 
	 */
	@Override
	public void addVertex(Vertex<T> v) throws IllegalArgumentException {
		if(!adjList.containsKey(v)) {
			this.adjList.put(v, new HashSet<Edge<T>>());
			numVertices++;
		}else {
			throw new IllegalArgumentException("Vertex alredy exists");
		}
	}
	/**
	 * Adds a edge between a pair of vertices with weight 1.0	
	 */
	@Override
	public void addEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException{
		if(adjList.containsKey(v1) && adjList.containsKey(v2)) {
			if(!isDirected) {
				adjList.get(v2).add(new Edge<T>(v2,v1, 1.0));
			}
			adjList.get(v1).add(new Edge<T>(v1,v2, 1.0));
			numEdges++;
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Adds a edge between a pair of vertices with weight w
	 */
	@Override
	public void addEdge(Vertex<T> v1, Vertex<T> v2, double w) throws IllegalArgumentException{
		if(!isWeighted) throw new IllegalArgumentException("Graph is not weighted");
		if(adjList.containsKey(v1) && adjList.containsKey(v2)) {
			if(!isDirected) {
				adjList.get(v2).add(new Edge<T>(v2,v1, w));
			}
			adjList.get(v1).add(new Edge<T>(v1,v2, w));
			numEdges++;
		}else {
			throw new IllegalArgumentException();
		}
		
	}
	/**
	 * Removes a vertex and all of its associated edges of the graph
	 */
	public void removeVertex(Vertex<T> v) throws IllegalArgumentException{
		if(adjList.containsKey(v)) {
			for(Edge<T> e: getEdges()) {
				if(e.initVertex().getValue().equals(v.getValue()) || e.endVertex().getValue().equals(v.getValue())) {
					removeEdge(e.initVertex(), e.endVertex());
				}
			}
			adjList.remove(v);
		}else {
			throw new IllegalArgumentException("Vertex not found");
		}
	}
	/**
	 * Removes the edge between a pair of vertices
	 */
	@Override
	public void removeEdge(Vertex<T> v1, Vertex<T> v2) {
		if(adjList.containsKey(v1) && adjList.containsKey(v2)) {
			for(Edge<T> edge: adjList.get(v1)) {
				if(edge.endVertex().equals(v2)) {
					adjList.get(v1).remove(edge);
					return;
				}
			}
		}else{
			throw new IllegalArgumentException("Vertex not found");
		}
		
	}
	/**
	 * Gets a vertex searching it by its unique key
	 * @param valueVertex -> Unique key
	 * @return v, Vertex<T>
	 */
	@Override
	public Vertex<T> getVertex(T valueVertex) throws IllegalArgumentException{
		for(Vertex<T> v: getVertices()) {
			if(v.getValue().equals(valueVertex)) {
				return v;
			}
		}
		throw new IllegalArgumentException("Vertex not found");
	}
	/**
	 * Gets all of the vertices of the graph
	 * @return VerticesList
	 */
	@Override
	public Iterable<Vertex<T>> getVertices() {
		return adjList.keySet();
	}
	
	public ArrayList<Vertex<T>> vertices(){
		ArrayList<Vertex<T>> vertices = new ArrayList<>();
		for(Vertex<T> v: getVertices()) {
			vertices.add(v);
		}
		return vertices;
	}
	/**
	 * Gets the weight of a given edge
	 * @return weight
	 */
	@Override
	public double getWeightEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException {
		if(adjList.containsKey(v1)) {
			for(Edge<T> edge: adjList.get(v1)) {
				if(edge.endVertex().equals(v2)){
					return edge.getWeight();
				}
			}
		}
		throw new IllegalArgumentException();
	}
	@Override
	public void setWeightEdge(Vertex<T> v1, Vertex<T> v2, double w)throws IllegalArgumentException {
		if(adjList.containsKey(v1) && adjList.containsKey(v2)) {
			Edge<T> e1 = getEdge(v1,v2);
			e1.setWeight(w);
			if(!isDirected) {
				Edge<T> e2 = getEdge(v2,v1);
				e2.setWeight(w);
			}
			return;
		}
		throw new IllegalArgumentException();
	}
	/**
	 * gets the edge between given pair of vertices
	 * @param v1
	 * @param v2
	 * @return edge
	 * @throws IllegalArgumentException
	 */
	public Edge<T> getEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException{
		for(Edge<T> edge: adjList.get(v1)) {
			if(edge.endVertex().equals(v2)) {
				return edge;
			}
		}
		throw new IllegalArgumentException("Edge not found");
	}
	/**
	 * Gets all of the edges in the graph
	 * @param v1
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Iterable<Edge<T>> getEdges(Vertex<T> v1) throws IllegalArgumentException{
		return adjList.get(v1);
	}
	
	@Override
	public void bfs(Vertex<T> startVertex) throws IllegalArgumentException{
		if(!adjList.containsKey(startVertex)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		for(Vertex<T> u: getVertices()) {
			if(!u.equals(startVertex)) {
				u.setColor(Vertex.WHITE);
				u.setD(INF);
				u.setPred(null);
			}
		}
		startVertex.setColor(Vertex.GRAY);
		startVertex.setD(0);
		startVertex.setPred(null);
		Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		queue.offer(startVertex);
		while(!queue.isEmpty()) {
			Vertex<T> u = queue.poll();
			for(Edge<T> e: adjList.get(u)) {
				Vertex<T> v = e.endVertex();
				if(v.getColor() == Vertex.WHITE) {
					v.setColor(Vertex.GRAY);
					v.setD(u.getD()+1);
					v.setPred(u);
					queue.offer(v);
				}
			}
			u.setColor(Vertex.BLACK);
		}
		
	}
	@Override
	public void dfs() {
		for(Vertex<T> u: getVertices()) {
			u.setColor(Vertex.WHITE);
			u.setPred(null);
		}
		for(Vertex<T> u: getVertices()) {
			if(u.getColor() == Vertex.WHITE) {
				dfsVisit(u);
			}
		}
	}
	
	public void dfsVisit(Vertex<T>u){
		time++;
		u.setD(time);
		u.setColor(Vertex.GRAY);
		for(Edge<T> e: adjList.get(u)) {
			Vertex<T> v = e.endVertex();
			if(v.getColor() == Vertex.WHITE) {
				v.setPred(u);
				dfsVisit(v);
			}
		}
		u.setColor(Vertex.BLACK);
		time++;
		u.setF(time);
	}
	/**
	 * Evaluates if there is a negative cycle in the graph 
	 * returns false in case it is one
	 * @return isThereANegativeCycle
	 */
	@Override
	public boolean bellmanford(Vertex<T> startVertex) throws IllegalArgumentException{
		if(!adjList.containsKey(startVertex)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		refVertex = startVertex;
		initializeSingleSource(startVertex);
		for(int i = 0; i < adjList.size(); i++) {
			for(Edge<T> edge: getEdges()) {
				relax(edge.initVertex(),edge.endVertex());
			}
		}
		for(Edge<T> e: getEdges()) {
			if(e.endVertex().getD() > e.initVertex().getD() + e.getWeight()) {
				return false;
			}
		}
		return true;
	}
	
	public void initializeSingleSource(Vertex<T> s) {
		for(Vertex<T> v: getVertices()) {
			v.setD(INF);
			v.setPred(null);
		}
		s.setD(0);
	}
	
	public void relax(Vertex<T> u,Vertex<T> v) {
		double tempDistance = u.getD() + getEdge(u,v).getWeight();
		if(v.getD() > tempDistance) {
			v.setD(tempDistance);
			v.setPred(u);
		}
	}
	/**
	 * Evaluates of a given pair of vertices are adjacent
	 */
	@Override
	public boolean areAdjacent(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException {
		if(!adjList.containsKey(v1) || !adjList.containsKey(v2)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		for(Edge<T> e: adjList.get(v1)) {
			if(e.endVertex().equals(v2)) {
				return true;
			}
		}
		return false;
	}
	
	public Set<Vertex<T>> adjacentVertices(Vertex<T> v) throws IllegalArgumentException{
		if(!adjList.containsKey(v)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Set<Vertex<T>> adjVertices = new HashSet<Vertex<T>>();
		for(Edge<T> e: adjList.get(v)) {
			adjVertices.add(e.endVertex());
		}
		return adjVertices;
	}
	/**
	 * Find the shortest path between each vertex of the graph and a start vertex
	 * @param s -> start vertex
	 */
	@Override
	public void dijkstra(Vertex<T> s) throws IllegalArgumentException {
		if(!adjList.containsKey(s)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		initializeSingleSource(s);
		Queue<Vertex<T>> priorityQ = new PriorityQueue<>(new VertexWeightComparator<Vertex<T>>());
		priorityQ.addAll(adjList.keySet());
		Set<Vertex<T>> vertexSet = new HashSet<Vertex<T>>();
		while(!priorityQ.isEmpty()) {
			Vertex<T> u = priorityQ.poll();
			if(!vertexSet.contains(u)) {
				for(Edge<T> e: adjList.get(u)) {
					relax(u,e.endVertex());
				}
				vertexSet.add(u);
			}
		}
	}

	/**
	 * Gets all of the edges in the graph
	 * @return edgesList
	 */
	public ArrayList<Edge<T>> getEdges(){
		ArrayList<Edge<T>> edgesList = new ArrayList<Edge<T>>();
		for(Vertex<T> v:getVertices()) {
			for(Edge<T> e: adjList.get(v)) {
				edgesList.add(e);
			}
		}
		return edgesList;
	}	
	/**
	 * Gets the path between the last vertex used as reference in a shortest path algorithm 
	 * and a given vertex in the graph
	 * Assuming that the graph is connected
	 */
	public ArrayList<Vertex<T>> vertexPath(Vertex<T> startVertex, Vertex<T> endVertex) throws IllegalArgumentException {
		if(!adjList.containsKey(endVertex)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Vertex<T> aux = endVertex;
		ArrayList<Vertex<T>> path = new ArrayList<>();
		while(aux.getD() != 0) {
			path.add(aux);
			aux = aux.getPred();
		}
		path.add(aux);
		return path;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Vertex<T> v:getVertices()) {
			s+= "Vertice " + v + "={";
			for(Edge<T> e: adjList.get(v)) {
				s+= e;
			}
			s += "}\n";
		}
		return s;
	}

	public void prim(Vertex<T> r) {
		for(Vertex<T> u :getVertices()) {
			u.setD(INF);
			u.setColor(Vertex.WHITE);
		}
		r.setD(0);
		r.setPred(null);
		Queue<Vertex<T>> q = new PriorityQueue<Vertex<T>>();
		q.offer(r);
		while(!q.isEmpty()) {
			Vertex<T> u = q.poll();
			for(Edge<T> e: adjList.get(u)) {
				Vertex<T> v = e.endVertex();
				if(v.getColor() == Vertex.WHITE && (e.getWeight() < v.getD())) {
					v.setD(e.getWeight());
					q.offer(v);
					v.setPred(u);
					
				}
				u.setColor(Vertex.BLACK);
			}
		}
	}

	//Does not count subGraphs with only one vertex
	public ArrayList<Edge<T>> kruskal(){
		ArrayList<Edge<T>> res = new ArrayList<>();
		int a = 0;
		int i = 0;
		ArrayList<Vertex<T>> vertices = vertices();
		ArrayList<Edge<T>> edgesList = getEdges();
		Collections.sort(edgesList);
		UnionFind u = new UnionFind(adjList.size());
		
		while(a < adjList.size() -1 && i < edgesList.size()) {
			Edge<T> edge = edgesList.get(i);
			i++;
			int x = u.find(vertices.indexOf(edge.initVertex()));
			int y = u.find(vertices.indexOf(edge.endVertex()));
			if(x != y) {
				res.add(edge);
				u.union(x, y);
			}
		}
		return res;
	}
	
	public double[][] getWeightsMatrix(){
		double[][] weightMatrix = new double[adjList.size()][adjList.size()];
		for(double[] rows: weightMatrix) {
			Arrays.fill(rows, INF);
		}
		for(int i = 0; i < adjList.size(); i++) {
			weightMatrix[i][i] = 0;
			Vertex<T> v = vertices().get(i);
			for(Edge<T> e: adjList.get(v)) {
				Vertex<T> u = e.endVertex();
				double w = e.getWeight();
				weightMatrix[i][vertices().indexOf(u)] = w;
			}
		}
		
		return weightMatrix;
	}
	@Override
	public double[][] floydWarshall(){
		double[][] weightMatrix = getWeightsMatrix();
		for(int k = 0; k < weightMatrix.length; k++) {
			for(int i = 0; i < weightMatrix.length; i++) {
				for(int j = 0; j < weightMatrix.length; j++) {
					if(weightMatrix[i][j] > weightMatrix[i][k] + weightMatrix[k][j]) {
						weightMatrix[i][j] = weightMatrix[i][k] + weightMatrix[k][j];
					}
				}
			}
		}
		return weightMatrix;
	}
	
}
