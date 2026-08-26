package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    List<DcMotor> motors = List.of(fl, fr, bl, br);
    List<String> motorNames = List.of("fl", "fr", "bl", "br");
    DrivePowerCoefficients powers;
    public DriveSubsystem(OpMode opMode){
        super(opMode.hardwareMap);
        gp1 = opMode.gamepad1;
        hwMap = opMode.hardwareMap;
        init();
    }


    @Override
    public void init() {
        fl = hwMap.get(DcMotor.class, motorNames.get(0));
        fr = hwMap.get(DcMotor.class, motorNames.get(1));
        bl = hwMap.get(DcMotor.class, motorNames.get(2));
        br = hwMap.get(DcMotor.class, motorNames.get(3));

        motors.forEach(dcMotor -> {
                    dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    dcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    dcMotor.setPower(0);
                }
        );
    }

    @Override
    public void update() {
        powers = MecanumDriver.driveMecanum(gp1.left_stick_x,gp1.left_stick_y,gp1.right_stick_x);
        fl.setPower(powers.getFrontLeft());
        fr.setPower(powers.getFrontRight());
        bl.setPower(powers.getBackLeft());
        br.setPower(powers.getBackRight());
    }

    @Override
    public void telemetry(@NonNull Telemetry telemetry) {
        motors.forEach(dcMotor -> telemetry.addData(motorNames.get(motors.indexOf(dcMotor)), dcMotor.getPower()));
    }
}
