package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    //DEVICES
    Servo shoulder, elbow, wrist;
    Servo linkage;
    Servo intakeClaw;

    public enum ArmPositions {
        BASE,
        SAMPLE,
        HUMAN_PLAYER
    }

    //PRESETS
    // TODO MAKE THIS STATIC ASAP AND CHANGE VALUES!!
    // FIXME EVERYTHING IS -1 [DO NOT RUN]

    public final double YawBase = 0.45, YawSample = 1.0, YawHumanPlayer = 0.25, YawTransfer = 0.0;
    public final double PitchBase = 0.05, PitchSample = 1, PitchHumanPlayer = 0.8;
    public final double LinkageOut = 0.18, LinkageIn = 0.73, LinkageTransfer = 0.5;
    public final double WristAxial = 0.0, WristTransverse = 0.3;
    public final double ClawOpen = 0.3, ClawClose = 0.0;

    public ArmPositions curArmPosition = ArmPositions.BASE;

    public boolean isClawOpen = true;
    public boolean g1RightBumperPressed = false;
    public int fineControlSteps = 1;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;

    public Intake(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.gamepad1 = gamepad1;

        shoulder = hardwareMap.get(Servo.class, "shoulderServo");
        elbow = hardwareMap.get(Servo.class, "elbowServo");
        wrist = hardwareMap.get(Servo.class, "wristServo");
        linkage = hardwareMap.get(Servo.class, "linkageServo");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClawServo");
    }

    public void actuate() {
        if(this.gamepad1.dpad_down) curArmPosition = ArmPositions.BASE;
        if(this.gamepad1.dpad_up){
            curArmPosition = ArmPositions.SAMPLE;
            isClawOpen = true;
        }
        if(this.gamepad1.dpad_left) curArmPosition = ArmPositions.HUMAN_PLAYER;

        if(this.gamepad1.b && curArmPosition == ArmPositions.SAMPLE) wrist.setPosition(WristAxial);
        if(this.gamepad1.x && curArmPosition == ArmPositions.SAMPLE) wrist.setPosition(WristTransverse);

        if(this.gamepad1.y && curArmPosition == ArmPositions.SAMPLE) linkage.setPosition(linkage.getPosition() + fineControlSteps);
        if(this.gamepad1.a && curArmPosition == ArmPositions.SAMPLE) linkage.setPosition(linkage.getPosition() - fineControlSteps);

        if(this.gamepad1.right_bumper && !g1RightBumperPressed) {
            g1RightBumperPressed = true;
            isClawOpen = !isClawOpen;
        }
        else if(!this.gamepad1.right_bumper) {
            g1RightBumperPressed = false;
        }
        if(isClawOpen)  intakeClaw.setPosition(ClawOpen);
        else            intakeClaw.setPosition(ClawClose);

        switch (curArmPosition) {
            case BASE: {
                linkage.setPosition(LinkageIn);
                shoulder.setPosition(YawBase);
                elbow.setPosition(PitchBase);
                wrist.setPosition(WristAxial);
                break;
            }

            case SAMPLE: {
                linkage.setPosition(LinkageOut);
                shoulder.setPosition(YawSample);
                elbow.setPosition(PitchSample);
                break;
            }

            case HUMAN_PLAYER: {
                linkage.setPosition(LinkageIn);
                shoulder.setPosition(YawHumanPlayer);
                elbow.setPosition(PitchHumanPlayer);
                break;
            }
        }
    }

    public void testActuate(double position) {
        shoulder.setPosition(position);
        elbow.setPosition(position);
        wrist.setPosition(position);
        linkage.setPosition(position);
        intakeClaw.setPosition(position);
    }

}