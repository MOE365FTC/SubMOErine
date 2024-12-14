package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MOEBot;

@Autonomous
public class SubMOErineLeft extends LinearOpMode {
    MOEBot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MOEBot(hardwareMap, gamepad1, gamepad2, telemetry, true);
        robot.outtake.autonInit();
        robot.intake.actuate();

        Pose2d startPos = new Pose2d(-36, -60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);
        TrajectoryActionBuilder leftTrajectory = drive.actionBuilder(startPos)
                /*
                // Go to first sample
                .strafeTo(new Vector2d(-48, -37))
                .waitSeconds(0.5)

                // Go to basket
                .strafeToLinearHeading(new Vector2d(-48, -39), Math.toRadians(-135))
                .afterTime(0,robot.outtake.autonBasket()) //raise outtake arm to score sample
                .waitSeconds(0.75)
                .strafeTo(new Vector2d(-46,-37))
                .afterTime(0.75,robot.outtake.autonWall()); //return outtake arm for wall specimen pickup after scoring specimen



                // Go to second sample
                .strafeTo(new Vector2d(-52, -49))
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-58, -37))
*/
                // Go to basket
                .strafeToLinearHeading(new Vector2d(-52, -52), Math.toRadians(45))
                .afterTime(0,robot.outtake.autonBasket()) //raise outtake arm to score sample
                .waitSeconds(0.75)
                .strafeTo(new Vector2d(-50,-50))
                .afterTime(0.75,robot.outtake.autonWall()) //return outtake arm for wall specimen pickup after scoring specimen
                .strafeTo(new Vector2d(-52,-52));
/*
                // Park
                .turn(Math.toRadians(-135));

 */

        waitForStart();
        Action leftAction = leftTrajectory.build();
        Actions.runBlocking(
                new SequentialAction(
                        leftAction
                )
        );
    }
}