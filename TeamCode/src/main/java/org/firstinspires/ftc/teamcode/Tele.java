package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Tele extends OpMode {
    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    private DcMotor shooter;
    private CRServo leftFeeder, rightFeeder;

    @Override
    public void init() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "fl");
        leftBackDrive = hardwareMap.get(DcMotor.class, "bl");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "fr");
        rightBackDrive = hardwareMap.get(DcMotor.class, "br");
        shooter = hardwareMap.get(DcMotor.class, "launcher");
        rightFeeder = hardwareMap.get(CRServo.class, "right_feeder");
        leftFeeder = hardwareMap.get(CRServo.class, "left_feeder");

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double axial   = -gamepad1.left_stick_y;
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        double max = Math.max(Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower)));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        shooter.setPower(gamepad1.right_trigger > 0.1 ? 1.0 : 0.0);
        leftFeeder.setPower(- 1 * gamepad1.left_trigger);
        rightFeeder.setPower(gamepad1.left_trigger);

        telemetry.addData("LF", leftFrontPower);
        telemetry.addData("RF", rightFrontPower);
        telemetry.addData("LB", leftBackPower);
        telemetry.addData("RB", rightBackPower);
        telemetry.update();
    }
}
