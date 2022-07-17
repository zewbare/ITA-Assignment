package assignment2;

public class Question1 {
	
	public static void main(String args[])
    {
        Graph g = new Graph(26);
 
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        g.addEdge(0, 5);
        g.addEdge(2, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 6);
        g.addEdge(5, 0);
        g.addEdge(5, 6);
        g.addEdge(5, 7);
        g.addEdge(6, 4);
        g.addEdge(6, 5);
        g.addEdge(6, 8);
        g.addEdge(6, 9);
        g.addEdge(6, 10);
        g.addEdge(9, 6);
        g.addEdge(9, 11);
        g.addEdge(11, 8);
        g.addEdge(11, 9);
        g.addEdge(11, 12);
        g.addEdge(11, 13);
        g.addEdge(13, 11);
        g.addEdge(13, 14);
        g.addEdge(13, 15);
        g.addEdge(13, 16);
        g.addEdge(13, 17);
        g.addEdge(14, 8);
        g.addEdge(14, 10);
        g.addEdge(14, 13);
        g.addEdge(14, 18);
        g.addEdge(14, 19);
        g.addEdge(18, 14);
        g.addEdge(18, 15);
        g.addEdge(18, 16);
        g.addEdge(18, 20);
        g.addEdge(20, 18);
        g.addEdge(20, 21);
        g.addEdge(21, 16);
        g.addEdge(21, 17);
        g.addEdge(21, 20);
        g.addEdge(21, 22);
        g.addEdge(21, 23);
        g.addEdge(22, 21);
        g.addEdge(22, 24);
        g.addEdge(23, 21);
        g.addEdge(23, 24);
        g.addEdge(23, 25);
        
 
        System.out.println("BFS Solution for the Missionaries and Cannibals problem:");	 
        g.BFS(1,25);
        System.out.println();
        System.out.println("DFS Solution for the Missionaries and Cannibals problem:");	
        g.DFS(1, 25);
        System.out.println();
        System.out.println("A Star Solution for the Missionaries and Cannibals problem:");
        g.aStar(1,25);
        System.out.println();
        System.out.println("Greedy Best First Search Solution for the Missionaries and Cannibals problem:");
        g.gBestFS(1,25);
    }
	
}
