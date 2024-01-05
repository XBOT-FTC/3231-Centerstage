package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;
import android.webkit.WebStorage;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


@Autonomous(name="BlueCloseToBackdrop", group = "LinearOpMode")
public class BlueBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
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
        drive.setPoseEstimate(new Pose2d(13,60,Math.toRadians(270)));

        // vision code for if left
        // 0 degrees = facing backdrop
        // 90 degrees turn left
        // give the trajectory sequence so u can run it when the if statements are amde ex. myTrajectory = andafnwn -> drive.followTrajectorySequence(andafnwn);
        //left trajectory
        if(selection == ThreeRectangleProcessor.Selected.LEFT) {
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, 60, Math.toRadians(270)))
                    .splineTo(new Vector2d(22, 31), Math.toRadians(270))
                    .waitSeconds(0.4)
                    //        turn on intaker
                    .back(30)
                    .waitSeconds(.03)
                    .splineToSplineHeading(new Pose2d(51, 42, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    //servo
                    .forward(7)
                    .waitSeconds(0.4)
                    .splineTo(new Vector2d(53, 57), Math.toRadians(180))
                    .build();
        }else if(selection == ThreeRectangleProcessor.Selected.MIDDLE) {
            //middle trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, 60, Math.toRadians(270)))
                    .forward(35)
                    .waitSeconds(0.4)
                    // turn on intaker
                    .back(30)
                    .waitSeconds(.03)
                    .splineToSplineHeading(new Pose2d(51, 36, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    //servo
                    .forward(7)
                    .waitSeconds(0.4)
                    .splineTo(new Vector2d(53, 57), Math.toRadians(180))
                    .build();
        }else if (selection == ThreeRectangleProcessor.Selected.RIGHT || selection == ThreeRectangleProcessor.Selected.NONE) {
            //right trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, 60, Math.toRadians(270)))
                    .splineTo(new Vector2d(8, 34), Math.toRadians(180))
                    .waitSeconds(0.4)
                    // turn on intaker
                    .back(30)
                    .waitSeconds(.03)
                    .splineToSplineHeading(new Pose2d(51, 36, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    //servo
                    .forward(7)
                    .waitSeconds(0.4)
                    .splineTo(new Vector2d(53, 57), Math.toRadians(180))
                    .build();
        }
        waitForStart();
        if(isStopRequested()) return;
        drive.followTrajectorySequence(myTrajectory);
    }
}
