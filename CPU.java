import java.util.*;

public class CPU{

    RbTree tree;
    Runqueue runqueue;
    Clock clock;
    int quantum;
    Boolean RESCHED;

    CPU(int id, Clock cl, int q){
        tree = new RbTree();
        runqueue = new Runqueue(id);
        clock = cl;
        RESCHED = false;
        quantum = q;
    }

    // scheduler tick
    void schedulerTick(){
        runqueue.setTimestampLastTick(clock.getTotalTicks());
        // Check if rescheduling is needed
        if (runqueue.getCurrent().getTimeSlice()<1 | runqueue.getCurrent().hasTerminated()){
            RESCHED = true;
        }
        // rebalance_tick
    }

    // insert process
    // delete process after termination (rbtree, table and runqueue)
    // migrate process from runqueue migration list
    
    // sleep current process and wake current process
    // runqueue handles sleep and wake, but we still have
    // to add and remove from rbtree

    // recalc task prio p.275
    
    // schedule
    void schedule(){
        int next_pid = tree.minimum_pid();
        Task next = getTask(next_pid);
        Task prev = runqueue.getCurrent();
        if (prev.hasTerminated()){
            runqueue.terminateCurrentProcess(next);
            // delete_task(prev)
        }
        // continua p.281
    }
    
    // load_balance, rebalance_tick, p.287

}