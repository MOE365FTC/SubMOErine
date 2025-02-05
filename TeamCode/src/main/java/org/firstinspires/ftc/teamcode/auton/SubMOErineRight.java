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
public class SubMOErineRight extends LinearOpMode {
    MOEBot robot;

    public static int firstXStrafe = 24, firstYStrafe = -48;
    public static int endSplineX = 50, endSplineY = -12;
    public static int pushSample1X = 50, pushSample1Y = -60;
    public static int strafeSecondSampleX = 45, strafeSecondSampleY = -55;
    public static int endSpline2X = 61, endSpline2Y = -12;
    public static int pushSample2x = 61, pushSample2y = -60;
    public static int pickUpSpecimenX = 44, pickUpSpecimenY = -55;
    public static int pickUpSpecimenX2 = 44, pickUpSpecimenY2 = -60;
    public static int placeSpecimenX1 = 1, placeSpecimenY1 = -29;
    public static int placeSpecimenX2 = 2, placeSpecimenY2 = -29;
    public static int parkX = 56, parkY = -60;
    public static Vector2d placeFirstSpecimen = new Vector2d(0, -26);

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MOEBot(hardwareMap, gamepad1, gamepad2, telemetry, true);
        robot.outtake.autonInit();
        robot.intake.actuate();

        Pose2d startPos = new Pose2d(12, -60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);
        TrajectoryActionBuilder rightTrajectory = drive.actionBuilder(startPos)
                // Place specimens
                .strafeTo(placeFirstSpecimen) // go to first specimen position
                .afterTime(0,robot.outtake.autonScoreChamber()) //raise outtake arm to score sample
                .afterTime(0.75,robot.outtake.autonWall()) //return outtake arm for wall specimen pickup after scoring specimen
/*
                // Go to first sample
                .strafeTo(new Vector2d(firstXStrafe, firstYStrafe))
                .splineToConstantHeading(new Vector2d(endSplineX, endSplineY), 0)

                // Push the sample
                .strafeTo(new Vector2d(pushSample1X, pushSample1Y))

                // Go to second sample
                .strafeTo(new Vector2d(strafeSecondSampleX, strafeSecondSampleY))
                .splineToConstantHeading(new Vector2d(endSpline2X, endSpline2Y), 0)

                // Push the sample
                .strafeTo(new Vector2d(pushSample2x, pushSample2y))

                // Pick up specimen
                .strafeTo(new Vector2d(pickUpSpecimenX, pickUpSpecimenY))
                .strafeTo(new Vector2d(pickUpSpecimenX2, pickUpSpecimenY2))
                .afterTime(0,robot.outtake.autonChamber())

                // Place specimen
                .strafeTo(new Vector2d(placeSpecimenX1, placeSpecimenY1))
                .afterTime(0,robot.outtake.autonScoreChamber()) //raise outtake arm to score sample
                .afterTime(0.75,robot.outtake.autonWall()) //return outtake arm for wall specimen pickup after scoring specimen

                // Pick up specimen
                .strafeTo(new Vector2d(pickUpSpecimenX, pickUpSpecimenY))
                .strafeTo(new Vector2d(pickUpSpecimenX2, pickUpSpecimenY2))
                .afterTime(0,robot.outtake.autonChamber())

                // Place specimen
                .strafeTo(new Vector2d(placeSpecimenX2, placeSpecimenY2))
                .afterTime(0,robot.outtake.autonScoreChamber()) //raise outtake arm to score sample
                .afterTime(0.75,robot.outtake.autonWall()) //return outtake arm for wall specimen pickup after scoring specimen
*/
                // Park
                .strafeTo(new Vector2d(parkX, parkY));

        waitForStart();
        Action rightAction = rightTrajectory.build();
        Actions.runBlocking(
                new SequentialAction(
                        rightAction
                )
        );
    }
}
