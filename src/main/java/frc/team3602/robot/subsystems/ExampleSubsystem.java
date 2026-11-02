package frc.team3602.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase{
    //Variables
    private final String word = "Hello, world!";

    public double number = 3.14;

    //Constructor
    public ExampleSubsystem(){
        //code here will be called one time when the robot code starts
    }

    //methods
    /**
     * Method that prints "This is a test" 
     */
    public Command testPrint(){
        return Commands.runOnce(()->{
             System.out.println("This is a test");
        });
    }

    /**
     * Method that references and prints the value of a variable
     */

     //TODO try replacing word with number
    public Command testVarPrint(){
        return Commands.runOnce(()->{
             System.out.println(word);
        });
    }

    /**
     * Method that prints the String given in the parameter
     * @param newWord is a message that will be printed
     */
    public Command testArgPrint(String newWord){
        return Commands.runOnce(()->{
            System.out.println(newWord);
        });
    }

}
