import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import cern.colt.bitvector.BitMatrix;
//This class create triangle index without duplicates and not care about verticesID.
//Because when verifying, there is no need to compare the vertex, just see in q: 
//if any2 edges share <= number of vertices compared to the index triangle. 
//The triangle index format: <E1,E2,E3>
//getTriangleIndexUsingRelatedEdges() and getHyperTriangleIndex() has the same function
//one is first generating each edge's related edge, then check triangle
//another is to go through all the triplet of edges' combination and then check.
public class createindexwithoutDup {

	/*
	 * given an hypergraph, generate each edge's related hyperedges, return an arraylist
	 * where arraylist[i] has the related edges for edge i , the first number is the edgeID
	 */
	public ArrayList<Set<Integer>> relatedEdges(BitMatrix array)
	{
		ArrayList<Set<Integer>> relatededges = new ArrayList<Set<Integer>>();
		int i, j, k;
		for(i = 0 ; i < array.rows(); i++)
		{
			Set<Integer> tmp = new HashSet<Integer>();
			for(j = 0 ; j < array.columns(); j++)				
			{
				if(array.get(j, i) == true)
				{
					//this way we may not get all related edges for edge i
					//e.g, i=100, since j starts from i, but we can eliminate duplicated triangles in 
					//getTriangleIndexUsingRelatedEdges()
					for(k = i; k < array.rows(); k++)
					{
						if(array.get(j, k) == true)
						{
							tmp.add(k);
						}
					}
				}
			}
			relatededges.add(tmp);
		}
		return relatededges;
	}
	/*
	 * getIndex() gives all the index in the graph
	 * here index elements only consist three edges: triangle shape and star shape
	 * star is using hyperedge as center, and two other hyperedge relted to it
	 * all of them are in the format<E1,E2, E3, set1#,set2#,set3#, set4#>
	 *  set1,2,3,are the vertices only in E1&E2, E2&E3, E3&E1, and sorted, set 4 is E1&E2&E3
	 * e.g., for hyper-triangle:
	 * get all the 3combinations of the edges and check if there is three columns
	 * that has 0 at different rows:
	 * 110
	 * 011
	 * 101
	 * column: vertex, row: edge
	 */
	public Set<ArrayList<Integer>> getTriIndex(BitMatrix array, 
			ArrayList<Set<Integer>> relateEdgeList)
	{

		int i, j, jj, k, kk, l, flag1, flag2, flag3;//flags for checking if there are 3 0s

		Set<ArrayList<Integer>> Index = new HashSet<ArrayList<Integer>>();

		ArrayList<Set<Integer>> relatededges= relateEdgeList;
		int edgeListSize = relatededges.size();
		for (i = 0; i < edgeListSize; i++)//for each edge in the hyperedge adjacency list
		{
			ArrayList<Integer> currentEdgeRelated = new ArrayList<Integer>(relatededges.get(i));
			currentEdgeRelated.remove(new Integer(i));
			int currentEdgeRelatedNum = currentEdgeRelated.size();
			for(jj = 0; jj < currentEdgeRelatedNum;  jj++)//go through each edge's related edge
			{
				j = currentEdgeRelated.get(jj);
				for(kk = jj+1; kk < currentEdgeRelatedNum; kk++)
				{	
					k =currentEdgeRelated.get(kk);
					flag1=0;//110
					flag2=0;//101
					flag3=0;//011
 					ArrayList<Integer> edges = new ArrayList<Integer>();
					Integer set1 =0;					//start checking the vertices
					Integer set2 =0;					//start checking the vertices
					Integer set3 =0;					//start checking the vertices
					Integer set4 =0;					//start checking the vertices
					for(l = 0; l < array.columns(); l++)
					{
						//110
						if((array.get(l, i)==true)&&(array.get(l, j)==true)&&(array.get(l, k)==false))
						{
							flag1=1;
							set1++;
						}
						//101
						else if((array.get(l, i)==true)&&(array.get(l, j)==false)&&(array.get(l, k)==true))
						{
							flag2=1;
							set2++;
						}
						//011
						else if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
						{
							flag3=1;
							set3++;
						}//111
						else if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
						{
							set4++;
						}
						
					}
					if(flag1*flag2*flag3==1)//triangle
					{
						edges.add(i);
						edges.add(j);
						edges.add(k);
						//add anytwo's intersection
						if((set1 >= set2) && (set2 >= set3))
						{
							edges.add(set1);
							edges.add(set2);
							edges.add(set3);
						}
						else if((set1 >= set2) && (set2 <= set3)
								&& (set1 >= set3))
						{
							edges.add(set1);
							edges.add(set3);
							edges.add(set2);
						}
						else if((set2 >= set1) && (set1 >= set3))
						{
							edges.add(set2);
							edges.add(set1);
							edges.add(set3);
						}
						else if((set2 >= set1) && (set1 <= set3)
								&& (set2 >= set3))
						{
							edges.add(set2);
							edges.add(set3);
							edges.add(set1);
						}
						else if((set3 >= set1) && (set1 >= set2))
						{
							edges.add(set3);
							edges.add(set1);
							edges.add(set2);
						}
						else if((set3 >= set1) && (set1 <= set2)
								&& (set3 >= set2))
						{
							edges.add(set3);
							edges.add(set2);
							edges.add(set1);
						}

						edges.add(set4);
						Index.add(edges);
					}
				}
			}
		}
		//generate the real triangle set e.g. <E1,E2,E3>,<v1,v2>,<v3>,<v4,v5>
		//becomes<v4,E1,v1,E2,v3,E3>,<v5,E1,v1,E2,v3,E3>,<v4,E1,v2,E2,v3,E3>,<v5,E1,v2,E2,v3,E3>
	
		return Index;
	}
	
