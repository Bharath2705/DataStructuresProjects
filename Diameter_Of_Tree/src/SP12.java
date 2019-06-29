package rbk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import rbk.BFSOO;
import rbk.Graph;
import rbk.Graph.Vertex;

public class SP12 {
	
	public static void main(String[] args) throws FileNotFoundException {
		String string = "9 8   1 2 2   1 3 3   2 4 5   2 5 4   3 6 1   3 7 7   6 8 -1   6 9 -1";
		//String string = "5 4  1 2 2   1 3 3   2 4 5   2 5 6 ";

		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		// Read graph from input
	    Graph g = Graph.readGraph(in);
	    g.printGraph(false);
	    System.out.println("Diameter of given graph is : " + diameter(g));
	}
	
	/*
	 * Implement the algorithm to find the diameter of a tree using the
     * algorithm discussed in class, that runs BFS twice.  Code this
     * algorithm without modifying Graph.java and BFSOO.java, using them
     * from package rbk.
     * assume graph g is acyclic and connected graph(tree).
	 */
	public static int diameter(Graph g){

		// Call breadth-first search
		BFSOO b = BFSOO.breadthFirstSearch(g, 1);
		Vertex farthest = null;
		int maxDistance = 0;
		for(Vertex u: g) {
			if(b.getParent(u)!= null && maxDistance<b.getDistance(u)){
				maxDistance = b.getDistance(u);
				farthest=u;
			}
		}
		
		// Call bfs with farthest vertex
		b = BFSOO.breadthFirstSearch(g, farthest);
		maxDistance = 0;
		for(Vertex u: g) {
			if(b.getParent(u)!= null && maxDistance<b.getDistance(u)){
				maxDistance = b.getDistance(u);
				farthest=u;
			}
		}		
		return maxDistance;
	}

}

