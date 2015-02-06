/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.physics.Vector;
import kylevedder.com.github.teams.Team;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Input;

/**
 * Tank Entity
 *
 * @author Kyle
 */
public class AITankEntity extends TankEntity
{

    private AIDriveState driveState = null;
    private AIDriveState prevDriveState = null;
    private Team enemyTeam = null;
    private float pastTurretUpdateDelta = 0f;
    private final float SHOOT_ANGLE_OFF_THRESHOLD = 0.5f;
    private final float DRIVE_TO_DIST = BaseGround.GROUND_SIZE * 4f;
    private final float NO_DRIVE_ANGLE_OFF_THRESHOLD = 45f;

    public AITankEntity(float x, float y, float angle, ObjectRegister register, Team enemyTeam)
    {
        super(x, y, angle, register);
        this.enemyTeam = enemyTeam;
        pastTurretUpdateDelta = 0f;
        driveState = AIDriveState.DRIVE_TO_TARGET;
        prevDriveState = AIDriveState.valueOf(driveState.toString());
    }

    @Override
    public void update(Input input, int delta)
    {
        TankEntity target = this.getClosestEnemy();
        if (target != null)
        {
            if (!this.destroyed)
            {
                float prevAngle = this.hitBox.getAngle();

                this.updateDrive(target, delta);
                Object[] objects = register.updateCollision(this.hitBox, this.vector, MainApp.NUM_COLLISION_UPDATES, delta);
                this.vector = (Vector) objects[1];
                this.hitBox = (CenteredRectangle) objects[0];

                this.updateTurret(target, delta);
                this.updateAnimation(Utils.wrapAngleDelta(this.hitBox.getAngle() - prevAngle), delta);
                this.updateShoot(target);

            }
        }
        super.update(input, delta);

    }

    float drivenDistance = 0f;

    /**
     * Updates the driving of the enemy
     *
     * @param target
     */
    private void updateDrive(TankEntity target, int delta)
    {
        if (!this.destroyed)
        {            
            boolean driveStateSwitched = (driveState != prevDriveState);
            //System.out.println(driveState.toString() + " " + driveStateSwitched);
            prevDriveState = AIDriveState.valueOf(driveState.toString());
            System.out.println("Case when strt: " + driveState);
            switch (driveState)
            {
                case DRIVE_TO_TARGET:

                    //
                    //Angle Values
                    //
                    //calculates angle to closest target
                    float desiredAngle = Utils.calculateAngle(this.hitBox, target.getHitBox());
                    //max rate at which the tank may turn
                    float turnRate = this.getTurnRate() / 1000 * delta;
                    //Angle amount to add to vector
                    float deltaAngle = Utils.wrapAngleDelta(desiredAngle - this.vector.getAngle());
                    //calc maximum allowable angle turn abount
                    deltaAngle = Utils.clampFloat(deltaAngle, -turnRate, turnRate);

                    //
                    //Speed/Distance Values
                    //
                    //Speed which to drive
                    float driveRate = this.getDriveSpeed() / 1000 * delta;
                    //the distance to the closest target
                    float distToTarget = Utils.getDistanceBetween(this.hitBox, target.getHitBox());
                    //sets the speed of the vector
                    float tankSpeed = 0;

                    //if at acceptable angle to drive and not too close to target
                    if (Math.abs(deltaAngle) <= NO_DRIVE_ANGLE_OFF_THRESHOLD && Math.abs(distToTarget) >= this.DRIVE_TO_DIST)
                    {
                        tankSpeed = driveRate;
                    }

                    //may be zero or a value depending on above set speed
                    this.vector.setSpeed(tankSpeed);
                    //regardless of if driving, turn toward the target
                    this.vector.addAngle(deltaAngle);

                    //check to see if vector will collide by moving to vector spot
                    CenteredRectangle tenitiveHitbox = new CenteredRectangle(hitBox);
                    tenitiveHitbox.updateDelta(this.vector.getXComp(), this.vector.getYComp(), this.vector.getAngle() - tenitiveHitbox.getAngle());
                    //if collide, go to collide mode
                    if (register.checkCollision(tenitiveHitbox, this.hitBox))
                    {
                        this.driveState = AIDriveState.BACK_AWAY_FROM_COLLISION;
                    }
                    break;
                case BACK_AWAY_FROM_COLLISION:                    
                    if (driveStateSwitched)
                    {
                        drivenDistance = 0f;
                    }
                    //Speed which to drive
                    driveRate = this.getDriveSpeed() / 1000 * delta;
                    //System.out.println(drivenDistance);
                    if(drivenDistance < 99f)
                    {
                        //clamp the vector to be drive speed or final distance to travel
                        float toTravel = Utils.clampFloat(100f-drivenDistance, -driveRate, 0);                        
                        this.vector.setSpeed(toTravel);
                        drivenDistance += toTravel;
                        break;
                    }
                    else
                    {
                        //System.out.println("Setting to drive to target");
                        this.driveState = AIDriveState.DRIVE_TO_TARGET;                        
                    }
                    
                    
                    break;
                case CIRCLE_TARGET:
                    break;
            }
            System.out.println("Case when done: " + driveState);
        }
        else
        {
            this.vector.setSpeed(0);
        }
    }
        

    /**
     * Updates the turret to aim and shoot
     */
    private void updateTurret(TankEntity target, int delta)
    {
        if (target != null)
        {
            float desiredTurretAngle = Utils.calculateTurretAngle(this.hitBox, target.getHitBox());
            this.addTurretAngle(desiredTurretAngle - this.turretAngle, delta);
            //intentionally done after turret has been moved.
            //this way, if pastTurretUpdateDelta != 0, we will know we have not reached our desired shoot angle
            this.pastTurretUpdateDelta = Utils.wrapAngleDelta(desiredTurretAngle - this.turretAngle);
        }
        else
        {
            this.pastTurretUpdateDelta = -9999f;
        }

    }

    /**
     * Updates the firing
     */
    private void updateShoot(TankEntity target)
    {
        if (Math.abs(this.pastTurretUpdateDelta) <= this.SHOOT_ANGLE_OFF_THRESHOLD && this.register.canSee(this, target))
        {
            this.fire();
        }
    }

    /**
     * Finds the closes enemy on the other team.
     *
     * @return
     */
    private TankEntity getClosestEnemy()
    {
        TankEntity closest = null;
        float distance = -1f;
        for (TankEntity e : enemyTeam.getEntityList())
        {
            float tempDist = Utils.getDistanceBetween(this.hitBox, e.getHitBox());
            if ((distance < 0f || tempDist < distance) && !e.isDestroyed())
            {
                closest = e;
                distance = tempDist;
            }
        }
        return closest;
    }

}

enum AIDriveState
{

    DRIVE_TO_TARGET, BACK_AWAY_FROM_COLLISION, CIRCLE_TARGET
}
