import java.util.*;

public class PrioArray{

    int tasks_active; // Number of process descriptors linked into the lists
    BitSet bitmap; // Priority bitmap -> 1 if the priority list is not empty
    ArrayList queue; // The 140 heads of the priority lists

    PrioArray(){
        this.bitmap = new BitSet(140);
        this.queue = new ArrayList(140);
    }

    // Faltan las funciones enqueue task y dequeue task de acuerdo a la prioridad
    // essentially:
    // list_add_tail(&p->run_list, &array->queue[p->prio]);
    // __set_bit(p->prio, array->bitmap);
    // array->nr_active++;
    // p->array = array;

    // Faltan las funciones que modifiquen el bitmap

}

public class ProcessDescriptor{

    int cpu; // Logical number of the CPU owning the runqueue to which the process belongs
    int state; // Estados posibles READY = 0, RUNNING = 1, BLOCKED = 2, TERMINATED = 3;
    int prio; // Priority -- TODO: definir si habra estatica y dinamica
    // LinkedList<> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    PrioArray prio_array; // PrioArray that includes the process
    int time_slice; // Ticks left in the time quantum of the process
    int first_time_slice; // Flag, 1 if the process never exhausted its time quantum

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