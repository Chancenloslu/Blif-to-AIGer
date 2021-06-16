package Nodes;

import java.util.Hashtable;
import java.util.Objects;

public class Node {

	private Term input1;
	private Term input2;
	private Term output;
	
	public static Hashtable<String, Node> nodeLookUpTable = new Hashtable<>();
	
	public Node(Term input1, Term input2, Term output) {
		this.input1=input1;
		this.input2=input2;
		this.output=output;
	}
	public Term getInput1() {
		return input1;
	}
	public void setInput1(Term input1) {
		this.input1 = input1;
	}
	public Term getInput2() {
		return input2;
	}
	public void setInput2(Term input2) {
		this.input2 = input2;
	}
	public Term getOutput() {
		return output;
	}
	public void setOutput(Term output) {
		this.output = output;
	}
	
	/**
	 * combination of the term name
	 * @param input1
	 * @param input2
	 * @return a new combined Term
	 */
	public Term Output(Term input1, Term input2) {
		
		String nameOfNewTerm = input1.termName + input2.termName;
		boolean valueOfNewTerm = input1.termValue && input2.termValue;
		Term t = new Term(nameOfNewTerm, valueOfNewTerm);
//		char[] name1=input1.termName;
//		int len1=name1.length;
//		char[] name2=input2.termName;
//		int len2=name2.length;
//		boolean value1=input1.termValue;
//		boolean value2=input2.termValue;
//		char[] nameReturn = null;
//		int index=0;
		
		return t;
	}
	
	public Node AndOp(Node n1, Node n2) {
		if(n1.equals(n2)) return n1;
		if()
		return n2;
	}
	
	//TODO: check duplicate in term names
	public Node createNode(Node n1, Node n2) {
		Term in1 = n1.output;
		Term in2 = n2.output;
		Term out = Output(in1, in2);
		Node n = new Node(in1, in2, out);
		nodeLookUpTable.put(, null);
		return n;
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
        Node n = (Node) obj;

        // check that the fields in the parameter match the fields in the object.
        return Objects.equals(input1,n.input1) && Objects.equals(input2,n.input2);
	}
	
	@Override
	/**
	 * but if the number of nodes is too big, we should override a stronger hashcode() method;
	 */
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = result * 31 + input1.hashCode();
		result = result * 31 + input2.hashCode();
		
		return result;
	}
	
	
	
	public static void main(String[] args) {
		Term in1 = new Term("a", true);
		Term in2 = new Term("b", true);
		Term in3 = new Term("c", true);
		Node n1 = new Node(in3, in2, null);
		Node n2 = new Node(in3, in2, null);
		System.out.println(n1.equals(n2));
	}
}
