import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import cern.colt.bitvector.BitMatrix;

/*
 * OBSOLETE
 * Use LAYER to determine
 * when triangles are got for the index, all the edges and vertices related to this 
 * triangle is given, since there are other edges and vertices not related to this triangle
 */
public class newDetermin {
	/*
	 * get how many layers of a graph have based on a hypertriangle
	 */
	public int getLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper)
	{
		int layer = 0;
		
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1v = new HashSet<Integer>(itsVer(triangle.get(1), hyper));
		layer1v = union(itsVer(triangle.get(3), hyper), layer1v);
		layer1v = union(itsVer(triangle.get(5), hyper), layer1v);
		int i,size1,size2=0;
		
		ArrayList<Integer> layer1V = new ArrayList<Integer>(layer1v);
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
				relatedver = union(itsVer(relatededgesArraynew.get(i), hyper), relatedver);
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
	 * get how many vertices of a certain number of layers based on a hypertriangle
	 */
	public Set<Integer> getVofLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper, int layer)
	{
		int level = 1;
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1v = new HashSet<Integer>(itsVer(triangle.get(1), hyper));
		layer1v = union(itsVer(triangle.get(3), hyper), layer1v);
		layer1v = union(itsVer(triangle.get(5), hyper), layer1v);
		int i;
		
		ArrayList<Integer> layer1V = new ArrayList<Integer>(layer1v);
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1v.size(); i++)
			relatededges.addAll(itsEdges(layer1V.get(i), hyper));
		ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);

		for(i = 0; i < relatededgesArray.size(); i++)
			relatedver.addAll(itsVer(relatededgesArray.get(i), hyper));

		while(level < layer)
		{
			level++;

			ArrayList<Integer> relatededgesArraynew = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArraynew.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArraynew.get(i), hyper), relatedver);
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
		}
		return relatedver;
	}
	
	/*
	 * get how many edges of a certain number of layers based on a hypertriangle
	 */
	public Set<Integer> getEofLayerTriangle(ArrayList<Integer> triangle, BitMatrix hyper, int layer)
	{
		int level = 1;
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		Set<Integer> layer1v = new HashSet<Integer>(itsVer(triangle.get(1), hyper));
		layer1v = union(itsVer(triangle.get(3), hyper), layer1v);
		layer1v = union(itsVer(triangle.get(5), hyper), layer1v);
		int i;
		
		ArrayList<Integer> layer1V = new ArrayList<Integer>(layer1v);
		
		//get all the edges related to the triangle
		for(i = 0; i < layer1v.size(); i++)
			relatededges.addAll(itsEdges(layer1V.get(i), hyper));
		ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);

		for(i = 0; i < relatededgesArray.size(); i++)
			relatedver.addAll(itsVer(relatededgesArray.get(i), hyper));

		while(level < layer)
		{
			level++;

			ArrayList<Integer> relatededgesArraynew = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArraynew.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArraynew.get(i), hyper), relatedver);
			}

			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
		}
		return relatededges;
	}
	/////////////////////////////////////////////////////////////////////////////
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
	 * get an triangle's all related edges
	 */
	public Set<Integer> relatedEdgeT(ArrayList<Integer> triangle, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		ArrayList<Integer> temp1 = new ArrayList<Integer>(itsEdges(triangle.get(0), hyper));
		int i,size1,size2=0;
		for(i = 0; i < temp1.size(); i++)
			relatededges.add(temp1.get(i));
		
		ArrayList<Integer> temp2 = new ArrayList<Integer>(itsEdges(triangle.get(2), hyper));
		for(i = 0; i < temp2.size(); i++)
			relatededges.add(temp2.get(i));
		
		ArrayList<Integer> temp3 = new ArrayList<Integer>(itsEdges(triangle.get(4), hyper));
		for(i = 0; i < temp3.size(); i++)
			relatededges.add(temp3.get(i));
		
		size1 = relatededges.size();
		while(size1 != size2)
		{
			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			size1 = relatededgesArray.size();
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}

			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
			size2 = relatededges.size();
		}
		//System.out.print(relatededges);
		return relatededges;
	}
	/*
	 * get edges' all related vertices
	 */
	public Set<Integer> relatedVerT(ArrayList<Integer> triangle, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		ArrayList<Integer> temp1 = new ArrayList<Integer>(itsVer(triangle.get(1), hyper));
		int i,size1,size2=0;
		for(i = 0; i < temp1.size(); i++)
			relatedver.add(temp1.get(i));
		
		ArrayList<Integer> temp2 = new ArrayList<Integer>(itsVer(triangle.get(3), hyper));
		for(i = 0; i < temp2.size(); i++)
			relatedver.add(temp2.get(i));
		
		ArrayList<Integer> temp3 = new ArrayList<Integer>(itsVer(triangle.get(5), hyper));
		for(i = 0; i < temp3.size(); i++)
			relatedver.add(temp3.get(i));
		
		size1 = relatedver.size();
		while(size1 != size2)
		{
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			size1 = relatedverArray.size();
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}

			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
			size2 = relatedver.size();
		}
		//System.out.print(relatedver);
		return relatedver;
	}
	
	/*
	 * get an star's all related edges
	 */
	public Set<Integer> relatedEdgeS(ArrayList<Integer> star, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		ArrayList<Integer> temp1 = new ArrayList<Integer>(itsEdges(star.get(0), hyper));
		int i,size1,size2=0;
		for(i = 0; i < temp1.size(); i++)
			relatededges.add(temp1.get(i));
 
		
		size1 = relatededges.size();
		while(size1 != size2)
		{
			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			size1 = relatededgesArray.size();
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}

			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
			size2 = relatededges.size();
		}
		//System.out.print(relatededges);
		return relatededges;
	}
	/*
	 * get star edges' all related vertices
	 */
	public Set<Integer> relatedVerS(ArrayList<Integer> star, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		
		ArrayList<Integer> temp1 = new ArrayList<Integer>(itsEdges(star.get(0), hyper));
		int i,size1,size2=0;
		for(i = 0; i < temp1.size(); i++)
			relatededges.add(temp1.get(i));
 
		ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);

		for(i = 0 ; i < relatededgesArray.size(); i++)
		{
			relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
		}	
		size1 = relatedver.size();
		
		while(size1 != size2)
		{
			size1 = relatedver.size();
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);

			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = union(itsEdges(relatedverArray.get(i), hyper), relatededges);
			}

			relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = union(itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
			size2 = relatedver.size();
		}
		//System.out.print(relatedver);
		return relatedver;
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
	 * <v1, Ep, v2, Eq, v3, Er, v1, n1, n2, n3>, all of them are integers
	 * 3 numbers at the end of each triangle: three edges' number of vertices in sorted order
	 */
	public Set<ArrayList<Integer>> getHyperTriangleIndex(BitMatrix array)
	{
		Set<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
		int i, j, k, p, q, r;//i,k,q are for edge, j,p,r are for vertex

		int v1, e1, v2, e2, v3, e3;
		HashMap<Integer, Set<Integer>> paper_au = new HashMap<Integer, Set<Integer>>();
		paper_au = array2hash(array);
		
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
													//********Add three numbers at the end of each triangle: three edges' number of vertices in sorted order
													if((paper_au.get(e1).size() <= paper_au.get(e2).size()) && (paper_au.get(e1).size() <= paper_au.get(e3).size()))
													{
														tempArray.add(6, paper_au.get(e1).size());
														if(paper_au.get(e2).size() <= paper_au.get(e3).size())
														{
															tempArray.add(7, paper_au.get(e2).size());
															tempArray.add(8, paper_au.get(e3).size());
														}
														else
														{
															tempArray.add(7, paper_au.get(e3).size());
															tempArray.add(8, paper_au.get(e2).size());
														}						
													}
													else if((paper_au.get(e2).size() <= paper_au.get(e1).size()) && (paper_au.get(e2).size() <= paper_au.get(e3).size()))
													{
														tempArray.add(6, paper_au.get(e2).size());
														if(paper_au.get(e1).size() <= paper_au.get(e3).size())
														{
															tempArray.add(7, paper_au.get(e1).size());
															tempArray.add(8, paper_au.get(e3).size());
														}
														else
														{
															tempArray.add(7, paper_au.get(e3).size());
															tempArray.add(8, paper_au.get(e1).size());
														}						
													}
													else
													{
														tempArray.add(6, paper_au.get(e3).size());
														if(paper_au.get(e1).size() <= paper_au.get(e2).size())
														{
															tempArray.add(7, paper_au.get(e1).size());
															tempArray.add(8, paper_au.get(e2).size());
														}
														else
														{
															tempArray.add(7, paper_au.get(e2).size());
															tempArray.add(8, paper_au.get(e1).size());
														}						
													}
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
		return removeRepeatTriangle(indexSet);
	}
	
	/*
	 * The index of star shape is stored as a set of vertices
	 * the vertex which has three or more edges related to it is in the index
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
	 * removeRepeatTriangle() removes the repeating triangles
	 */
	public Set<ArrayList<Integer>> removeRepeatTriangle(Set<ArrayList<Integer>> list)
	{
		Set<ArrayList<Integer>> removed = new HashSet<ArrayList<Integer>>();
		//triangleList has all the triangles as a set for each one's edges, if another triangle has the same edge, remove it from list
		ArrayList<Set<Integer>> edgeTriangle = new ArrayList<Set<Integer>>();
		//convert the list set to array
		ArrayList<ArrayList<Integer>> listArray = new ArrayList<ArrayList<Integer>>(list);
		for(int i = 0 ; i < list.size(); i++)
		{
			Set<Integer> tmp = new HashSet<Integer>();
			tmp.add(listArray.get(i).get(1));
			tmp.add(listArray.get(i).get(3));
			tmp.add(listArray.get(i).get(5));
			if(!edgeTriangle.contains(tmp))
			{
				edgeTriangle.add(tmp);
				removed.add(listArray.get(i));
			}
		}
		return removed;
	}
	/*
	 * determine if a hypergraph has a subgraph, ignore label
	 * give each vertex and edge a hash value to match to the hyper graph
	 * if there is a match, this subgraph is in the hypergraph, otherwise, no
	 */
	public Set<Set<Object>> determineTriangle(BitMatrix array, Set<ArrayList<Integer>> indexT, BitMatrix hyper){
		Set<HashMap<Integer, Integer>> results = new HashSet<HashMap<Integer, Integer>>();
		int getAll=1;//0 means not all matches is printed, only all the edge positions printed
		Set<Set<Integer>> matches = new HashSet<Set<Integer>>();//edge matches set when getAll=0
		Set<Set<Object>> returnIndx = new HashSet<Set<Object>>();
		
		int m,n,o, p, q, size;
		int flag = 0;//flag should be 1 if there is a match
		int flg = 0;//test whether an edge is in otherV
		Set<ArrayList<Integer>> large = new HashSet<ArrayList<Integer>>();
		Set<ArrayList<Integer>> largeReal = new HashSet<ArrayList<Integer>>();
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

		int r1,r2,r3,s1,s2,s3;
		large = indexT;
		int layerQ;
		mySet = removeRepeatTriangle(getHyperTriangleIndex(array));
		ArrayList<ArrayList<Integer>> mySetArray = new ArrayList<ArrayList<Integer>>(mySet);
  
		if(mySet.size() > 0)
		{

			ArrayList<Integer> i = mySetArray.get(0);
			//for(ArrayList<Integer> i : mySetArray)
			{
			//GET THE LAYER NUMBER OF THE QUERY  
			layerQ = getLayerStar(i, array);
			for(ArrayList<Integer> j : large)
			{				
				//compare three edges's vertices
				if((i.get(6)<=j.get(6)) && (i.get(7)<=j.get(7)) && (i.get(8)<=j.get(8)))
				{
					largeReal.add(j);
				}
			}

			//use optimized index to do the matching
			for(ArrayList<Integer> j: largeReal)
			{

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

					//get all the other edges 
					Set<Integer> unionSetE = new HashSet<Integer>();
					Set<Integer> relatedEdges = getEofLayerTriangle(j, hyper, layerQ);
					for(Integer re:relatedEdges) 
					{
						if(!ehash.values().contains(re))
						{
							unionSetE.add(re);
						}
					}
					ArrayList<Integer> permuE = new ArrayList<Integer>(unionSetE);

					ArrayList<ArrayList<Integer>> permuResultsE = Permutation(permuE,array.rows()-3);
					for(ArrayList<Integer> currentPermuE: permuResultsE)
					{
						flag = 1;

						for(o = 0 ; o < otherE.size(); o++)
						{
							if((array.get(i.get(0), otherE.get(o))==hyper.get(vhash.get(i.get(0)), currentPermuE.get(o))
									|| (array.get(i.get(0), otherE.get(o))== false && hyper.get(vhash.get(i.get(0)), currentPermuE.get(o)) ==true))
									&& (array.get(i.get(2), otherE.get(o))==hyper.get(vhash.get(i.get(2)), currentPermuE.get(o))
									|| (array.get(i.get(2), otherE.get(o))== false && hyper.get(vhash.get(i.get(2)), currentPermuE.get(o)) ==true))
									&& (array.get(i.get(4), otherE.get(o))==hyper.get(vhash.get(i.get(4)), currentPermuE.get(o))
									|| ((array.get(i.get(4), otherE.get(o))== false && hyper.get(vhash.get(i.get(4)), currentPermuE.get(o)) ==true)))
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
						/*/test non-triangle edges to all non zero vertices
						if((flag == 1) && (otherE.size() > 0))
						{
							//add the hashtable of "other" edges
							//HashMap<Integer, Integer> otherEhash = new HashMap<Integer, Integer>();
							Set<Integer> unionSetE1 = new HashSet<Integer>();
							Set<Integer> relatedEdges1 = getEofLayerTriangle(j, hyper, layerQ);
							for(Integer re:relatedEdges1) 
							{
								if(!ehash.values().contains(re))
								{
									unionSetE1.add(re);
								}
							}
							ArrayList<Integer> permuE1 = new ArrayList<Integer>(unionSetE1);

							ArrayList<ArrayList<Integer>> otherEpermu1 = Permutation(permuE1,otherE.size());
							ArrayList<ArrayList<Integer>> newOtherEpermu1 = new ArrayList<ArrayList<Integer>>();
							
							//remove 
							for(ArrayList<Integer> currentEpermu1: otherEpermu1)
							{
								//not including three triangle edges
								if( (!currentEpermu1.contains(j.get(1)))
										&&(!currentEpermu1.contains(j.get(3)))
										&&(!currentEpermu1.contains(j.get(5))) )
								{								
									newOtherEpermu1.add(currentEpermu1);
								}
							}
							for(ArrayList<Integer> currentEpermu1: newOtherEpermu1)
							{
								flag = 1;
								for(p = 0; p< otherE.size(); p++)
								{
									for(q = 0; q < otherV.size(); q++)
									{
										if( (array.get(i.get(0), otherE.get(p))== hyper.get(vhash.get(i.get(0)), currentEpermu1.get(p))
												|| (array.get(i.get(0), otherE.get(p))== false && hyper.get(vhash.get(i.get(0)), currentEpermu1.get(p)) == true ))
												&&(array.get(i.get(2), otherE.get(p))== hyper.get(vhash.get(i.get(2)), currentEpermu1.get(p))
												|| (array.get(i.get(2), otherE.get(p))== false && hyper.get(vhash.get(i.get(2)), currentEpermu1.get(p)) == true ))
												&&(array.get(i.get(4), otherE.get(p))== hyper.get(vhash.get(i.get(4)), currentEpermu1.get(p))
												|| (array.get(i.get(4), otherE.get(p))== false && hyper.get(vhash.get(i.get(4)), currentEpermu1.get(p)) == true ))
												&&(array.get(otherV.get(q), otherE.get(p))== hyper.get(otherVhash.get(otherV.get(q)), currentEpermu1.get(p))
												|| (array.get(otherV.get(q), otherE.get(p))== false && hyper.get(otherVhash.get(otherV.get(q)), currentEpermu1.get(p)) == true ))
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
										otherEhash.put(otherE.get(p), currentEpermu1.get(p));
									}
								}
								if(flag == 1)
								{
									realOtherEhash = otherEhash;
									break;
								}
							}

							//DO THE MEMORY RELEASE
							for(size = 0 ; size < otherEpermu1.size(); size++)
								otherEpermu1.remove(size);
							otherEpermu1.trimToSize();
							for(size = 0 ; size < newOtherEpermu1.size(); size++)
								newOtherEpermu1.remove(size);
							newOtherEpermu1.trimToSize();
							
							//test zero V
							if((flag == 1) && (otherE.size() > 0))
							{
								HashMap<Integer, Integer> zeroVhash = new HashMap<Integer, Integer>();

								for(p = 0; p< array.columns(); p++)
								{
									if( (!vhash.containsKey(p)) && (!otherVhash.containsKey(p)) )
										zeroV.add(p);
								}
								Set<Integer> unionSetZeroV = new HashSet<Integer>();
								Set<Integer> relatedV = getVofLayerTriangle(j, hyper, layerQ);
								for(Integer re:relatedV) 
								{
									if(!vhash.values().contains(re))
									{
										unionSetZeroV.add(re);
									}
								}
								ArrayList<Integer> permuZeroV = new ArrayList<Integer>(unionSetZeroV);
								ArrayList<ArrayList<Integer>> zeroVpermu = Permutation(permuZeroV,zeroV.size());
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
											if((array.get(zeroV.get(q), otherE.get(p))==hyper.get(currentZpermu.get(q), otherEhash.get(otherE.get(p)))==true) ||((array.get(zeroV.get(q), otherE.get(p))==false) && (hyper.get(currentZpermu.get(q), otherEhash.get(otherE.get(p)))==true))) 
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
						}*/
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
							if((array.get(otherV.get(o), i.get(1))==hyper.get(currentPermu.get(o), ehash.get(i.get(1)))
									|| (array.get(otherV.get(o), i.get(1))== false && hyper.get(currentPermu.get(o), ehash.get(i.get(1))) == true))
									&& (array.get(otherV.get(o), i.get(3))==hyper.get(currentPermu.get(o), ehash.get(i.get(3)))
									|| (array.get(otherV.get(o), i.get(3))== false && hyper.get(currentPermu.get(o), ehash.get(i.get(3))) == true))
									&& (array.get(otherV.get(o), i.get(5))==hyper.get(currentPermu.get(o), ehash.get(i.get(5)))
									|| (array.get(otherV.get(o), i.get(5))== false && hyper.get(currentPermu.get(o), ehash.get(i.get(5))) == true))
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
							Set<Integer> unionSetE = new HashSet<Integer>();
							Set<Integer> relatedEdges = getEofLayerTriangle(j, hyper, layerQ);
							for(Integer re:relatedEdges) 
							{
								if(!ehash.values().contains(re))
								{
									unionSetE.add(re);
								}
							}
							ArrayList<Integer> permuE = new ArrayList<Integer>(unionSetE);

							ArrayList<ArrayList<Integer>> otherEpermu = Permutation(permuE,otherE.size());
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
										if( (array.get(i.get(0), otherE.get(p))== hyper.get(vhash.get(i.get(0)), currentEpermu.get(p))
												|| (array.get(i.get(0), otherE.get(p))== false && hyper.get(vhash.get(i.get(0)), currentEpermu.get(p)) == true ))
												&&(array.get(i.get(2), otherE.get(p))== hyper.get(vhash.get(i.get(2)), currentEpermu.get(p))
												|| (array.get(i.get(2), otherE.get(p))== false && hyper.get(vhash.get(i.get(2)), currentEpermu.get(p)) == true ))
												&&(array.get(i.get(4), otherE.get(p))== hyper.get(vhash.get(i.get(4)), currentEpermu.get(p))
												|| (array.get(i.get(4), otherE.get(p))== false && hyper.get(vhash.get(i.get(4)), currentEpermu.get(p)) == true ))
												&&(array.get(otherV.get(q), otherE.get(p))== hyper.get(otherVhash.get(otherV.get(q)), currentEpermu.get(p))
												|| (array.get(otherV.get(q), otherE.get(p))== false && hyper.get(otherVhash.get(otherV.get(q)), currentEpermu.get(p)) == true ))
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
							
							//test zero V
							if((flag == 1) && (otherE.size() > 0))
							{
								HashMap<Integer, Integer> zeroVhash = new HashMap<Integer, Integer>();

								for(p = 0; p< array.columns(); p++)
								{
									if( (!vhash.containsKey(p)) && (!otherVhash.containsKey(p)) )
										zeroV.add(p);
								}
								Set<Integer> unionSetZeroV = new HashSet<Integer>();
								Set<Integer> relatedV = getVofLayerTriangle(j, hyper, layerQ);
								for(Integer re:relatedV) 
								{
									if(!vhash.values().contains(re))
									{
										unionSetZeroV.add(re);
									}
								}
								ArrayList<Integer> permuZeroV = new ArrayList<Integer>(unionSetZeroV);
								ArrayList<ArrayList<Integer>> zeroVpermu = Permutation(permuZeroV,zeroV.size());
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
											if((array.get(zeroV.get(q), otherE.get(p))==hyper.get(currentZpermu.get(q), otherEhash.get(otherE.get(p)))==true) ||((array.get(zeroV.get(q), otherE.get(p))==false) && (hyper.get(currentZpermu.get(q), otherEhash.get(otherE.get(p)))==true))) 
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
					//System.out.println(realEhash);
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
			}
			//JUST USE ONE OF THE TRIANGLES IN Q TO MATCH
			//if(flag == 1)
			//{
				//break;
			//}
		//}

		}
		if(getAll == 1)
		{
		System.out.println("\nnewDetermin.java(determineTrianglegetbyT())"+results);
		}
		for(HashMap s: results)
		{
			Set<Object> temp = new HashSet<Object>();
			for(Object in: s.values())
			{
				temp.add(in);
			}
			returnIndx.add(temp);
		}
		return returnIndx;
	}
	
	/*
	 * This function determins whether there is star shape in the hypergraph
	 * return match is all the vertices that can match the center
	 */
	public Set<Integer> determineStar(BitMatrix array, Set<ArrayList<Integer>> indexS, BitMatrix hyper){

		System.out.print("\n(newDetermin.java-determinStar())checking star index...\n");
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
		mySet = getHyperStarIndex(array);

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
											/*
											for(m = 0; m < array.rows(); m++)
											{
												if(!unStarEdge.contains(m))
												{
													for(n = 0 ; n < array.columns(); n++)
													{
														if(unStarV.contains(n))
														{
															if((array.get(n, m) == hyper.get(unStarVHash.get(n), ehash.get(m))) || ((array.get(n, m) == false)&&( hyper.get(unStarVHash.get(n), ehash.get(m)) == true)))
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
											}*/
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
	public static int row = 10, column = 10, truthNum = 100, times = 100;
	public static void main(String[] args) {
		newDetermin n = new newDetermin();
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(1);
		a.add(5);
		a.add(3);
		System.out.print(n.Permutation(a, 2));
		//BitMatrix hyper = new BitMatrix(column,row); 
		//genQuery g = new genQuery();
		//hyper = g.genHyper(row, column,truthNum);
		//n.relatedEdgeS(a, hyper);
		//n.relatedVerS(a, hyper);
	}
}
