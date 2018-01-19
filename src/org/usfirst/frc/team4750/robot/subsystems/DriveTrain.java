/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// Motors
	VictorSP frontLeftMotor;
	VictorSP frontRightMotor;
	VictorSP backLeftMotor;
	VictorSP backRightMotor;

	// Controller groups
	SpeedControllerGroup leftMotors;
	SpeedControllerGroup rightMotors;

	// Robot drive
	DifferentialDrive robotDrive;

	public DriveTrain(int frontLeftMotorPort, int frontRightMotorPort, int backLeftMotorPort, int backRightMotorPort) {

		// Initialize motors
		frontLeftMotor = new VictorSP(frontLeftMotorPort);
		frontRightMotor = new VictorSP(frontRightMotorPort);
		backLeftMotor = new VictorSP(backLeftMotorPort);
		backRightMotor = new VictorSP(backRightMotorPort);

		// Initialize controller groups
		leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
		rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);

		// initialize robot drive
		robotDrive = new DifferentialDrive(leftMotors, rightMotors);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		//setDefaultCommand(new TankDrive());
	}

	// Joystick (teleop) drive
	public void controllerDrive(Joystick l, Joystick r) {
		// Set motor speeds to the joystick values
		robotDrive.tankDrive(-l.getY(), -r.getY());
	}
	
	// Gyro drive
	public void setDriveMotors(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	// Autonomous drive
	public void autonDrive(double moveSpeed, double turnSpeed) {
		// Set motor speeds
		robotDrive.arcadeDrive(moveSpeed, turnSpeed);
	}
}
