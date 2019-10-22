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

    // try to wake up
    // recalc task prio
    // schedule
    void schedule(){

    }
    // load_balance, rebalance_tick, p.287

}