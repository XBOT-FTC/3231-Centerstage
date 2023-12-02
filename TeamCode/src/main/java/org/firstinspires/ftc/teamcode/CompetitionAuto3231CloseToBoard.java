package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "CompAutoFar", group="Test")
public class CompetitionAuto3231CloseToBoard extends LinearOpMode {

    VisionPortal myVisionPortal;
    ThreeRectangleProcessor threeRectangleProcessor;

    @Override
    public void runOpMode() throws InterruptedException {
        threeRectangleProcessor = new ThreeRectangleProcessor();

        VisionPortal.Builder myVisionPortalBuilder;

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
        while(!opModeIsActive()) {
            selection = threeRectangleProcessor.getSelection();
            telemetry.addLine("NOT started yet.");
            telemetry.addLine(selection.name());
            telemetry.addData("Camera state", myVisionPortal.getCameraState());
            telemetry.update();
        }
        //DRIVE SETUPS
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        drive.setSpeedChange(0.25);

        //GRABBER SETUPS
        Grabber grabber = new Grabber(hardwareMap);
        grabber.setPosition(0.0, 0.7);
        //Intaker setup
        Intaker intaker = new Intaker(hardwareMap);
        intaker.setSpinPower(1.0);
        intaker.setStopPower(0.0);
        waitForStart();
        //determineCommand(String command, double speed, int ticks, Telemetry telemetry)
        if(selection == ThreeRectangleProcessor.Selected.LEFT){
            // run AUTO program LEFT here
            drive.determineCommand("STRAFE-LEFT", 1, 100, telemetry);
            drive.determineCommand("FORWARD", 1, 110, telemetry);
            intaker.setSpinPower(-1.0);
            drive.determineCommand("BACKWARD",1,100, telemetry );
            intaker.setStopPower(0);
            drive.determineCommand("STRAFE-RIGHT",1,200,telemetry);
            drive.determineCommand("FORWARD",1,100,telemetry);
            drive.turnMovement("LEFT",50,telemetry,1);//rotate to backdrop
            drive.determineCommand("BACKWARD",1,10,telemetry);//get to the wall
            grabber.openServo(0.7);
            //determine parking left or right
            telemetry.addLine("It's left");
        } else if(selection == ThreeRectangleProcessor.Selected.RIGHT){
            // run AUTO program RIGHT here
            drive.determineCommand("STRAFE-RIGHT", 1 ,100, telemetry);
            drive.determineCommand("FORWARD", 1, 50, telemetry);
            intaker.setSpinPower(-1.0);
            drive.determineCommand("BACKWARD",1,100, telemetry );
            intaker.setStopPower(0);
            drive.determineCommand("STRAFE-RIGHT",1,100,telemetry);
            drive.determineCommand("FORWARD",1,100,telemetry);
            drive.turnMovement("LEFT",50,telemetry,1);//face the wall
            grabber.openServo(0.7);
            //determine parking
            telemetry.addLine("It's right");
        } else if (selection == ThreeRectangleProcessor.Selected.MIDDLE){
            // run AUTO program MIDDLE here
            drive.determineCommand("FORWARD", 1, 150,telemetry);
            //intake
            drive.determineCommand("BACKWARD",1,140,telemetry);
            drive.determineCommand("STRAFE-RIGHT",1,100,telemetry);
            drive.determineCommand("FORWARD",1,100,telemetry);
            drive.turnMovement("LEFT",50,telemetry,1);//face the backdrop
            drive.determineCommand("FORWARD",1,10,telemetry);//get to the backdrop
            grabber.openServo(0.7);
            //determine parking
            telemetry.addLine("It's middle");
        } else {
            drive.determineCommand("FORWARD", 1, 150,telemetry);
            //intake command
            drive.determineCommand("BACKWARD",1,140,telemetry);
            drive.determineCommand("STRAFE-RIGHT",1,100,telemetry);
            drive.determineCommand("FORWARD",1,100,telemetry);
            drive.turnMovement("LEFT",50,telemetry,1);//face the backdrop
            drive.determineCommand("FORWARD",1,10,telemetry);//get to the backdrop
            grabber.openServo(0.7);
            // figure out what to do if we can't find anything. perhaps just run the middle program?
            telemetry.addLine("Not Found");

        }
            telemetry.update();
    }
}
