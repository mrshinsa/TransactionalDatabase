package thumbtack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/* 
 * downloaded and modified from 
 * http://cslibrary.stanford.edu/110/BinaryTrees.html
 * http://www.brilliantsheep.com/a-complete-binary-search-tree-implementation-in-java/
*/

// BinaryTree.java 
public class BinaryTree { 
  // Root node pointer. Will be null for an empty tree. 
  private Node root; 
  HashMap<Integer, List<String>> valueCount;  
  /* 
   --Node-- 
   The binary tree is built using this nested node class. 
   Each node stores one data element, and has left and right 
   sub-tree pointer which may be null. 
   The node is a "dumb" nested class -- we just use it for 
   storage; it does not have any methods. 
  */ 
  private static class Node { 
    Node left; 
    Node right; 
    Node parent; 
    Integer value; 
    String name;

    Node(String newName, Integer newData) { 
      left = null; 
      right = null; 
      parent = null;
      value = newData; 
      name = newName;
    } 
  } 

  /** 
   Creates an empty binary tree -- a null root pointer. 
  */ 
  public void BinaryTree() { 
    root = null; 
    // depending on the amount of data used, change the initialCapacity
    int initialCapacity = 64;
    valueCount = new HashMap<>(initialCapacity);
  } 
  
    private void addMapEntry( Integer valueKey, String nameValue ){ 

        List<String> list = valueCount.get(valueKey);
        if (list == null)        {
            list = new ArrayList<>();
            valueCount.put(valueKey, list);            
        }
        list.add(nameValue);            
    }
    
    private void removeMapEntry( Integer valueKey, String nameValue ){        
        List<String> list  = valueCount.get(valueKey);
        if (list  != null){
            list.remove(nameValue);
        }
    }
    private int getMapEntrySize( Integer valueKey ){        
          List<String> list = valueCount.get(valueKey);   
          if (list == null)
              return 0;
          else
            return (list.size());
    }
    
  /** 
   Returns true if the given target is in the binary tree. 
   Uses a recursive helper. 
  */ 
  public Integer getNode(String name) { 
  //  return(lookup(root, name)); 
      return lookup(root, name);
  } 

  /** 
   Recursive lookup  -- given a node, recur 
   down searching for the given data. 
  */ 
  private Integer lookup(Node node, String name) { 
    if (node == null) { 
          return null; 
    }     
    if (node.name.compareTo(name) == 0) { 
      return(node.value); 
    } 
    else if (node.name.compareTo(name) > 0) { 
      return(lookup(node.left, name)); 
    } 
    else { 
      return(lookup(node.right, name)); 
    } 
  }   
          
  
  /** 
   Inserts the given data into the binary tree. 
   Uses a recursive helper. 
  */ 
  public void setNode(String name, Integer value) { 
    root = insert(root, name, value); 
  } 

  /** 
   Recursive insert -- given a node pointer, recur down and 
   insert the given data into the tree. Returns the new 
   node pointer (the standard way to communicate 
   a changed pointer back to the caller). 
  */ 
  private Node insert(Node node, String name, Integer value) { 
    if (node == null) { 
      node = new Node(name, value); 
      addMapEntry( value, name );
    } 
    else { 
      if (node.name.compareTo(name) == 0) { 
        removeMapEntry( node.value, name );     
        node.name = name; 
        node.value = value;  
        addMapEntry( value, name ); 
      } 
      else if (node.name.compareTo(name) > 0) { 
        node.left = insert(node.left, name, value); 
        node.left.parent = node;
      } 
      else { 
        node.right = insert(node.right, name, value); 
        node.right.parent = node;
      } 
    } 
    return(node); // in any case, return the new pointer to the caller 
  }
  
  /* 
   Returns true if the given target is in the binary tree. 
   Uses a recursive helper. 
  */   
    public void countNode(Integer value) { 
    //      System.out.println(countNode(root, value, 0)); 
          System.out.println(getMapEntrySize(value)); 
    } 

    /*
// obsolete tree traversal. Use hashmap instead.
  private int countNode(Node node, Integer value, int count) { 
    if (node == null) { 
      return(count); 
    }     
    if (node.value == value){
        count++;   
    }
     count = countNode(node.left, value, count);     
     count = countNode(node.right, value, count); 
    return count;
  } 
  */
  
          
    /**
     * Helper method for the binary search tree delete operation. It used for
     * swapping the nodes, while keeping the structure of the binary search tree.
     * Refer to page 296 of Introduction to Algorithms (3rd Edition) by Cormen 
     * et. al. for further details.
     */
    private void swap( Node a, Node b ) {
 
    	if( a.parent == null ) {
    		root = b;
    	} else if( a == a.parent.left ) {
    		a.parent.left = b;
    	} else {
    		a.parent.right = b;
    	} 
    	if( b != null ) {
    		b.parent = a.parent;
    	}
    }
 
    /**
     * Deletes the node containing the specified data. The search for the node
     * to be deleted starts from the root node.
     */
    public void delete( String name ) {
        delete( root, name );
    }
 
    /**
     * Deletes the node containing the specified data. The search for the node
     * to be deleted starts from the specified node. Refer to page 298 of
     * Introduction to Algorithms (3rd Edition) by Cormen et. al. for further
     * details.
     */
    private void delete( Node node, String name ) {
		// Can't find the node we want to delete.
    	if( node == null ) {
    		return;
    	}
    	// We've found the node we want to delete.
    	else if (node.name.compareTo(name) == 0) { 
    		// Check if it has a left subtree. If it deson't then just replace
    		// the node we want to delete with its right subtree.
            if( node.left == null ) {
                swap( node, node.right ); 
            } 
            // Check if it has a right subtree. If it doesn't then just replace
            // the node we want to delete with its left subtree.
            else if( node.right == null ) {
                swap( node, node.left );
            } 
            // Since it has both a left and right subtree, then traverse to the
            // left-most child of the right subtree (i.e. the smallest node in the
            // right subtree). Once found, exchange the data values between the node
            // and the node that we want to delete.
            else {
                Node minNode = node.right; 
                while( minNode.left != null ) {
                    minNode = minNode.left;
                } 
                if( minNode.parent != node ) {
                	swap( minNode, minNode.right );
                	minNode.right = node.right;
                	minNode.right.parent = minNode;
                } 
                swap( node, minNode );
                minNode.left = node.left;
                minNode.left.parent = minNode;
            }            
            removeMapEntry( node.value, name );    
        }
    	// Continue searching in the left subtree.
    	else if (node.name.compareTo(name) > 0) { 
            delete( node.left, name );
        } 
    	// Continue searching in the right subtree.
    	else {
            delete( node.right, name );
        }
    }
}   

  

