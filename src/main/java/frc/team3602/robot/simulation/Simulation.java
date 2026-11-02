package frc.team3602.robot.simulation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team3602.robot.subsystems.ElevatorSubsystem;

/**
 * Custom utils class to use in Simulation
 * requires adding each simulated subsystem and info
 * Author: C Furmanski (clydestale158)
 */
public class Simulation extends SubsystemBase {
    private ElevatorSubsystem elevSusbsys;

    private final Mech elevMech = new Mech("Elevator", 2.5, 3.5);
   
    /* Constructor */
    public Simulation(ElevatorSubsystem elevSubsys) {
        this.elevSusbsys = elevSubsys;

        elevMech.addViz("Carriage", 1.25, 0.1, elevSubsys.startingHeight, 90, 90, new Color8Bit(Color.kOrange));

        SmartDashboard.putData("Elev Mech", elevMech.getMech());
    }

    @Override
    public void simulationPeriodic() {
        elevMech.viz.setLength(elevSusbsys.elevSim.getPositionMeters());
    }
}
