import java.util.*;

public class ProcessTable {
    private HashMap<Integer, PCB> table; // <PID, PCB>

    public synchronized PCB getProcess(int PID){
        return table.get(PID);
    }

    public synchronized Boolean addProcess(int PID, PCB process){
        if (!table.get(PID)){
            return false;
        }
        table.put(PID, process);
        return true;
    }

    public synchronized Boolean removeProcess(int PID){
        PCB process = table.remove(PID);
        if (process!=null) return true;
        return false;
    }

    public synchronized void print(){
        String s="";
        table.values().forEach(pcb -> s+=pcb.toString()+"\n");
        System.out.println(s);
    }
}