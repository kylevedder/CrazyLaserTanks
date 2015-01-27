/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class Camera
{

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    
    public static final float ZOOM_AMOUNT = .1f;

    private float renderOffsetX;
    private float renderOffsetY;
    
    private float zoom;

    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     */
    public Camera(CenteredRectangle rect)
    {
        update(rect);
        this.setZoom(1);
    }

    /**
     * Camera class to define rendering values.
     *
     * @param posX
     * @param posY
     */
    public Camera(float posX, float posY)
    {
        update(posX, posY);
        this.setZoom(1);
    }
    
    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     * @param zoom
     */
    public Camera(CenteredRectangle rect, float zoom)
    {
        update(rect);
        this.setZoom(zoom);
    }

    /**
     * Camera class to define rendering values.
     *
     * @param posX
     * @param posY
     * @param zoom
     */
    public Camera(float posX, float posY, float zoom)
    {
        update(posX, posY);
        this.setZoom(zoom);
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
        renderOffsetX = (posX - this.SCREEN_WIDTH / this.getZoom() / 2) * this.getZoom();
        renderOffsetY = (posY - this.SCREEN_HEIGHT/ this.getZoom() / 2) * this.getZoom();

        //calculates offset so that the camera never goes off the edge
        renderOffsetX = Utils.clampFloat(renderOffsetX, - (BaseGround.GROUND_SIZE / 2)* this.getZoom(), (GameEngine.WORLD_WIDTH * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2)* this.getZoom() - SCREEN_WIDTH);
        renderOffsetY = Utils.clampFloat(renderOffsetY, - (BaseGround.GROUND_SIZE / 2)* this.getZoom(), (GameEngine.WORLD_HEIGHT * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2)* this.getZoom() - SCREEN_HEIGHT);
    }
    
//    public void updateZoom(Input input)
//    {
//        if()
//    }
    
    
    /**
     * Useful utility to check if an item needs to be rendered.
     *
     * @param rect rectangle of the item
     * @param image image of the item
     * @param renderOffsetX renderer offset of the item in the X
     * @param renderOffsetY renderer offset of the item in the Y
     * @return
     */
    public static boolean isVisible(CenteredRectangle rect, float renderOffsetX, float renderOffsetY)
    {
        return //within screen X
                rect.getMinX() * MainApp.gameEngine.camera.getZoom() - renderOffsetX + rect.getWidth() * MainApp.gameEngine.camera.getZoom() > 0 
                && (rect.getMinX() * MainApp.gameEngine.camera.getZoom() - renderOffsetX)  * MainApp.gameEngine.camera.getZoom()  < Camera.SCREEN_WIDTH * MainApp.gameEngine.camera.getZoom()
                
                && //within screen Y
                
                rect.getMinY() * MainApp.gameEngine.camera.getZoom()- renderOffsetY + rect.getWidth() * MainApp.gameEngine.camera.getZoom() > 0 
                && (rect.getMinY() * MainApp.gameEngine.camera.getZoom() - renderOffsetY) * MainApp.gameEngine.camera.getZoom() < Camera.SCREEN_HEIGHT * MainApp.gameEngine.camera.getZoom();
    }

    /**
     * Gets the zoom of the camera. 1 = 1x zoom, 2 = 2x zoom, etc
     * @return 
     */
    public synchronized float getZoom()
    {
        return zoom;
    }

    /**
     * Sets the zoom of the camera. 1 = 1x zoom, 2 = 2x zoom, etc
     * @param zoom 
     */
    public synchronized void setZoom(float zoom)
    {
        this.zoom = Utils.clampFloat(zoom, .4f, 1.5f);
    }
    
    /**
     * Adds the given value to the zoom of the camera.
     * @param zoom 
     */
    public synchronized void addZoom(float zoom)
    {
        this.setZoom(zoom + this.getZoom());
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
