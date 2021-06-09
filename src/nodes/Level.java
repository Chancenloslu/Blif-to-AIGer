package nodes;

import java.util.ArrayList;
import java.util.HashSet;

public class Level {
    //private HashSet<Node> unduplicatedLevel;
    public ArrayList<Node> duplicatedLevel;

//    public Level(HashSet<Node> unduplicatedLevel) {
//        this.unduplicatedLevel = unduplicatedLevel;
 //   }

    public Level(ArrayList<Node> duplicatedLevel) {
        this.duplicatedLevel = duplicatedLevel;
    }

}
