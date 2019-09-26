
import java.io.*;
import java.util.*;


public class Test{

    public static void main(String [] args){

        RbTree t = new RbTree();
        t.insert(2);
        t.insert(1);
        t.insert(17);
        t.insert(9);
        t.insert(13);
        t.print();
        t.delete(2);
        t.delete(17);
        t.print();

    }

}