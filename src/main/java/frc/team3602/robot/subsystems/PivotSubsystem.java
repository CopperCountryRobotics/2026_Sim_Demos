package frc.team3602.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class PivotSubsystem {
   // The first parameter is the PCM's CAN ID.
  // The second is the solenoid's channel number.
  Solenoid m_solenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);

    /*Constructor */
    public PivotSubsystem(){}
}
