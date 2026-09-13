package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.rr.MecanumDrive;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.StartLocation;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Subsystem;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.drive.DrivePowerCoefficients;
import org.gentrifiedApps.gentrifiedAppsUtil.drive.MecanumDriver;
import org.gentrifiedApps.gentrifiedAppsUtil.heatseeker.Driver;

import java.lang.reflect.Array;
import java.util.List;

public class DriveSubsystem extends Subsystem {
    HardwareMap hwMap;
    Gamepad gp1;
    DcMotor fl, fr, bl, br;
    List<DcMotor> motors;
    List<String> motorNames = List.of("fl", "fr", "bl", "br");
    DrivePowerCoefficients powers;
    StartLocation startLocation;
    public DriveSubsystem(OpMode opMode, StartLocation _loc ){
        super(opMode.hardwareMap);
        gp1 = opMode.gamepad1;
        hwMap = opMode.hardwareMap;
        startLocation = _loc;
        init();
    }


    @Override
    public void init() {
        fl = hwMap.get(DcMotor.class, motorNames.get(0));
        fr = hwMap.get(DcMotor.class, motorNames.get(1));
        bl = hwMap.get(DcMotor.class, motorNames.get(2));
        br = hwMap.get(DcMotor.class, motorNames.get(3));
        motors = List.of(fl, fr, bl, br);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        motors.forEach(dcMotor -> {
                    dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    dcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    dcMotor.setPower(0);
                }
        );
        Scribe.getInstance().logDebugOnce("Drive Initialized");
    }

    @Override
    public void update() {
        powers = MecanumDriver.driveMecanum(-gp1.left_stick_x,gp1.left_stick_y,gp1.right_stick_x);
        fl.setPower(powers.getFrontLeft());
        fr.setPower(powers.getFrontRight());
        bl.setPower(powers.getBackLeft());
        br.setPower(powers.getBackRight());
//        drive.updatePoseEstimate();
    }
    public void doTurn(double _t){
        powers = MecanumDriver.driveMecanum(-gp1.left_stick_x,gp1.left_stick_y,_t);
        fl.setPower(powers.getFrontLeft());
        fr.setPower(powers.getFrontRight());
        bl.setPower(powers.getBackLeft());
        br.setPower(powers.getBackRight());
//        drive.updatePoseEstimate();
    }

    @Override
    public void telemetry(@NonNull Telemetry telemetry) {
        motors.forEach(dcMotor -> telemetry.addData(motorNames.get(motors.indexOf(dcMotor)), dcMotor.getPower()));
//        telemetry.addLine(String.format("Pose (%3.2f,%3.2f,%3.2f)",drive.localizer.getPose().position.x,drive.localizer.getPose().position.y,drive.localizer.getPose().heading.toDouble()));
    }
}
