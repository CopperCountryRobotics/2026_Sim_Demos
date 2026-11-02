package frc.team3602.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.team3602.robot.subsystems.ExampleSubsystem;

public class RobotContainer {
    /* Controllers */
    private CommandXboxController xbox = new CommandXboxController(0);

    /* Subsystems */
    private ExampleSubsystem exampleSubsys = new ExampleSubsystem();

    /* Constructor */
    public RobotContainer() {
        simConfigButtonBindings();
    }

    private void simConfigButtonBindings() {
        xbox.button(1).onTrue(exampleSubsys.testPrint());//Go to this method to find the message it will print
        xbox.button(2).onTrue(exampleSubsys.testVarPrint());//Go to this method and change the variable
        xbox.button(3).onTrue(exampleSubsys.testArgPrint("This is an argument or a parameter"));//Change the parameter here
    }

}
