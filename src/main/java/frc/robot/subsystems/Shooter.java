/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter extends SubsystemBase implements Runnable{
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
  private CANPIDController m_pidController;
    final CANSparkMax m_shooterright;
    final CANSparkMax m_shooterleft;

    private CANEncoder shootEncoder1;
    private CANEncoder shootEncoder2;

    final int shooterCANID_1 = 11;
    final int shooterCANID_2 = 14;

    RobotContainer rc = new RobotContainer();
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

  m_shooterleft = new CANSparkMax(shooterCANID_1, MotorType.kBrushless);
  m_shooterright = new CANSparkMax(shooterCANID_2, MotorType.kBrushless);

  shootEncoder1 = m_shooterleft.getEncoder();
  shootEncoder2 = m_shooterright.getEncoder();


  m_shooterleft.setInverted(true);
  m_shooterright.follow(m_shooterleft, true);

  m_pidController = m_shooterleft.getPIDController();

  kP = .002; 
  kI = 0.0;
  kD = 0.002; 
  kIz = 0; 
  kFF = (10/5700) * 4000; 
  kMaxOutput = 1; 
  kMinOutput = 0;
  maxRPM = 4000;

  // set PID coefficients
  m_pidController.setP(kP);
  m_pidController.setI(kI);
  m_pidController.setD(kD);
  m_pidController.setIZone(kIz);
  m_pidController.setFF(kFF);
  m_pidController.setOutputRange(kMinOutput, kMaxOutput);

  // display PID coefficients on SmartDashboard
  SmartDashboard.putNumber("P Gain", kP);
  SmartDashboard.putNumber("I Gain", kI);
  SmartDashboard.putNumber("D Gain", kD);
  SmartDashboard.putNumber("I Zone", kIz);
  SmartDashboard.putNumber("Feed Forward", kFF);
  SmartDashboard.putNumber("Max Output", kMaxOutput);
  SmartDashboard.putNumber("Min Output", kMinOutput);

  Thread shoot = new Thread(this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

@Override
public void run() {
  while(true){
	// TODO Auto-generated method stub
	 // read PID coefficients from SmartDashboard
   double p = SmartDashboard.getNumber("P Gain", 0);
   double i = SmartDashboard.getNumber("I Gain", 0);
   double d = SmartDashboard.getNumber("D Gain", 0);
   double iz = SmartDashboard.getNumber("I Zone", 0);
   double ff = SmartDashboard.getNumber("Feed Forward", 0);
   double max = SmartDashboard.getNumber("Max Output", 0);
   double min = SmartDashboard.getNumber("Min Output", 0);
 
   // if PID coefficients on SmartDashboard have changed, write new values to controller
   if((p != kP)) { m_pidController.setP(p); kP = p; }
   if((i != kI)) { m_pidController.setI(i); kI = i; }
   if((d != kD)) { m_pidController.setD(d); kD = d; }
   if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
   if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
   if((max != kMaxOutput) || (min != kMinOutput)) { 
     m_pidController.setOutputRange(min, max); 
     kMinOutput = min; kMaxOutput = max; 
   }
     double setPoint = rc.operateController().getRawAxis(1)*maxRPM;
   if (rc.operateController().getRawAxis(2) > 0.7){
     m_pidController.setReference(5200 , ControlType.kVelocity);
   } else {
     m_pidController.setReference(0 , ControlType.kVelocity);
   }
     
     SmartDashboard.putNumber("SetPoint", setPoint);
     SmartDashboard.putNumber("ProcessVariable", shootEncoder1.getVelocity());
     SmartDashboard.putNumber("ProcessVariable", shootEncoder2.getVelocity());

     SmartDashboard.putNumber("distance", heightValue/tanValue); //distance from target*/
     SmartDashboard.putNumber("Pot", (((s_hood.get() * 163.429271856365)-2.603118)) + 23); //angle of hood
     SmartDashboard.putNumber("index encoder", m_indexer.getSensorCollection().getQuadraturePosition());
     SmartDashboard.putNumber("NeoEncoder1", shootEncoder1.getVelocity());
     SmartDashboard.putNumber("NeoEncoder2", shootEncoder2.getVelocity()); 
     SmartDashboard.putNumber("ultra1", s_ultra1.getRangeInches());
     SmartDashboard.putBoolean("BallIndex", s_ultra1Range);
     SmartDashboard.putBoolean("Index FULL", s_ultra2Range);
     SmartDashboard.putNumber("Gyro Angle", s_roboGyro.getAngle()); 
     SmartDashboard.putNumber("Ballcount", ballcount);
     SmartDashboard.putNumber("Index reset Timer", t_indexreset);
     SmartDashboard.putBoolean("CANSHOOT", inRANGE);
    }
}
}
