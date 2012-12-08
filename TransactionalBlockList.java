/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thumbtack;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 * @author kevlee2
 */
public class TransactionalBlockList  {
    
   Stack<Block> transactionList ; 
        
   Block currentBlock = null;
   //  private Block root; 
    
    private static class Block { 
        CommandList commandList;
      
    Block() { 
        commandList = new CommandList();
        } 
    }
  
    // for each trasnaction block, keep a reverse rollback commands
    public TransactionalBlockList() {
        transactionList = new Stack<>(); 
    }     
    
    public void beginTransaction()    {          
        if (currentBlock != null){
            transactionList.push(currentBlock);
        }
        currentBlock = new Block(); 
    }
    
    public void addCommand(String method, String name, Integer value, BinaryTree database)    { 
        
        if (currentBlock != null){
            String reverseMethod;
  //          String reverseName = name;
            Integer reverseValue = database.getNode(name);
         
            switch (method) {
                case "SET":
                    if (reverseValue != null)  {
                        reverseMethod = method;
                        reverseValue = database.getNode(name);                    
                    }
                    else {
                        reverseMethod = "UNSET";                    
                    }
                    currentBlock.commandList.addReverseCommand(reverseMethod, name, reverseValue);
                    break;
                case "UNSET":
                    if (reverseValue != null)  {
                        reverseMethod = "SET";
                        reverseValue = database.getNode(name);     
                        currentBlock.commandList.addReverseCommand(reverseMethod, name, reverseValue);  
                    }
                    break;
            }
        }
    }
    
    public void rollBackTransaction(BinaryTree database) 
    {
        // execute the current blocks reverse commands.
        if (currentBlock != null){            
            currentBlock.commandList.rollBackCommand(database);
         }
        else        {
            System.out.println("INVALID ROLLBACK");
        }
        
        
        // pop the current transaction block 
        if (!transactionList.empty())        {
            currentBlock = transactionList.pop();
        }
        else         {
            currentBlock = null;
        }
    }
        
    public void commitTransaction()
    {
        while(!transactionList.empty())        {
            transactionList.pop();
        }
        currentBlock = null;
    }
    
    public void printTransaction()
    {
        System.out.println("*** printCommand");
     //   currentBlock.commandList.
        
    }
}
