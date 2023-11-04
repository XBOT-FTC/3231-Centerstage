package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/* CONTROLS
   DPAD UP: LINEAR SLIDE UP
   DPAD DOWN: LINEAR SLIDE DOWN
   A: PRECISION MODE
*/
public class LinearSlide {

    private DcMotor linearSlideLeft = null;
    private DcMotor linearSlideRight = null;
    public int maxPosition = 0;
    public double power = 0;
    public int tickChange = 0;
    public boolean precisionMode = false;
    public int precisionLoop = 0;
    private double speedModeLimiter = 0;


    public LinearSlide(HardwareMap hardwareMap, DcMotorSimple.Direction direction) {
        // motor for left linear slide, sets up encoders
        linearSlideLeft = hardwareMap.get(DcMotor.class, "l_slide");
        linearSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        linearSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
        linearSlideLeft.setTargetPosition(0);
        linearSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideLeft.setDirection(direction);

        // motor for right linear slide, sets up encoders
        linearSlideRight = hardwareMap.get(DcMotor.class, "r_slide");
        linearSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        linearSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
        linearSlideRight.setTargetPosition(0);
        linearSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideRight.setDirection(direction.inverted());
    }

    public void slide(Gamepad gamepad, Telemetry telemetry) {
        slideDPad(gamepad, telemetry);
    }

    private void slideDPad(Gamepad gamepad, Telemetry telemetry) {
        int position = linearSlideLeft.getCurrentPosition();

        // linear slide add 250 with dpad
        if (gamepad.dpad_up && position < maxPosition) {
            position += tickChange;
            if (position > maxPosition) {
                position = maxPosition;
            }
        }
        else if (gamepad.dpad_down && position > 0) {
            position -= tickChange;
            if (position < 0) {
                position = 0;
            }
        }

        precisionModeSwitch(gamepad);

        linearSlideLeft.setTargetPosition(position);
        linearSlideRight.setTargetPosition(position);
        linearSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (linearSlideLeft.getCurrentPosition() < position) {
            if (precisionMode) {
                linearSlideLeft.setPower(power *= speedModeLimiter);
                linearSlideRight.setPower(power *= speedModeLimiter);
            }
            else {
                linearSlideLeft.setPower(power);
                linearSlideRight.setPower(power);
            }
        }
        else if (linearSlideLeft.getCurrentPosition() > position) {
            if (precisionMode) {
                linearSlideLeft.setPower(power *= -speedModeLimiter);
                linearSlideRight.setPower(power *= -speedModeLimiter);
            }
            else {
                linearSlideLeft.setPower(-power);
                linearSlideRight.setPower(-power);
            }
        }

        int currentPositionLeft = linearSlideLeft.getCurrentPosition();
        int currentPositionRight = linearSlideRight.getCurrentPosition();
        telemetry.addData("Current Left Slide Position", currentPositionLeft);
        telemetry.addData("Current Right Slide Position", currentPositionRight);
        telemetry.addData("Slide Goal Position", position);
        telemetry.addData("Slide Precision Mode Status", precisionMode);
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

    public void setSlidePower(double power) {
        this.power = power;
    }

    public void setTickChange(int tickChange) {
        this.tickChange = tickChange;
    }

    public void setMaxPosition(int maxTicks) {
        this.maxPosition = maxTicks;
    }

    public void setSpeedModeLimiter(double speedModeLimiter) {
        this.speedModeLimiter = speedModeLimiter;
    }
}
