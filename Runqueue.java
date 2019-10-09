import java.util.*;



public class Runqueue{

    // Atributos de la tabla 7-4 p.267
    int runnable_tasks_count; // Number of runnable processes in the runqueue lists
    int cpu_load; // CPU load factor based on the average number of processes in the runqueue
    int task_switches; // Number of process switches performed by the CPU
    int expired_timestamp; // Insertion time of the eldest process in the expired lists
    int timestamp_last_tick; // Timestamp value of the last timer interrupt
    Task current;
    PrioArray active; // Active processes
    PrioArray expired; // Expired processes
    int best_expired_prio; // The best static priority (lowest value) among the expired processes
    int io_wait_tasks_count; // Number of processes that are now waiting for a disk I/O operation to complete
    // struct sched_domain sd;
    Boolean active_balance; // Flag set if some process shall be migrated from this runqueue to another (runqueue balancing)

    Runqueue(){
        this.runnable_tasks_count = 0;
        this.cpu_load = 0;
        this.task_switches = 0;
        this.expired_timestamp = -1;
        this.timestamp_last_tick = -1;
        this.current = null;
        this.active = new PrioArray();
        this.expired = new PrioArray();
        this.best_expired_prio = -1;
        this.io_wait_tasks_count = 0;
        this.active_balance = false;
    }

    // Getter runnable_tasks_count
    
}