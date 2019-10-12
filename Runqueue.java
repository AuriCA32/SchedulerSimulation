import java.util.*;

public class Runqueue{

    // Atributos de la tabla 7-4 p.267
    int cpu_id; // ID of cpu
    int nr_running; // Number of runnable processes in the runqueue lists
    int cpu_load; // CPU load factor based on the average number of processes in the runqueue
    int nr_switches; // Number of process switches
    int nr_sleep; // Number of process that are waiting for IO (not in any prio array)
    int expired_timestamp; // Insertion time of the eldest process in the expired lists
    int timestamp_last_tick; // Timestamp value of the last timer interrupt
    Task current; // Current task
    PrioArray active; // Active processes
    PrioArray expired; // Expired processes
    int best_expired_prio; // The best static priority (lowest value) among the expired processes
    // struct sched_domain sd;
    Boolean active_balance; // Flag set if some process shall be migrated from this runqueue to another (runqueue balancing)
    LinkedList<Task> migration_queue; // List of process to be removed from the runqueue

    private final int TASK_RUNNING = 0; // Runnable or running
    private final int TASK_SLEEP = 1; // Bloqueado
    private final int TASK_TERMINATED = 2;

    Runqueue(int cpu){
        this.cpu_id = cpu;
        this.nr_running = 0;
        this.cpu_load = 0;
        this.nr_switches = 0;
        this.nr_sleep = 0;
        this.expired_timestamp = -1;
        this.timestamp_last_tick = -1;
        this.current = null;
        this.active = new PrioArray();
        this.expired = new PrioArray();
        this.best_expired_prio = -1;
        this.active_balance = false;
        this.migration_queue = new LinkedList<Task>();
    }

    // Getter cpu_load
    public int getCpuLoad() {
        return cpu_load;
    }

    // Getter migration_queue
    public LinkedList<Task> getCpuLoad() {
        return migration_queue;
    }

    public Boolean addNewTask(Task task){
        Boolean add = this.active.enqueueTask(task);
        if (!add){
            return false;
        }
        this.nr_running++;
        task.setState(TASK_RUNNING);
        task.setCpu(this.cpu_id);
        return true;
    }

    // When the current process expires
    public void changeCurrentProcess(Task next_task){
        // remove
        // enqueue in expired
        // change curr process prio array
        // current -> next_task
        // nr_switches++
        // asign expired_timestamp
        // check if best_expired_prio needs reasign
    }
    // Exchange expired and active arrays
    public void exchangeArrays(){
        // Check if active is empty
        // reasign
    }
    // Process goes to sleep
    // Terminate/remove process
    // Migrate process (add to list)
    // Calculate cpu load -> paper, libro
    // Change current
    // Balancing needed
    
}