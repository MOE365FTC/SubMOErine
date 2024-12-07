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
    int position = 0;
    public Ascent (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        ascentMotor = hardwareMap.get(DcMotor.class, "ascentMotor");
    }

    public void actuate() {

    }

    public void testActuate(double pos) {
        position += (int)pos;
        ascentMotor.setTargetPosition(position);
        this.telemetry.addData("Ascent Position:", position);
    }
}
