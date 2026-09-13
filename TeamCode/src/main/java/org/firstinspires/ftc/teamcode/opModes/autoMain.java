package org.firstinspires.ftc.teamcode.opModes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
//import org.firstinspires.ftc.teamcode.opModes.util.OpModeStorage;

@Autonomous
public class autoMain extends OpMode {
    Robot robot = null;


    @Override
    public void init() {
        robot = new Robot(this);

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
//        OpModeStorage.autonomousEndPose = robot.follower.pose(); //saves your position in that file
    }
}
