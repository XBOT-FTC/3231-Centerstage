package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/* CONTROLS
   RIGHT BUMPER: SWITCH POSITION OF SERVO TING (RIGHT SERVO)
   LEFT BUMPER: SWITCH POSITION OF SERVO XIAO (LEFT SERVO)

   DPAD UP: SERVO INCREMENT
   DPAD DOWN: SERVO DECREMENT
*/
public class Xiaotingatron {

    private Servo xiao = null;
    private Servo ting = null;
    public int xiaoLoop = 0;
    public int tingLoop = 0;
    public double position = 0.77;
    public boolean xiaoToggleButton = false;
    public boolean tingToggleButton = false;

    public double xiaoHoldingPosition = 0;
    public double tingHoldingPosition = 0;
    public double xiaoScoringPosition = 0;
    public double tingScoringPosition = 0;
    public Xiaotingatron(HardwareMap hardwareMap, Servo.Direction direction) {
        xiao = hardwareMap.get(Servo.class, "xiao");
        ting = hardwareMap.get(Servo.class, "ting");
        xiao.setDirection(direction);
        ting.setDirection(direction);
    }

    public void xiao(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.left_bumper) {
            if (xiaoLoop == 0) {
                xiaoToggleButton = !xiaoToggleButton;
                xiaoLoop++;
            }
        }
        else {
            xiaoLoop = 0;
        }

        if (xiaoToggleButton) {
            xiao.setPosition(xiaoScoringPosition);
        }
        else {
            xiao.setPosition(xiaoHoldingPosition);
        }

//        if (gamepad.dpad_up) {
//            position += 0.0001;
//            xiao.setPosition(position);
//        }
//        else if (gamepad.dpad_down) {
//            position -= 0.0001;
//            xiao.setPosition(position);
//        }
//        xiao.setPosition(position);

        telemetry.addData("Xiao", "Servo (%.2f)", xiao.getPosition());
        telemetry.addData("xiaoToggleButton", xiaoToggleButton);
    }

    public void ting(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.right_bumper) {
            if (tingLoop == 0) {
                tingToggleButton = !tingToggleButton;
                tingLoop++;
            }
        }
        else {
            tingLoop = 0;
        }

        if (tingToggleButton) {
            ting.setPosition(tingScoringPosition);
        }
        else {
            ting.setPosition(tingHoldingPosition);
        }

//        if (gamepad.dpad_up) {
//            position += 0.0001;
//            ting.setPosition(position);
//        }
//        else if (gamepad.dpad_down) {
//            position -= 0.0001;
//            ting.setPosition(position);
//        }
//        ting.setPosition(position);

        telemetry.addData("Ting", "Servo (%.2f)", ting.getPosition());
        telemetry.addData("tingToggleButton", tingToggleButton);
    }

    public void setXiaoHoldingPosition(double position) {
        xiaoHoldingPosition = position;
    }

    public void setTingHoldingPosition(double position) {
        tingHoldingPosition = position;
    }

    public void setXiaoScoringPosition(double position) {
        xiaoScoringPosition = position;
    }

    public void setTingScoringPosition(double position) {
        tingScoringPosition = position;
    }
}
