package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Drive Forward (2 seconds)", group="Testing")
public class AlanAutonomous extends LinearOpMode {

    // Declare OpMode members
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private Servo servoTesting = null;
    private DcMotor linearSlide = null;
    static final int tickNumber = 1000;

    int armDownPosition = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeft  = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        servoTesting = hardwareMap.get(Servo.class, "servos");
        linearSlide = hardwareMap.get(DcMotor.class, "linearslide");
        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        servoTesting.setDirection(Servo.Direction.FORWARD);
        linearSlide.setDirection(DcMotor.Direction.FORWARD);

        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setTargetPosition(armDownPosition);
        double linearSlidePosition = linearSlide.getCurrentPosition();

        driveForward(0.5, 2000);
        strafeRight(0.5, 2000);
        driveBackward(0.5, 2000);
        strafeLeft(0.5, 2000);

    }

    public void isBusy() {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Every motor is successfully working");
            telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }


    //DRIVING FORWARDS

    public void driveForward(double speed, int ticks) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

        isBusy();
    }

    //DRIVING BACKWARDS

    public void driveBackward(double speed, int ticks) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);


        isBusy();
    }


    //STRAFE LEFT

    public void strafeLeft(double speed, int ticks) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);


        isBusy();
    }



    // STRAFE RIGHT

    public void strafeRight(double speed, int ticks) {

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);


        isBusy();
    }



    public void turnLeft(double speed, int ticks) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

        isBusy();
    }

    public void turnRight(double speed, int ticks) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

        isBusy();

    }



    //MOVING LINEAR SLIDE

    public void moveLinearSlide(double speed, int ticks) {
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentPos = linearSlide.getCurrentPosition();
        int goalPosition = currentPos + ticks;
        linearSlide.setTargetPosition(goalPosition);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(speed);



        while (linearSlide.isBusy()) {
            telemetry.addLine("Linear slide is moving successfully");
        }



        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlide.setPower(0);
    }


}