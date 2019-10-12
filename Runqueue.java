import java.util.*;

public class Runqueue{

    // Atributos de la tabla 7-4 p.267
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

    Runqueue(){
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

    // Add new process

    // Process expires

    // Exchange expired and active arrays

    // Terminate/remove process
    // Migrate process
    // Calculate cpu load
    
}