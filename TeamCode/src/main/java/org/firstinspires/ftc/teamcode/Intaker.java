package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intaker {
    private DcMotor intaker;
    public boolean spinMode = false;
    public boolean reverseSpinMode = false;
    public double spinPower;
    public double stopPower;


    public Intaker(HardwareMap hardwareMap) {
        intaker = hardwareMap.get(DcMotor.class, "intaker");
        intaker.setDirection(CRServo.Direction.FORWARD);
    }

    public void moveIntake(Gamepad gamepad, Telemetry telemetry) {
        intakeTrigger(gamepad, telemetry);
    }

    public void intakeTrigger(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.b || gamepad.x) {
            if (gamepad.b) {
                intaker.setPower(-spinPower);
            } else if (gamepad.x) {
                intaker.setPower(spinPower);
            }


        } else {
            intaker.setPower(stopPower);
        }


        telemetry.addData("Current spinning power", spinPower);
    }




    public void setSpinPower(double spinPower) {
        this.spinPower = spinPower;
    }

    public void setStopPower(double stopPower) {
        this.stopPower = stopPower;
    }
    public void setIntakePower(double power){
        intaker.setPower(power);
    }
}
