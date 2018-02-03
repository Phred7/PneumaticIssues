/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2906.robot;

import org.usfirst.frc.team2906.robot.subsystems.DriveWC;
import org.usfirst.frc.team2906.robot.subsystems.IXBar;
import org.usfirst.frc.team2906.robot.subsystems.Intake;
import org.usfirst.frc.team2906.robot.subsystems.Lift;
import org.usfirst.frc.team2906.robot.subsystems.Limelight;
import org.usfirst.frc.team2906.robot.subsystems.NavX;
import org.usfirst.frc.team2906.robot.subsystems.Pneumatics;
import org.usfirst.frc.team2906.robot.commands.*;

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
	public static DriveWC driveWC;
	public static Intake intake;
	public static IXBar ixBar;
	public static Lift lift;
	public static Limelight limelight;
	public static Pneumatics pneumatics;
	public static NavX navX;
	public static OI oi;

	Command AutoNone;
	Command AutoPILine;
	Command AutoPIILine;
	Command AutoPIIILine;
	Command m_autonomousCommand;
	
	final String None = "No Auto";
	final String PILine = "PI - Line";
	final String PIILine = "PII - Line";
	final String PIIILine = "PIII - Line";
	
	String[] autoList = {None, PILine, PIILine, PIIILine};
	
	SendableChooser<Command> chooser = new SendableChooser<>();

	public void robotInit() {
		RobotMap.init();
		
		driveWC = new DriveWC();
		intake = new Intake();
		ixBar = new IXBar();
		lift = new Lift();
		limelight = new Limelight();
		pneumatics = new Pneumatics();
		navX = new NavX();
		oi = new OI();
		
		chooser.addDefault("Default: No Auto", new AutoNone());
		chooser.addObject("My Auto", new AutoPILine());
		chooser.addObject("My Auto", new AutoPIILine());
		chooser.addObject("My Auto", new AutoPIIILine());
		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public void disabledInit() {

	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		m_autonomousCommand = (Command) chooser.getSelected();
	    System.out.println("Auto selected: " + chooser.getSelected());

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("navx angle", RobotMap.navX.getAngle());
		SmartDashboard.putNumber("navx pitch", RobotMap.navX.getPitch());
		SmartDashboard.putNumber("navx roll", RobotMap.navX.getRoll());
		SmartDashboard.putNumber("navx yaw", RobotMap.navX.getYaw());
	}

	
	public void teleopInit() {

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("navx angle", RobotMap.navX.getAngle());
		SmartDashboard.putNumber("navx pitch", RobotMap.navX.getPitch());
		SmartDashboard.putNumber("navx roll", RobotMap.navX.getRoll());
		SmartDashboard.putNumber("navx yaw", RobotMap.navX.getYaw());
		SmartDashboard.putBoolean("Collission", Robot.navX.collisionDetection());
		SmartDashboard.putNumber("x velocity", Robot.navX.getXVelocity());
		SmartDashboard.putNumber("y velocity", Robot.navX.getYVelocity());
		SmartDashboard.putNumber("z velocity", Robot.navX.getZVelocity());
		//SmartDashboard.putNumber("Gyro angle", RobotMap.gAngle);
	}

	public void testPeriodic() {
	}
}
