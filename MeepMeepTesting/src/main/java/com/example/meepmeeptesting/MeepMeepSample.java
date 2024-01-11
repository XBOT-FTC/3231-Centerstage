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
                        drive.trajectorySequenceBuilder(new Pose2d(13, -63, Math.toRadians(90)))
                                .forward(0.0001)
                                .addDisplacementMarker(() -> {
                                })
                                .splineTo(new Vector2d(13, -34), Math.toRadians(90))
                                .waitSeconds(1)
                                .splineTo(new Vector2d(2, -34), Math.toRadians(180))
                                .waitSeconds(0.2)
                                .addDisplacementMarker(() -> {
                                })
                                .addDisplacementMarker(() -> {
                                })
                                .waitSeconds(2) //dropping purple pixel
                                .back(10)
                                .splineTo(new Vector2d(13, -63), Math.toRadians(-90))
                                .splineToLinearHeading(new Pose2d(48, -29, Math.toRadians(180)), Math.toRadians(0))
                                //placing
                                .waitSeconds(0.5)
                                .addDisplacementMarker(() -> {
                                })
                                .waitSeconds(2)
                                //parking
                                .strafeLeft(30)
                                .waitSeconds(.4)
                                .back(9)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}