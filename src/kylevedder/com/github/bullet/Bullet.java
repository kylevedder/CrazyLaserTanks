/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.bullet;

import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.animation.CustomAnimation;
import kylevedder.com.github.animation.TerminalCustomAnimation;
import kylevedder.com.github.entity.BaseEntity;
import kylevedder.com.github.interfaces.Renderable;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;

/**
 *
 * @author Kyle
 */
public class Bullet implements Renderable
{

    private final float BALL_SCALE = 0.25f;
    private final float EXPLOSION_SCALE = 2f;
    private final int EXPLOSION_SIZE = 9;
    private final int EXPLOSION_SPEED = 100;

    private boolean exist = true;
    private Vector vector = null;
    private Circle c = null;
    private Image ball = null;
    private TerminalCustomAnimation explosion = null;
    private BaseEntity parentPointer = null;

    private Image imageToRender = null;

    protected final float DAMAGE = 1f;

    public Bullet(float x, float y, float speed, float angle, BaseEntity parentPointer)
    {
        try
        {
            this.vector = new Vector(speed, angle);
            imageToRender = ball = new Image("images/ball.png").getScaledCopy(BALL_SCALE);
            explosion = new TerminalCustomAnimation(new SpriteSheet(new Image("images/explosionNew.png").getScaledCopy(EXPLOSION_SCALE), (int) (EXPLOSION_SIZE * EXPLOSION_SCALE), (int) (EXPLOSION_SIZE * EXPLOSION_SCALE)), EXPLOSION_SPEED);
            this.c = new Circle(x, y, ball.getWidth() / 2);
            this.parentPointer = parentPointer;
            this.exist = true;
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Input input, int delta)
    {
        if (this.exist)
        {
            float xComp = this.vector.getXComp() / (1000 / delta);
            float yComp = this.vector.getYComp() / (1000 / delta);
            Line line = new Line(this.c.getCenterX(), this.c.getCenterY(), this.c.getCenterX() + xComp, this.c.getCenterY() + yComp);
            BaseEntity be = MainApp.gameEngine.register.checkCollisionWithEntity(line, parentPointer);
            if (be != null)
            {
                be.addDamage(DAMAGE);
                this.explosion.update(delta);
                this.imageToRender = this.explosion.getFrame(false);
                if (this.imageToRender == null)
                {
                    this.destroy();
                }
            }
            else if (MainApp.gameEngine.register.checkCollisionWithGround(line))
            {
                this.explosion.update(delta);
                this.imageToRender = this.explosion.getFrame(false);
                if (this.imageToRender == null)
                {
                    this.destroy();
                }
            }
            else
            {
                this.c.setCenterX(this.c.getCenterX() + xComp);
                this.c.setCenterY(this.c.getCenterY() - yComp);
                this.imageToRender = this.ball;
            }
        }

    }

    /**
     * Destroys the object
     */
    public void destroy()
    {
        this.exist = false;
    }

    /**
     * Checks to see if this object exists
     *
     * @return
     */
    public boolean doesExist()
    {
        return this.exist;
    }

    @Override
    public void render()
    {
        if (this.exist)
        {
            this.imageToRender.drawCentered(this.c.getCenterX(), this.c.getCenterY());
        }
    }

}