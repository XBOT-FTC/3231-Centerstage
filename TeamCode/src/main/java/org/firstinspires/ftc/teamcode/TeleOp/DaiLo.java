package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.Grabber;
import org.firstinspires.ftc.teamcode.lib.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.MecanumDrive;
import org.firstinspires.ftc.teamcode.lib.Swivel;

@TeleOp(name="DaiLo", group="Linear OpMode")
public class DaiLo extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, DcMotorSimple.Direction.REVERSE);
        LinearSlide linearSlide = new LinearSlide(hardwareMap, DcMotorSimple.Direction.FORWARD);
        Swivel swivel = new Swivel(hardwareMap, DcMotorSimple.Direction.FORWARD);
        Grabber grabber = new Grabber(hardwareMap, Servo.Direction.FORWARD);


        // initializations:

        // drive
        drive.setSpeedModeLimiter(0.25);
        drive.setSpeedDecrement(0.2);
        drive.setRotatePower(0.3);

        // linear slide
        linearSlide.setSlidePower(1);
        linearSlide.setMaxPosition(2850);
        linearSlide.setTickChange(90);
        linearSlide.setSpeedModeLimiter(0.25);

        // swivel
        swivel.setSwivelPower(0.5);
        swivel.setMaxPosition(600);
        swivel.setTickChange(10);
        swivel.setSpeedModeLimiter(0.25);

        // grabber
        grabber.setOpenPosition(0.33);
        grabber.setClosePosition(0.31);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drive.drive(gamepad1, telemetry);
            linearSlide.slide(gamepad2, telemetry);
            grabber.openClose(gamepad2, telemetry);
            swivel.swivel(gamepad2, telemetry);

            telemetry.update();
        }
    }
}
