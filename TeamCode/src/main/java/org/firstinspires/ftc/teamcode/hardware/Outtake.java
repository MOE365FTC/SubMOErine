package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

public class Outtake {
    //DEVICES
    Servo outtakeClaw;
    Servo outtakeTiltRight, outtakeTiltLeft;
    Servo outtakeWrist;
    DcMotor outtakeSlidesRight, outtakeSlidesLeft;
    
    public enum OuttakeSlidePositions {
        WALL,
        SCORE_CHAMBER,
        BASKET,
        TRANSFER
    }


    public OuttakeSlidePositions curOuttakePos = OuttakeSlidePositions.WALL;
    public boolean isClawOpen = true;
    // PRESETS
    // TODO MAKE THIS STATIC ASAP AND CHANGE VALUES!!
    // FIXME EVERYTHING IS -1 [DO NOT RUN]
    public final int OuttakeSlideBase = 0, OuttakeSlideScoreRung = 1300, OuttakeSlideBasket = 2400, OuttakeSlideTransfer = 500, OuttakeSlideMax = 3200;
    public final double ClawOpen = 0.7, ClawClose = 0.87;
    public boolean g2RightBumperPressed = false;
    public static double TiltWallRight = 0.15, TiltChamberRight = 0.97, TiltBasketRight = 0, TiltTransferRight = 0.6;
    public final double TiltWallLeft = 1 - TiltWallRight, TiltChamberLeft = 1 - TiltChamberRight , TiltBasketLeft = 1 - TiltBasketRight, TiltTransferLeft = 1 - TiltTransferRight;
    public final double WristWall = 0.4, WristChamber = 0.05, WristBasket = 0.3, WristTransfer = 0.5;
    public double OUTTAKE_MOTOR_POWER = 1;

    public static double ELAPSED_TIME_CHAMBER = 0.25;

    public List<Action> runningActions = new ArrayList<>();

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    ElapsedTime runTime = new ElapsedTime();

