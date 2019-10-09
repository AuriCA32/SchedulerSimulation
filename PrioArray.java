import java.util.*;

public class PrioArray{

    private int nr_active; // Number of process descriptors linked into the lists
    private BitSet bitmap; // Priority bitmap -> 1 if the priority list is not empty
    private LinkedList<Task> [] queue ; // The 140 heads of the priority lists



    PrioArray(){
        this.bitmap = new BitSet(140);
        this.queue =  new LinkedList[140];
    }

    public void setBit(int prio){
        this.bitmap.set(prio);
    }

    public void enqueueTask(Task p){

        this.queue[p.getPrio()].add(p);
        setBit(p.getPrio());
        this.nr_active++;
        p.setArray(this);

    }

}