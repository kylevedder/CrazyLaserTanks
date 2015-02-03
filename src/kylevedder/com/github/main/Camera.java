/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.slickstates.SlickStateSinglePlayer;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class Camera
{

    public static final float ZOOM_AMOUNT = .1f;

    private float renderOffsetX;
    private float renderOffsetY;
    
    private ScreenManager screenManager = null;

    private volatile float zoom;

    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     */
    public Camera(CenteredRectangle rect, ScreenManager screenManager) throws SlickException
    {
        this.screenManager = screenManager;
        update(rect);
        this.setZoom(1);
    }

    /**
     * Camera class to define rendering values.
     *
     * @param posX
     * @param posY
     */
    public Camera(float posX, float posY, ScreenManager screenManager) throws SlickException
    {    
        this.screenManager = screenManager;
        update(posX, posY);
        this.setZoom(1);
    }

    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     * @param zoom
     */
    public Camera(CenteredRectangle rect, float zoom, ScreenManager screenManager) throws SlickException
    {
        this.screenManager = screenManager;
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
    public Camera(float posX, float posY, float zoom, ScreenManager screenManager) throws SlickException
    {
        this.screenManager = screenManager;
        update(posX, posY);
        this.setZoom(zoom);
    }

    /**
     * Updates the position of the camera to be centered around the rectangle.
     *
     * @param rect
     */
    public void update(CenteredRectangle rect) throws SlickException
    {
        update(rect.getCenterX(), rect.getCenterY());
    }

    /**
     * Updates the position of the camera to be centered around the given x,y
     * position
     *
     * @param posX
     * @param posY
     * @param input
     */
    public void update(float posX, float posY) throws SlickException
    {
        //gets the basic render offset
        renderOffsetX = (posX - this.screenManager.getCurrentResWidth() / this.getZoom() / 2) * this.getZoom();
        renderOffsetY = (posY - this.screenManager.getCurrentResHeight()/ this.getZoom() / 2) * this.getZoom();

        //calculates offset so that the camera never goes off the edge
        renderOffsetX = Utils.clampFloat(renderOffsetX, -(BaseGround.GROUND_SIZE / 2) * this.getZoom(), (SlickStateSinglePlayer.WORLD_WIDTH * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2) * this.getZoom() - this.screenManager.getCurrentResWidth());
        renderOffsetY = Utils.clampFloat(renderOffsetY, -(BaseGround.GROUND_SIZE / 2) * this.getZoom(), (SlickStateSinglePlayer.WORLD_HEIGHT * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2) * this.getZoom() - this.screenManager.getCurrentResHeight());
        
    }

    

    /**
     * Useful utility to check if an item needs to be rendered.
     *
     * @param rect rectangle of the item
     * @param image image of the item
     * @param renderOffsetX renderer offset of the item in the X
     * @param renderOffsetY renderer offset of the item in the Y
     * @return
     */
    public static boolean isVisible(CenteredRectangle rect, Camera camera)
    {
        
        return //within screen X
                rect.getMinX() * camera.getZoom() - camera.getRenderOffsetX() + rect.getWidth() * camera.getZoom() > 0
                && (rect.getMinX() * camera.getZoom() - camera.getRenderOffsetX()) * camera.getZoom() < MainApp.screenManager.getCurrentResWidth() * camera.getZoom()

                && //within screen Y
                rect.getMinY() * camera.getZoom() - camera.getRenderOffsetY() + rect.getWidth() * camera.getZoom() > 0
                && (rect.getMinY() * camera.getZoom() - camera.getRenderOffsetY()) * camera.getZoom() < MainApp.screenManager.getCurrentResHeight() * camera.getZoom();
    }

    

    /**
     * Gets the zoom of the camera. 1 = 1x zoom, 2 = 2x zoom, etc
     *
     * @return
     */
    public synchronized float getZoom()
    {
        return zoom;
    }

    /**
     * Sets the zoom of the camera. 1 = 1x zoom, 2 = 2x zoom, etc
     *
     * @param zoom
     */
    public synchronized void setZoom(float zoom)
    {
        this.zoom = Utils.clampFloat(zoom, .4f, 1.5f);
    }

    /**
     * Adds the given value to the zoom of the camera.
     *
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
