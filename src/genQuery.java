import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;

/*
 * This class is used to generate one query
 */
public class genQuery {

	/*
	 * The file is in  the format: author: paper
	 * given the number of column and row, generate a graph in the database
	 * 1.pick a random row number, get all connected edges and vertices
	 * 2.randomly get (column, row) as a subgraph 
	 * 3.test if it is connected. 
	 * 4.if yes, then this is a query, if no, keep on doing 1.
	 */
	public BitMatrix knownQuery(int row, int column, BitMatrix hyper)
	{
		BitMatrix hyperQ = new BitMatrix(column, row);
		newDetermin n = new newDetermin();
		Random generator = new Random();
		int pick = generator.nextInt(row);
		int i, j ,k, size1=0, size2=0;
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		relatedver = n.itsVer(pick, hyper); 
		HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
		while((size1 < row) || (size2 < column))
		{
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), hyper), relatededges);
			}
			size1 = relatededges.size();
			ArrayList<Integer> relatededgeArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgeArray.size(); i++)
			{
				relatedver = n.union(n.itsVer(relatededgeArray.get(i), hyper), relatedver);
			}
			size2 = relatedver.size();
			
		}
		ArrayList<Integer> relatededgeArray = new ArrayList<Integer>(relatededges);
		ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
		for(i = 0; i < row ;i++)
		{
			ehash.put(i, relatededgeArray.get(i));
		}
		for(i = 0; i < column ;i++)
		{
			vhash.put(i, relatedverArray.get(i));
		}
		for(i = 0 ; i < column; i++)
		{
			for(j = 0 ; j < row; j++)
			{
				if(hyper.get(vhash.get(i), ehash.get(j)) == true)
					hyperQ.put(i, j, true);
				else
					hyperQ.put(i, j, false);
			}
		}
		
		return hyperQ;
	}
	/*
	 * generate a known query with second level index in it
	 * 1.random get a second level index element in the second indexing list 
	 * right now, not using row and column parameters 
	 */
	public BitMatrix known2ndQuery(int row, int column, BitMatrix hyper, ArrayList<ArrayList<Integer>> second)
	{
		Random generator = new Random();
		BitMatrix test = new BitMatrix(4,5);;
		//pick up a random second level index
		int pick;
		do{
		pick = generator.nextInt(second.size());
		
		if(second.get(pick).get(1) == 3)//sharing one edge and 3 vertices
			/*
			 * 	  { 1,1,0,1},
	    	      { 0,1,1,0},
	    	      { 1,0,1,0},
	    	      { 1,1,0,0},
	    	      { 1,0,1,0}
			 */
		{
			System.out.println("TYPE3");
			test = new BitMatrix(4,5);
			test.put(0, 0, true);
			test.put(1, 0, true);
			test.put(3, 0, true);
			test.put(1, 1, true);
			test.put(2, 1, true);
			test.put(0, 2, true);
			test.put(2, 2, true);
			test.put(0, 3, true);
			test.put(1, 3, true);
			test.put(0, 4, true);
			test.put(2, 4, true);
		}
		else if(second.get(pick).get(1) == 2)//sharing one edge and 2 vertices
			/*
			 * 	  { 1,1,0,1},
	    	      { 0,1,1,0},
	    	      { 1,0,1,0},
	    	      { 0,1,0,1},
	    	      { 0,0,1,1}
			 */
		{
			System.out.println("TYPE2");
			test = new BitMatrix(4,5);
			test.put(0, 0, true);
			test.put(1, 0, true);
			test.put(3, 0, true);
			test.put(1, 1, true);
			test.put(2, 1, true);
			test.put(0, 2, true);
			test.put(2, 2, true);
			test.put(1, 3, true);
			test.put(3, 3, true);
			test.put(2, 4, true);
			test.put(3, 4, true);
		}
		else if(second.get(pick).get(1) == 1)//sharing one edge and 1 vertices
			/*
			 * 	  { 1,1,0,0,0},
	    	      { 0,1,1,1,0},
	    	      { 1,0,1,0,0},
	    	      { 0,1,0,0,1},
	    	      { 0,0,0,1,1}
			 */
		{
			System.out.println("TYPE1");
			test = new BitMatrix(5,5);
			test.put(0, 0, true);
			test.put(1, 0, true); 
			test.put(1, 1, true);
			test.put(2, 1, true);
			test.put(3, 1, true);
			test.put(0, 2, true);
			test.put(2, 2, true);
			  test.put(1, 3, true);
			  test.put(4, 3, true);
			  test.put(3, 4, true);
			  test.put(4, 4, true);
		}
		else if(second.get(pick).get(1) == 0)//sharing one edge and 1 vertices
			/*
			 * 	  { 1,1,0,0,0,0},
	    	      { 0,1,1,1,1,0},
	    	      { 1,0,1,0,0,0},
	    	      { 0,0,0,1,0,1},
	    	      { 0,0,0,0,1,1}
			 */
		{
			System.out.println("TYPE0");
			test = new BitMatrix(6,5);
			test.put(0, 0, true);
			test.put(1, 0, true); 
			test.put(1, 1, true);
			test.put(2, 1, true);
			test.put(3, 1, true);
			test.put(4, 1, true);
			test.put(0, 2, true);
			test.put(2, 2, true);
			test.put(3, 3, true);
			test.put(5, 3, true);
			test.put(4, 4, true);
			test.put(5, 4, true);
		}
		}
		while(second.get(pick).get(1) == 0);
		return test;
	}
	/*
	 * generate a known query with triangle in it
	 * 1.random get a triangle in the indexing list
	 * 2.random get some related edges in the whole set of the trianlge's related edges
	 * 3.random get some related vertices in the whole set of the trianlge's related vertices
	 * 4.test if the query is connected
	 * 5. if yes, return, else, keep on doing 2.
	 */
	public BitMatrix knownTQuery(int row, int column, BitMatrix hyper, Set<ArrayList<Integer>> triangle)
	{
		ArrayList<ArrayList<Integer>> tri = new ArrayList<ArrayList<Integer>>(triangle);
		BitMatrix hyperQ = new BitMatrix(column, row);
		newDetermin n = new newDetermin();
		Random generator = new Random();
		
		//pick up a random triangle
		int pick = generator.nextInt(tri.size());
 		int i, j , k, temp, size1=0, size2=0;
		Set<Integer> relatededges = new HashSet<Integer>(n.relatedEdgeT(tri.get(pick), hyper));
		Set<Integer> relatedver = new HashSet<Integer>(n.relatedVerT(tri.get(pick), hyper));
		ArrayList<Integer> pickT = new ArrayList<Integer>(tri.get(pick));;
		 

		while((relatededges.size() <= row) || (relatedver.size() <= column))
		{
			pick = generator.nextInt(tri.size());
			pickT = tri.get(pick);
			relatededges = new HashSet<Integer>(n.relatedEdgeT(pickT, hyper));
			relatedver = new HashSet<Integer>(n.relatedVerT(pickT, hyper));
		}
		HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
		//v0 in query corresponds triangle-0, v1 corresponds triangle-2, v2 corresponds triangle-4
		vhash.put(0, pickT.get(0)); 
		vhash.put(1, pickT.get(2));
		vhash.put(2, pickT.get(4));
		//e0 in query corresponds triangle-1, e1 corresponds triangle-3, e2 corresponds triangle-5
		ehash.put(0, pickT.get(1)); 
		ehash.put(1, pickT.get(3));
		ehash.put(2, pickT.get(5));
		
		ArrayList<Integer> permuE = new ArrayList<Integer>();
		for(Integer re:relatededges) 
		{
			if(!ehash.values().contains(re))
			{
				permuE.add(re);
			}
		}
		
		ArrayList<Integer> permuV = new ArrayList<Integer>();
		for(Integer re:relatedver) 
		{
			if(!vhash.values().contains(re))
			{
				permuV.add(re);
			}
		}
	
		ArrayList<ArrayList<Integer>> permuResultsE = n.Permutation(permuE,row-3);
		ArrayList<ArrayList<Integer>> permuResultsV = n.Permutation(permuV,column-3);
		for(ArrayList<Integer> currentPermuV: permuResultsV)
		{
			for(ArrayList<Integer> currentPermuE: permuResultsE)
			{
				for(i = 3; i < row; i++)
					ehash.put(i, currentPermuE.get(i-3));
				for(i = 3; i < column; i++)
					vhash.put(i, currentPermuV.get(i-3));
				for(i = 0 ; i < row; i++)
				{
					for(k = 0; k < column; k++)
					{
						if(hyper.get(vhash.get(k), ehash.get(i)) == true)
							hyperQ.put(k, i, true);
						else
							hyperQ.put(k, i, false);
					}
				}
				if(testConnect(hyperQ)==true)
					break;
			}
			if(testConnect(hyperQ)==true)
				break;
		}
		System.out.print("genQuery.java-knownTquery() ");

		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{

				if(hyperQ.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		System.out.print("(genQuery.java)know graph's: ");
		System.out.print("E"+ehash);
		System.out.println("V"+vhash);
		//among all the permutations, must be one can be connected graph
		return hyperQ;
	}
	

	/*
	 * generate a known query with star in it
	 * 1.random get a star in the indexing list
	 * 2.random get some related edges in the whole set of the star related edges
	 * 3.random get some related vertices in the whole set of the star related vertices
	 * 4.test if the query is connected
	 * 5. if yes, return, else, keep on doing 2.
	 */
	public BitMatrix knownSQuery(int row, int column, BitMatrix hyper, Set<ArrayList<Integer>> star)
	{
		ArrayList<ArrayList<Integer>> stars = new ArrayList<ArrayList<Integer>>(star);
		BitMatrix hyperQ = new BitMatrix(column, row);
		newDetermin n = new newDetermin();
		Random generator = new Random();
		
		//pick up a random triangle
		int pick = generator.nextInt(stars.size());
 		int i, j , k, temp, size1=0, size2=0;
		Set<Integer> relatededges = new HashSet<Integer>(n.relatedEdgeS(stars.get(pick), hyper));
		Set<Integer> relatedver = new HashSet<Integer>(n.relatedVerS(stars.get(pick), hyper));
		ArrayList<Integer> pickS = new ArrayList<Integer>(stars.get(pick));;
		 

		while((relatededges.size() <= row) || (relatedver.size() <= column))
		{
			pick = generator.nextInt(stars.size());
			pickS = stars.get(pick);
			relatededges = new HashSet<Integer>(n.relatedEdgeS(pickS, hyper));
			relatedver = new HashSet<Integer>(n.relatedVerS(pickS, hyper));
		}
		HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();

		
		ArrayList<Integer> permuE = new ArrayList<Integer>(relatededges);
		
		ArrayList<Integer> permuV = new ArrayList<Integer>(relatedver);
 
	
		ArrayList<ArrayList<Integer>> permuResultsE = n.Permutation(permuE,row);
		ArrayList<ArrayList<Integer>> permuResultsV = n.Permutation(permuV,column);
		for(ArrayList<Integer> currentPermuV: permuResultsV)
		{
			for(ArrayList<Integer> currentPermuE: permuResultsE)
			{
				for(i = 0; i < row; i++)
					ehash.put(i, currentPermuE.get(i));
				for(i = 0; i < column; i++)
					vhash.put(i, currentPermuV.get(i));
				for(i = 0 ; i < row; i++)
				{
					for(k = 0; k < column; k++)
					{
						if(hyper.get(vhash.get(k), ehash.get(i)) == true)
							hyperQ.put(k, i, true);
						else
							hyperQ.put(k, i, false);
					}
				}
				if((testConnect(hyperQ)==true) && (n.getHyperStarIndex(hyperQ).size() > 0))
					break;
			}
			if((testConnect(hyperQ)==true) && (n.getHyperStarIndex(hyperQ).size() > 0))
				break;
		}
		System.out.println("genQuery.java-knownSQuery()");

		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyperQ.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		System.out.print("(genQuery.java)know graph's: ");
		System.out.print("E"+ehash);
		System.out.println("V"+vhash);
		//among all the permutations, must be one can be connected graph
		return hyperQ;
	}
	/*
	 * generate a random query between a*b and (c-1+a)*(c-1+b)
	 */
	//generate query with triangle in it
	public BitMatrix oneQueryTriangle(int a, int b, int c)
	{
		Random generator = new Random();
		createindexwithoutDup testShape = new createindexwithoutDup();
		Set<ArrayList<Integer>> triangles = new HashSet<ArrayList<Integer>>();

		int row = generator.nextInt(c) + a;
		int column = generator.nextInt(c) + b;
		
		BitMatrix hyperQ = new BitMatrix(column, row);
		int i, j, randomNum1,randomNum2;

		do{
			do
			{
				hyperQ = new BitMatrix(column, row);
				for(i = 0; i < row*2; i++)
				{
					randomNum1 = generator.nextInt(row);
					randomNum2 = generator.nextInt(column);
					hyperQ.put(randomNum2, randomNum1, true);
				}
				ArrayList<Set<Integer>> relateEdgeList = testShape.relatedEdges(hyperQ);
				triangles = testShape.getTriIndex(hyperQ, relateEdgeList); 
			}while (triangles.size() == 0) ;
		}while(testConnect(hyperQ)==false);
		//print out the array
		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyperQ.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		return hyperQ;
	}	
	//generate query with star in it
	public BitMatrix oneQueryStar(int a, int b, int c)
	{
		Random generator = new Random();
		createindexwithoutDup testShape = new createindexwithoutDup();
		Set<ArrayList<Integer>> stars = new HashSet<ArrayList<Integer>>();

		int row = generator.nextInt(c) + a;
		int column = generator.nextInt(c) + b;
		
		BitMatrix hyperQ = new BitMatrix(column, row);
		int i, j, randomNum1,randomNum2;

		do{
			do
			{
				hyperQ = new BitMatrix(column, row);
				for(i = 0; i < row*2; i++)
				{
					randomNum1 = generator.nextInt(row);
					randomNum2 = generator.nextInt(column);
					hyperQ.put(randomNum2, randomNum1, true);
				}
				stars = testShape.getStarIndex(hyperQ);
			}while (stars.size() == 0) ;
		}while(testConnect(hyperQ)==false);
		//print out the array
		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyperQ.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		return hyperQ;
	}	
	/*
	 * generate a random query with second level indexing between a*b and (c-1+a)*(c-1+b)
	 */
	public BitMatrix oneQuerySecond(int a, int b, int c)
	{
		Random generator = new Random();
		newDetermin testShape = new newDetermin();
		secondlevelindx se = new secondlevelindx();
		Set<ArrayList<Integer>> triangles = new HashSet<ArrayList<Integer>>();
		Set<ArrayList<Integer>> stars = new HashSet<ArrayList<Integer>>();

		int row = generator.nextInt(c) + a;
		int column = generator.nextInt(c) + b;
		
		BitMatrix hyperQ = new BitMatrix(column, row);
		int i, j, randomNum1,randomNum2;

		int second = 0;
		do{
			do
			{
				hyperQ = new BitMatrix(column, row);
				for(i = 0; i < row*2; i++)
				{
					randomNum1 = generator.nextInt(row);
					randomNum2 = generator.nextInt(column);
					hyperQ.put(randomNum2, randomNum1, true);
				}
				triangles = testShape.getHyperTriangleIndex(hyperQ);
				stars = testShape.getHyperStarIndex(hyperQ);
				second = se.get2ndLevelIndx(triangles).size() + se.get2ndLevelStar(stars, hyperQ).size();
			}while( second == 0 );
		}while(testConnect(hyperQ)==false);
		System.out.println(second);
		//print out the array
		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyperQ.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		return hyperQ;
	}
	//test if the generated hypergraph query is connected
	public boolean testConnect(BitMatrix array)
	{
		int i,j, size1, size2=0, size3=-1, size4 = -2;

		newDetermin n = new newDetermin();
		Set<Integer> relatededges = new HashSet<Integer>();
		Set<Integer> relatedver = new HashSet<Integer>();
		relatedver = n.itsVer(0, array); 
		size1 = relatedver.size();
		//pick one edge and its vertices, find if there is a way to connect all edges and vertices
		while(size1 != size2)
		{
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			size1 = relatedverArray.size();
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), array), relatededges);
			}

			ArrayList<Integer> relatededgeArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgeArray.size(); i++)
			{
				relatedver = n.union(n.itsVer(relatededgeArray.get(i), array), relatedver);
			}
			size2 = relatedver.size();
			
		}
		
		while(size3 != size4)
		{
			ArrayList<Integer> relatedverArray = new ArrayList<Integer>(relatedver);
			
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), array), relatededges);
			}
			size3 = relatededges.size();
			ArrayList<Integer> relatededgeArray = new ArrayList<Integer>(relatededges);
			for(i = 0 ; i < relatededgeArray.size(); i++)
			{
				relatedver = n.union(n.itsVer(relatededgeArray.get(i), array), relatedver);
			}
			relatedverArray = new ArrayList<Integer>(relatedver);
			for(i = 0 ; i < relatedverArray.size(); i++)
			{
				relatededges = n.union(n.itsEdges(relatedverArray.get(i), array), relatededges);
			}
			size4 = relatededges.size();
			
		}
		if((size1 == array.columns()) && (size3 == array.rows()))
		{
			return true;
		}
		else
			return false;
	}
	public BitMatrix genHyper(int row, int column, int truth)
	{
		BitMatrix hyper = new BitMatrix(column, row);
		Random generator = new Random();
		int i, j, randomNum1,randomNum2;
		for(i = 0; i < truth; i++)
		{
			randomNum1 = generator.nextInt(row);
			randomNum2 = generator.nextInt(column);
			hyper.put(randomNum2, randomNum1, true);
		}
		//print out the hypergraph
		for(i = 0 ; i < row ; i ++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyper.get(j, i) == true)
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println();
		}
		return hyper;
	}
	/*
	 * generate a hypergraph with (triangleNum) triangles with known positions
	 */
	public HashMap<BitMatrix, Set<ArrayList<Integer>>> genHyperWithTrianglePosition(int row, int column, int triangleNum)
	{
		HashMap<BitMatrix, Set<ArrayList<Integer>>> hash = new HashMap<BitMatrix, Set<ArrayList<Integer>>>();
		BitMatrix hyper = new BitMatrix(column, row);
		Random generator = new Random();
		Set<ArrayList<Integer>> triangles = new HashSet<ArrayList<Integer>>();
		//store the vertices and edges so that no duplicate
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		ArrayList<Integer> edges = new ArrayList<Integer>();
		int i, j, temp1, temp2;
		//There are triangleNum triangles to be created
		for(i = 0; i < triangleNum; i++)
		{
			ArrayList<Integer> oneTriangle = new ArrayList<Integer>();

			while(oneTriangle.size()!=6)
			{
				temp1 = generator.nextInt(column);//v
				temp2 = generator.nextInt(row);//e
				
				if((!vertices.contains(temp1)) && (!edges.contains(temp2)))
				{
					oneTriangle.add(temp1);
					vertices.add(temp1);
					oneTriangle.add(temp2);
					edges.add(temp2);
				}
			}
			triangles.add(oneTriangle);
			hyper.put(oneTriangle.get(0), oneTriangle.get(1), true);
			hyper.put(oneTriangle.get(2), oneTriangle.get(1), true);
			hyper.put(oneTriangle.get(2), oneTriangle.get(3), true);
			hyper.put(oneTriangle.get(4), oneTriangle.get(3), true);
			hyper.put(oneTriangle.get(4), oneTriangle.get(5), true);
			hyper.put(oneTriangle.get(0), oneTriangle.get(5), true); 
		}
		System.out.print(triangles);
		System.out.println();
		//besides all the 1's , make all other numbers in the array 0
		for(i =0 ; i < row; i++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyper.get(j, i) != true)
					hyper.put(j, i, false);
			}
		}
		/*/PRINT
		for(i =0 ; i < row; i++)
		{
			for(j = 0 ; j < column; j ++)
			{
				System.out.print(hyper[i][j]);
			}
			System.out.println();
		}
		*/
		hash.put(hyper, triangles);
		return hash;
	} 
	
	/*
	 * generate a hypergraph with (num) Fouredge 2-triangles (sharing one edge)
	 * with known positions according to sharing vertices number(0,1 or 2)
	 */
	public HashMap<BitMatrix, Set<ArrayList<Integer>>> genHyperWith2TrianglePosition(int row, int column, int num, int sharingVnum)
	{
		HashMap<BitMatrix, Set<ArrayList<Integer>>> hash = new HashMap<BitMatrix, Set<ArrayList<Integer>>>();
		BitMatrix hyper = new BitMatrix(column, row);
		Random generator = new Random();
		Set<ArrayList<Integer>> triangles2 = new HashSet<ArrayList<Integer>>();
		//store the vertices and edges so that no duplicate
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		ArrayList<Integer> edges = new ArrayList<Integer>();
		int i, j, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11;
		//There are num triangles to be created
		for(i = 0; i < num; i++)
		{
			ArrayList<Integer> one5edge = new ArrayList<Integer>();
			one5edge.add(1);
			one5edge.add(sharingVnum);

			Set<Integer> verticesTemp = new HashSet<Integer>();
			Set<Integer> edgesTemp = new HashSet<Integer>();
			if(sharingVnum == 3)
			{
				do{
					temp1 = generator.nextInt(row);//common edge
					
					temp2 = generator.nextInt(column);//common vertex
					temp3 = generator.nextInt(column);//common vertex
					temp4 = generator.nextInt(column);//common vertex
					
					temp5 = generator.nextInt(row);//other edges
					temp6 = generator.nextInt(row);//other edges
					temp7 = generator.nextInt(row);//other edges
					temp8 = generator.nextInt(row);//other edges
					
					verticesTemp.add(temp2);
					verticesTemp.add(temp3);
					verticesTemp.add(temp4);
					
					edgesTemp.add(temp1);
					edgesTemp.add(temp5);
					edgesTemp.add(temp6);
					edgesTemp.add(temp7);
					edgesTemp.add(temp8);
				}while((verticesTemp.size()!=3) && (edgesTemp.size() != 5));
				
				one5edge.add(temp1);
				one5edge.add(temp2);
				one5edge.add(temp3);
				one5edge.add(temp4);
				one5edge.add(temp5);
				one5edge.add(temp6);
				one5edge.add(temp7);
				one5edge.add(temp8);
				
				triangles2.add(one5edge);
				hyper.put(one5edge.get(3), one5edge.get(2), true);
				hyper.put(one5edge.get(4), one5edge.get(2), true);

				hyper.put(one5edge.get(3), one5edge.get(6), true);
				hyper.put(one5edge.get(5), one5edge.get(6), true);

				hyper.put(one5edge.get(4), one5edge.get(7), true);
				hyper.put(one5edge.get(5), one5edge.get(7), true);

				hyper.put(one5edge.get(3), one5edge.get(8), true);
				hyper.put(one5edge.get(4), one5edge.get(8), true);

				hyper.put(one5edge.get(4), one5edge.get(9), true);
				hyper.put(one5edge.get(5), one5edge.get(9), true);
				 
			}
			
			if(sharingVnum == 2)
			{
				do{
					temp1 = generator.nextInt(row);//common edge
					
					temp2 = generator.nextInt(column);//common vertex
					temp3 = generator.nextInt(column);//common vertex
					
					temp5 = generator.nextInt(row);//other edges
					temp6 = generator.nextInt(row);//other edges
					temp7 = generator.nextInt(row);//other edges
					temp8 = generator.nextInt(row);//other edges
					

					temp4 = generator.nextInt(column);//other vertex
					temp9 = generator.nextInt(column);//other vertex
					
					verticesTemp.add(temp2);
					verticesTemp.add(temp3);
					verticesTemp.add(temp4);
					verticesTemp.add(temp9);

					edgesTemp.add(temp1);
					edgesTemp.add(temp5);
					edgesTemp.add(temp6);
					edgesTemp.add(temp7);
					edgesTemp.add(temp8);
				}while((verticesTemp.size()!=4) && (edgesTemp.size() != 5));
				
				one5edge.add(temp1);
				one5edge.add(temp2);
				one5edge.add(temp3);
				one5edge.add(temp5);
				one5edge.add(temp6);
				one5edge.add(temp7);
				one5edge.add(temp8);
				one5edge.add(temp4);
				one5edge.add(temp9);
				triangles2.add(one5edge);

				hyper.put(one5edge.get(3), one5edge.get(2), true);
				hyper.put(one5edge.get(4), one5edge.get(2), true);

				hyper.put(one5edge.get(3), one5edge.get(5), true);
				hyper.put(one5edge.get(9), one5edge.get(5), true);

				hyper.put(one5edge.get(4), one5edge.get(6), true);
				hyper.put(one5edge.get(9), one5edge.get(6), true);

				hyper.put(one5edge.get(3), one5edge.get(7), true);
				hyper.put(one5edge.get(10), one5edge.get(7), true);

				hyper.put(one5edge.get(4), one5edge.get(8), true);
				hyper.put(one5edge.get(10), one5edge.get(8), true);
				 
			}
			
			if(sharingVnum == 1)
			{
				do{
					temp1 = generator.nextInt(row);//common edge
					
					temp2 = generator.nextInt(column);//common vertex
					
					temp5 = generator.nextInt(row);//other edges
					temp6 = generator.nextInt(row);//other edges
					temp7 = generator.nextInt(row);//other edges
					temp8 = generator.nextInt(row);//other edges
					

					temp3 = generator.nextInt(column);//other vertex
					temp4 = generator.nextInt(column);//other vertex
					temp9 = generator.nextInt(column);//other vertex
					temp10 = generator.nextInt(column);//other vertex
					
					verticesTemp.add(temp2);
					verticesTemp.add(temp3);
					verticesTemp.add(temp4);
					verticesTemp.add(temp9);
					verticesTemp.add(temp10);

					edgesTemp.add(temp1);
					edgesTemp.add(temp5);
					edgesTemp.add(temp6);
					edgesTemp.add(temp7);
					edgesTemp.add(temp8);
				}while((verticesTemp.size()!=5) && (edgesTemp.size() != 5));
				
				one5edge.add(temp1);
				one5edge.add(temp2);
				one5edge.add(temp5);
				one5edge.add(temp6);
				one5edge.add(temp7);
				one5edge.add(temp8);
				one5edge.add(temp3);
				one5edge.add(temp4);
				one5edge.add(temp9);
				one5edge.add(temp10);
				triangles2.add(one5edge);

				hyper.put(one5edge.get(3), one5edge.get(2), true);
				hyper.put(one5edge.get(8), one5edge.get(2), true);
				hyper.put(one5edge.get(9), one5edge.get(2), true); 
				

				hyper.put(one5edge.get(3), one5edge.get(4), true);
				hyper.put(one5edge.get(10), one5edge.get(4), true);
				  

				hyper.put(one5edge.get(8), one5edge.get(5), true);
				hyper.put(one5edge.get(10), one5edge.get(5), true); 
				

				hyper.put(one5edge.get(3), one5edge.get(6), true);
				hyper.put(one5edge.get(11), one5edge.get(6), true);

				hyper.put(one5edge.get(9), one5edge.get(7), true);
				hyper.put(one5edge.get(11), one5edge.get(7), true);
				 
			}
			
			if(sharingVnum == 0)
			{
				do{
					temp1 = generator.nextInt(row);//common edge
					
					
					temp5 = generator.nextInt(row);//other edges
					temp6 = generator.nextInt(row);//other edges
					temp7 = generator.nextInt(row);//other edges
					temp8 = generator.nextInt(row);//other edges
					

					temp2 = generator.nextInt(column);//other vertex
					temp3 = generator.nextInt(column);//other vertex
					temp4 = generator.nextInt(column);//other vertex
					temp9 = generator.nextInt(column);//other vertex
					temp10 = generator.nextInt(column);//other vertex
					temp11 = generator.nextInt(column);//other vertex
					
					verticesTemp.add(temp2);
					verticesTemp.add(temp3);
					verticesTemp.add(temp4);
					verticesTemp.add(temp9);
					verticesTemp.add(temp10);
					verticesTemp.add(temp11);

					edgesTemp.add(temp1);
					edgesTemp.add(temp5);
					edgesTemp.add(temp6);
					edgesTemp.add(temp7);
					edgesTemp.add(temp8);
				}while((verticesTemp.size()!=6) && (edgesTemp.size() != 5));
				
				one5edge.add(temp1);
				one5edge.add(temp5);
				one5edge.add(temp6);
				one5edge.add(temp7);
				one5edge.add(temp8);
				one5edge.add(temp2);
				one5edge.add(temp3);
				one5edge.add(temp4);
				one5edge.add(temp9);
				one5edge.add(temp10);
				one5edge.add(temp11);
				triangles2.add(one5edge);

				hyper.put(one5edge.get(7), one5edge.get(2), true);
				hyper.put(one5edge.get(8), one5edge.get(2), true);
				hyper.put(one5edge.get(9), one5edge.get(2), true);
				hyper.put(one5edge.get(10), one5edge.get(2), true); 

				hyper.put(one5edge.get(7), one5edge.get(3), true);
				hyper.put(one5edge.get(11), one5edge.get(3), true);
				hyper.put(one5edge.get(8), one5edge.get(4), true);
				hyper.put(one5edge.get(11), one5edge.get(4), true); 

				hyper.put(one5edge.get(9), one5edge.get(5), true);
				hyper.put(one5edge.get(12), one5edge.get(5), true);
				hyper.put(one5edge.get(10), one5edge.get(6), true);
				hyper.put(one5edge.get(12), one5edge.get(6), true); 

			} 
		}
		for(i =0 ; i < row; i++)
		{
			for(j = 0 ; j < column; j ++)
			{
				if(hyper.get(j, i) != true)
					hyper.put(j, i, false);
			}
		}
		/*
		for(i =0 ; i < row; i++)
		{
			for(j = 0 ; j < column; j ++)
			{
				System.out.print(hyper[i][j]);
			}
			System.out.println();
		}
		*/
		hash.put(hyper, triangles2);
		System.out.print(triangles2);
		System.out.println();
		return hash;
	} 
	public static void main(String args[])
	 {
		int i, j;
		genQuery a = new genQuery();
		a.oneQuerySecond(10,10,2);
		//a.genHyperWith2TrianglePosition(100, 100, 3, 2);
	 }
}
