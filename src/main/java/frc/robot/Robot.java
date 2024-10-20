/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auto;
import frc.robot.commands.Climb;
import frc.robot.subsystems.DriveTrn;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private AnalogPotentiometer s_hood;


  private RobotContainer rc;
  DriveTrn DT;
  Limelight LL;
  Shooter S;
  Indexer I;
  public Compressor comp;

  File XControls;
  File YControls;
  FileWriter xRecord;
  FileWriter yRecord;
  String xString;
  String yString;
  
  private boolean inRANGE;
  public Gyro gyro;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    gyro = new Gyro(){

    @Override
    public void close() throws Exception {
      // TODO Auto-generated method stub
      
    }
  
    @Override
    public void reset() {
      // TODO Auto-generated method stub
      
    }
  
    @Override
    public double getRate() {
      // TODO Auto-generated method stub
      return 0;
    }
  
    @Override
    public double getAngle() {
      // TODO Auto-generated method stub
      return 0;
    }
  
    @Override
    public void calibrate() {
      // TODO Auto-generated method stub
      
    }
  };
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }


  @Override
  public void autonomousInit() {
    Command auto = rc.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (auto != null) {
      auto.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    rc = new RobotContainer();
    DT = new DriveTrn();
    LL = new Limelight();
    S = new Shooter();
    I = new Indexer();
  }

  @Override
  public void teleopPeriodic() {
    
   

    if (rc.operateController().getPOV() == 0){new Climb();}
    else if(rc.operateController().getPOV() == 180){new Climb();}

    double setPoint = rc.operateController().getRawAxis(1)*S.maxRPM;
    SmartDashboard.putNumber("SetPoint", setPoint);

    SmartDashboard.putNumber("ProcessVariable", S.shootEncoder1.getVelocity());
    SmartDashboard.putNumber("ProcessVariable", S.shootEncoder2.getVelocity());

    
    SmartDashboard.putNumber("Pot", (((s_hood.get() * 163.429271856365)-2.603118)) + 23); //angle of hood
    SmartDashboard.putNumber("index encoder", I.m_indexer.getSensorCollection().getQuadraturePosition());

    SmartDashboard.putNumber("NeoEncoder1", S.shootEncoder1.getVelocity());
    SmartDashboard.putNumber("NeoEncoder2", S.shootEncoder2.getVelocity()); 

    SmartDashboard.putNumber("ultra1", I.s_ultra1.getRangeInches());

    SmartDashboard.putBoolean("BallIndex", I.s_ultra1Range);
    SmartDashboard.putBoolean("Index FULL", I.s_ultra2Range);
    SmartDashboard.putNumber("Ballcount", I.ballcount);
    SmartDashboard.putNumber("Index reset Timer", I.t_indexreset);

    SmartDashboard.putNumber("Gyro Angle", gyro.getAngle()); 

    SmartDashboard.putBoolean("CANSHOOT", LL.inRANGE);
  

 
}
@Override
public void testInit() {
  //comp.start();

  XControls = new File("/home/lvuser/Xpos.txt");
  YControls = new File("/home/lvuser/Ypos.txt");

    try {
      xRecord = new FileWriter(XControls);
      yRecord = new FileWriter(YControls);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


 }
 
@Override
public void testPeriodic() {

  Double xJoyStick = rc.driveController().getX(Hand.kRight);
  Double yJoyStick = rc.driveController().getY(Hand.kLeft);


  xString = String.valueOf(xJoyStick) + "\n";
  yString = String.valueOf(yJoyStick) + "\n";

    try {
      xRecord.append(xString);
      xRecord.flush();
      yRecord.append(yString);
      yRecord.flush();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }






  if(rc.operateController().getRawButton(7)) {
    comp.start();
  }
   if(rc.operateController().getRawButton(8)) {
     comp.stop();
   }
}




}
