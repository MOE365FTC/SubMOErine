package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.MOEBot;

@TeleOp(group = "Match Tele-Op")
public class Teleop extends OpMode {
    MOEBot robot;

    @Override
    public void init() {
        robot = new MOEBot(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void loop() {
        robot.chassis.imuTelemetry(telemetry);
        robot.chassis.odoTelemetry(telemetry);
        robot.chassis.fieldCentricDrive();
    }
}
