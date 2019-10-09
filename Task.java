// Based on struct task_struck 
import java.util.*;

public class Task{

    private final int TASK_RUNNING = 1; 
    private final int TASK_INTERRUPTIBLE = 2; 
    private final int TASK_UNINTERRUPTIBLE = 3;
    private final int TASK_STOPPED = 4; 
    private final int EXIT_ZOMBIE = 5;
    private final int EXIT_DEAD = 6; 


    private final int PIDTYPE_MAX = 4;


    
    

    /* -1 unrunnable, 0 runnable, >0 stopped;  */
    private long state;  

    

    /* SMP configuration members  */
    
    /* Current CPU  */
    private int cpu; // Logical number of the CPU owning the runqueue to which the process belongs

    
    Task last_wakee;

    /* This members allow for quick access to recently used cpus */

    private int recent_used_cpu; 
    private int wake_cpu;

    /* End of SMP config */



    private int prio; // Priority -- TODO: definir si habra estatica y dinamica

    private int policy;
    // LinkedList<> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    private PrioArray prio_array; // PrioArray that includes the process    
    private int time_slice; // Ticks left in the time quantum of the process
    private int first_time_slice; // Flag, 1 if the process never exhausted its time quantum


    /* Parent-child and siblings relations */
    private Task real_parent; 
    private Task parent;
    private LinkedList<Task> children;
    private LinkedList<Task> sibling; 
    private Task group_leader; 

    /* PID hash tables */
    HashMap<Integer,Task> [] pid_links = new HashMap[PIDTYPE_MAX]; 


    private Signal signals; 


    public int getPrio(){
        return prio;
    }

    public void setArray(PrioArray array){
        this.prio_array = array; 
    }

}