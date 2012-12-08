/*
 * EduAVLTree: An AVL tree implementation of educational intention.
 * 
 * Copyright (C) 2011 Rafael W.
 *
 * EduAVLTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EduAVLTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EduAVLTree. If not, see http://www.gnu.org/licenses/.
 *
 */
package thumbtack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * From : http://code.google.com/p/java-educational-avl-tree/
 * An AVL tree of educational intention. This tree merely accepts numbers as elements in order to
 * allow an intuitive code illustration by a direct usage of comparison operators (<,>,==).
 * 
 * This class is able to print the tree to the console.
 * @author Rafael W.
 * @version 0.1
 */
public class EduAVLTree implements Serializable {
	
	/**
	 * Serial ID (v0.1)
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The tree root or <code>null</code> if no element is inserted.
	 */
	private Node root;
        private HashMap<Integer, List<String>> valueCount;  
	
	/**
	 * A tree node.
	 * @author Rafael W.
	 */
	public class Node {
		
		/**
		 * This node's left and right children and this node's parent.
		 */
		private Node left, right, parent;
		
		/**
		 * The value of this node.
		 */
		//private final int value;
                private Integer value; 
                private String name;
		
		/**
		 * The balance and height of this node.
		 */
		private int balance, height = 1;
	
		/**
		 * Creates a new node.
		 * @param val The value.
		 */
		private Node(String name, Integer val) {
			this.name = name;
			this.value = val;
		}
		
		/**
		 * Sets this node's left child.
		 * @param left The new left child.
		 */
		private void setLeft(Node left) {
			
			this.left = left;
			if(left != null)
				left.parent = this;
			
		}
		
		/**
		 * Sets this node's right child.
		 * @param right The right child.
		 */
		private void setRight(Node right) {
			
			this.right = right;
			if(right != null)
				right.parent = this;
			
		}
		
		/**
		 * Sets this node as root.
		 */
		private void setRoot() {
			
			EduAVLTree.this.root = this;
			parent = null;
			
		}
		
		/**
		 * Replaces a child with another node.
		 * @param old The old node.
		 * @param rep The new node.
		 */
		private void replace(Node old, Node rep) {
			
			if(left == old)
				setLeft(rep);
			
			else if(right == old)
				setRight(rep);
			
			else
				throw new IllegalArgumentException("Cannot replace node because node is no son.");
			
		}
		
		/**
		 * Updates this node's height and balance values.
		 */
		private void update() {
			
			int[] sonHeight = sonHeight();
			
			balance = sonHeight[0] - sonHeight[1];
			height = Math.max(sonHeight[0], sonHeight[1]) + 1;
			
		}
		
		/**
		 * Computes an array containing the heights of both sons.
		 * (0: left son, 1: right son)
		 * @return The heights of the sons as an array.
		 */
		private int[] sonHeight() {
			
			return new int[] {
				right == null ? 0 : right.height,
				left == null ? 0 : left.height
			};
			
		}
		
		/**
		 * Returns the only son given such a condition is satisfied.
		 * @return The only son of this node.
		 */
		private Node onlySon() {
			
			if(right != null && left == null)
				return right;
			else if(left != null && right == null)
				return left;
			else
				throw new IllegalStateException("This node does not have an only son.");
			
		}
		
		/**
		 * Checks if this node is a leaf.
		 * @return <code>true</code> if this node is a leaf.
		 */
		private boolean isLeaf() {
			return left == null && right == null;
		}
		
		/**
		 * Checks if this node has only one son.
		 * @return <code>true</code> if the node has only one son.
		 */
		private boolean isSingleSon() {
			return left == null ^ right == null;
		}
		
		/**
		 * Removes a son of this node.
		 * @param son The son to remove.
		 */
		private void removeSon(Node son) {
			
			if(son == null)
				throw new NullPointerException("Cannot remove son that is null.");
			
			if(left == son)
				left = null;
			else if(right == son)
				right = null;
			
			son.parent = null;
			
		}
					
	}

