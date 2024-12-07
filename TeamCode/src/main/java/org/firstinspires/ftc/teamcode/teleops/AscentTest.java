package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Ascent;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

@Config
@TeleOp(group = "test")
public class AscentTest extends OpMode {
    public static double position = 0.0;

    Ascent ascent;

    @Override
    public void init() {
        ascent = new Ascent(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void loop() {
        ascent.testActuate(gamepad1.left_stick_y);
    }
}