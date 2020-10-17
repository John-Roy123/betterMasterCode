/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.DriveTrn;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class auto extends CommandBase {
  private double gRCombin = circumference/gR;
  DriveTrn DT = new DriveTrn();
  /**
   * Creates a new auto.
   */
  public auto() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(new DriveTrn(), new Shooter(), new Indexer(), new Collector());
    

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double s_mleft = Math.abs(DT.m_talon1.getSelectedSensorPosition() / 2048);
    double s_mright = Math.abs(DT.m_talon4.getSelectedSensorPosition()/2048);
    double lwheelSpin = gRCombin * s_mleft; 
    double rwheelSpin = gRCombin * s_mright; //how many inches per motor spin 
    int state = 0;
    x++;
   if(lwheelSpin < 60 && x < 135){
      left.set(-0.3);
      right.set(-0.3);
    } else if(x > 0 && x < 255){
      if(x > 100 && x < 113){
        m_hood.set(-.7);
      }else{
        m_hood.set(0);
      }
      m_shooterleft.set(1); 
      m_shooterright.set(-1); 
      if(x > 135 && x < 255){
        m_feeder.set(-.8);
        m_indexer.set(.8); 
        //m_talon1.setSelectedSensorPosition(0);
        //m_talon4.setSelectedSensorPosition(0);
      } else {
        m_feeder.set(0); 
        m_indexer.set(0); 
      }
     } else if (x > 255) {
      if(lwheelSpin < 65.25){
        left.set(0.4);
      } 
      if(rwheelSpin < 65.25){
        right.set(-0.4);
      } 
      m_feeder.set(0); 
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
