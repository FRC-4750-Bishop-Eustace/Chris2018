/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4750.robot.subsystems;

import org.usfirst.frc.team4750.robot.commands.TankDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Gyro extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// Gyro
	AHRS gyro;
	
	//Variabls
	float heading;

	public Gyro() {
		gyro = new AHRS(SerialPort.Port.kMXP);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		//setDefaultCommand(new TankDrive());
	}

	public float getHeading() {
		heading = gyro.getFusedHeading();
		return heading;
	}
	
	public void reset() {
		gyro.reset();
	}
}
