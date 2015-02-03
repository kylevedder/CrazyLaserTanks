/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class ScreenManager
{

    public static final int WINDOWED_SCREEN_WIDTH = 640;
    public static final int WINDOWED_SCREEN_HEIGHT = 480;    
    
    private int currentResWidth = WINDOWED_SCREEN_WIDTH;
    private int currentResHeight = WINDOWED_SCREEN_HEIGHT;
    
    private boolean fullscreen = false;
    
    private GameContainer gc = null;

    /**
     * Object for managing the state of the screen.
     * @param gc
     * @param fullscreen
     * @throws SlickException 
     */
    public ScreenManager(GameContainer gc, boolean fullscreen) throws SlickException
    {
        this.fullscreen = fullscreen;
        this.gc = gc;
        this.setFullscreen(fullscreen);
    }
    
    /**
     * Updates this object
     * @param input
     * @throws SlickException 
     */
    public void update(Input input) throws SlickException
    {
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
     * Sets the screen to be full screen or windowed screen size.
     * @param fullscreen 
     */
    public void setFullscreen(boolean fullscreen) throws SlickException
    {
        this.fullscreen = fullscreen;
        currentResWidth = (fullscreen) ? gc.getScreenWidth() : WINDOWED_SCREEN_WIDTH;
        currentResHeight = (fullscreen) ? gc.getScreenHeight() : WINDOWED_SCREEN_HEIGHT;
        MainApp.app.setDisplayMode(currentResWidth, currentResHeight, fullscreen);
    }
    
    /**
     * Toggles the screen between windowed and fullscreen.
     * @throws SlickException 
     */
    public void toggleFullscreen() throws SlickException
    {
        this.setFullscreen(!fullscreen);
    }

    /**
     * Checks if the screen is windowed or fullscreen.
     * @return 
     */
    public boolean isFullscreen()
    {
        return fullscreen;
    }
    
    

    /**
     * Gets the current height of the screen.
     * @return 
     */
    public int getCurrentResHeight()
    {
        return currentResHeight;
    }

    /**
     * Gets the current width of the screen.
     * @return 
     */
    public int getCurrentResWidth()
    {
        return currentResWidth;
    }
    

    

}
