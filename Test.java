import java.io.*;
import java.util.*;

// Clase de prueba para hilos
class ThreadTest extends Thread{

    RbTree tree;
    int thread_no;

    public ThreadTest(RbTree t, int number){
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

public class Test{

    public static void main(String [] args){
        // Prueba RBTree
        // RbTree t = new RbTree();
        // new ThreadTest(t, 0);
        // new ThreadTest(t, 1);
        // Prueba PCB
        HashMap<Integer, Integer[]> io_operations = new HashMap<Integer, Integer[]>();
        io_operations.put(0, new Integer[] {5,1,0});
        io_operations.put(1, new Integer[] {8,2,0});
        PCB process_0 = new PCB(0, 100, 0, io_operations);
        System.out.println(process_0.toString());
        PCB process_1 = new PCB(1, 150, 0, io_operations);
        PCB process_2 = new PCB(2, 250, 0, io_operations);
        // Process table
    }

}
