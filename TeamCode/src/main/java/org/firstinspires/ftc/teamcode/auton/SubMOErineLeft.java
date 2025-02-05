package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MOEBot;

@Config
@Autonomous
public class SubMOErineLeft extends LinearOpMode {
    MOEBot robot;

    public static Vector2d scorePosition = new Vector2d(-52, -52);
    public static float alignX = -50, alignY = -50;
    public static float scoreX = -62, scoreY = -58;

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
                .strafeToLinearHeading(new Vector2d(alignX, alignY), Math.toRadians(45))
                .afterTime(0,robot.outtake.autonBasket()) //raise outtake arm to score sample
                .waitSeconds(2)
                .strafeTo(new Vector2d(scoreX,scoreY))
                .afterTime(0, robot.outtake.openClaw())
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(alignX, alignY))
                .afterTime(0,robot.outtake.autonWall()) //return outtake arm for wall specimen pickup after scoring specimen
                // Park
                .waitSeconds(2)
                .strafeTo(new Vector2d(scoreX, scoreY));


        waitForStart();
        Action leftAction = leftTrajectory.build();
        Actions.runBlocking(
                new SequentialAction(
                        leftAction
                )
        );
    }
}