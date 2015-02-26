import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import cern.colt.bitvector.BitMatrix;
/*
 * ///OBSOLETE
 *  determin.java, newDeter.java and newDetermin.java are 
 * replaced by createindexwithoutDup.java
 */

public class determin {
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
   
   public Set<Set<Integer>> UNION(Set<Set<Integer>> list1, Set<Set<Integer>> list2) {
       Set<Set<Integer>> set = new HashSet<Set<Integer>>();


       set.addAll(list1);
       set.addAll(list2);

       return set;
    }   
   
   public Set<Integer> difference(Set<Integer> list1, Set<Integer> list2) {
	   Set<Integer> tmp = new HashSet<Integer>(list1);
	    tmp.removeAll(list2);
	    return tmp;
	  }
   
   
	/*
	 * The hyper-triangle is stored as this format:
	 * <v1, Ep, v2, Eq, v3, Er, v1>, all of them are integers
	 */
	public Set<ArrayList<Integer>> getHyperTriangleIndex(BitMatrix array)
	{
		Set<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
		int i, j, k, p, q, r;//i,k,q are for edge, j,p,r are for vertex

		int v1, e1, v2, e2, v3, e3;
		
		//int[] tempArray = new int[6];
		
		for(i = 0 ; i < array.rows() - 2; i++)
		{
			for ( j = 0 ; j < array.columns() ; j ++ )
			{
				if(array.get(j, i) == true)
				{
					//v1 = j;
					for(k = 0; k < array.rows()-1; k ++ )
					{
						if((array.get(j, k) == true) && (k != i))
						{
							//e1 = k;
							//find the second vertex
							for( p = 0; p < array.columns(); p++)
							{
								if((array.get(p, k) == true) && (p != j))
								{
									//v2 = p;
									for(q = 0; q < array.rows(); q++)
									{
										if((array.get(p, q) == true) && ( q!= i ) && (q != k))
										{
											//e2 = q;
											for(r = 0 ; r< array.columns(); r++)
											{
												if((array.get(r, q) == true) && (array.get(r, i) == true) && (r != j) && (r!= p))
												{
													v1 = j; e1 = k;
													v2 = p; e2 = q;
													v3 = r; e3 = i;
													ArrayList<Integer> tempArray = new ArrayList<Integer>();
													//tempArray.add(0, v1+1);
													//tempArray.add(1, e1+315);
													///tempArray.add(2, v2+1);
													//tempArray.add(3, e2+315);
													//tempArray.add(4, v3+1);
													//tempArray.add(5, e3+315);
													tempArray.add(0, v1);
													tempArray.add(1, e1);
													tempArray.add(2, v2);
													tempArray.add(3, e2);
													tempArray.add(4, v3);
													tempArray.add(5, e3);
													set.add(tempArray);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//2 STEPS TO REMOVE REPLICATION: SAME SET BUT DIFFERENT ORDER; TRIANGLE HAS 3 VERTICES ON SAME EDGE
		
		/*/each arraylist is the key in a set, and the value is its corresponding vertex set and edge set,
		//if there is arraylist has repeated set value, then delete it, this is to remove all repeated triangles
		int flag = 0; //0 means this arraylist has not been repeated.
		HashMap<ArrayList<Integer>, Set<Set<Integer>>> triangleHash = new HashMap<ArrayList<Integer>, Set<Set<Integer>>>();
				
		for(ArrayList<Integer> trg: set)
		{
			Set<Integer> temp1 = new HashSet<Integer>();
			temp1.add(trg.get(0));
			temp1.add(trg.get(2));
			temp1.add(trg.get(4));
			Set<Integer> temp2 = new HashSet<Integer>();
			temp2.add(trg.get(1));
			temp2.add(trg.get(3));
			temp2.add(trg.get(5));
			Set<Set<Integer>> temp = new HashSet<Set<Integer>>();
			temp.add(temp1);
			temp.add(temp2);
			flag = 0;
			for(ArrayList<Integer> current: triangleHash.keySet())
			{
				if(triangleHash.get(current).equals(temp))
				{
					flag = 1;
					break;
				}
			}
			if(flag == 0)
				triangleHash.put(trg,  temp);
		}
		*/
		Set<ArrayList<Integer>> indexSet = new HashSet<ArrayList<Integer>>();
		
		//delete those triangles which have three vertices in one edge
		for( ArrayList<Integer> eachIndex: set)
		{
			if (((array.get(eachIndex.get(0), eachIndex.get(1))==true)&&(array.get(eachIndex.get(0), eachIndex.get(3))==true)
					&&(array.get(eachIndex.get(0), eachIndex.get(5))==true)) || 
					((array.get(eachIndex.get(2), eachIndex.get(1))==true)&&(array.get(eachIndex.get(2), eachIndex.get(3))==true)
							&&(array.get(eachIndex.get(2), eachIndex.get(5))==true)) ||
							((array.get(eachIndex.get(4), eachIndex.get(1))==true)&&(array.get(eachIndex.get(4), eachIndex.get(3))==true)
									&&(array.get(eachIndex.get(4), eachIndex.get(5))==true)))
			{
				
			}
			else
			{
				indexSet.add(eachIndex);
			}
		}
		return indexSet;
	}
	
	/*
	 * The index of star shape is stored as a set of vertices
	 * the vertex which has three or more edges related to it is in the index
	 * [starCenter, edge£]
	 */
	public Set<ArrayList<Integer>> getHyperStarIndex(BitMatrix array)
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
	 * determine if a hypergraph has a subgraph, ignore label
	 * give each vertex and edge a hash value to match to the hyper graph
	 * if there is a match, this subgraph is in the hypergraph, otherwise, no
	 */
	public Set<HashMap<Integer, Integer>> determineTriangle(BitMatrix array, Set<ArrayList<Integer>> indexT, BitMatrix hyper){
		Set<HashMap<Integer, Integer>> results = new HashSet<HashMap<Integer, Integer>>();
		Set<Set<Integer>> returnIndx = new HashSet<Set<Integer>>();
		int getAll=1;//0 means not all matches is printed, only all the edge positions printed
		Set<Set<Integer>> matches = new HashSet<Set<Integer>>();//edge matches set when getAll=0
		
		int m,n,o, p, q, size;
		int flag = 0;//flag should be 1 if there is a match
		int flg = 0;//test whether an edge is in otherV
		Set<ArrayList<Integer>> large = new HashSet<ArrayList<Integer>>();
		//mySet: the triangles got by subgraph
		Set<ArrayList<Integer>> mySet = new HashSet<ArrayList<Integer>>();

		//Set<Integer> others = new HashSet<Integer>(); 
		//unionSet: all vertices related to triangle edges other than the three in index
		Set<Integer> unionSet = new HashSet<Integer>(); 
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
		ArrayList<Integer> allAuthor = new ArrayList<Integer>(allA);

		
		
		HashMap<Integer, Integer> realVhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realEhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realOtherVhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realOtherEhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realZeroVhash = new HashMap<Integer, Integer>();

		large = indexT;
		mySet = getHyperTriangleIndex(array);
		ArrayList<ArrayList<Integer>> mySetArray = new ArrayList<ArrayList<Integer>>(mySet);
		//for(ArrayList<Integer> ii: mySet)
			//System.out.println(ii);
		//for(ArrayList<Integer> ii: large)
			//System.out.println(ii);
		if(mySet.size() > 0)
		{
			ArrayList<Integer> i = mySetArray.get(0);
			//{
				for(ArrayList<Integer> j: large)
				{
					//TEST IF THIS COMPARISON IS REPEATED
					//Set<Integer> tempSet1 = new HashSet<Integer>(); 
					//tempSet1.add(j.get(1));
					//tempSet1.add(j.get(3));
					//tempSet1.add(j.get(5));
					//if((getAll == 0) && (matches.contains(tempSet1)))
						//continue;
					//otherV: vertices not in Triangle index, but related to this triangle
					//otherV , otherE , and zero V are all related to "array", not hyper
					ArrayList<Integer> otherV = new ArrayList<Integer>();
					ArrayList<Integer> otherE = new ArrayList<Integer>();
					//vertices not related to the triangle: test them to the other edges
					ArrayList<Integer> zeroV = new ArrayList<Integer>();
					
					
					//hash the sub graph's v and e to the large graph's v and e
					HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> otherVhash = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> otherEhash = new HashMap<Integer, Integer>();


					//triangle's edges and vertices hash from subgraph to large graph
					for(m = 0; m < 3; m++)
					{
						vhash.put(i.get(m*2), j.get(m*2));
						ehash.put(i.get(m*2+1), j.get(m*2+1));
						flag = 1;
					}
					
					//11111:pick the vertex not in triangle index(column of a vertex not all 0s)
					for(n = 0; n < array.columns(); n++)
					{
						if(!(vhash.keySet().contains(n)) 
								&& ((array.get(n, i.get(1))==true)||(array.get(n, i.get(3))==true)||(array.get(n, i.get(5))==true)))
						{
							otherV.add(n);
						}
					}
					//22222:pick the edges not in triangle 
					for(n = 0; n < array.rows(); n++)
					{
						if(!(ehash.keySet().contains(n)) )
						{
							otherE.add(n);
						}
					}
					//get all the related nodes of the three edges in triangle
					unionSet = union(paper_au.get(j.get(1)), 
							union(paper_au.get(j.get(5)),paper_au.get(j.get(3))));
					
					if(otherV.size() > unionSet.size())//if there are more related vertices: false
					{
						flag = 0;
						continue;
					}
					else if((otherV.size() == 0) && (otherE.size() > 0))
					{

						//get all the other edges in the whole hypergraph
						Set<Integer> unionSetE = new HashSet<Integer>();
						for(o = 0; o < hyper.rows(); o++)
						{
							if(!ehash.values().contains(o))
							{
								unionSetE.add(o);
							}
						}
						ArrayList<Integer> permuE = new ArrayList<Integer>(unionSetE);

						ArrayList<ArrayList<Integer>> permuResultsE = Permutation(permuE,array.rows()-3);
						for(ArrayList<Integer> currentPermuE: permuResultsE)
						{
							flag = 1;

							for(o = 0 ; o < otherE.size(); o++)
							{
								if((array.get(i.get(0), otherE.get(o))==hyper.get(vhash.get(i.get(0)), currentPermuE.get(o)))
										&& (array.get(i.get(2), otherE.get(o))==hyper.get(vhash.get(i.get(2)), currentPermuE.get(o)))
										&& (array.get(i.get(4), otherE.get(o))==hyper.get(vhash.get(i.get(4)), currentPermuE.get(o)))
										)
										{
											otherEhash.put(otherE.get(o), currentPermuE.get(o));
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
								realOtherEhash = otherEhash;
								break;
							}
						}
					}
					else
					{
						ArrayList<Integer> permu = new ArrayList<Integer>(unionSet);

						ArrayList<ArrayList<Integer>> permuResults = Permutation(permu,otherV.size());
						ArrayList<ArrayList<Integer>> newPermu = new ArrayList<ArrayList<Integer>>();
						
						//remove 
						for(ArrayList<Integer> currentPermu: permuResults)
						{
							//not including three triangle vertices
							if( (!currentPermu.contains(j.get(0)))
									&&(!currentPermu.contains(j.get(2)))
									&&(!currentPermu.contains(j.get(4))) )
							{								
								newPermu.add(currentPermu);
							}
						}
						//Starting test each permutation to the three edges in triangle
						//othersArray.get(i) correspond permuResults[0].get(i)
						for(ArrayList<Integer> currentPermu: newPermu)
						{
							flag = 1;
							//test three triangle edges to other vertices if to triangle vertices are true
							for(o = 0 ; o < otherV.size(); o++)
							{
								//compare subgraph and whole graph
								if((array.get(otherV.get(o), i.get(1))==hyper.get(currentPermu.get(o), ehash.get(i.get(1))))
										&& (array.get(otherV.get(o), i.get(3))==hyper.get(currentPermu.get(o), ehash.get(i.get(3))))
										&& (array.get(otherV.get(o), i.get(5))==hyper.get(currentPermu.get(o), ehash.get(i.get(5))))
										)
								{
									otherVhash.put(otherV.get(o), currentPermu.get(o));
									flag = flag * 1;
								}
								else
								{
									flag = 0;
									break;
								}
							}
							//test non-triangle edges to all non zero vertices
							if((flag == 1) && (otherE.size() > 0))
							{
								//add the hashtable of "other" edges
								//HashMap<Integer, Integer> otherEhash = new HashMap<Integer, Integer>();
								
								ArrayList<ArrayList<Integer>> otherEpermu = Permutation(allPaper,otherE.size());
								ArrayList<ArrayList<Integer>> newOtherEpermu = new ArrayList<ArrayList<Integer>>();
								
								//remove 
								for(ArrayList<Integer> currentEpermu: otherEpermu)
								{
									//not including three triangle edges
									if( (!currentEpermu.contains(j.get(1)))
											&&(!currentEpermu.contains(j.get(3)))
											&&(!currentEpermu.contains(j.get(5))) )
									{								
										newOtherEpermu.add(currentEpermu);
									}
								}
								for(ArrayList<Integer> currentEpermu: newOtherEpermu)
								{
									flag = 1;
									for(p = 0; p< otherE.size(); p++)
									{
										for(q = 0; q < otherV.size(); q++)
										{
											if( (array.get(i.get(0), otherE.get(p))== hyper.get(vhash.get(i.get(0)), currentEpermu.get(p)))
													&&(array.get(i.get(2), otherE.get(p))== hyper.get(vhash.get(i.get(2)), currentEpermu.get(p)))
													&&(array.get(i.get(4), otherE.get(p))== hyper.get(vhash.get(i.get(4)), currentEpermu.get(p)))
													&&(array.get(otherV.get(q), otherE.get(p))== hyper.get(otherVhash.get(otherV.get(q)), currentEpermu.get(p)))
													)
											{
												flag = flag * 1;
											}
											else
											{
												flag = 0;
												break;
											}
										}
										if(flag == 0)
										{
											break;
										}
										else
										{
											otherEhash.put(otherE.get(p), currentEpermu.get(p));
										}
									}
									if(flag == 1)
									{
										realOtherEhash = otherEhash;
										break;
									}
								}

								//DO THE MEMORY RELEASE
								for(size = 0 ; size < otherEpermu.size(); size++)
									otherEpermu.remove(size);
								otherEpermu.trimToSize();
								for(size = 0 ; size < newOtherEpermu.size(); size++)
									newOtherEpermu.remove(size);
								newOtherEpermu.trimToSize();
								
								//test zero edges
								if((flag == 1) && (otherE.size() > 0))
								{
									HashMap<Integer, Integer> zeroVhash = new HashMap<Integer, Integer>();

									for(p = 0; p< array.columns(); p++)
									{
										if( (!vhash.containsKey(p)) && (!otherVhash.containsKey(p)) )
											zeroV.add(p);
									}
									ArrayList<ArrayList<Integer>> zeroVpermu = Permutation(allAuthor,zeroV.size());
									ArrayList<ArrayList<Integer>> newZeroVpermu = new ArrayList<ArrayList<Integer>>();
									//remove triangle vertices and related vertices in the permutation
									for(ArrayList<Integer> currentZpermu: zeroVpermu)
									{
										//not including three triangle vertices and related vertices
										if( (!currentZpermu.contains(j.get(0)))
												&&(!currentZpermu.contains(j.get(2)))
												&&(!currentZpermu.contains(j.get(4))) )
										{							
											flg = 1;
											for(m = 0; m< otherV.size() ; m++)
											{
												if(!currentZpermu.contains(otherVhash.get(otherV.get(m))))
													flg = flg * 1;
												else
												{
													flg = 0;
													break;
												}
									
											}
											if(flg == 1)
												newZeroVpermu.add(currentZpermu);
										}
									}
									for(ArrayList<Integer> currentZpermu: newZeroVpermu)
									{
										flag = 1;
										for(q = 0; q < zeroV.size(); q++)
										{
											for(p = 0; p< otherE.size(); p++)
											{
												if(array.get(zeroV.get(q), otherE.get(p))==hyper.get(currentZpermu.get(q), otherEhash.get(otherE.get(p)))) 
												{
													flag = flag * 1;
												}
												else
												{
													flag = 0;
													break;
												}
											}
											if(flag == 0)
											{
												break;
											}
											else
											{
												zeroVhash.put(zeroV.get(q), currentZpermu.get(q));
											}
										}
										if(flag == 1)
										{
											realZeroVhash = zeroVhash;
											break;
										}
									}
									
								}
								else
								{
									break;
								}
							}
							if(flag == 1)
							{
								break;
							}
						}
						if(flag == 1)
						{
							realOtherVhash = otherVhash;
						}
					}
					if((flag == 1)&& (getAll == 1))
					{
						realEhash = ehash;
						realVhash = vhash;
						results.add(realEhash);
						returnIndx.add(realEhash.keySet());
						System.out.print(returnIndx);
						//System.out.print(realVhash);
						//System.out.print(realOtherEhash);
						//System.out.print(realOtherVhash);
						//System.out.println(realZeroVhash);
						continue;
					}
					else if((flag == 1)&& (getAll == 0))
					{
						Set<Integer> tempSet2 = new HashSet<Integer>(); 
						for(Integer ehashvalue: ehash.values())
							tempSet2.add(ehashvalue);
						matches.add(tempSet2);
					}
				}
				//JUST USE ONE OF THE TRIANGLES IN Q TO MATCH
				//if(flag == 1)
				//{
					//break;
				//}
			//}

		}
		System.out.println(matches+"~~~");
		return results;
	}
	
	/*
	 * This function determins whether there is star shape in the hypergraph
	 */
	public Set<HashMap<Integer, Integer>> determineStar(BitMatrix array, Set<ArrayList<Integer>> indexS, BitMatrix hyper){

		System.out.print("checking star index...\n");
		Set<HashMap<Integer, Integer>> results = new HashSet<HashMap<Integer, Integer>>();
		int unRelatedV = 0;
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
		HashMap<Integer, Integer> realEhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> realZeroVhash = new HashMap<Integer, Integer>();
				
		large = indexS ;
		mySet = getHyperStarIndex(array);

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
										if(array.get(n, m) == hyper.get(vhash.get(n), ehash.get(m)))
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
							ArrayList<Integer> unStarUnionEdge = new ArrayList<Integer>();
							//match unStaredges
							for(j = 0; j < hyper.rows(); j++)
							{
								if(!ehash.values().contains(j))
									unStarUnionEdge.add(j);
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
											if(array.get(i,unStarEdge.get(j)) == hyper.get(vhash.get(i), unStarEdgeHash.get(unStarEdge.get(j))))
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
										
										for(j = 0; j < hyper.columns(); j++)
										{
											if(!vhash.values().contains(j))
											{
												unionUnrelatedSet.add(j);
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
													if(array.get(unStarV.get(i), jj) == hyper.get(unStarVHash.get(unStarV.get(i)), unStarEdgeHash.get(jj)))
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
												//System.out.print(unStarVHash);

												realVhash = vhash;
												realEhash = ehash;
												break;
											}
										}
										if(flg == 1)
											break;
										
									}
								}
								if(flg == 1)
									break;
							}

							//System.out.println(ehash);
							//System.out.println(vhash);
						}
						if(flg == 1)
							break;
					}
					//DO THE MEMORY RELEASE
					for(size = 0 ; size < nodeStarPermu.size(); size++)
						nodeStarPermu.remove(size);
					nodeStarPermu.trimToSize();
					if(flg == 1)
						break;
				}

				//DO THE MEMORY RELEASE
				for(size = 0 ; size < edgeStarPermu.size(); size++)
					edgeStarPermu.remove(size);
				edgeStarPermu.trimToSize();
				if(flg == 1)
					break;
			}
			if(flg == 1)
			{
				results.add(realEhash);
				System.out.println(realEhash);
				//System.out.println(realVhash);
			}
		}
		return results;
	}
}
