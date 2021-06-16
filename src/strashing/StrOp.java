package strashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrOp {

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

	/**
     * 
     * @param str 	original string
     * @param num 	the number of characters in substrings, which you want to return
     * @return
     */
    public static Set<String> getSubstring(String str, int num) {
    	Set<String> toReturn = new HashSet<>();
		char[] array = str.toCharArray();
		 if(array==null||array.length==0){  
		     return null;  
		 } 
		 
		 List<Character> tempList=new ArrayList<>(); 
		 combine(array, 0, num, tempList, toReturn);

		return toReturn;
    }
    

    //从字符数组中第begin个字符开始挑选number个字符加入list中
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
        
        //一是把这个字符放到组合中去，接下来还需要在剩下的n-1个字符中选取m-1个字符；
//        二是不把这个字符放到组合中去，接下来需要在剩下的n-1个字符中选择m个字符。
        list.add(array[begin]);  
        combine(array,begin+1,number-1,list, set);  
        list.remove((Character)array[begin]);  
        combine(array,begin+1,number,list, set);  
    } 

}