    public Outtake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry, boolean isAuton, boolean actuateMotor) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        outtakeSlidesRight = hardwareMap.get(DcMotor.class, "SlidesRight");
        outtakeSlidesLeft = hardwareMap.get(DcMotor.class, "SlidesLeft");
        outtakeTiltRight = hardwareMap.get(Servo.class, "ShoulderRight");
        outtakeTiltLeft = hardwareMap.get(Servo.class, "ShoulderLeft");

        outtakeClaw = hardwareMap.get(Servo.class, "OuttakeClaw");

        outtakeWrist = hardwareMap.get(Servo.class, "OuttakeWrist");

        outtakeSlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(actuateMotor) {
            if (isAuton) {
                outtakeSlidesRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                outtakeSlidesRight.setTargetPosition(0);

                outtakeSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                outtakeSlidesLeft.setTargetPosition(0);
            }
            outtakeSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            outtakeSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//            outtakeSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            outtakeSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void actuate() {
        outtakeSlidesRight.setPower(OUTTAKE_MOTOR_POWER);
        outtakeSlidesLeft.setPower(OUTTAKE_MOTOR_POWER);

        if(this.gamepad2.dpad_down)  curOuttakePos = OuttakeSlidePositions.WALL;
        if(this.gamepad2.dpad_up)    curOuttakePos = OuttakeSlidePositions.SCORE_CHAMBER;
        if(this.gamepad2.dpad_right) curOuttakePos = OuttakeSlidePositions.BASKET;
        if(this.gamepad2.dpad_left) curOuttakePos = OuttakeSlidePositions.TRANSFER;

        if(this.gamepad2.right_bumper && !g2RightBumperPressed) {
            g2RightBumperPressed = true;
            isClawOpen = !isClawOpen;
        } else if(!this.gamepad2.right_bumper) {
            g2RightBumperPressed = false;
        }
        if(isClawOpen) {
            outtakeClaw.setPosition(ClawOpen);
        } else {
            outtakeClaw.setPosition(ClawClose);
        }

        this.telemetry.addData("outPos", curOuttakePos);
        this.telemetry.addData("currentPosition", outtakeSlidesRight.getCurrentPosition());
        this.telemetry.addData("targetPosition", outtakeSlidesRight.getTargetPosition());

        switch (curOuttakePos) {
            case WALL: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideBase);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideBase);
                outtakeTiltRight.setPosition(TiltWallRight);
                outtakeTiltLeft.setPosition(TiltWallLeft);
                outtakeWrist.setPosition(WristWall);
                break;
            }

            case SCORE_CHAMBER: {
                runTime.reset();
                outtakeSlidesRight.setTargetPosition(OuttakeSlideScoreRung);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideScoreRung);
                outtakeTiltRight.setPosition(TiltChamberRight);
                outtakeTiltLeft.setPosition(TiltChamberLeft);
                outtakeWrist.setPosition(WristChamber);
                break;
            }

            case BASKET: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideBasket);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideBasket);
                outtakeTiltRight.setPosition(TiltBasketRight);
                outtakeTiltLeft.setPosition(TiltBasketLeft);
                outtakeWrist.setPosition(WristBasket);
                break;
            }

            case TRANSFER: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideTransfer);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideTransfer);
                outtakeTiltRight.setPosition(TiltTransferRight);
                outtakeTiltLeft.setPosition(TiltTransferLeft);
                outtakeWrist.setPosition(WristTransfer);
                break;
            }
        }
    }

    public void autonInit(){
        outtakeSlidesRight.setPower(OUTTAKE_MOTOR_POWER);
        outtakeSlidesRight.setTargetPosition(OuttakeSlideBase);

        outtakeSlidesLeft.setPower(OUTTAKE_MOTOR_POWER);
        outtakeSlidesLeft.setTargetPosition(OuttakeSlideBase);

//        outtakeTilt.setPosition(0.17);
        outtakeClaw.setPosition(ClawClose);
    }

    public void testActuate(double position) {
        outtakeClaw.setPosition(position);
        outtakeTiltRight.setPosition(position);
        outtakeTiltLeft.setPosition(position);
        outtakeWrist.setPosition(position);
    }

    public void testMotorActuate(double pos) {
//        position += (int)pos;
//

//        outtakeSlides.setTargetPosition(position);

        if(Math.abs(pos) < 0.1) pos = -0.1;
        outtakeSlidesRight.setPower(-pos);
        outtakeSlidesLeft.setPower(-pos);
        outtakeClaw.setPosition(ClawClose);
        outtakeTiltRight.setPosition(TiltChamberRight);
        outtakeTiltLeft.setPosition(TiltChamberLeft);
        outtakeWrist.setPosition(WristChamber);

        this.telemetry.addData("Outtake Position Right", outtakeSlidesRight.getCurrentPosition());
        this.telemetry.addData("Outtake Position Left", outtakeSlidesLeft.getCurrentPosition());
    }

    public void autonActuate(OuttakeSlidePositions slidePositions) {
        switch (slidePositions) {
            case WALL: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideBase);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideBase);
                outtakeTiltRight.setPosition(TiltWallRight);
                outtakeTiltLeft.setPosition(TiltWallLeft);
                outtakeWrist.setPosition(WristWall);
                break;
            }
            
            case SCORE_CHAMBER: {
                runTime.reset();
                outtakeSlidesRight.setTargetPosition(OuttakeSlideScoreRung);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideScoreRung);
                outtakeTiltRight.setPosition(TiltChamberRight);
                outtakeTiltLeft.setPosition(TiltChamberLeft);
                outtakeWrist.setPosition(WristChamber);
                break;
            }

            case BASKET: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideBasket);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideBasket);
                outtakeTiltRight.setPosition(TiltBasketRight);
                outtakeTiltLeft.setPosition(TiltBasketLeft);
                outtakeWrist.setPosition(WristBasket);
                break;
            }

            case TRANSFER: {
                outtakeSlidesRight.setTargetPosition(OuttakeSlideTransfer);
                outtakeSlidesLeft.setTargetPosition(OuttakeSlideTransfer);
                outtakeTiltRight.setPosition(TiltTransferRight);
                outtakeTiltLeft.setPosition(TiltTransferLeft);
                outtakeWrist.setPosition(WristTransfer);
                break;
            }
        }
    }

    public Action autonWall(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtakeTiltRight.setPosition(TiltWallRight);
                outtakeTiltLeft.setPosition(TiltWallLeft);
                outtakeClaw.setPosition(ClawOpen);
                outtakeWrist.setPosition(WristWall);
                autonActuate(OuttakeSlidePositions.WALL);
                return false;
            }
        };
    }

    public Action openClaw(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtakeClaw.setPosition(ClawOpen);
                return false;
            }
        };
    }

    public Action autonScoreChamber(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtakeClaw.setPosition(ClawClose);
                autonActuate(OuttakeSlidePositions.SCORE_CHAMBER);
                return false;
            }
        };
    }

    public Action autonBasket(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                autonActuate(OuttakeSlidePositions.BASKET);
                return false;
            }
        };
    }
}
