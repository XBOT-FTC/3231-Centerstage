package org.firstinspires.ftc.teamcode;

import android.sax.StartElementListener;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class MecanumDrive {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;
    private double speedChange;
    private double defaultSpeed = 0.5;
    private double maxSpeed = 1.0;
    private double minSpeed = 0.25;
    private double goalUpSpeed;
    private double goalDownSpeed;

    public MecanumDrive(HardwareMap hardwareMap) throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(Gamepad gamepad, Telemetry telemetry) {

        // Setup a variable for each drive wheel to save power level for telemetry


        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double rx = gamepad.right_stick_x;

        frontLeftPower = y + x + rx;
        frontRightPower = y - x - rx;
        backLeftPower = y - x + rx;
        backRightPower = y + x - rx;

        if (gamepad.dpad_down) {
            goalDownSpeed = defaultSpeed - speedChange;
            if (goalDownSpeed > minSpeed) {
                defaultSpeed -= speedChange;
            }
        }

        if (gamepad.dpad_up) {
            goalUpSpeed = defaultSpeed + speedChange;
            if (goalUpSpeed < maxSpeed) {
                defaultSpeed += speedChange;
            }
        }

        frontLeftPower = defaultSpeed;
        frontRightPower = defaultSpeed;
        backLeftPower = defaultSpeed;
        backRightPower = defaultSpeed;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);


        if (gamepad.y) {
            verticalMovement(1000, telemetry);
        }

        if (gamepad.a) {
            verticalMovement(-1000, telemetry);
        }

        if (gamepad.x) {
            horizontalMovement("LEFT", 1000, telemetry);
        }

        if (gamepad.b) {
            horizontalMovement("RIGHT", 1000, telemetry);
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "frontleft (%.2f), frontright (%.2f), backleft (%.2f), backright (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.update();
    }
//    public void determineCommand(String command, double speed, int ticks, Telemetry telemetry) {
//        //FORWARD
//        if (command.equals("FORWARD")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
//
//        //BACKWARDS
//        } else if (command.equals("BACKWARD")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
//
//        //STRAFE LEFT
//        } else if (command.equals("STRAFE-LEFT")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
//
//
//        //STRAFE RIGHT
//        } else if (command.equals("STRAFE-RIGHT")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
//
//        //TURN RIGHT
//        } else if (command.equals("TURN-RIGHT")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);
//
//        //TURN LEFT
//        } else if (command.equals("TURN-LEFT")) {
//            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
//            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
//            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
//            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
//
//        //IF COMMAND IS INVALID
//        } else {
//            return;
//        }
//
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setPower(speed);
//        frontRight.setPower(speed);
//        backLeft.setPower(speed);
//        backRight.setPower(speed);
//
//        if (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
//            telemetry.addLine("Motors are successfully moving");
//        }
//    }

    public void verticalMovement(int verticalTicks, Telemetry telemetry) {
        setTargetPosition(frontLeft.getCurrentPosition() + verticalTicks, frontRight.getCurrentPosition() + verticalTicks, backLeft.getCurrentPosition() + verticalTicks, backRight.getCurrentPosition() + verticalTicks);
        setRealSpeed();
        runToPosition();
        setVerticalTelemetry(telemetry);
    }

    public void horizontalMovement(String way, int horizontalTicks, Telemetry telemetry) {
        if (way.equals("LEFT")) {
            setTargetPosition(frontLeft.getCurrentPosition() - horizontalTicks, frontRight.getCurrentPosition() + horizontalTicks, backLeft.getCurrentPosition() + horizontalTicks, backRight.getCurrentPosition() - horizontalTicks);
            setRealSpeed();
            runToPosition();
            setStrafeLeftTelemetry(telemetry);
        } else if (way.equals("RIGHT")) {
            setTargetPosition(frontLeft.getCurrentPosition() + horizontalTicks, frontRight.getCurrentPosition() - horizontalTicks, backLeft.getCurrentPosition() - horizontalTicks, backRight.getCurrentPosition() + horizontalTicks);
            setRealSpeed();
            runToPosition();
            setStrafeRightTelemetry(telemetry);
        }
    }

    public void setTargetPosition(int flTicks, int frTicks, int blTicks, int brTicks) {
        frontLeft.setTargetPosition(flTicks);
        frontRight.setTargetPosition(frTicks);
        backLeft.setTargetPosition(blTicks);
        backRight.setTargetPosition(brTicks);
    }

    public void setRealSpeed() {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    public void runToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setVerticalTelemetry(Telemetry telemetry) {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Motors are currently moving to desired position");
        }
    }

    public void setStrafeLeftTelemetry(Telemetry telemetry) {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Motors are currently strafing strafing left!");
        }
    }

    public void setStrafeRightTelemetry(Telemetry telemetry) {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Motors are currently strafing right!");
        }
    }


    public void setSpeedChange(double speedChange) {
        this.speedChange = speedChange;
    }
}
