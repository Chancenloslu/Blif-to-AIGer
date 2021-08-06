package strashing;

import main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIG {
//    private LinkedList<Level> aig;
    
    public AIG() {
		super();
	}

	public void generateAIG(NodeLib lib, String keyOfNode1, String keyOfNode2, int operationType) {

    	if(operationType == Main.AND){
			Node n1 = lib.getNode(keyOfNode1);
			Node n2 = lib.getNode(keyOfNode2);
			this.Op_And(lib, n1, n2);
		}else if(operationType == Main.OR){

		}
	}

	public Node Op_Or(NodeLib lib, Node n_1, Node n_2){
    	Node res;
    	Node n1 = not(n_1);
    	Node n2 = not(n_2);
    	String keyOfNewNode = n1.key + "+" + n2.key;
    	res = CreateNode(n1, n2, keyOfNewNode);
		Aig_NodeLibAdd( lib, res );
    	res = not(res);
    	return res;
	}

	private Node not(Node node) {
    	int index = node.index;
    	boolean isInverted;
    	int remainder = index % 2;
    	if(remainder == 1){// this node is Inverted
    		index = index - 1;
    		isInverted = false;
		}else {//this node not Inverted
			index = index + 1;
			isInverted = true;
		}
		Node n = new Node(node.father, node.leftChild, node.rightChild, node.key, index, isInverted);
    	return n;
	}

	/*
	两个节点还有lib为参数，查找lib中是否有等效节点，有 返回， 无 创建并加到lib中

	 */
	public Node Op_And(NodeLib lib, Node n1, Node n2 )
	{
		Node res;
		HashMap<String, Node> level;

		/*** trivial cases ***/
		if ( n1.equals(n2)) return n1;
		//if ( n1.equals(NOT(n2))) return null;
		//if ( n1 == const ) return 0 or n2;
		//if ( n2 == const ) return 0 or n1;
		//TODO: swap
//		if ( n1 < n2 ) { /** swap the arguments **/
//			temp = n1; n1 = n2; n2 = temp;
//		}
		/*** one level structural hashing ***/
		res = Aig_NodeLibLookup( lib, n1, n2 );
		if ( res!=null ) return res;
		res = CreateNode( n1, n2 );
		//Aig_NodeLibAdd( lib, res );
		//if(FlagUseOneLevelHashing) return res;
		/*** functional reduction ***/
		/*将存放n1和n2结果的节点的层取出来。若无 创建，在lib中添加层
		若有 层中已有的节点和结果生成的节点再作等效检测*/
		level = Aig_LevelLibLookup(lib, n1, n2 );
		if ( level == null ) {
			level = CreateNewLevel( res );
			Aig_LevelLibAdd(lib, level, res);
			return res;
		}

		Aig_NodeLibAdd( lib, res );
		return res;
	}

	private void Aig_LevelLibAdd(NodeLib lib, HashMap<String, Node> level, Node n1) {
		int indexOfLevelToBeAdded = n1.key.length() - 1;
		lib.Aig_NodeLib.add(indexOfLevelToBeAdded, level);
	}

	private HashMap<String, Node> CreateNewLevel(Node res) {
		HashMap<String, Node> level = new HashMap<>();
		level.put(res.key, res);
		return level;
	}

	private void Aig_NodeLibAdd(NodeLib lib, Node n1) {
		String k = n1.key;
		if(k.contains("+")){
			HashMap<String, Node> newLevel = new HashMap<>();
			newLevel.put(k, n1);
			lib.Aig_NodeLib.add(newLevel);
		}else{
			int indexOfLevelToStore = n1.key.length() - 1;
			lib.Aig_NodeLib.get(indexOfLevelToStore).put(k, n1);
		}
	}

	private Node CreateNode(Node n1, Node n2){
		Node.INDEX += 2;
		int index = Node.INDEX;
		String keyOfNewNode = n1.key + n2.key;
		keyOfNewNode = StrOp.sort(keyOfNewNode);
		Node newNode = new Node(new ArrayList<Node>(), n1, n2, keyOfNewNode, index,false);
		n1.father.add(newNode);
		n2.father.add(newNode);
		return newNode;
	}

	private Node CreateNode(Node n1, Node n2, String keyOfNewNode){
		Node.INDEX += 2;
		int index = Node.INDEX;
		Node newNode = new Node(new ArrayList<Node>(), n1, n2, keyOfNewNode, index,false);
		n1.father.add(newNode);
		n2.father.add(newNode);
		return newNode;
	}
	/**
	 * check, whether the node to be created already exists
	 * @param lib
	 * @param n1
	 * @param n2
	 * @return
	 */
	private Node Aig_NodeLibLookup(NodeLib lib, Node n1, Node n2) {
		List<HashMap<String, Node>> library = lib.Aig_NodeLib;
		String keyOfNewNode = n1.key + n2.key;
		keyOfNewNode = StrOp.sort(keyOfNewNode);
		int indexOfLevel = keyOfNewNode.length() - 1;
		try{
			HashMap<String, Node> level = library.get(indexOfLevel);
			if(level.containsKey(keyOfNewNode)) return level.get(keyOfNewNode);
			return null;
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	private HashMap<String, Node> Aig_LevelLibLookup(NodeLib lib, Node n1, Node n2) {
		List<HashMap<String, Node>> library = lib.Aig_NodeLib;
		String keyOfNewNode = n1.key + n2.key;
		keyOfNewNode = StrOp.sort(keyOfNewNode);
		int indexOfLevel = keyOfNewNode.length() - 1;
		try {
			HashMap<String, Node> level = library.get(indexOfLevel);
			return level;
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	public int calculate_AIGNode(NodeLib lib) {
		ArrayList<HashMap<String, Node>> tempLib = lib.Aig_NodeLib;
		int numOfNode = 0;
		for(int i=1; i<tempLib.size(); i++){
			numOfNode += tempLib.get(i).size();
		}
		return numOfNode;
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

	public ArrayList<Node> flieNodes(NodeLib lookUpLib) {
		ArrayList<HashMap<String, Node>> tempLib = lookUpLib.Aig_NodeLib;
		ArrayList<Node> res = new ArrayList<>();
		for(int i=1; i<tempLib.size();i++){
			HashMap<String, Node> map = tempLib.get(i);
			for(Map.Entry<String, Node> entry:map.entrySet()){
				Node n = entry.getValue();
				res.add(n);
			}
		}
		return res;
	}
}
