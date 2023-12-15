package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Snapper {
    private CRServo snapper = null;
    private double correctPosition = 1.0;
    private double currentLocation;

    public Snapper(HardwareMap hardwareMap) {
        snapper = hardwareMap.get(CRServo.class, "snapper");
        snapper.setDirection(CRServo.Direction.REVERSE);
    }

    public void snapperTrigger(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.left_bumper) {
            snapper.setPower(-0.75);
        }
        if (gamepad.right_bumper) {
            snapper.setPower(0.75);
        }

    }

}