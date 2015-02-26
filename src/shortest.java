import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

/*
 * IMPLEMENT SIMILARITY MEASURES
 */
public class shortest {
	
	//change a hypergraph's edges to a simplegraph
	public int[][] hyperedge2edgeSimple(int[][] hyper)
	{
		int i,j;
		int edgeNum = hyper.length;
		int[][] simple = new int[edgeNum][edgeNum];
		//go over each node and if it is included in more than one edges, permutate the edges and give them 1
		for(i = 0 ; i < hyper[0].length; i++)
		{
			Set<Integer> tmp = new HashSet<Integer>();

			for(j = 0 ; j < edgeNum; j++)
			{
				if(hyper[j][i]==1)
					tmp.add(j);
			}
			determin a = new determin();
			ArrayList<Integer> set = new ArrayList<Integer>(tmp);
			ArrayList<ArrayList<Integer>> permu = new ArrayList<ArrayList<Integer>>();

			if(set.size() > 1)
			{
				permu = a.Permutation(set, 2);
			}
			for(ArrayList<Integer> pair: permu)
			{
				simple[pair.get(0)][pair.get(1)] = 1;
			}
		}
		return simple;
	}

    public Set<Integer> difference(Set<Integer> list1, Set<Integer> list2) {
		   Set<Integer> tmp = new HashSet<Integer>(list1);
		    tmp.removeAll(list2);
		    return tmp;
		  }
    public Set<Integer> union(Set<Integer> list1, Set<Integer> list2) {
        Set<Integer> set = new HashSet<Integer>();

        set.addAll(list1);
        set.addAll(list2);

        return set;
    }
	//find the shortest path between start to end
	public int getShortest(int[][] array, int nodeStart, int nodeEnd)
	{
		Set<Integer> next = new HashSet<Integer>();

		Set<Integer> total = new HashSet<Integer>();
		int i, pathLength = 0;
		total.add(nodeStart);
		next.add(nodeStart);
		while(!next.contains(nodeEnd))
		{
			pathLength++;
			Set<Integer> temp = new HashSet<Integer>();

			for(Integer n: next)
			{
				for(i = 0; i < array[0].length; i++)
				{
					if(array[n][i] == 1)
					{				
						temp.add(i);
					}	
				}
			}
			next = difference(temp, total);
			total = union(total, next);
			if(next.size() == 0)
			{
				pathLength = 0;
				break;
			}
		}
		return pathLength;
	}
	
	/*
	 * sim = (Sn + Se)/2, Sn = commonNode#/TwoEdgetotal#, Se = shortestPath/total#edge
	 */
	public double similarity(int[][] hyper, int[][] edge, int firstEdge, int secondEdge)
	{
		double sim = 0;
		int commonNodes=0; 
		double path = 0;
		double totalNodes = 0.0;
		int totalPath = hyper.length;
		int i, j;
		for(i = 0; i < hyper[0].length; i++)
		{
			if((hyper[firstEdge][i] == 1 ) && ( hyper[secondEdge][i] == 1))
			{
				totalNodes++;
				commonNodes++;
			}
			else if((hyper[firstEdge][i] ==1) || ( hyper[secondEdge][i] == 1))
				totalNodes ++ ;
		}
		path = getShortest(edge, firstEdge, secondEdge);
		//normalize Se
		if(path == 0)
			sim = commonNodes/(2*totalNodes);
		else
			sim = ((commonNodes/totalNodes) + (Math.log(path/totalPath))/(Math.log(1.0/totalPath)))/2;
		return sim;
	}

}
