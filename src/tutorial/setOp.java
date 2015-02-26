package tutorial;

import java.util.HashSet;
import java.util.Set;

public class setOp {
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
}
