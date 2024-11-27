package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer {

    //DEVICES
    CRServo roller;

    //PRESETS
    public static final double rollPower = 0.7;

    //USAGE
    Gamepad gamepad1;
    Telemetry telemetry;
    public Transfer(HardwareMap hardwareMap,  Gamepad gamepad1, Telemetry telemetry){
        this.gamepad1 = gamepad1;
        roller = hardwareMap.get(CRServo.class, "tRoll");
    }

    public void actuate() {
//        if (Intake.intoTransfer) roller.setPower(rollPower);
    }
}
