import java.io.*;
import java.util.*;



public class RbTree{

    private final int BLACK = 0; 
    private final int RED = 1;

    private class Node{

        int key = -1, color = BLACK; 
        Node parent = nil, left = nil, right = nil; 
    
        Node(int k){
            this.key = k; 
        }
    }

    private final Node nil = new Node(-1);
    private Node root = nil;

    public void insert(int key){
        insert(new Node(key));
    }

    public void insert(Node z){
        Node y = nil; 
        Node x = this.root; 
        while (x != nil){
            y = x; 
            if (z.key < x.key){
                x = x.left; 
            }else x = x.right; 
        }
        z.parent = y; 
        if (y == nil) this.root = z;
        else if (z.key < y.key) y.left = z;
        else y.right = z;
        z.left = nil; 
        z.right = nil;
        z.color = RED; 

        fixup(z);

    }

    private void fixup(Node z){

        while (z.parent.color == RED){
            Node y = nil;
            if (z.parent == z.parent.parent.left){
                y = z.parent.parent.right; 
                if (y != nil && y.color == RED){
                    z.parent.color = BLACK; 
                    y.color = BLACK; 
                    z.parent.parent.color = RED; 
                    z = z.parent.parent;
                    continue;
                }
                if (z == z.parent.right){
                    z = z.parent; 
                    leftRotate(z);
                }
                z.parent.color = BLACK; 
                z.parent.parent.color = RED; 
                rightRotate(z.parent.parent);
            }else{
                y = z.parent.parent.left; 
                if (y != nil && y.color == RED){
                    z.parent.color = BLACK; 
                    y.color = BLACK; 
                    z.parent.parent.color = RED; 
                    z = z.parent.parent;
                    continue;
                }
                if (z == z.parent.left){
                    z = z.parent; 
                    rightRotate(z);
                }
                z.parent.color = BLACK; 
                z.parent.parent.color = RED; 
                leftRotate(z.parent.parent);
            }
        }
        this.root.color = BLACK;

    }


    private void leftRotate(Node x){
        Node y = x.right; 
        x.right = y.left; 
        if (y.left != nil)
            y.left.parent = x; 
        y.parent = x.parent; 
        if (x.parent == nil)
            this.root = y; 
        else if (x == x.parent.left)
            x.parent.left = y; 
        else x.parent.right = y;

        y.left = x; 
        x.parent = y; 
    }

    private void rightRotate(Node x){
        Node y = x.left; 
        x.left = y.right; 
        if (y.right != nil)
            y.right.parent = x; 
        
        y.parent = x.parent; 
        if (x.parent == nil)
            root = y; 
        else if (x == x.parent.right)
            x.parent.right = y; 
        else x.parent.left = y; 

        y.right = x; 
        x.parent = y; 
    }

    private String tabs(ArrayList<Boolean> b){
        String s = "";
        for(int i = 0; i < b.size(); ++i){

            if (b.get(i)){
                s += " \u2502";
            }else{
                s += "  ";
            }
        }
        return s;
    }

    private String show(Node x, ArrayList<Boolean> b){
        String s = "";
        if (x != null) {
            s += "[" + Integer.toString(x.key) + ": " + Integer.toString(x.color) + "]\n";
            s += tabs(b);
            s += " \u251c"; 
            b.add(true); 
            s += show(x.right, b);
            b.remove(b.size()-1);

            s += tabs(b);
            s += " \u2514"; 
            b.add(false); 
            s += show(x.left, b);
            b.remove(b.size()-1);
        }else{
            s += " |\n";
        }

        return s;
    }

    public void print(){

        ArrayList<Boolean> b = new ArrayList<>();
        String s = this.show(this.root,b);
        System.out.println(s);
    }

}