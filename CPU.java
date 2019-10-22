import java.util.*;

public class CPU{

    RbTree tree;
    Runqueue runqueue;
    Clock clock;
    int quantum;
    Boolean RESCHED;
    ProcessTable table;
    public HashMap<Integer, LinkedList<Task>> io_queues;

    CPU(int id, Clock cl, int q, ProcessTable t){
        tree = new RbTree();
        runqueue = new Runqueue(id, q);
        clock = cl;
        RESCHED = false;
        quantum = q;
        table = t;
        io_queues = new HashMap<Integer, LinkedList<Task>>();
    }

    // scheduler tick
    public void schedulerTick(){
        runqueue.setTimestampLastTick(clock.getTotalTicks());
        // Check if rescheduling is needed
        Task curr = runqueue.getCurrent();
        if (curr.getTimeSlice()<1 | curr.hasTerminated()){
            RESCHED = true;
        }
        int io = curr.neededIO();
        if(io!=-1){
            schedule();
            return;
        }
        // rebalance_tick
    }

    // insert process
    public void insertTask(int pid){
        Task t = table.getTask(pid);
        tree.insert(t.getDynamicPrio(), pid);
        runqueue.addNewTask(t);
    }

    // delete process after termination (rbtree, table)
    public void deleteTask(Task t){
        table.removeTask(t.getPid());
        tree.delete(t.getDynamicPrio(), pid);
    }
    
    // migrate process from runqueue migration list
    // acceder al migration list desde afuera es synchronized
    
    // sleep current process and wake current process functions
    // runqueue handles sleep and wake, but we still have
    // to add and remove from rbtree
    public void wakeTask(Task task, LinkedList<Task> io_device_queue){
        runqueue.wakeProcess(task, io_device_queue);
        tree.insert(task.getDynamicPrio(), task.getPid());
    }

    public void sleepTask(Task task, LinkedList<Task> io_device_queue){
        int next_pid = tree.minimum_pid();
        Task next = table.getTask(next_pid);
        runqueue.sleepCurrentProcess(next, io_device_queue);
        tree.delete(task.getDynamicPrio(), task.getPid());
    }

    // recalc task prio p.275
    
    // schedule
    public void schedule(){
        int next_pid = tree.minimum_pid();
        Task next = table.getTask(next_pid);
        Task prev = runqueue.getCurrent();
        if (prev.hasTerminated()){
            runqueue.terminateCurrentProcess(next);
            deleteTask(prev);
        }else{
            int io = prev.neededIO();
            if(io!=-1){
                // Check io queues for key THIS IS FOR WAKE
                // for (Integer dispositivo: this.io_queues.keySet()){
                //     LinkedList<Task> queue = this.io_queues.get(dispositivo);
                //     for (Task t: queue){
                        
                //     }   
                // }
                LinkedList<Task> list = io_queues.get(io);
                sleepTask(prev, list);
            }else{
                runqueue.changeCurrentProcess(next);
            }
        }
        RESCHED = false;
    }
    
    // load_balance, rebalance_tick, p.287

}