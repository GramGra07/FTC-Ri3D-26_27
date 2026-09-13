package org.firstinspires.ftc.teamcode.opModes.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoExtensions {
    public static Servo initServo(HardwareMap hw, String name) {
        return hw.get(Servo.class, name);
    }

    public static void setPose(Servo servo, double degrees) {
        double degreeMult = 0.00555555554;
        servo.setPosition(degreeMult * degrees);
    }

    public static int servoFlipVal = 62;
    public static double hcalc = 96.0;
    public static int lastSetVal = 0;
}
