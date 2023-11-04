package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.lib.MecanumDrive;
@Autonomous(name="DaiLo Auto", group="Linear OpMode")
public class Auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, DcMotorSimple.Direction.REVERSE);

        waitForStart();

        drive.setPowers(-0.3);
        sleep(3700);
        drive.setPowers(0);
    }
}
