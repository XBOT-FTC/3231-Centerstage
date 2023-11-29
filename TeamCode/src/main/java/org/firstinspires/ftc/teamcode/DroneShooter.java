package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DroneShooter {

    public double defaultPosition;
    public double shootingPosition;

    public Servo shooter;

    public boolean buttonPressed = false;
    public boolean shootingOn = false;

    public DroneShooter(HardwareMap hardwareMap, Servo.Direction direction, double defaultPosition, double shootingPosition) {
        this.defaultPosition = defaultPosition;
        this.shootingPosition = shootingPosition;

        this.shooter = hardwareMap.get(Servo.class, "shooter");
        this.shooter.setDirection(direction);
        setPosition(defaultPosition);
    }

    public void setPosition(double position) {
        this.shooter.setPosition(position);
    }

    public void shootingControls(Gamepad gamepad, Telemetry telemetry) {

        // fine tuning:
        if (gamepad.dpad_up) {
            if (shootingOn) {
                this.shootingPosition += 0.0001;
            } else {
                this.defaultPosition += 0.0001;
            }
        }
        else if (gamepad.dpad_down) {
            if (shootingOn) {
                this.shootingPosition -= 0.0001;
            } else {
                this.defaultPosition -= 0.0001;
            }
        }

        telemetry.addData("defaultPosition %f", this.defaultPosition);
        telemetry.addData("shootingPosition %f", this.shootingPosition);

        // logic for controlling if dpad is pressed and released.
        if (gamepad.a) {
            if (!buttonPressed) {
                buttonPressed = true;
                shootingOn = false;
            }
        } else {
            if (buttonPressed) {
                shootingOn = true;
                buttonPressed = false;
            }
        }

        // set the servo to move
        if(shootingOn) {
            setPosition(shootingPosition);
        } else {
            setPosition(defaultPosition);
        }
        telemetry.addData("Toggle: %b", buttonPressed);
        telemetry.addData("Shooting: %b", shootingOn);
        telemetry.addData("Shooter Position: %f", shooter.getPosition());
    }
}