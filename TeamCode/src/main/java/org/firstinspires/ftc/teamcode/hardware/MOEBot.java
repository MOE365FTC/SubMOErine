package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;


public class MOEBot {
    //MECHANISMS
    public Chassis chassis;
    public Intake intake;
    public Outtake outtake;

    public MOEBot(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {

        chassis = new Chassis(hardwareMap, gamepad1);
        intake = new Intake(hardwareMap, gamepad1, gamepad2, telemetry);
        outtake = new Outtake(hardwareMap, gamepad1, gamepad2, telemetry);
    }

}
