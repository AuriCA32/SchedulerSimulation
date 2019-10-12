public class ProcessorScheduleEntity{
    
    public RbTree tasks_timeline;
    public RbTree.RbNode rb_leftmost;
    int min_vruntime, id;
    Runqueue rq;

    ProcessorScheduleEntity(int id){
        this.tasks_timeline = new RbTree();
        this.id=id;
        this.rq = new Runqueue(id);
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