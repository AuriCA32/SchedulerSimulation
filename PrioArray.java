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

    PrioArray(BitSet bitmap, LinkedList<Task>[] queue, int nr_active){
        this.bitmap = bitmap;
        this.queue =  queue;
        this.nr_active = nr_active;
    }

    public PrioArray clone(){
        BitSet bitmap_clone = (BitSet) bitmap.clone();
        LinkedList<Task> [] queue_clone =  queue.clone();
        return new PrioArray(bitmap_clone, queue_clone, 0);
    }

    public int getNrActive(){
        return nr_active;
    }

    public BitSet getBitmap(){
        return bitmap;
    }

    public LinkedList<Task>[] getQueue(){
        return queue;
    }

    public Boolean enqueueTask(Task p){
        int priority = p.getPrio();
        LinkedList<Task> list = this.queue[priority];
        if (list==null){ // Not initialized
            list = new LinkedList<Task>();
            this.queue[priority] = list;
        }
        if (list.contains(p)){
            return false;
        }
        list.add(p);        
        this.bitmap.set(priority);
        this.nr_active++;
        p.setArray(this);
        return true;
    }

    public Boolean dequeueTask(Task p){
        int priority = p.getPrio();
        // Check if task was last on list
        LinkedList<Task> list = this.queue[priority];
        Boolean removed = list.remove(p);
        if (!removed){
            return removed;
        }
        if (list.isEmpty()){
            this.bitmap.set(priority, false);
        }
        nr_active--;
        p.setArray(null);
        return removed;
    }

    public String listToString(int priority){
        String s="";
        LinkedList<Task> list = this.queue[priority];
        if(list!=null || !list.isEmpty()){
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
        s+="Lists:\n";
        for(int i=0; i<this.queue.length; i++){
            if (this.queue[i]!=null && !this.queue[i].isEmpty()){
                s+="\tPriority "+i+".\n\t"+this.listToString(i)+"\n";
            }
        }
        return s;
    }

}