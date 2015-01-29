/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
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

    private Team enemyTeam = null;
    private float pastTurretUpdateDelta = 0f;
    private final float SHOOT_ANGLE_OFF_THRESHOLD = 0.5f;
    private final float DRIVE_TO_DIST = BaseGround.GROUND_SIZE * 6f;
    private final float DRIVE_ANGLE_OFF_THRESHOLD = 0.5f;

    public AITankEntity(float x, float y, float angle, Team enemyTeam)
    {
        super(x, y, angle);
        this.enemyTeam = enemyTeam;
        pastTurretUpdateDelta = 0f;
    }

    @Override
    public void update(Input input, int delta)
    {
        TankEntity target = this.getClosestEnemy();
        if (target != null)
        {
            float prevAngle = this.hitBox.getAngle();
            if (!this.destroyed)this.updateDrive(target, delta);
            Object[] objects = MainApp.gameEngine.register.updateCollision(this.hitBox, this.vector, MainApp.NUM_COLLISION_UPDATES, delta);
            this.vector = (Vector) objects[1];
            this.hitBox = (CenteredRectangle) objects[0];

            if (!this.destroyed)
            {
                this.updateTurret(target, delta);
                this.updateAnimation(Utils.wrapAngleDelta(this.hitBox.getAngle() - prevAngle), delta);
                this.updateShoot();
            }
            super.update(input, delta);
        }
    }
    
    private int driveDirecton = 1;
    /**
     * Updates the driving of the enemy
     *
     * @param target
     */
    private void updateDrive(TankEntity target, int delta)
    {
        if(MainApp.gameEngine.register.checkCollision(this))
        {
            driveDirecton *= -1;
        }
        //adds to both the angle of the vector and the rotation of the vector
        float tankAngleAppend = 0;
        //sets the speed of the vector
        float tankSpeed = 0;

        float desiredAngle = Utils.calculateAngle(this.hitBox, target.getHitBox());
        float distToTarget = Utils.getDistanceBetween(this.hitBox, target.getHitBox());

        //need to drive there
        if (Math.abs(distToTarget) <= this.DRIVE_TO_DIST)//circle target
        {
            desiredAngle += 90f;

        }
        //else: drive at it

        tankSpeed = this.getDriveSpeed() / (1000 / delta);
        //angle not OK
        if (Math.abs(desiredAngle) > this.SHOOT_ANGLE_OFF_THRESHOLD)
        {
            //angle too big
            if (this.hitBox.getAngle() > desiredAngle)
            {
                tankAngleAppend -= this.getTurnRate() / (1000 / delta);
            }
            //angle  too small
            else if (this.hitBox.getAngle() < desiredAngle)
            {
                tankAngleAppend += this.getTurnRate() / (1000 / delta);
            }
        }
        tankSpeed *= driveDirecton;

        //sets the vector's speed
        this.vector.setSpeed(tankSpeed);
        //adds to the vector's angle
        this.vector.addAngle(tankAngleAppend);

    }

    /**
     * Updates the turret to aim and shoot
     */
    private void updateTurret(TankEntity target, int delta)
    {
        if (target != null)
        {
            float desiredTurretAngle = Utils.calculateAngle(this.hitBox, target.getHitBox());
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
    private void updateShoot()
    {
        if (Math.abs(this.pastTurretUpdateDelta) <= this.SHOOT_ANGLE_OFF_THRESHOLD)
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
