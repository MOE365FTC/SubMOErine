package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    //DEVICES
    Servo elbow, wrist;
    Servo linkage;
    Servo intakeClaw;

    public enum ArmPositions {
        BASE,
        SAMPLE,
        TRANSFER
    }

    //PRESETS
    // TODO MAKE THIS STATIC ASAP AND CHANGE VALUES!!
    // FIXME EVERYTHING IS -1 [DO NOT RUN]
    public final double PitchBase = 0.02, PitchSample = 0.97, PitchTransfer = 0.02;
    public final double LinkageOut = 0.11, LinkageIn = 0.95;
    public final double WristAxial = 0.81, WristTransverse = 0.49;
    public final double ClawOpen = 0.3, ClawClose = 0.05;

    public ArmPositions curArmPosition = ArmPositions.BASE;

    public boolean isClawOpen = true;
    public boolean g1RightBumperPressed = false;
    public int fineControlSteps = 1;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;

    public Intake(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.gamepad1 = gamepad1;

        elbow = hardwareMap.get(Servo.class, "IntakeElbow");
        wrist = hardwareMap.get(Servo.class, "IntakeWrist");
        linkage = hardwareMap.get(Servo.class, "IntakeLinkage");
        intakeClaw = hardwareMap.get(Servo.class, "IntakeClaw");
    }


    public void actuate() {
        if(this.gamepad1.dpad_down) curArmPosition = ArmPositions.BASE;
        if(this.gamepad1.dpad_up){
            curArmPosition = ArmPositions.SAMPLE;
            isClawOpen = true;
        }
        if(this.gamepad1.dpad_left) curArmPosition = ArmPositions.TRANSFER;

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
                elbow.setPosition(PitchBase);
                wrist.setPosition(WristAxial);
                break;
            }

            case SAMPLE: {
                linkage.setPosition(LinkageOut);
                elbow.setPosition(PitchSample);
                break;
            }

            case TRANSFER: {
                linkage.setPosition(LinkageIn);
                elbow.setPosition(PitchTransfer);
                wrist.setPosition(WristAxial);
                break;
            }
        }
    }

    public void testActuate(double position) {
        elbow.setPosition(position);
        wrist.setPosition(position);
        linkage.setPosition(position);
        intakeClaw.setPosition(position);
    }

    public void autonActuate(ArmPositions armPositions) {
        switch (armPositions) {
            case BASE: {
                linkage.setPosition(LinkageIn);
                elbow.setPosition(PitchBase);
                wrist.setPosition(WristAxial);
                break;
            }

            case SAMPLE: {
                linkage.setPosition(LinkageOut);
                elbow.setPosition(PitchSample);
                break;
            }

            case TRANSFER: {
                linkage.setPosition(LinkageIn);
                elbow.setPosition(PitchTransfer);
                wrist.setPosition(WristAxial);
                break;
            }
        }
    }

    public Action autonBase() {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                autonActuate(ArmPositions.BASE);
                intakeClaw.setPosition(ClawClose);
                return false;
            }
        };
    }

    public Action autonSample() {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                autonActuate(ArmPositions.SAMPLE);
                intakeClaw.setPosition(ClawClose);
                return false;
            }
        };
    }

}