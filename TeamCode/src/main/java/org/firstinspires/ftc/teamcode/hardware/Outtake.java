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
    public final int OuttakeSlideBase = -1, OuttakeSlideRung = -1, OuttakeSlideScoreRung = -1, OuttakeSlideBasket = -1;
    public final double ClawOpen = 0.5, ClawClose = 0.25;
    public boolean g2RightBumperPressed = false;
    public final double TiltWall = 0.95, TiltChamber = 0.2 , TiltBasket = 0.7, TiltTransfer = 0;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Outtake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.gamepad2 = gamepad2;

        outtakeSlides = hardwareMap.get(DcMotor.class, "outtakeSlideMotor");
        outtakeTilt = hardwareMap.get(Servo.class, "outtakeTiltServo");
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClawServo");

        outtakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void actuate() {
        if(this.gamepad2.dpad_down)     curOuttakePos = OuttakeSlidePositions.WALL;
        if(this.gamepad2.dpad_up)       curOuttakePos = OuttakeSlidePositions.CHAMBER;
        if(this.gamepad2.dpad_right)    curOuttakePos = OuttakeSlidePositions.SCORE_CHAMBER;

        if(this.gamepad2.right_bumper && !g2RightBumperPressed) {
            g2RightBumperPressed = true;
            isClawOpen = !isClawOpen;
        }
        else if(!this.gamepad2.right_bumper) {
            g2RightBumperPressed = false;
        }
        if(isClawOpen)  outtakeClaw.setPosition(ClawOpen);
        else            outtakeClaw.setPosition(ClawClose);

        switch (curOuttakePos) {
            case WALL: {
//                outtakeSlides.setTargetPosition(OuttakeSlideBase);
                outtakeTilt.setPosition(TiltWall);
            }
            case CHAMBER: {
//                outtakeSlides.setTargetPosition(OuttakeSlideRung);
                outtakeTilt.setPosition(TiltChamber);
            }
            case SCORE_CHAMBER: {
//                outtakeSlides.setTargetPosition(OuttakeSlideScoreRung);
                outtakeTilt.setPosition(TiltChamber);
            }
            case BASKET: {
//                outtakeSlides.setTargetPosition(OuttakeSlideBasket);
                outtakeTilt.setPosition(TiltBasket);
            }
        }
    }

    public void testActuate(double position) {
        outtakeClaw.setPosition(position);
        outtakeTilt.setPosition(position);
    }
}
