package org.firstinspires.ftc.teamcode.subsystems;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opModes.config.IntakeConfig;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Subsystem;

public class IntakeSubsystem extends Subsystem {

    DcMotor intake = null;
    CRServo spinner = null;
    HardwareMap map = null;
    OpMode myOpMode;
    public IntakeSubsystem(OpMode opMode){
        super(opMode.hardwareMap);
        myOpMode = opMode;
        map = opMode.hardwareMap;
        init();
    }

    @Override
    public void init() {
        intake = map.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinner = map.get(CRServo.class, "spinner");
        Scribe.Companion.getInstance().logDebugOnce("Intake Initialized");
    }

    @Override
    public void update() {
        float triggerR = myOpMode.gamepad1.right_trigger;
        float triggerL = myOpMode.gamepad1.left_trigger;

        double scale = IntakeConfig.maxPower;
        double scaledR = triggerR*scale;
        double scaledL = -1*triggerL*scale;
        if (scaledR>0){
            intake.setPower(scaledR);
        }else if (scaledL<0){
            intake.setPower(scaledL);
        }else{
            intake.setPower(0);
        }

        if (myOpMode.gamepad1.right_bumper){
            spinner.setPower(1);
        }else{
            spinner.setPower(0);
        }
    }

    @Override
    public void telemetry(@NonNull Telemetry telemetry) {
        telemetry.addData("Intake Power",intake.getPower());
        telemetry.addData("Spinner Power", spinner.getPower());
    }
}
