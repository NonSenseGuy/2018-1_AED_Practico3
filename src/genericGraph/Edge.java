package genericGraph;

public class Edge<T> implements Comparable<Edge<T>>{
	private Vertex<T> v1;
	private Vertex<T> v2;
	private double w;
	private double d;
	private Edge<T> pred;
	
	public Edge(Vertex<T> v1, Vertex<T> v2, double w) {
		this.v1 = v1; 
		this.v2 = v2;
		this.w = w;
	}
	
	public Edge<T> getPred(){
		return pred;
	}
	
	public void setPred(Edge<T> e) {
		pred = e;
	}
	
	public Vertex<T> initVertex(){
		return v1;
	}
	
	public Vertex<T> endVertex(){
		return v2;
	}
	
	public double getWeight() {
		return w;
	}
	
	public void setWeight(double w) {
		this.w = w;
	}
	
	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "(" + v1 + "," + v2 + ")";
	}

	@Override
	public int compareTo(Edge<T> o) {
		if(w > o.getWeight()) {
			return 1;
		}else if(w == o.getWeight()) {
			return 0;
		}else {
			return -1;
		}
	}
	
}

