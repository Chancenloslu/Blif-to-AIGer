package Nodes;

import java.util.Objects;

/**
 * Terms represents the terms like "abc" or "abc'", which are used as inputs and outputs in Node
 * @author chenl
 *
 */
public class Term {
	public String termName;
	public boolean termValue;
	
	public Term(String termName, boolean termValue) {
		super();
		this.termName = termName;
		this.termValue = termValue;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) return false;
		// if it is this object itself
        if(obj == this) return true;
        // check if the type is right
        if(!(obj instanceof Term)) return false;

        // transfer to Node
        Term t = (Term) obj;

        // check that the fields in the parameter match the fields in the object.
        return Objects.equals(termName,t.termName) && Objects.equals(termValue,t.termValue);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 21;
		result = result * 19 + termName.hashCode();
		// true:result + 1, false:result + 0
		if(termValue) result = result * 19 + 1;
				
		return result;
	}

}
