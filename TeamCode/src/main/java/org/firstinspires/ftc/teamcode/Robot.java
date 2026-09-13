package org.firstinspires.ftc.teamcode;


import static java.util.concurrent.locks.LockSupport.park;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ATagCluster;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.StartLocation;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.generics.Alliance;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.generics.pointClasses.Target2D;
import org.gentrifiedApps.gentrifiedAppsUtil.controllers.initMovement.InitMovementController;
import org.gentrifiedApps.gentrifiedAppsUtil.hardware.voltage.VoltageTracker;
import org.gentrifiedApps.gentrifiedAppsUtil.looptime.LoopTimeController;

public class Robot {
    protected final OpMode opMode;

    public InitMovementController initMovementController;
    public LoopTimeController loopTimeController;
    public VoltageTracker voltageTracker;
    public DriveSubsystem drive;
    public IntakeSubsystem intake;
    public ShooterSubsystem shooter;
    FtcDashboard dashboard = null;
    Telemetry telemetry = null;
    ATagCluster tag = new ATagCluster();
    public Robot(OpMode opMode) {
        this.opMode = opMode;
        this.initMovementController = new InitMovementController(opMode.gamepad1, opMode.gamepad2);
        this.loopTimeController = new LoopTimeController();
        dashboard = FtcDashboard.getInstance();
        this.telemetry  = new MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().getTelemetry());

        init();
    }

    public Telemetry telemetry() {
        return this.telemetry;
    }

    public HardwareMap hwMap() {
        return opMode.hardwareMap;
    }

    private void init() {
        telemetry().clearAll();
        Scribe.getInstance().logData("Starting init of Robot");
        voltageTracker = new VoltageTracker(hwMap());
        loopTimeController.setLoopSavingCache(hwMap());
        drive = new DriveSubsystem(opMode, new StartLocation(Alliance.BLUE, new Target2D(0.0,0.0,0.0)));
        intake = new IntakeSubsystem(opMode);
        shooter = new ShooterSubsystem(opMode.hardwareMap);
        tag.init(hwMap());
        tag.sendToDash();
    }

    public void update() {
        if (initMovementController.hasMovedOnInit()) {
            Scribe.getInstance().logDebugOnce("Has moved on init");
            intake.update();
//            if (opMode.gamepad1.b) shooter.kill();
//            if (opMode.gamepad1.y) shooter.setTargetVelocity(ShooterSubsystem.Targets.k3);
//            if (opMode.gamepad1.x) shooter.setTargetVelocity(ShooterSubsystem.Targets.k2);
            if (opMode.gamepad1.dpad_up) {
                shooter.setP1(ShooterSubsystem.pow);
            }else  if (opMode.gamepad1.dpad_down) {
                shooter.setP1(-ShooterSubsystem.pow);
            }else{
                shooter.setP1(0);
            }
            if (opMode.gamepad1.dpad_left){
                shooter.setP2(ShooterSubsystem.pow);
            }else if (opMode.gamepad1.dpad_right){
                shooter.setP2(-ShooterSubsystem.pow);
            }else{
                shooter.setP2(0);
            }
            if (opMode.gamepad1.a){

                drive.doTurn(shooter.turn(tag.RANGE()));
            }else {
                drive.update();
            }
            shooter.update();
        } else {
            initMovementController.checkHasMovedOnInit();
        }
        tag.RANGE();
        voltageTracker.update();
        loopTimeController.update();
        buildTelemetry();
    }

    private void buildTelemetry() {
        tag.telemetry(telemetry());
        loopTimeController.telemetry(telemetry());
        drive.telemetry(telemetry());
        intake.telemetry(telemetry());
        voltageTracker.telemetry(telemetry());
        telemetry().update();
    }
}