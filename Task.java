// Based on struct task_struck 
import java.util.*;

public class Task{

    public int pid;

    public int state;
    /* SMP configuration members  */
    
    /* Current CPU  */
    public int cpu; // Logical number of the CPU owning the runqueue to which the process belongs

    // public Task last_wakee;

    /* This members allow for quick access to recently used cpus */
    public int recent_used_cpu; 
    // public int wake_cpu;

    /* End of SMP config */

    public int static_prio; // Static priority
    public int dynamic_prio; // Dynamic priority

    // LinkedList<> run_list; // Pointers to the next and prev elements in the runqueue list to which the process belongs
    public PrioArray prio_array; // PrioArray that includes the process    
    public int time_slice; // Ticks left in the time quantum of the process
    public int first_time_slice; // Flag, 1 if the process never exhausted its time quantum

    public int curr_exec_t; 
    public int total_execution_t;
    public int system_arrival_t;

    // <nro dispositivo, [en qué momento de su ejecución usa el dispositivo, total ciclos e/s, current ciclos e/s]>
    // esto asumiendo que tenemos mas de un dispositivo de e/s
    HashMap<Integer, Integer[]> io_operations;

    Task(int pid, int prio, int total_execution_t, int system_arrival_t, HashMap<Integer, Integer[]> io_operations){
        this.pid=pid;
        this.static_prio=prio;
        this.dynamic_prio=prio;
        this.state=-1; // State new
        this.curr_exec_t=0;
        this.total_execution_t=total_execution_t;
        this.system_arrival_t=system_arrival_t;
        this.io_operations=io_operations;
    }

    // Getter cpu
    public int getPid() {
        return pid;
    }

    // Setter cpu
    public void setPid(int cpu) {
        this.pid = pid;
    }

    // Getter cpu
    public int getCpu() {
        return cpu;
    }

    // Setter cpu
    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    // Getter state
    public int getState() {
        return state;
    }

    // Setter state
    public void setState(int  state) {
        this.state = state;
    }

    // Getter prio
    public int getStaticPrio() {
        return static_prio;
    }

    // Setter prio
    public void setStaticPrio(int prio) {
        this.static_prio = prio;
    }

    // Getter prio
    public int getDynamicPrio() {
        return dynamic_prio;
    }

    // Setter prio
    public void setDynamicPrio(int prio) {
        this.dynamic_prio = prio;
    }

    public PrioArray getArray(){
        return prio_array;
    }

    public void setArray(PrioArray array){
        this.prio_array = array; 
    }

    public int getCurrExecT(){
        return curr_exec_t;
    }

    public void setCurrExecT(int curr_exec_t){
        this.curr_exec_t = curr_exec_t;
    }

    public int getTotalExecT(){
        return total_execution_t;
    }

    public int getSystemArrivalT(){
        return system_arrival_t;
    }

    public int getTimeSlice(){
        return time_slice;
    }

    public void setTimeSlice(int ts){
        time_slice = ts;
    }

    public void decrementTimeSlice(){
        time_slice--;
    }

    public String toString(){
        String s="";
        s+="Task: "+Integer.toString(this.pid)+"\n";
        s+="\tPriority: "+Integer.toString(this.prio)+"\n";
        s+="\tState: "+Integer.toString(this.state)+"\n";
        s+="\tCPU: "+Integer.toString(this.cpu)+"\n";
        s+="\tCurrent execution time: "+Integer.toString(this.curr_exec_t)+"\n";
        s+="\tTotal execution time: "+Integer.toString(this.total_execution_t)+"\n";
        s+="\tSystem arrival time: "+Integer.toString(this.system_arrival_t)+"\n";
        s+="\tI/O Operations:\n";
        for (Integer dispositivo: this.io_operations.keySet()){
            s+="\t\t<Device "+Integer.toString(dispositivo)+ ", " +Arrays.toString(this.io_operations.get(dispositivo))+">\n";
        }
        return s;
    }

}