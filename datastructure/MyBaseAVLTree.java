/* usage:
    MyBaseAVLTree avlTree = new MyBaseAVLTree();
    avlTree.insert(1);
    avlTree.insert(2);
    avlTree.insert(3);
    avlTree.insert(4);
    avlTree.insert(5);
    avlTree.insert(6);
    avlTree.insert(7);
    avlTree.delete(4);
    System.out.println("5 exists? " + avlTree.search(5));
    avlTree.delete(5);
    System.out.println("5 exists? " + avlTree.search(5));
*/

package datastructure;

public class MyBaseAVLTree {
  private class Node {
    public Object val;
    public Node left;
    public Node right;
    public int height;
    public Node(Object val) {
      this.val = val;
      left = null;
      right = null;
      height = 1;
    }
  }
  private Node root;
  public MyBaseAVLTree() {
    root = null;
  }
  private int getHeight(Node node) {
    return node == null ? 0 : node.height;
  }
  private Node leftRightRotate(Node node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
  }
  private Node rightLeftRotate(Node node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
  }
  private Node leftRotate(Node node) {
    Node te = node.right;
    node.right = te.left;
    te.left = node;
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    te.height = Math.max(getHeight(te.left), getHeight(te.right)) + 1;
    return te;
  }
  private Node rightRotate(Node node) {
    Node te = node.left;
    node.left = te.right;
    te.right = node;
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    te.height = Math.max(getHeight(te.left), getHeight(te.right)) + 1;
    return te;
  }
  @SuppressWarnings("unchecked")
  private boolean recursiveSearch(Node curr, Object val) {
    if(curr == null) {
      return false;
    }
    int compare = ((Comparable<Object>)val).compareTo(curr.val);
    if(compare < 0) {
      return recursiveSearch(curr.left, val);
    } else if(compare > 0) {
      return recursiveSearch(curr.right, val);
    } else {
      return true;
    }
  }
  @SuppressWarnings("unchecked")
  private Node recursiveDelete(Node curr, Object val) {
    if(curr == null) {
      return null;
    }
    int compare = ((Comparable<Object>)val).compareTo(curr.val);
    if(compare < 0) { // delete from left
      curr.left = recursiveDelete(curr.left, val);
      if(getHeight(curr.right) - getHeight(curr.left) == 2) {
        if(getHeight(curr.right.left) > getHeight(curr.right.right)) {
          curr = rightLeftRotate(curr);
        } else {
          curr = leftRotate(curr);
        }
      }
    } else if(compare > 0) { // delete from right
      curr.right = recursiveDelete(curr.right, val);
      if(getHeight(curr.left) - getHeight(curr.right) == 2) {
        if(getHeight(curr.left.right) > getHeight(curr.left.left)) {
          curr = leftRightRotate(curr);
        } else {
          curr = rightRotate(curr);
        }
      }
    } else { // delete current
      if(curr.left != null && curr.right != null) { // two children
        Node te;
        for(te=curr.right;te.left!=null;te=te.left);
        curr.val = te.val;
        curr.right = recursiveDelete(curr.right, te.val);
        if(getHeight(curr.left) - getHeight(curr.right) == 2) {
          if(getHeight(curr.left.right) > getHeight(curr.left.left)) {
            curr = leftRightRotate(curr);
          } else {
            curr = rightRotate(curr);
          }
        }
      } else { // single or no child
        if(curr.left != null) {
          curr = curr.left;
        } else if(curr.right != null) {
          curr = curr.right;
        } else {
          curr = null;
        }
      }
    }
    if(curr == null) {
      return null;
    }
    curr.height = Math.max(getHeight(curr.left), getHeight(curr.right)) + 1;
    return curr;
  }
  @SuppressWarnings("unchecked")
  private Node recursiveInsert(Node curr, Object val) {
    if(curr == null) {
      curr = new Node(val);
    } else {
      int compare = ((Comparable<Object>)val).compareTo(curr.val);
      if(compare < 0) { // left
        curr.left = recursiveInsert(curr.left, val);
      } else if(compare > 0) { // right
        curr.right = recursiveInsert(curr.right, val);
      } else { // all elements are unique
        return curr;
      }
      int leftHeight = getHeight(curr.left);
      int rightHeight = getHeight(curr.right);
      int offset = leftHeight - rightHeight;
      if(offset == 2) { // left
        if(((Comparable<Object>)val).compareTo(curr.left.val) > 0) {
          curr = leftRightRotate(curr);
        } else {
          curr = rightRotate(curr);
        }
      } else if(offset == -2) { // right
        if(((Comparable<Object>)val).compareTo(curr.right.val) < 0) {
          curr = rightLeftRotate(curr);
        } else {
          curr = leftRotate(curr);
        }
      }
    }
    curr.height = Math.max(getHeight(curr.left), getHeight(curr.right)) + 1;
    return curr;
  }
  public boolean search(Object val) {
    return recursiveSearch(root, val);
  }
  public void delete(Object val) {
    root = recursiveDelete(root, val);
  }
  public void insert(Object val) {
    root = recursiveInsert(root, val);
  }
}