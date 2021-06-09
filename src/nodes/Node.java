package nodes;

public class Node {
    public Node father;
    public Node leftChild;
    public Node rightChild;
    public char[] key;
    public boolean single;     //if it is final state(key=1), stop separating it

    /**
     * middle node
     * @param father
     * @param leftChild
     * @param rightChild
     * @param key
     * @param single
     */
    public Node(Node father, Node leftChild, Node rightChild, char[] key, boolean single) {
        this.father = father;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.key = key;
        this.single=single;
    }

    /**
     * default first node
     * @param key
     */
    public Node(char[] key){
        this.key=key;
        this.father=null;
        this.single=false;
        this.leftChild=null;
        this.rightChild=null;
    }

}
