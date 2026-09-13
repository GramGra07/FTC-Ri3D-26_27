package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Subsystem;
import org.gentrifiedApps.gentrifiedAppsUtil.motion.controllers.PIDController;

@Config
public class ShooterSubsystem extends Subsystem {
    DcMotorEx shooter;
    DcMotorEx shooter2;
    public static double pow = 1.0;
    public static double maxVelocity = 3000;
    public static double STOP = 0;
    public static double targetVelocity = STOP;
    public enum  Targets{
        k3(3000),
        k2(2000),
        k0(STOP);
        final double speed;

        Targets(double speed) {
            this.speed = speed;
        }
    }
    public static PIDFCoefficients coefs = new PIDFCoefficients(0,0,0,0);
    PIDController pid = new PIDController(coefs.p,coefs.i,coefs.d);
    public static PIDFCoefficients headingCoefs = new PIDFCoefficients(0,0,0,0);
    PIDController pidHead = new PIDController(headingCoefs.p, headingCoefs.i, headingCoefs.d);
    public static double TARGET_HEADING = 0;
public void updatePID(){
    pid.setPID(coefs.p, coefs.i, coefs.d);
    pidHead.setPID(headingCoefs.p,headingCoefs.i, headingCoefs.d);
}
    public ShooterSubsystem(@NonNull HardwareMap hwMap) {
        super(hwMap);
        shooter = hwMap.get(DcMotorEx.class,"shooter");
        shooter2 = hwMap.get(DcMotorEx.class,"shooter2");
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void init() {
        shooter.setVelocity(STOP);
        shooter2.setVelocity(STOP);
    }
    public double p1;
public void setP1 (double _p){
    p1 = _p;
}

public double p2;
public void setP2(double _p){
    p2 = _p;
}
    @Override
    public void update() {
//    updatePID();
//    double velo = shooter.getVelocity();
//    double power = pid.calculate(velo,targetVelocity);
//    shooter.setPower(power);
    shooter.setPower(p1);
    shooter2.setPower(p2);
    }

    public void setTargetVelocity(Targets _t){
        targetVelocity = _t.speed;
    }
    public void kill(){
    setTargetVelocity(Targets.k0);
    }

    public double turn(double roll){
        return pidHead.calculate(roll, TARGET_HEADING);
    }

    @Override
    public void telemetry(@NonNull Telemetry telemetry) {
        double velo = shooter.getVelocity();
        telemetry.addLine(String.format("Shooter \n Velo %6.4f\nPower %6.4f\nTarget %6.4f",velo,shooter.getPower(),targetVelocity));
    }
}
