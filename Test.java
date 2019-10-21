import java.io.*;
import java.util.*;

// Clase de prueba para Rbtree
class ThreadRbtreeTest extends Thread{

    RbTree tree;
    int thread_no;

    public ThreadRbtreeTest(RbTree t, int number){
        this.tree=t;
        this.thread_no=number;
        new Thread(this,"Thread "+number).start();
    }

    public void run(){
        for (int i = 0; i < 5; i++) {
            this.tree.insert(i, i);
            System.out.println(thread_no+" inserta "+i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
        this.tree.print();
        for (int i = 0; i < 5; i++) {
            this.tree.delete(i,i);
            System.out.println(thread_no+" elimina "+i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
        this.tree.print();
    }

}

// Clase de prueba para ProcessTable
class ThreadProcessTableTest extends Thread{

    ProcessTable table;
    int thread_no;
    Task task;

    public ThreadProcessTableTest(ProcessTable t, int number, Task task){
        this.table=t;
        this.thread_no=number;
        this.task = task;
        new Thread(this,"Thread "+number).start();
    }

    public void run(){
        // Add process
        Boolean added = this.table.addTask(this.task);
        if (added){
            System.out.println("Luego de que "+thread_no+" inserta un proceso");
            System.out.println("Thread "+thread_no+" "+this.table.toString());
            System.out.println();
        }
        try {
            sleep((int)(Math.random() * 100));
        } catch (InterruptedException e) { }

        // Get process
        Task taskie = this.table.getTask(this.task.getPid());
        if (taskie!=null){
            System.out.println("Thread "+thread_no+" "+taskie.toString());
            System.out.println();
        }
        try {
            sleep((int)(Math.random() * 100));
        } catch (InterruptedException e) { }

        // Add same process
        added = this.table.addTask(this.task);
        if (added){
            System.out.println("Proceso repetido insertado por"+thread_no);
        }else{
            System.out.println("Proceso repetido NO insertado por"+thread_no);
        }
        try {
            sleep((int)(Math.random() * 100));
        } catch (InterruptedException e) { }

        // Remove process
        Boolean removed = this.table.removeTask(this.task.getPid());
        if (removed){
            System.out.println("Luego de que "+thread_no+" elimina un proceso");
            System.out.println("Thread "+thread_no+" "+this.table.toString());
            System.out.println();
        }
        try {
            sleep((int)(Math.random() * 100));
        } catch (InterruptedException e) { }

        // Get removed process
        taskie = this.table.getTask(this.task.getPid());
        if (taskie!=null){
            System.out.println("Proceso eliminado obtenido por"+thread_no);
        }else{
            System.out.println("Proceso eliminado NO obtenido por"+thread_no);
        }
        try {
            sleep((int)(Math.random() * 100));
        } catch (InterruptedException e) { }

        // Remove same process
        removed = this.table.removeTask(this.task.getPid());
        if (removed){
            System.out.println("Proceso eliminado nuevamente por"+thread_no);
        }else{
            System.out.println("Proceso NO eliminado nuevamente por"+thread_no);
        }
    }

}

public class Test{

    public static void main(String [] args){
        // Prueba RBTree
        // RbTree t = new RbTree();
        // new ThreadTest(t, 0);
        // new ThreadTest(t, 1);
        // Prueba PCB
        HashMap<Integer, Integer[]> io_operations = new HashMap<Integer, Integer[]>();
        io_operations.put(0, new Integer[] {5,1,0});
        // io_operations.put(1, new Integer[] {8,2,0});
        Task task_0 = new Task(0, 0, 100, 0, io_operations);
        // System.out.println(task_0.toString());
        Task task_1 = new Task(1, 1, 150, 0, io_operations);
        // System.out.println(task_1.toString());
        Task task_2 = new Task(2, 2, 250, 0, io_operations);
        /* System.out.println(task_2.toString());
        Process table
        ProcessTable table = new ProcessTable();
        new ThreadProcessTableTest(table, 0, task_0);
        new ThreadProcessTableTest(table, 1, task_1);
        new ThreadProcessTableTest(table, 2, task_2);
        PrioArray prio_array = new PrioArray();
        prio_array.enqueueTask(task_0);
        System.out.println("Add task");
        System.out.println(prio_array.toString());
        if (task_0.getArray()==prio_array){
            System.out.println("Son el mismo");
        }
        Boolean add = prio_array.enqueueTask(task_0);
        if (add){
            System.out.println("Add same task");
        }
        prio_array.dequeueTask(task_0);
        System.out.println("Remove task");
        System.out.println(prio_array.toString());
        Boolean removed = prio_array.dequeueTask(task_0);
        if (removed){
            System.out.println("Removed same task");
        }
        if (task_0.getArray()!=prio_array){
            System.out.println("Sin prio array");
        } */
        // Create I/O queue
        LinkedList<Task> io_queue_0 = new LinkedList<Task>();
        Runqueue run_cpu_0 = new Runqueue(0);
        // System.out.println(run_cpu_0.toString());
        run_cpu_0.addNewTask(task_0);
        // System.out.println(run_cpu_0.toString());
        Boolean add = run_cpu_0.addNewTask(task_0);
        if (add){
            System.out.println("Add");
        }
        run_cpu_0.changeCurrentProcess(task_0);
        // System.out.println(run_cpu_0.toString());
        Boolean changed = run_cpu_0.changeCurrentProcess(run_cpu_0.getCurrent());
        if (changed){
            System.out.println("Changed");
        }
        run_cpu_0.exchangeArrays();
        // Proceso terminado
        run_cpu_0.addNewTask(task_1);
        // System.out.println(run_cpu_0.toString());
        Boolean terminated = run_cpu_0.terminateCurrentProcess(task_1);
        if (terminated){
            System.out.println("Terminated");
        }
        task_0.setCurrExecT(task_0.getTotalExecT());
        run_cpu_0.terminateCurrentProcess(task_1);
        // System.out.println(run_cpu_0.toString());
        run_cpu_0.addNewTask(task_2);
        run_cpu_0.changeCurrentProcess(task_2);
        run_cpu_0.changeCurrentProcess(null);
        // System.out.println(run_cpu_0.toString());
        run_cpu_0.exchangeArrays();
        // System.out.println(run_cpu_0.toString());
        // System.out.println(task_1.getArray().toString());
        // Migrate process
        run_cpu_0.migrateProcess(task_1);
        System.out.println(run_cpu_0.toString());
    }

}
