package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Ascent {
    //DEVICES
    DcMotor ascentMotor;


    //PRESETS

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Ascent (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        ascentMotor = hardwareMap.get(DcMotor.class, "ascentMotor");
    }

    public void actuate() {

    }
}
