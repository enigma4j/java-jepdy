package enigma4j.jepdy.model;

import java.util.Comparator;

public class ClueComparator implements Comparator<Clue> {

    @Override
        public int compare(Clue o1, Clue o2) {

            if(o1==null && o2==null) return 0;
            if(o1==null) return 1;
            if(o2==null) return -1;

            int vcomp=Integer.compare(o1.value,o2.value);
            if(vcomp!=0) return vcomp;
            return o1.clue.compareTo(o2.clue);

        }



}
