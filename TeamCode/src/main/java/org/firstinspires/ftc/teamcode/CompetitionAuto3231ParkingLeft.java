package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ThreeRectangleProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Disabled
@Autonomous(name = "CompAutoParkingLeft", group="Test")
public class CompetitionAuto3231ParkingLeft extends LinearOpMode {

  /*  VisionPortal myVisionPortal;
    ThreeRectangleProcessor threeRectangleProcessor;
*/
    @Override
    public void runOpMode() throws InterruptedException {
      /*
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
        */

        //DRIVE SETUPS
        MecanumDrive drive = new MecanumDrive(hardwareMap);

        //GRABBER SETUPS
        Grabber grabber = new Grabber(hardwareMap);
        grabber.setPosition(0.0, 0.7);
        telemetry.addLine("waiting for start");
        telemetry.update();
        //intaker setup
        Intaker intaker = new Intaker(hardwareMap);
        intaker.setSpinPower(1.0);
        intaker.setStopPower(0.0);
        waitForStart();
        telemetry.addLine("auto started");
        //make sure the robot's back is facing towards the backdrop
        drive.determineCommand("STRAFE-RIGHT",.5,1000,telemetry);
        drive.determineCommand("BACKWARD",.5,400,telemetry);//assuming it touches the backdrop?
        grabber.openServo(0.7);//score pixel (note on 12/1/23 : find this to work during the comp)
        drive.determineCommand("STRAFE-RIGHT",0.5,1000,telemetry);
        drive.determineCommand("BACKWARD",0.5,1000,telemetry);
        telemetry.addLine("auto stopped");
        telemetry.update();
        //parked

  /*      if(selection == ThreeRectangleProcessor.Selected.LEFT){
            // run AUTO program LEFT here
            drive.determineCommand("FORWARD", 1, 50, telemetry);//basic movement to go towards the teamprop
            drive.turnMovement("LEFT", 50, telemetry,1); // rotate to face according place 90 degrees btw
            drive.determineCommand("FORWARD",1,50,telemetry);//push pixel in, may change based off if ramming into
            //insert sucking command or whatever it is called
            drive.determineCommand("BACKWARD",1,100, telemetry );//to make space
            drive.determineCommand("STRAFE-LEFT", 1,50,telemetry);
            drive.determineCommand("FORWARD", 1,50,telemetry);
            drive.turnMovement("RIGHT",100,telemetry,1);//go thru tunnel 90 degrees
            drive.determineCommand("FORWARD",1,100,telemetry);
            drive.turnMovement("RIGHT",50,telemetry,1);// 90 degrees facing backwards
            drive.determineCommand("BACKWARD", 1,50,telemetry); // all that leads to the wall
            //idk go left or right for parking, decide later
            grabber.openServo(0.7);//opens servo


        } else if(selection == ThreeRectangleProcessor.Selected.RIGHT){
            // run AUTO program RIGHT here
            drive.determineCommand("FORWARD", 1, 100, telemetry);
            drive.turnMovement("RIGHT", 50,telemetry,1);//change if it's a different function
            //insert sucking command or whatever it is called involves going foward? gotta test  to find out
            drive.determineCommand("FORWARD",1,50,telemetry);//push pixel
            drive.determineCommand("BACKWARD",1,100, telemetry );
            drive.turnMovement("LEFT",50,telemetry,1);//90 degrees
            drive.determineCommand("BACKWARD",1,50,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,50,telemetry);
            drive.determineCommand("FORWARD",1,50,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,50,telemetry);//goes to wall with this
            drive.turnMovement("RIGHT",50,telemetry,1);//should make the robot face the backdrop with the back
            grabber.openServo(0.7);//should open servo
            //idk go left or right for parking, decide later
            telemetry.addLine("It's right");
        } else if (selection == ThreeRectangleProcessor.Selected.MIDDLE){
            // run AUTO program MIDDLE here
            drive.determineCommand("FORWARD", 1, 150,telemetry);
            //intaker command
            drive.determineCommand("BACKWARD",1,140,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,150,telemetry);
            drive.determineCommand("FORWARD", 1, 100,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,100,telemetry);
            drive.turnMovement("RIGHT",50,telemetry,1);//rotates 90 left to make back face the backdrop
            drive.determineCommand("FORWARD",1,100,telemetry);
            grabber.openServo(0.7);//open servo to score
            //idk go left or right for parking, decide later
            telemetry.addLine("It's middle");
        } else {
            // figure out what to do if we can't find anything. perhaps just run the middle program?
            telemetry.addLine("Not Found");
            drive.determineCommand("FORWARD", 1, 150,telemetry);
            drive.determineCommand("BACKWARD",1,140,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,150,telemetry);
            drive.determineCommand("FORWARD", 1, 100,telemetry);
            drive.determineCommand("STRAFE-LEFT",1,100,telemetry);
            drive.turnMovement("RIGHT",50,telemetry,1);//rotates 90 left to make back face the backdrop
            drive.determineCommand("FORWARD",1,100,telemetry);
            grabber.openServo(0.7);
        } */
            telemetry.update();
    }
}
