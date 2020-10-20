/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Climb extends CommandBase {

  RobotContainer rc = new RobotContainer();
  private CANSparkMax m_climbleft;
  private CANSparkMax m_climbright;
  private static final int winchCANID_1 = 13;  //Neos
  private static final int winchCANID_2 = 12;

  public Climb() {
    // Use addRequirements() here to declare subsystem dependencies.
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climbleft = new CANSparkMax(winchCANID_1, MotorType.kBrushless);
    m_climbright = new CANSparkMax(winchCANID_2, MotorType.kBrushless);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (rc.operateController().getPOV() == 0) {
      m_climbleft.set(0.65);
      m_climbright.set(-0.65); 
    } else if(rc.operateController().getPOV() == 180){
      m_climbleft.set(-0.5); 
      m_climbright.set(0.5); 
    } else {
      m_climbleft.set(0);
      m_climbright.set(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
