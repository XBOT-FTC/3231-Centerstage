package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hanger {
    private DcMotor actualHanger = null;
    private int upTicks;
    private int downTicks;
    private int currentFirstPosition;
    private int maxPosition;
    private int minPosition;
    private int goalUpPosition;
    private int goalUpPosition1;
    private int goalDownPosition;
    private double hangerPower;
    private double updatedFirstPosition;

    public Hanger(HardwareMap hardwareMap) {
        actualHanger = hardwareMap.get(DcMotor.class, "hanger");
        actualHanger.setDirection(DcMotor.Direction.FORWARD);
        actualHanger.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actualHanger.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        actualHanger.setTargetPosition(0);
    }

    public void moveHanger(Gamepad gamepad, Telemetry telemetry) {
        hangerTrigger(gamepad, telemetry);
    }

    public void hangerTrigger(Gamepad gamepad, Telemetry telemetry) {
        currentFirstPosition = actualHanger.getCurrentPosition();

        if (gamepad.right_trigger > 0) {
            goalUpPosition = currentFirstPosition + upTicks;
            actualHanger.setTargetPosition(goalUpPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(hangerPower);
        } else if (gamepad.left_trigger > 0) {
            goalDownPosition = currentFirstPosition - downTicks;
            actualHanger.setTargetPosition(goalDownPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(-hangerPower);

        } else {
            actualHanger.setPower(0);
        }
//        if (Math.abs(actualHanger.getTargetPosition() - actualHanger.getCurrentPosition()) < 15) {
//            actualHanger.setPower(0);
//        }

        telemetry.addData("Current Position for linear slides", actualHanger.getCurrentPosition());
        telemetry.addData("Target Position for linear slides", actualHanger.getTargetPosition());

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
