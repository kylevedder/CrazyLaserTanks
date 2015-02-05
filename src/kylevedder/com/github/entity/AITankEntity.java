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
 *
 * @author Kyle
 */
public class AITankEntity extends TankEntity
{

    private AIDriveState driveState = null;
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

    /**
     * Updates the driving of the enemy
     *
     * @param target
     */
    private void updateDrive(TankEntity target, int delta)
    {
        if (!this.destroyed)
        {
            //sets the speed of the vector
            float tankSpeed = 0;

            float desiredAngle = Utils.calculateAngle(this.hitBox, target.getHitBox());
            float distToTarget = Utils.getDistanceBetween(this.hitBox, target.getHitBox());

            float deltaAngle = Utils.wrapAngleDelta(desiredAngle - this.vector.getAngle());

            float driveRate = (delta > 0) ? this.getDriveSpeed() / (1000 / delta) : 0;
            switch (this.driveState)
            {
                case DRIVE_TO_TARGET:

                    //if at acceptable angle to drive
                    if (Math.abs(deltaAngle) <= NO_DRIVE_ANGLE_OFF_THRESHOLD)
                    {

                        if (Math.abs(distToTarget) >= this.DRIVE_TO_DIST)//drive to target
                        {
                            tankSpeed = driveRate;
                        }

                    }

                    //regardless of if at acceptable angle to drive, turn toward the target
                    this.setVector(tankSpeed, desiredAngle, delta);

                    //check to see if vector will collide
                    CenteredRectangle tenitiveHitbox = new CenteredRectangle(hitBox);
                    //update tenitiveHitbox to new location
                    tenitiveHitbox.updateDelta(this.vector.getXComp(), this.vector.getYComp(), this.vector.getAngle() - tenitiveHitbox.getAngle());
                    //if collide, go to collide mode
                    if (register.checkCollision(tenitiveHitbox, this.hitBox))
                    {
                        this.driveState = AIDriveState.BACK_AWAY_FROM_COLLISION;
                    }
                    break;                    
                case BACK_AWAY_FROM_COLLISION:
                    
                    break;
                case CIRCLE_TARGET:
                    break;
            }

        }
        else
        {
            this.vector.setSpeed(0);
        }
    }

    /**
     * Sets the vector and adds the given angle
     *
     * @param speed
     * @param angle
     * @param delta
     */
    private void setVector(float speed, float angle, int delta)
    {
        float turnRate = (delta > 0) ? this.getTurnRate() / (1000 / delta) : 0;
        float deltaAngle = Utils.wrapAngleDelta(angle - this.vector.getAngle());
        deltaAngle = Utils.clampFloat(deltaAngle, -turnRate, turnRate);

        this.vector.setSpeed(speed);
        this.vector.addAngle(deltaAngle);
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
