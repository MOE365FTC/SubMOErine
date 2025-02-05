package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "test")
public class ServoTester extends OpMode {
    String[] servos = {
            "IntakeElbow", "IntakeWrist", "IntakeLinkage",
            "IntakeClaw", "HopRight", "HopLeft", "SHOULDERS",
            "OuttakeClaw", "OuttakeWrist"
    };


    public static double position = 0.0;
    public boolean dpad_up_pressed = false, dpad_down_pressed = false;

    int indx = 0;

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up && !dpad_up_pressed){
            indx += 1;
            dpad_up_pressed = true;
        } else if(!gamepad1.dpad_up){
            dpad_up_pressed = false;
        }
        if(gamepad1.dpad_down && !dpad_down_pressed){
            indx -= 1;
            dpad_down_pressed = true;
        } else if(!gamepad1.dpad_down){
            dpad_down_pressed = false;
        }
        if (indx < 0)
            indx = servos.length - 1;

        indx = indx % servos.length;
        telemetry.addData("Servo Name", servos[indx]);
        if(gamepad1.a) {
            String servoString = servos[indx];
            if (servoString.equalsIgnoreCase("SHOULDERS")){
                hardwareMap.get(Servo.class, "ShoulderRight").setPosition(position);
                hardwareMap.get(Servo.class, "ShoulderLeft").setPosition(1-position);
            } else{
                hardwareMap.get(Servo.class, servoString).setPosition(position);
            }
        }
        telemetry.update();
    }
}
