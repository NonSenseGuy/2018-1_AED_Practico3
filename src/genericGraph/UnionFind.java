package genericGraph;

public class UnionFind {
	
	private int[] parent;
	private int[] rank;
	
	public UnionFind(int max) {
		parent = new int[max];
		rank = new int[max];
		for(int i = 0; i < max; i++) {
			parent[i] = i;
		}
	}
	
	public int find(int i) {
		if(i == parent[i]) {
			return i;
		}
		return find(parent[i]);
	}
	
	public void union(int i, int j) {
		int a = find(i);
		int b = find(j);
		
	    if (b == a) return;

	    if (rank[a] > rank[b]) {
	      parent[b] = a;
	    } else if (rank[b] > rank[a]) {
	      parent[a] = b;
	    } else {
	      parent[b] = a;
	      rank[a]++;
	    }
	}
	
	
}
