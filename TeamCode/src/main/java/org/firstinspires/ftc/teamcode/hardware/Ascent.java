package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Ascent {
    //DEVICES
    DcMotor ascentLeft, ascentRight;
    Servo hopLeft, hopRight;

    //PRESETS
    // FIXME EVERYTHING IS -1 [DO NOT USE]

    public int ASCENT_TOP = -1;
    public double ASCENT_MOTOR_POWER = 0.8;
    public double HOP_TOP_LEFT = 0.85, HOP_BOTTOM_LEFT = 0.05;
    public double HOP_TOP_RIGHT = 0.93, HOP_BOTTOM_RIGHT = 0.1;
    public static double SPEED = 0.5;
    public double hopPosition = 0;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;


    public Ascent (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry, boolean isAuton) {
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        ascentRight = hardwareMap.get(DcMotor.class, "AscentRight");
        ascentLeft = hardwareMap.get(DcMotor.class, "AscentLeft");

        hopRight = hardwareMap.get(Servo.class, "HopRight");
        hopLeft = hardwareMap.get(Servo.class, "HopLeft");

        ascentRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        ascentMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ascentLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        ascentMotor.setDirection(DcMotorSimple.Direction.REVERSE);

//        hopRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        hopLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        if(isAuton) {
            ascentRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ascentRight.setTargetPosition(0);

            ascentLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ascentLeft.setTargetPosition(0);
        }
        //ascentMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ascentLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void actuate() {
        ascentRight.setPower(this.gamepad2.right_stick_y);
        ascentLeft.setPower(this.gamepad2.right_stick_y);
        if(gamepad1.x) {
            hopRight.setPosition(HOP_TOP_RIGHT);
            hopLeft.setPosition(HOP_TOP_LEFT);
        } else if(gamepad1.b){
            hopRight.setPosition(HOP_BOTTOM_RIGHT);
            hopLeft.setPosition(HOP_BOTTOM_LEFT);
        }
    }

    public void autonActuate() {
        ascentRight.setTargetPosition(ASCENT_TOP);
        ascentRight.setPower(ASCENT_MOTOR_POWER);
    }

    public void testMotorActuate(double pos) {
//        position += (int)pos;

//        ascentMotor.setTargetPosition(position);
        ascentRight.setPower(-pos);
        ascentLeft.setPower(-pos);

        this.telemetry.addData("Ascent Position:", ascentRight.getCurrentPosition());
        this.telemetry.addData("Ascent Power", ascentRight.getPower());
    }
}
