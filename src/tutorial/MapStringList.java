package tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MapStringList { 
	   private HashMap map;  
	   
	   public MapStringList()  
	   {  
	      map = new HashMap();  
	   }  
	   
	   public void add(String key, String value)  
	   {  
	      if (map.containsKey(key))  
	      {  
	         List values = (List)map.get(key);  
	         values.add(value);  
	      }  
	   
	      else  
	      {  
	         List values = new ArrayList();  
	         values.add(value);  
	         map.put(key, values);  
	      }  
	   }  
	   
	   public List getValues(String key)  
	   {  
	       return (List)map.get(key);  
	   }  
	   public Set getKeys()
	   {
		   return map.keySet();
	   }
	   
}