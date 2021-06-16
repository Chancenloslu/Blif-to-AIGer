package strashing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AIG {


    
    public AIG() {
		super();
	}

	public int calculate_AIGNode(List<HashMap<String, Node>> lib, List<Node> AIG_commonNode) {
		int numOfNode = 0;
		int numOfCommonNode = 0;
		
		HashMap<String, Node> mintermNode = lib.get(lib.size()-1);
		/*number Of Node above the level of Minterm*/
		numOfNode = mintermNode.size() - 1;
		
		for(Map.Entry<String, Node> entry: mintermNode.entrySet()) {
			numOfNode += calculate_Node(entry.getValue());
		}
		
		for(Node n: AIG_commonNode) {
			numOfCommonNode += calculate_Node(n);
		}
		
		return numOfNode - numOfCommonNode;
		
	}
	
	public int calculate_Node(Node Minterm) {
		int numOfNode = 0;
		if(Minterm.hasChild()) {
			numOfNode++;
			numOfNode += calculate_Node(Minterm.leftChild);
			numOfNode += calculate_Node(Minterm.rightChild);
		}
		return numOfNode;
	}
	
}
