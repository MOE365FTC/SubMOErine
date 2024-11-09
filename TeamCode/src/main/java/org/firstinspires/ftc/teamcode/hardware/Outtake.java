package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //DEVICES
    DcMotor outtakeSlides;
    Servo outtakeBucket;

    //PRESETS
    public static final double outtakeSlidesPower = 0.7, outtakeBucketPower = 0.7; //NEEDS TESTING
    public static final double outtakeTilt = 0.7, transferTilt = 0.2; //NEEDS TESTING
    public static final int outtakeSlidesBase = 0, outtakeSlidesTransfer = 200, outtakeSlidesExtend = 1000; //NEEDS TESTING

    public static int outtakeSlidesTarget = outtakeSlidesBase;
    public static final int outtakeSlidesTargetOffset = 25;

    //USAGE
    Gamepad gamepad1, gamepad2;
    Telemetry telemetry;
    public Outtake (HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.gamepad2 = gamepad2;

        outtakeBucket = hardwareMap.get(Servo.class, "tilt");
        outtakeSlides = hardwareMap.get(DcMotorEx.class, "intakeSlides");

        outtakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void actuate() {
        //swap bucket preset
        if (gamepad2.left_bumper) swapBucketPreset();

        //outtake slides preset
        if(gamepad2.dpad_up) outtakeSlidesTarget = outtakeSlidesExtend;
        else if(gamepad2.dpad_right) outtakeSlidesTarget = outtakeSlidesTransfer;
        else if(gamepad2.dpad_down) outtakeSlidesTarget = outtakeSlidesBase;


        //outtake manual control
        if(gamepad2.left_stick_y > 0.3) outtakeSlidesTarget += outtakeSlidesTargetOffset;
        else if(gamepad2.left_stick_y < -0.3) outtakeSlidesTarget -= outtakeSlidesTargetOffset;

        extendOuttakeSlides(outtakeSlidesTarget);
    }

    public void extendOuttakeSlides(int targetPos){
        outtakeSlides.setTargetPosition(targetPos);
        outtakeSlides.setPower(outtakeSlidesPower);
    }
    public void swapBucketPreset(){
        if(outtakeBucket.getPosition()== transferTilt) outtakeBucket.setPosition(outtakeTilt);
        else if(outtakeBucket.getPosition()== outtakeTilt) outtakeBucket.setPosition(transferTilt);
        else telemetry.addData("Outtake bucket rot pos(not tranfer or outtake preset):", outtakeBucket.getPosition()); // shouldn't happen
    }
}
