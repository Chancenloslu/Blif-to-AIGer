package strashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrOp {
//    public HashMap<String, Node> unduplicatedLevel = new HashMap<>();
//    public ArrayList<Node> duplicatedLevel;

    /**
     * sort the key of the Node, ignore the upper- and lowercase
     * @param string
     * @return sorted string
     */
    public static String sort(String string) {
    	String str = "";
    	char[] charArr = string.toCharArray();
    	List<String> list = new ArrayList<>();
    	for(char c: charArr) {
    		list.add(String.valueOf(c));
    	}
    	Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
    	for(String s: list) {
    		str = str + s;
    	}
		return str;
    }
	
	public static String deleteCharString(String sourceString, String strElemData) {
		
		char[] chElemData = strElemData.toCharArray();
		String deleteString = "";

		for (int i = 0; i < sourceString.length(); i++) {
			boolean isCharToBeDeleted = false;
			for(char c: chElemData) {
				if (sourceString.charAt(i) == c) isCharToBeDeleted = true;
			}
			if(!isCharToBeDeleted) deleteString += sourceString.charAt(i);
		}
		return deleteString;
	}	
	
//    /**
//     * find the most occurrence substring
//     * @param level 	the level in the LookUpLibrary 
//     * @param key		the key of Nodes, which is parsed from the blif-file 
//     * @return 
//     */
//    public static String getMostOcurSub(HashMap<String, Node> level, String key) {
//    	int maxCount = 0;
//    	String stringWithMaxCount = null;
////    	Hashtable<Level, HashSet<Node>> table = new Hashtable<>();
//    	
//    	// calculate the number of Occurence of each childNode
//    	Map<String, Integer> map = new HashMap<>();
//    	for(Map.Entry<String, Node> entry: level.entrySet()) {
//    		Set<String> set = getSubstring(entry.getKey(), lib.get);
//    		for(String s: set) {
//    			if(!map.containsKey(s)) map.put(s, 1);
//    			else {
//    				int oldValue = map.get(s);
//    				map.replace(s, oldValue, oldValue+1);
//    			}
//    		}
//    	}
//    	
//    	for(Map.Entry<String,Integer> m: map.entrySet()) {
//    		if(m.getValue() > maxCount) {
//    			maxCount = m.getValue();
//    			stringWithMaxCount = m.getKey();
//    		}
//		}
//    	
//    	
//    	System.out.println(""+stringWithMaxCount+"出现的次数为："+maxCount+"次");
//    	
//    	return stringWithMaxCount;
//    }
    
	/**
	 * get subString of str
	 * @param str
	 * @return set of Substring
	 */
	public static HashSet<String> getSubstring(String str) {
		
    	HashSet<String> toReturn = new HashSet<>();
		char[] array = str.toCharArray();
		 if(array==null||array.length==0){  
		     return null;  
		 } 
		 
		 List<Character> tempList=new ArrayList<>();
		 for(int num=2; num<str.length(); num++) {
			 combine(array, 0, num, tempList, toReturn);
		 }
		 
//		 for(String s: toReturn) {
//			 System.out.println(s);
//		 }
         
		 return toReturn;
    }
	
    /**
     * get subString of str
     * @param str 	original string
     * @param num 	the number of characters in substrings, which you want to return
     * @return		the set with all the substring
     */
    public static Set<String> getSubstring(String str, int num) {
    	Set<String> toReturn = new HashSet<>();
		char[] array = str.toCharArray();
		 if(array==null||array.length==0){
		     return null;  
		 } 
		 
		 List<Character> tempList=new ArrayList<>(); 
		 combine(array, 0, num, tempList, toReturn);
//		 for(String s: toReturn) {
//			 System.out.println(s);
//		 }
         
		 return toReturn;
    }
    

    /**
     * Pick (@param number) characters from the (@param begin)th character in the character 
     * array and add them to the list
     * 
     * @param array 	char array 
     * @param begin		the start position 
     * @param number	the number of characters in substring
     * @param list		tempList
     * @param set		set with substring to return
     */
    public static void combine(char[] array,int begin,int number,List<Character> list, Set<String> set){  
        if(number==0){
        	String out="";
			for(Character c:list) {
				out=out+c.toString();
			}
			set.add(out);
//			System.out.print(out+" ");
			return; 
        }  
        if(begin==array.length){  
            return;  
        } 
        
        /*One is to put this character in the combination, and then you need to 
        select m-1 characters from the remaining n-1 characters;*/
        list.add(array[begin]);  
        combine(array,begin+1,number-1,list, set);  
        list.remove((Character)array[begin]);  
        
        /* The second is not to put this character in the combination, and then 
         you need to select m characters from the remaining n-1 characters.*/
        combine(array,begin+1,number,list, set);  
    } 

}
