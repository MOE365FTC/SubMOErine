package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Ascent;

@Config
@TeleOp(group = "test")
public class AscentPositionTest extends OpMode {
    Ascent ascent;

    @Override
    public void init() {
        ascent = new Ascent(hardwareMap, gamepad1, gamepad2, telemetry, true);
    }

    @Override
    public void loop() {
        ascent.testMotorActuate(gamepad1.left_stick_y);

    }
}