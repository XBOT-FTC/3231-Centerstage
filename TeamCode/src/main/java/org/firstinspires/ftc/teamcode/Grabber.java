package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {
    private Servo servoGrabber = null;
    private double openPosition;
    private double closePosition;


        public Grabber(HardwareMap hardwareMap) {
            servoGrabber = hardwareMap.get(Servo.class, "servos");
            servoGrabber.setPosition(0);
        }
        public void grab(Gamepad gamepad, Telemetry telemetry) {

            if (gamepad.a) {
                servoGrabber.setPosition(openPosition);
                telemetry.addLine("Servo successfully opening up to position");

            } else if (gamepad.b) {
                servoGrabber.setPosition(closePosition);
                telemetry.addLine("Servo successfully closing up to position");
            }
            double servoPosition = servoGrabber.getPosition();
            telemetry.addData("Servo/Grabber position: ", servoPosition);
        }

        public void setPosition(double closePosition, double openPosition) {
            this.closePosition = closePosition;
            this.openPosition = openPosition;
        }


}
