/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thumbtack;

import java.util.Stack;

/**
 *
 * @author Kevin
 */
public class CommandList {
    
    Stack<reverseCommand> commandList; 
    
    private static class reverseCommand { 
      Integer value; 
      String name;
      String command;
      
      reverseCommand(String newCommand, String newName, Integer newData) { 
        value = newData; 
        name = newName;
        command = newCommand;
      } 
    }  
    
    // for each trasnaction block, keep a reverse rollback commands
    public CommandList() {
        commandList = new Stack<>();         
    }     
  
    public void addReverseCommand(String method, String name, Integer value)    {   
    //     System.out.println("addReverseCommand: " + method + ":" + name + ":" + value);
        reverseCommand newCommand = new reverseCommand(method, name, value);
        commandList.push(newCommand);  
    }
    
    public void rollBackCommand(BinaryTree database)    {
        while( ! commandList.empty() )        {
            reverseCommand item = commandList.pop();
     //       System.out.println(item.command + ":" + item.name + ":" + item.value);
            // execute the command here
            
            switch (item.command) {
                case "SET":
                    database.setNode(item.name, item.value);
                    break;
                case "UNSET":
                    database.delete(item.name);
                    break;
            }
        }
    }
}
