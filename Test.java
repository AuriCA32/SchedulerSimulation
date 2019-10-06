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
            this.tree.insert(i);
            System.out.println(thread_no+" inserta "+i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
        this.tree.print();
        for (int i = 0; i < 5; i++) {
            this.tree.delete(i);
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
        RbTree t = new RbTree();
        new ThreadTest(t, 0);
        new ThreadTest(t, 1);
        // Prueba process table

    }

}
