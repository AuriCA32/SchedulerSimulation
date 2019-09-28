
import java.io.*;
import java.util.*;


public class Test{

    public static void main(String [] args){

        RbTree t = new RbTree();
        t.insert(10);
        t.insert(9);
        t.insert(8);
        t.insert(7);
        t.insert(6);
        t.insert(5);
        t.insert(4);
        t.insert(3);
        t.insert(2);
        t.insert(1);
        t.print();
        t.delete(7);
        t.delete(5);
        t.delete(6);
        t.print();

    }

}
