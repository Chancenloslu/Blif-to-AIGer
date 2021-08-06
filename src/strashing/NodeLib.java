package strashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*	library format:
	level of primaryinput			a b c d A B C D
									-----------
	Node with two literals 			ab Ab aB AB
				.					-----------
				.					abc abC Abc AbC ......
				.					-----------
	level of Minterms				abcd AbCd ......
				.					-----------
 */
public class NodeLib {

	public ArrayList<HashMap<String, Node>> Aig_NodeLib = new ArrayList<>();
	private int numOfLevels;

	public NodeLib() {
	}

//	public SubstruLib(String[] inputFromParser) {
//		// TODO Auto-generated constructor stub
//		for(String s: inputFromParser) {
//			Node n = new Node(s);
//			unduplicatedLevel.add(n);
//		}
//	}


	public int getNumOfLevels() {
		numOfLevels = this.Aig_NodeLib.size();
		return numOfLevels;
	}

	public List<HashMap<String, Node>> getAig_NodeLib() {
		return Aig_NodeLib;
	}

	public Node getNode(String keyOfNode) {
		Node res = null;
		int indexOfLevel = keyOfNode.length() - 1;
		try{
			HashMap<String, Node> tempTable = this.Aig_NodeLib.get(indexOfLevel);
			res = tempTable.get(keyOfNode);
		}catch (IndexOutOfBoundsException e){//no level of this index
			return null;
		}
		return res;
	}


//	/**
//     * find the biggest common Node
//     * @param key		the key of minterm, which is parsed from the blif-file
//     * @return
//     */
//    public String getCommmonNode(String key) {
//    	String keyOfCommonNode = null;
////    	Hashtable<Level, HashSet<Node>> table = new Hashtable<>();
//
//    	for (int i = this.lib.size() - 1; i > 0; i--) {
//    		Set<String> set = StrOp.getSubstring(key, i + 1);
//    		for(String s: set) {
//    			if(this.lib.get(i).containsKey(s)) return s;
//    		}
//    	}
//
//    	return keyOfCommonNode;
//    }

	//can't direct add B to the lib it is ~b
    /**
     * initialize the LookUpLibrary with the primary input
     * @param inputnames the primary input
     */
	public void initLib(char[] inputnames) {

		HashMap<String, Node> tableForInit = new HashMap<>();
		
		// add node a b c d e A B C D E
		for(int i=0; i<inputnames.length; i++) {
			Node.INDEX = Node.INDEX + 2;
			int index = Node.INDEX;
			Node n1 = new Node(String.valueOf(inputnames[i]), index);//a----1
			Node n2 = new Node(String.valueOf(Character.toUpperCase(inputnames[i])), index+1);//A----0
			tableForInit.put(n1.key, n1);
			tableForInit.put(n2.key, n2);
		}
		
		this.Aig_NodeLib.add(tableForInit);
		
	}
}


