package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Intaker;
import org.firstinspires.ftc.teamcode.Snapper;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="RedCloseToBackdrop", group = "LinearOpMode")
public class RedBackdropNew extends LinearOpMode {
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
        Intaker intaker = new Intaker(hardwareMap);

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


        drive.setPoseEstimate(new Pose2d(13, -63, Math.toRadians(90)));
        if (selection == ThreeRectangleProcessor.Selected.RIGHT) {
            //if right vision code
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, -63, Math.toRadians(90)))
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
                    .splineTo(new Vector2d(24, -34), Math.toRadians(90))
                    .waitSeconds(2) //dropping purple pixel
                    .back(19)
                    .waitSeconds(1)
                    .splineToLinearHeading(new Pose2d(56, -39, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    .forward(0.01)
                    .waitSeconds(1)
                    //servo drops yellow pixel
                    .addTemporalMarker(10.5, () -> {
                        grabber.openServo(0.56);
                    })
                    .waitSeconds(1.5)
                    .forward(1.4)
                    .waitSeconds(1)
                    .forward(4)
                    .splineTo(new Vector2d(40, -59), Math.toRadians(0))
                    .splineTo(new Vector2d(59, -59), Math.toRadians(0))
                    .addDisplacementMarker( () -> {
                        grabber.openServo(0.683);
                    })
                    .build();
        } else if (selection == ThreeRectangleProcessor.Selected.LEFT) {
            //left trajectory

            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13, -63, Math.toRadians(90)))
                    .forward(0.0001)
                    .addDisplacementMarker(() -> {
                        snapper.setPowerSnapper(-1);
                    })
                    .waitSeconds(0.5)
                    .addTemporalMarker(2,() -> {
                        snapper.setPowerSnapper(0);
                    })
                    .splineTo(new Vector2d(13, -37), Math.toRadians(90))
                    .waitSeconds(1)
                    .splineTo(new Vector2d(5, -35), Math.toRadians(180))
                    .waitSeconds(0.2)
                    .waitSeconds(2) //dropping purple pixel
                    .back(10)
                    .splineTo(new Vector2d(13, -60), Math.toRadians(-90))
                    .splineToLinearHeading(new Pose2d(54.25, -24.75, Math.toRadians(180)), Math.toRadians(0))
                    //placing
                    .waitSeconds(0.5)
                    .addTemporalMarker(13.5,() -> {
                        grabber.openServo(0.56);
                    })
                    .waitSeconds(3.5)
                    .forward(3)
                    .waitSeconds(.5)
                    //parking
                    .strafeLeft(33.25)
                    .waitSeconds(.4)
                    .back(11.5)
                    .addDisplacementMarker(() -> {
                        grabber.openServo(0.683);
                    })
                    .build();
        } else if (selection == ThreeRectangleProcessor.Selected.MIDDLE || selection == ThreeRectangleProcessor.Selected.NONE) {
            //middle trajectory
            myTrajectory = drive.trajectorySequenceBuilder(new Pose2d(13,-63, Math.toRadians(90)))
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
                    .addDisplacementMarker(() -> {
                        intaker.setIntakePower(0);
                    })
                    //servo
                    .splineToLinearHeading(new Pose2d(54, -31, Math.toRadians(180)), Math.toRadians(0))
                    .waitSeconds(0.5)
                    .forward(0.01)
                    .waitSeconds(1.5)
                    //servo drops yellow pixel
                    .addTemporalMarker(10, () -> {
                        grabber.openServo(0.54);
                    })
                    .waitSeconds(1.75)
                    .forward(1.4)
                    .forward(0.01)
                    .splineTo(new Vector2d(40, -60), Math.toRadians(0))
                    .splineTo(new Vector2d(59, -60), Math.toRadians(0))
                    .addDisplacementMarker( () -> {
                        grabber.openServo(0.683);
                    })
                    .build();

        }
        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(myTrajectory);

    }

}
