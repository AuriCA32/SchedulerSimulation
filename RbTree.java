
import java.util.*;

public class RbTree{

    private final int BLACK = 0; 
    private final int RED = 1;

    private final Node nil = new Node(-1);
    private Node root = nil;

    private class Node{
        int key = -1, color = BLACK; 
        Node parent = nil, left = nil, right = nil; 

        Node(int k){
            this.key = k; 
        }
    }

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
        // z.left = nil; 
        // z.right = nil;
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
            this.root = y; 
        else if (x == x.parent.right)
            x.parent.right = y; 
        else x.parent.left = y; 

        y.right = x; 
        x.parent = y; 
    }

    public Node search(int key){
        Node y = nil;
        Node x = this.root; 
        while (x != nil){
            y = x; 
            if (x.key == key){
                break; 
            }
            else if (x.key < key){
                x = x.right; 
            }else{
                x = x.left;
            }
        }
        return y;
    }

    public Node minimun(Node n){
        while(n.left != nil){
            n = n.left; 
        }
        return n; 
    }

    private void transplant(Node u, Node v){
        if (u.parent == nil){
            this.root = v; 
        }else if (u == u.parent.left){
            u.parent.left = v;
        }else{
            u.parent.right = v; 
        }
        v.parent = u.parent; 
    }

    public Boolean delete(int key){
        return delete(new Node(key));
    }

    public Boolean delete(Node z){
        if ((z = search(z.key))== nil) return false;

        Node x; 
        Node y = z; 

        int y_original_color = y.color;

        if(z.left == nil){
            x = z.right;  
            transplant(z, z.right);  
        }else if(z.right == nil){
            x = z.left;
            transplant(z, z.left); 
        }else{
            y = minimun(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; 
        }
        if(y_original_color==BLACK)
            deleteFixup(x);  
        return true;
    }

    private void deleteFixup(Node x){
        while(x!=root && x.color == BLACK){ 
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rightRotate(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }else{
                Node w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    leftRotate(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
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