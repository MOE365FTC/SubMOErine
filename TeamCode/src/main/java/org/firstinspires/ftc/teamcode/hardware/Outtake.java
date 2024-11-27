package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //DEVICES
    Servo outtakeClaw;
    DcMotor outtakeSlides;
    
    public enum OuttakeSlidePositions {
        BASE,
        RUNG,
        SCORE_RUNG,
        BASKET
    }


    public OuttakeSlidePositions curOuttakePos = OuttakeSlidePositions.BASE;
    public boolean isClawOpen = true;
    // PRESETS
    // TODO MAKE THIS STATIC ASAP AND CHANGE VALUES!!
    // FIXME EVERYTHING IS -1 [DO NOT RUN]
    public final int OuttakeSlideBase = -1, OuttakeSlideRung = -1, OuttakeSlideScoreRung = -1, OuttakeSlideBasket = -1;
    public final double ClawOpen = -1.0, ClawClose = -1.0;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Outtake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.gamepad2 = gamepad2;

        outtakeSlides = hardwareMap.get(DcMotor.class, "outtakeSlides");
        outtakeClaw = hardwareMap.get(Servo.class, "claw");

        outtakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void actuate() {
        if(isClawOpen)  outtakeClaw.setPosition(ClawOpen);
        else            outtakeClaw.setPosition(ClawClose);

        if(this.gamepad2.dpad_down)     curOuttakePos = OuttakeSlidePositions.BASE;
        if(this.gamepad2.dpad_up)       curOuttakePos = OuttakeSlidePositions.RUNG;
        if(this.gamepad2.dpad_right)    curOuttakePos = OuttakeSlidePositions.SCORE_RUNG;

        if(this.gamepad2.right_bumper)  isClawOpen = !isClawOpen;

        switch (curOuttakePos) {
            case BASE:
                outtakeSlides.setTargetPosition(OuttakeSlideBase);

            case RUNG:
                outtakeSlides.setTargetPosition(OuttakeSlideRung);

            case SCORE_RUNG: {
                // FIXME UP FOR INTERPRETATION
                outtakeSlides.setTargetPosition(OuttakeSlideScoreRung);
                isClawOpen = true;
            }

            case BASKET:
                outtakeSlides.setTargetPosition(OuttakeSlideBasket);
        }
    }

    public void testActuate(double position) {
        //outtake claw
        outtakeClaw.setPosition(position);
    }
}
