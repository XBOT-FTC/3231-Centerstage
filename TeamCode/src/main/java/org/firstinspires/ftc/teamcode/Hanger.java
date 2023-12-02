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
//        if(gamepad.y) {
//            actualHanger.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            actualHanger.setPower(-0.25);
//        }
        if (gamepad.right_trigger > 0) {
            goalUpPosition = currentFirstPosition + upTicks;
//            if (goalUpPosition > maxPosition) {
//                goalUpPosition = maxPosition;
//            }
            actualHanger.setTargetPosition(goalUpPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(hangerPower);
//
//
        }
        if (gamepad.left_trigger > 0) {
            goalDownPosition = currentFirstPosition - downTicks;
//            if (goalDownPosition < minPosition) {
//               goalDownPosition = minPosition;
//            }
            actualHanger.setTargetPosition(goalDownPosition);
            actualHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            actualHanger.setPower(-hangerPower);

        }
        telemetry.addData("Current Position for linear slides %d", actualHanger.getCurrentPosition());
        telemetry.addLine("");

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
