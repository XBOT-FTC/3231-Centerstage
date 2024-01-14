package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Snapper;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name="BlueAwayBackdrop.java", group = "LinearOpMode")
public class RedAwayBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        VisionPortal myVisionPortal;
        Grabber grabber = new Grabber(hardwareMap);
        Snapper snapper = new Snapper(hardwareMap);
        ThreeRectangleProcessor threeRectangleProcessor;
        threeRectangleProcessor = new ThreeRectangleProcessor();
        VisionPortal.Builder myVisionPortalBuilder;
        TrajectorySequence myTrajectory = null;

        // Create a new VisionPortal Builder object.
        myVisionPortalBuilder = new VisionPortal.Builder();

        // Specify the camera to be used for this VisionPortal.
        WebcamName webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
        myVisionPortalBuilder.setCamera(webcam);      // Other choices are: RC phone camera and "switchable camera name".

        // Add the AprilTag Processor to the VisionPortal Builder.
        myVisionPortalBuilder.addProcessor(threeRectangleProcessor);       // An added Processor is enabled by default.

        // Optional: set other custom features of the VisionPortal (4 are shown here).
        myVisionPortalBuilder.setCameraResolution(new Size(1280, 720));  // Each resolution, for each camera model, needs calibration values for good pose estimation.
        myVisionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2);  // MJPEG format uses less bandwidth than the default YUY2.
        myVisionPortalBuilder.enableLiveView(true);      // Enable LiveView (RC preview).
        myVisionPortalBuilder.setAutoStopLiveView(true);     // Automatically stop LiveView (RC preview) when all vision processors are disabled.

        // Create a VisionPortal by calling build()
        myVisionPortal = myVisionPortalBuilder.build();
     //   drive.setPoseEstimate(new Pose2d(-36, 60, Math.toRadians(270)));
        drive.setPoseEstimate(new Pose2d(13,63,Math.toRadians(270)));

        ThreeRectangleProcessor.Selected selection = ThreeRectangleProcessor.Selected.NONE;
        //right trajectory
        if (selection == ThreeRectangleProcessor.Selected.RIGHT) {
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, 63, Math.toRadians(270)))
                    .forward(0.0001)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(-1);
                    })
                    .waitSeconds(0.5)
                    .addTemporalMarker(2,() -> {
                        snapper.setPowerSnapper(0);
                    })
                    .splineTo(new Vector2d(13, 37), Math.toRadians(270))
                    .waitSeconds(1)
                    .splineTo(new Vector2d(5, 35), Math.toRadians(180))
                    .waitSeconds(0.2)
                    .waitSeconds(2) //dropping purple pixel
                    .back(10)
//            drive.trajectorySequenceBuilder(new Pose2d(-36, 60, Math.toRadians(270)))
//
//                    .splineTo(new Vector2d(-28, 34), Math.toRadians(0))
//                    .waitSeconds(10)
                    //intaker
                    //     addDisplacementMarker(() -> {
                    //     intaker.setSpinPower(1);
                    //     })
//                    .back(20)
//                    .waitSeconds(0.5)
//                    .strafeLeft(24)
//                    .waitSeconds(0.5)
//                    .forward(45)
//                    .splineToSplineHeading(new Pose2d(51, 42, Math.toRadians(180)), Math.toRadians(0))
//                    .waitSeconds(0.5)
//                    //servo
//                    .addDisplacementMarker(() -> {
//                        grabber.openServo(1);
//                    })
//                    .waitSeconds(0.5)
//                    .forward(20)
//                    .splineTo(new Vector2d(40, 60), Math.toRadians(0))
//                    .splineTo(new Vector2d(60, 60), Math.toRadians(0))
                    .build();

        }else if (selection == ThreeRectangleProcessor.Selected.LEFT) {
            //left trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, 63, Math.toRadians(270)))
                    //changes based off of the the team prop position
                    // x cord = 48-50? for backdrop location test in person
                    .forward(0.0001)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(-1);
                    })
                    .waitSeconds(0.5)
                    .addTemporalMarker(2,() -> {
                        snapper.setPowerSnapper(0);
                    })
                    .splineTo(new Vector2d(24, 34), Math.toRadians(270))
                    .waitSeconds(2) //dropping purple pixel
                    .back(19)
                    .waitSeconds(1)
        //drive.trajectorySequenceBuilder(new Pose2d(-36, 60, Math.toRadians(270)))
//                    .forward(30)
//                    .waitSeconds(0.3)
//                    .splineTo(new Vector2d(-47, 32), Math.toRadians(180))
//                    //intaker
//                    .waitSeconds(0.3)
//                    .back(13)
//                    .waitSeconds(0.3)
//                    .strafeRight(4)
//                    .waitSeconds(0.3)
//                    .turn(Math.toRadians(180))
//                    .waitSeconds(0.3)
//                    .strafeLeft(24)
//                    .waitSeconds(10)
//                    .forward(37)
//                    .waitSeconds(0.5)
//                    .splineToSplineHeading(new Pose2d(51, 42, Math.toRadians(180)), Math.toRadians(0))
//                    .waitSeconds(0.5)
//                    .addDisplacementMarker(() -> {
//                        grabber.openServo(1);
//                    })
//                    //servo
//                    .waitSeconds(0.5)
//                    .forward(20)
//                    .splineTo(new Vector2d(40, 60), Math.toRadians(0))
//                    .splineTo(new Vector2d(60, 60), Math.toRadians(0))
                    .build();
        }
        else if (selection == ThreeRectangleProcessor.Selected.MIDDLE || selection == ThreeRectangleProcessor.Selected.NONE) {
            //middle trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13,63, Math.toRadians(270)))
                    .forward(0.0001)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(-1);
                    })
                    .waitSeconds(0.5)
                    .addTemporalMarker(2,() -> {
                        snapper.setPowerSnapper(0);
                    })
                    .waitSeconds(0.1)
                    .forward(35.4)
                    .waitSeconds(0.5)
                    .back(19)
                    .waitSeconds(2)
//            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(-36, 60, Math.toRadians(270)))
//                    .forward(30)
//                    //intaker
//                    .waitSeconds(0.3)
//                    .back(27)
//                    .waitSeconds(0.3)
//                    .turn(Math.toRadians(90))
//                    .waitSeconds(0.3)
//                    .waitSeconds(10)
//                    .forward(36)
//                    .waitSeconds(0.5)
//                    .splineToSplineHeading(new Pose2d(51, 42, Math.toRadians(180)), Math.toRadians(0))
//                    .waitSeconds(0.5)
//                    //servo
//                    .addDisplacementMarker(() -> {
//                        grabber.openServo(1);
//                    })
//                    .waitSeconds(0.5)
//                    .forward(20)
//                    .splineTo(new Vector2d(40, 60), Math.toRadians(0))
//                    .splineTo(new Vector2d(60, 60), Math.toRadians(0))
                    .build();
        }
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(myTrajectory);
    }
}
