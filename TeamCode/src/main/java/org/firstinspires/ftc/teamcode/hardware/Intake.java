package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    //DEVICES
    CRServo roller;
    Servo tilt;
    DcMotor intakeSlides;

    //PRESETS
    public static final double extendPower = 0.7, rollPower = 0.7; //NEEDS TESTING
    public static final double intakeTilt = 0, startTilt = 0.4, transferTilt = 0.7; //NEEDS TESTING
    public static final int intakeSlidesBase = 0, intakeSlidesTransfer = 200, intakeSlidesExtend = 1100; //NEEDS TESTING

    public static int intakeSlidesTarget = intakeSlidesBase;
    public static final int intakeSlidesTargetOffset = 25;

    //COMPATIBILITY
    public static boolean intoTransfer = false;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Intake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        //vars
        this.gamepad1 = gamepad1;

        tilt = hardwareMap.get(Servo.class, "tilt");
        roller = hardwareMap.get(CRServo.class, "iRoll");
        intakeSlides = hardwareMap.get(DcMotorEx.class, "intakeSlides");

        intakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        intakeSlides.setDirection(DcMotorSimple.Direction.REVERSE);

        //init
        tilt.setPosition(startTilt);
    }

    public void actuate() {
        //roller run
        if(gamepad1.right_trigger>0.7) roller.setPower(rollPower);
        else roller.setPower(0);

        //intake slide presets
//        if(gamepad1.dpad_up) intakeSlidesTarget = intakeSlidesExtend;
//        else if(gamepad1.dpad_right) intakeSlidesTarget = intakeSlidesTransfer;
//        else if(gamepad1.dpad_down) intakeSlidesTarget = intakeSlidesBase;

        //intake slide manual control
        if (gamepad2.right_stick_y > 0.3) {
            intakeSlidesTarget += intakeSlidesTargetOffset;
        } else if (gamepad2.right_stick_y < -0.3){
            intakeSlidesTarget -= intakeSlidesTargetOffset;
        }

        //tilt mechanism preset switch
        if (gamepad1.right_bumper) swapTiltPreset();

        extendIntakeSlides(intakeSlidesTarget);
    }

    //extends intake slides to target pos
    public void extendIntakeSlides(int targetPos){
        intakeSlides.setTargetPosition(targetPos);
        intakeSlides.setPower(extendPower);
    }

    //swaps the tilt servo pos between intake and transfer
    public void swapTiltPreset(){
        if (tilt.getPosition() == intakeTilt || tilt.getPosition() == startTilt) {
            tilt.setPosition(transferTilt);
            intoTransfer = true;
        }
        else if (tilt.getPosition() == transferTilt || tilt.getPosition() == startTilt) {
            tilt.setPosition(intakeTilt);
            intoTransfer = false;
        }
        else telemetry.addData("Tilt pos(not intake or transfer preset):", tilt.getPosition()); // shouldn't happen
    }
}
