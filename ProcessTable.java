import java.util.*;

public class ProcessTable {
    private HashMap<Integer, Task> table; // <PID, PCB>

    public synchronized Task getTask(int pid){
        return table.get(pid);
    }

    public synchronized Boolean addTask(int pid, Task p){
        if (table.get(pid)!= null){
            return false;
        }
        table.put(pid, p);
        return true;
    }

    public synchronized Boolean removeTask(int pid){
        Task process = table.remove(pid);
        if (process!=null) return true;
        return false;
    }

    public synchronized string toString(){
        String s="";
        table.values().forEach(pcb -> s+=pcb.toString()+"\n");
        return s;
    }
}