package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hanger {
    private DcMotor actualHanger = null;
    private DcMotor actualHanger1 = null;
    private int upTicks;
    private int downTicks;
    private int currentFirstPosition;
    private int currentSecondPosition;
    private int maxPosition;
    private int minPosition;
    private int goalUpPosition;
    private int goalUpPosition1;
    private int goalDownPosition;
    private int goalDownPosition1;
    private double hangerPower;
    private double updatedFirstPosition;

    public Hanger(HardwareMap hardwareMap) {
        actualHanger = hardwareMap.get(DcMotor.class, "hanger");
        actualHanger.setDirection(DcMotor.Direction.FORWARD);
        actualHanger.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actualHanger.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        actualHanger1 = hardwareMap.get(DcMotor.class, "hanger1");
        actualHanger1.setDirection(DcMotor.Direction.FORWARD);
        actualHanger1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actualHanger1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        actualHanger.setTargetPosition(0);
        actualHanger1.setTargetPosition(0);
    }

    public void hang(Gamepad gamepad, Telemetry telemetry) {
        hangerTrigger(gamepad, telemetry);
    }

    public void hangerTrigger(Gamepad gamepad, Telemetry telemetry) {
        currentFirstPosition = actualHanger.getCurrentPosition();
        currentSecondPosition = actualHanger1.getCurrentPosition();

        if (gamepad.right_trigger > 0) {
            goalUpPosition = currentFirstPosition + upTicks;
            goalUpPosition1 = currentSecondPosition + upTicks;
            if (goalUpPosition > maxPosition && goalUpPosition1 > maxPosition) {
                goalUpPosition = maxPosition;
                goalUpPosition1 = maxPosition;
            }
            actualHanger.setTargetPosition(goalUpPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(hangerPower);

            actualHanger1.setTargetPosition(goalUpPosition);
            actualHanger1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger1.setPower(hangerPower);

        }
        if (gamepad.left_trigger > 0) {
            goalDownPosition = currentFirstPosition - downTicks;
            goalDownPosition1 = currentSecondPosition - downTicks;
            if (goalDownPosition < minPosition && goalDownPosition1 < minPosition) {
                goalDownPosition = minPosition;
                goalDownPosition1 = minPosition;
            }
            actualHanger.setTargetPosition(goalDownPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(-hangerPower);

            actualHanger1.setTargetPosition(goalUpPosition);
            actualHanger1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger1.setPower(-hangerPower);

        }
        updatedFirstPosition = actualHanger.getCurrentPosition();
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

    public void setHangerPower(double hangerPower) {
        this.hangerPower = hangerPower;
    }
}
