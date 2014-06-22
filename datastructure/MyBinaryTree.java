/* usage:
    MyBinaryTree bTree = new MyBinaryTree();
    bTree.insert("Google");
    bTree.insert("Amazon");
    bTree.insert("Apple");
    bTree.insert("Facebook");
    bTree.insert("MicroSoft");
    System.out.println("InOrder Traverse:");
    bTree.inOrder();
    System.out.println("There are " + bTree.getSize() + " nodes.");
    System.out.println("Level Traverse:");
    bTree.levelTraverse();
    
    // if store object into binary tree, the class must implement Comparable<Object>
    class Student implements Comparable<Object> {
      public String name;
      public int score;
      public Student(String name, int score) {
        this.name = name;
        this.score = score;
      }
      @Override
      public int compareTo(Object o) { // order by score, ascending
        Student student = (Student)o;
        if(this.score < student.score) {
          return -1;
        } else if(student.score == this.score) {
          return 0;
        } else {
          return 1;
        }
      }
      @Override
      public String toString() {
        return "[name is " + name + ", score is " + score + "]";
      }
    }
    // following is how to use
    MyBinaryTree bTree2 = new MyBinaryTree();
    Student s1 = new Student("Bob", 93);
    Student s2 = new Student("Tom", 87);
    Student s3 = new Student("Jerry", 90);
    Student s4 = new Student("Jim", 93);
    bTree2.insert(s1);
    bTree2.insert(s2);
    bTree2.insert(s3);
    bTree2.insert(s4);
    System.out.println("InOrder Traverse:");
    bTree2.inOrder();
*/

package datastructure;

import java.util.*;

public class MyBinaryTree {
  private class Node {
    public Object val;
    public Node left;
    public Node right;
    public Node(Object val) {
      this.val = val;
      left = null;
      right = null;
    }
  }
  private Node root;
  private int size;
  public MyBinaryTree() {
    root = null;
    size = 0;
  }
  private void preOrder(Node curr) {
    if(curr == null) {
      return;
    }
    System.out.println(curr.val);
    preOrder(curr.left);
    preOrder(curr.right);
  }
  private void inOrder(Node curr) {
    if(curr == null) {
      return;
    }
    inOrder(curr.left);
    System.out.println(curr.val);
    inOrder(curr.right);
  }
  private void postOrder(Node curr) {
    if(curr == null) {
      return;
    }
    postOrder(curr.left);
    postOrder(curr.right);
    System.out.println(curr.val);
  }
  @SuppressWarnings("unchecked")
  private Node recursiveInsert(Node curr, Object val) {
    if(curr == null) {
      curr = new Node(val);
    } else if(((Comparable<Object>)val).compareTo(curr.val) < 0) {
      curr.left = recursiveInsert(curr.left, val);
    } else {
      curr.right = recursiveInsert(curr.right, val);
    }
    return curr;
  }
  public int getSize() {
    return size;
  }
  public void preOrder() {
    preOrder(root);
  }
  public void inOrder() {
    inOrder(root);
  }
  public void postOrder() {
    postOrder(root);
  }
  public void levelTraverse() {
    if(root == null) {
      System.out.println("The BinaryTree is empty.");
      return;
    }
    Queue<Node> queue = new LinkedList<Node>();
    Node node;
    queue.add(root);
    while(!queue.isEmpty()) {
      node = queue.poll();
      System.out.println(node.val);
      if(node.left != null) {
        queue.offer(node.left);
      }
      if(node.right != null) {
        queue.offer(node.right);
      }
    }
  }
  public void insert(Object val) {
    root = recursiveInsert(root, val);
    size++;
  }
}
