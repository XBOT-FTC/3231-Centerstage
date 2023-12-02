package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {
    private Servo servoGrabber = null;
    private Servo servoGrabber1 = null;
    private double openPosition = 1;
    private double closePosition = 0;
    private boolean grabMode = false;
    private boolean grabMode1= false;
    private boolean aPress;
    private boolean bPress;

        public Grabber(HardwareMap hardwareMap) {
            servoGrabber = hardwareMap.get(Servo.class, "servo");
            servoGrabber.setPosition(0);

            servoGrabber1 = hardwareMap.get(Servo.class, "ting");
            servoGrabber1.setPosition(0);
        }



        public void grab(Gamepad gamepad, Telemetry telemetry) {

            if (gamepad.a) {
                aPress = true;
            } else {
                if (aPress) {
                    aPress = false;
                    grabMode = !grabMode;
                }
            }

            if (gamepad.b) {
                bPress = true;
            } else {
                if (bPress) {
                    bPress = false;
                    grabMode1 = !grabMode1;
                }
            }

            setGrabbingForFirstServo();
            setGrabbingForSecondServo();

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

      public void setGrabbingForSecondServo() {
            if (grabMode1) {
                servoGrabber1.setPosition(openPosition);
            } else {
                servoGrabber1.setPosition(closePosition);
            }
      }

        public void setPosition(double closePosition, double openPosition) {
            this.closePosition = closePosition;
            this.openPosition = openPosition;
        }
        public void openServo(double openPosition){
            servoGrabber.setPosition(openPosition);
        }


}
