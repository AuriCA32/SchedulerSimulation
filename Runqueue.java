import java.util.*;

public class PrioArray{

    // Tabla 3-2 del libro
    int tasks_active; // Number of process descriptors linked into the lists
    BitSet bitmap; // Priority bitmap -> 1 if the priority list is not empty
    ArrayList<LinkedList<ProcessDescriptor>> queue; // The 140 heads of the priority lists

    PrioArray(){
        this.bitmap = new BitSet(140);
        this.queue = new ArrayList<LinkedList<ProcessDescriptor>>(140);
        this.tasks_active=0;
    }

    // Getter tasks_active
    public int getTasksActive() {
        return tasks_active;
    }
    
    public void enqueue_task(ProcessDescriptor task){
        int priority = task.getPrio();
        // Check if reference is null to initialize list
        LinkedList<ProcessDescriptor> list = this.queue.get(priority);
        if (!list){
            list = new LinkedList<ProcessDescriptor>();
        }
        list.add(task);
        this.queue.get(priority) = list;
        this.bitmap.set(priority);
        tasks_active++;
    }

    public void dequeue_task(ProcessDescriptor task){
        int priority = task.getPrio();
        // Check if task was last on list
        LinkedList<ProcessDescriptor> list = this.queue.get(priority);
        list.remove(task);
        if (list.isEmpty()){
            // Change bitmap
            this.bitmap.set(priority, false);
        }
        tasks_active--;
    }

}

public class ProcessDescriptor{

    // Tabla 7-5 p.269
    int PID;
    int cpu; // Logical number of the CPU owning the runqueue to which the process belongs
    int state; // Estados posibles READY = 0, RUNNING = 1, BLOCKED = 2, TERMINATED = 3;
    int prio; // Priority -- TODO: definir si habra estatica y dinamica
    LinkedList<ProcessDescriptor> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    PrioArray prio_array; // PrioArray of the Runqueue that includes the process
    int sleep_avg; // Average sleep time of the process *
    int timestamp; // Time of last insertion of the process in the runqueue, or time of last process switch involving the process
    int last_ran; // Time of last process switch that replaced the process
    int time_slice; // Ticks left in the time quantum of the process
    int first_time_slice; // Flag, 1 if the process never exhausted its time quantum

    ProcessDescriptor(int PID, int prio){
        // El resto de los atributos se setean al asignarlo a un CPU
        this.PID=PID;
        this.prio=prio;
    }

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
    public void setState(int state) {
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

    // Getter run_list
    public LinkedList<ProcessDescriptor> getRunList(){
        return run_list;
    }

    // Setter run_list
    public void setRunList(ProcessDescriptor prev, ProcessDescriptor next){
        this.run_list.set(0, prev);
        this.run_list.set(1, next);
    }

    // Getter prio_array
    public int getPrioArray() {
        return prio_array;
    }

    // Setter prio_array
    public void setPrioArray(PrioArray prio_array) {
        this.prio_array = prio_array;
    }

    // Getter sleep_avg
    public int getSleepAvg() {
        return sleep_avg;
    }

    // Setter sleep_avg
    public void setSleepAvg(int sleep_avg) {
        this.sleep_avg = sleep_avg;
    }

    // Getter timestamp
    public int getTimestamp() {
        return timestamp;
    }

    // Setter timestamp
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    // Getter last_ran
    public int getLastRan() {
        return last_ran;
    }

    // Setter last_ran
    public void setLastRan(int last_ran) {
        this.last_ran = last_ran;
    }

    // Getter time_slice
    public int getTimeSlice() {
        return time_slice;
    }

    // Setter time_slace
    public void setTimeSlace(int time_slice) {
        this.time_slice = time_slice;
    }

    // Getter first_time_slice
    public int getFirstTimeSlice() {
        return first_time_slice;
    }

    // Setter first_time_slace
    public void setFirstTimeSlace(int first_time_slice) {
        this.first_time_slice = first_time_slice;
    }

}

public class Runqueue{

    // Atributos de la tabla 7-4 p.267
    int runnable_tasks_count; // Number of runnable processes in the runqueue lists
    int cpu_load; // CPU load factor based on the average number of processes in the runqueue
    int task_switches; // Number of process switches performed by the CPU
    int expired_timestamp; // Insertion time of the eldest process in the expired lists
    int timestamp_last_tick; // Timestamp value of the last timer interrupt
    ProcessDescriptor current;
    PrioArray active; // Active processes
    PrioArray expired; // Expired processes
    int best_expired_prio; // The best static priority (lowest value) among the expired processes
    int io_wait_tasks_count; // Number of processes that are now waiting for a disk I/O operation to complete
    // struct sched_domain sd;
    Boolean active_balance; // Flag set if some process shall be migrated from this runqueue to another (runqueue balancing)
    // TODO: atributos para manejar IO y blocked processes -> puede ser como en schedule entity

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
    public int getRunnableTasksCount() {
        return runnable_tasks_count;
    }

    // Getter cpu_load
    public int getCpuLoad() {
        return cpu_load;
    }

    // Getter task_switches
    public int getTaskSwitches() {
        return task_switches;
    }

    // Getter expired_timestamp
    public int getExpiredTimestamp() {
        return expired_timestamp;
    }

    // Getter timestamp_last_tick
    public int getTimestampLastTick() {
        return timestamp_last_tick;
    }

    // Getter current
    public ProcessDescriptor getCurrent() {
        return current;
    }

    // Getter active
    public PrioArray getActive() {
        return active;
    }

    // Getter expired
    public PrioArray getExpired() {
        return expired;
    }

    // Getter best_expired_prio
    public int getBestExpiredPrio() {
        return best_expired_prio;
    }

    // Getter io_wait_tasks_count
    public int getIOWaitTasksCount() {
        return io_wait_tasks_count;
    }

    // Getter active_balance
    public Boolean getActiveBalance() {
        return active_balance;
    }

}