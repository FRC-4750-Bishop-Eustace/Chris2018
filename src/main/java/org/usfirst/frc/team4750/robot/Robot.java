/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot;

import org.usfirst.frc.team4750.robot.commands.AutonLeft;
import org.usfirst.frc.team4750.robot.commands.AutonRight;
import org.usfirst.frc.team4750.robot.commands.AutonStraight;
import org.usfirst.frc.team4750.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	// Instantiate the drive train subsystem to the motor ports
	public static final DriveTrain driveTrain = new DriveTrain(RobotMap.FRONT_LEFT_MOTOR_PORT,
			RobotMap.FRONT_RIGHT_MOTOR_PORT, RobotMap.BACK_LEFT_MOTOR_PORT, RobotMap.BACK_RIGHT_MOTOR_PORT);

	public static OI oi;

	// Sendable chooser to choose starting position
	SendableChooser<String> chooser = new SendableChooser<>();

	// String to hold starting position
	String startPos;

	// Autonomous command
	Command autonomousCommand;

	// String to hold game specific data
	String gameData;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		// Add start position options
		chooser.addDefault("Middle", "M");
		chooser.addObject("Left", "L");
		chooser.addObject("Right", "R");

		// Output chooser to smart dashboard
		SmartDashboard.putData("Start Position", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		// Get start position
		startPos = chooser.getSelected();
		SmartDashboard.putString("Start Position", startPos);

		// Get plate formation
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("Plate Order", gameData);

		// Check if the switch plate owned is left or right
		if (gameData.charAt(0) == 'L') { // If left
			autonomousCommand = new AutonLeft(); // Run left auton
		} else if (gameData.charAt(0) == 'R') { // If right
			autonomousCommand = new AutonRight(); // Run right auton
		}

		if (startPos == "L" && gameData.charAt(0) == 'L') {
			autonomousCommand = new AutonStraight();
		} else if (startPos == "L" && gameData.charAt(0) == 'R') {
			autonomousCommand = new AutonRight();
		} else if (startPos == "M" && gameData.charAt(0) == 'L') {
			autonomousCommand = new AutonLeft();
		} else if (startPos == "M" && gameData.charAt(0) == 'R') {
			autonomousCommand = new AutonRight();
		} else if (startPos == "L" && gameData.charAt(0) == 'L') {
			autonomousCommand = new AutonLeft();
		} else if (startPos == "L" && gameData.charAt(0) == 'R') {
			autonomousCommand = new AutonStraight();
		} else {
			autonomousCommand = new AutonStraight();
		}

		switch (startPos) {
		case "L":
			switch (gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new AutonStraight();
				break;
			case 'R':
				autonomousCommand = new AutonRight();
				break;
			}
			break;
		case "M":
			switch (gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new AutonLeft();
				break;
			case 'R':
				autonomousCommand = new AutonRight();
				break;
			}
			break;
		case "R":
			switch (gameData.charAt(0)) {
			case 'L':
				autonomousCommand = new AutonLeft();
				break;
			case 'R':
				autonomousCommand = new AutonStraight();
				break;
			}
			break;
		}

		// Output the current command to the smart dashboard
		SmartDashboard.putData("AutonomousCommand", autonomousCommand);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
