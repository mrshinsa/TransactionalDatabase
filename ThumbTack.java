/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thumbtack;

import java.util.Scanner;

/**
 *
 * @author kevlee2
 */
public class ThumbTack {

    static BinaryTree database;
    static TransactionalBlockList transaction;

    private static void set(String name, Integer value)    {
        transaction.addCommand("SET", name, value, database);
        database.setNode(name, value);
    }
    private static void get(String name)    {
        System.out.println(database.getNode(name));
    }

    private static void unset(String name)    {
        transaction.addCommand("UNSET", name, 0, database);
        database.delete(name);
    }

    private static void numEqualTo(Integer value)    {
        database.countNode(value);
    }

    private static void end()    {     
        System.out.println("As you wish... ");   
    }

    private static void begin()    {        
        transaction.beginTransaction();    
    }

    private static void rollback()    {
        transaction.rollBackTransaction(database);
    }

    private static void commit()    {
        transaction.commitTransaction();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here              
        
        database = new BinaryTree();
        database.BinaryTree();
        transaction = new TransactionalBlockList();
        
        Scanner in = new Scanner(System.in);
        Boolean keepGoing = true;                
        String command; 
        String name; 
        Integer value;
        
        while (keepGoing){  
            System.out.println("Master, what is thy bidding?> ");
            System.out.print(":>");
            command = in.next();
            switch (command.toUpperCase()) {
                case "SET":
                    name = in.next();                 
                    try {
                        value = Integer.parseInt( in.next() );       
                        set(name, value);
                    } catch (NumberFormatException e) {
                        System.err.println("Argument 3" + " must be an integer");
                    }     
                    break;
                case "GET":
                    name = in.next(); 
                    get(name);                    
                    break;
                case "UNSET":
                    name = in.next(); 
                    unset(name); 
                    break;
                case "NUMEQUALTO":                     
                    try {
                        value = Integer.parseInt( in.next() ); 
                        numEqualTo(value);
                    } catch (NumberFormatException e) {
                        System.err.println("Argument 2" + " must be an integer");
                    }   
                    break;
                case "END":
                    end();
                    keepGoing = false;
                    break;
                case "BEGIN":
                    begin();
                    break;
                case "ROLLBACK":
                    rollback(); 
                    break;
                case "COMMIT":
                    commit();
                    break;
                default :
                    System.err.println("Please enter a valid command...");
            }      
        }
        System.exit(0);
    }
}
    
