import java.util.*;

public class PrioArray{

    private int nr_active; // Number of process descriptors linked into the lists
    private BitSet bitmap; // Priority bitmap -> 1 if the priority list is not empty
    private LinkedList<Task> [] queue ; // The 140 heads of the priority lists

    PrioArray(){
        this.bitmap = new BitSet(140);
        this.queue =  new LinkedList[140];
        this.nr_active = 0; 
    }

    public void setBit(int prio){
        this.bitmap.set(prio);
    }

    public void enqueueTask(Task p){
        int priority = p.getPrio();
        LinkedList<Task> list = this.queue[priority];
        if (list==null){ // Not initialized
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

    public String listToString(int priority){
        String s="";
        LinkedList<Task> list = this.queue[priority];
        if(list!=null){
            for (Task task:list){
                s+=task.toString()+"\n";
            }
        }
        return s;
    }

    public String toString(){
        String s="";
        s+="Active processes: "+Integer.toString(this.nr_active)+"\n";
        s+="Bitmap: "+bitmap.toString()+"\n";
        s+="Lists:";
        for(int i=0; i<this.queue.length; i++){
            if (this.queue[i]!=null){
                s+="\tPriority "+this.listToString(i)+"\n";
            }
        }
        return s;
    }

}