package org.firstinspires.ftc.teamcode.opModes.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.Servo;

@Utility
public class SetupServo extends LinearOpMode {
    private Servo servo;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Press square to set servo to 0.0", "");
            telemetry.update();
            while (!gamepad1.square && opModeIsActive() && !isStopRequested()) {
            }
            ServoExtensions.setPose(servo, 0.0);
            telemetry.addData("Press circle to set servo to 180", "");
            telemetry.update();
            while (!gamepad1.circle && opModeIsActive() && !isStopRequested()) {
            }
            ServoExtensions.setPose(servo, 180.0);
            telemetry.addData("Press triangle to set servo to 90", "");
            telemetry.update();
            while (!gamepad1.triangle && opModeIsActive() && !isStopRequested()) {
            }
            ServoExtensions.setPose(servo, 90.0);
        }
    }
}
