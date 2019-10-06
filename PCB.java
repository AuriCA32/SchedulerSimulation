import java.util.*;

public class PCB {

    // Estados posibles
    // private final int READY = 0; 
    // private final int RUNNING = 1;
    // private final int BLOCKED = 2;
    // private final int TERMINATED = 3;

    int PID, state, priority, curr_exec_t, total_execution_t, system_arrival_t;
    int  curr_cpu_cycles, total_cpu_cycles;
    // nro dispositivo, [en qué momento de su ejecución usa el dispositivo, total ciclos e/s, current ciclos e/s]
    // esto asumiendo que tenemos mas de un dispositivo de e/s
    HashMap<Integer, Integer[]> io_operations;

    public PCB(int PID, int state, int priority, int curr_exec_t, int total_execution_t, int system_arrival_t,
        int curr_cpu_cycles, int total_cpu_cycles, HashMap<Integer, Integer[]> io_operations){
        this.PID = PID;
        this.state = state;
        this.priority = priority;
        this.curr_exec_t = curr_exec_t;
        this.total_execution_t = total_execution_t;
        this.system_arrival_t = system_arrival_t;
        this.curr_cpu_cycles = curr_cpu_cycles;
        this.total_cpu_cycles = total_cpu_cycles;
        this.io_operations = io_operations;
    }

    // Getter PID
    public int getPID() {
        return PID;
    }

    // Getter state
    public int getState() {
        return state;
    }

    // Setter state
    public void setState(int state) {
        this.state = state;
        // TODO: chequear que este entre los estados posibles
    }

    // Getter priority
    public int getPriority() {
        return priority;
    }

    // Getter current execution time
    public int getCurrentExecT() {
        return curr_exec_t;
    }

    // Setter current execution time
    public void setCurrentExecT(int time) {
        this.curr_exec_t = time;
    }

    // Getter total execution time
    public int getTotalExecT() {
        return total_execution_t;
    }

    // Getter system arrival time
    public int getSystemArrivalT() {
        return system_arrival_t;
    }

    // Getter current cpu cycles
    public int getCurrentCPUCycles() {
        return curr_cpu_cycles;
    }

    // Setter current cpu cycles
    public void setCurrentCPUCycles(int time) {
        this.curr_cpu_cycles = time;
    }

    // Getter total cpu cycles
    public int getTotalCPUCycles() {
        return total_cpu_cycles;
    }

    public void toString(){
        String s="";
        s+="Process: "+Integer.toString(this.PID)
        s+="\tState: "Integer.toString(this.state)+"\n"
        s+="\tPriority: "Integer.toString(this.priority)+"\n"
        s+="\tCurrent execution time: "Integer.toString(this.curr_exec_t)+"\n"
        s+="\tTotal execution time: "Integer.toString(this.total_execution_t)+"\n"
        s+="\tSystem arrival time: "Integer.toString(this.system_arrival_t)+"\n"
        s+="\tCurrent CPU cycles: "Integer.toString(this.curr_cpu_cycles)+"\n"
        s+="\tTotal CPU cycles: "Integer.toString(this.total_cpu_cycles)+"\n"
        s+="\tI/O Operations: "this.io_operations.toString()+"\n"
        return s;
        // System.out.println(s);
    }

}