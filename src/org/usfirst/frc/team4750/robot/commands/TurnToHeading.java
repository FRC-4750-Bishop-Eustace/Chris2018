/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4750.robot.OI;
import org.usfirst.frc.team4750.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class TurnToHeading extends Command {
	
	// Variables
	float offset;
	float targetHeading;
	float startHeading;
	float lastHeading;
	float speed;
	
	public TurnToHeading(float offset) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gyro);
		this.offset = offset;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		requires(Robot.gyro);
		// Reset the gyro
		Robot.gyro.reset();
		
		startHeading = Robot.gyro.getHeading();
		targetHeading = startHeading + offset;
		
		if(targetHeading < 0) {
			targetHeading += 360;
		}else if(targetHeading > 360) {
			targetHeading -= 360;
		}
		
		SmartDashboard.putNumber("Offset", offset);
		SmartDashboard.putNumber("Start Heading", startHeading);
		SmartDashboard.putNumber("Target Heading", targetHeading);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		lastHeading = Robot.gyro.getHeading();
		
		if(Math.abs(targetHeading - lastHeading) < 180) {
			offset = targetHeading - lastHeading;
		} else {
			if(targetHeading - lastHeading > 0) {
				offset = targetHeading - lastHeading - 360;
			} else if(targetHeading - lastHeading < 0) {
				offset = targetHeading - lastHeading + 360;
			}else {
				offset = 0;
			}
		}
		
		speed = offset / 180;
		
		if(Math.abs(speed) < .3) {
			if(speed < 0) {
				speed = -0.3f;
			}
			else {
				speed = 0.3f;	
			}
		}
		
		Robot.driveTrain.setDriveMotors(speed, -speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(offset) < 2.0;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.setDriveMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
