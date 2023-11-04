/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

//@TeleOp(name="Anthony Trenh Mecanum Test", group="Linear OpMode")
//@Disabled
public class Sample extends LinearOpMode {

    // Declare OpMode members.
    // (creates motors that will be on the robot)
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private Servo servo = null;
    private DcMotor linearSlide = null;
    private DcMotor intake = null;

    boolean toggleButton = false;
    int loop;
    public int maxPosition = 3000;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // physical port on robot has set names, using "deviceName" recognizes what port the motor will be assigned to
        // motors for wheels
//        leftFrontDrive  = hardwareMap.get(DcMotor.class, "lf_drive");
//        leftBackDrive = hardwareMap.get(DcMotor.class, "lb_drive");
//        rightFrontDrive  = hardwareMap.get(DcMotor.class, "rf_drive");
//        rightBackDrive = hardwareMap.get(DcMotor.class, "rb_drive");
        // servo for grabber
//        servo = hardwareMap.get(Servo.class, "servo");
        // motor for linear slide, sets up encoders
//        linearSlide = hardwareMap.get(DcMotor.class, "l_slide");
//        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
//        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
//        linearSlide.setTargetPosition(0);
//        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake = hardwareMap.get(DcMotor.class, "intake");

        // sets directions of motors and servo
//        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
//        servo.setDirection(Servo.Direction.FORWARD);
//        linearSlide.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // setup variable for wheel motor powers
//            double leftFrontPower;
//            double leftBackPower;
//            double rightFrontPower;
//            double rightBackPower;

            double intakePower;

            // y and x are for moving, rotate is for rotating
//            double y = -gamepad1.left_stick_y;
//            double x = gamepad1.left_stick_x * 1.95;
//            double rotate = gamepad1.right_stick_x;

            // denominator cuts down values if sum exceeds 1
//            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rotate), 1);

            // sets powers to formula of joystick values
//            leftFrontPower = (y + x + rotate) / denominator;
//            leftBackPower = (y - x + rotate) / denominator;
//            rightFrontPower = (y - x - rotate) / denominator;
//            rightBackPower = (y + x - rotate) / denominator;

            // send power to the wheels
//            leftFrontDrive.setPower(leftFrontPower);
//            leftBackDrive.setPower(leftBackPower);
//            rightFrontDrive.setPower(rightFrontPower);
//            rightBackDrive.setPower(rightBackPower);

            intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
            intake.setPower(intakePower);

//            // toggle button for servo, but it activates the moment you hit button
//            if (gamepad1.x) {
//                if (loop == 0) {
//                    toggleButton = !toggleButton;
//                    loop++;
//                }
//            }
//            else {
//                loop = 0;
//            }
//
//            if (toggleButton) {
//                servo.setPosition(1);
//            }
//            else {
//                servo.setPosition(0.3);
//            }
//
//            // gets current position of motor
//            int position = linearSlide.getCurrentPosition();
//
//            // linear slide add 250 with dpad
//            if (gamepad1.dpad_up && position < maxPosition) {
//                position += 230;
//                if (position > maxPosition) {
//                    position = maxPosition;
//                }
//            }
//            else if (gamepad1.dpad_down && position > 0) {
//                position -= 230;
//                if (position < 0) {
//                    position = 0;
//                }
//            }
//
//            if (gamepad1.a || gamepad1.b || gamepad1.y) {
//                // figure out what pole position to go to
//                if (gamepad1.a) {
//                    position = 1000;
//                }
//                else if (gamepad1.b) {
//                    position = 1675;
//                }
//                else if (gamepad1.y) {
//                    position = 2350;
//                }
//            }
//
//            //
//            linearSlide.setTargetPosition(position);
//            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            // go to pole
//            if (linearSlide.getCurrentPosition() < position) {
//                linearSlide.setPower(1);
//            }
//            else if (linearSlide.getCurrentPosition() > position) {
//                linearSlide.setPower(-1);
//            }


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Front Motors", "Left Front (%.2f), Right Front (%.2f)", leftFrontPower, rightFrontPower);
//            telemetry.addData("Back Motors", "Left Back (%.2f), Right Back (%.2f)", leftBackPower, rightBackPower);
//            telemetry.addData("Servo", "Servo (%.2f)", servo.getPosition());
//            telemetry.addData("Encoder Position", position);
            telemetry.update();
        }
    }
}
