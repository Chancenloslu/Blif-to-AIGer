package nodes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class AIG {
    private LinkedList<Level> aig;

    /**
     * constructor
     * @param aig
     */
    public AIG(LinkedList<Level> aig){
        this.aig=aig;
    }
    /**
     * separate the levels
     */
    public void separateLevels(Level firstLevel){
        int index=0;
        while(index<firstLevel.duplicatedLevel.size()){
            Node n=firstLevel.duplicatedLevel.get(index);
            while (n.key.length!=1){
                for(int i=index+1;i<firstLevel.duplicatedLevel.size();i++){
                    Node nCmp=firstLevel.duplicatedLevel.get(i);
                    
                }
            }
        }

    }
}
