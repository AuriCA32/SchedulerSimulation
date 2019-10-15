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

    private final int[] prio_to_weight = new int[]{
        88761, 71755, 56483, 46273, 36291,
        29154, 23254, 18705, 14949, 11916,
        9548, 7620, 6100, 4904, 3906,
        3121, 2501, 1991, 1586, 1277,
        1024, 820, 655, 526, 423,
        335, 272, 215, 172, 137,
        110, 87, 70, 56, 45,
        36, 29, 23, 18, 15
        };

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
        Boolean add = active.enqueueTask(task);
        if (!add){
            return false;
        }
        nr_running++;
        task.setState(TASK_RUNNING);
        task.setCpu(cpu_id);
        return true;
    }

    // When the current process expires
    public void changeCurrentProcess(Task next_task){
        Boolean removed = active.dequeueTask(current);
        if (removed){
            expired.enqueue(current);
            current = next_task;
            nr_switches++;
            // TODO: asign expired_timestamp
            // check if best_expired_prio needs reasign
        }
    }

    // Exchange expired and active arrays
    public void exchangeArrays(){
        if (active.getNrActive()==0){
            PrioArray act = active.clone();
            active = expired;
            expired = act;
        }
    }

    // Current process goes to sleep
    public void sleepCurrentProcess(Task next_task){
        current.setState(TASK_SLEEP);
        Boolean removed = active.dequeueTask(current);
        if (removed){
            // TODO: enqueue in its I/O device queue
            current = next_task;
            nr_switches++;
        }
    }

    // Terminate process
    public void terminateCurrentProcess(Task next_task){
        if(current.getCurrExecT==current.getTotalExecT){
            Boolean removed = active.dequeueTask(current);
            if (removed){
                current.setState(TASK_TERMINATED);
                current = next_task;
                nr_switches++;
                nr_running--;
            }
        }
    }

    // Migrate process
    public void migrateProcess(Task process){
        Boolean removed = active.dequeueTask(process);
        if (removed){
            migration_queue.enqueue(process);
            active_balance = true;
        }
    }

    // Calculate cpu load
    public void calculateCpuLoad(){
        // Calculos libro
        // Asign cpu_load to it
    }    
}