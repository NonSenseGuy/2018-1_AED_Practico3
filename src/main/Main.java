package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;



import genericGraph.*;

public class Main {
	
	private static GraphMatrix<Integer> g;
	
	public static int[] verticesGrade(int[][] graph, int k) {
		int res[] = new int[graph.length];
		for(int i = 0; i < graph.length; i++) {
			int counter = 0;
			for(int j = 0; j < graph.length; j++) {
				if(graph[i][j] > 0 && graph[i][j] <= k) {
					counter++;
				}
			}
			res[i] = counter;
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int testCases = Integer.parseInt(br.readLine());
		
		for(int t = 0; t < testCases; t++) {
			int numVertices = Integer.parseInt(br.readLine());
			int grade = Integer.parseInt(br.readLine());
			double[][] matrix = new double [numVertices][numVertices];
		
			g = new GraphMatrix<Integer>(numVertices,false, false);
			for(int i = 0; i < numVertices;i++) {
				String arr[] = br.readLine().split(" ");
				for(int j = 0; j < numVertices; j++ ) {
					int v = Integer.parseInt(arr[j]);
					if(v != 0) {
						matrix[i][j] = v;
					}else if(i != j){
						matrix[i][j] = GraphMatrix.INF;
					}
					
				}
			}
			for(int i = 0; i < numVertices; i++) {
				g.addVertex(i);
			}
			g.setAdjMatrix(matrix);
			g.floydWarshall();
			int intArray[][] = new int[g.getDistMatrix().length][g.getDistMatrix().length];
			for(int i = 0; i < intArray.length; i++) {
				for(int j = 0; j < intArray.length; j++) {
					intArray[i][j] = (int)g.getDistMatrix()[i][j];
				}
			}
			
			System.out.println("");
			for(int[] row: intArray) {
				for(int i = 0; i < row.length; i++) {
					if(row[i] == GraphMatrix.INF) {
						row[i] = -1;
					}
				}
				System.out.println(Arrays.toString(row));
			}
			System.out.println(Arrays.toString(verticesGrade(intArray, grade)));
			
		}
	}
}
