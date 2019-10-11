import java.util.*;

public class ProcessTable {
    private HashMap<Integer, Task> table; // <PID, PCB>

    ProcessTable(){
        this.table = new HashMap<Integer,Task>();
    }

    public synchronized Task getTask(int pid){
        return table.get(pid);
    }

    public synchronized Boolean addTask(Task p){
        int pid = p.getPid();
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

    public synchronized String toString(){
        String s="";
        for (Integer pid: this.table.keySet()){
            s+="PID: "+Integer.toString(pid)+ ", " +this.table.get(pid).toString()+"\n";
        }
        return s;
    }
}