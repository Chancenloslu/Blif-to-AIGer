package strashing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    public List<Node> father = new ArrayList<>();
    public Node leftChild;
    public Node rightChild;
    public String key;
    public boolean isCommonNode;     //if it is final state(key=1), stop separating it
    public int Index;

    /**
     * middle node
     * @param father
     * @param leftChild
     * @param rightChild
     * @param key
     * @param single
     */
    public Node(List<Node> father, Node leftChild, Node rightChild, String key, boolean isCommonNode) {
        this.father = father;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.key = key;
        this.isCommonNode = isCommonNode;
    }

    /**
     * default first node
     * @param key
     */
    public Node(String key){
        this.key=key;
        this.father=new ArrayList<Node>();
        this.isCommonNode = false;
        this.leftChild=null;
        this.rightChild=null;
    }
    
    
    public boolean hasChild() {
    	return this.leftChild != null && this.rightChild != null; 
    }

	public boolean isChildOf(Node node) {
    	if(node.leftChild == null && node.rightChild == null) return false;
    	if(this.key == node.leftChild.key || this.key == node.rightChild.key) return true;
    	return this.isChildOf(node.leftChild) || this.isChildOf(node.rightChild);
    }
    
    public boolean isPrimaryInput() {
    	return this.leftChild == null && this.rightChild == null;
    }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) return false;
		// if it is this object itself
        if(obj == this) return true;
        // check if the type is right
        if(!(obj instanceof Node)) return false;

        // transfer to Node
        Node t = (Node) obj;

        // check that the fields in the parameter match the fields in the object.
        return Objects.equals(this.key, t.key);
	}

	@Override
	public int hashCode() {
		
		// TODO Auto-generated method stub
		int result = 21;
		result = result * 19 + key.hashCode();
		
		return result;
	}
    
    
    
}
