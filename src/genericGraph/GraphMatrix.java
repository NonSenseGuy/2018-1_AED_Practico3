package genericGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class GraphMatrix<T> implements IGraph<T>{
	
	private Map<T, Integer> vertices;
	private List<Vertex<T>> verticesLookUp;
	private double[][] adjMatrix;
	private double[][] distMatrix;
	private int index;
	private final boolean isDirected;
	private final boolean isWeighted;
	
	public GraphMatrix(int numVertices, boolean isDirected, boolean isWeighted) {
		adjMatrix = new double[numVertices][numVertices];
		for(double[] row: adjMatrix) {
			Arrays.fill(row, (double)INF);
		}
		for(int i = 0; i < numVertices; i++) {
			adjMatrix[i][i] = 0;
		}
		distMatrix = adjMatrix.clone();
		index = 0;
		vertices = new HashMap<T, Integer>();
		verticesLookUp = new ArrayList<Vertex<T>>();
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
	}

	
	public List<Vertex<T>> getVerticesLookUp() {
		return verticesLookUp;
	}



	public void setVerticesLookUp(List<Vertex<T>> verticesLookUp) {
		this.verticesLookUp = verticesLookUp;
	}



	public double[][] getAdjMatrix() {
		return adjMatrix;
	}
	



	public void setAdjMatrix(double[][] adjMatrix) {
		this.adjMatrix = adjMatrix;
		this.distMatrix = adjMatrix;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	public void setVertices(Map<T, Integer> vertices) {
		this.vertices = vertices;
	}

	public void addVertex(T v) {
		Vertex<T> vertex = new Vertex<T>(v);
		addVertex(vertex);
	}

	@Override
	public void addVertex(Vertex<T> v) throws IllegalArgumentException {
		if(vertices.containsKey(v.getValue())) {
			throw new IllegalArgumentException("Vertex already exists in the graph");
		}
		vertices.put(v.getValue(), index);
		verticesLookUp.add(v);
		index++;
	}

	@Override
	public void addEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException {
		if(!vertices.containsKey(v1.getValue()) || !vertices.containsKey(v2.getValue())) {
			throw new IllegalArgumentException("Vertex not found");
		}
		int indexV1 = vertices.get(v1.getValue());
		int indexV2 = vertices.get(v2.getValue());
		adjMatrix[indexV1][indexV2] = 1;
		if(!isDirected) {
			adjMatrix[indexV2][indexV1] = 1;
		}
	}

	@Override
	public void addEdge(Vertex<T> v1, Vertex<T> v2, double w) throws IllegalArgumentException {
		if(!isWeighted) throw new IllegalArgumentException();
		if(!vertices.containsKey(v1.getValue()) || !vertices.containsKey(v2.getValue())) {
			throw new IllegalArgumentException("Vertex not found");
		}
		int indexV1 = vertices.get(v1.getValue());
		int indexV2 = vertices.get(v2.getValue());
		adjMatrix[indexV1][indexV2] = w;
		if(!isDirected) {
			adjMatrix[indexV2][indexV1] = w;
		}
	}

	@Override
	public void removeEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException {
		if(!vertices.containsKey(v1.getValue()) || !vertices.containsKey(v2.getValue())) {
			throw new IllegalArgumentException("Vertex not found");
		}
		int indexV1 = vertices.get(v1.getValue());
		int indexV2 = vertices.get(v2.getValue());
		adjMatrix[indexV1][indexV2] = INF;
		if(!isDirected) {
			adjMatrix[indexV2][indexV1] = INF;
		}
	}
	
	public double[][] getDistMatrix(){
		return distMatrix;
	}

	@Override
	public Vertex<T> getVertex(T valueVertex) throws IllegalArgumentException {
		if(vertices.containsKey(valueVertex)) {
			return verticesLookUp.get(vertices.get(valueVertex));
		}
		throw new IllegalArgumentException("Vertex not found");
	}

	@Override
	public Iterable<Vertex<T>> getVertices() {
		return verticesLookUp;
	}

	@Override
	public double getWeightEdge(Vertex<T> v1, Vertex<T> v2) throws IllegalArgumentException {
		int indexV1 = vertices.get(v1.getValue());
		int indexV2 = vertices.get(v2.getValue());
		return adjMatrix[indexV1][indexV2];
	}

	@Override
	public void setWeightEdge(Vertex<T> v1, Vertex<T> v2, double w) throws IllegalArgumentException {
		int indexV1 = vertices.get(v1.getValue());
		int indexV2 = vertices.get(v2.getValue());
		adjMatrix[indexV1][indexV2] = w;
		
	}
	@Override
	public void bfs(Vertex<T> startVertex) {
		if(!vertices.containsKey(startVertex.getValue())) {
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
			int index = vertices.get(u.getValue());
			for(int i = 0; i < vertices.size(); i++) {
				if(i != index) {
					if(adjMatrix[index][i] != INF) {
						Vertex<T> v = verticesLookUp.get(i);
						if(v.getColor() == Vertex.WHITE) {
							v.setColor(Vertex.GRAY);
							v.setD(u.getD());
							v.setPred(u);
							queue.offer(v);
						}
					}
				}
			}
			u.setColor(Vertex.BLACK);
		}
		
	}


	@Override
	public void dfs() {
		for(Vertex<T> v : verticesLookUp) {
			v.setColor(Vertex.WHITE);
		}
		for(Vertex<T> u: verticesLookUp) {
			if(u.getColor() == Vertex.WHITE) {
				dfsVisit(u);
			}
		}
	}
	
	public void dfsVisit(Vertex<T> u) {
		u.setColor(Vertex.BLACK);
		int indexU = vertices.get(u);
		for(int i = 0; i < vertices.size(); i++) {
			if(i != indexU) {
				Vertex<T> v = verticesLookUp.get(i);
				if(adjMatrix[indexU][i] != INF && v.getColor() == Vertex.WHITE) {
					v.setPred(u);
					dfsVisit(v);
				}
			}
		}
	}

	@Override
	public boolean areAdjacent(Vertex<T> v1, Vertex<T> v2) {
		int indexV1 = vertices.get(v1);
		int indexV2 = vertices.get(v2);
		
		return adjMatrix[indexV1][indexV2] == INF ? false:true;
	}
	
	public List<Vertex<T>> getAdjacentVertices(Vertex<T> v){
		int index = vertices.get(v.getValue());
		List<Vertex<T>> result = new ArrayList<Vertex<T>>();
		for(int i = 0; i < adjMatrix[index].length; i++) {
			if(adjMatrix[index][i] != INF) {
				result.add(verticesLookUp.get(i));
			}
		}
		return result;
	}


	@Override
	public void dijkstra(Vertex<T> s) {
		ShortestPath sp = new ShortestPath();
		int src = vertices.get(s.getValue());
		sp.dijkstra(distMatrix, src);
	}
	
	@Override
	public boolean bellmanford(Vertex<T> startVertex) {
		ShortestPath sp = new ShortestPath();
		int src = vertices.get(startVertex.getValue());
		return sp.bellmanford(distMatrix, src);
	}

	@Override
	public int getNumVertices() {
		return vertices.size();
	}

	@Override
	public int getNumEdges() {
		int numEdges = 0;
		for(int i = 0; i < vertices.size(); i++) {
			for(int j = 0; j < vertices.size(); j++) {
				if(j != i && adjMatrix[i][j] != INF) {
					numEdges++;
				}
			}
		}
		return numEdges;
	}

	@Override
	public double[][] floydWarshall(){
		double[][] weightMatrix = adjMatrix;
		
		
		for(int k = 0; k < weightMatrix.length; k++) {
			for(int i = 0; i < weightMatrix.length; i++) {
				for(int j = 0; j < weightMatrix.length; j++) {
					if(weightMatrix[i][j] > weightMatrix[i][k] + weightMatrix[k][j]) {
						weightMatrix[i][j] = weightMatrix[i][k] + weightMatrix[k][j];
					}
				}
			}
		}
		distMatrix = weightMatrix;
		return weightMatrix;
	}
	
	
	@Override
	public String toString() {
		String msg = "";
		for(int i = 0; i < vertices.size(); i++) {
			if(verticesLookUp.get(i) != null) {
				msg += verticesLookUp.get(i) + "-> ";
			}
			for(int j = 0; j < vertices.size(); j++) {
				if(j != i) {
					if(verticesLookUp.get(j) != null) {
						msg += "(" + verticesLookUp.get(j) + "," + adjMatrix[i][j] + "), ";  
					}	
				}
			}
			msg+= "\n";
		}
		return msg;
		
		
	}
	
	private class ShortestPath{
		
		private int minDistance(double[] dist, boolean sptSet[]) {
			double min = INF;
			int min_index = -1;
			for(int i = 0; i < verticesLookUp.size(); i++) {
				if(sptSet[i] == false && dist[i] < min) {
					min = dist[i];
					min_index = i;
				}
			}
			
			return min_index;
		}
		
		private void dijkstra(double[][] graph, int src) {
			double[] dist = new double[verticesLookUp.size()];
			boolean[] sptSet = new boolean[verticesLookUp.size()];
			
			for(int i = 0; i < verticesLookUp.size(); i++) {
				dist[i] = INF;
				sptSet[i] = false;
			}
			
			dist[src] = 0;
			int u = -1;
			for(int count = 0; count < verticesLookUp.size() -1; count++) {
				u = minDistance(dist,sptSet);
				sptSet[u] = true;
				for(int v = 0; v < verticesLookUp.size(); v++) {
					if(!sptSet[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
						dist[v] = dist[u] + graph[u][v];
						verticesLookUp.get(v).setD(dist[u] + graph[u][v]);
						verticesLookUp.get(v).setPred(verticesLookUp.get(u));
					}
				}
			}
			distMatrix[src] = dist;
			
		}
		
		private boolean bellmanford(double[][] graph, int src) {
			double[] dist = new double[verticesLookUp.size()];
			boolean[] sptSet = new boolean[verticesLookUp.size()];
			for(int i = 0; i < verticesLookUp.size(); i++) {
				dist[i] = INF;
				sptSet[i] = false;
			}
			dist[src] = 0;
			int u = -1;
			for(int count = 0; count < verticesLookUp.size() -1; count++) {
				u = minDistance(dist,sptSet);
				sptSet[u] = true;
				for(int v = 0; v < verticesLookUp.size(); v++) {
					if(!sptSet[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
						dist[v] = dist[u] + graph[u][v];
						verticesLookUp.get(v).setD(dist[u] + graph[u][v]);
						verticesLookUp.get(v).setPred(verticesLookUp.get(u));
					}
				}
			}
			
			for(int v = 0; v < verticesLookUp.size(); v++) {
				if(!sptSet[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
					return false;
				}
			}
			distMatrix[src] = dist;
			return true;
			
			
		}
	}

}
