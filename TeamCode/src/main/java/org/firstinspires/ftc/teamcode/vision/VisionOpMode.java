package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "Vision Op Mode", group="Vision")
public class VisionOpMode extends LinearOpMode {

    private ThreeRectangleProcessor threeRectangleProcessor;
    private VisionPortal visionPortal;

    private ThreeRectangleProcessor.Selected selected;


    @Override
    public void runOpMode() throws InterruptedException {

        threeRectangleProcessor = new ThreeRectangleProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), threeRectangleProcessor);

        // Detect where the team prop is at during initialization
        while(!opModeIsActive()) {
            telemetry.addLine("Op Mode is Not Active");
            selected = threeRectangleProcessor.getSelection();
            telemetry.addLine(selected.name() + " is selected.");
            telemetry.update();
        }
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addLine("opModeIsActive()");
            telemetry.addLine(selected.name() + " is where the team prop is located at");
            telemetry.update();
        }

    }
}
