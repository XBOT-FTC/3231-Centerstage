package org.firstinspires.ftc.teamcode;

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

public class LinearSlide {
    private DcMotor linearSlide = null;
    private int upTicks;
    private int downTicks;
    private int currentFirstPosition;

    private int maxPosition;
    private int minPosition;
    private int goalUpPosition;
    private int goalDownPosition;
    private double linearPower;
    private double updatedFirstPosition;

    public LinearSlide(HardwareMap hardwareMap) {
        linearSlide = hardwareMap.get(DcMotor.class, "linearslide");
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        linearSlide.setTargetPosition(0);

    }

    public void slide(Gamepad gamepad, Telemetry telemetry) {
        slideTrigger(gamepad, telemetry);
    }

    public void slideTrigger(Gamepad gamepad, Telemetry telemetry) {
        currentFirstPosition = linearSlide.getCurrentPosition();


        if (gamepad.right_bumper) {
            goalUpPosition = currentFirstPosition + upTicks;

                if (goalUpPosition > maxPosition) {
                    goalUpPosition = maxPosition;
                }
                linearSlide.setTargetPosition(goalUpPosition);
                linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                linearSlide.setPower(linearPower);
        }
        if (gamepad.left_bumper) {
            goalDownPosition = currentFirstPosition - downTicks;
            if (goalDownPosition < minPosition) {
                goalDownPosition = minPosition;
            }
            linearSlide.setTargetPosition(goalDownPosition);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlide.setPower(-linearPower);

        }

        if (Math.abs(linearSlide.getTargetPosition() - linearSlide.getCurrentPosition()) < 25) {
            linearSlide.setPower(0);
        }
        updatedFirstPosition = linearSlide.getCurrentPosition();
        telemetry.addLine("Current Position for linear slides" + updatedFirstPosition);

    }
    public void setMaxPosition(int maxPosition) {
        this.maxPosition = maxPosition;
    }
    public void setMinPosition(int minPosition) {
        this.minPosition = minPosition;
    }
    public void setUpTicks(int upTicks) {
        this.upTicks = upTicks;
    }
    public void setDownTicks(int downTicks) {
        this.downTicks = downTicks;
    }

    public void setLinearPower(double linearPower) {
        this.linearPower = linearPower;
    }



}