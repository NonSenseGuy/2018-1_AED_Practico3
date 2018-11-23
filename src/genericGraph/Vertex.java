package genericGraph;

public class Vertex<T> implements Comparable<Vertex<T>> {
	public final static int WHITE = 0;
	public final static int GRAY = 1;
	public final static int BLACK = 2;
	private T value;
	private double d;
	private int f;
	private int color;
	private Vertex<T> pred;
	
	public Vertex(T type) {
		value = type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Vertex<T> getPred() {
		return pred;
	}

	public void setPred(Vertex<T> pred) {
		this.pred = pred;
	}

	@Override
	public String toString() {
		return ""+ value;
	}

	@Override
	public int compareTo(Vertex<T> o) {
		if(d < o.getD()) {
			return -1;
		}else if(d > o.getD()) {
			return 1;
		}else
			return 0;
	}
	
	
	
	
}
