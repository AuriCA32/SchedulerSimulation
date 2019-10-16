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

    private final int[] prio_to_weight = new int[]{ // Luego se puede cambiar a un arreglo compartido
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

    // Getter timestamp_last_tick
    public int getTimestampLastTick() {
        return timestamp_last_tick;
    }

    // Setter timestamp_last_tick
    public void setTimestampLastTick(int last_tick) {
        timestamp_last_tick = last_tick;
    }

    // Getter current
    public Task getCurrent() {
        return current;
    }

    // Getter migration_queue
    public LinkedList<Task> getMigrationQueue() {
        return migration_queue;
    }

    public Boolean addNewTask(Task task){
        Boolean add = active.enqueueTask(task);
        if (!add){
            return false;
        }
        nr_running++;
        addLoad(task.getPrio());
        task.setState(TASK_RUNNING);
        task.setCpu(cpu_id);
        return true;
    }

    // When the current process expires
    public Boolean changeCurrentProcess(Task next_task){
        Boolean removed = active.dequeueTask(current);
        Boolean add = false; // Abs &
        if (removed){
            add = expired.enqueueTask(current);
            current = next_task;
            nr_switches++;
            // TODO: asign expired_timestamp
            // check if best_expired_prio needs reasign
        }
        return removed && add;
    }

    // Exchange expired and active arrays
    public void exchangeArrays(){
        if (active.getNrActive()==0){
            PrioArray act = active.clone();
            active = expired;
            expired = act;
            // Recalculate cpu load
            LinkedList<Task> [] queue = active.getQueue();
            for(LinkedList<Task> list : queue){
                for(Task t : list){
                    addLoad(t.getPrio());
                }
            }
        }
    }

    // Wake task
    public Boolean wakeProcess(Task next_task, LinkedList<Task> io_device_queue){
        Boolean removed = io_device_queue.remove(next_task);
        Boolean add = false;
        if (removed){
            add = active.enqueueTask(next_task);
            if (add){
                next_task.setState(TASK_RUNNING);
                nr_sleep--;
            }
        }
        return add && removed;
    }

    // Current process goes to sleep
    public Boolean sleepCurrentProcess(Task next_task, LinkedList<Task> io_device_queue){
        Boolean removed = active.dequeueTask(current);
        Boolean add = false;
        if (removed){
            add = io_device_queue.add(current);
            if(add){
                current.setState(TASK_SLEEP);
                current = next_task;
                nr_switches++;
                nr_sleep++;
            }
        }
        return removed && add;
    }

    // Terminate process
    public Boolean terminateCurrentProcess(Task next_task){
        if(current.getCurrExecT()==current.getTotalExecT()){
            Boolean removed = active.dequeueTask(current);
            if (removed){
                removeLoad(current.getPrio());
                current.setState(TASK_TERMINATED);
                current = next_task;
                nr_switches++;
                nr_running--;
            }
            return removed;
        }
        return false;
    }

    // Migrate process
    public void migrateProcess(Task process){
        Boolean removed = active.dequeueTask(process);
        if (removed){
            migration_queue.add(process);
            removeLoad(current.getPrio());
            active_balance = true;
        }
    }

    // Add load to cpu
    public void addLoad(int task_prio){
        cpu_load += prio_to_weight[task_prio];
    }

    // Calculate cpu load
    public void removeLoad(int task_prio){
        cpu_load -= prio_to_weight[task_prio];
    }

    // Calculate current task cpu percentage usage
    public int currentUsage(int task_prio){
        return prio_to_weight[task_prio]/cpu_load;
    }

    public String toString(){
        String s="";
        s+="ID: "+Integer.toString(cpu_id)+"\n";
        s+="Runnable process: "+Integer.toString(nr_running)+"\n";
        s+="Load: "+Integer.toString(cpu_load)+"\n";
        s+="Process switches: "+Integer.toString(nr_switches)+"\n";
        s+="Process asleep: "+Integer.toString(nr_sleep)+"\n";
        s+="Expired timestamp: "+Integer.toString(expired_timestamp)+"\n";
        s+="Timestamp last tick: "+Integer.toString(timestamp_last_tick)+"\n";
        s+="Current task: "+current.toString()+"\n";
        s+="Active array: "+active.toString()+"\n";
        s+="Expired array: "+expired.toString()+"\n";
        s+="Best Expired Prio: "+Integer.toString(best_expired_prio)+"\n";
        s+="Active Balance: "+active_balance+"\n";
        s+="Migration Queue: \n";
        for(Task mt : migration_queue){
            s+="\tTask "+mt.toString()+"\n";
        }
        return s;
    }

}