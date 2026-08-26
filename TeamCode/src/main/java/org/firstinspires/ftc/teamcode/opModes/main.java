package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.Robot;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;

@TeleOp()
public class main extends LinearOpMode {
    @Override
    public void runOpMode() {
        Scribe.getInstance().startLogger(this);
        Robot robot = new Robot(this);
        waitForStart();
        while (opModeIsActive()) {
            robot.update();
        }
    }
}