        public void EduAVLTree() { 
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
        
        public int count( Integer valueKey ){        
              List<String> list = valueCount.get(valueKey); 
              int count;
              if (list == null)
                  count = 0;
              else
                  count = list.size();
                return count;
        }
 	
	/**
	 * Checks if a value was inserted in the tree.
	 * @param val The value.
	 * @return <code>true</code> if the value was found.
	 */
	public Integer get(String name) {		
	//	System.out.printf("Looking for %d:%n", val);		
		// Walking down tree until end or until value was found.
		Node c = root;
		while(c != null){			
			// Value match -> End of search.
                        if (c.name.compareTo(name) == 0) { 
                          return(c.value); 
                        } 
			// Value smaller than current element -> Go left.
                        else if (c.name.compareTo(name) > 0) { 
				c = c.left;
			//	System.out.printf(" -> Requested value is smaller than %d : Going left.", c.value);			
			// Value bigger than current element -> Go right.
			} else {
				c = c.right;
			//	System.out.printf(" -> Requested value is bigger than %d : Going right.", c.value);
			}
                }
	//	System.out.println(" -> Requested value was not found.");
		return null;		
	}
        
	/**
	 * Adds a new value to this tree.
	 * @param val The new value.
	 */
	public void add(String name, Integer val) {		
	//	System.out.printf("Adding element: %d%n", val);		
		// No root yet exists -> Insert as root and return.
		if(root == null) {
			new Node(name, val).setRoot();
                        addMapEntry( val, name );
		//	System.out.println(" -> Setting as root.");
			return;
		}		
		// Find right path for insertion.
		Node c = root;
		while(true) {			
			// Element is smaller than current node's value -> Continue at right.                    
                        if (c.name.compareTo(name) > 0) { 
			//if(val < c.value) {				
			//	System.out.printf(" -> Smaller than %d : Going left%n", c.value);				
				if(c.left == null) {
					c.setLeft(new Node(name, val));
                                        addMapEntry( val, name );
				//	System.out.printf(" -> Setting as left child of %d%n", c.value);
					up(c);
					return;
				} else
					c = c.left;				
			// Element is bigger than current node's value -> Continue at right.
                        } else if (c.name.compareTo(name) < 0) {
		//	} else if(val > c.value) {				
			//	System.out.printf(" -> Bigger than %d : Going right%n", c.value);				
				if(c.right == null) {
					c.setRight(new Node(name, val));
                                        addMapEntry( val, name );
				//	System.out.printf(" -> Setting as right child of %d%n", c.value);
					up(c);
					return;
				} else
					c = c.right;				
			// Element is already included -> Exception.
			} else{                            
                            removeMapEntry( c.value, name );     
                            c.name = name; 
                            c.value = val;  
                            addMapEntry( val, name );
                            return;
                        }			
		}		
	}
	
	/**
	 * Removes an element from the tree.
	 * @param val The value.
	 */
	public void remove(String name) {
		
		Node k = findNode(root, name);
		if(k == null)
                    return;
		//	throw new IllegalArgumentException("Cannot remove " + val + " because such a node does not exist.");
		Node kParent = k.parent;		
	//	System.out.printf("Removing value: %d%n", val);		
		// Delete node that is a leaf.
		if(k.isLeaf()) {			
		//	System.out.println(" -> Corresponding node is a leaf : Performing cutoff.");			
			if(k == root)
				root = null;
			else
				k.parent.removeSon(k);				
		// Delete node that us a single son.
		} else if(k.isSingleSon()) {			
		//	System.out.println(" -> Corresponding node has only one son. Connecting node's son and parent.");			
			if(k == root)
				k.onlySon().setRoot();
			else
				k.parent.replace(k, k.onlySon());		
		// Delete inner node.
		} else {			
			// Check if it is more efficient to exchange node by another left or right side node
			// If uncertain, choose right side (and thus bigger element) for finding a replacement.
			boolean replaceByLeft = k.left != null && 
				(k.right == null || k.left.height > k.right.height);			
			Node s;
			if(replaceByLeft)
				s = findNextSmallerNode(k);
			else
				s = findNextBiggerNode(k);			
	//		System.out.printf(" -> Correspondig node has two sons. Removing next %s element from tree first" +
	//				" before proceeding. Removing: %d%n", replaceByLeft ? "bigger" : "smaller", s.value);			
			// Remove node first that is to be used as a replacement.
			remove(s.name);			
	//		System.out.printf(" -> %d is removed. Proceeding with replacing %d%n", s.value, k.value);			
			// Set new node in place of the removed node.
			if(k == root)
				s.setRoot();
			else
				k.parent.replace(k, s);			
			// Fix father - son relationships.
			s.setLeft(k.left);
			s.setRight(k.right);			
			s.update();			
		}
                removeMapEntry( k.value, name );    		
		if(kParent != null)
			up(kParent);		
	}
	
	/**
	 * Finds the next bigger valued node for a starting node.
	 * @param n The starting node.
	 * @return The next bigger node.
	 */
	private Node findNextBiggerNode(Node n) {
		
		if(n.right == null)
			throw new IllegalArgumentException("There is no next bigger node.");
		
		Node c = n.right;
		while(c.left != null)
			c = c.left;
		
		return c;
		
	}
	
	/**
	 * Finds the next smaller valued node for a starting node.
	 * @param n The starting node.
	 * @return The next smaller node.
	 */
	private Node findNextSmallerNode(Node n) {
		
		if(n.left == null)
			throw new IllegalArgumentException("There is no next smaller node.");
		
		Node c = n.left;
		while(c.right != null)
			c = c.right;
		
		return c;
		
	}
	
	/**
	 * Finds a node of a certain value.
	 * @param n The current node.
	 * @param val The value of interest.
	 * @return The node or <code>null</code> if no such node exists.
	 */
	private Node findNode(Node n, String name) {	
		if(n == null)
			return null;		
		if(n.name.compareTo(name) == 0)
			return n;
		else if(n.name.compareTo(name) > 0) 
			return findNode(n.left, name);
		else
			return findNode(n.right, name);		
	}
	
	/**
	 * Walks up from the most recent deleted item to the tree root in order to
	 * inform all entities 
	 * @param a The current node that has to be revalidated after a tree alteration.
	 */
	private void up(Node a) {		
		Node next = a.parent;
		int orgHeigth = a.height;		
	//	System.out.printf("Validating element: %d (balance/height: %d, %d =>", a.value, a.balance, a.height);
		a.update();
	//	System.out.printf(" %d, %d)%n", a.balance, a.height);		
		// Check if tree node is out of balance.
		if(Math.abs(a.balance) >= 2)
			a = rotate(a);		
		// Update parent nodes only if the tree root was not yet reached
		// and if the height of the current node was altered.
		if(next != null && orgHeigth != a.height)
			up(next);		
	}
	
	/**
	 * Performs a rotation. Will only work correctly if rotation is actually required.
	 * @param a The rotation anchor.
	 * @return The node's replacement node.
	 */
	private Node rotate(Node a) {
		
		// Correct for right side overweight.
		if (a.balance == 2)
			// Correct for right-left side overweight.
			if(a.right.balance == -1)
				return rotateLeftDouble(a);
			// Correct for right-right side overweight.
			else //if(a.right.balance == 1 || /* delete only: */ a.right.balance == 0)
				return rotateLeft(a);
		
		// Correct for left side overweight
		else //if(a.balance == -2)
			// Correct for left-right side overweight.
			if(a.left.balance == 1)
				return rotateRightDouble(a);
			// Correct for left-left side overweight.
			else //if(a.left.balance == -1 || /* delete only: */ a.left.balance == 0)
				return rotateRight(a);
		
	}
	
	/**
	 * Performs a single right rotation:
	 *       a                  s
	 *      / \                / \
	 *     s   T3      =>    T1   a    
	 *    / \                    / \
	 *  T1   T2                T2   T3
	 * @param a The rotation anchor.
	 * @return The new anchor node.
	 */
	private Node rotateRight(Node a) {
		
		Node s = a.left;

		System.out.printf(" -> Right single rotation on elements a: %d, s: %d%n", a.value, s.value);
		
		if (a == root)
			s.setRoot();
		else
			a.parent.replace(a, s);
		
		a.setLeft(s.right);
		a.update();
		
		s.setRight(a);
		s.update();
		
		return s;
		
	}
	
	/**
	 * Performs a single left rotation:
	 *        a                   s
	 *       / \                 / \
	 *     T1   s      =>       a   T3
	 *         / \             / \    
	 *       T2   T3         T1   T2
	 * @param a The rotation anchor.
	 * @return The new anchor node.
	 */
	private Node rotateLeft(Node a) {
		
		Node s = a.right;

		System.out.printf(" -> Left single rotation on elements a: %d, s: %d%n", a.value, s.value);
		
		if (a == root)
			s.setRoot();
		else
			a.parent.replace(a, s);
		
		a.setRight(s.left);
		a.update();
		
		s.setLeft(a);
		s.update();
		
		return s;
		
	}
	
	/**
	 * Performs a double left rotation:
	 *          a                     b
	 *         / \                  /   \
	 *       T1   s               a       s
	 *           / \      =>     / \     / \
	 *          b   T4         T1   T2 T3   T4
	 *         / \
	 *       T2   T3
	 * @param a The rotation anchor.
	 * @return The new anchor node.
	 */
	private Node rotateLeftDouble(Node a) {
		
		Node s = a.right;
		Node b = s.left;

		System.out.printf(" -> Double left rotation on elements a: %d, s: %d, b: %d%n", a.value, s.value, b.value);
		
		if (a == root)
			b.setRoot();
		else
			a.parent.replace(a, b);

		a.setRight(b.left);
		a.update();
		
		s.setLeft(b.right);
		s.update();
		
		b.setLeft(a);
		b.setRight(s);
		b.update();	
		
		return b;
		
	}
	
	/**
	 * Performs a double right rotation:
	 *          a                     b
	 *         / \                  /   \
	 *        s   T4              s       a
	 *       / \        =>       / \     / \
	 *     T1   b              T1   T2 T3   T4
	 *         / \
	 *       T2   T3
	 * @param a The rotation anchor.
	 * @return The new anchor node.
	 */
	private Node rotateRightDouble(Node a) {
		
		Node s = a.left;
		Node b = s.right;
		
		System.out.printf(" -> Double right rotation on elements a: %d, s: %d, b: %d%n", a.value, s.value, b.value);
		
		if (a == root)
			b.setRoot();
		else
			a.parent.replace(a, b);
			
		a.setLeft(b.right);
		a.update();
		
		s.setRight(b.left);
		s.update();
		
		b.setRight(a);
		b.setLeft(s);
		b.update();
		
		return b;
		
	}
	
	/**
	 * Returns a string representation of the tree.
	 */
	@Override
	public String toString() {
		if(root == null)
			return "[null]";
		else
			return root.toString();
	}

}