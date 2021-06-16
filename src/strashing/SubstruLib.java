package strashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*	library format:
	Node with only one level		a b c d e 
									-----------
	Node with two level 			ab bc cd de ac bd ce ad be ae 
				.					-----------
				.					abd bcd bde ace abc acd ade abe bce cde 
				.					-----------
				.					bcde abce acde abcd abde 
				.					-----------
				.					abcde 
 */
public class SubstruLib {

	public List<HashMap<String, Node>> lib = new ArrayList<>();
	
	public List<Node> AIG_commonNode = new ArrayList<>();
	
	public SubstruLib() {
	}


	public List<HashMap<String, Node>> getLib() {
		return lib;
	}

	public void setLib(List<HashMap<String, Node>> lib) {
		this.lib = lib;
	}

	public List<Node> getAIG_commonNode() {
		return AIG_commonNode;
	}

	public void setAIG_commonNode(List<Node> aIG_commonNode) {
		AIG_commonNode = aIG_commonNode;
	}
	
	public void removeRelatedNode(Node commonNode) {
		int index = commonNode.key.length() - 1;
		char[] chElemData = commonNode.key.toCharArray();
		for (Iterator<Map.Entry<String, Node>> it = lib.get(index).entrySet().iterator(); it.hasNext();) {
			boolean shouldBeDeleted = false;
			Map.Entry<String, Node> entry = it.next();
			String key = entry.getKey();
			for (int i = 0; i < key.length(); i++) {
				for(char c: chElemData) {
					if (key.charAt(i) == c && entry.getValue().isCommonNode == false) 
						shouldBeDeleted = true;
				}
			}
			if(shouldBeDeleted) it.remove();
		}
	}
	
	public void generateAIG(String keyOfMinterm) {
		
		
		String keyOfcommonNode = getCommmonNode(keyOfMinterm);
		
		if(keyOfcommonNode != null) {
			
			lib.get(keyOfcommonNode.length()-1).get(keyOfcommonNode).isCommonNode = true;
			Node commonNode = lib.get(keyOfcommonNode.length()-1).get(keyOfcommonNode);
			removeRelatedNode(commonNode);
			String keyOfRestNode = StrOp.deleteCharString(keyOfMinterm, keyOfcommonNode);
			AIG_commonNode.add(commonNode);

			lib.get(commonNode.key.length()-1);
			for(int i=0; i<keyOfRestNode.length(); i++) {
				//剩下bc，检查是否有b c node，没有则添加
				String keyOfRestSingleNode = keyOfRestNode.substring(i, i+1);
				if(!lib.get(0).containsKey(keyOfRestSingleNode)) {
					Node n = new Node(keyOfRestSingleNode);
					lib.get(0).put(n.key, n);
				}
				//commonNode from Ab-->Abc
				commonNode = generateNode(commonNode, lib.get(0).get(keyOfRestSingleNode), lib);
			}
			
		}else {		//No common Nodes
			
			List<HashMap<String, Node>> tempLib = new ArrayList<>();
			Node minterm = new Node(keyOfMinterm);
			initLib(minterm, tempLib);
			for(int i=0; i<tempLib.size(); i++) {
				for(Map.Entry<String, Node> entry: tempLib.get(i).entrySet()) {
					Node n = entry.getValue();
					String key = n.key;
					lib.get(i).put(key,n);
				}
			}
		}

	}
	
	/**
     * find the biggest common Node
     * @param key		the key of minterm, which is parsed from the blif-file
     * @return 
     */
    public String getCommmonNode(String key) {
    	String keyOfCommonNode = null;
    	
    	for (int i = this.lib.size() - 1; i > 0; i--) {
    		Set<String> set = StrOp.getSubstring(key, i + 1);
    		for(String s: set) {
    			if(this.lib.get(i).containsKey(s)) keyOfCommonNode= s;
    		}
    	}
    	
    	return keyOfCommonNode;
    }
	
