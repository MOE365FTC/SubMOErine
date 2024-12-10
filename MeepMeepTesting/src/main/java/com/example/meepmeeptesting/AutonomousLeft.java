package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class AutonomousLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36, -60, Math.toRadians(90)))

                // Go to first sample
                .strafeTo(new Vector2d(-48, -37))
                .waitSeconds(0.5)

                // Go to basket
                .strafeTo(new Vector2d(-48, -39))
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-52, -52))
                .waitSeconds(0.75)

                // Go to second sample
                .strafeTo(new Vector2d(-52, -49))
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-58, -37))

                // Go to basket
                .strafeTo(new Vector2d(-56, -39))
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-52, -52))
                .waitSeconds(0.75)

                // Park
                .turn(Math.toRadians(-135))

                .build());

        // Background Code
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}