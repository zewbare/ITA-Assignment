package assignment2;

import java.util.*;
public class Graph {
	
	    int V;   // No. of vertices
	    private LinkedList<Integer> adj[]; //Adjacency Lists
	    int[] heuristic = {5,4,4,3,3,3,4,4,2,2,2,3,1,1,3,3,2,2,1,1,2,0,1,1,-1,0};
	    int[] parent;
	    int[] weight;
	    int[] func;
	    // Constructor
	    Graph(int v)
	    {
	        V = v;
	        parent = new int[V];
	        weight = new int[V];
	        func = new int[V];
	        adj = new LinkedList[v];
	        for (int i=0; i<v; ++i) {
	            adj[i] = new LinkedList();
	            parent[i] = -1;
	            weight[i] = 1;
	        }
	        weight[0] = 0;
	    }
	 
	    // Function to add an edge into the graph
	    void addEdge(int v,int w)
	    {
	        adj[v].add(w);
	    }
	    
	    
	    
	    void aStar(int v, int f) {
	    	v = v-1;
	    	f = f-1;
	    	int n=-1;

	    	
	    	Queue<Integer> open = new LinkedList<>();
	    	Queue<Integer> closed = new LinkedList<>();

	    	open.add(v);

	    	while(!open.isEmpty()) {
	    		n = open.peek();
	    		if(n==f) {
	    			int[] result = new int[26];
	    			int p=0;
	    			result[p++] = f+1;
	    	    	while(f>0) {
	    	    		result[p++] = parent[f]+1;
	    	    		f = parent[f];
	    	    	}
	    	    	while(p>0) {
	    	    		System.out.print(result[--p] + " ");
	    	    	}
	    			return;
	    		}


	    	Iterator<Integer> i = adj[n].listIterator();
	    	while(i.hasNext()) {
	    		int m = i.next();
	    		int totalWeight = weight[n] + 1;
	    		
	    		if(!open.contains(m) && !closed.contains(m)) {
	    			parent[m] = n;
	    			weight[m] = totalWeight;
		    		func[m] = totalWeight + heuristic[m];
	    			open.add(m);
	    		}
	    		else {
	    			if((totalWeight+heuristic[m]) < func[m]) {
	    				parent[m] = n;
	    				weight[m] = totalWeight;
	    				
	    				if(closed.contains(m)) {
	    					closed.remove(m);
	    					open.add(m);
	    				}
	    			}
	    		}
	    	}
	    	open.remove(n);
	    	closed.add(n);
	    	}
	    }
	    
	    Comparator<Integer> heuristicComparator = new Comparator<Integer>() {
	    	public int compare(Integer n, Integer m) {
	            return heuristic[n] - heuristic[m];
	        }
	    };
	    
	    void gBestFS(int s, int f) {
	    	
	    	s = s-1;
	    	f = f-1;
	    	int n = -1;
	    	
	    	PriorityQueue<Integer> open = new PriorityQueue<>(heuristicComparator);
	    	PriorityQueue<Integer> closed = new PriorityQueue<>();
	    	
	    	open.add(s);
	    	
	    	while(!open.isEmpty()) {
	    		n = open.peek();
	    		if(n==f) {
	    			int[] result = new int[26];
	    			int p=0;
	    			result[p++] = f+1;
	    	    	while(f>0) {
	    	    		result[p++] = parent[f]+1;
	    	    		f = parent[f];
	    	    	}
	    	    	while(p>0) {
	    	    		System.out.print(result[--p] + " ");
	    	    	}
	    			return;
	    		}


	    	Iterator<Integer> i = adj[n].listIterator();
	    	while(i.hasNext()) {
	    		int m = i.next();
	    		if(!open.contains(m) && !closed.contains(m)) {
	    			parent[m] = n;
	    			open.add(m);
	    		}
	    	}
	    	open.remove(n);
	    	closed.add(n);
	    	}
	    	
	    }
	 
	    // prints BFS traversal from a given source s to the final vertex f
	    void BFS(int s, int f)
	    {
	    	s = s-1;
	    	f = f-1;
	        // Mark all the vertices as not visited(By default the boolean array is set as false)
	        boolean visited[] = new boolean[V];
	 
	        // Create a queue for BFS
	        LinkedList<Integer> queue = new LinkedList<Integer>();
	 
	        // Mark the current node as visited and enqueue it
	        visited[s]=true;
	        queue.add(s);
	 
	        while (queue.size() != 0)
	        {
	            s = queue.poll();
	            System.out.print((s+1)+" ");
	            if(s==f)
	            	break;
	 
	            Iterator<Integer> i = adj[s].listIterator();
	            while (i.hasNext())
	            {
	                int n = i.next();
	                if (!visited[n])
	                {
	                    visited[n] = true;
	                    queue.add(n);
	                }
	            }
	        }
	    }
	    
	    void DFS(int v, int f){
			boolean visited[] = new boolean[V];
	    	doDFS(v-1, f-1, visited);
	    }
	    
		void doDFS(int v, int f, boolean visited[])
		{
			// Mark the current node as visited and print it
			visited[v] = true;
			System.out.print((v+1) + " ");

			// Recur for all the vertices adjacent to this
			// vertex
			Iterator<Integer> i = adj[v].listIterator();
			while (i.hasNext()) {
				int n = i.next();
				if (!visited[n] && visited[f]==false)
					doDFS(n, f, visited);
			}
		}

}

//referred geeksforgeeks.com for BFS, DFS
//referred https://stackabuse.com/graphs-in-java-a-star-algorithm/ for A Star
//referred https://www.youtube.com/watch?v=dv1m3L6QXWs for Greedy Best First Search
