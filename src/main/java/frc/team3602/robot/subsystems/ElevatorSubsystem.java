package frc.team3602.robot.subsystems;

import static frc.team3602.robot.Constants.HardwareConstants.*;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** just an elevator subsystem that does NOT use motion magic. **USE THIS** */
public class ElevatorSubsystem extends SubsystemBase {
    // Motors
    private final TalonFX motor = new TalonFX(ELEV_LEAD_MOTOR_ID);
    private final TalonFX followerMotor = new TalonFX(ELEV_FOLLOW_MOTOR_ID);

    // Controllers
    private PIDController controller;
    private ElevatorFeedforward ffeController;

    private PIDController controllerLow; // ONLY FOR CCR DEMO
    private PIDController controllerHigh; // ONLY FOR CCR DEMO

    public enum kPMode { // ONLY FOR CCR DEMO
        High,
        Low,
        Standard
    }

    private kPMode mode = kPMode.Standard; // ONLY FOR CCR DEMO

    // Setpoints
    public final double startingHeight = 0;
    private double setpoint = startingHeight;

    // Elev sim
    public final ElevatorSim elevSim = new ElevatorSim(DCMotor.getKrakenX60(2), (0.125), 0.1, 0.005, -0.01, 3.0, true,
            0.2);

    /** Constructor */
    public ElevatorSubsystem() {
        if (RobotBase.isSimulation()) {
            ffeController = new ElevatorFeedforward(0.1, 0.1, 0);

            controller = new PIDController(18, 0, 0);// todo tune eventually

            controllerLow = new PIDController(1, 0, 0); // ONLY FOR CCR DEMO
            controllerHigh = new PIDController(200, 0, 0); // ONLY FOR CCR DEMO

            controller.enableContinuousInput(-0.01, 3);
            controllerLow.enableContinuousInput(-0.01, 3);
            controllerHigh.enableContinuousInput(-0.01, 3);

        } else {
            controller = new PIDController(0.2, 0, 0.001);// .54kp
            ffeController = new ElevatorFeedforward(2.0, 0.27, 0.0, 0.0);
        }

        // configurations
        TalonFXConfiguration cfg = new TalonFXConfiguration();

        MotorOutputConfigs outputCfg = cfg.MotorOutput;
        outputCfg.NeutralMode = NeutralModeValue.Brake;
        outputCfg.Inverted = InvertedValue.CounterClockwise_Positive;

        CurrentLimitsConfigs limitCfg = cfg.CurrentLimits;
        limitCfg.StatorCurrentLimit = ELEV_CURRENT_LIMIT;

        // config application
        motor.getConfigurator().apply(cfg);
        followerMotor.getConfigurator().apply(cfg);

        // setting the control of the follower motor to be a follower of our lead motor
        followerMotor.setControl(new Follower(ELEV_LEAD_MOTOR_ID, MotorAlignmentValue.Aligned));
    }

    /** Run once command that changes the setpoint of the elevator */
    public Command setHeight(double newHeight) {
        return runOnce(() -> {
            setpoint = newHeight;
        });
    }

    /**
     * method that returns the elevator rotor position (or meters in a simulation)
     */
    public double getEncoder() {
        if (Utils.isSimulation()) {
            return elevSim.getPositionMeters();
        } else {
            return motor.getRotorPosition().getValueAsDouble() * (Math.PI * 2.15) / 12;
        }
    }

    /** returns the combined calculated effort of our ffe and pid controllers */
    private double getEffort() {
        if (RobotBase.isSimulation()) { // if else statements with mode are only for CCR DEMO
            if (mode == kPMode.Standard) {
                return ffeController.calculate(0)
                        + controller.calculate(getEncoder(), setpoint);
            } else if (mode == kPMode.Low) {
                return ffeController.calculate(0)
                        + controllerLow.calculate(getEncoder(), setpoint);
            } else {
                return ffeController.calculate(0)
                        + controllerHigh.calculate(getEncoder(), setpoint);
            }
        } else {
            return ffeController.calculate(0)
                    + controller.calculate(getEncoder(), setpoint);
        }
    }

    /**
     * returns true if the elev encoder and setpoint are within 1.5 units of each
     * other
     */
    public boolean isNearGoal() {
        if (RobotBase.isSimulation()) {
            return MathUtil.isNear(setpoint, getEncoder(), 0.08);
        } else {
            return MathUtil.isNear(setpoint, getEncoder(), 1.5);
        }
    }

    /**
     * ONLY FOR CCR DEMO
     * changes "Mode" to be able to show off different KP's
     */
    public Command changeMode(kPMode mode) {
        return runOnce(() -> {
            this.mode = mode;
        });
    }

    @Override
    public void simulationPeriodic() {
        // update the elev sim
        elevSim.setInput(getEffort());
        elevSim.update(0.0005);

        SmartDashboard.putString("Elevator Mode", mode.toString()); // ONLY FOR CCR DEMO
    }

    @Override
    public void periodic() {
        // updating dashboard
        SmartDashboard.putNumber("Elevator encoder", getEncoder());
        SmartDashboard.putNumber("Elevator setpoint", setpoint);
        SmartDashboard.putNumber("Elevator set voltage", motor.getMotorVoltage().getValueAsDouble());
        SmartDashboard.putNumber("Elevator calculated voltage", getEffort());

        // update motor voltage with calculated PID + FFE efforts
        motor.setVoltage(getEffort());

    }
}
