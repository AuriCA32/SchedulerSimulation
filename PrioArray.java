import java.util.*;

public class PrioArray{

    private int nr_active; // Number of process descriptors linked into the lists
    private BitSet bitmap; // Priority bitmap -> 1 if the priority list is not empty
    private LinkedList<Task> [] queue ; // The 140 heads of the priority lists

    PrioArray(){
        this.bitmap = new BitSet(140);
        this.queue =  new LinkedList[140];
        nr_active = 0; 
    }

    public void setBit(int prio){
        this.bitmap.set(prio);
    }

    public void enqueueTask(Task p){
        int priority = p.getPrio();
        LinkedList<Task> list = this.queue[priority];
        if (!list){ // Not initialized
            list = new LinkedList<Task>();
            this.queue[priority] = list;
        }
        list.add(p);        
        setBit(priority);
        this.nr_active++;
        p.setArray(this);
    }

    public void dequeue_task(Task p){
        int priority = p.getPrio();
        // Check if task was last on list
        LinkedList<Task> list = this.queue[priority];
        list.remove(p);
        if (list.isEmpty()){
            this.bitmap.set(priority, false);
        }
        nr_active--;
    }

}