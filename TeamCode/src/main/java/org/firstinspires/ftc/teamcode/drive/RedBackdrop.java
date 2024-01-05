package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name="RedCloseToBackdrop", group = "LinearOpMode")
public class RedBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        VisionPortal myVisionPortal;
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
        ThreeRectangleProcessor.Selected selection = ThreeRectangleProcessor.Selected.NONE;


        drive.setPoseEstimate(new Pose2d(13, -63, Math.toRadians(270)));
        if (selection == ThreeRectangleProcessor.Selected.RIGHT) {
            //if right vision code
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, -63, Math.toRadians(90)))
                    //changes based off of the the team prop position
                    // x cord = 48-50? for backdrop location test in person
                    .splineTo(new Vector2d(23, -34), Math.toRadians(90))
                    .waitSeconds(2) //dropping purple pixel
                    .back(20)
                    .waitSeconds(1)
                    .splineToLinearHeading(new Pose2d(48, -42, Math.toRadians(180)), Math.toRadians(0))
                    //servo drops yellow pixel
                    .waitSeconds(1)
                    .forward(4)
                    .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                    .splineTo(new Vector2d(60, -60), Math.toRadians(0))
                    .build();
        } else if (selection == ThreeRectangleProcessor.Selected.LEFT) {
            //left trajectory

            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, -63, Math.toRadians(90)))

                    .splineTo(new Vector2d(13, -34), Math.toRadians(90))
                    .waitSeconds(1)
                    .splineTo(new Vector2d(2, -34), Math.toRadians(180))

                    .waitSeconds(2) //dropping purple pixel
                    .back(10)
                    .splineTo(new Vector2d(13, -63), Math.toRadians(-90))
                    .splineToLinearHeading(new Pose2d(48, -29, Math.toRadians(180)), Math.toRadians(0))
                    //placing
                    .waitSeconds(2)
                    //parking
                    .strafeLeft(30)
                    .waitSeconds(.4)
                    .back(9)
                    .build();
        } else if (selection == ThreeRectangleProcessor.Selected.MIDDLE || selection == ThreeRectangleProcessor.Selected.NONE) {
            //middle trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13,-63, Math.toRadians(90)))
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
                    .build();

        }
        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(myTrajectory);

    }

}
