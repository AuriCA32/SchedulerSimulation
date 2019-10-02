import java.util.*;

public class ProcessTable {
    private HashMap<Integer, PCB> table; // <PID, PCB>

    public synchronized PCB getProcess(int PID){ //No estoy segura si debe sere synchronized
        return table.get(PID);
    }

    public synchronized void addProcess(int PID, PCB process){
        table.put(PID, process);
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