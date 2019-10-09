import java.util.*;

public class PCB {

    int PID, curr_exec_t, total_execution_t, system_arrival_t;
    // nro dispositivo, [en qué momento de su ejecución usa el dispositivo, total ciclos e/s, current ciclos e/s]
    // esto asumiendo que tenemos mas de un dispositivo de e/s
    HashMap<Integer, Integer[]> io_operations;

    public PCB(int PID, int curr_exec_t, int total_execution_t, int system_arrival_t,
        HashMap<Integer, Integer[]> io_operations){
        this.PID = PID;
        this.curr_exec_t = curr_exec_t;
        this.total_execution_t = total_execution_t;
        this.system_arrival_t = system_arrival_t;
        this.io_operations = io_operations;
    }

    // Getter PID
    public int getPID() {
        return PID;
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

    public void toString(){
        String s="";
        s+="Process: "+Integer.toString(this.PID)+"\n"
        s+="\tCurrent execution time: "Integer.toString(this.curr_exec_t)+"\n"
        s+="\tTotal execution time: "Integer.toString(this.total_execution_t)+"\n"
        s+="\tSystem arrival time: "Integer.toString(this.system_arrival_t)+"\n"
        s+="\tI/O Operations: "this.io_operations.toString()+"\n"
        return s;
    }

}