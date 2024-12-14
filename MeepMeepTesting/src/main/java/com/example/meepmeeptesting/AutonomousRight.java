package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class AutonomousRight {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                // Place specimen
                .strafeTo(new Vector2d(0, -34))
                .waitSeconds(0.75)

                // Go to first sample
                .strafeTo(new Vector2d(24, -55))
                .splineToConstantHeading(new Vector2d(48, -12), 0)

                // Push the sample
                .strafeTo(new Vector2d(48, -60))

                // Go to second sample
                .strafeTo(new Vector2d(42, -55))
                .splineToConstantHeading(new Vector2d(56, -12), 0)

                // Push the sample
                .strafeTo(new Vector2d(56, -60))

                // Pick up specimen
                .strafeTo(new Vector2d(44, -55))
                .strafeTo(new Vector2d(44, -60))
                .waitSeconds(0.5)

                // Place specimen
                .strafeTo(new Vector2d(1, -34))
                .waitSeconds(0.75)

                // Pick up specimen
                .strafeTo(new Vector2d(44, -55))
                .strafeTo(new Vector2d(44, -60))
                .waitSeconds(0.5)

                // Place specimen
                .strafeTo(new Vector2d(2, -34))
                .waitSeconds(0.75)

                // Park
                .strafeTo(new Vector2d(56, -60))
                .build());

        // Background Code
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}