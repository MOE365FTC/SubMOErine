package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Ascent;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

@Config
@TeleOp(group = "test")
public class OuttakePositionTest extends OpMode {
    Outtake outtake;

    @Override
    public void init() {
        outtake = new Outtake(hardwareMap, gamepad1, gamepad2, telemetry, true, true);
    }

    @Override
    public void loop() {
        outtake.testMotorActuate(gamepad1.left_stick_y);
    }
}
