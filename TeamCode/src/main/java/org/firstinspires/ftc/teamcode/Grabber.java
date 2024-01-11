package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {
    private Servo servoGrabber = null;
    //    private Servo servoGrabber1 = null;
    private double openPosition = 1;
    private double closePosition = 0;
    private boolean grabMode = false;
    //    private boolean grabMode1= false;
    private boolean bPress = false;

    public Grabber(HardwareMap hardwareMap) {
        servoGrabber = hardwareMap.get(Servo.class, "servo");
        //servoGrabber.setPosition(openPosition);
        servoGrabber.setDirection(Servo.Direction.FORWARD);
    }



    public void grab(Gamepad gamepad, Telemetry telemetry) {

        if (gamepad.b) {
            if (!bPress) {
                bPress = true;
            }
        } else {
            if (bPress) {
                bPress = false;
                grabMode = !grabMode;
            }
        }

        if (gamepad.left_bumper) {
            if(grabMode) {
                this.openPosition += 0.0025;
            } else {
                this.closePosition += 0.0025;
            }
        } else if (gamepad.right_bumper) {
            if(grabMode) {
                this.openPosition -= 0.0025;
            } else {
                this.closePosition -= 0.0025;
            }
        }
        this.setGrabbingForFirstServo();
        // set the servo to move
        double servoPosition = servoGrabber.getPosition();
        telemetry.addData("Servo/Grabber position: ", servoPosition);
    }

    public void setGrabbingForFirstServo() {
        if (grabMode) {
            servoGrabber.setPosition(openPosition);
        } else {
            servoGrabber.setPosition(closePosition);
        }
    }

    public void setPosition(double closePosition, double openPosition) {
        this.closePosition = closePosition;
        this.openPosition = openPosition;
    }
    public void openServo(double openPosition){
        servoGrabber.setPosition(openPosition);
    }


    public void setGrabPosition(double closePosition, double openPosition) {
        this.closePosition = closePosition;
        this.openPosition = openPosition;
    }
}