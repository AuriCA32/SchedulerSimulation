import java.util.*;


public class Runqueue{

    // Atributos de la tabla 7-4 p.267

    public long nr_running; 
    public long cp_load; 
    public long nr_switches; 

    public long timestamp_last_tick;
    public Task curr; 
    public PrioArray active; 
    public PrioArray expired; 
    public PrioArray [] arrays; 
    public int nr_iowait; 
    public int active_balance; 

    public Task migration_thread; 
    public LinkedList<Task> migration_queue; 

    Runqueue(){
        
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