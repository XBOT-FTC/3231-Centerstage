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
                        drive.trajectorySequenceBuilder(new Pose2d(13, 60, Math.toRadians(270)))
                                //changes based off of the the team prop position
                                // x cord = 48-50? for backdrop location test in person
                                .forward(0.0001)
                                .addDisplacementMarker(() -> {
                                })
                                .waitSeconds(0.5)
                                .addTemporalMarker(2,() -> {
                                })
                                .splineTo(new Vector2d(24, 34), Math.toRadians(270))
                                .waitSeconds(2) //dropping purple pixel
                                .back(19)
                                .waitSeconds(1)
                                .splineToLinearHeading(new Pose2d(56, 39, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .forward(0.01)
                                .waitSeconds(1)
                                //servo drops yellow pixel
                                .addTemporalMarker(10.5, () -> {
                                })
                                .waitSeconds(1.5)
                                .forward(1.4)
                                .waitSeconds(1)
                                .forward(4)
                                .splineTo(new Vector2d(40, 59), Math.toRadians(0))
                                .splineTo(new Vector2d(59, 59), Math.toRadians(0))
                                .addDisplacementMarker( () -> {
                                })
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}