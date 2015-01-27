/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.utils.Utils;

/**
 *
 * @author Kyle
 */
public class Camera
{

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private float renderOffsetX;
    private float renderOffsetY;

    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     */
    public Camera(CenteredRectangle rect)
    {
        update(rect);
    }

    /**
     * Camera class to define rendering values.
     *
     * @param rect
     */
    public Camera(float posX, float posY)
    {
        update(posX, posY);
    }

    /**
     * Updates the position of the camera to be centered around the rectangle.
     *
     * @param rect
     */
    public void update(CenteredRectangle rect)
    {
        update(rect.getCenterX(), rect.getCenterY());
    }

    /**
     * Updates the position of the camera to be centered around the given x,y
     * position
     *
     * @param posX
     * @param posY
     */
    public void update(float posX, float posY)
    {
        //gets the basic render offset
        renderOffsetX = posX - (this.SCREEN_WIDTH / 2);
        renderOffsetY = posY - (this.SCREEN_HEIGHT / 2);

        //calculates offset so that the camera never goes off the edge
        renderOffsetX = Utils.clampFloat(renderOffsetX, -BaseGround.GROUND_SIZE / 2, GameEngine.WORLD_WIDTH * BaseGround.GROUND_SIZE - BaseGround.GROUND_SIZE / 2 - SCREEN_WIDTH);
        renderOffsetY = Utils.clampFloat(renderOffsetY, -BaseGround.GROUND_SIZE / 2, GameEngine.WORLD_HEIGHT * BaseGround.GROUND_SIZE - BaseGround.GROUND_SIZE / 2 - SCREEN_HEIGHT);
        System.out.println(renderOffsetX + "," + renderOffsetY);

    }

    /**
     * Amount to shift the camera over in X
     *
     * @return
     */
    public float getRenderOffsetX()
    {
        return renderOffsetX;
    }

    /**
     * Amount to shift the camera over in Y
     *
     * @return
     */
    public float getRenderOffsetY()
    {
        return renderOffsetY;
    }

}
