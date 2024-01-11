package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Intaker;
import org.firstinspires.ftc.teamcode.Snapper;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name="RedAwayFromBackdrop", group = "LinearOpMode")
public class RedAwayBackdrop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        VisionPortal myVisionPortal;
        Grabber grabber = new Grabber(hardwareMap);
        Intaker intaker = new Intaker(hardwareMap);
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
        ThreeRectangleProcessor.Selected selection = ThreeRectangleProcessor.Selected.NONE;

        while (!opModeIsActive()) {
            selection = threeRectangleProcessor.getSelection();
            telemetry.addLine("NOT started yet.");
            telemetry.addLine(selection.name());
            telemetry.addData("Camera state", myVisionPortal.getCameraState());
            telemetry.update();
        }

        drive.setPoseEstimate(new Pose2d(-36,-60, Math.toRadians(90)));
        if(selection == ThreeRectangleProcessor.Selected.RIGHT) {
            //right trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(-36, -60, Math.toRadians(90)))
                    .forward(0.01)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(-1);
                    })
                    .forward(0.01)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(0);
                    })
                    .splineTo(new Vector2d(-28, -34), Math.toRadians(0))
                    .waitSeconds(10)
                    //intaker

                    .addDisplacementMarker(() -> {
                 //   intaker.setPower(spinPower);
                    })
                    .back(20)
                    .waitSeconds(0.5)
                    .strafeRight(24)
                    .waitSeconds(0.5)
                    .forward(40)
                    .splineToSplineHeading(new Pose2d(51, -42, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    //servo
                    .addDisplacementMarker(() -> {
                        grabber.openServo(1);
                    })
                    .waitSeconds(0.5)
                    .forward(20)
                    .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                    .splineTo(new Vector2d(60, -60), Math.toRadians(0))
                    .build();
        }else if (selection == ThreeRectangleProcessor.Selected.LEFT) {
            //left trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(-36, -60, Math.toRadians(90)))
                    .forward(30)
                    .waitSeconds(0.3)
                    .splineTo(new Vector2d(-47, -32), Math.toRadians(180))
                    //intaker
                    .waitSeconds(0.3)
                    .back(13)
                    .waitSeconds(0.3)
                    .strafeLeft(4)
                    .waitSeconds(0.3)
                    .turn(Math.toRadians(180))
                    .waitSeconds(0.3)
                    .strafeRight(24)
                    .waitSeconds(10)
                    .forward(37)
                    .waitSeconds(0.5)
                    .splineToSplineHeading(new Pose2d(51, -42, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    .addDisplacementMarker(() -> {
                        grabber.openServo(1);
                    })
                    .waitSeconds(0.5)
                    .forward(20)
                    .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                    .splineTo(new Vector2d(60, -60), Math.toRadians(0))
                    .build();
        }else if (selection == ThreeRectangleProcessor.Selected.MIDDLE || selection == ThreeRectangleProcessor.Selected.NONE) {
            //middle trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(-36, -60, Math.toRadians(90)))
                    .forward(30)
                    //intaker
                    .waitSeconds(0.3)
                    .back(27)
                    .waitSeconds(0.3)
                    .turn(Math.toRadians(-90))
                    .waitSeconds(0.3)
                    .waitSeconds(10)
                    .forward(36)
                    .waitSeconds(0.5)
                    .splineToSplineHeading(new Pose2d(51, -42, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    //servo
                    .addDisplacementMarker(() -> {
                        grabber.openServo(1);
                    })
                    .waitSeconds(0.5)
                    .forward(20)
                    .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                    .splineTo(new Vector2d(60, -60), Math.toRadians(0))
                    .build();
        }
        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(myTrajectory);

    }
}