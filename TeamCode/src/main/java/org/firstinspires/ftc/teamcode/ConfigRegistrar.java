package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigCreator;
import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigMaker;

public final class ConfigRegistrar {

    static ConfigMaker config = new ConfigMaker("Ri3DBot")
            .addModule_detect() // will automatically detect using the scan config
            .addIMU_detect(ConfigMaker.ModuleType.CONTROL_HUB)// ^ same
            .addMotor("fl", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.RevRoboticsUltraplanetaryHDHexMotor, 0)
            .addMotor("fr", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.RevRoboticsUltraplanetaryHDHexMotor, 2)
            .addMotor("bl", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.RevRoboticsUltraplanetaryHDHexMotor, 1)
            .addMotor("br", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.RevRoboticsUltraplanetaryHDHexMotor, 3);

    static boolean isEnabled = false;

    private ConfigRegistrar() {
    }

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if (!isEnabled) return;
        manager.register(config.metaData(), new ConfigCreator(config));
    }
}
