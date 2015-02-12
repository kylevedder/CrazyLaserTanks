/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.pathfinding.PathFindingMap;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.physics.Vector;
import kylevedder.com.github.teams.Team;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

/**
 *
 *
 * @author Kyle
 */
public class AITankEntity extends TankEntity
{

    private AIDriveState driveState = null;
    private AIDriveState prevDriveState = null;
    private Team enemyTeam = null;
    private GroundHolder gh;
    private PathFindingMap pfm;
    private float pastTurretUpdateDelta = 0f;
    private final float SHOOT_ANGLE_OFF_THRESHOLD = 0.5f;
    private final float DRIVE_TO_DIST = BaseGround.GROUND_SIZE * 4f;
    private final float NO_DRIVE_ANGLE_OFF_THRESHOLD = 10f;
    private AStarPathFinder pathFinder;

    private int MAX_PATH_LENGTH = 4000;

    public AITankEntity(float x, float y, float angle, ObjectRegister register, Team yourTeam, Team enemyTeam, GroundHolder gh)
    {
        super(x, y, angle, register);
        this.enemyTeam = enemyTeam;
        this.gh = gh;
        this.pfm = gh.getPathFindingMap();
        pathFinder = new AStarPathFinder(pfm, MAX_PATH_LENGTH, false);
        pastTurretUpdateDelta = 0f;
        this.driveState = (AIDriveState.DRIVE_TO_TARGET);
        prevDriveState = AIDriveState.valueOf(driveState.toString());
    }

    Path path = null;
    Point driveToPoint = null;

    @Override
    public void update(Input input, int delta)
    {

        TankEntity target = this.getClosestEnemy();
        path = pathFinder.findPath(null, gh.entityXtoGroundX(this.hitBox.getCenterX()), gh.entityYtoGroundY(this.hitBox.getCenterY()),
                gh.entityXtoGroundX(target.getCenterX()), gh.entityYtoGroundY(target.getCenterY()));
        if (target != null)
        {
            if (!this.destroyed)
            {
                float prevAngle = this.hitBox.getAngle();

                this.updateDriveNew(target, delta, path);
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
    int driveDirection = 1;

    private void updateDriveNew(TankEntity target, int delta, Path path)
    {
        if (!this.destroyed)
        {
            //drive at target
            if (this.register.canSee(this, target))
            {
                float angleToTarget = Utils.calculateAngle(this.hitBox, target.getHitBox());
                float turnRate = this.getTurnRate() / 1000 * delta;
                float turnDelta = Utils.clampFloat(Utils.wrapAngleDelta(angleToTarget - this.vector.getAngle()), -turnRate, turnRate);

                float distToTarget = Utils.getDistanceBetween(this.hitBox, target.getHitBox());
                float driveRate = this.getDriveSpeed() / 1000 * delta;
                float tankSpeed = 0;

                //if at acceptable angle to drive and not too close to target
                if (Math.abs(turnDelta) <= NO_DRIVE_ANGLE_OFF_THRESHOLD && Math.abs(distToTarget) >= this.DRIVE_TO_DIST)
                {
                    tankSpeed = driveRate;
                }

                this.vector.setSpeed(tankSpeed);
                this.vector.addAngle(turnDelta);
            }
            //take path
            else if (path != null)
            {
                if (path.getLength() > 0)
                {                          
                    float tankSpeed = 0;
                    float driveRate = this.getDriveSpeed() / 1000 * delta;

                    if(driveToPoint == null || Math.abs(Utils.getDistanceBetween(hitBox, driveToPoint)) < driveRate)
                    {
                        driveToPoint = new Point(gh.groundXtoEntityX(path.getX(1)), gh.groundYtoEntityY(path.getY(1)));
                    }
                    
                    float desiredAngle = Utils.calculateAngle(hitBox.getCenterX(), hitBox.getCenterY(), driveToPoint.getCenterX(), driveToPoint.getCenterX());

                    float turnRate = this.getTurnRate() / 1000 * delta;
                    float turnDelta = Utils.clampFloat(Utils.wrapAngleDelta(desiredAngle - this.vector.getAngle()), -turnRate, turnRate);
                    this.vector.addAngle(turnDelta);
                    if (Math.abs(Utils.wrapAngleDelta(desiredAngle - this.vector.getAngle())) <= NO_DRIVE_ANGLE_OFF_THRESHOLD)
                    {
                        tankSpeed = driveRate;
                    }
                    this.vector.setSpeed(tankSpeed);
                }
                else
                {

                }
            }
        }
    }

    @Override
    public void renderHelpers(Graphics g)
    {
        super.renderHelpers(g); //To change body of generated methods, choose Tools | Templates.
        if (path != null)
        {
            for (int i = 0; i < path.getLength(); i++)
            {
                g.fillOval(gh.groundXtoEntityX(path.getX(i)) - 4, gh.groundYtoEntityY(path.getY(i)) - 4, 8, 8);
            }
        }
        g.fillOval(this.hitBox.getCenterX() - 5, this.hitBox.getCenterY() - 5, 10, 10);
        g.setColor(Color.green);
        gh.getGroundArray()[gh.entityXtoGroundX(hitBox.getCenterX())][gh.entityYtoGroundY(hitBox.getCenterY())].renderHelpers(g);
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
            boolean driveStateSwitched = (driveState != prevDriveState);
            //System.out.println(driveState.toString() + " " + driveStateSwitched);
            prevDriveState = AIDriveState.valueOf(driveState.toString());
            System.out.println("Case when strt: " + driveState);

            float angleToTarget = Utils.calculateAngle(this.hitBox, target.getHitBox());
            float turnRate = this.getTurnRate() / 1000 * delta;
            float turnDelta = Utils.clampFloat(Utils.wrapAngleDelta(angleToTarget - this.vector.getAngle()), -turnRate, turnRate);

            float distToTarget = Utils.getDistanceBetween(this.hitBox, target.getHitBox());
            float driveRate = this.getDriveSpeed() / 1000 * delta;
            float tankSpeed = 0;

            switch (driveState)
            {
                case DRIVE_TO_TARGET:

                    //if at acceptable angle to drive and not too close to target
                    if (Math.abs(turnDelta) <= NO_DRIVE_ANGLE_OFF_THRESHOLD && Math.abs(distToTarget) >= this.DRIVE_TO_DIST)
                    {
                        tankSpeed = driveRate;
                    }

                    this.vector.setSpeed(tankSpeed);
                    this.vector.addAngle(turnDelta);

                    if (checkNewVectorCollide())
                    {
                        this.driveState = (AIDriveState.BACK_AWAY_FROM_COLLISION);
                    }
                    break;
                case BACK_AWAY_FROM_COLLISION:
                    if (driveStateSwitched)
                    {
                        drivenDistance = 0f;
                    }
                    this.vector.addAngle(turnDelta);
                    this.vector.setSpeed(-driveRate);
                    drivenDistance += driveRate;

                    if (drivenDistance > 100f || checkNewVectorCollide())
                    {
                        this.driveState = (AIDriveState.DRIVE_TO_TARGET);
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
     * Checks to see if new vector will collide.
     *
     * @return
     */
    private boolean checkNewVectorCollide()
    {
        //check to see if vector will collide by moving to vector spot
        CenteredRectangle tenitiveHitbox = new CenteredRectangle(hitBox);
        tenitiveHitbox.updateDelta(this.vector.getXComp(), this.vector.getYComp(), this.vector.getAngle() - tenitiveHitbox.getAngle());
        //if collide, go to collide mode
        return register.checkCollision(tenitiveHitbox, this.hitBox);
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
