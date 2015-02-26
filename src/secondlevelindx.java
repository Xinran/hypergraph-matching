import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;
import tutorial.setOp;

///OBSOLETE
public class secondlevelindx {
	/*
	 * get a second level index's all related edges
	 */
	public Set<Integer> relatedEdge2nd(ArrayList<Integer> indxEle, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		newDetermin n = new newDetermin();
		
		int i,size1,size2=0;
		if(indxEle.get(1) == 3)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsEdges(indxEle.get(3), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatededges.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsEdges(indxEle.get(4), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatededges.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsEdges(indxEle.get(5), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatededges.add(temp3.get(i));
		}
		else if(indxEle.get(1) == 2)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsEdges(indxEle.get(3), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatededges.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsEdges(indxEle.get(4), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatededges.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsEdges(indxEle.get(9), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatededges.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsEdges(indxEle.get(10), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatededges.add(temp4.get(i));
		}
		else if(indxEle.get(1) == 1)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsEdges(indxEle.get(3), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatededges.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsEdges(indxEle.get(8), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatededges.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsEdges(indxEle.get(9), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatededges.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsEdges(indxEle.get(10), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatededges.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsEdges(indxEle.get(11), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatededges.add(temp5.get(i));
		}
		else if(indxEle.get(1) == 0)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsEdges(indxEle.get(7), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatededges.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsEdges(indxEle.get(8), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatededges.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsEdges(indxEle.get(9), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatededges.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsEdges(indxEle.get(10), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatededges.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsEdges(indxEle.get(11), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatededges.add(temp5.get(i));	
			
			ArrayList<Integer> temp6 = new ArrayList<Integer>(n.itsEdges(indxEle.get(12), hyper));
			for(i = 0; i < temp6.size(); i++)
				relatededges.add(temp6.get(i));
		}
		size1 = relatededges.size();
		while(size1 != size2)
		{
			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			size1 = relatededgesArray.size();
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = n.union(n.itsVer(relatededgesArray.get(i), hyper), relatedver);
			}

			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
			size2 = relatededges.size();
		}
		//System.out.print(relatededges);
		return relatededges;
	}
	
	/*
	 * get a second level index's all related vertices
	 */
	public Set<Integer> relatedVer2nd(ArrayList<Integer> indxEle, BitMatrix hyper)
	{
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		newDetermin n = new newDetermin();
		
		int i,size1,size2=0;
		if(indxEle.get(1) == 3)
		{
			//edges all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsVer(indxEle.get(2), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatedver.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsVer(indxEle.get(6), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatedver.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsVer(indxEle.get(7), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatedver.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsVer(indxEle.get(8), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatedver.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsVer(indxEle.get(9), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatedver.add(temp5.get(i));
		}
		else if(indxEle.get(1) == 2)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsVer(indxEle.get(2), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatedver.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsVer(indxEle.get(5), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatedver.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsVer(indxEle.get(6), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatedver.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsVer(indxEle.get(7), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatedver.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsVer(indxEle.get(8), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatedver.add(temp5.get(i));
		}
		else if(indxEle.get(1) == 1)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsVer(indxEle.get(2), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatedver.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsVer(indxEle.get(4), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatedver.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsVer(indxEle.get(5), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatedver.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsVer(indxEle.get(6), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatedver.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsVer(indxEle.get(7), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatedver.add(temp5.get(i));
		}
		else if(indxEle.get(1) == 0)
		{
			//first vertex's all related edges 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(n.itsVer(indxEle.get(2), hyper));
			for(i = 0; i < temp1.size(); i++)
				relatedver.add(temp1.get(i));
		
			ArrayList<Integer> temp2 = new ArrayList<Integer>(n.itsVer(indxEle.get(3), hyper));
			for(i = 0; i < temp2.size(); i++)
				relatedver.add(temp2.get(i));
			
			ArrayList<Integer> temp3 = new ArrayList<Integer>(n.itsVer(indxEle.get(4), hyper));
			for(i = 0; i < temp3.size(); i++)
				relatedver.add(temp3.get(i));
			
			ArrayList<Integer> temp4 = new ArrayList<Integer>(n.itsVer(indxEle.get(5), hyper));
			for(i = 0; i < temp4.size(); i++)
				relatedver.add(temp4.get(i));
			
			ArrayList<Integer> temp5 = new ArrayList<Integer>(n.itsVer(indxEle.get(6), hyper));
			for(i = 0; i < temp5.size(); i++)
				relatedver.add(temp5.get(i));
		}
		size1 = relatedver.size();
		while(size1 != size2)
		{
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			size1 = relatedverArray.size();
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), hyper), relatededges);
			}

			ArrayList<Integer> relatededgesArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgesArray.size(); i++)
			{
				relatedver = n.union(n.itsVer(relatededgesArray.get(i), hyper), relatedver);
			}
			size2 = relatedver.size();
		}
		//System.out.print(relatededges);
		return relatedver;
	}
	/*
	 * create a hashtable based on an array, columns are keys, row= 1, add it to value
	 */
	public 	HashMap<Integer, Set<Integer>> array2hashC(BitMatrix hyper)
	{
		HashMap<Integer, Set<Integer>> hash = new HashMap<Integer, Set<Integer>>();
		int m,n;
		for(m = 0; m < hyper.columns(); m++)
		{
			Set<Integer> s = new HashSet<Integer>();
			for(n = 0; n< hyper.rows(); n++)
			{
				if(hyper.get(m, n)==true)
				{
					s.add(n);
				}
			}
			hash.put(m, s);
		}
		return hash;
	}
	/*
	 * ONLY CONSIDER SHARED ONE EDGE
	 * there are only three cases for sharing one edge: 
	 * one edge and 3 vertices: structure has 10 elements[1, 3, 135, 97, 313, 122, 15, 177, 134, 178]
	 * one edge and 2 vertices: structure has 11 elements
	 * one edge and 1 vertices: structure has 12 elements
	 * one edge and 0 vertices: structure has 13 elements
	 * The return list should be :
	 * [1, #sharedVertices, sharedEdge1, (sharedEdge2/3), sharedVertex1, sharedVetex2,(sharedVertex), otherEdge, otherVertex ]
	 */
	public ArrayList<ArrayList<Integer>> get2ndLevelIndx(Set<ArrayList<Integer>> list)
	{
		ArrayList<ArrayList<Integer>> fouredge = new ArrayList<ArrayList<Integer>>();
		//fouredgeTemp is a list of all pattern's edge, in order to remove duplicate
		ArrayList<Set<Integer>> fouredgeTemp = new ArrayList<Set<Integer>>();
		ArrayList<ArrayList<Integer>> triangle = new ArrayList<ArrayList<Integer>>(list);
		
		setOp op = new setOp();
		
		int i, j, k;
		int size = triangle.size();
		for(i = 0 ; i < size; i ++)
		{

			Set<Integer> edges1 = new HashSet<Integer>();//get three edges in a triangle
			Set<Integer> vertices1 = new HashSet<Integer>(); //get three vertices in a traingle
			
			edges1.add(triangle.get(i).get(1));
			edges1.add(triangle.get(i).get(3));
			edges1.add(triangle.get(i).get(5));
			vertices1.add(triangle.get(i).get(0));
			vertices1.add(triangle.get(i).get(2));
			vertices1.add(triangle.get(i).get(4));
			for(j = i+1; j < size; j++)
			{
				Set<Integer> tempSetE = new HashSet<Integer>();
				Set<Integer> tempSetV = new HashSet<Integer>();
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				
				//listOfedge is used to store each pattern's edges, in order to remove repeated patterns
				Set<Integer> listOfedge = new HashSet<Integer>();

				Set<Integer> edges2 = new HashSet<Integer>();//get three edges in a triangle
				Set<Integer> vertices2 = new HashSet<Integer>(); //get three vertices in a traingle

				edges2.add(triangle.get(j).get(1));
				edges2.add(triangle.get(j).get(3));
				edges2.add(triangle.get(j).get(5));
				vertices2.add(triangle.get(j).get(0));
				vertices2.add(triangle.get(j).get(2));
				vertices2.add(triangle.get(j).get(4));
				tempSetE = op.intersection(edges1, edges2);//shared edges
				//find sharing edge pattern
				if(tempSetE.size()==1)
				{
					//System.out.print(triangle.get(i));
					//System.out.println(triangle.get(j));
					
					//***ADD SHARED EDGE NUMBER
					tempList.add(1);
					tempSetV = op.intersection(vertices1, vertices2);//shared vertices
					//***ADD SHARED VERTICES NUMBER
					tempList.add(tempSetV.size());
					for(Integer s: tempSetE)
					{
						//***ADD SHARED EDGE
						tempList.add(s);
						listOfedge.add(s);
					}
 
					for(Integer s: tempSetV)
					{
						//***ADD SHARED VERTICES
						tempList.add(s);
					}
					//add extra edges
					if(!tempSetE.contains(triangle.get(i).get(1)))
					{
						tempList.add(triangle.get(i).get(1));
						listOfedge.add(triangle.get(i).get(1));
					}
					if(!tempSetE.contains(triangle.get(i).get(3)))
					{
						tempList.add(triangle.get(i).get(3));
						listOfedge.add(triangle.get(i).get(3));
					}
					if(!tempSetE.contains(triangle.get(i).get(5)))
					{
						tempList.add(triangle.get(i).get(5));
						listOfedge.add(triangle.get(i).get(5));
					}
					if(!tempSetE.contains(triangle.get(j).get(1)))
					{
						tempList.add(triangle.get(j).get(1));
						listOfedge.add(triangle.get(j).get(1));
					}
					if(!tempSetE.contains(triangle.get(j).get(3)))
					{
						tempList.add(triangle.get(j).get(3));
						listOfedge.add(triangle.get(j).get(3));
					}
					if(!tempSetE.contains(triangle.get(j).get(5)))
					{
						tempList.add(triangle.get(j).get(5));
						listOfedge.add(triangle.get(j).get(5));
					}
					//add extra vertices
					if(!tempSetV.contains(triangle.get(i).get(0)))
					{
						tempList.add(triangle.get(i).get(0));
					}
					if(!tempSetV.contains(triangle.get(i).get(2)))
					{
						tempList.add(triangle.get(i).get(2));
					}
					if(!tempSetV.contains(triangle.get(i).get(4)))
					{
						tempList.add(triangle.get(i).get(4));
					}
					if(!tempSetV.contains(triangle.get(j).get(0)))
					{
						tempList.add(triangle.get(j).get(0));
					}
					if(!tempSetV.contains(triangle.get(j).get(2)))
					{
						tempList.add(triangle.get(j).get(2));
					}
					if(!tempSetV.contains(triangle.get(j).get(4)))
					{
						tempList.add(triangle.get(j).get(4));
					}
					
					
					if(!fouredgeTemp.contains(listOfedge))
					{
						fouredgeTemp.add(listOfedge);
						fouredge.add(tempList);
					}
				}
			}
			
		}
		//System.out.print(fouredge);

		return fouredge;
	}
	/*
	 * parameters: query array, query's 2ndlevel shape, hyper's 2nd level shape, hypergraph
	 */
	public Set<Integer> determinSecond(BitMatrix array, ArrayList<ArrayList<Integer>> index2Q, ArrayList<ArrayList<Integer>> index2, BitMatrix hyper)	
	{
		int i,j, flag;
    	Set<ArrayList<Integer>> match = new HashSet<ArrayList<Integer>>();
    	
    	if((index2Q.size() > 0) && (index2.size() > index2Q.size()))
    	{
			for(ArrayList<Integer> eachSecond: index2)
    		{
        		for(ArrayList<Integer> eachTest: index2Q)
    			{
        			//case1: share one edge and three vertices
    				if((eachTest.get(0)==1) && (eachTest.get(1)==3)
    						&&(eachTest.get(0) == eachSecond.get(0)) 
    						&& (eachTest.get(1) == eachSecond.get(1)))
    				{
    					//hash the sub graph's v and e to the large graph's v and e
    					HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
    					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
    					
    					ehash.put(eachTest.get(2), eachSecond.get(2));
    					ehash.put(eachTest.get(6), eachSecond.get(6));
    					ehash.put(eachTest.get(7), eachSecond.get(7));
    					ehash.put(eachTest.get(8), eachSecond.get(8));
    					ehash.put(eachTest.get(9), eachSecond.get(9));
    					vhash.put(eachTest.get(3), eachSecond.get(3));
    					vhash.put(eachTest.get(4), eachSecond.get(4));
    					vhash.put(eachTest.get(5), eachSecond.get(5));
    					//pick up extra vertices
    					if(array.columns() > 3)
    					{
    						ArrayList<Integer> unPatternV = new ArrayList<Integer>();
    						Set<Integer> relatedver = relatedVer2nd(eachTest, hyper);
    						Set<Integer> unionSetV = new HashSet<Integer>();
 
    						for(Integer re:relatedver) 
    						{
    							if(!vhash.values().contains(re))
    							{
    								unionSetV.add(re);
    							}
    						} 
    						ArrayList<Integer> permu = new ArrayList<Integer>(unionSetV); 
    						ArrayList<ArrayList<Integer>> permuResults = determin.Permutation(permu,array.columns()-3);
    						//find the vertices not in pattern
    						for(i = 0; i < array.columns(); i++)
    						{
    							if((i!= eachTest.get(3)) && (i!= eachTest.get(4)) &&(i!= eachTest.get(5)))
    							{
    								unPatternV.add(i);
    							}
    						}
    						//since the triangle is found, only one group of vertices matches cause a return
    						for(ArrayList<Integer> currentVPermu: permuResults)
    						{
    							flag = 1;
    							HashMap<Integer, Integer> otherVhash = new HashMap<Integer, Integer>();
    							for(i = 0 ; i < array.rows(); i++)
    							{
    								for(j = 0; j < unPatternV.size(); j++)
    								{
    									if(array.get(unPatternV.get(j), i)== hyper.get(currentVPermu.get(j), i))
    									{
    										flag = flag * 1;
        									otherVhash.put(unPatternV.get(j), currentVPermu.get(j));
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
    							}
    							if(flag == 1)
    							{
    								System.out.println(vhash);
    		    					match.add(eachSecond);
    								break;
    							}
    						}

    					}
    					else
    					{
	    					match.add(eachSecond);
    					}
    				}
       			//case2: share one edge and two vertices
    				else if((eachTest.get(0)==1) && (eachTest.get(1)==2)
    						&&(eachTest.get(0) == eachSecond.get(0)) 
    						&& (eachTest.get(1) == eachSecond.get(1)))
    				{
    					//hash the sub graph's v and e to the large graph's v and e
    					HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
    					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
    					
    					ehash.put(eachTest.get(2), eachSecond.get(2));
    					ehash.put(eachTest.get(9), eachSecond.get(5));
    					ehash.put(eachTest.get(6), eachSecond.get(6));
    					ehash.put(eachTest.get(7), eachSecond.get(7));
    					ehash.put(eachTest.get(8), eachSecond.get(8));
    					vhash.put(eachTest.get(3), eachSecond.get(3));
    					vhash.put(eachTest.get(4), eachSecond.get(4));
    					vhash.put(eachTest.get(5), eachSecond.get(9));
    					vhash.put(eachTest.get(5), eachSecond.get(10));
    					//pick up extra vertices
    					if(array.columns() > 4)
    					{
    						ArrayList<Integer> unPatternV = new ArrayList<Integer>();
    						Set<Integer> relatedver = relatedVer2nd(eachTest, hyper);
    						Set<Integer> unionSetV = new HashSet<Integer>();
 
    						for(Integer re:relatedver) 
    						{
    							if(!vhash.values().contains(re))
    							{
    								unionSetV.add(re);
    							}
    						} 
    						ArrayList<Integer> permu = new ArrayList<Integer>(unionSetV); 
    						ArrayList<ArrayList<Integer>> permuResults = determin.Permutation(permu,array.columns()-3);
    						//find the vertices not in pattern
    						for(i = 0; i < array.columns(); i++)
    						{
    							if((i!= eachTest.get(3)) && (i!= eachTest.get(4)) &&(i!= eachTest.get(5)))
    							{
    								unPatternV.add(i);
    							}
    						}
    						//since the triangle is found, only one group of vertices matches cause a return
    						for(ArrayList<Integer> currentVPermu: permuResults)
    						{
    							flag = 1;
    							HashMap<Integer, Integer> otherVhash = new HashMap<Integer, Integer>();
    							for(i = 0 ; i < array.rows(); i++)
    							{
    								for(j = 0; j < unPatternV.size(); j++)
    								{
    									if(array.get(unPatternV.get(j), i)== hyper.get(currentVPermu.get(j), i))
    									{
    										flag = flag * 1;
        									otherVhash.put(unPatternV.get(j), currentVPermu.get(j));
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
    							}
    							if(flag == 1)
    							{
    								System.out.print(vhash);
    		    					match.add(eachSecond);
    								break;
    							}
    						}

    					}
    					else
    					{
	    					match.add(eachSecond);
    					}
    				}
    				//case3: share one edge and 1 vertices
    				else if((eachTest.get(0)==1) && (eachTest.get(1)==1)
    						&&(eachTest.get(0) == eachSecond.get(0)) 
    						&& (eachTest.get(1) == eachSecond.get(1)))
    				{
    					//hash the sub graph's v and e to the large graph's v and e
    					HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
    					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
    					
    					ehash.put(eachTest.get(2), eachSecond.get(2));
    					ehash.put(eachTest.get(8), eachSecond.get(4));
    					ehash.put(eachTest.get(9), eachSecond.get(5));
    					ehash.put(eachTest.get(6), eachSecond.get(6));
    					ehash.put(eachTest.get(7), eachSecond.get(7));
    					vhash.put(eachTest.get(3), eachSecond.get(3));
    					vhash.put(eachTest.get(4), eachSecond.get(8));
    					vhash.put(eachTest.get(5), eachSecond.get(9));
    					vhash.put(eachTest.get(5), eachSecond.get(10));
    					vhash.put(eachTest.get(5), eachSecond.get(11));
    					//pick up extra vertices
    					if(array.columns() > 5)
    					{
    						ArrayList<Integer> unPatternV = new ArrayList<Integer>();
    						Set<Integer> relatedver = relatedVer2nd(eachTest, hyper);
    						Set<Integer> unionSetV = new HashSet<Integer>();
 
    						for(Integer re:relatedver) 
    						{
    							if(!vhash.values().contains(re))
    							{
    								unionSetV.add(re);
    							}
    						} 
    						ArrayList<Integer> permu = new ArrayList<Integer>(unionSetV); 
    						ArrayList<ArrayList<Integer>> permuResults = determin.Permutation(permu,array.columns()-3);
    						//find the vertices not in pattern
    						for(i = 0; i < array.columns(); i++)
    						{
    							if((i!= eachTest.get(3)) && (i!= eachTest.get(4)) &&(i!= eachTest.get(5)))
    							{
    								unPatternV.add(i);
    							}
    						}
    						//since the triangle is found, only one group of vertices matches cause a return
    						for(ArrayList<Integer> currentVPermu: permuResults)
    						{
    							flag = 1;
    							HashMap<Integer, Integer> otherVhash = new HashMap<Integer, Integer>();
    							for(i = 0 ; i < array.rows(); i++)
    							{
    								for(j = 0; j < unPatternV.size(); j++)
    								{
    									if(array.get(unPatternV.get(j), i)== hyper.get(currentVPermu.get(j), i))
    									{
    										flag = flag * 1;
        									otherVhash.put(unPatternV.get(j), currentVPermu.get(j));
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
    							}
    							if(flag == 1)
    							{
    								System.out.print(vhash);
    		    					match.add(eachSecond);
    								break;
    							}
    						}

    					}
    					else
    					{
	    					match.add(eachSecond);
    					}
    				}
    				//case3: share one edge and 0 vertices
    				else if((eachTest.get(0)==1) && (eachTest.get(1)==0)
    						&&(eachTest.get(0) == eachSecond.get(0)) 
    						&& (eachTest.get(1) == eachSecond.get(1)))
    				{
    					//hash the sub graph's v and e to the large graph's v and e
    					HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
    					HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
    					
    					ehash.put(eachTest.get(2), eachSecond.get(2));
    					ehash.put(eachTest.get(7), eachSecond.get(3));
    					ehash.put(eachTest.get(8), eachSecond.get(4));
    					ehash.put(eachTest.get(9), eachSecond.get(5));
    					ehash.put(eachTest.get(6), eachSecond.get(6));
    					vhash.put(eachTest.get(3), eachSecond.get(7));
    					vhash.put(eachTest.get(4), eachSecond.get(8));
    					vhash.put(eachTest.get(5), eachSecond.get(9));
    					vhash.put(eachTest.get(5), eachSecond.get(10));
    					vhash.put(eachTest.get(5), eachSecond.get(11));
    					vhash.put(eachTest.get(5), eachSecond.get(12));
    					//pick up extra vertices
    					if(array.columns() > 6)
    					{
    						ArrayList<Integer> unPatternV = new ArrayList<Integer>();
    						Set<Integer> relatedver = relatedVer2nd(eachTest, hyper);
    						Set<Integer> unionSetV = new HashSet<Integer>();
 
    						for(Integer re:relatedver) 
    						{
    							if(!vhash.values().contains(re))
    							{
    								unionSetV.add(re);
    							}
    						} 
    						ArrayList<Integer> permu = new ArrayList<Integer>(unionSetV); 
    						ArrayList<ArrayList<Integer>> permuResults = determin.Permutation(permu,array.columns()-3);
    						//find the vertices not in pattern
    						for(i = 0; i < array.columns(); i++)
    						{
    							if((i!= eachTest.get(3)) && (i!= eachTest.get(4)) &&(i!= eachTest.get(5)))
    							{
    								unPatternV.add(i);
    							}
    						}
    						//since the triangle is found, only one group of vertices matches cause a return
    						for(ArrayList<Integer> currentVPermu: permuResults)
    						{
    							flag = 1;
    							HashMap<Integer, Integer> otherVhash = new HashMap<Integer, Integer>();
    							for(i = 0 ; i < array.rows(); i++)
    							{
    								for(j = 0; j < unPatternV.size(); j++)
    								{
    									if(array.get(unPatternV.get(j), i)== hyper.get(currentVPermu.get(j), i))
    									{
    										flag = flag * 1;
        									otherVhash.put(unPatternV.get(j), currentVPermu.get(j));
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
    							}
    							if(flag == 1)
    							{
    								System.out.print(vhash);
    		    					match.add(eachSecond);
    								break;
    							}
    						}

    					}
    					else
    					{
	    					match.add(eachSecond);
    					}
    				}
    			}
    		}
    	}
    	Set<Integer> returnIndx = new HashSet<Integer>();
		for(ArrayList<Integer> s: match)
		{
			returnIndx.add(s.get(2));
		}
    	System.out.println("secondlevelmathces"+returnIndx);
		return returnIndx;
	}
	/*
	 * TWO STARS: ONLY CONSIDER SHARED ONE EDGE
	 * [1stCenter, 2ndCenter, sharedEdge ]
	 */
	public ArrayList<ArrayList<Integer>> get2ndLevelStar(Set<ArrayList<Integer>> list, BitMatrix hyper)
	{
		int i, j;
		ArrayList<ArrayList<Integer>> twoStar = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> stars = new ArrayList<ArrayList<Integer>>(list);
		HashMap<Integer, Set<Integer>> verticeEdgeHash = array2hashC(hyper);//hyper is the indexing graph, not the hyperdatabase
		setOp op = new setOp();
		
		int size = stars.size();
		for(i = 0; i < size; i++ )
		{
			for(j = i+1; j < size; j++)
			{
				Set<Integer> tempSetE = new HashSet<Integer>();
				tempSetE = op.intersection(verticeEdgeHash.get(stars.get(i).get(0)), verticeEdgeHash.get(stars.get(j).get(0)));
				ArrayList<Integer> tempArrayE = new ArrayList<Integer>(tempSetE);
				if(tempSetE.size() == 1)
				{
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.add(stars.get(i).get(0));
					temp.add(stars.get(j).get(0));
					temp.add(tempArrayE.get(0));
					twoStar.add(temp);
				}
			}
		}

		return twoStar;
	}
	/*
	 * parameters: query array, query's 2ndlevel shape, hyper's 2nd level shape, hypergraph
	 */
	public Set<Integer> determinSecondStar(BitMatrix array, ArrayList<ArrayList<Integer>> index2Q, ArrayList<ArrayList<Integer>> index2, BitMatrix hyper)
	{
		int i,j;
		newDetermin c = new newDetermin();
		Set<Integer> matches = new HashSet<Integer>();
		
    	if((index2Q.size() > 0) && (index2.size() > index2Q.size()))
    	{
    		//pick the second index in hyper have both degree greater than the one in query
    		//find the one in index2Q which has the max edge degree
    		int maxDegree = 0;
    		for(ArrayList<Integer> each2nd: index2Q)
    		{
    			int degree1 = c.itsEdges(each2nd.get(0), array).size();
    			if(degree1 > maxDegree)
    				maxDegree = degree1;
    			int degree2 = c.itsEdges(each2nd.get(1), array).size();
    			if(degree2 > maxDegree)
    				maxDegree = degree2;
    		} 
 
    		//suppose the maximum degree star is in the second level indexing of the query:only use stars in hyper's second lvl index
    		Set<ArrayList<Integer>> hyperNewIndex =  new HashSet<ArrayList<Integer>>();
    		for(i = 0 ; i < index2.size(); i++)
    		{
    			//each second level index can add two stars in the index
    			int a = index2.get(i).get(0);
    			int aSize = c.itsEdges(a, hyper).size();
    			int b = index2.get(i).get(1);
    			int bSize = c.itsEdges(b, hyper).size();
    			//if both stars in index2 has larger degree, use both of them
    			if((aSize >= maxDegree) && (bSize >= maxDegree))
    			{
        			ArrayList<Integer> temp1 = new ArrayList<Integer>();
        			ArrayList<Integer> temp2 = new ArrayList<Integer>();
 
            		temp1.add(a);
            		temp1.add(aSize);
 
            		temp2.add(b);
            		temp2.add(bSize);
 
        			hyperNewIndex.add(temp1);
        			hyperNewIndex.add(temp2);
    			}
 
    		}
    		matches = c.determineStar(array, hyperNewIndex, hyper);
    	} 
		return matches;
	}
}
