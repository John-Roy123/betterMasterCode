/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Collector extends SubsystemBase {

  RobotContainer rc = new RobotContainer();

  public WPI_VictorSPX m_collector;
  private static final int collectCANID = 9;

  public DoubleSolenoid a_collector;

  public boolean collecting = true;
  public boolean togglecollector = false;

  /**
   * Creates a new Collector.
   */
  public Collector() {
    m_collector= new WPI_VictorSPX(collectCANID);
    a_collector = new DoubleSolenoid(2, 3); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(rc.operateController().getYButtonPressed() || rc.driveController().getRawButton(6)) {
      a_collector.set(Value.kForward);
    } else if(rc.operateController().getAButtonPressed() || rc.driveController().getRawButton(5)){
      a_collector.set(Value.kReverse);
    } else {
      a_collector.set(Value.kOff);
    }
  }
}