	/*
	 * The index of star shape is stored as a set of vertices
	 * the vertex which has three or more edges related to it is in the index
	 */
	public Set<ArrayList<Integer>> getStarIndex(BitMatrix array)
	{
		Set<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
		int i, j, sum;
		for (i = 0; i < array.columns(); i ++)
		{
			sum = 0;
			for (j = 0; j < array.rows(); j ++)
			{
				if(array.get(i, j) == true)
				{
					sum ++;
				}
					
			}
			if(sum > 2)
			{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(i);
				temp.add(sum);
				set.add(temp);
			}
		}
		
		return set;
	}
	/*
	 * getTriangleIndexUsingRelatedE() gives all the triangles in the graph
	 * in format <v1, Ep, v2, Eq, v3, Er, v1, n1, n2, n3> and got from below:
	 * The hyper-triangles are originally stored as this format:
	 * <<E1,E2,E3>,set1,set2,set3>, set1,2,3 are the vertices in E1&E2, E2&E3, E3&E1
	 * get all the 3combinations of the edges and check if there is three columns
	 * that has 0 at different rows:
	 * 110
	 * 011
	 * 101
	 * column: vertex, row: edge
	 
	public Set<ArrayList<Integer>> getTriangleIndexUsingRelatedE(BitMatrix array, 
			ArrayList<Set<Integer>> relateEdgeList)
	{

		int i, j, jj, k, kk, l, flag1, flag2, flag3;//flags for checking if there are 3 0s

		Set<ArrayList<Integer>> triangleIndex = new HashSet<ArrayList<Integer>>();
		ArrayList<ArrayList<ArrayList<Integer>>> triangleIndexRaw = new ArrayList<ArrayList<ArrayList<Integer>>>();

		ArrayList<Set<Integer>> relatededges= relateEdgeList;
		int edgeListSize = relatededges.size();
		for (i = 0; i < edgeListSize; i++)//for each edge in the hyperedge adjacency list
		{
			ArrayList<Integer> currentEdgeRelated = new ArrayList<Integer>(relatededges.get(i));
			currentEdgeRelated.remove(new Integer(i));
			int currentEdgeRelatedNum = currentEdgeRelated.size();
			for(jj = 0; jj < currentEdgeRelatedNum;  jj++)//go through each edge's related edge
			{
				j = currentEdgeRelated.get(jj);
				for(kk = jj+1; kk < currentEdgeRelatedNum; kk++)
				{	
					k =currentEdgeRelated.get(kk);
					flag1=0;//110
					flag2=0;//101
					flag3=0;//011
					ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
					ArrayList<Integer> edges = new ArrayList<Integer>();
					ArrayList<Integer> set1 = new ArrayList<Integer>();
					ArrayList<Integer> set2 = new ArrayList<Integer>();
					ArrayList<Integer> set3 = new ArrayList<Integer>();					//start checking the vertices
					for(l = 0; l < array.columns(); l++)
					{
						//110
						if((array.get(l, i)==true)&&(array.get(l, j)==true)&&(array.get(l, k)==false))
						{
							flag1=1;
							set1.add(l);
						}
						//101
						if((array.get(l, i)==true)&&(array.get(l, j)==false)&&(array.get(l, k)==true))
						{
							flag2=1;
							set2.add(l);
						}
						//011
						if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
						{
							flag3=1;
							set3.add(l);
						}
						
					}
					if(flag1*flag2*flag3==1)
					{
						edges.add(i);
						edges.add(j);
						edges.add(k);
						temp.add(edges);
						temp.add(set1);
						temp.add(set2);
						temp.add(set3);
						triangleIndexRaw.add(temp);
					}
				}
			}
		}
		//generate the real triangle set e.g. <E1,E2,E3>,<v1,v2>,<v3>,<v4,v5>
		//becomes<v4,E1,v1,E2,v3,E3>,<v5,E1,v1,E2,v3,E3>,<v4,E1,v2,E2,v3,E3>,<v5,E1,v2,E2,v3,E3>
		for(i = 0; i < triangleIndexRaw.size(); i++)
		{
			for(j = 0; j < triangleIndexRaw.get(3).size(); j++)//pick the first node
			{
				for(k = 0; k < triangleIndexRaw.get(1).size(); k++)//pick the second node
				{
					for(l = 0; l < triangleIndexRaw.get(2).size(); l++)//pick the third node
					{
						ArrayList<Integer> tmpTrian = new ArrayList<Integer>();
						tmpTrian.add(j);
						tmpTrian.add(triangleIndexRaw.get(i).get(0).get(0));
						tmpTrian.add(k);
						tmpTrian.add(triangleIndexRaw.get(i).get(0).get(1));
						tmpTrian.add(l);
						tmpTrian.add(triangleIndexRaw.get(i).get(0).get(2));
						triangleIndex.add(tmpTrian);
					}
				}
			}
		}
		return triangleIndex;
	}*/
	/*
	 * getTriangleIndexUsingRelatedEdges() gives all the unduplicated triangles in the graph
	 * The hyper-triangle is stored as this format:
	 * <E1,E2,E3,W,X,Y,Z,O,P,Q>, all of them are integers
	 * get all the 3combinations of the edges and check if there is three columns
	 * that has 0 at different rows:
	 * 110
	 * 011
	 * 101
	 * column: vertex, row: edge
	 * also recording all the patterns numbers:
	 * W-111: means how many nodes are shared by all the three edge
	 * X-100: how many nodes is belonging to EdgeA
	 * Y-010: how many nodes is belonging to EdgeB
	 * Z-001: how many nodes is belonging to EdgeC
	 * O-110: how many nodes is belonging to EdgeAB
	 * P-101: how many nodes is belonging to EdgeBC
	 * Q-011: how many nodes is belonging to EdgeAC
	 * at the end compare the last 3 numbers in each <E1,E2,E3,W,X,Y,Z> and sort them, smallest at end
	 * 
	 
	public Set<ArrayList<Integer>> getTriangleIndexUsingRelatedEdges(BitMatrix array, 
			ArrayList<Set<Integer>> relateEdgeList)
	{

		int i, j, jj, k, kk, l, flag1, flag2, flag3;//flags for checking if there are 3 0s

		Set<ArrayList<Integer>> triangleIndex = new HashSet<ArrayList<Integer>>();
		ArrayList<Set<Integer>> relatededges= relateEdgeList;
		int edgeListSize = relatededges.size();
		for (i = 0; i < edgeListSize; i++)//for each edge in the hyperedge adjacency list
		{
			if(i == 134)
			{
				i=134;//TESTUSE
			}
			ArrayList<Integer> currentEdgeRelated = new ArrayList<Integer>(relatededges.get(i));
			currentEdgeRelated.remove(new Integer(i));
			int currentEdgeRelatedNum = currentEdgeRelated.size();
			for(jj = 0; jj < currentEdgeRelatedNum;  jj++)//go through each edge's related edge
			{
				j = currentEdgeRelated.get(jj);
				for(kk = jj+1; kk < currentEdgeRelatedNum; kk++)
				{					
					int interOfAll = 0;//indicating how many nodes shared by all three edges
					int interOfTwo1 = 0;
					int interOfTwo2 = 0;
					int interOfTwo3 = 0;
					int oneEdgeNode1 = 0;
					int oneEdgeNode2 = 0;
					int oneEdgeNode3 = 0;
					k =currentEdgeRelated.get(kk);
					flag1=0;//110
					flag2=0;//101
					flag3=0;//011
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					//start checking the vertices
					for(l = 0; l < array.columns(); l++)
					{
						if((array.get(l, i)==true)&&(array.get(l, j)==true)&&(array.get(l, k)==false))
						{
							flag1=1;
							interOfTwo1++;
						}
						if((array.get(l, i)==true)&&(array.get(l, j)==false)&&(array.get(l, k)==true))
						{
							flag2=1;
							interOfTwo2++;
						}
						if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
						{
							flag3=1;
							interOfTwo3++;
						}
						if((array.get(l, i)==true)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
							interOfAll++;
						if((array.get(l, i)==false)&&(array.get(l, j)==false)&&(array.get(l, k)==true))
							oneEdgeNode1++;
						if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==false))
							oneEdgeNode2++;
						if((array.get(l, i)==true)&&(array.get(l, j)==false)&&(array.get(l, k)==false))
							oneEdgeNode3++;
					}
					if(flag1*flag2*flag3==1)
					{
						tmp.add(i);
						tmp.add(j);
						tmp.add(k);
						//add the number of all 3's intersection
						tmp.add(interOfAll);
						//add anytwo's intersection
						if((interOfTwo1 >= interOfTwo2) && (interOfTwo2 >= interOfTwo3))
						{
							tmp.add(interOfTwo1);
							tmp.add(interOfTwo2);
							tmp.add(interOfTwo3);
						}
						else if((interOfTwo1 >= interOfTwo2) && (interOfTwo2 <= interOfTwo3)
								&& (interOfTwo1 >= interOfTwo3))
						{
							tmp.add(interOfTwo1);
							tmp.add(interOfTwo3);
							tmp.add(interOfTwo2);
						}
						else if((interOfTwo2 >= interOfTwo1) && (interOfTwo1 >= interOfTwo3))
						{
							tmp.add(interOfTwo2);
							tmp.add(interOfTwo1);
							tmp.add(interOfTwo3);
						}
						else if((interOfTwo2 >= interOfTwo1) && (interOfTwo1 <= interOfTwo3)
								&& (interOfTwo2 >= interOfTwo3))
						{
							tmp.add(interOfTwo2);
							tmp.add(interOfTwo3);
							tmp.add(interOfTwo1);
						}
						else if((interOfTwo3 >= interOfTwo1) && (interOfTwo1 >= interOfTwo2))
						{
							tmp.add(interOfTwo3);
							tmp.add(interOfTwo1);
							tmp.add(interOfTwo2);
						}
						else if((interOfTwo3 >= interOfTwo1) && (interOfTwo1 <= interOfTwo2)
								&& (interOfTwo3 >= interOfTwo2))
						{
							tmp.add(interOfTwo3);
							tmp.add(interOfTwo2);
							tmp.add(interOfTwo1);
						}
						//add nodes # belonging to only one edge
						if((oneEdgeNode1 >= oneEdgeNode2) && (oneEdgeNode2 >= oneEdgeNode3))
						{
							tmp.add(oneEdgeNode1);
							tmp.add(oneEdgeNode2);
							tmp.add(oneEdgeNode3);
						}
						else if((oneEdgeNode1 >= oneEdgeNode2) && (oneEdgeNode2 <= oneEdgeNode3)
								&& (oneEdgeNode1 >= oneEdgeNode3))
						{
							tmp.add(oneEdgeNode1);
							tmp.add(oneEdgeNode3);
							tmp.add(oneEdgeNode2);
						}
						else if((oneEdgeNode2 >= oneEdgeNode1) && (oneEdgeNode1 >= oneEdgeNode3))
						{
							tmp.add(oneEdgeNode2);
							tmp.add(oneEdgeNode1);
							tmp.add(oneEdgeNode3);
						}
						else if((oneEdgeNode2 >= oneEdgeNode1) && (oneEdgeNode1 <= oneEdgeNode3)
								&& (oneEdgeNode2 >= oneEdgeNode3))
						{
							tmp.add(oneEdgeNode2);
							tmp.add(oneEdgeNode3);
							tmp.add(oneEdgeNode1);
						}
						else if((oneEdgeNode3 >= oneEdgeNode1) && (oneEdgeNode1 >= oneEdgeNode2))
						{
							tmp.add(oneEdgeNode3);
							tmp.add(oneEdgeNode1);
							tmp.add(oneEdgeNode2);
						}
						else if((oneEdgeNode3 >= oneEdgeNode1) && (oneEdgeNode1 <= oneEdgeNode2)
								&& (oneEdgeNode3 >= oneEdgeNode2))
						{
							tmp.add(oneEdgeNode3);
							tmp.add(oneEdgeNode2);
							tmp.add(oneEdgeNode1);
						}
 						triangleIndex.add(tmp);
					}
				}
			}
		}
		return triangleIndex;
	}*/
	/*
	 * This is the naive way of getTriangleIndexUsingRelatedEdges():get all 3 combinations of the edges
	 * and then test if they are a triangle
	 * getHyperTriangleIndex() gives all the unduplicated triangles in the graph
	 * The hyper-triangle is stored as this format:
	 * <E1,E2,E3>, all of them are integers
	 * get all the 3combinations of the edges and check if there is three columns
	 * that has 0 at different rows:
	 * 110
	 * 011
	 * 101
	 * column: vertex, row: edge
	 */
	public Set<ArrayList<Integer>> getHyperTriangleIndexNaive(BitMatrix array)
	{
		int i, j, k, l, flag1, flag2, flag3;//flags for checking if there are 3 0s

		Set<ArrayList<Integer>> triangleIndex = new HashSet<ArrayList<Integer>>();

		//n^3 for all combinations of the edges
		for(i = 0; i < array.rows(); i++)
		{
			for(j=i+1; j < array.rows(); j++)
			{
				for(k = j+1; k < array.rows();k++)
				{
					flag1=0;//110
					flag2=0;//101
					flag3=0;//011
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					//start checking the vertices
					for(l = 0; l < array.columns(); l++)
					{
						if((array.get(l, i)==true)&&(array.get(l, j)==true)&&(array.get(l, k)==false))
							flag1=1;
						if((array.get(l, i)==true)&&(array.get(l, j)==false)&&(array.get(l, k)==true))
							flag2=1;
						if((array.get(l, i)==false)&&(array.get(l, j)==true)&&(array.get(l, k)==true))
							flag3=1;
					}
					if(flag1*flag2*flag3==1)
					{
						tmp.add(i);
						tmp.add(j);
						tmp.add(k);
						triangleIndex.add(tmp);
					}
				}
			}
		}
		return triangleIndex;
	}
	
