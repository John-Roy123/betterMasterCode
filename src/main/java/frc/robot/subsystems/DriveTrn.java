/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class DriveTrn extends SubsystemBase implements Runnable{
  RobotContainer rc = new RobotContainer();
  /**
   * Creates a new DriveTrn.
   */
  public static WPI_TalonFX m_talon1;
  public static WPI_TalonFX m_talon2;
  public static WPI_TalonFX m_talon3;
  public static WPI_TalonFX m_talon4;
  public static WPI_TalonFX m_talon5;
  public static WPI_TalonFX m_talon6;
  NeutralMode brake = NeutralMode.Brake;
  TalonFXInvertType kInvertType = TalonFXInvertType.CounterClockwise;
  
  //SpeedControllerGroup left = new SpeedControllerGroup(m_talon1, m_talon2, m_talon5);
  //SpeedControllerGroup right = new SpeedControllerGroup(m_talon3, m_talon4, m_talon6);

  public DriveTrn() {

  m_talon1 = new WPI_TalonFX(1);
  m_talon2 = new WPI_TalonFX(2); 
  m_talon3 = new WPI_TalonFX(3);
  m_talon4 = new WPI_TalonFX(4);
  m_talon5 = new WPI_TalonFX(5);
  m_talon6 = new WPI_TalonFX(6);

m_talon1.setInverted(false);
m_talon2.setInverted(false);
m_talon5.setInverted(false);
m_talon3.setInverted(true);
m_talon4.setInverted(true);
m_talon6.setInverted(true);  

m_talon1.setNeutralMode(brake);
m_talon2.setNeutralMode(brake);
m_talon3.setNeutralMode(brake);
m_talon4.setNeutralMode(brake);
m_talon5.setNeutralMode(brake);  
m_talon6.setNeutralMode(brake);


m_talon1.setInverted(kInvertType);
m_talon2.setInverted(kInvertType);
m_talon5.setInverted(kInvertType);
Thread DT = new Thread(this);
DT.start();

  }
  public void run(){
    while(true){
rc.drive(rc.driveController().getX(Hand.kRight), rc.driveController().getY(Hand.kLeft));
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    
  }
}
