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
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class Camera
{

    public static final int WINDOWED_SCREEN_WIDTH = 640;
    public static final int WINDOWED_SCREEN_HEIGHT = 480;

    public static final int FULL_SCREEN_WIDTH = 1920;
    public static final int FULL_SCREEN_HEIGHT = 1080;

    public static final boolean FULLSCREEN_START = false;

    public boolean fullscreen = false;

    public int currentResWidth = (FULLSCREEN_START) ? FULL_SCREEN_WIDTH : WINDOWED_SCREEN_WIDTH;
    public int currentResHeight = (FULLSCREEN_START) ? FULL_SCREEN_HEIGHT : WINDOWED_SCREEN_HEIGHT;

    public static final float ZOOM_AMOUNT = .1f;

    private float renderOffsetX;
    private float renderOffsetY;

    private volatile float zoom;

    /**
     * Camera class to define rendering values.
     *
     * @param rect - Rectangle to center the camera around.
     */
    public Camera(CenteredRectangle rect)
    {
        this.fullscreen = FULLSCREEN_START;
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
        this.fullscreen = FULLSCREEN_START;
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
        this.fullscreen = FULLSCREEN_START;
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
        this.fullscreen = FULLSCREEN_START;
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
     * Updates the position of the camera to be centered around the rectangle
     * and allows for fullscreen toggling.
     *
     * @param rect
     * @param input
     */
    public void update(CenteredRectangle rect, Input input)
    {
        update(rect.getCenterX(), rect.getCenterY(), input);
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
        this.update(posX, posY, null);

    }

    /**
     * Updates the position of the camera to be centered around the given x,y
     * position and allows for fullscreen toggling
     *
     * @param posX
     * @param posY
     * @param input
     */
    public void update(float posX, float posY, Input input)
    {
        //gets the basic render offset
        renderOffsetX = (posX - this.currentResWidth / this.getZoom() / 2) * this.getZoom();
        renderOffsetY = (posY - this.currentResHeight / this.getZoom() / 2) * this.getZoom();

        //calculates offset so that the camera never goes off the edge
        renderOffsetX = Utils.clampFloat(renderOffsetX, -(BaseGround.GROUND_SIZE / 2) * this.getZoom(), (GameEngine.WORLD_WIDTH * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2) * this.getZoom() - WINDOWED_SCREEN_WIDTH);
        renderOffsetY = Utils.clampFloat(renderOffsetY, -(BaseGround.GROUND_SIZE / 2) * this.getZoom(), (GameEngine.WORLD_HEIGHT * BaseGround.GROUND_SIZE) * this.getZoom() - (BaseGround.GROUND_SIZE / 2) * this.getZoom() - WINDOWED_SCREEN_HEIGHT);

        //update if in fullscreen
        if (input != null)
        {
            if (input.isKeyPressed(Input.KEY_F11))
            {
                this.toggleFullscreen();
            }
        }
    }

    /**
     * Gets the current resolution height of the screen.
     * @return 
     */
    public int getCurrentResHeight()
    {
        return currentResHeight;
    }

    /**
     * Gets the current resolution width of the screen.
     * @return 
     */
    public int getCurrentResWidth()
    {
        return currentResWidth;
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
    public static boolean isVisible(CenteredRectangle rect, float renderOffsetX, float renderOffsetY)
    {
        return //within screen X
                rect.getMinX() * MainApp.gameEngine.camera.getZoom() - renderOffsetX + rect.getWidth() * MainApp.gameEngine.camera.getZoom() > 0
                && (rect.getMinX() * MainApp.gameEngine.camera.getZoom() - renderOffsetX) * MainApp.gameEngine.camera.getZoom() < MainApp.gameEngine.camera.getCurrentResWidth() * MainApp.gameEngine.camera.getZoom()

                && //within screen Y
                rect.getMinY() * MainApp.gameEngine.camera.getZoom() - renderOffsetY + rect.getWidth() * MainApp.gameEngine.camera.getZoom() > 0
                && (rect.getMinY() * MainApp.gameEngine.camera.getZoom() - renderOffsetY) * MainApp.gameEngine.camera.getZoom() < MainApp.gameEngine.camera.getCurrentResHeight() * MainApp.gameEngine.camera.getZoom();
    }

    /**
     * Checks if the game is in full screen
     *
     * @return
     */
    public boolean isFullscreen()
    {
        return fullscreen;
    }

    /**
     * Sets the game to be in fullscreen
     *
     * @param fullscreen
     */
    public void setFullscreen(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
        try
        {
            if (fullscreen)
            {
                currentResHeight = FULL_SCREEN_HEIGHT;
                currentResWidth = FULL_SCREEN_WIDTH;                
            }
            else
            {
                currentResHeight = WINDOWED_SCREEN_HEIGHT;
                currentResWidth = WINDOWED_SCREEN_WIDTH;                
            }
            MainApp.app.setDisplayMode(currentResWidth, currentResHeight, this.fullscreen);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Toggles the game in and out of fullscreen
     */
    public void toggleFullscreen()
    {
        setFullscreen(!this.fullscreen);
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
