package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepSample {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //blue backdrop left
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(13,-63, Math.toRadians(90)))
                                .forward(37)
                                .waitSeconds(0.3)
                                .back(6)
                                .waitSeconds(0.3)
                                .splineTo(new Vector2d(13, -63), Math.toRadians(-90))
                                .waitSeconds(0.3)
                                //servo
                                .splineToLinearHeading(new Pose2d(48, -32, Math.toRadians(180)), Math.toRadians(0))
                                .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                                .splineTo(new Vector2d(60, -60), Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}