package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.MOEBot;

@Autonomous
public class SubMOErineLeft extends LinearOpMode {
    MOEBot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MOEBot(hardwareMap, gamepad1, gamepad2, telemetry, true);
        robot.outtake.autonInit();
        robot.intake.actuate();

        waitForStart();
    }
}