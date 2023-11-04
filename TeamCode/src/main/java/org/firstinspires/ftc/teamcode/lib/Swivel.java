package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/* CONTROLS
   RIGHT BUMPER: SWIVEL UP
   LEFT BUMPER: SWIVEL DOWN
   A: PRECISION MODE
   B: GO TO SCORING SWIVEL
*/
public class Swivel {

    private DcMotor swivel = null;
    public int maxPosition = 0;
    public double power = 0;
    public int tickChange = 0;
    public boolean precisionMode = false;
    public int precisionLoop = 0;
    private double speedModeLimiter = 0;
    public int position = 0;
    public int scoringPosition = 0;

    public Swivel(HardwareMap hardwareMap, DcMotorSimple.Direction direction) {
        swivel = hardwareMap.get(DcMotor.class, "swivel");
        swivel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
        swivel.setTargetPosition(0);
        swivel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        swivel.setDirection(direction);
    }
    public void swivel(Gamepad gamepad, Telemetry telemetry) {

        // linear slide add 250 with dpad
        if (gamepad.right_bumper && swivel.getCurrentPosition() < maxPosition) {
            position += tickChange;
            if (position > maxPosition) {
                position = maxPosition;
            }
        }
        else if (gamepad.left_bumper && swivel.getCurrentPosition() > 0) {
            position -= tickChange;
            if (position < 0) {
                position = 0;
            }
        }

        precisionModeSwitch(gamepad);

        swivel.setTargetPosition(position);
        swivel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (swivel.getCurrentPosition() < position) {
            if (precisionMode) {
                swivel.setPower(power *= speedModeLimiter);
            }
            else {
                swivel.setPower(power);
            }
        }
        else if (swivel.getCurrentPosition() > position) {
            if (precisionMode) {
                swivel.setPower(power *= -speedModeLimiter);
            }
            else {
                swivel.setPower(-power);
            }
        }

        int currentPosition = swivel.getCurrentPosition();
        telemetry.addData("Current Swivel Position", currentPosition);
        telemetry.addData("Swivel Goal Position", position);
        telemetry.addData("Swivel Precision Mode Status", precisionMode);
        telemetry.addData("Swivel Power", power);
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

    public void scoringPosition(Gamepad gamepad) {
            if (gamepad.b) {
                position = scoringPosition;
            }
    }

    public void setSwivelPower(double power) {
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

