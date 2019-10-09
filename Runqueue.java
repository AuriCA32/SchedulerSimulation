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

    int cpu; // Logical number of the CPU owning the runqueue to which the process belongs
    int state; // Estados posibles READY = 0, RUNNING = 1, BLOCKED = 2, TERMINATED = 3;
    int prio; // Priority -- TODO: definir si habra estatica y dinamica
    // LinkedList<> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    // PrioArray prio_array; // PrioArray that includes the process
    int time_slice; // Ticks left in the time quantum of the process
    int first_time_slice; // Flag, 1 if the process never exhausted its time quantum

    ProcessDescriptor(int cpu, int prio){
        this.cpu=cpu;
        this.prio=prio;
    }

    // Faltan setters y getters

}

public class Runqueu{

    // Atributos de la tabla 7-4 p.267
    int runnable_tasks_count, cpu_load, timestamp_last_tick;
    Node current; // Deberia ser task_t
    // prio_array_t active, expired;
    // prio_array_t[2] arrays;
    // atomic_t io_wait_tasks;
    // struct sched_domain sd;
    // int active_balance si vamos a hacer runqueue balancing

    Runqueu(){
        
    }

    // Getter tasks_count
    public int getTasksCount() {
        return tasks_count;
    }

    // Getter running_tasks_count
    public int getRunningTasksCount() {
        return running_tasks_count;
    }

    // Getter min_vruntime
    public int getMinVRuntime() {
        return min_vruntime;
    }

    public void addNewProcess(int priority, int PID){
        tasks_timeline.insert(priority, PID);
        tasks_count++;
        running_tasks_count++;
        // Check if new process is minvruntime?
    }

    public Boolean blockProcess(int priority, int PID){
        Boolean deleted = tasks_timeline.delete(priority, PID);
        if(deleted){
            blocked.put(PID, priority);
            running_tasks_count--;
            // Agregar al priority queue del I/O device -> en el codigo del hilo del procesador
        }
        return deleted;
    }

    public void unblockProcess(int PID){
        int priority = blocked.remove(PID);
        tasks_timeline.insert(priority, PID);
        running_tasks_count++;
        // Check if new process is minvruntime?
    }

    public Boolean terminateProcess(){
        Boolean deleted = tasks_timeline.delete(priority, PID);
        if(deleted){
            tasks_count--;
            running_tasks_count--;
        }
        return deleted;
    }
}