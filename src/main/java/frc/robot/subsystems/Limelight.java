/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;



public class Limelight extends SubsystemBase implements Runnable{
  RobotContainer rc = new RobotContainer();
  Robot r = new Robot();
  Shooter S = new Shooter();

  final double h2 = 90.875; //height of target
  final double h1 = 14.75; //height of limeligt

  public boolean inRANGE;
  
  double hoodAngle;
  double calculateAngle;

  double limeTarget;
  /**
   * Creates a new Limelight.
   */
  public Limelight() {
    Thread LL = new Thread(this);
  }
  public void run(){
    while(true){
      final double a2 = Math.toRadians(NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    final double a1 = Math.toRadians(13); //angle of limelight
  
    final double heightValue = (h2 - h1);
    
    final double angleboth = a1 + a2;
    final double tanValue = Math.tan(angleboth);
  
    double distanceFromTarget = (heightValue/tanValue);
  
  


    if (rc.driveController().getRawAxis(3) > 0.65){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    } else if (rc.driveController().getRawAxis(3) < 0.6) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    }
    limelightTracking();
    double tx = (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
  double hordis = Math.abs(tx);
  double steer = .055; 
limeTarget = tx * steer; 
if(rc.driveController().getRawAxis(3) > 0.7 && (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1)) {
  if (hordis > 1 || hordis < -1) {
  if(tx < 5 && tx > -5){
        rc.drive((tx * .1 ), 0);
      }else{
        rc.drive(limeTarget, 0);
      }    
    }
  }else{
   
      rc.drive((rc.driveController().getX(Hand.kRight)), -(rc.driveController().getY(Hand.kLeft)));

  }

  SmartDashboard.putNumber("distance", heightValue/tanValue); //distance from target*/

    }
  }

  @Override
  public void periodic() {
  }

  public void limelightTracking(){
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
  }

public boolean inRANGE(double distanceFromTarget){

  if(distanceFromTarget > 160 && distanceFromTarget < 200){
    return inRANGE = true;
  } 
  
  else {
    return inRANGE = false;

  }
}
}
