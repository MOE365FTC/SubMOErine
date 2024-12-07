package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Ascent {
    //DEVICES
    DcMotor ascentMotor;

    //PRESETS
    // FIXME EVERYTHING IS -1 [DO NOT USE]

    public int ASCENT_TOP = 3400;

    public double ASCENT_MOTOR_POWER = 0.8;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    int position = 0;

    public Ascent (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry, boolean isAuton) {
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        ascentMotor = hardwareMap.get(DcMotor.class, "ascentMotor");
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(isAuton) {
            ascentMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ascentMotor.setTargetPosition(0);
        }
        ascentMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void actuate() {
        ascentMotor.setPower(this.gamepad2.right_stick_y);
    }

    public void autonActuate() {
        ascentMotor.setTargetPosition(ASCENT_TOP);
        ascentMotor.setPower(ASCENT_MOTOR_POWER);
    }

    public void testMotorActuate(double pos) {
//        position += (int)pos;

//        ascentMotor.setTargetPosition(position);
        ascentMotor.setPower(-pos);

        this.telemetry.addData("Ascent Position:", ascentMotor.getCurrentPosition());
        this.telemetry.addData("Ascent Power", ascentMotor.getPower());
    }
}
