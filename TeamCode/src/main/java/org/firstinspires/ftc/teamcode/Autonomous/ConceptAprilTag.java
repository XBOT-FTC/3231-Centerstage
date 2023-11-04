package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Disabled
public class ConceptAprilTag extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    @Override
    public void runOpMode() throws InterruptedException {
        initAprilTag();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            List<AprilTagDetection> aprilTagDetectionList; // list of all AprilTags
            int aprilTagIdCode; // ID code of current detection in for() loop

            // list of AprilTag detections
            aprilTagDetectionList = aprilTagProcessor.getDetections();

            // cycle through list and process each ArilTag
            for (AprilTagDetection aprilTagDetection : aprilTagDetectionList) {
                if (aprilTagDetection.metadata != null) {
                    aprilTagIdCode = aprilTagDetection.id;
                }
            }
        }

    }

    private void initAprilTag() {
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setDrawAxes(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawCubeProjection(true)
                .build();


        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

    }
}
