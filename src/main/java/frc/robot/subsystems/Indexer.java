/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Indexer extends SubsystemBase{

  RobotContainer rc = new RobotContainer();
  Collector C = new Collector();

  public WPI_TalonSRX m_indexer;
  public static final int indexerCANID = 10;

  public Ultrasonic s_ultra1;
  public Ultrasonic s_ultra2;

  public boolean s_ultra1Range = false;
  public boolean s_ultra2Range = false;

  public int t_indexreset;
  public int ballcount = 0;

  private boolean collecting = true;

  public static final int feederCANID = 8;
  public WPI_VictorSPX m_feeder;
  
  private int t_ultra1;

  /**
   * Creates a new Feeder.
   */
  public Indexer() {
    m_indexer = new WPI_TalonSRX(indexerCANID);

    s_ultra1 = new Ultrasonic(7, 6);
    s_ultra2 = new Ultrasonic(8, 9);

    s_ultra1.setAutomaticMode(true);
    s_ultra2.setAutomaticMode(true);

    t_ultra1 = 0;
  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(s_ultra1Range){
      if(t_indexreset == 1){
        ballcount++;
        t_indexreset++;
      }else{
        t_indexreset++;
      }
    }

    if(rc.operateController().getRawAxis(3) > 5){
      m_feeder.set(-1);
      m_indexer.set(.7); 
      ballcount = 0;
    }
    else if (rc.operateController().getBumper(Hand.kLeft)){
      m_indexer.set(-0.3);
      C.m_collector.set(-1);
      m_feeder.set(.7);
    }else if(rc.operateController().getBumper(Hand.kRight)){
      m_indexer.set(0.3);
      C.m_collector.set(1);
   }else if(s_ultra1.getRangeInches() < 5 && !(s_ultra2.getRangeInches() < 5 )){
    m_indexer.set(.4);
    collecting = true;
  }else { 
    if(t_ultra1 < 6){
      m_feeder.set(-.2);
    } else if(collecting && s_ultra2.getRangeInches() < 8){
      t_ultra1 = 0;
    } else {
      collecting = false;
      m_feeder.set(0);                                     
    }
    m_indexer.set(0);
  }
  }

}
