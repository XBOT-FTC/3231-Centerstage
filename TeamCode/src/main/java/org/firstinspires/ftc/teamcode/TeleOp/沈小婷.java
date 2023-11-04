package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.Grabber;
import org.firstinspires.ftc.teamcode.lib.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.MecanumDrive;
import org.firstinspires.ftc.teamcode.lib.Xiaotingatron;

@TeleOp(name="沈小婷", group="Linear OpMode")
public class 沈小婷 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, DcMotorSimple.Direction.REVERSE);
        Xiaotingatron xiaotingatron = new Xiaotingatron(hardwareMap, Servo.Direction.FORWARD);

        // initializations:

        // drive
        drive.setSpeedModeLimiter(0.25);
        drive.setSpeedDecrement(0.2);
        drive.setRotatePower(0.3);

        // xiaotingatron
        xiaotingatron.setTingHoldingPosition(0.7);
        xiaotingatron.setTingScoringPosition(0.555);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drive.drive(gamepad1, telemetry);
            xiaotingatron.ting(gamepad2, telemetry);

            telemetry.update();
        }
    }
}
