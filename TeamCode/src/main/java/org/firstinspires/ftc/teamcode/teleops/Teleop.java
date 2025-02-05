package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.MOEBot;

@TeleOp(group = "Match Tele-Op")
public class Teleop extends OpMode {

    MOEBot robot;

    public static boolean isAuton = true;

    @Override
    public void init() {
        robot = new MOEBot(hardwareMap, gamepad1, gamepad2, telemetry, isAuton);
    }

    @Override
    public void loop() {
        robot.chassis.imuTelemetry(telemetry);
        robot.chassis.odoTelemetry(telemetry);
        robot.chassis.fieldCentricDrive();
        robot.outtake.actuate();
        robot.intake.actuate();
//        robot.ascent.actuate();

    }
}
