package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/* CONTROLS
   LEFT JOYSTICK: STRAFE
   RIGHT JOYSTICK: ROTATE
   DPAD UP: INCREMENT SPEED PERCENTAGE
   DPAD DOWN: DECREMENT SPEED PERCENTAGE
   A: PRECISION MODE
 */
public class MecanumDrive {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    public double speedModeLimiter = 0;
    public boolean precisionMode = false;
    private double speedChange;
    private int count = 0;
    private int precisionLoop = 0;
    private int speedChangeLoop = 0;
    private double speedDecrement = 0;
    public double rotatePower;

    public MecanumDrive(HardwareMap hardwareMap, DcMotorSimple.Direction direction) {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "lf_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb_drive");
        rightFrontDrive  = hardwareMap.get(DcMotor.class, "rf_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb_drive");

//        setModes();

        leftFrontDrive.setDirection(direction);
        leftBackDrive.setDirection(direction);
        rightFrontDrive.setDirection(direction.inverted());
        rightBackDrive.setDirection(direction.inverted());
    }

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        // y and x are for moving, rotate is for rotating
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x * 1.5;
        double rotate = gamepad.right_stick_x;


        // denominator cuts down values if sum exceeds 1
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rotate), 1);

        // sets powers to formula of joystick values
        double leftFrontPower = (y + x + rotate) / denominator;
        double leftBackPower = (y - x + rotate) / denominator;
        double rightFrontPower = (y - x - rotate) / denominator;
        double rightBackPower = (y + x - rotate) / denominator;

        // decrement or increment speed percentage
        speedChange(gamepad);

        // precision mode (change if statement condition to change button for precision mode)
        precisionModeSwitch(gamepad);

        // turn 180 degrees
//        turn180(gamepad);

        // send power to the wheels
        if (precisionMode) {
            leftFrontDrive.setPower(leftFrontPower *= speedModeLimiter);
            leftBackDrive.setPower(leftBackPower *= speedModeLimiter);
            rightFrontDrive.setPower(rightFrontPower *= speedModeLimiter);
            rightBackDrive.setPower(rightBackPower *= speedModeLimiter);
        }
        if (!precisionMode) {
            leftFrontDrive.setPower(leftFrontPower *= speedChange);
            leftBackDrive.setPower(leftBackPower *= speedChange);
            rightFrontDrive.setPower(rightFrontPower *= speedChange);
            rightBackDrive.setPower(rightBackPower *= speedChange);
        }

        telemetry.addData("Front Motors", "Left Front (%.2f), Right Front (%.2f)", leftFrontPower, rightFrontPower);
        telemetry.addData("Back Motors", "Left Back (%.2f), Right Back (%.2f)", leftBackPower, rightBackPower);
        telemetry.addData("Drive Precision Mode Status", precisionMode);
        telemetry.addData("Speed Change", speedChange);
//        encoderPositionsTelemetry(telemetry);
    }

    // speed goes down or up in percentage of speedchange
    public void speedChange(Gamepad gamepad) {
        if (gamepad.dpad_up) {
            if (count > 0 && speedChangeLoop == 0) {
                count--;
                speedChangeLoop++;
            }
        }
        else if (gamepad.dpad_down) {
            if (count < (1 / speedDecrement) - 1 && speedChangeLoop == 0) {
                count++;
                speedChangeLoop++;
            }
        }
        else {
            speedChangeLoop = 0;
        }
        speedChange = 1 - speedDecrement * count;
    }

    // switches precision mode on or off if press a
    public void precisionModeSwitch(Gamepad gamepad) {
        if (gamepad.a) {
            if (precisionLoop == 0) {
                precisionMode = !precisionMode;
                precisionLoop++;
            }
        }
        else {
            precisionLoop = 0;
        }
    }

    public void turn180(Gamepad gamepad) {
        if (gamepad.b) {
            setTargetPositions(300);
            setRunToPositions();
            setPowers(rotatePower);
        }
    }

    public void encoderPositionsTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Front Ticks", leftFrontDrive.getCurrentPosition());
        telemetry.addData("Left Back Ticks", leftBackDrive.getCurrentPosition());
        telemetry.addData("Right Front Ticks", rightFrontDrive.getCurrentPosition());
        telemetry.addData("Right Back Ticks", rightBackDrive.getCurrentPosition());
    }

    public void setModes() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setTargetPositions(int position) {
        leftFrontDrive.setTargetPosition(-position);
        leftBackDrive.setTargetPosition(-position);
        rightFrontDrive.setTargetPosition(position);
        rightBackDrive.setTargetPosition(position);
    }

    public void setRunToPositions() {
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPowers(double power) {
        leftFrontDrive.setPower(-power);
        leftBackDrive.setPower(-power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);
    }

    public void setSpeedModeLimiter(double speedModeLimiter) {
        this.speedModeLimiter = speedModeLimiter;
    }

    public void setSpeedDecrement(double speedDecrement) {
        this.speedDecrement = speedDecrement;
    }

    public void setRotatePower(double power) {
        rotatePower = power;
    }

}
