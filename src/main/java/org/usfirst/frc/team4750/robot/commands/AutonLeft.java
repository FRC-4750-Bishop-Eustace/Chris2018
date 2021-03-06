/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4750.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class AutonLeft extends Command {

	Timer timer;
	boolean isFinished = false;

	public AutonLeft() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		requires(Robot.driveTrain);

		// Reset/start the timer
		timer.reset();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (timer.get() <= .5) { // If in the first .5 seconds
			Robot.driveTrain.autonDrive(0, -1); // Turn left
		} else if (timer.get() > .5 && timer.get() <= 1) { // If between .5 and 1 second
			Robot.driveTrain.autonDrive(-1, 0); // Drive forward
		} else { // After
			isFinished = true; // Finish
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
