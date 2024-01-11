package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Snapper {
    private CRServo snapper = null;
    private double correctPosition = 1.0;
    private double currentLocation;

    private boolean snapperOn = false;

    public Snapper(HardwareMap hardwareMap) {
        snapper = hardwareMap.get(CRServo.class, "snapper");
        snapper.setDirection(CRServo.Direction.FORWARD);
    }

    public void moveSnapper(Gamepad gamepad, Telemetry telemetry) {
        snapperTrigger(gamepad, telemetry);
    }

    public void snapperTrigger(Gamepad gamepad, Telemetry telemetry) {

            if (gamepad.right_bumper) {
                snapper.setPower(1.0);
            } else if (gamepad.left_bumper) {
                snapper.setPower(-1.0);
            } else {
                snapper.setPower(0);
            } else {
                snapper.setPower(0.0);
            }

            telemetry.addData("Snapper power:", snapper.getPower());

    }

}