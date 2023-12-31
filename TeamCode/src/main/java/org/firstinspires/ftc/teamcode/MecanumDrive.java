package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    private boolean aPressed = false;
    private boolean speedMode = false;
    private double speedConstant;

    private double ticksPerInch;

    public MecanumDrive(HardwareMap hardwareMap) throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "lf_drive");
        frontRight = hardwareMap.get(DcMotor.class, "rf_drive");
        backLeft = hardwareMap.get(DcMotor.class, "lb_drive");
        backRight = hardwareMap.get(DcMotor.class, "rb_drive");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.speedConstant = 1;
    }

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Setup a variable for each drive wheel to save power level for telemetry


        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double rx = -gamepad.right_stick_x;

        frontLeftPower = y + x + rx;
        frontRightPower = y - x - rx;
        backLeftPower = y - x + rx;
        backRightPower = y + x - rx;

        if (gamepad.a) {
            if (!aPressed) {
                aPressed = true;
                speedMode = false;
            }
        } else {
            if (aPressed) {
                speedMode = true;
                aPressed = false;
            }
        }

        if (speedMode) {
            speedConstant = 0.3;
        } else {
            speedConstant = 1.0;
        }

        frontLeft.setPower(frontLeftPower * speedConstant);
        frontRight.setPower(frontRightPower * speedConstant);
        backLeft.setPower(backLeftPower * speedConstant);
        backRight.setPower(backRightPower * speedConstant);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "frontleft (%.2f), frontright (%.2f), backleft (%.2f), backright (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.addData("Speed Mode", speedMode);
    }
    public void determineCommand(String command, double speed, int ticks, Telemetry telemetry) {
        //FORWARD
        if (command.equalsIgnoreCase("FORWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        //BACKWARDS
        } else if (command.equalsIgnoreCase("BACKWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);

        //STRAFE LEFT
        } else if (command.equalsIgnoreCase("STRAFE-LEFT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);


        //STRAFE RIGHT
        } else if (command.equals("STRAFE-RIGHT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        //TURN RIGHT
        } else if (command.equals("TURN-RIGHT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);

        //TURN LEFT
        } else if (command.equals("TURN-LEFT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        //IF COMMAND IS INVALID
        } else {
            telemetry.addLine("ELSE");
            return;
        }

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);


        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("current position", frontLeft.getCurrentPosition());
            telemetry.addData("target position", frontLeft.getTargetPosition());
            telemetry.addLine("Motors are successfully moving");
            telemetry.update();
        }
    }

    public void verticalMovement(int verticalTicks, double speed, Telemetry telemetry) {
        setTargetPosition(frontLeft.getCurrentPosition() + verticalTicks, frontRight.getCurrentPosition() + verticalTicks, backLeft.getCurrentPosition() + verticalTicks, backRight.getCurrentPosition() + verticalTicks);
        setRealSpeed(speed);
        runToPosition();
        setVerticalTelemetry(telemetry);
    }

    public void horizontalMovement(String way, int horizontalTicks, Telemetry telemetry, double speed) {
        if (way.equalsIgnoreCase("LEFT")) {
            setTargetPosition(frontLeft.getCurrentPosition() - horizontalTicks, frontRight.getCurrentPosition() + horizontalTicks, backLeft.getCurrentPosition() + horizontalTicks, backRight.getCurrentPosition() - horizontalTicks);
            setRealSpeed(speed);
            runToPosition();
            setStrafeLeftTelemetry(telemetry);
        } else if (way.equalsIgnoreCase("RIGHT")) {
            setTargetPosition(frontLeft.getCurrentPosition() + horizontalTicks, frontRight.getCurrentPosition() - horizontalTicks, backLeft.getCurrentPosition() - horizontalTicks, backRight.getCurrentPosition() + horizontalTicks);
            setRealSpeed(speed);
            runToPosition();
            setStrafeRightTelemetry(telemetry);
        }
    }

    public void turnMovement(String turnWay, int turnTicks, Telemetry telemetry, double speed) {
        if (turnWay.equals("LEFT")) {
            setTargetPosition(frontLeft.getCurrentPosition() - turnTicks, frontRight.getCurrentPosition() + turnTicks, backLeft.getCurrentPosition() - turnTicks, backRight.getCurrentPosition() + turnTicks);
            setRealSpeed(speed);
            runToPosition();
            turnLeftTelemetry(telemetry);
        } else if (turnWay.equals("RIGHT")) {
            setTargetPosition(frontLeft.getCurrentPosition() + turnTicks, frontRight.getCurrentPosition() - turnTicks, backLeft.getCurrentPosition() + turnTicks, backRight.getCurrentPosition() - turnTicks);
            setRealSpeed(speed);
            runToPosition();
            turnRightTelemetry(telemetry);
        }
    }

    public void turnLeftTelemetry(Telemetry telemetry) {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Motors are currently turning left");
        }
    }

    public void turnRightTelemetry(Telemetry telemetry) {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addLine("Motors are currently turning right");
        }
    }

    public void setTargetPosition(int flTicks, int frTicks, int blTicks, int brTicks) {
        frontLeft.setTargetPosition(flTicks);
        frontRight.setTargetPosition(frTicks);
        backLeft.setTargetPosition(blTicks);
        backRight.setTargetPosition(brTicks);
    }

    public void setRealSpeed(double speed) {
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
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
}
