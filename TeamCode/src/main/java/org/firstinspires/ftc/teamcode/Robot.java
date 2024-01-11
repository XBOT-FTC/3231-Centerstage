package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MD: Robot", group="Linear Opmode")
public class Robot extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        //DRIVE SETUPS
        MecanumDrive drive = new MecanumDrive(hardwareMap);


        Grabber grabber = new Grabber(hardwareMap);
        grabber.setGrabPosition(0.675, 0.56);

        DroneShooter droneShooter = new DroneShooter(hardwareMap, Servo.Direction.FORWARD, 0.7, 0.1);

        //HANGER SETUPS
        Hanger hanger = new Hanger(hardwareMap);
        //(537.7 pulses per rotation) * (163.5 mm travel distance) / (8 mm per rotation)
        hanger.setMaxPosition(10000);
        hanger.setMinPosition(0);
        hanger.setUpTicks(200);
        hanger.setDownTicks(200);
        hanger.setHangerPower(1.0);

        Intaker intaker = new Intaker(hardwareMap);
        intaker.setSpinPower(0.5);
        intaker.setStopPower(0.0);

//       SNAPPER SETUPS
        Snapper snapper = new Snapper(hardwareMap);


        waitForStart();

        while(opModeIsActive()) {
            drive.drive(gamepad1, telemetry);
            grabber.grab(gamepad1, telemetry);
            intaker.moveIntake(gamepad2, telemetry);
            droneShooter.shootingControls(gamepad2, telemetry);
            hanger.moveHanger(gamepad2, telemetry);
            snapper.moveSnapper(gamepad2, telemetry);
            telemetry.update();
        }
    }
}
