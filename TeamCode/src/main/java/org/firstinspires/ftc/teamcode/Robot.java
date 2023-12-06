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
        grabber.setGrabPosition(1.0, 0.0);

        DroneShooter droneShooter = new DroneShooter(hardwareMap, Servo.Direction.FORWARD, 0.7, 0.1);

        //LINEAR SLIDE SETUPS
//        LinearSlide linearSlide = new LinearSlide(hardwareMap);
//        linearSlide.setMaxPosition(6400);
//        linearSlide.setMinPosition(0);
//        linearSlide.setUpTicks(150);
//        linearSlide.setDownTicks(150);
//        linearSlide.setLinearPower(0.6);

        //HANGER SETUPS
        Hanger hanger = new Hanger(hardwareMap);
        //(537.7 pulses per rotation) * (163.5 mm travel distance) / (8 mm per rotation)
        hanger.setMaxPosition(10000);
        hanger.setMinPosition(0);
        hanger.setUpTicks(540);
        hanger.setDownTicks(540);
        hanger.setHangerPower(1.0);

        // INTAKER SETUPS
        Intaker intaker = new Intaker(hardwareMap);
        intaker.setSpinPower(1.0);
        intaker.setStopPower(0.0);


        waitForStart();

        while(opModeIsActive()) {
            drive.drive(gamepad1, telemetry);
            grabber.grab(gamepad2, telemetry);
            intaker.moveIntake(gamepad2, telemetry);
            droneShooter.shootingControls(gamepad2, telemetry);
            hanger.moveHanger(gamepad2, telemetry);
            telemetry.update();
        }
    }
}
