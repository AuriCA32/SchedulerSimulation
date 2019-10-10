// Based on struct task_struck 
import java.util.*;

public class Task{

    private final int TASK_RUNNING = 1;
    private final int TASK_SLEEP = 2; // Bloqueado
    private final int TASK_TERMINATED = 3;

    // private final int PIDTYPE_MAX = 4;
    
    public int pid; 

    /* -1 unrunnable, 0 runnable, >0 stopped;  */
    public int state;
    /* SMP configuration members  */
    
    /* Current CPU  */
    public int cpu; // Logical number of the CPU owning the runqueue to which the process belongs

    // public Task last_wakee;

    /* This members allow for quick access to recently used cpus */
    public int recent_used_cpu; 
    // public int wake_cpu;

    /* End of SMP config */

    public int prio; // Priority -- TODO: definir si habra estatica y dinamica

    // public int policy;
    // LinkedList<> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    public PrioArray prio_array; // PrioArray that includes the process    
    public int time_slice; // Ticks left in the time quantum of the process
    public int first_time_slice; // Flag, 1 if the process never exhausted its time quantum

    /* Parent-child and siblings relations */
    // public Task real_parent;
    // public Task parent;
    // public LinkedList<Task> children;
    // public LinkedList<Task> sibling;
    // public Task group_leader;

    /* PID hash tables */
    // HashMap<Integer,Task> [] pid_links = new HashMap[PIDTYPE_MAX];

    public float curr_exec_t; 
    public float total_execution_t;
    public float system_arrival_t;

    // public Signal signals; 

    // Getter cpu
    public int getCpu() {
        return cpu;
    }

    // Setter cpu
    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    // Getter state
    public int getState() {
        return state;
    }

    // Setter state
    public void setState(long  state) {
        this.state = state;
    }

    // Getter prio
    public int getPrio() {
        return prio;
    }

    // Setter prio
    public void setPrio(int prio) {
        this.prio = prio;
    }

    public void setArray(PrioArray array){
        this.prio_array = array; 
    }

}