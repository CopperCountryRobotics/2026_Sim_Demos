package frc.team3602.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.team3602.robot.simulation.Simulation;
import frc.team3602.robot.subsystems.ElevatorSubsystem;
import frc.team3602.robot.subsystems.ElevatorSubsystem.kPMode;

public class RobotContainer {
    /* Controllers */ 
    private CommandXboxController xbox = new CommandXboxController(0);
    private CommandXboxController xbox2 = new CommandXboxController(1);

    /* Subsystems */
    private ElevatorSubsystem elevSubsys = new ElevatorSubsystem();

    private Simulation simulation = new Simulation(elevSubsys);

    /*Constructor */
    public RobotContainer() {
        if (RobotBase.isSimulation()) {
            simConfigButtonBindings();
        } else {
            configButtonBindings();
        }
    }

    private void simConfigButtonBindings() {
        xbox.button(1).onTrue(elevSubsys.setHeight(0));
        xbox.button(2).onTrue(elevSubsys.setHeight(0.5));
        xbox.button(3).onTrue(elevSubsys.setHeight(1));
        xbox.button(4).onTrue(elevSubsys.setHeight(1.5));

        xbox2.button(1).onTrue(elevSubsys.changeMode(kPMode.Low));
        xbox2.button(2).onTrue(elevSubsys.changeMode(kPMode.Standard));
        xbox2.button(3).onTrue(elevSubsys.changeMode(kPMode.High));
    }

    private void configButtonBindings() {
        xbox.a().onTrue(elevSubsys.setHeight(0));
        xbox.b().onTrue(elevSubsys.setHeight(3));
    }
}