	/*
	 * get how many layers of a graph have based on a hypertriangle
	 * start from layer0, so a triangle with all nodes in it would be in layer0
	 */
	public int getLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper)
	{
		int layer = 0;
		
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1v = new HashSet<Integer>(itsVer(triangle.get(0), hyper));
		layer1v = union(itsVer(triangle.get(1), hyper), layer1v);
		layer1v = union(itsVer(triangle.get(2), hyper), layer1v);
		int i,size1,size2=0;
		
		ArrayList<Integer> layer1V = new ArrayList<Integer>(layer1v);//set to array
		int edgeSize1 = 3;
		int verSize1 = layer1V.size();
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1v.size(); i++)
			relatededges.addAll(itsEdges(layer1V.get(i), hyper));
		ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);

		for(i = 0; i < relatededgesArray.size(); i++)
			relatedver.addAll(itsVer(relatededgesArray.get(i), hyper));
		
		size1 = relatededges.size();
		size2 = relatedver.size();
		while(size1+size2 != edgeSize1+verSize1)
		{
			layer++;
			edgeSize1 = size1;
			verSize1 = size2;

			ArrayList<Integer> relatededgesArraynew = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArraynew.size(); i++)
			{
				relatedver =union(itsVer(relatededgesArraynew.get(i), hyper), relatedver);
			}

			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
			relatededgesArraynew = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArraynew.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArraynew.get(i), hyper), relatedver);
			}
			size1 = relatededges.size();
			size2 = relatedver.size();
		}
		return layer;
	}
	/*
	 * create a hashtable based on an array, rows are keys, column= 1, add it to value
	 */
	public 	HashMap<Integer, Set<Integer>> array2hash(BitMatrix hyper)
	{
		HashMap<Integer, Set<Integer>> hash = new HashMap<Integer, Set<Integer>>();
		int m,n;
		for(m = 0; m < hyper.rows(); m++)
		{
			Set<Integer> s = new HashSet<Integer>();
			for(n = 0; n< hyper.columns(); n++)
			{
				if(hyper.get(n, m)==true)
				{
					s.add(n);
				}
			}
			hash.put(m, s);
		}
		return hash;
	}
	/*
	 * return all edges related to one vertex
	 */
	public 	Set<Integer> itsEdges(int ver, BitMatrix hyper)
	{
		Set<Integer> itsedges = new HashSet<Integer>();
		int m;
		for(m = 0; m < hyper.rows(); m++)
		{
			if(hyper.get(ver, m)==true)
			{
				itsedges.add(m);
			} 
		}
		return itsedges;
	}
	/*
	 * return all edges related to one vertex
	 */
	public 	Set<Integer> itsVer(int edge, BitMatrix hyper)
	{
		Set<Integer> itsver = new HashSet<Integer>();
		int m;
		for(m = 0; m < hyper.columns(); m++)
		{
			if(hyper.get(m, edge)==true)
			{
				itsver.add(m);
			} 
		}
		return itsver;
	}
	/*
	 * union and intersection of sets
	 */
   public Set<Integer> union(Set<Integer> list1, Set<Integer> list2) {
        Set<Integer> set = new HashSet<Integer>();

        set.addAll(list1);
        set.addAll(list2);

        return set;
    }

   public Set<Integer> intersection(Set<Integer> list1, Set<Integer> list2) {
       Set<Integer> set = new HashSet<Integer>();

        for (Integer t : list1) {
            if(list2.contains(t)) {
                set.add(t);
            }
        }

        return set;
    }
	/*
	 * union and intersection of array
	 */
  public Set<Integer> unionArray(ArrayList<Integer> list1, ArrayList<Integer> list2) {
       Set<Integer> set = new HashSet<Integer>();

       set.addAll(list1);
       set.addAll(list2);

       return set;
   }
   public Set<Integer> intersectionArray(ArrayList<Integer> list1, ArrayList<Integer> list2) {
       Set<Integer> set = new HashSet<Integer>();

        for (Integer t : list1) {
            if(list2.contains(t)) {
                set.add(t);
            }
        }

        return set;
    }
   /*
    * exclude(list1, list2) returns the elements that in 2 but not in 1
    */
   public Set<Integer> exclude(Set<Integer> list1, Set<Integer> list2) {
       Set<Integer> set = new HashSet<Integer>();

       for (Integer t : list2) {
           if(!list1.contains(t)) {
               set.add(t);
           }
       }

       return set;
   }
	/*
	 * get how many layers of a graph have based on a star center
	 */
	public int getLayerStar(ArrayList<Integer> star, BitMatrix hyper)
	{
		int layer = 0;
		
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1e = new HashSet<Integer>(itsEdges(star.get(0), hyper));
		int i,size1,size2=0;
		
		ArrayList<Integer> layer1E = new ArrayList<Integer>(layer1e);
		int verSize1 = 1;
		int edgeSize1 = layer1E.size();
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1e.size(); i++)
			relatedver.addAll(itsVer(layer1E.get(i), hyper));
		ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);

		//for(i = 0; i < relatedverArray.size(); i++)
			//relatededges.addAll(itsEdges(relatedverArray.get(i), hyper));
		
		size1 = edgeSize1;
		size2 = relatedver.size();
		while(size1+size2 != edgeSize1+verSize1)
		{
			layer++;
			edgeSize1 = size1;
			verSize1 = size2;

			ArrayList<Integer> relatedverArraynew = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArraynew.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArraynew.get(i), hyper), relatededges);
			}

			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
			size1 = relatededges.size();
			size2 = relatedver.size();
		}
		return layer;
	}
	//the set of the permutation results
	private Set<ArrayList<Integer>> permutation = new HashSet<ArrayList<Integer>>();

	/*
	 * fullPermu() implements the permutation of an array, e.g 6 arrays for [1,2,3]:
	 * [[3, 2, 1], [3, 1, 2], [1, 3, 2], [2, 3, 1], [2, 1, 3], [1, 2, 3]]
	 */
	public Set<ArrayList<Integer>> fullPermu(int[] a, int[] b)
	{
		int i, j, k, m, n;
		int[] temp1 = a;
		int[] temp2 = b;
		if(temp2.length == 1)
		{
			ArrayList<Integer> result = new ArrayList<Integer>();
			for(int current: temp1)
				result.add(current);
			result.add(temp2[0]);
			permutation.add(result);
		}
		else for(i = 0; i<temp2.length;i++)
		{
			try{
				ArrayList<Integer> newArray = new ArrayList<Integer>();//prepare for second parameter

				for(j=0; j < i ; j++)
				{
					newArray.add(temp2[j]) ;
				}
				for(k = j+1; k < temp2.length; k++)
				{
					newArray.add(temp2[k]) ;
				}
				int[] tmp1 = new int[temp1.length + 1];

				if(temp1.length == 0)
					tmp1[0] = temp2[i];
				else
				{
					for(n = 0; n < temp1.length; n++)
					{
						tmp1[n] = temp1[n];
					}
					tmp1[n] = temp2[i];
				}

				int[] tmp2 = new int[newArray.size()];
				for(m = 0; m< newArray.size(); m++)
				{
					tmp2[m] = newArray.get(m);
				}
				fullPermu(tmp1, tmp2);
			}catch(IndexOutOfBoundsException e){
		          e.printStackTrace();
			}
		}
		return permutation;
	}

	/*
	 * getAllCombinations & combination give the permutation of m numbers out of n
	 */
	public static ArrayList<ArrayList<Integer>> Permutation(ArrayList<Integer> data, int length)
	{
		ArrayList<ArrayList<Integer>> allCombinations = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> initialCombination = new ArrayList<Integer>();
		combination(allCombinations, data, initialCombination, length);
		return allCombinations;
	}
	private static void combination(ArrayList<ArrayList<Integer>> allCombinations, ArrayList<Integer> data, 
		ArrayList<Integer> initialCombination, int length)
	{
		if(length == 1)
		{
			for(int i=0; i<data.size(); i++)
			{
				ArrayList<Integer> newCombination = new ArrayList<Integer>(initialCombination);
				newCombination.add(data.get(i));
				allCombinations.add(newCombination);
			}
		}

		else if(length > 1)
		{
			for(int i=0; i<data.size(); i++)
			{
				ArrayList<Integer> newCombination = new ArrayList<Integer>(initialCombination);
				newCombination.add(data.get(i));

				ArrayList<Integer> newData = new ArrayList<Integer>(data);
				for(int j=0; j<=i; j++)
					newData.remove(data.get(i));

				combination(allCombinations, newData, newCombination, length - 1);
			}
		}
	}
	/*
	 * get how many edges in a certain layer based on a hypertriangle
	 * layer0 has the three edges and all the related nodes
	 */
	public Set<Integer> getEofLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper, int layer)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
	
		relatededges.add(triangle.get(0));
		relatededges.add(triangle.get(1));
		relatededges.add(triangle.get(2));
		if(layer == 0)
			return relatededges;
		else
		{
			int l = 0; 
			int i;
			Set<Integer> previousLayerE = new HashSet<Integer>(relatededges);
			Set<Integer> previousLayerV = new HashSet<Integer>(itsVer(triangle.get(0), hyper));
			previousLayerV = union(itsVer(triangle.get(1), hyper), previousLayerV);
			previousLayerV = union(itsVer(triangle.get(2), hyper), previousLayerV);
			Set<Integer> newLayerE = new HashSet<Integer>();
	
			while(l < layer)
			{
				l++; 
	
				for(i = 0 ; i < previousLayerV.size(); i++)
				{
					newLayerE =union(itsEdges(new ArrayList<Integer>(previousLayerV).get(i), hyper), newLayerE);
				}
				newLayerE = exclude(previousLayerE, newLayerE);
				previousLayerE = union(previousLayerE, newLayerE);//all previous layers E given to it
	
				for(i = 0 ; i < previousLayerE.size(); i++)
					previousLayerV = union(itsVer(new ArrayList<Integer>(previousLayerE).get(i), hyper), previousLayerV);
				
			}
			relatededges = newLayerE;
		return relatededges;
		}
	}
	/*
	 * get how many vertices of a certain number of layers based on a hypertriangle
	 */
	public Set<Integer> getVofLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper, int layer)
	{
		Set<Integer> relatevs = new HashSet<Integer>();
		
		relatevs = union(itsVer(triangle.get(0), hyper), relatevs);
		relatevs = union(itsVer(triangle.get(1), hyper), relatevs);
		relatevs = union(itsVer(triangle.get(2), hyper), relatevs);
		if(layer == 0)
			return relatevs;
		else
		{
			int l = 0; 
			int i;
			Set<Integer> previousLayerV = new HashSet<Integer>(relatevs);
			Set<Integer> previousLayerE = new HashSet<Integer>();
			previousLayerE.add(triangle.get(0));
			previousLayerE.add(triangle.get(1));
			previousLayerE.add(triangle.get(2));
			Set<Integer> newLayerV = new HashSet<Integer>();
	
			while(l < layer)
			{
				l++; 
	
				for(i = 0 ; i < previousLayerE.size(); i++)
				{
					newLayerV =union(itsVer(new ArrayList<Integer>(previousLayerE).get(i), hyper), newLayerV);
				}
				newLayerV = exclude(previousLayerV, newLayerV);
				previousLayerV = union(previousLayerV, newLayerV);//all previous layers E given to it
	
				for(i = 0 ; i < previousLayerV.size(); i++)
					previousLayerE = union(itsEdges(new ArrayList<Integer>(previousLayerV).get(i), hyper), previousLayerE);
				
			}
			relatevs = newLayerV;
		return relatevs;
		}
	}
	/*
	 * get vertices of a certain layers based on a star center
	 */
	public Set<Integer> getVofLayerStar(ArrayList<Integer> star, BitMatrix hyper, int layer)
	{
		int level = 1;
		
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1e = new HashSet<Integer>(itsEdges(star.get(0), hyper));
		int i;
		
		ArrayList<Integer> layer1E = new ArrayList<Integer>(layer1e);
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1e.size(); i++)
			relatedver.addAll(itsVer(layer1E.get(i), hyper));

		while(level < layer)
		{
			level++;

			ArrayList<Integer> relatedverArraynew = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArraynew.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArraynew.get(i), hyper), relatededges);
			}

			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
		}
		return relatedver;
	}
	
	/*
	 * get how many edges of a certain layer based on a star center
	 */
	public Set<Integer> getEofLayerStar(ArrayList<Integer> star, BitMatrix hyper, int layer)
	{
		int level = 1;
		
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1e = new HashSet<Integer>(itsEdges(star.get(0), hyper));
		int i;
		
		ArrayList<Integer> layer1E = new ArrayList<Integer>(layer1e);
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1e.size(); i++)
			relatedver.addAll(itsVer(layer1E.get(i), hyper));
		ArrayList<Integer> relatedverArraynew = new ArrayList<Integer>(relatedver);
		for(i = 0 ; i < relatedverArraynew.size(); i++)
		{
			relatededges = union(itsEdges(relatedverArraynew.get(i), hyper), relatededges);
		}
		while(level < layer)
		{
			level++;

			relatedverArraynew = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArraynew.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArraynew.get(i), hyper), relatededges);
			}

			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
		}
		return relatededges;
	}
	/*
	 * determine if a hypergraph has a subgraph, ignore label
	 * use one edge in the query to match all the edges in the hypergraph
	 * and layer verify,return matched edges as the possible positions
	 */
	public ArrayList<Integer> determineOneEdge(BitMatrix array, BitMatrix hyper){
	
		int i, j, k, flag=1;
		ArrayList<Integer> match = new ArrayList<Integer>();
		int edgeQ = 0;
		Set<Integer> edgeQver = itsVer(0, array);
		ArrayList<Integer> edge0v = new ArrayList<Integer>(edgeQver);
		for(i = 0; i < hyper.rows();i++)//match each edge: 0-i
		{
			if(edgeQver.size() > itsVer(i, hyper).size())
			{
				flag = 0;
				continue;
			}
			//compare all the Edge0 nodes with edge0
			HashMap<Integer, Integer> edge0vhash = new HashMap<Integer, Integer>();
			ArrayList<ArrayList<Integer>> permuV = Permutation(new ArrayList<Integer>(itsVer(i, hyper)),edgeQver.size());

			for(ArrayList<Integer> currentV: permuV)
			{
				for(j = 0; j < edge0v.size(); j++)
				{
					edge0vhash.put(edge0v.get(j), currentV.get(j));
				}
				flag = 1;

				for(j = 0 ; j < currentV.size(); j++)
				{
					for(k = 0 ; k < array.columns(); k++)//centerEdge's vers to all other edges relation
					{
						if(array.get(k, 0)==hyper.get(currentV.get(j), i))
						{
							flag = flag * 1;
						}
						else
						{
							flag = 0 ;
							break;
						}
					}
					if(flag == 1)
					{
						break;
					}
				}
			}
			//get all edges related to edge0
			Set<Integer> edge0edgesQ = new 	HashSet<Integer>();
			Set<Integer> edge0edges = new 	HashSet<Integer>();
			for(j = 0; j < edge0v.size(); j++)
			{
				edge0edgesQ = union(edge0edgesQ, itsEdges(edge0v.get(j), array));
				edge0edges = union(edge0edges, itsEdges(edge0vhash.get(edge0v.get(j)), hyper));
			}
			//compare all the nodes and layer1E relationship
			if(edge0edgesQ.size() > edge0edges.size())
			{
				flag = 0;
				continue;
			}
			HashMap<Integer, Integer> edgesHash = new HashMap<Integer, Integer>();
			ArrayList<Integer> edge0edgesQarray = new ArrayList<Integer>(edge0edgesQ);
			ArrayList<ArrayList<Integer>> permuE = Permutation(new ArrayList<Integer>(edge0edges),edge0edgesQ.size());
			for(ArrayList<Integer> currentE: permuE)
			{
				for(j = 0; j < edge0edgesQ.size(); j++)
				{
					edgesHash.put(edge0edgesQarray.get(j), currentE.get(j));
				}
				flag = 1;

				for(j = 0 ; j < edge0edgesQarray.size(); j++)
				{
				for(k = 0; k < edge0v.size(); k++)
				{ 
					if((array.get(edge0edgesQarray.get(j), edge0v.get(k))==
							hyper.get(edge0vhash.get(edge0v.get(k)), edgesHash.get(edge0edgesQarray.get(j)) )))
					{
						flag = flag * 1;
					}
					else
					{
						flag = 0 ;
						break;
					}
				}	
				
				if(flag == 1)
				{
					break;
				}
			}
			}
			if(flag == 0)
				continue;
			else
			{
				match.add(i);
				continue;
			}
		}
		return match;
	}
	public Set<ArrayList<Integer>> determineTriangleNew(BitMatrix array, Set<ArrayList<Integer>> indexT, BitMatrix hyper){
		Set<ArrayList<Integer>> returnIndx = new HashSet<ArrayList<Integer>>();
		int m,n,p,q,r;
		int flag = 0;//flag should be 1 if there is a match
		Set<ArrayList<Integer>> large = indexT;
		Set<ArrayList<Integer>> largeReal = new HashSet<ArrayList<Integer>>();
		//mySet: the triangles got by subgraph
		Set<ArrayList<Integer>> mySet = new HashSet<ArrayList<Integer>>();
		int layerQ;
		ArrayList<Set<Integer>> relateEdgeListQ = relatedEdges(array);
		mySet = getTriIndex(array, relateEdgeListQ);
		ArrayList<ArrayList<Integer>> mySetArray = new ArrayList<ArrayList<Integer>>(mySet);
		if(mySet.size() > 0)
		{
			ArrayList<Integer> triQ = mySetArray.get(0);//triangle in Q
			layerQ = getLayerTriangle(triQ, array);
			ArrayList<ArrayList<Integer>> layerQEs = new ArrayList<ArrayList<Integer>>();//each layers edges
			ArrayList<ArrayList<Integer>> layerQVs = new ArrayList<ArrayList<Integer>>();//each layers vertices
			for(m = 0; m <= layerQ; m++)
			{
				layerQEs.add(new ArrayList<Integer>(getEofLayerTriangle(triQ, array, m)));
				layerQVs.add(new ArrayList<Integer>(getVofLayerTriangle(triQ, array, m)));
			}
			for(ArrayList<Integer> j : large)
			{				
				//compare three edges's vertice intersections
				if((triQ.get(3)<=j.get(3)) && (triQ.get(4)<=j.get(4)) && (triQ.get(5)<=j.get(5)) && (triQ.get(6)<=j.get(6)))
				{
					largeReal.add(j);
				}
			}
			//use optimized index to do the matching
			for(ArrayList<Integer> j: largeReal)
			{
				ArrayList<ArrayList<Integer>> layerEs = new ArrayList<ArrayList<Integer>>();//each layers edges in hyper
				ArrayList<ArrayList<Integer>> layerVs = new ArrayList<ArrayList<Integer>>();//each layers vertices in hyper
				for(m = 0; m <= layerQ; m++)
				{
					layerEs.add(new ArrayList<Integer>(getEofLayerTriangle(j, hyper, m)));
					layerVs.add(new ArrayList<Integer>(getVofLayerTriangle(j, hyper, m)));
				}
				for(m = 0; m <= layerQ; m++)
				{
					ArrayList<ArrayList<Integer>> permuLayermE = Permutation(layerEs.get(m),layerQEs.get(m).size());
					ArrayList<ArrayList<Integer>> permuLayermV = Permutation(layerVs.get(m),layerQVs.get(m).size());
					for(n = 0; n < permuLayermE.size(); n++)
					{
						for(p = 0; p < permuLayermV.size(); p++)
						{
							flag = 1;
							for(q = 0; q < permuLayermE.get(n).size(); q++)
							{
								for(r = 0; r < permuLayermV.get(p).size(); r++)
								{
									//System.out.print("inQ" +layerQVs.get(m).get(r)+layerQEs.get(m).get(q)+ array.get(layerQVs.get(m).get(r), layerQEs.get(m).get(q)));
									//System.out.println("inH" +permuLayermV.get(p).get(r)+permuLayermV.get(n).get(q)+hyper.get(permuLayermV.get(p).get(r), permuLayermV.get(n).get(q)));
									if((array.get(layerQVs.get(m).get(r), layerQEs.get(m).get(q))==
											hyper.get(permuLayermV.get(p).get(r), permuLayermE.get(n).get(q))) )
								
									{
										flag = flag * 1;
									}
									else
									{
										flag = 0 ;
										break;
									}
								}
								if(flag == 0) break;
							}
							if(flag == 1) break;
						}
						if(flag == 1) continue;
					}
				}
				if(flag == 1)
					returnIndx.add(j);
			}
		}
		else
			System.out.print("there is no triangle in the query\n");
		return returnIndx;
		
}
	/*
	 * determine if a hypergraph has a subgraph, ignore label
	 * give each vertex and edge a hash value to match to the hyper graph
	 * if there is a match, this subgraph is in the hypergraph, otherwise, no
	 */
	public Set<ArrayList<Integer>> determineTriangle(BitMatrix array, Set<ArrayList<Integer>> indexT, BitMatrix hyper){
		Set<ArrayList<Integer>> returnIndx = new HashSet<ArrayList<Integer>>();
		
		int m,n,o;
		int flag = 0;//flag should be 1 if there is a match
		Set<ArrayList<Integer>> large = indexT;
		Set<ArrayList<Integer>> largeReal = new HashSet<ArrayList<Integer>>();
		//mySet: the triangles got by subgraph
		Set<ArrayList<Integer>> mySet = new HashSet<ArrayList<Integer>>();

		/*
		 * create hash table: paper-authors
		 */
		HashMap<Integer, Set<Integer>> paper_au = new HashMap<Integer, Set<Integer>>();
		paper_au = array2hash(hyper);
		ArrayList<Integer> allPaper = new ArrayList<Integer>(paper_au.keySet());
		Set<Integer> allA = new HashSet<Integer>();
		//get the array of all the authors(actually all the vertex-numbers)
		for(Integer currentPaper: allPaper)
		{
			for(Integer author: paper_au.get(currentPaper) )
				allA.add(author);
		}
		
		int layerQ;
		ArrayList<Set<Integer>> relateEdgeListQ = relatedEdges(array);
		mySet = getTriIndex(array, relateEdgeListQ);
		ArrayList<ArrayList<Integer>> mySetArray = new ArrayList<ArrayList<Integer>>(mySet);
  
		if(mySet.size() > 0)
		{
			ArrayList<Integer> i = mySetArray.get(0);//triangle in Q
 
			//GET THE LAYER NUMBER OF THE QUERY  
			layerQ = getLayerTriangle(i, array);
			for(ArrayList<Integer> j : large)
			{				
				//compare three edges's vertice intersections
				if((i.get(3)<=j.get(3)) && (i.get(4)<=j.get(4)) && (i.get(5)<=j.get(5)) && (i.get(6)<=j.get(6)))
				{
					largeReal.add(j);
				}
			}

			//use optimized index to do the matching
			for(ArrayList<Integer> j: largeReal)
			{
				//hash the sub graph's v and e to the large graph's v and e
				HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
				HashMap<Integer, Integer> layer0Vhash = new HashMap<Integer, Integer>();

				////////////////////////////STEP1
				//get layer0 all nodes 
				ArrayList<Integer> layer0VQ = new ArrayList<Integer>(getVofLayerTriangle(i, array, 0));
				ArrayList<Integer> layer0V = new ArrayList<Integer>(getVofLayerTriangle(j, hyper, 0));
				//triangle's edges hash from subgraph to large graph,then permutate the vertices
				for(m = 0; m < 3; m++)
				{
					ehash.put(i.get(m), j.get(m));
					flag = 1;
				}
				if(layer0VQ.size() > layer0V.size())//if there are more related vertices: false
				{
					flag = 0;
					continue;
				}
				//compare all the nodes and 3edge relationship
				
				ArrayList<ArrayList<Integer>> permuLayer0V = Permutation(layer0V,layer0VQ.size());
				for(ArrayList<Integer> currentLayer0V: permuLayer0V)
				{
					flag = 1;

					for(o = 0 ; o < layer0VQ.size(); o++)
					{
						if((array.get(layer0VQ.get(o), i.get(0))==hyper.get(currentLayer0V.get(o), j.get(0)))
								&&(array.get(layer0VQ.get(o), i.get(1))==hyper.get(currentLayer0V.get(o), j.get(1)))
								&&(array.get(layer0VQ.get(o), i.get(2))==hyper.get(currentLayer0V.get(o), j.get(2)))
								)
								{
									flag = flag * 1;
								}
						else
						{
							flag = 0 ;
							break;
						}
					}
					if(flag == 1)
					{
						for(o = 0 ; o < layer0VQ.size(); o++)
							layer0Vhash.put(layer0VQ.get(o), currentLayer0V.get(o));
						break;
					}
				}
				if(flag == 0)
					continue;
				///////////////////////////////STEP2
				//get layer1 all edges 
				ArrayList<Integer> layer1EQ = new ArrayList<Integer>(getEofLayerTriangle(i, array, 1));
				ArrayList<Integer> layer1E = new ArrayList<Integer>(getEofLayerTriangle(j, hyper, 1));
				if(layer1EQ.size() > layer1E.size())//if there are more related vertices: false
				{
					flag = 0;
					continue;
				}
				//compare all the nodes and layer1E relationship
				
				ArrayList<ArrayList<Integer>> permuLayer1E = Permutation(layer1E,layer1EQ.size());
				for(ArrayList<Integer> currentLayer1E: permuLayer1E)
				{
					flag = 1;

					for(o = 0 ; o < layer1EQ.size(); o++)
					{
						for(n=0; n < layer0VQ.size(); n++)
						{
							if((array.get(layer0VQ.get(n), layer1EQ.get(o))==
									hyper.get(layer0Vhash.get(layer0VQ.get(n)), currentLayer1E.get(o)))
								)
									{
										flag = flag * 1;
									}
							else
							{
								flag = 0 ;
								break;
							}
						}
					}
					if(flag == 1)
					{
						break;
					}
				}
				if(flag == 1)
					returnIndx.add(j);
			}
		}
		
		return returnIndx;
	}
	
	/*
	 * This function determins whether there is star shape in the hypergraph
	 * return match is all the vertices that can match the center
	 */
	public Set<Integer> determineStar(BitMatrix array, Set<ArrayList<Integer>> indexS, BitMatrix hyper){

		System.out.print("\n(createindexwithoutDup.java-determinStar())checking star index...\n");
		Set<Integer> results = new HashSet<Integer>();
		int unRelatedV = 0;
		int layerQ = 0;
		Set<ArrayList<Integer>> large = new HashSet<ArrayList<Integer>>();
		Set<ArrayList<Integer>> mySet = new HashSet<ArrayList<Integer>>();
		Set<ArrayList<Integer>> newLarge = new HashSet<ArrayList<Integer>>();
		ArrayList<Integer> unStarEdge = new ArrayList<Integer>();//store unstar edges
		ArrayList<Integer> starEdge = new ArrayList<Integer>();//store star edges
		ArrayList<Integer> unStarV = new ArrayList<Integer>();//store unrelated vertices

		int maxDegree = 0, maxNode = -1, flag = 0, i,j,k,m,n, flg=1, size;//flag = 1 means there is at least a star, maxNode = -1 , there is not match
		
		//each edge's vertices in hyper
		HashMap<Integer, Set<Integer>> paper_au = new HashMap<Integer, Set<Integer>>();
		paper_au = array2hash(hyper);
		

		HashMap<Integer, Integer> realVhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realVhashnew = new HashMap<Integer, Integer>(); 
				
		large = indexS ;
		mySet = getStarIndex(array);

		if(mySet.size() == 0)
			return null;
		//for(ArrayList<Integer> ii: mySet)
			//System.out.println(ii);
		//use the largest degree star in subgraph to match the same degree star in hypergraph
		for(ArrayList<Integer> current: mySet)
		{
			if(current.get(1) > maxDegree)
			{
				maxDegree = current.get(1);
				maxNode = current.get(0);
			}
		}
		//get all unStar edges
		for(j = 0; j< array.rows() ; j++)
		{
			if(array.get(maxNode, j)!=true)
				unStarEdge.add(j);	
			else
				starEdge.add(j);
		}
		//get all the vertices unrelated to the star
		for(j = 0; j < array.columns(); j++)
		{
			int tempSum = 0;
			for(Integer currentStarEdge: starEdge)
			{
				if(array.get(j, currentStarEdge) == true)
				tempSum = 1 + tempSum;
			}
			if(tempSum == 0)
			{
				unStarV.add(j);
				unRelatedV++;
			}
				
		}
		if(maxNode != -1)// maxNode == -1 means sub has no star, mySet is empty
		{
			//get a new set that only has the stars with same number of edges as the max one in sub
			for(ArrayList<Integer> current: large)
			{		
				if(current.get(1) >= maxDegree)
				{
					flag = 1;
					newLarge.add(current);
				}
			}
		}
		if(flag == 1)
		{
			//GET THE LAYER OF THE QUERY
			ArrayList<Integer> st = new ArrayList<Integer>();
			st.add(maxNode);
			st.add(maxDegree);
			layerQ = getLayerStar(st, array);
			//EVERY LOOP FOR MATCHING STARTS HERE
			for(ArrayList<Integer> current: newLarge)
			{
				ArrayList<Integer> currentEdgeSet = new ArrayList<Integer>();
				//get current's first element(vertex)'s all edges related to it
				for(i = 0 ; i< hyper.rows(); i++)
				{
					if(hyper.get(current.get(0), i)  == true)
						currentEdgeSet.add(i);
				}
				//foreach hyper's edge's vertex with more than maxDegree edges, permute the edges to match the sub's				
				ArrayList<ArrayList<Integer>> edgeStarPermu = Permutation(currentEdgeSet,maxDegree);

				for(ArrayList<Integer> currentEdgePerm: edgeStarPermu)
				{
					int temp = 0;
					//hash the edges
					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
					//hash star edges
					for(j = 0; j< array.rows() ; j++)
					{
						if(array.get(maxNode, j) == true)
							ehash.put(j, currentEdgePerm.get(temp++));							
					}
					
					//union all the vertices related to star edges
					Set<Integer> unionSet = new HashSet<Integer>(); 
					for(Integer edge: currentEdgePerm)
						unionSet = union(unionSet, paper_au.get(edge));
					unionSet.remove(current.get(0));
					ArrayList<Integer> nodeUnion = new ArrayList<Integer>(unionSet);
					
					//the related nodes of these edges in hyper need to permutation out sub's nodes' number-1
					ArrayList<ArrayList<Integer>> nodeStarPermu = Permutation(nodeUnion,array.columns()-1-unRelatedV);

					for(ArrayList<Integer> currentNodePermu: nodeStarPermu)
					{
						flg = 1;//flg shows whether there is a match for all nodes.
						HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
						ArrayList<Integer> tempNodes = new ArrayList<Integer>();
						for(k = 0; k< array.columns(); k++)
						{
							if((k!=maxNode) && (!unStarV.contains(k)))
								tempNodes.add(k);
						}
						for(k = 0; k< array.columns()-1-unRelatedV; k++)
						{
							vhash.put(tempNodes.get(k), currentNodePermu.get(k));
						}
						vhash.put(maxNode, current.get(0));
						//System.out.print(vhash);
						//System.out.println(ehash);
						for(m = 0; m < array.rows(); m++)
						{
							if(!unStarEdge.contains(m))
							{
								for(n = 0 ; n < array.columns(); n++)
								{
									if(!unStarV.contains(n))
									{
										if((array.get(n, m) == hyper.get(vhash.get(n), ehash.get(m))) || ((array.get(n, m) == false)&&(hyper.get(vhash.get(n), ehash.get(m)) == true)))
											flg = flg*1;
										else
										{
											flg = 0;
											break;
										}
									}
								}
							}
							if(flg == 0)
								break;
						}
						if((flg == 1) && (maxDegree != array.rows()))
						{
							//match unStaredges
							ArrayList<Integer> unStarUnionEdge = new ArrayList<Integer>();
							Set<Integer> relatedEdges = new HashSet<Integer>();
							relatedEdges = getEofLayerStar(current, hyper, layerQ);
							for(Integer re:relatedEdges) 
							{
								if(!ehash.values().contains(re))
								{
									unStarUnionEdge.add(re);
								}
							}

							ArrayList<ArrayList<Integer>> unStarEdgePermu = Permutation(unStarUnionEdge,array.rows()-maxDegree);
							for(ArrayList<Integer> currentUnStarEdge: unStarEdgePermu)
							{		
								HashMap<Integer, Integer> unStarEdgeHash = new HashMap<Integer, Integer>();
							
								for(j= 0 ; j< unStarEdge.size(); j++)
								{
									unStarEdgeHash.put(unStarEdge.get(j), currentUnStarEdge.get(j));
								}
							
								for(j= 0 ; j< unStarEdge.size(); j++)
								{
									flg = 1;
									for(i = 0 ; i < array.columns(); i++)
									{
										if(!unStarV.contains(i))
										{
											if(array.get(i,unStarEdge.get(j)) == hyper.get(vhash.get(i), unStarEdgeHash.get(unStarEdge.get(j))) || ((array.get(i,unStarEdge.get(j)) == false )&&(hyper.get(vhash.get(i), unStarEdgeHash.get(unStarEdge.get(j)))== true)))
											{
												flg = flg*1;
											}
											else
											{
												flg = 0;
												break;
											}
										}
									}
									if(flg == 0)
										break;
								}
								if(flg == 1)
								{
									if(unRelatedV > 0)
									{
										//union all the vertices Unrelated to star edges
										ArrayList<Integer> unionUnrelatedSet = new ArrayList<Integer>(); 
										Set<Integer> relatedVers = new HashSet<Integer>();
										relatedVers = getVofLayerStar(current, hyper, layerQ); 
										for(Integer re:relatedVers) 
										{
											if(!vhash.values().contains(re))
											{
												unionUnrelatedSet.add(re);
											}
										}
										
										ArrayList<ArrayList<Integer>> unStarVPermu = Permutation(unionUnrelatedSet,unRelatedV);
										for(ArrayList<Integer> currentUnStarVPermu: unStarVPermu)
										{
											HashMap<Integer, Integer> unStarVHash = new HashMap<Integer, Integer>();
											for(j= 0 ; j< unStarV.size(); j++)
											{
												unStarVHash.put(unStarV.get(j), currentUnStarVPermu.get(j));
											}
											for(Integer jj: unStarEdgeHash.keySet())
											{
												flg = 1;
												for(i = 0; i < unStarV.size(); i++)
												{
													if(array.get(unStarV.get(i), jj) == hyper.get(unStarVHash.get(unStarV.get(i)), unStarEdgeHash.get(jj)) || ((array.get(unStarV.get(i), jj) == false)&&(hyper.get(unStarVHash.get(unStarV.get(i)), unStarEdgeHash.get(jj)) == true)))
													{
														flg = flg*1;
													}
													else
													{
														flg = 0;
														break;
													}
												}
												if(flg == 0)
													break;
											}
											if(flg == 1)
											{ 
												break;
											}
										}
										if(flg == 1)
											break;
										
									}
								}
								if(flg == 1)
								{ 
									break;
								}
							}

							//System.out.println(ehash);
							//System.out.println(vhash);
						}
						if(flg == 1)
						{
							realVhash = vhash; //!!!!first control
							break;
						}
					}
					//DO THE MEMORY RELEASE
					for(size = 0 ; size < nodeStarPermu.size(); size++)
						nodeStarPermu.remove(size);
					nodeStarPermu.trimToSize();
					if(flg == 1)
					{ 
 						break;
					}
				}

				//DO THE MEMORY RELEASE
				for(size = 0 ; size < edgeStarPermu.size(); size++)
					edgeStarPermu.remove(size);
				edgeStarPermu.trimToSize();
				if(flg == 1)
				{ 
					realVhashnew = realVhash;//!!!!second control
					results.add(realVhashnew.get(maxNode)); 
				}
			}
		}  
		System.out.println(results);
		return results;
	}
	//get2ndlevelTri() generates all the second level index with 2 triangles
	//its a list of numbers, where each number is the center of the 5-edge structure
	public Set<ArrayList<Integer>> get2ndlevelTri(Set<ArrayList<Integer>> triangleList)
	{
		
		int i, j;
		Set<ArrayList<Integer>> secondLevelList = new HashSet<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> triangles = new ArrayList<ArrayList<Integer>>(triangleList);
		ArrayList<ArrayList<Integer>> trianglesEdgeOnly = new ArrayList<ArrayList<Integer>>();
		//convert the orginal triangle list to only-edge list
		for(i = 0; i < triangles.size(); i++)
		{
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(triangles.get(i).get(0));
			tmp.add(triangles.get(i).get(1));
			tmp.add(triangles.get(i).get(2));
			trianglesEdgeOnly.add(tmp);
		}
		for( i = 0; i < trianglesEdgeOnly.size(); i++)
		{
			for( j = i; j < trianglesEdgeOnly.size(); j++)
			{
				if(intersectionArray(trianglesEdgeOnly.get(i), trianglesEdgeOnly.get(j)).size() == 1)
				{
					secondLevelList.add(new ArrayList<Integer>(unionArray(trianglesEdgeOnly.get(i), trianglesEdgeOnly.get(j))));
				}
			}
		}
		return secondLevelList;
	}
	
	//determinSecond() determine if a second level index is the position of the query, return the matches
	public Set<ArrayList<Integer>> determinSecond(BitMatrix array, Set<ArrayList<Integer>> index2ndQ, Set<ArrayList<Integer>> index2nd, BitMatrix hyper)
	{
		Set<ArrayList<Integer>> match = new HashSet<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>(index2nd);
		ArrayList<ArrayList<Integer>> indexQ = new ArrayList<ArrayList<Integer>>(index2ndQ);
		ArrayList<Integer> oneIndexEQ = indexQ.get(0);
		ArrayList<Integer> verSet = new ArrayList<Integer>();
		ArrayList<Integer> verSetQ = new ArrayList<Integer>();
		int i, j, flag;
		for(ArrayList<Integer> oneIndexE:index)
		{
			HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
			for(i = 0; i < 5;i++)
			{
				ehash.put(oneIndexEQ.get(i),oneIndexE.get(i));
				verSet = new ArrayList<Integer>(unionArray(verSet, new ArrayList<Integer>(itsVer(oneIndexE.get(i), hyper))));
				verSetQ = new ArrayList<Integer>(unionArray(verSetQ, new ArrayList<Integer>(itsVer(oneIndexEQ.get(i), array))));
			}
			ArrayList<ArrayList<Integer>> permuVerSet = Permutation(verSet, verSetQ.size());
			for(ArrayList<Integer> currentVerSet: permuVerSet)
			{
				flag = 1;
	
				for(i = 0 ; i < verSetQ.size(); i++)
				{
					if((array.get(verSetQ.get(i), oneIndexEQ.get(0))==hyper.get(currentVerSet.get(i), ehash.get(oneIndexEQ.get(0))))
							&&(array.get(verSetQ.get(i), oneIndexEQ.get(1))==hyper.get(currentVerSet.get(i), ehash.get(oneIndexEQ.get(1))))
							&&(array.get(verSetQ.get(i), oneIndexEQ.get(2))==hyper.get(currentVerSet.get(i), ehash.get(oneIndexEQ.get(2))))
							&&(array.get(verSetQ.get(i), oneIndexEQ.get(3))==hyper.get(currentVerSet.get(i), ehash.get(oneIndexEQ.get(3))))
							&&(array.get(verSetQ.get(i), oneIndexEQ.get(4))==hyper.get(currentVerSet.get(i), ehash.get(oneIndexEQ.get(4))))
							
							)
							{
								flag = flag * 1;
							}
					else
					{
						flag = 0 ;
						break;
					}
				}
				if(flag == 1)
				{
					match.add(oneIndexE);
					break;
				}
			}
		}
		return match;
	}
	public static void main(String args[])
	{
		createindexwithoutDup c = new createindexwithoutDup();
	}
}
