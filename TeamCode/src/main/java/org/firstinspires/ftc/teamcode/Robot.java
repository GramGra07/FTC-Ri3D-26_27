package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;
import org.gentrifiedApps.gentrifiedAppsUtil.controllers.initMovement.InitMovementController;
import org.gentrifiedApps.gentrifiedAppsUtil.hardware.voltage.VoltageTracker;
import org.gentrifiedApps.gentrifiedAppsUtil.looptime.LoopTimeController;

public class Robot {
    private final OpMode opMode;

    public InitMovementController initMovementController;
    public LoopTimeController loopTimeController;
    public VoltageTracker voltageTracker;
    public DriveSubsystem drive;

    public Robot(OpMode opMode) {
        this.opMode = opMode;
        this.initMovementController = new InitMovementController(opMode.gamepad1, opMode.gamepad2);
        this.loopTimeController = new LoopTimeController();

        init();
    }

    public Telemetry telemetry() {
        return opMode.telemetry;
    }

    public HardwareMap hwMap() {
        return opMode.hardwareMap;
    }

    private void init() {
        telemetry().clearAll();
        Scribe.getInstance().logData("Starting init of Robot");
        voltageTracker = new VoltageTracker(hwMap());
        loopTimeController.setLoopSavingCache(hwMap());
        drive = new DriveSubsystem(opMode);
    }

    public void update() {
        if (initMovementController.hasMovedOnInit()) {
            Scribe.getInstance().logDebugOnce("Has moved on init");
            drive.update();
        } else {
            initMovementController.checkHasMovedOnInit();
        }
        voltageTracker.update();
        loopTimeController.update();
        buildTelemetry();
    }

    private void buildTelemetry() {
        loopTimeController.telemetry(telemetry());
        drive.telemetry(telemetry());
        voltageTracker.telemetry(telemetry());
        telemetry().update();
    }
}