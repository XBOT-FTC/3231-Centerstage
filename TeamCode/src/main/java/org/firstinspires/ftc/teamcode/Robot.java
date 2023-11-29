package org.firstinspires.ftc.teamcode;

import android.graphics.Path;
import android.nfc.cardemulation.HostNfcFService;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.libuvc.nativeobject.LibUsbDevice;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DroneShooter;

@TeleOp(name="MD: Robot", group="Linear Opmode")
public class Robot extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        //DRIVE SETUPS
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        drive.setSpeedChange(0.25);

        //GRABBER SETUPS
        Grabber grabber = new Grabber(hardwareMap);
        grabber.setPosition(0.0, 0.7);

        DroneShooter droneShooter = new DroneShooter(hardwareMap, Servo.Direction.FORWARD, 0.1, 0.1);

        //LINEAR SLIDE SETUPS
//        LinearSlide linearSlide = new LinearSlide(hardwareMap);
//        linearSlide.setMaxPosition(6400);
//        linearSlide.setMinPosition(0);
//        linearSlide.setUpTicks(150);
//        linearSlide.setDownTicks(150);
//        linearSlide.setLinearPower(0.6);

        //HANGER SETUPS
//        Hanger hanger = new Hanger(hardwareMap);
//        hanger.setMaxPosition(6400);
//        hanger.setMinPosition(0);
//        hanger.setUpTicks(250);
//        hanger.setDownTicks(200);
//        hanger.setHangerPower(1.0);

//      drive.determineCommand("FORWARD", 1.0, 600, telemetry);

        waitForStart();

        while(opModeIsActive()) {
            drive.drive(gamepad1, telemetry);
            grabber.grab(gamepad1, telemetry);
            droneShooter.shootingControls(gamepad1, telemetry);
//            linearSlide.slide(gamepad1, telemetry);
            telemetry.update();
        }
    }
}