    /**
     * initialize the LookUpLibrary with the first Mintern from Parser
     * @param node	the first Mintern from Parser
     */
	public void initLib(Node node, List<HashMap<String, Node>> lib) {
		String firstMintern = node.key;	//aBcde
		HashMap<String, Node> tableForInit = new HashMap<>();
		
		// add node a B c d e
		for(int i=0; i<firstMintern.length(); i++) {
			Node n = new Node(firstMintern.substring(i, i+1));
			tableForInit.put(n.key, n); // a B c d e
		}
		
		lib.add(tableForInit);
		
		int numOfLevelToBeProcessed = tableForInit.size();
		
		/*initialize the lookUp library*/
		while(lib.size() < numOfLevelToBeProcessed) {
			lib.add(new HashMap<String, Node>());
		}
		int indexOfLevelToBeProcessed = 0;
		do {
			HashMap<String, Node> tempList = new HashMap<>();	//level to be processed
			tempList = lib.get(indexOfLevelToBeProcessed);
			
			for(Map.Entry<String, Node> entryOfTempList : tempList.entrySet()){
	            for (Map.Entry<String, Node> entryOfTableForInit : tableForInit.entrySet()){
	            	Node n1 = entryOfTempList.getValue();
	            	Node n2 = entryOfTableForInit.getValue();
	            	generateNode(n1, n2, lib);
//	            	if(!tableOfNewNode.containsKey(n.key)) tableOfNewNode.put(n.key, n);
	            }
			}
			indexOfLevelToBeProcessed++;
		} while(indexOfLevelToBeProcessed < numOfLevelToBeProcessed - 1);
		
	}
	
	
	
    public Node generateNode(Node n1, Node n2, List<HashMap<String, Node>> lib) {
    	
    	/*the two Nodes are the same*/
    	if(n1.key == n2.key) {
    		if(!lib.get(n1.key.length()-1).containsKey(n1.key)) lib.get(n1.key.length()-1).put(n1.key, n1);
    		return n1;
    	}
    	
    	
    	/*n1 n2 are primary inputs, a b c d*/
    	if(n1.isPrimaryInput() && n2.isPrimaryInput()) {
    		String n1_key = n1.key.toLowerCase();
        	String n2_key = n2.key.toLowerCase();
        	if (n1_key.compareTo(n2_key)>0) {
        		Node temp = n1;
        		n1 = n2;
        		n2 = temp;
        	}
        	String k = n1.key + n2.key;
    		Node n = new Node(new ArrayList<Node>(), n1, n2, k, false);
    		if(!lib.get(1).containsKey(k)) lib.get(1).put(k, n);
    		return n;
    	}
    	
    	
    	if(n1.isChildOf(n2)) {
    		int index = n2.key.length() - 1;
    		if(!lib.get(index).containsKey(n2.key)) lib.get(index).put(n2.key, n2);
    		return n2;
    	}
    	if(n2.isChildOf(n1)) {
    		int index = n1.key.length() - 1;
    		if(!lib.get(index).containsKey(n1.key)) lib.get(index).put(n1.key, n1);
    		return n1;
    	}
    	
    	//acB --> aBc

		String k = n1.key + n2.key;
    	k = StrOp.sort(k);
    	Node newNode = new Node(new ArrayList<Node>(), n1, n2, k, false);
    	if(!n1.father.contains(newNode)) n1.father.add(newNode);
    	if(!n2.father.contains(newNode)) n2.father.add(newNode);
    	int index = k.length() - 1;
    	lib.get(index).put(k, newNode);
    	return newNode;
    }
    
    
    public Node generateNode(Node n1, Node n2) {
    	
    	/*the two Nodes are the same*/
    	if(n1.key == n2.key) {
    		return n1;
    	}
    	
    	
    	/*n1 n2 are primary inputs, a b c d*/
    	if(n1.isPrimaryInput() && n2.isPrimaryInput()) {
    		String n1_key = n1.key.toLowerCase();
        	String n2_key = n2.key.toLowerCase();
        	if (n1_key.compareTo(n2_key)>0) {
        		Node temp = n1;
        		n1 = n2;
        		n2 = temp;
        	}
        	String k = n1.key + n2.key;
    		Node n = new Node(new ArrayList<Node>(), n1, n2, k, false);
    		return n;
    	}
    	
    	
    	if(n1.isChildOf(n2)) {
    		return n2;
    	}
    	if(n2.isChildOf(n1)) {
    		return n1;
    	}
    	
    	//acB --> aBc

		String k = n1.key + n2.key;
    	k = StrOp.sort(k);
    	Node newNode = new Node(new ArrayList<Node>(), n1, n2, k, false);
    	if(!n1.father.contains(newNode)) n1.father.add(newNode);
    	if(!n2.father.contains(newNode)) n2.father.add(newNode);
    	return newNode;
    }
}


