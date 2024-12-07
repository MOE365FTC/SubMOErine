package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

@Config
@TeleOp(group = "test")
public class ServoTest extends OpMode {
    public static double position = 0.0;

    Intake intake;
    Outtake outtake;

    @Override
    public void init() {
        intake = new Intake(hardwareMap, gamepad1, gamepad2, telemetry);
        outtake = new Outtake(hardwareMap, gamepad1, gamepad2, telemetry, false, false);
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            intake.testActuate(position);
            outtake.testActuate(position);
        }
    }
}