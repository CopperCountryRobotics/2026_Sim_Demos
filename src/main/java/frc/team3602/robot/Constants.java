/*
 * Copyright (C) 2025 Team 3602 All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found
 * in the root directory of this project.
 */

package frc.team3602.robot;

public final class Constants {
    public class HardwareConstants{
        /* Can IDs */
        //can ids 40 - 51 are used in the drivetrain
        public final static int ELEV_LEAD_MOTOR_ID = 53;
        public final static int ELEV_FOLLOW_MOTOR_ID = 57;
        public final static int PIVOT_MOTOR_ID = 52;
        public final static int INTAKE_MOTOR_ID = 56;
        public final static double CLIMBER_MOTOR_ID = 18;
        public final static int PIVOT_CANCODER_ID = 34;


        // gearing and current limits
        public final static double ELEV_GEARING = 12;
        public final static double ELEV_CURRENT_LIMIT = 35;

        public final static double CLIMB_GEARING = 6;
        public final static double CLIMB_CURRENT_LIMIT = 30;

        public final static double PIVOT_GEARING = 12;
        public final static double PIVOT_CURRENT_LIMIT = 30;
        
    }
}