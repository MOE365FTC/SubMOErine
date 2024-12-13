package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //DEVICES
    Servo outtakeClaw, outtakeTilt;
    DcMotor outtakeSlides;
    
    public enum OuttakeSlidePositions {
        WALL,
        CHAMBER,
        SCORE_CHAMBER,
        BASKET
    }


    public OuttakeSlidePositions curOuttakePos = OuttakeSlidePositions.WALL;
    public boolean isClawOpen = true;
    // PRESETS
    // TODO MAKE THIS STATIC ASAP AND CHANGE VALUES!!
    // FIXME EVERYTHING IS -1 [DO NOT RUN]
    public final int OuttakeSlideBase = 0, OuttakeSlideRung = 720, OuttakeSlideScoreRung = 1360, OuttakeSlideBasket = 3020;
    public final double ClawOpen = 0.5, ClawClose = 0.28;
    public boolean g2RightBumperPressed = false;
    public final double TiltWall = 0.95, TiltChamber = 0.4 , TiltBasket = 0.7, TiltTransfer = 0;

    public double OUTTAKE_MOTOR_POWER = 1;

    int position = 0;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Outtake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry, boolean isAuton, boolean actuateMotor) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        outtakeSlides = hardwareMap.get(DcMotor.class, "outtakeSlideMotor");
        outtakeTilt = hardwareMap.get(Servo.class, "outtakeTiltServo");
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClawServo");

//        outtakeSlides.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(actuateMotor) {
            if (isAuton) {
                outtakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                outtakeSlides.setTargetPosition(0);
            }
            outtakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //outtakeSlides.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void actuate() {
        outtakeSlides.setPower(OUTTAKE_MOTOR_POWER);
        if(this.gamepad2.dpad_down)  curOuttakePos = OuttakeSlidePositions.WALL;
        if(this.gamepad2.dpad_left)  curOuttakePos = OuttakeSlidePositions.CHAMBER;
        if(this.gamepad2.dpad_up)    curOuttakePos = OuttakeSlidePositions.SCORE_CHAMBER;
        if(this.gamepad2.dpad_right) curOuttakePos = OuttakeSlidePositions.BASKET;

        if(this.gamepad2.right_bumper && !g2RightBumperPressed) {
            g2RightBumperPressed = true;
            isClawOpen = !isClawOpen;
        }
        else if(!this.gamepad2.right_bumper) {
            g2RightBumperPressed = false;
        }
        if(isClawOpen) outtakeClaw.setPosition(ClawOpen);
        else           outtakeClaw.setPosition(ClawClose);

        this.telemetry.addData("outPos", curOuttakePos);
        this.telemetry.addData("currentPosition", outtakeSlides.getCurrentPosition());
        this.telemetry.addData("targetPosition", outtakeSlides.getTargetPosition());

        switch (curOuttakePos) {
            case WALL: {
                outtakeSlides.setTargetPosition(OuttakeSlideBase);
                outtakeTilt.setPosition(TiltWall);
                break;
            }
            case CHAMBER: {
                outtakeSlides.setTargetPosition(OuttakeSlideRung);
                outtakeTilt.setPosition(TiltChamber);
                break;
            }
            case SCORE_CHAMBER: {
                outtakeSlides.setTargetPosition(OuttakeSlideScoreRung);
                outtakeTilt.setPosition(TiltChamber);
                break;
            }
            case BASKET: {
                outtakeSlides.setTargetPosition(OuttakeSlideBasket);
                outtakeTilt.setPosition(TiltBasket);
                break;
            }
        }
    }

    public void autonInit(){
        outtakeSlides.setPower(OUTTAKE_MOTOR_POWER);
        outtakeSlides.setTargetPosition(OuttakeSlideBase);
        outtakeTilt.setPosition(0.17);
        outtakeClaw.setPosition(ClawClose);
    }

    public void testActuate(double position) {
        outtakeClaw.setPosition(position);
        outtakeTilt.setPosition(position);
    }

    public void testMotorActuate(double pos) {
//        position += (int)pos;
//

//        outtakeSlides.setTargetPosition(position);
        outtakeSlides.setPower(-pos);
        outtakeClaw.setPosition(ClawClose);
        outtakeTilt.setPosition(TiltChamber);

        this.telemetry.addData("Outtake Position", outtakeSlides.getCurrentPosition());
    }
}
